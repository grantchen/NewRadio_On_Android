/*
ID: lazydom1
LANG: JAVA
TASK: AuthorListView.java
Created on: 2012-10-15-下午7:05:05
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.GUI;

import java.io.InputStream;
import java.util.ArrayList;

import org.Pisces.XMLparser.AuthorEntry;
import org.Pisces.XMLparser.GetXml;
import org.Pisces.XMLparser.PullAuthorHandler;
import org.Pisces.newradio.AuthorPage;
import org.Pisces.newradio.ListProgram;
import org.Pisces.newradio.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AuthorListView {

	final static String basePath = android.os.Environment.getExternalStorageDirectory()+"/.NewRadio/";
	
    private ListView list;  
    private ArrayAdapter<AuthorEntry> adapter;  
    private ArrayList<AuthorEntry> authorEntryList;
    private Activity father;
    
    
	public AuthorListView(AuthorPage father)
	{
		super();
		this.father = father;
		getinfo();
	}
	
	public void getinfo()
	{
		InputStream authorStream = GetXml.getXmlFromInternet("http://bchine.com/pisces/newradio/authors.xml");
		//InputStream authorStream = new GetXml().getXmlFromSDcard(basePath+"authors.xml");
        
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
		authorEntryList.clear();
		this.getinfo();
	}
}


