package com.arqiva.speedtestclient;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringEscapeUtils;

/** The Main class responsible to create an object for HttpGetter and completes the end to end communication 
 * between the client and server
 * Creates an getit object
 * Performs the Upload and Download functionalities with the server
 * @author deepakselvaraj
 *
 */

public class Main {

	public static void main(String[] args) {
		
	//creates an object for the HttpGetter class 
		
		HttpGetter getit= new HttpGetter();
	
	//call doGet method from HttpGetter class to perform the download operation from the server
		
		System.out.println("Test 1 Start");		
		getit.doGet("http://"+args[0]+":8080/SpeedTest/speed?size="+args[1],"");
		System.out.println("Test 1 Complete");

		int len = Integer.parseInt(args[2]);
	
	//call postForm method from HttpGetter class to perform the upload operation within the server	
		
		System.out.println("Test 2 Start");	
		getit.postForm("http://"+args[0]+"/SpeedTest/speed?size=1","","",len);
		System.out.println("Test 2 Complete");	

	}

}
