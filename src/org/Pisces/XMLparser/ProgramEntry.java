/*
ID: lazydom1
LANG: JAVA
TASK: ProgramEntry.java
Created on: 2012-10-13-����12:14:27
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.XMLparser;


public class ProgramEntry {
	
	private int ID;
	private String title;
	private String name;
	private String pushtime;
	private long how_long;
	private String comment;
	private int DJ;
	private String img;
	private String source;
	private int Album;
	private String AlbumName;
	
	public String getAlbumName() {
		return AlbumName;
	}




	public void setAlbumName(String albumName) {
		AlbumName = albumName;
	}




	public ProgramEntry()
	{
		super();
		ID = 0;
		this.title = "";
		this.name = "";
		this.pushtime = "";
		this.how_long = 0;
		this.comment = "";
		this.DJ = 0;
		this.img = "";
		this.source = "";
	}
	
	
	

	public int getAlbum() {
		return Album;
	}




	public void setAlbum(int album) {
		Album = album;
	}




	public int getID() {
		return ID;
	}




	public void setID(int iD) {
		ID = iD;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getPushtime() {
		return pushtime;
	}




	public void setPushtime(String pushtime) {
		this.pushtime = pushtime;
	}




	public long getHow_long() {
		return how_long;
	}




	public void setHow_long(long how_long) {
		this.how_long = how_long;
	}




	public String getComment() {
		return comment;
	}




	public void setComment(String comment) {
		this.comment = comment;
	}




	public int getDJ() {
		return DJ;
	}




	public void setDJ(int dJ) {
		DJ = dJ;
	}




	public String getImg() {
		return img;
	}




	public void setImg(String img) {
		this.img = img;
	}




	public String getSource() {
		return source;
	}




	public void setSource(String source) {
		this.source = source;
	}




	public ProgramEntry(int iD, String title, String name, String pushtime,
			long how_long, String comment, int dJ, String img, String source) {
		super();
		ID = iD;
		this.title = title;
		this.name = name;
		this.pushtime = pushtime;
		this.how_long = how_long;
		this.comment = comment;
		DJ = dJ;
		this.img = img;
		this.source = source;
	}




	@Override
	public String toString()
	{
		return title+"";
	}
		
}

