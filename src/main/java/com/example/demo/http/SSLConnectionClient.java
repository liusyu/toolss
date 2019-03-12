/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.demo.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.oltu.oauth2.client.HttpClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.client.response.OAuthClientResponseFactory;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;


/**
 * Implementation of the OAuth HttpClient using URL Connection
 *
 *
 *
 *
 */
public class SSLConnectionClient implements HttpClient {

    public SSLConnectionClient() {
    }

    public <T extends OAuthClientResponse> T execute(OAuthClientRequest request, Map<String, String> headers,
                                                     String requestMethod, Class<T> responseClass)
        throws OAuthSystemException, OAuthProblemException {

        String responseBody = null;
        URLConnection c = null;
        int responseCode = 0;
        try {
            URL url = new URL(request.getLocationUri());

            c = url.openConnection();
            responseCode = -1;
            if (c instanceof HttpsURLConnection) {
            	HttpsURLConnection httpsURLConnection = (HttpsURLConnection)c;
            	
            	SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
                httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                httpsURLConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());

                if (headers != null && !headers.isEmpty()) {
                    for (Map.Entry<String, String> header : headers.entrySet()) {
                        httpsURLConnection.addRequestProperty(header.getKey(), header.getValue());
                    }
                }

                if (!OAuthUtils.isEmpty(requestMethod)) {
                    httpsURLConnection.setRequestMethod(requestMethod);
                    if (requestMethod.equals(OAuth.HttpMethod.POST)) {
                        httpsURLConnection.setDoOutput(true);
                        OutputStream ost = httpsURLConnection.getOutputStream();
                        PrintWriter pw = new PrintWriter(ost);
                        pw.print(request.getBody());
                        pw.flush();
                        pw.close();
                    }
                } else {
                    httpsURLConnection.setRequestMethod(OAuth.HttpMethod.GET);
                }

                httpsURLConnection.connect();

                InputStream inputStream;
                responseCode = httpsURLConnection.getResponseCode();
                if (responseCode == 400 || responseCode == 401) {
                    inputStream = httpsURLConnection.getErrorStream();
                } else {
                    inputStream = httpsURLConnection.getInputStream();
                }

                responseBody = OAuthUtils.saveStreamAsString(inputStream);
            }
        } catch (Exception e) {
            throw new OAuthSystemException(e);
        }

        return OAuthClientResponseFactory
            .createCustomResponse(responseBody, c.getContentType(), responseCode, responseClass);
    }

    @Override
    public void shutdown() {
        // Nothing to do here
    }
    
    static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
   
    static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }      

}
