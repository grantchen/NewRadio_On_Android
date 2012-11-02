/*
ID: lazydom1
LANG: JAVA
TASK: PullAuthorHandler.java
Created on: 2012-10-15-下午6:52:28
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.XMLparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class PullAuthorHandler {

	//解析用到的tag
		private String _entryname = "dict";
		
		//用于保存xml解析获取的结果 
		private ArrayList<AuthorEntry> authorEntryList = null;
		private AuthorEntry authorEntry = null;
		private boolean startEntryElementFlag = false;
		private boolean startAuthor = false; 
		
		private String cmd = "@";
		
		 //解析xml数据 
		public ArrayList<AuthorEntry> parse(InputStream inStream)
		{
			try {
				
				//使用Android提供的实用工具类android.util.Xml
				XmlPullParser xmlPullParser = Xml.newPullParser();
				xmlPullParser.setInput(inStream, "UTF-8");
				
				int eventType = xmlPullParser.getEventType();  
	            boolean isDone = false;
	            
	            while((eventType != XmlPullParser.END_DOCUMENT)&&(isDone != true))
	            {
	            	String localName = null;
	            	switch(eventType){
	            		case XmlPullParser.START_DOCUMENT:
	            		{
	            			authorEntryList = new ArrayList<AuthorEntry>();
	            		}break;
	            		case XmlPullParser.START_TAG:
	            		{
	            			localName = xmlPullParser.getName();
	            			if(localName.equalsIgnoreCase("array"))
	            			{
	            				startAuthor = true;
	            			}else
	            			if(startAuthor&&localName.equals(_entryname))
	            			{
	            				authorEntry = new AuthorEntry();
	            				startEntryElementFlag = true;
	            			}else
	            			if(startEntryElementFlag)
	            			{
	            				if(cmd.equals("@"))
	            				{
	            					cmd = xmlPullParser.nextText();
	            				}else
	            				{
	            					String str = xmlPullParser.nextText();
	            					
	            					if(cmd.equals("Name"))
	            					{
	            						authorEntry.setName(str);
	            					}else
	            					if(cmd.equals("ID"))
	            					{
	            						int id = Integer.parseInt(str);
	            						authorEntry.setID(id);
	            					}else
	            					if(cmd.equals("ThumbImageURL"))
	            					{
	            						authorEntry.setThumbImageUrl(str);
	            					}else
	            					if(cmd.equals("BigImageURL"))
	            					{
	            						authorEntry.setBigImageUrl(str);
	            					}else
	            					if(cmd.equals("Description"))
	            					{
	            						authorEntry.setDescription(str);
	            					}else
	            					if(cmd.equals("WeiboURL"))
	            					{
	            						authorEntry.setWeiboURL(str);
	            					}else
	            					if(cmd.equals("WeiboName"))
	            					{
	            						authorEntry.setWeiboName(str);
	            					}else
	            					if(cmd.equals("Email"))
	            					{
	            						authorEntry.setEmail(str);
	            					}
	            				
	            					cmd = "@";
	            				}
	            			}
	            		};break;
	            		case XmlPullParser.END_TAG:
	            		{
	            			localName = xmlPullParser.getName();
	            			if((localName.equalsIgnoreCase(_entryname))&&startEntryElementFlag)
	            			{
	            				authorEntryList.add(authorEntry);
	            				startEntryElementFlag = false;
	            			}
	            		};break;
	            		
	            		default: break;
	            	}
	            	eventType = xmlPullParser.next();
	            }
	            
				
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e2)
			{
				e2.printStackTrace();
			}
			return authorEntryList;
		}
}

