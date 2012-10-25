/*
ID: lazydom1
LANG: JAVA
TASK: RecordVoice.java
Created on: 2012-10-26-上午12:14:13
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.newradio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.Pisces.newradio.R;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.ReportPolicy;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class RecordVoice extends Activity{
	
	
	private final static int STOP = 1;
	private final static int START = 2;
	private static int status;
	
	long startTime;
	
	private Button rec = null;
	private MediaRecorder mic = null;
	private ProgressBar bar = null;
	private Handler handle = null;
	private TextView text = null;
	private Handler sendOk;
	
	private String AudioName = "/mnt/sdcard/.NewRadio/tmp.mp4";
	
	
	OnClickListener start = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			rec.setText("停止录音");
			rec.setOnClickListener(stop);
			
			File f = new File(AudioName);
			if(f.exists()) f.delete();
			
			startRecord();
		}
	};
	OnClickListener stop = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			rec.setText("开始录音");
			rec.setOnClickListener(start);
			status = STOP;
			if(mic!=null)
			{
				mic.stop();
			}
			bar.setProgress(0);
			bar.setMax(10);
			text.setText("录制时间：0s");
			
			sendOk = new Handler(){
				
				public void handleMessage(Message msg){
					if(msg.what==2)
					{
						Toast.makeText(RecordVoice.this, "录音上传完成，请等待服务器相应！", Toast.LENGTH_SHORT).show();
						receieve();
					}
				}
			};
			
			
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						
						Socket soc = new Socket("211.87.227.116", 8998);
						File f = new File(AudioName);
						
						FileInputStream fin = new FileInputStream(f);
						OutputStream out = soc.getOutputStream();
						int bit = fin.read();
						while(bit!=-1)
						{
							out.write(bit);
							bit = fin.read();
						}
						
						out.close();
						fin.close();
						soc.close();
						Message msg = Message.obtain();
						msg.what=2;
						sendOk.sendMessage(msg);
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			t.start();
			
		}
	};
	public void receieve()
	{
		
	}
	
	private void startRecord()
	{
		if(mic!=null)
			mic.release();
		mic = new MediaRecorder();
		mic.setAudioSource(MediaRecorder.AudioSource.MIC);
		mic.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mic.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); 
		mic.setOutputFile(AudioName);
		status = START;
		try {  
            mic.prepare();  
            mic.start();
           startTime = System.currentTimeMillis();
            Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(status==START)
					{
						long time = System.currentTimeMillis();
						Message msg = Message.obtain();
						msg.what =1;
						msg.arg1 =(int) (time-startTime)/1000;
						handle.sendMessage(msg);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						//	e.printStackTrace();
						}
					}
				}
			});
            
            t.start();
        } catch (IllegalStateException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		
	}
	
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.recordvoice);
	        MobclickAgent.onError(this);
	        MobclickAgent.updateOnlineConfig(this);
	        MobclickAgent.setDefaultReportPolicy(this, ReportPolicy.BATCH_AT_LAUNCH);

	        handle = new Handler(){
	        	public void handleMessage(Message msg){
	        		if(msg.what==1)
	        		{
	        			bar.setProgress(msg.arg1);
	        			if(bar.getProgress()==bar.getMax())
	        				bar.setMax(bar.getMax()+10);
	        			text.setText("录制时间: "+msg.arg1+"s");
	        		}
	        	}
	        };
	        
	        bar = (ProgressBar) findViewById(R.id.recordbar);
	        bar.setMax(10);
	        rec = (Button) findViewById(R.id.record);
	        rec.setOnClickListener(start);
	        text = (TextView) findViewById(R.id.rectime);
	        
	    }

	
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_new_radio, menu);
	        return true;
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

