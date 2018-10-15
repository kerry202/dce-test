package cn.dagongniu.oax.https;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 获取网络状态
 */
public class PrefUtils {

	/**
	 * 获取网络状态
	 *
	 * @param context Context
	 * @param type    获取网络的类型，0类型返回 不能上网（0）或者 可以上网 （1）；1类型返回具体类型，参见@return
	 * @return -1没有网络连接，TYPE_MOBILE 0，TYPE_WIFI 1，TYPE_WIMAX 6，TYPE_BLUETOOTH 7，TYPE_ETHERNET 9，TYPE_VPN 17，other
	 */
	public static int getNetState(Context context, int type) {
		int result;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			result = -1;
		} else {
			NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isConnected() && mNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
				result = mNetworkInfo.getType();
			} else {
				result = -1;
			}
		}
		if (type == 0) return result < 0 ? 0 : result <= 6 || result == 9 || result == 17 ? 1 : 0;
		return result;
	}
}
