/*
ID: lazydom1
LANG: JAVA
TASK: About.java
Created on: 2012-10-15-上午8:21:50
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.newradio;


import java.io.File;

import org.Pisces.IO.BASE;
import org.Pisces.IO.Downloader;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.ReportPolicy;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;

public class About extends Activity{
	
	private WebView web = null;
	private Handler handler;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.about);
	        MobclickAgent.onError(this);
	        MobclickAgent.updateOnlineConfig(this);
	        MobclickAgent.setDefaultReportPolicy(this, ReportPolicy.BATCH_AT_LAUNCH);
	        
	        web = (WebView) findViewById(R.id.webView1);
	        
	        
	        File f = new File(BASE.basePath+"about.html");
	        
	        if(f.exists()) f.delete();
	        
	        web.loadUrl("file:///android_asset/about.html");
	        
	        handler = new Handler(){
        		public void handleMessage(Message msg){
        			if(msg.what==1)
        			{
        				web.loadUrl("file://"+BASE.basePath+"about.html");
        			}else
        			if(msg.what==2)
        			{
        				//Toast.makeText(About.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
        			}
        		}
        	};
	      
        	Downloader down = new Downloader(BASE.AboutPath+"about.html", BASE.basePath+"about.html", handler);
        	down.start();
	        
	    }
	 
	 
	 public void back(View v)
	 {
		 this.finish();
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

