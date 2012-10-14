/*
ID: lazydom1
LANG: JAVA
TASK: ProgramView.java
Created on: 2012-10-14-下午2:22:01
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.newradio;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.Pisces.IO.Dao;
import org.Pisces.IO.DirHelper;
import org.Pisces.IO.Downloader;
import org.Pisces.IO.LoadInfo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProgramView extends Activity {
	
	
	private static final int INIT = 0;
	private static final int PAUSE = 2;
	private static final int PLAYING = 1;
	private static int status = INIT;

	/*
	 * 点击事件监听器
	 */
	private OnClickListener down;//下载
	private OnClickListener play;//播放
	private OnClickListener pause;//暂停下载
	private OnClickListener pauseOnPlay;//暂停播放
	private OnClickListener continuePlay;//继续播放
	private OnClickListener stop;//停止播放
	private OnClickListener delete;//删除文件
	
	private Downloader downsound;//下载器
	
	private static MediaPlayer mp;
	
	boolean fileIsFinished = false;
	private Button but1 = null;
	private Button but2 = null;
	private Bundle bundle = null;
	private File sound = null;
	private File img = null;
	private String source = null;
	private String sourceImg = null; 
	final static String basePath = android.os.Environment.getExternalStorageDirectory()+"/.NewRadio/"; 
	final static String baseUrl = "http://bchine.com/pisces/newradio/";
	
	public void onCreate(Bundle savedInstanceState) {
		
		try{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.programview);
	     
	        bundle = this.getIntent().getExtras();
	        
	        source = bundle.getString("source");
	        sourceImg = bundle.getString("img");
	        
	        TextView subtitle = (TextView) findViewById(R.id.textView1);
	        subtitle.setText(bundle.getString("subtitle"));
	        
	        TextView pushtime = (TextView) findViewById(R.id.textView2);
	        Date date = new Date(bundle.getLong("pushtime"));
	        
	        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
	        
	        pushtime.setText("发布时间: "+dateF.format(date));
	        
	        TextView how_long = (TextView) findViewById(R.id.textView3);
	        long time = bundle.getLong("how_long");
	        long hour = time/3600;time%=3600;
	        long min = time/60;time%=60;
	        long sec = time;
	        String res = "";
	        if(hour>0) res = hour+"时";
	        if(min>0) res += min+"分";
	        if(sec>0) res += sec+"秒";
	        how_long.setText(res);
	        
	        TextView comment = (TextView) findViewById(R.id.textView4);
	        comment.setText(bundle.getString("comment"));
	        
	        sound = new File(basePath+source);
	        img = new File(basePath+sourceImg);
	        downsound = new Downloader(baseUrl+source,basePath+source , 4,this,mHandler );
	        //判断文件是否完整
	        if(sound.exists()&&sound.length()==bundle.getLong("filesize")&&downsound.isFirst(baseUrl+source))
	        {
	        	fileIsFinished = true;
	        }
	        
	        but1 = (Button) findViewById(R.id.button1);
	        but2 = (Button) findViewById(R.id.button2);
	        
	        play = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					play(v);
					
				}
			};
			pauseOnPlay = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					pauseOnPlay(v);
				}
			};
			continuePlay = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mp.start();
					but1.setText("暂停");
			    	but1.setOnClickListener(pauseOnPlay);

				}
			};
			down = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					down(v);
				}
			};
			pause = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					pause(v);
				}
			};
			stop = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					stop(v);
				}
			};
			delete = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					deleteProgramFiles(v);
				}
			};
	        
			//文件是否已经下载完
	        if(fileIsFinished)
	        {
	        	but1.setText("播放");
	        	but1.setOnClickListener(play);
	        }else
	        {
	        	but1.setText("下载");
	        	but1.setOnClickListener(down);
	        }
	        
	        //如果文件正在播放，那么应该是显示 暂停和停止按钮
	        if(mp!=null&&mp.isPlaying())
	        {
	        	but1.setText("暂停");
		    	but1.setOnClickListener(pauseOnPlay);
		    	
		    	but2.setText("停止");
		    	but2.setOnClickListener(stop);
	        }
	      
	        
		}catch(Exception e)
		{
			
		}finally{
			//关闭数据库
			Dao.closeDb();
			
		}
    }
	
	/*
	 * 返回上一层
	 */
	public void backtoProgramList(View v)
	{
		this.finish();
	}
	/*
	 * 删除文件
	 */
	public void deleteProgramFiles(View v)
	{
		if(downsound!=null&&(downsound.isdownloading()||downsound.isPause()))
		{
			Toast.makeText(this, "节目正在下载，不能删除！", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!sound.exists())
		{
			Toast.makeText(this, "节目还没有下载！", Toast.LENGTH_SHORT).show();
		}else
		{
			sound.delete();
			if(img.exists())
				img.delete();
			but1.setText("下载");
        	but1.setOnClickListener(down);
			Toast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show();
		}
	}
	/*
	 * 
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				String url = (String) msg.obj;
				int length = msg.arg1;
				ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar1);
				if (bar != null) {
					// 设置进度条按读取的length长度更新
					bar.incrementProgressBy(length);
					if (bar.getProgress() == bar.getMax()) {
						Toast.makeText(ProgramView.this, "下载完成！", Toast.LENGTH_SHORT).show();
						
						bar.incrementProgressBy(-bar.getMax());
						downsound.delete(url);
						downsound.reset();
						but1.setText("播放");
			        	but1.setOnClickListener(play);
					}
				}
			}
		}
	};
	/**
	 * 显示进度条
	 */
	private void showProgress(LoadInfo loadInfo, String url, View v) {
		ProgressBar bar = null;
		if (bar == null) {
			bar = (ProgressBar) findViewById(R.id.progressBar1);
			bar.setMax(loadInfo.getFileSize());
			bar.setProgress(loadInfo.getComplete());
			//System.out.println(loadInfo.getFileSize()+"--"+loadInfo.getComplete());
			//LinearLayout.LayoutParams params = new LayoutParams(
			//		LayoutParams.FILL_PARENT, 5);
			//((LinearLayout) ((LinearLayout) v.getParent()).getParent())
			//		.addView(bar, params);
		}
	}
	
	private void down(View v)
	{
		//先判断文件夹是否存在，不存在则建立
		if(!DirHelper.isFileExist(".NewRadio/"+bundle.getString("author")))
		{
			DirHelper.creatSDDir(".NewRadio/"+bundle.getString("author"));
		}
		
		but1.setText("暂停");
    	but1.setOnClickListener(pause);
		
		
		if (downsound.isdownloading())
			return;
		// 得到下载信息类的个数组成集合
		LoadInfo loadInfo = downsound.getDownloaderInfors();
		// 显示进度条
		showProgress(loadInfo, baseUrl+source, v);
		// 调用方法开始下载
		downsound.download();
	}
	private void pause(View V)
	{
		downsound.pause();
		but1.setText("继续");
    	but1.setOnClickListener(down);
	}
	private void pauseOnPlay(View v)
	{
		but1.setText("播放");
    	but1.setOnClickListener(continuePlay);
    	mp.pause();
    	status = PAUSE;
    	
	}
	private void stop(View v)
	{
		mp.stop();
		but1.setText("播放");
		but1.setOnClickListener(play);
		
		but2.setText("删除");
		but2.setOnClickListener(delete);
	}
	
	private void play(View v)
	{
		but1.setText("暂停");
    	but1.setOnClickListener(pauseOnPlay);
    	but2.setText("停止");
    	but2.setOnClickListener(stop);
    	
    	
    	mp = new MediaPlayer();
    	
    	try {
    		
			mp.setDataSource(basePath+source);
			mp.prepare();
			mp.start(); 
			status = PLAYING;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
}

