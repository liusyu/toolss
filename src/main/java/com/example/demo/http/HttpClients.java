package com.example.demo.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;

public class HttpClients {

	private static Log logger = LogFactory.getLog(HttpClients.class);
	
	protected static Gson json;

	static {
		json = new Gson();
	}
	
	public static HttpEntity get(String url, Map<String, String> parameters) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
        if (parameters != null) {
            params.putAll(parameters);
        }
        if(!CollectionUtils.isEmpty(params))
        	url += "?" + buildQuery(params, "UTF-8");
		System.out.println("api url:" + url);
        
        HttpEntity entity = null;
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpClient client = new DefaultHttpClient();
			if(url.startsWith("https")) {
				enableSSL(client);
			}
			httpGet.addHeader("Cookie", "JSESSIONID=18478311F3334F83C60CAFC1202B47DD; bjui_font=bjui-font-c; org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE=zh; FILTER_NOT_FOUND_IMG=%7B%22%E8%80%83%E5%8B%A4%E8%AF%B7%E5%81%87%E6%83%85%E5%86%B5%E7%BB%9F%E8%AE%A1%22%3Atrue%2C%22%E6%91%84%E5%BD%B1%E6%91%84%E5%83%8F%E6%8B%8D%E6%91%84%E5%88%B6%E4%BD%9C%E6%9C%8D%E5%8A%A1%E7%94%B3%E8%AF%B7%22%3Atrue%2C%22%E6%98%8E%E6%95%85%E5%AE%AB%E6%A0%A1%E5%8C%BA%E7%94%B5%E5%AD%90%E6%98%BE%E7%A4%BA%E5%B1%8F%E4%BF%A1%E6%81%AF%E5%8F%91%E5%B8%83%E5%AE%A1%E6%89%B9%22%3Atrue%7D");
			//httpGet.addHeader("User-Agent:", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");
			HttpResponse response = client.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			entity = response.getEntity();
			if (status != 200) {
				throw new RuntimeException(String.format("api return error http code %d, detail: \n%s", status,
						EntityUtils.toString(entity, "UTF-8")));
			}
			return entity;
		} catch (Exception e) {
			String msg = String.format("Failed to call api '%s'", url);
			logger.error(msg, e);
			httpGet.abort();
			throw e;
		}
	}
	
	public static String getString(String url) throws Exception {
		return getString(url, null);
	}
	
	public static String getString(String url, Map<String, String> parameters) throws Exception {
		return EntityUtils.toString(get(url, parameters), "UTF-8");
	}
	
	public static <T> T getJson(String url, Class<T> clazz) throws Exception {
		return getJson(url, new LinkedHashMap<String, String>(), clazz);
	}
	
	public static <T> T getJson(String url, Map<String, String> parameters, Class<T> clazz) throws Exception {
		return json.fromJson(getString(url, parameters), clazz);
	}
	
//	public static <T> T getXml(String url, Class<T> clazz) throws Exception {
//		return getXml(url, new LinkedHashMap<String, String>(), clazz);
//	}
	
//	public static <T> T getXml(String url, Map<String, String> parameters, Class<T> clazz) throws Exception {
//		return JaxbMapper.fromXml(getString(url, parameters), clazz);
//	}
	
	public static HttpEntity put(String url, Map<String, String> parameters) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
        if (parameters != null) {
            params.putAll(parameters);
        }
        
        System.out.println("api url:" + url);
		System.out.println("api params:" + json.toJson(params));
        
        HttpEntity entity = null;
        HttpPut httpPut = new HttpPut(url);
		try {
			HttpClient client = new DefaultHttpClient();
			if(url.startsWith("https")) {
				enableSSL(client);
			}
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
			}
			httpPut.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
			
			HttpResponse response = client.execute(httpPut);
			int status = response.getStatusLine().getStatusCode();
			entity = response.getEntity();
			if (status != 200) {
				throw new RuntimeException(String.format("api return error http code %d, detail: \n%s", status,
						EntityUtils.toString(entity, "UTF-8")));
			}
			return entity;
		} catch (Exception e) {
			String msg = String.format("Failed to call api '%s'", url);
			logger.error(msg, e);
			httpPut.abort();
			throw e;
		}
	}
	
	public static String putString(String url, Map<String, String> parameters) throws Exception {
		return EntityUtils.toString(put(url, parameters), "UTF-8");
	}
	
	public static <T> T putJson(String url, Map<String, String> parameters, Class<T> clazz) throws Exception {
		return json.fromJson(putString(url, parameters), clazz);
	}
	
//	public static <T> T putXml(String url, Map<String, String> parameters, Class<T> clazz) throws Exception {
//		return JaxbMapper.fromXml(putString(url, parameters), clazz);
//	}
	
	public static HttpEntity post(String url, Map<String, String> parameters) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
        if (parameters != null) {
            params.putAll(parameters);
        }
        
        System.out.println("api url:" + url);
		System.out.println("api params:" + json.toJson(params));
        
        HttpEntity entity = null;
        HttpPost httpPost = new HttpPost(url);
		try {
			HttpClient client = new DefaultHttpClient();
			if(url.startsWith("https")) {
				enableSSL(client);
			}
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
			
			HttpResponse response = client.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			entity = response.getEntity();
			if (status != 200) {
				throw new RuntimeException(String.format("api return error http code %d, detail: \n%s", status,
						EntityUtils.toString(entity, "UTF-8")));
			}
			return entity;
		} catch (Exception e) {
			String msg = String.format("Failed to call api '%s'", url);
			logger.error(msg, e);
			httpPost.abort();
			throw e;
		}
	}
	
	public static String postString(String url, Map<String, String> parameters) throws Exception {
		return EntityUtils.toString(post(url, parameters), "UTF-8");
	}
	
	public static <T> T postJson(String url, Map<String, String> parameters, Class<T> clazz) throws Exception {
		return json.fromJson(postString(url, parameters), clazz);
	}
	
//	public static <T> T postXml(String url, Map<String, String> parameters, Class<T> clazz) throws Exception {
//		return JaxbMapper.fromXml(postString(url, parameters), clazz);
//	}
	
	/**
     * 
     * @param params 请求参数
     * @return 构建query
     */
    public static String buildQuery(Map<String, String> params, String charset) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                try {
                    sb.append(key).append("=").append(URLEncoder.encode(value, charset));
                } catch (UnsupportedEncodingException e) {}
            }
        }
        return sb.toString();

    }
	
	private static TrustManager truseAllManager = new X509TrustManager() {

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}

		@Override
		public void checkClientTrusted(X509Certificate[] cert, String oauthType)
				throws java.security.cert.CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] cert, String oauthType)
				throws java.security.cert.CertificateException {
		}
	};

	private static void enableSSL(HttpClient httpclient) {
		// 调用ssl
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", sf, 443);
			httpclient.getConnectionManager().getSchemeRegistry().register(https);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
