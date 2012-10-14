/*
ID: lazydom1
LANG: JAVA
TASK: SaxProgramHandler.java
Created on: 2012-10-13-下午12:41:35
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.XMLparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;


public class PullProgramHandler {
	
	//解析用到的tag
	private String _entryname = "program";
	private String _entryID = "id";
	private String _entrytile = "title";
	private String _entrysubtitle = "subtitle";
	private String _entrypushtime = "pushtime";
	private String _entryhow_long = "how_long";
	private String _entrycomment = "comment";
	private String _entryimg = "img";
	private String _entrysource = "source";
	private String _author = "author";
	private String _filesize = "filesize";
	//用于保存xml解析获取的结果 
	private ArrayList<ProgramEntry> programEntryList = null;
	private ProgramEntry programEntry = null;
	private Boolean startEntryElementFlag = false;
	
    //解析xml数据 
	public ArrayList<ProgramEntry> parse(InputStream inStream)
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
            			programEntryList = new ArrayList<ProgramEntry>();
            		}break;
            		case XmlPullParser.START_TAG:
            		{
            			localName = xmlPullParser.getName();
            			if(localName.equalsIgnoreCase(_entryname))
            			{
            				programEntry = new ProgramEntry();
            				startEntryElementFlag = true;
            			}else
            			if(startEntryElementFlag)
            			{
            				String currentData = null;
            				if(localName.equalsIgnoreCase(_author))
            				{
            					currentData = xmlPullParser.nextText();
            					programEntry.setAuthor(currentData.trim());
            				}else
            				if(localName.equalsIgnoreCase(_entryID))
            				{
            					currentData = xmlPullParser.nextText();
            					int ID = Integer.parseInt(currentData.trim());
            					programEntry.setID(ID);
            				}else
            				if(localName.equalsIgnoreCase(_entrycomment))
            				{
            					currentData = xmlPullParser.nextText();
            					programEntry.setComment(currentData.trim());
            				}else
            				if(localName.equalsIgnoreCase(_entryhow_long))
            				{
            					currentData = xmlPullParser.nextText();
            					long how_long = Long.parseLong(currentData.trim());
            					programEntry.setHow_long(how_long);
            				}else
            				if(localName.equalsIgnoreCase(_entryimg))
            				{
            					currentData = xmlPullParser.nextText();
            					programEntry.setImg(currentData.trim());
            				}else
            				if(localName.equalsIgnoreCase(_entrypushtime))
            				{
            					currentData = xmlPullParser.nextText();
            					long pushtime = Long.parseLong(currentData.trim());
            					programEntry.setPushtime(pushtime);
            				}else
            				if(localName.equalsIgnoreCase(_entrysubtitle))
            				{
            					currentData = xmlPullParser.nextText();
            					programEntry.setSubtitle(currentData.trim());
            				}else
            				if(localName.equalsIgnoreCase(_entrytile))
            				{
            					currentData = xmlPullParser.nextText();
            					programEntry.setTitle(currentData.trim());
            				}else
            				if(localName.equalsIgnoreCase(_entryimg))
            				{
            					currentData = xmlPullParser.nextText();
            					programEntry.setImg(currentData.trim());
            				}else
            				if(localName.equalsIgnoreCase(_entrysource))
            				{
            					currentData = xmlPullParser.nextText();
            					programEntry.setSource(currentData.trim());
            				}else
            				if(localName.equalsIgnoreCase(_filesize))
            				{
            					currentData = xmlPullParser.nextText();
            					programEntry.setFilesize(Long.parseLong(currentData.trim()));
            				}
            			}
            		};break;
            		case XmlPullParser.END_TAG:
            		{
            			localName = xmlPullParser.getName();
            			if((localName.equalsIgnoreCase(_entryname))&&startEntryElementFlag)
            			{
            				programEntryList.add(programEntry);
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
		return programEntryList;
	}
}

