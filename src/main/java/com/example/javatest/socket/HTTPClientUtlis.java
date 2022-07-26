//package com.example.javatest.socket;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import javax.net.ssl.*;
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.security.SecureRandom;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//
//public class HTTPClientUtlis {
//    private static final Log log = LogFactory.getLog(HTTPClientUtlis.class);
//    private static final int CONNECT_TIMEOUT = 5000;
//    private static final int READ_TIMEOUT = 5000;
//
//    public static String sendPost(String urlPath, String paramString) {
//        return urlPath.startsWith("https://") ? sendPostWithSSL(urlPath, paramString) : sendPostWithoutSSL(urlPath, paramString);
//    }
//
//    private static String sendPostWithoutSSL(String urlPath, String paramString) {
//        log.info("### HTTP POST request ==> " + urlPath + "?" + paramString);
//
//        try {
//            URL url = new URL(urlPath);
//            byte[] data = paramString.getBytes();
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setDoOutput(true);
//            conn.setUseCaches(false);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Content-Length", Integer.toString(data.length));
//            conn.setConnectTimeout(CONNECT_TIMEOUT);
//            conn.setReadTimeout(READ_TIMEOUT);
//            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//            out.write(data);
//            out.flush();
//            out.close();
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String inputLine = "";
//            StringBuffer response = new StringBuffer();
//
//            while((inputLine = br.readLine()) != null) {
//                response.append(inputLine);
//            }
//
//            br.close();
//            log.info("### HTTP POST response ==> " + StringUtils.abbreviate(response.toString(), 200));
//            return response.toString();
//        } catch (UnknownHostException var9) {
//            log.error("HTTP POST 통신 중 오류발생 !! ", var9);
//            return "";
//        } catch (IOException var10) {
//            log.error("HTTP POST 통신 중 오류발생 !! ", var10);
//            return "";
//        }
//    }
//
//    private static String sendPostWithSSL(String urlPath, String paramString) {
//        log.info("### HTTPS POST request ==> " + urlPath + "?" + paramString);
//        StringBuffer sb = new StringBuffer();
//        BufferedReader br = null;
//
//        try {
//            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//
//                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//                }
//
//                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//                }
//            }};
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init((KeyManager[])null, trustAllCerts, new SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            URL url = new URL(urlPath);
//            byte[] data = paramString.getBytes();
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setDoOutput(true);
//            conn.setUseCaches(false);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Content-Length", Integer.toString(data.length));
//            conn.setConnectTimeout(CONNECT_TIMEOUT);
//            conn.setReadTimeout(READ_TIMEOUT);
//            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//            out.write(data);
//            out.flush();
//            out.close();
//            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = "";
//
//            while((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            log.info("### HTTPS POST response ==> " + StringUtils.abbreviate(sb.toString(), 300));
//            conn.disconnect();
//        } catch (Exception var19) {
//            log.error("HTTPS POST 통신 중 오류발생 !! ", var19);
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException var18) {
//                }
//            }
//
//        }
//
//        return sb.toString();
//    }
//}
