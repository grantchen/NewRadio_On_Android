/*
ID: lazydom1
LANG: JAVA
TASK: AuthorEntry.java
Created on: 2012-10-15-下午6:50:31
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.XMLparser;

public class AuthorEntry {
	
	
	private static String Album[] = new String[25];
	private static String weibo[] = new String[25];
	
	
	private String Name = null;
	private int ID = 0;
	private String ThumbImageUrl = null;
	private String BigImageUrl = null;
	private String Description = null;
	private String WeiboName = null;
	private String WeiboURL = null;
	private String Email = null;
	
	
	public AuthorEntry()
	{
		Album[0] = "出错了！";
		Album[4] = "YY CLUB";
		Album[5] = "邝楠音乐随笔";
		Album[19] = "奇妙的南瓜";
		Album[20] = "陈丹的有声音乐杂志";
		Album[21] = "隔墙有耳";
		Album[22] = "李青的音乐枕头";
		Album[23] = "New Radio 集合";
		
		weibo[0] = "出错了！";
		weibo[4] = "@杨樾杨樾";
		weibo[5] = "@DJ邝楠";
		weibo[19] = "@DJ董鹏";
		weibo[20] = "@DJ陈丹";
		weibo[21] = "@曾克";
		weibo[22] = "@古典的河流";
		weibo[23] = "@NewRadio播客平台";
		
	}
	

	public String getWeiboURL() {
		return WeiboURL;
	}


	public void setWeiboURL(String weiboURL) {
		WeiboURL = weiboURL;
	}



	public static String getAlbum(int i) {
		return Album[i];
	}



	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getThumbImageUrl() {
		return ThumbImageUrl;
	}

	public void setThumbImageUrl(String thumbImageUrl) {
		ThumbImageUrl = thumbImageUrl;
	}

	public String getBigImageUrl() {
		return BigImageUrl;
	}

	public void setBigImageUrl(String bigImageUrl) {
		BigImageUrl = bigImageUrl;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getWeiboName() {
		return WeiboName;
	}
	public static String getWeiboName(int index) {
		return weibo[index];
	}

	public void setWeiboName(String weiboName) {
		WeiboName = weiboName;
		weibo[this.ID] = weiboName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	public static void setInit()
	{
		Album[0] = "出错了！";
		Album[4] = "YY CLUB";
		Album[5] = "邝楠音乐随笔";
		Album[19] = "奇妙的南瓜";
		Album[20] = "陈丹的有声音乐杂志";
		Album[21] = "隔墙有耳";
		Album[22] = "李青的音乐枕头";
		Album[23] = "New Radio 集合";
		
		weibo[0] = "出错了！";
		weibo[4] = "@杨樾杨樾";
		weibo[5] = "@DJ邝楠";
		weibo[19] = "@DJ董鹏";
		weibo[20] = "@DJ陈丹";
		weibo[21] = "@曾克";
		weibo[22] = "@古典的河流";
		weibo[23] = "@NewRadio播客平台";
	}

	public String toString()
	{
		return Album[ID];
	}
	
}

