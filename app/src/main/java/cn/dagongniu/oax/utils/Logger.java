package cn.dagongniu.oax.utils;

import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import cn.dagongniu.oax.OAXApplication;


/**
 * 类描述：logger
 */

public class Logger
{
	public static final  int     VERBOSE     = 2;
	public static final  int     DEBUG       = 3;
	public static final  int     INFO        = 4;
	public static final  int     WARN        = 5;
	public static final  int     ERROR       = 6;
	public static final  int     ASSERT      = 7;
	private static final boolean isLogEnable = OAXApplication.getAppLog(); //true:able,iv_false:unable.
	private static final int     LOG_LEVEL   = VERBOSE;

	public static void d(String TAG, String msg)
	{
		if (isLogEnable && LOG_LEVEL <= DEBUG)
		{
			Log.d(TAG, msg);
			save(DateUtils.getSystemTime() + "-TAG:" + TAG + "\tlog:" + msg + "\n");
		}
	}

	public static void i(String TAG, String msg)
	{
		if (isLogEnable && LOG_LEVEL <= INFO)
		{
			Log.i(TAG, msg);
			save(DateUtils.getSystemTime() + "TAG:" + TAG + "\tlog:" + msg + "\n");
		}
	}

	public static void e(String TAG, String msg)
	{
		if (isLogEnable && LOG_LEVEL <= ERROR)
		{
			Log.e(TAG, msg);

			save(DateUtils.getSystemTime() + "TAG:" + TAG + "\tlog:" + msg + "\n");
		}
	}

	public static void v(String TAG, String msg)
	{
		if (isLogEnable && LOG_LEVEL <= VERBOSE)
		{
			Log.v(TAG, msg);
			save(DateUtils.getSystemTime() + "TAG:" + TAG + "\tlog:" + msg + "\n");
		}
	}

	public static void w(String TAG, String msg)
	{
		if (isLogEnable && LOG_LEVEL <= WARN)
		{
			Log.w(TAG, msg);
			save(DateUtils.getSystemTime() + "TAG:" + TAG + "\tlog:" + msg + "\n");
		}
	}


	//store log
	//use 使用“iv_false”之前释放。
	static        boolean isPathExist   = false;
	public static boolean isStoreInFile = false;

	public static boolean save(String pathString, String fileName, String msg)
	{
		if (isStoreInFile)
		{
			File pathFile = new File(pathString);
			if (!pathFile.exists())
			{
				isPathExist = pathFile.mkdirs();
			}
			if (isPathExist)
			{
				File file = new File(pathFile, fileName);
				try
				{
					FileOutputStream e                  = new FileOutputStream(file, true);
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(e, "UTF-8");
					outputStreamWriter.write(msg);
					outputStreamWriter.flush();
					e.close();
					return true;
				} catch (FileNotFoundException var6)
				{
					var6.printStackTrace();
					return false;
				} catch (UnsupportedEncodingException var7)
				{
					var7.printStackTrace();
					return false;
				} catch (IOException var8)
				{
					var8.printStackTrace();
					return false;
				} catch (Exception var9)
				{
					var9.printStackTrace();
					return false;
				}
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	public static boolean save(String fileName, String msg)
	{
		return save(Environment.getExternalStorageDirectory().getPath() + "/Log_wo", fileName, msg);
	}

	public static boolean save(String msg)
	{
		return save(Environment.getExternalStorageDirectory().getPath() + "/Log_wo", DateUtils.getSystemTime("yyyy年MM月dd日HH点") + ".txt", msg);
	}
}
