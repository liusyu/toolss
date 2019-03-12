package com.example.demo.http;


import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

public class HttpConnection {
	public static JSONObject doGet(String requestURL, String outputStr) {
		JSONObject jsonObject = new JSONObject();
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferReader = null;
		try {
			URL url = new URL(requestURL);
			conn = (HttpURLConnection) (url.openConnection());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(HttpMethod.GET.name());
			conn.setRequestProperty(MediaTypes.CONTENT_TYPE,MediaTypes.TEXT_HTML_UTF_8);
			if (null != outputStr) {
				os = conn.getOutputStream();
				os.write(outputStr.getBytes( MediaTypes.UTF_8_CHARSET));
			}
			is = conn.getInputStream();
			inputStreamReader = new InputStreamReader(is,  MediaTypes.UTF_8_CHARSET);
			bufferReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			jsonObject.put("message", e.getMessage());
		} finally {
			try {
					closeStream(inputStreamReader,null,os,is,bufferReader);
				} catch (Exception e) {
;
				}
		}
		return jsonObject;

	}


    /**
     *二进制加密文件请求
     * @param url
     * @param inputStream1
     * @param fileSercret
     * @return
     * @throws IOException
     * @throws Exception
     */
    protected  JSONObject doPutFile(String url,InputStream inputStream1,String fileSercret)
            throws IOException, Exception {
        HttpURLConnection httpURLConnection = createHttpURLConnection(url, fileSercret,true);
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        BufferedReader bufferReader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream1.read(bytes))!= -1){
                outputStream.write(bytes, 0, length);
            }
            outputStreamWriter.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is "
                                + httpURLConnection.getResponseCode());
            }
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, MediaTypes.UTF_8_CHARSET);
            bufferReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferReader.readLine()) != null) {
                buffer.append(str);
            }
        } finally {
            closeStream(inputStreamReader,outputStreamWriter,outputStream,inputStream,reader);
        }

        return JSONObject.fromObject(buffer.toString());
    }
	public static JSONObject doPost(String requestURL, String outputStr)  throws IOException, Exception {
		JSONObject jsonObject = new JSONObject();
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferReader = null;

		try {
			URL url = new URL(requestURL);
			conn = (HttpURLConnection) (url.openConnection());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty(MediaTypes.CONTENT_TYPE,
					"application/x-www-form-urlencoded");

			if (null != outputStr) {
				os = conn.getOutputStream();
				os.write(outputStr.getBytes( MediaTypes.UTF_8_CHARSET));
			}

			is = conn.getInputStream();
			inputStreamReader = new InputStreamReader(is,  MediaTypes.UTF_8_CHARSET);
			bufferReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			jsonObject.put("message", e.getMessage());
		} finally {
			closeStream(inputStreamReader,null,os,is,bufferReader);
		}
		return jsonObject;
	}

	@SuppressWarnings("rawtypes")
	public static JSONObject doPut(String url, Map parameterMap)
			throws IOException, Exception {
		StringBuffer parameterBuffer = new StringBuffer();
		connectParam(parameterBuffer,parameterMap);
		URL localURL = new URL(url);
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("PUT");
		httpURLConnection.setRequestProperty("Accept-Charset",  MediaTypes.UTF_8_CHARSET);
		httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		BufferedReader bufferReader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);
			outputStreamWriter.write(parameterBuffer.toString());
			outputStreamWriter.flush();
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception(
						"HTTP Request is not success, Response code is "
								+ httpURLConnection.getResponseCode());
			}
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,  MediaTypes.UTF_8_CHARSET);
			bufferReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
		} finally {
			closeStream(inputStreamReader,outputStreamWriter,outputStream,inputStream,reader);
		}

		return JSONObject.fromObject(buffer.toString());
	}
//===========================================================common=======================
	private static void connectParam(StringBuffer parameterBuffer,Map parameterMap){
		if (parameterMap != null) {
			Iterator iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key).toString();
				} else {
					value = "";
				}

				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
	}
	private HttpURLConnection createHttpURLConnection(String url) throws IOException {
		return createHttpURLConnection(url,false);
	}

	/**
	 * 是否二进制流请求
	 * @param url
	 * @param isStream
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection createHttpURLConnection(String url,boolean isStream) throws IOException {
		return createHttpURLConnection(url,isStream);
	}
	private HttpURLConnection createHttpURLConnection(String url,String fileSercret,boolean isStream) throws IOException {
		URL localURL = new URL(url);
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod(HttpMethod.PUT.name());
		httpURLConnection.setRequestProperty(MediaTypes.ACCEPT_CHARSET, MediaTypes.UTF_8_CHARSET);
		if(isStream)
				httpURLConnection.setRequestProperty(MediaTypes.CONTENT_MD5, fileSercret );
		if(StringUtils.isNotBlank(fileSercret)) {
			httpURLConnection.setRequestProperty(MediaTypes.CONTENT_TYPE,MediaTypes.OCTET_STREAM);
		}
		return httpURLConnection;
	}
	private static URLConnection openConnection(URL localURL)
			throws IOException {
		return localURL.openConnection();
	}
	private static void closeStream(InputStreamReader inputStreamReader,OutputStreamWriter outputStreamWriter,OutputStream outputStream,InputStream inputStream,BufferedReader reader) throws IOException {
		if (outputStreamWriter != null) {
			outputStreamWriter.close();
		}
		if (outputStream != null) {
			outputStream.close();
		}
		if (reader != null) {
			reader.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}
	}
//	private static URLConnection openConnection(URL localURL)
//			throws IOException {
//		URLConnection connection;
//		String proxyHost = null;
//		Integer proxyPort = null;
//		if (proxyHost != null && proxyPort != null) {
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
//					proxyHost, proxyPort));
//			connection = localURL.openConnection(proxy);
//		} else {
//			connection = localURL.openConnection();
//		}
//		return connection;
//	}


}
