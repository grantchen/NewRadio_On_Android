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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
	 
public class Downloader  {
	

	//下载状态
	private final int STOP = -1;
	private final int DOWN = 1;
	private int state;
	
	private final static int DELY = 5000;
	private final static int DELY2 = 2000;
	
	//定义的一些常量变量，看名字就知道什么意思了
	private static final int BUFFER_SIZE = 32;
	private String urlStr;
	private String savePath;
	private int downloadPercent = 0;
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
					File file;
					HttpURLConnection con = null;
					RandomAccessFile f = null;
					URL url;
					byte[] buf = new byte[BUFFER_SIZE];
				try {
					file = new File(savePath);
					if(!file.exists())
						file.createNewFile();
					
					f = new RandomAccessFile(file, "rw");
					url = new URL(urlStr);
					
					//Log.v("DEBUG","new URL");
					
					con = (HttpURLConnection) url.openConnection();
					con.setConnectTimeout(DELY);
					con.setReadTimeout(DELY);
					Log.v("DOWN", "getLength");
					
					long length = getFilesize(urlStr, 0,handler);
					long hasdown = file.length();
					
					con = (HttpURLConnection) url.openConnection();
					con.setAllowUserInteraction(true);
					con.setConnectTimeout(DELY);
					con.setReadTimeout(DELY);
					
					
					if(length==hasdown)
					{
						completed = true;
					}else
					{
						if(length==-1)
						{
							Log.e("DOWN","FUCK -1");
							throw new SocketTimeoutException();
						}
						Log.v("DOWN", "startDown!");
						con.setRequestProperty("Range", "bytes=" + hasdown + "-" + length);
						Log.v("download from","bytes="+ ("bytes="
								+ (hasdown + "-" + length)));
						
						f.seek(hasdown);
						
						con.connect();
						
						bis = new BufferedInputStream(con.getInputStream());
						while (hasdown<length) {
						
							if(state==STOP)
							{
								PUB.remove(urlStr);
								return;
							}
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
						Log.v("DOWN","下载完成");
						
						bis.close();
						
						Message msg = Message.obtain();
						msg.what = 1;
						handler.sendMessage(msg);
						state = STOP;
						PUB.remove(urlStr);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
					Message msg = Message.obtain();
					msg.what = 2;
					handler.sendMessage(msg);
					state = STOP;
					Log.w("DOWN", "网络连接失败");
					e.printStackTrace();
				}finally{
					try {
						state = STOP;
						if(bis!=null)
							bis.close();
						if(f!=null)
							f.close();
						if(con!=null)
							con.disconnect();
						PUB.remove(urlStr);
					} catch (IOException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					}
				}
						
				}
			});
			t.start();
			
		}
	    
		public int getDownloadPercent()
		{
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
		public String getUrl()
		{
			return this.urlStr;
		}
		public void setHandler(Handler h)
		{
			this.handler = h;
		}
		public Handler getHandler()
		{
			return this.handler;
		}
		
		//size比网络上文件大返回1，小返回-1，相等返回0，联网失败返回2
		public static long getFilesize(String urlStr,long size ,Handler h)
		{
			long fileSize = 0;
			URL url;
			try {
				url = new URL(urlStr);
				URLConnection con = url.openConnection();
				con.setConnectTimeout(DELY2);
				con.setReadTimeout(DELY2);
				fileSize = con.getContentLength();
				
				if(fileSize<=0) throw new IOException();
				
				Message msg = Message.obtain();
				if(size>fileSize)
					msg.what = 1;
				else
				if(size<fileSize)
					msg.what = -1;
				else
					msg.what=0;
				
				h.sendMessage(msg);
				
			} catch (IOException e)
			{
				Message msg = Message.obtain();
				msg.what = 2;
				h.sendMessage(msg);
				Log.w("DOWN", "网络连接失败");
			//	e.printStackTrace();
			}
			
			return fileSize;
			
		}
}


