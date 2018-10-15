package util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import teprinciple.updateapputils.R;


/**
 * Created by Teprinciple on 2016/12/13.
 */
class DownloadAppUtils {
    private static final String TAG = DownloadAppUtils.class.getSimpleName();
    public static long downloadUpdateApkId = -1;//下载更新Apk 下载任务对应的Id
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径


    /**
     * 通过浏览器下载APK包
     *
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void download(final Context context, String url, final String serverVersionName, final boolean isShowProgressDialog) {

        String packageName = context.getPackageName();
        String filePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//外部存储卡
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            Log.i(TAG, "没有SD卡");
            return;
        }

        String apkLocalPath = filePath + File.separator + packageName + "_" + serverVersionName + ".apk";
        Log.i(TAG, apkLocalPath);
        downloadUpdateApkFilePath = apkLocalPath;

        FileDownloader.setup(context);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        if (isShowProgressDialog) {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
            progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            progressDialog.setTitle(context.getResources().getString(R.string.download_now));
            progressDialog.setMax(100);
            progressDialog.show();
        }
        FileDownloader.getImpl().create(url)
                .setPath(apkLocalPath)
                .setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        if (isShowProgressDialog) {
                            progressDialog.incrementProgressBy((int) (soFarBytes * 100.0 / totalBytes));
                        } else {
                            send(context, (int) (soFarBytes * 100.0 / totalBytes), serverVersionName);
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        if (isShowProgressDialog) {
                            progressDialog.cancel();
                            installApk(context);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.exit(0);
                        } else {
                            send(context, 100, serverVersionName);
                        }
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        progressDialog.cancel();
                        Toast.makeText(context, context.getResources().getString(R.string.download_error), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }


    private static void send(Context context, int progress, String serverVersionName) {
        Intent intent = new Intent("teprinciple.update");
        intent.putExtra("progress", progress);
        intent.putExtra("title", serverVersionName);
        context.sendBroadcast(intent);
    }

    private static void installApk(Context context) {
        try {
            if (DownloadAppUtils.downloadUpdateApkFilePath != null) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                File apkFile = new File(DownloadAppUtils.downloadUpdateApkFilePath);
                if (UpdateAppUtils.needFitAndroidN && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(
                            context, context.getPackageName() + ".factory.provider", apkFile);
                    i.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    i.setDataAndType(Uri.fromFile(apkFile),
                            "application/vnd.android.package-archive");
                }
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        } catch (Exception e) {
            Log.d("installApk Exception", e.getMessage());
        }
    }
}
