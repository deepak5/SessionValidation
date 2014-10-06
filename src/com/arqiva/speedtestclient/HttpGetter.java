package com.arqiva.speedtestclient;

/** HttpGetter class is responsible to perform the Get and Post functions with the server
 * This class contains two methods namely doGet and postform to take care of the GET and POST 
 * communication with the server 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpGetter {
	
	private String cookie;
	
	
	public HttpGetter(){
		
	}
	
	// doGet is responsible to make an http request to the server
	// url and referrer are the two string paramaters being passed to the method for making a get request
	
	public String doGet(String url,String referrer)
	{

		int result=0;
		String res="";
	      System.out.println("GET start --------------------------------------------------------------------------"+url);
		
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "text/html, application/xhtml+xml, */*");
            httpGet.setHeader("Accept-Language", "en-GB");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; MDDRJS)");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate");
            httpGet.setHeader("DNT", "1");
            httpGet.setHeader("Connection", "Keep-Alive");
            
            if (cookie!=null) httpGet.setHeader("Cookie", cookie);
            if (referrer!=null) httpGet.setHeader("Referer", referrer);
            
            // executing the get request to the server and holds a reference for the response
            
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST either fully consume the response content  or abort request
            // execution by calling CloseableHttpResponse#close().

            //
            try {
    			System.out.println("Get Response Code : " + response1.getStatusLine().getStatusCode());
                result=response1.getStatusLine().getStatusCode();
                Header tem=response1.getFirstHeader("Set-Cookie");
                        
                if (tem!=null) cookie=tem.getValue().split(";")[0];
                
                System.out.println("cookie = "+cookie);
                HttpEntity entity = response1.getEntity();
    	    // 	System.out.println("Http Get entity "+EntityUtils.toString(entity));
             //   referrer=url;  
              
                res= EntityUtils.toString(entity); 
            } 
            
            finally {
                response1.close();
            }
            
            httpclient.close();
            
            
            
            System.out.println("GET size  --------------------------------------------------------------------------"+res.length());
            
            return res;
        }
		catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}     
           
        System.out.println("GET End --------------------------------------------------------------------------");
        
        return null;
	}
	
	// Upload Data to the server 
	
	public String postForm(String url,String looking_for,String location, int size)
	{
		int results=0;
		
		
		int posa=url.indexOf("http://");
		int posb=url.indexOf("/",posa+7);
		
		String host = url.substring(posa+7,posb);
		 
	    Socket socket;
		try {
			socket = new Socket(host, 8080);
			
		    String path = url.substring(posb,url.length());
		    
		    System.out.println("testne "+path);
		    
		    BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
		    wr.write("POST " + path + " HTTP/1.0\r\n");
		    wr.write("Content-Length: " + size+ "\r\n");
		    wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
		    wr.write("\r\n");

		    int i=0;
		    
		    while(i<size)
		    {
		    	wr.write("A");
		    	i++;
		    }
		    wr.flush();

		    BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		      System.out.println(line);
		    }
		    wr.close();
		    rd.close();
		    socket.close();
				
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	    return null;
		
	}
	
}