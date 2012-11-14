/*
ID: lazydom1
LANG: JAVA
TASK: DownLoaderInfo.java
Created on: 2012-11-13-上午10:30:29
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.IO;

public class DownLoadersInfo {

	public String url;
	public Downloader downloader;
	public String source = null;
	public String img = null;
	public String title = null;
	public String comment = null;
	public String pushtime = null;
	public int ID;
	public int DJ;
	public long how_long;
	public DownLoadersInfo(String url,Downloader d,int ID,String source,String img,long how_long,String title,int DJ,String comment,String pushtime)
	{
		this.url = url;
		this.downloader = d;
		this.ID = ID;
		this.source = source;
		this.img = img;
		this.how_long = how_long;
		this.title = title;
		this.DJ = DJ;
		this.comment = comment;
		this.pushtime = pushtime;
	}
	
	@Override
	public String toString()
	{
		return this.title;
	}
}

