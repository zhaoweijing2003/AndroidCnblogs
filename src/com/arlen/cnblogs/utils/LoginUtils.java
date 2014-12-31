package com.arlen.cnblogs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.util.Log;

public class LoginUtils {

	private static final String TAG = LoginUtils.class.getSimpleName();

	private static AndroidHttpClient httpClient = AndroidHttpClient
			.newInstance("");
	private static BasicHttpContext httpContext = new BasicHttpContext();
	private static BasicCookieStore cookieStore = new BasicCookieStore();

	public static boolean login(String userName, String password,
			Map<String, String> map) throws Exception {
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		HttpPost httpPost = new HttpPost(AppMacros.CNBLOGS_LOGIN);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE", map.get("__VIEWSTATE")));
		params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", map
				.get("__VIEWSTATEGENERATOR")));
		params.add(new BasicNameValuePair("__EVENTVALIDATION", map
				.get("__EVENTVALIDATION")));
		params.add(new BasicNameValuePair("tbUserName", userName));
		params.add(new BasicNameValuePair("tbPassword", password));
		params.add(new BasicNameValuePair("chkRemember", "on"));
		params.add(new BasicNameValuePair("btnLogin", map.get("btnLogin")));
		params.add(new BasicNameValuePair("txtReturnUrl", map
				.get("txtReturnUrl")));

		httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Origin", "http://passport.cnblogs.com");
		httpPost.addHeader("Referer", "http://passport.cnblogs.com/login.aspx");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		try {
			HttpResponse httpResponse = httpClient.execute(httpPost,
					httpContext);

			Log.i(TAG, "=======" + httpResponse.getAllHeaders().length);

			Header locationHeader = httpResponse.getFirstHeader("Location");

			if (locationHeader != null) {
				Log.i(TAG, "��½�ɹ���" + locationHeader.getValue());
				return true;
			} else {
				Log.i(TAG, "��¼ʧ�ܣ���������");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean login(String userName, String password, String code,
			Map<String, String> map) throws Exception {
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		HttpPost httpPost = new HttpPost(AppMacros.CNBLOGS_LOGIN);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE", map.get("__VIEWSTATE")));
		params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", map
				.get("__VIEWSTATEGENERATOR")));
		params.add(new BasicNameValuePair("__EVENTVALIDATION", map
				.get("__EVENTVALIDATION")));
		params.add(new BasicNameValuePair("tbUserName", userName));
		params.add(new BasicNameValuePair("tbPassword", password));
		params.add(new BasicNameValuePair("LBD_VCID_c_login_logincaptcha", map
				.get("LBD_VCID_c_login_logincaptcha")));
		params.add(new BasicNameValuePair(
				"LBD_BackWorkaround_c_login_logincaptcha", "1"));
		params.add(new BasicNameValuePair("CaptchaCodeTextBox", code));
		params.add(new BasicNameValuePair("chkRemember", "on"));
		params.add(new BasicNameValuePair("btnLogin", map.get("btnLogin")));
		params.add(new BasicNameValuePair("txtReturnUrl", map
				.get("txtReturnUrl")));

		httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Origin", "http://passport.cnblogs.com");
		httpPost.addHeader("Referer", "http://passport.cnblogs.com/login.aspx");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		for (int i = 0; i < params.size(); i++) {
			System.out.print(params.get(i).getName());
			System.out.print(" === ");
			System.out.println(params.get(i).getValue());
		}

		try {
			HttpResponse httpResponse = httpClient.execute(httpPost,
					httpContext);

			Log.i(TAG, "=======" + httpResponse.getAllHeaders().length);

			Header locationHeader = httpResponse.getFirstHeader("Location");

			HttpGet httpget = new HttpGet(
					"http://home.cnblogs.com/ajax/ing/MyLastIng");

			try {
				HttpResponse re2 = httpClient.execute(httpget);
				// �����¼�ɹ����ҳ��
				String str = EntityUtils.toString(re2.getEntity());
				System.out.println(str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				httpget.abort();
			}

			if (locationHeader != null) {
				Log.i(TAG, "��½�ɹ���" + locationHeader.getValue());
				return true;
			} else {
				Log.i(TAG, "��¼ʧ�ܣ���������");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
