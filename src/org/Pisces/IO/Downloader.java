/*
ID: lazydom1
LANG: JAVA
TASK: Downloader.java
Created on: 2012-10-18-下午3:27:11
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.IO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

	 
public class Downloader  {
	
	
	//下载状态
	private final int STOP = -1;
	private final int DOWN = 1;
	private int state;
	
	
	//定义的一些常量变量，看名字就知道什么意思了
	private static final int BUFFER_SIZE = 1024;
	private long fileSize;
	private int downloadedSize;
	private String urlStr;
	private String savePath;
	private int downloadPercent = 0;
	private long startTime,curTime;
	private	boolean completed = false;
	
	private Handler handler;
	
	//用URL,保存路径,保存名称来构造。
	public Downloader(String URL, String savePath,Handler handler) {
		this.urlStr = URL;
		this.savePath = savePath;
		this.handler = handler;
	}
	 
		private void startDown() {
			
			Thread t = new Thread(new Runnable() {
			
				@Override
				public void run() {
					// TODO Auto-generated method stub
					BufferedInputStream bis = null;
					byte[] buf = new byte[BUFFER_SIZE];
				try {
					File file = new File(savePath);
					if(!file.exists())
						file.createNewFile();
					
					RandomAccessFile f = new RandomAccessFile(file, "rw");
					URL url = new URL(urlStr);
					
					//Log.v("DEBUG","new URL");
					
					URLConnection con = url.openConnection();
					Log.v("DEBUG", "openconnection");
					
					long length = con.getContentLength();
					long hasdown = file.length();
					
					con = url.openConnection();
					con.setAllowUserInteraction(true);
					
					if(length==hasdown)
					{
						completed = true;
					}else
					{
						con.setRequestProperty("Range", "bytes=" + hasdown + "-" + length);
						Log.v("download from","bytes="+ ("bytes="
								+ (hasdown + "-" + length)));
						
						f.seek(hasdown);
						
						bis = new BufferedInputStream(con.getInputStream());
						while (hasdown<length) {
						
							if(state==STOP) return;
							int bit = bis.read(buf, 0, BUFFER_SIZE);
							if(bit==-1)	break;
							
							f.write(buf, 0, bit);
							
							hasdown+=bit;
							
							downloadPercent =  (int) ((hasdown*100)/length);
							//Log.v("downing",hasdown+"");
							//Log.v("downing",downloadPercent+"");
							
							Message msg = Message.obtain();
							msg.what = 0;
							msg.arg1 = downloadPercent;
							
							handler.sendMessage(msg);
							
							if(hasdown>=length)
								break;
						}
						
						completed = true;
						bis.close();
						f.close();
						
						Message msg = Message.obtain();
						msg.what = 1;
						handler.sendMessage(msg);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
				}
			});
			t.start();
			
		}
	       //获取下载百分比
		public int getDownloadPercent(){
			return this.downloadPercent;
		}
	   
		
	    //下载完成的标志
		public boolean isCompleted(){
			return this.completed;
		}
		public boolean isDownloading()
		{
			return state == DOWN;
		}
		
		public void stop()
		{
			this.state = STOP;
		}
		public void start() {
			state = DOWN;
			startDown();
			Log.v("下载",urlStr);
		}
		
		public long getFilesize()
		{
			fileSize = 0;
			URL url;
			try {
				url = new URL(urlStr);
				URLConnection con = url.openConnection();
				fileSize = con.getContentLength();
			} catch (MalformedURLException e) {
				
			} catch (IOException e)
			{
			}
			return fileSize;
		}
}


