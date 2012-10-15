/*
ID: lazydom1
LANG: JAVA
TASK: About.java
Created on: 2012-10-15-上午8:21:50
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.newradio;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends Activity{
	
	
	TextView text2 = null;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.about);
	        
	        text2 = (TextView) findViewById(R.id.textView2);
	        text2.setText("测试");
	    }
	 
	 
	 public void back(View v)
	 {
		 this.finish();
	 }

}

