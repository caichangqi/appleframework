package com.appleframework.core.net;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.exception.NestableRuntimeException;

import com.appleframework.core.io.IOUtility;
import com.appleframework.core.utils.StringUtility;

/**
 * @author Santhosh Kumar T
 */
public final class URLUtility {
   /**
    * Constructs <code>URI</code> from given string.
    * <p/>
    * The <code>URISyntaxException</code> is rethrown as <code>IllegalArgumentException</code>
    */
   public static URI toURI(String str) {
      try {
         return new URI(str);
      } catch (URISyntaxException ex) {
         throw new IllegalArgumentException(ex);
      }
   }

   /**
    * Constructs <code>URI</code> from given <code>URL</code>.
    * <p/>
    * The <code>URISyntaxException</code> is rethrown as <code>IllegalArgumentException</code>
    */
   public static URI toURI(URL url) {
      try {
         return url.toURI();
      } catch (URISyntaxException ex) {
         throw new IllegalArgumentException(ex);
      }
   }

   public static String toSystemID(URL url) {
      try {
         if ("file".equals(url.getProtocol())) {
            return new File(url.toURI()).getAbsolutePath();
         } else {
            return url.toString();
         }
      } catch (URISyntaxException ex) {
         throw new NestableRuntimeException(ex);
      }
   }

   /**
    * returns Query Parameters in specified uri as <code>Map</code>. key will be param name and
    * value wil be param value.
    * 
    * @param uri
    *           The string to be parsed into a URI
    * @param e
    *           if null, <code>UTF-8</code> will be used
    * @throws URISyntaxException
    *            in case of invalid uri
    * @throws UnsupportedEncodingException
    *            if named character encoding is not supported
    */
   public static Map<String, String> getQueryParams(String uri, String e)
            throws URISyntaxException, UnsupportedEncodingException {
      String encoding = e;
      if (encoding == null) {
         encoding = IOUtility.UTF_8.name();
      }
      String query = new URI(uri).getRawQuery();
      String params[] = Pattern.compile("&", Pattern.LITERAL).split(query);
      Map<String, String> map = new HashMap<String, String>(params.length);
      for (String param : params) {
         int equal = param.indexOf('=');
         String name = param.substring(0, equal);
         String value = param.substring(equal + 1);
         name = URLDecoder.decode(name, encoding);
         value = URLDecoder.decode(value, encoding);
         map.put(name, value);
      }
      return map;
   }

   public static String suggestFile(URI uri, String extension) {
      String path = uri.getPath();
      String tokens[] = StringUtility.getTokens(path, "/", true);
      String file = tokens[tokens.length - 1];
      int dot = file.indexOf('.');
      if (dot == -1) {
         return file + '.' + extension;
      } else {
         return file.substring(0, dot + 1) + extension;
      }
   }

   public static String suggestPrefix(Properties suggested, String uri) {
      String prefix = suggested.getProperty(uri);
      if (prefix != null) {
         return prefix;
      } else {
         try {
            URI u = new URI(uri);
            // suggest prefix from path
            String path = u.getPath();
            if (path != null) {
               StringTokenizer stok = new StringTokenizer(path, "/");
               while (stok.hasMoreTokens()) {
                  prefix = stok.nextToken();
               }
            }
            if (prefix != null) {
               return prefix;
            } else {
               // suggest prefix from host
               String host = u.getHost();
               if (host != null) {
                  StringTokenizer stok = new StringTokenizer(host, ".");
                  String curPrefix = null;
                  while (stok.hasMoreTokens()) {
                     prefix = curPrefix;
                     curPrefix = stok.nextToken();
                  }
               }
               if (prefix != null) {
                  return prefix;
               }
            }
         } catch (URISyntaxException ignore) {
            // xml spec doesn't guarantee that namespace uri should be valid uri
         }
      }
      return "ns";
   }

   private static volatile SSLContext sc;

   /**
    * Creates connection to the specified url. If the protocol is <code>https</code> the connection
    * created doesn't validate any certificates.
    * 
    * @param url
    *           url to which connection has to be created
    * @param proxy
    *           proxy to be used. can be null
    * @return <code>URLConnection</code>. the connection is not yet connected
    * @throws IOException
    *            if an I/O exception occurs
    */
   public static URLConnection createUnCertifiedConnection(URL url, Proxy proxy) throws IOException {
      if (sc == null) {
         synchronized (URLUtility.class) {
            if (sc == null) {
               TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                  @Override
                  public X509Certificate[] getAcceptedIssuers() {
                     return new X509Certificate[0];
                  }

                  @Override
                  public void checkClientTrusted(X509Certificate[] certs, String authType) {
                  }

                  @Override
                  public void checkServerTrusted(X509Certificate[] certs, String authType) {
                  }
               } };
               try {
                  SSLContext s = SSLContext.getInstance("SSL");
                  s.init(null, trustAllCerts, new SecureRandom());
                  URLUtility.sc = s;
               } catch (Exception ex) {
                  throw new NestableRuntimeException(ex);
               }
            }
         }
      }
      URLConnection con = proxy == null ? url.openConnection() : url.openConnection(proxy);
      if ("https".equals(url.getProtocol())) {
         HttpsURLConnection httpsCon = (HttpsURLConnection) con;
         httpsCon.setSSLSocketFactory(sc.getSocketFactory());
         httpsCon.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String urlHostName, SSLSession session) {
               return true;
            }
         });
      }
      return con;
   }

}
