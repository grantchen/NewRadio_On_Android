/*
ID: lazydom1
LANG: JAVA
TASK: ListProgram.java
Created on: 2012-10-15-下午7:45:40
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.newradio;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.Pisces.IO.BASE;
import org.Pisces.IO.Downloader;
import org.Pisces.XMLparser.AuthorEntry;
import org.Pisces.XMLparser.ComparePrograms;
import org.Pisces.XMLparser.GetXml;
import org.Pisces.XMLparser.ProgramEntry;
import org.Pisces.XMLparser.PullProgramHandler;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.ReportPolicy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListProgram extends Activity{
	
	
	private TextView text = null;
	private Bundle bundle = null;
	private Button but1;
	
    private ListView list;  
    private ArrayAdapter<ProgramEntry> adapter;  
    private ArrayList<ProgramEntry> programEntryList;
    
    private int DJ;
    
    
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listprogram);
	        MobclickAgent.onError(this);
	        MobclickAgent.updateOnlineConfig(this);
	        MobclickAgent.setDefaultReportPolicy(this, ReportPolicy.BATCH_AT_LAUNCH);
	        
	        bundle = this.getIntent().getExtras();
	        
	        text = (TextView) findViewById(R.id.nameofprogram);
	        but1 = (Button) findViewById(R.id.referProgram);
	        
	        but1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					refersh(v);
				}
			});
	        
	        
	        this.DJ = bundle.getInt("DJ");
	        text.setText(AuthorEntry.getAlbum(DJ));

	        downXml(false);
	    }
	 
	 public void getinfo(int DJ)
		{
			//InputStream programStream = GetXml.getXmlFromInternet("http://bchine.com/pisces/newradio/"+author.trim()+".xml");
			InputStream programStream = GetXml.getXmlFromSDcard(BASE.basePath+"Podcast.aspx");
			
			
			//InputStream programStream = new GetXml().getXmlFromSDcard(basePath+author.trim()+".xml");
	        
	        PullProgramHandler pullHandler = new PullProgramHandler(DJ);
	        
	        
	        programEntryList = pullHandler.parse(programStream);
	        
	        Collections.sort(programEntryList,new ComparePrograms());
	        
	        list = (ListView) findViewById(R.id.programslist);
	        adapter = new ArrayAdapter<ProgramEntry>(this,android.R.layout.simple_list_item_1, programEntryList);
	        list.setAdapter(adapter);
	        
	        OnItemClickListener lis1 = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(ListProgram.this, ProgramView.class);
					Bundle bundle = new Bundle();

					ProgramEntry selected = programEntryList.get(arg2);
					
					bundle.putString("title", selected.getTitle());
					bundle.putString("pushtime", selected.getPushtime());
					bundle.putLong("how_long", selected.getHow_long());
					bundle.putString("name", selected.getName());
					bundle.putString("comment", selected.getComment());
					bundle.putString("source", selected.getSource());
					bundle.putString("img", selected.getImg());
					bundle.putInt("ID", selected.getID());
					bundle.putInt("DJ", selected.getDJ());
					
					
					intent.putExtras(bundle);
					startActivity(intent);
				}
			};
			list.setOnItemClickListener(lis1);
			
		}
		
	 
	 public void backtoAuthors(View v)
	 {
		 this.finish();
	 }
	 
	 public void refersh(View v)
	{
		downXml(true);
	}
		
	private void downXml(boolean isRefersh)
	{
		File f= new File(BASE.basePath+"Podcast.aspx");
		if(isRefersh||!f.exists()||f.length()==0)
		{
			if(f.exists())
				f.delete();
			Downloader down = new Downloader(BASE.baseUrl+"Podcast.aspx", BASE.basePath+"Podcast.aspx", handler);
			down.start();
		}else
			if(f.exists()&&f.length()>0) getinfo(DJ);
	}
	private Handler handler = new Handler()
	 {
		 public void handleMessage(Message msg) {
			 if(msg.what==1)
			 {
				 if(programEntryList!=null)
					 programEntryList.clear();
				 getinfo(DJ);
			 }else
			if(msg.what==2)
			{
				Toast.makeText(ListProgram.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
			}
		 }
	 };
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

