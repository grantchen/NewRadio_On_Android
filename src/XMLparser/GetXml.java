/*
ID: lazydom1
LANG: JAVA
TASK: GetXml.java
Created on: 2012-10-13-下午1:49:57
Author: lazydomino@163.com(pisces)
*/

package XMLparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetXml {

	
	    public static InputStream getXmlFromInternet(String url)
	    {
	    	//从网络上获取实时地震数据
	    	URL infoUrl = null;
	    	InputStream inStream = null;
	    	try {
				infoUrl = new URL(url);
				URLConnection connection = infoUrl.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection)connection;
				int responseCode = httpConnection.getResponseCode();
				if(responseCode == HttpURLConnection.HTTP_OK)
				{
					inStream = httpConnection.getInputStream();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return inStream;
	    }
}

