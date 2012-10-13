/*
ID: lazydom1
LANG: JAVA
TASK: ProgramEntry.java
Created on: 2012-10-13-����12:14:27
Author: lazydomino@163.com(pisces)
*/

package XMLparser;

import java.util.Comparator;
import java.util.Date;

public class ProgramEntry {
	
	private int ID;
	private Date date;
	private String title;
	private String subtitle;
	private long pushtime;
	private long how_long;
	private String comment;
	private String author;
	private String img;
	private String source;
	
	public ProgramEntry()
	{
		super();
		ID = 0;
		this.title = "";
		this.subtitle = "";
		this.pushtime = 0;
		this.how_long = 0;
		this.comment = "";
		this.author = "";
		this.img = "";
		this.source = "";
	}
	public ProgramEntry(int iD, Date date, String title, String subtitle,
			long pushtime, long how_long, String comment, String author,
			String img, String source) {
		super();
		ID = iD;
		this.date = date;
		this.title = title;
		this.subtitle = subtitle;
		this.pushtime = pushtime;
		this.how_long = how_long;
		this.comment = comment;
		this.author = author;
		this.img = img;
		this.source = source;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public long getPushtime() {
		return pushtime;
	}
	public void setPushtime(long pushtime) {
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	@Override
	public String toString()
	{
		return ID+" "+title+" "+subtitle;
	}
		
}

