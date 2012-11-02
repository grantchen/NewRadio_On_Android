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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;


public class PullProgramHandler {
	
	//解析用到的tag
	private String _start = "dict";
	
	//用于保存xml解析获取的结果 
	private ArrayList<ProgramEntry> programEntryList = null;
	private ProgramEntry programEntry = null;
	private Boolean startEntryElementFlag = false;
	private Boolean startProgram = false;
	private String cmd = "@";
	private int DJ = 0;
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
            			if(localName.equalsIgnoreCase("array"))
            			{
            				startProgram = true;
            			}else
            			if(startProgram&&localName.equalsIgnoreCase(_start))
            			{
            				programEntry = new ProgramEntry();
            				startEntryElementFlag = true;
            			}else
            			if(startEntryElementFlag)
            			{
            				if(cmd.equals("@"))
            					cmd = xmlPullParser.nextText();
            				else
            				{
            					String str = xmlPullParser.nextText();
            					
            					if(cmd.equals("ID"))
            					{
            						int ID = Integer.parseInt(str);
            						programEntry.setID(ID);
            					}else
            					if(cmd.equals("Name"))
            					{
            						programEntry.setName(str);
            					}else
            					if(cmd.equals("Title"))
            					{
            						programEntry.setTitle(str);
            					}else
            					if(cmd.endsWith("ReleaseDate"))
            					{
            						programEntry.setPushtime(str);
            					}else
            					if(cmd.equals("Length"))
            					{
            						long time = Long.parseLong(str);
            						programEntry.setHow_long(time);
            					}else
            					if(cmd.equals("Description"))
            					{
            						programEntry.setComment(str);
            					}else
            					if(cmd.equals("ProgramURL"))
            					{
            						programEntry.setSource(str);
            					}else
            					if(cmd.equals("ImageURL"))
            					{
            						programEntry.setImg(str);
            					}else
            					if(cmd.equals("DJ"))
            					{
            						int dj = Integer.parseInt(str);
            						programEntry.setDJ(dj);
            					}else
            					if(cmd.equals("Album"))
            					{
            						int t = Integer.parseInt(str);
            						programEntry.setAlbum(t);
            					}
            					
            					cmd = "@"; 
            				}
            				
            			}
            			
            		};break;
            		case XmlPullParser.END_TAG:
            		{
            			localName = xmlPullParser.getName();
            			if((localName.equalsIgnoreCase("dict"))&&startEntryElementFlag)
            			{
            				if(programEntry.getDJ()==DJ)
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



	public PullProgramHandler(int dJ) {
		super();
		DJ = dJ;
	}
}

