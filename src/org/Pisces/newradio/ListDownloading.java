/*
ID: lazydom1
LANG: JAVA
TASK: ListDownloading.java
Created on: 2012-11-13-下午6:19:20
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.newradio;

import java.util.ArrayList;

import org.Pisces.IO.DownLoadersInfo;
import org.Pisces.IO.PUB;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.ReportPolicy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListDownloading extends Activity{

	private Bundle bundle = null;
	private Button but1 = null;
	private ListView listView = null;
	
	private ArrayList<DownLoadersInfo> list = new ArrayList<DownLoadersInfo>(); 
	private ArrayAdapter<DownLoadersInfo> adapter; 
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listdowning);
	        MobclickAgent.onError(this);
	        MobclickAgent.updateOnlineConfig(this);
	        MobclickAgent.setDefaultReportPolicy(this, ReportPolicy.BATCH_AT_LAUNCH);
	        
	        bundle = this.getIntent().getExtras();
	        but1 = (Button) findViewById(R.id.back);
	        listView = (ListView) findViewById(R.id.listdowning);
	        
	        list.clear();
	        for(int i=0;i<PUB.getSize();i++)
	        {
	        	list.add(PUB.getDownloadersInfo(i));
	        }
	        
	        adapter = new ArrayAdapter<DownLoadersInfo>(this,android.R.layout.simple_list_item_1, list);
	        listView.setAdapter(adapter);
	       
	        OnItemClickListener lis1 = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(ListDownloading.this, ProgramView.class);
					Bundle bundle = new Bundle();

					DownLoadersInfo selected = list.get(arg2);
					
					bundle.putString("title", selected.title);
					bundle.putString("pushtime", selected.pushtime);
					bundle.putLong("how_long", selected.how_long);
				//	bundle.putString("name", selected.);
					bundle.putString("comment", selected.comment);
					bundle.putString("source", selected.source);
					bundle.putString("img", selected.img);
					bundle.putInt("ID", selected.ID);
					bundle.putInt("DJ", selected.DJ);
					
					
					intent.putExtras(bundle);
					startActivity(intent);
				}
			};
			listView.setOnItemClickListener(lis1);
			
			OnClickListener back = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					backtoTop(v);
				}
			};
			
	        but1.setOnClickListener(back);
	    }
	 
	 /*
		 * 返回上一层
		 */
		public void backtoTop(View v)
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

