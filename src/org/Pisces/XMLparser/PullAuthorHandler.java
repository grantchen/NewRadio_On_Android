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
		private String _entryname = "author";
		private String _Cname = "Cname";
		private String _Ename = "Ename";
		private String _program_name = "program_name";
		private String _summary = "summary";
		private String _tot_program = "tot_program";
		
		//用于保存xml解析获取的结果 
		private ArrayList<AuthorEntry> authorEntryList = null;
		private AuthorEntry authorEntry = null;
		private Boolean startEntryElementFlag = false;
		
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
	            			if(localName.equalsIgnoreCase(_entryname))
	            			{
	            				authorEntry = new AuthorEntry();
	            				startEntryElementFlag = true;
	            			}else
	            			if(startEntryElementFlag)
	            			{
	            				String currentData = null;
	            				if(localName.equalsIgnoreCase(_Cname))
	            				{
	            					currentData = xmlPullParser.nextText();
	            					authorEntry.setCname(currentData.trim());
	            				}else
	            				if(localName.equalsIgnoreCase(_Ename))
	            				{
	            					currentData = xmlPullParser.nextText();
	            					authorEntry.setEname(currentData.trim());
	            				}else
	            				if(localName.equalsIgnoreCase(_program_name))
	            				{
	            					currentData = xmlPullParser.nextText();
	            					authorEntry.setProgram_name(currentData.trim());
	            				}else
	            				if(localName.equalsIgnoreCase(_summary))
	            				{
	            					currentData = xmlPullParser.nextText();
	            					authorEntry.setSummary(currentData.trim());
	            				}else
	            				if(localName.equalsIgnoreCase(_tot_program))
	            				{
	            					currentData = xmlPullParser.nextText();
	            					int tot = Integer.parseInt(currentData);
	            					authorEntry.setTot_program(tot);
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

