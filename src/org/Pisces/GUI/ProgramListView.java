/*
ID: lazydom1
LANG: JAVA
TASK: ProgramListView.java
Created on: 2012-10-13-下午9:59:00
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.GUI;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.Pisces.XMLparser.ComparePrograms;
import org.Pisces.XMLparser.GetXml;
import org.Pisces.XMLparser.ProgramEntry;
import org.Pisces.XMLparser.PullProgramHandler;
import org.Pisces.newradio.AuthorPage;
import org.Pisces.newradio.ProgramView;
import org.Pisces.newradio.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProgramListView {

    private ListView list;  
    private ArrayAdapter<ProgramEntry> adapter;  
    private ArrayList<ProgramEntry> programEntryList;
    private String author; 
    private AuthorPage father;
    
	public ProgramListView(AuthorPage father,String author)
	{
		super();
		
		this.author = author;
		this.father = father;
		
		getinfo();
	}
	
	public void getinfo()
	{
		InputStream programStream = GetXml.getXmlFromInternet("http://bchine.com/pisces/newradio/"+author.trim()+".xml");
        
        PullProgramHandler pullHandler = new PullProgramHandler();
        
        programEntryList = pullHandler.parse(programStream);
        
        Collections.sort(programEntryList,new ComparePrograms());
        
        list = (ListView) father.findViewById(R.id.listView1);
        adapter = new ArrayAdapter<ProgramEntry>(father, android.R.layout.simple_list_item_1, programEntryList);
        list.setAdapter(adapter);
        
        OnItemClickListener lis1 = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(father, ProgramView.class);
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
				father.startActivity(intent);
			}
		};
		list.setOnItemClickListener(lis1);
		
	}
	
	public void refersh(View v)
	{
		programEntryList.clear();
		getinfo();
	}
}

