/*
ID: lazydom1
LANG: JAVA
TASK: ProgramView.java
Created on: 2012-10-14-下午2:22:01
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.newradio;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.Pisces.IO.BASE;
import org.Pisces.IO.DirHelper;
import org.Pisces.IO.Downloader;
import org.Pisces.XMLparser.AuthorEntry;
import org.Pisces.newradio.R.id;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.ReportPolicy;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ProgramView extends Activity {
	
	
	private static final int INIT = 0;
	private static final int PAUSE = 2;
	private static final int PLAYING = 1;
	private static int status = INIT;
	private static boolean seekbarIsChange = false; 

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
	private OnClickListener deleteOndownloading;
	
	private File f;
	private File fileFlag;
	private long how_longP;
	private int ID;
	private Downloader downsound;//下载器
	
	private static MediaPlayer mp = null;
	private static String Playing = null;
	private DelayThread dThread = null;
	
	private String res; 
	private TextView how_long;
	private boolean fileIsFinished = false;
	private Button but1 = null;
	private Button but2 = null;
	private ImageView imgV = null;
	private ProgressBar bar;
	private SeekBar seekBar;
	private Bundle bundle = null;
	private File sound = null;
	private File img = null;
	private String source = null;
	private String sourceImg = null;
	
	public void onCreate(Bundle savedInstanceState) {
		
		try{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.programview2);
	        MobclickAgent.onError(this);
	        MobclickAgent.updateOnlineConfig(this);
	        MobclickAgent.setDefaultReportPolicy(this, ReportPolicy.BATCH_AT_LAUNCH);
	        
	        bundle = this.getIntent().getExtras();
	        
	        //先判断文件夹是否存在，不存在则建立
	        if(!DirHelper.isExist(".NewRadio/"+"programs"))
	        {
	        	DirHelper.createDir(".NewRadio/"+"programs");
	        }
	        if(!DirHelper.isExist(".NewRadio/"+"cache"))
	        {
	        	DirHelper.createDir(".NewRadio/"+"cache");
	        }
	        
	        ID = bundle.getInt("ID");
	        source = bundle.getString("source");
	        sourceImg = bundle.getString("img");
	        how_longP = bundle.getLong("how_long");
	        
	        TextView subtitle = (TextView) findViewById(R.id.textView1);
	        subtitle.setText(bundle.getString("title"));
	        
	        TextView pushtime = (TextView) findViewById(R.id.textView2);
	        
	        String date = "";
	        String pt = bundle.getString("pushtime");
	        date = pt.substring(0, 10);

	        
	        pushtime.setText("发布时间: "+date);
	        
	        how_long = (TextView) findViewById(R.id.textView3);
	        long time = bundle.getLong("how_long");
	        long hour = time/3600;time%=3600;
	        long min = time/60;time%=60;
	        long sec = time;
	        res = "";
	        if(hour>0) res = hour+"时";
	        if(min>0) res += min+"分";
	        if(sec>0) res += sec+"秒";
	        how_long.setText(res);
	        
	        TextView comment = (TextView) findViewById(R.id.textView4);
	        comment.setText(bundle.getString("comment"));
	        
	        sound = new File(BASE.basePath+"programs/"+ID+".mp3");
	        img = new File(BASE.basePath+"cache/"+ID+".png");
	        downsound = new Downloader(source,BASE.basePath+"programs/"+ID+".mp3",mHandler);
	        
	        seekBar = (SeekBar) findViewById(R.id.seekBar1);
	        bar = (ProgressBar) findViewById(R.id.progressBar1);
	        imgV = (ImageView) findViewById(R.id.imageView1);
	        seekBar.setVisibility(View.GONE);
			bar.setVisibility(View.VISIBLE);
	        
			//显示图片
			drawImg();
			
			
	        f = new File(BASE.basePath+"programs/"+ID+".mp3");
	        fileFlag = new File(BASE.basePath+"programs"+ID+".flag");
	        
	        but1 = (Button) findViewById(R.id.downLoad);
	        but2 = (Button) findViewById(R.id.deleteP);
	        
	        setListener();
	        
	        
	        //判断文件是否已经下载完整
	        fileIsFinished = false;
	        
	        if(f.exists()&&fileFlag.exists())
	        	fileIsFinished = true;
	        
			//文件是否已经下载完
	        if(fileIsFinished)
	        {
	        	but1.setText("播放");
	        	but1.setOnClickListener(play);
	        	changetoSeekBar();
	        	
	        	if(mp==null)
	        	{
	        		mp = new MediaPlayer();
	        		mp.setDataSource(BASE.basePath+"programs/"+ID+".mp3");
	        		mp.prepare();
	        	}
	        	
	        }else
	        {
	        	but1.setText("下载");
	        	but1.setOnClickListener(down);
	        	
	        	Log.v("Debug","set down listener");
	        	
	        	seekBar.setVisibility(View.GONE);
				bar.setVisibility(View.VISIBLE);
				bar.setProgress(downsound.getDownloadPercent());
				
	        }
	        
	        //如果文件正在播放，那么应该是显示 暂停和停止按钮
	        if(mp!=null)
	        {
		    	if(Playing!=null&&Playing.equalsIgnoreCase(BASE.basePath+"programs/"+ID+".mp3")&&mp.isPlaying())
		    	{
		    		changetoSeekBar();
		    		stopProgressUpdate();
		    		startProgressUpdate();
		    		but1.setText("暂停");
		    		but1.setOnClickListener(pauseOnPlay);
		    		but2.setText("停止");
		    		but2.setOnClickListener(stop);
		    	}
	        }
	        
	        
		}catch(Exception e)
		{
			e.printStackTrace();
		}
    }
	
	/*
	 * 返回上一层
	 */
	public void backtoProgramList(View v)
	{
		//downsound.close();
		this.finish();
	}
	
	/*
	 * 下载
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			bar.setMax(100);
			if(msg.what==0)
			{
				int length = msg.arg1;
				bar.setProgress(length);
			}else
			if(msg.what==1)
			{
				Toast.makeText(ProgramView.this, "节目下载完成！", Toast.LENGTH_SHORT).show();
				try {
					fileFlag.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				changetoSeekBar();
				but1.setText("播放");
				but1.setOnClickListener(play);
				
				but2.setText("删除");
				but2.setOnClickListener(delete);
			}else
			if(msg.what==2)
			{
				
				but1.setText("下载");
				but1.setOnClickListener(down);
				
				but2.setText("删除");
				but2.setOnClickListener(delete);
				Toast.makeText(ProgramView.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
			}
			
			
		}
	};
	
/*
 * 播放
 */
	private Handler pHandle = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		
    		if(seekbarIsChange) return;
    		
    		if(msg.what==1) return;
    		
    		int position = mp.getCurrentPosition();  
            
            int mMax = mp.getDuration();
            int sMax = seekBar.getMax();
            
            int time = position/1000;
            int hour = time/3600;time%=3600;
            int min = time/60;time%=60;
            int sec = time;
            String res2 = "";
            if(hour>0) res2=hour+"时";
            if(min>0) res2+=min+"分";
            if(sec>0) res2+=sec+"秒";
            
            if(res2.length()>1)
            	how_long.setText(res2+"/"+res);
            else
            	how_long.setText(res);
            seekBar.setProgress(position*sMax/mMax);  
    	}
    };
    
    /*
     * 显示图片
     */
    private void drawImg()
    {
    	//imgV.setScaleType(ScaleType.CENTER_INSIDE);
    	//imgV.setAdjustViewBounds(true);
    	imgV.setMaxHeight(70);
    	imgV.setMaxHeight(70);
    	imgV.setMinimumHeight(70);
    	imgV.setMinimumWidth(70);
    	
    	if(!img.exists()||img.length()==0)
    	{
    		Handler imgH = new Handler(){
    			public void handleMessage(Message msg){
    				if(msg.what==1)
    				{
    					InputStream imgIn = null;
    					try {
    						imgIn = new FileInputStream(img);
    					} catch (FileNotFoundException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    					Drawable drawImg = BitmapDrawable.createFromStream(imgIn,"img");
    					imgV.setImageDrawable(drawImg);
    				}else
    				if(msg.what==2)
    				{
    					Toast.makeText(ProgramView.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
    				}
    			}
    		};
    		Downloader downImg = new Downloader(sourceImg, BASE.basePath+"cache/"+ID+".png", imgH);
    		downImg.start();
    		
    	}else{
	    	InputStream imgIn = null;
			try {
				imgIn = new FileInputStream(img);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Drawable drawImg = BitmapDrawable.createFromStream(imgIn, "img");
			imgV.setImageDrawable(drawImg);
    	}
    }
    /*
	 * 删除文件
	 */
	public void deleteProgramFiles(View v)
	{
		if(f.exists()&&!fileFlag.exists())
		{
			Toast.makeText(this, "未下载完成的文件删除完毕！！", Toast.LENGTH_SHORT).show();
			f.delete();
			return;
		}
		
		if(!sound.exists())
		{
			Toast.makeText(this, "节目还没有下载！", Toast.LENGTH_SHORT).show();
		}else
		{
			fileFlag.delete();
			sound.delete();
			if(img.exists())
				img.delete();
			but1.setText("下载");
        	but1.setOnClickListener(down);
        	bar.setProgress(0);
        	
        	seekBar.setVisibility(View.GONE);
			bar.setVisibility(View.VISIBLE);
        	
			Toast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void download(View v)
	{
		
		if (downsound.isDownloading())
			return;
		
		//if(downsound==null)
		downsound = new Downloader(source,BASE.basePath+"programs/"+ID+".mp3",mHandler);
		
		Log.v("Debug", "download "+source);
		
		but1.setText("暂停");
    	but1.setOnClickListener(pause);
    	but2.setText("停止");
    	but2.setOnClickListener(pause);
		
		downsound.start();
	}
	
	/*
	 * 移除下载进度条，换成播放状态进度条
	 */
	private void changetoSeekBar()
	{
		/*
		v1.setVisibility(View.VISIBLE);   //正常显示
		v1.setVisibility(View.INVISIBLE); //隐藏参与布局（还占着地方）
		v1.setVisibility(View.GONE);      //隐藏不参与布局（不占地方）
		*/
		
		 bar.setVisibility(View.GONE);
		 seekBar.setVisibility(View.VISIBLE);
		 seekBar.setMax(100);
	}
	
	private void pause(View V)
	{
		downsound.stop();
		but1.setText("继续");
    	but1.setOnClickListener(down);
	}
	
	private void pauseOnPlay(View v)
	{
		but1.setText("播放");
    	but1.setOnClickListener(continuePlay);
    	mp.pause();
    	status = PAUSE;
    	stopProgressUpdate();
    	
	}
	private void stop()
	{
		mp.stop();
		but1.setText("播放");
		but1.setOnClickListener(play);
		
		but2.setText("删除");
		but2.setOnClickListener(delete);
		
		how_long.setText(res);
		
		try {
			
			stopProgressUpdate();
			seekBar.setProgress(0);
			try {
				mp.prepare();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void playP(View v)
	{
		but1.setText("暂停");
    	but1.setOnClickListener(pauseOnPlay);
    	but2.setText("停止");
    	but2.setOnClickListener(stop);
    	try {
    		
    		
    		if(mp!=null)
    		{
    			mp.stop();
    			//mp.release();
    		}
    		mp = new MediaPlayer();
    		
    		mp.setDataSource(BASE.basePath+"programs/"+ID+".mp3");
    		mp.prepare();
    		
    		mp.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					stop();
				}
			});
    		
    		int now = seekBar.getProgress();
            int mMax = mp.getDuration();  
            int sMax = seekBar.getMax();  
                        
            startProgressUpdate();
            int goTo = now*mMax/sMax;
            if(goTo<mp.getDuration()&&goTo>=0)
            {
            	mp.seekTo(now*mMax/sMax);
            }
            mp.start();
			
            Log.v("PLAY",BASE.basePath+"programs/"+ID+".mp3");
            
            Playing = BASE.basePath+"programs/"+ID+".mp3";
            
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
    	
	}
	//初始化监听器
	private void setListener()
	{
		/*
         * 监听器
         */
        play = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playP(v);
				
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
		    	startProgressUpdate();

			}
		};
		down = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				download(v);
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
				stop();
			}
		};
		delete = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteProgramFiles(v);
			}
		};
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
				seekbarIsChange = false;
				
				int dest = seekBar.getProgress();  
                  
                int mMax = mp.getDuration();  
                int sMax = seekBar.getMax();  
                
                mp.seekTo(mMax*dest/sMax);
                if(!mp.isPlaying()) return;
                startProgressUpdate();
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				//if(!mp.isPlaying()) return;
				//stopProgressUpdate();
				//startProgressUpdate();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//if(!mp.isPlaying()) return;
					stopProgressUpdate();
			}
			
		});
		/*
		 * end
		 */
	}
	
	private void startProgressUpdate(){
    	//开辟Thread 用于定期刷新SeekBar
		//if(dThread==null)
		dThread = new DelayThread(500);
    	dThread.start();
    	seekbarIsChange = false;
    }
	private void stopProgressUpdate()
	{
		seekbarIsChange = true;
	}
	
	
public class DelayThread extends Thread {
	int milliseconds;
	
	public DelayThread(int i){
		milliseconds = i;
	}
	public void run() {
		while(!seekbarIsChange&&mp.isPlaying()){
			try {
				sleep(milliseconds);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//Log.v("TAG","stop dThread");
			}
		
			pHandle.sendEmptyMessage(0);
		}
	}
}

//umeng统计
public void onResume() {
    super.onResume();
    MobclickAgent.onResume(this);
}
public void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);
}
}
