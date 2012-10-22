/*
ID: lazydom1
LANG: JAVA
TASK: AuthorListView.java
Created on: 2012-10-15-下午7:05:05
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.GUI;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import org.Pisces.IO.Downloader;
import org.Pisces.XMLparser.AuthorEntry;
import org.Pisces.XMLparser.GetXml;
import org.Pisces.XMLparser.PullAuthorHandler;
import org.Pisces.newradio.AuthorPage;
import org.Pisces.newradio.ListProgram;
import org.Pisces.newradio.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AuthorListView {

	final static String basePath = "/mnt/sdcard/.NewRadio/";
	final static String baseUrl = "http://bchine.com/pisces/newradio/";
	
    private ListView list;  
    private ArrayAdapter<AuthorEntry> adapter;  
    private ArrayList<AuthorEntry> authorEntryList;
    private Activity father;
    private Button but2;
    
    
	public AuthorListView(AuthorPage father)
	{
		super();
		this.father = father;
		but2 = (Button) father.findViewById(R.id.button2);
		
		but2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refersh(v);
			}
		});
		
		downXml(false);
		
		getinfo();
	}
	
	public void getinfo()
	{
		//InputStream authorStream = GetXml.getXmlFromInternet("http://bchine.com/pisces/newradio/authors.xml");
		InputStream authorStream = GetXml.getXmlFromSDcard(basePath+"authors.xml");
        
        PullAuthorHandler pullHandler = new PullAuthorHandler();
        
        authorEntryList = pullHandler.parse(authorStream);
        
        list = (ListView) father.findViewById(R.id.listauthors);
        adapter = new ArrayAdapter<AuthorEntry>(father, android.R.layout.simple_list_item_1, authorEntryList);
        
        list.setAdapter(adapter);
        
        OnItemClickListener lis1 = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(father, ListProgram.class);
				Bundle bundle = new Bundle();

				AuthorEntry selected = authorEntryList.get(arg2);
				
				bundle.putString("Cname", selected.getCname());
				bundle.putString("Ename", selected.getEname());
				bundle.putString("program_name", selected.getProgram_name());
				bundle.putString("summary", selected.getSummary());
				bundle.putInt("tot_program", selected.getTot_program());

				intent.putExtras(bundle);
				father.startActivity(intent);
			}
		};
		list.setOnItemClickListener(lis1);
	}
	
	public void refersh(View v)
	{
		downXml(true);
		authorEntryList.clear();
		this.getinfo();
	}
	
	private void downXml(boolean isRefersh)
	{
		File f= new File(basePath+"authors.xml");
		if(isRefersh||!f.exists())
		{
			if(f.exists())
				f.delete();
			Handler handler = new Handler();
			Downloader down = new Downloader(baseUrl+"authors.xml", basePath+"authors.xml", handler);
			down.start();
			while(!down.isCompleted())
			{
				
			}
			Log.v("downXML","authors.xml");
		}
	}
}


