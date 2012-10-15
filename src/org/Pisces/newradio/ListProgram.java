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

import org.Pisces.XMLparser.ComparePrograms;
import org.Pisces.XMLparser.GetXml;
import org.Pisces.XMLparser.ProgramEntry;
import org.Pisces.XMLparser.PullProgramHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListProgram extends Activity{
	
	
	TextView text = null;
	Bundle bundle = null;
	
	final static String basePath = android.os.Environment.getExternalStorageDirectory()+"/.NewRadio/";
	
    private ListView list;  
    private ArrayAdapter<ProgramEntry> adapter;  
    private ArrayList<ProgramEntry> programEntryList;
    private String author = null; 
    
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listprogram);
	        
	        bundle = this.getIntent().getExtras();
	        
	        text = (TextView) findViewById(R.id.nameofprogram);
	        
	        String tmp = bundle.getString("program_name")+"";
	        text.setText(tmp);
	        
	        this.author = bundle.getString("Ename");
	        getinfo(author);
	        
	    }
	 
	 
	 public void getinfo(String author)
		{
			InputStream programStream = GetXml.getXmlFromInternet("http://bchine.com/pisces/newradio/"+author.trim()+".xml");
			File f = new File(basePath+author.trim()+".xml");
			
			if(!f.exists())
			{
			//	..
			}
			
			//InputStream programStream = new GetXml().getXmlFromSDcard(basePath+author.trim()+".xml");
	        
	        PullProgramHandler pullHandler = new PullProgramHandler();
	        
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
					bundle.putLong("pushtime", selected.getPushtime());
					bundle.putLong("how_long", selected.getHow_long());
					bundle.putString("subtitle", selected.getSubtitle());
					bundle.putString("comment", selected.getComment());
					bundle.putString("source", selected.getSource());
					bundle.putString("img", selected.getImg());
					bundle.putInt("ID", selected.getID());
					bundle.putLong("filesize", selected.getFilesize());
					bundle.putString("author", selected.getAuthor());
					
					
					intent.putExtras(bundle);
					startActivity(intent);
				}
			};
			list.setOnItemClickListener(lis1);
			
		}
		
		public void refersh(View v)
		{
			if(author==null) return;
			programEntryList.clear();
			this.getinfo(author);
		}
	 
	 public void backtoAuthors(View v)
	 {
		 this.finish();
	 }
	 

}

