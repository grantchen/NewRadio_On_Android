/*
ID: lazydom1
LANG: JAVA
TASK: AuthorListView.java
Created on: 2012-10-15-下午7:05:05
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.Pisces.IO.BASE;
import org.Pisces.IO.Downloader;
import org.Pisces.XMLparser.AuthorEntry;
import org.Pisces.XMLparser.GetXml;
import org.Pisces.XMLparser.PullAuthorHandler;
import org.Pisces.newradio.AuthorPage;
import org.Pisces.newradio.ListProgram;
import org.Pisces.newradio.R;
import org.Pisces.newradio.RecordVoice;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AuthorListView {
	
    private ListView list;  
    private ArrayAdapter<AuthorEntry> adapter;  
    private ArrayList<AuthorEntry> authorEntryList;
    private Activity father;
    
    private ImageView img1 = null;
    private ImageView img2 = null;
    private ImageView img3 = null;
    private ImageView img4 = null;
    private ImageView img5 = null;
    private ImageView img6 = null;
    
	public AuthorListView(AuthorPage father)
	{
		super();
		this.father = father;
		//but2 = (Button) father.findViewById(R.id.button2);
		//but1 = (Button) father.findViewById(R.id.button1);
		
		img1 = (ImageView) father.findViewById(R.id.imageView1);
		img2 = (ImageView) father.findViewById(R.id.imageView2);
		img3 = (ImageView) father.findViewById(R.id.imageView3);
		img4 = (ImageView) father.findViewById(R.id.imageView4);
		img5 = (ImageView) father.findViewById(R.id.imageView5);
		img6 = (ImageView) father.findViewById(R.id.imageView6);
		
		downXml(false);
	}
	
	public void getinfo()
	{
		//InputStream authorStream = GetXml.getXmlFromInternet("http://bchine.com/pisces/newradio/authors.xml");
		InputStream authorStream = GetXml.getXmlFromSDcard(BASE.basePath+"DJ.aspx");
        
        PullAuthorHandler pullHandler = new PullAuthorHandler();
        
        authorEntryList = pullHandler.parse(authorStream);
        
        adapter = new ArrayAdapter<AuthorEntry>(father, android.R.layout.simple_list_item_1, authorEntryList);
       
        
        setListener();
        

	}
	
	private void setListener()
	{
		OnClickListener lis1 = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeToDJ(0);
			}
		};
		OnClickListener lis2 = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeToDJ(1);
			}
		};
		OnClickListener lis3 = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeToDJ(2);
			}
		};
		OnClickListener lis4 = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeToDJ(3);
			}
		};
		OnClickListener lis5 = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeToDJ(4);
			}
		};
		OnClickListener lis6 = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeToDJ(5);
			}
		};
		
		img1.setOnClickListener(lis4);
		img2.setOnClickListener(lis3);
		img3.setOnClickListener(lis2);
		img4.setOnClickListener(lis6);
		img5.setOnClickListener(lis1);
		img6.setOnClickListener(lis5);
		
	}
	
	private void changeToDJ(int num)
	{
		
		Intent intent = new Intent();
		intent.setClass(father, ListProgram.class);
		Bundle bundle = new Bundle();
		
		AuthorEntry selected = authorEntryList.get(num);
		
		bundle.putInt("DJ", selected.getID());
		bundle.putString("", AuthorEntry.getAlbum(selected.getID()));

		intent.putExtras(bundle);
		father.startActivity(intent);
	}
	
	
	public void changeToHumming()
	{
		Intent intent = new Intent();
		intent.setClass(father, RecordVoice.class);
		
		father.startActivity(intent);
	}
	public void refersh(View v)
	{
		downXml(true);
	}
	
	private void downXml(boolean isRefersh)
	{
		File f= new File(BASE.basePath+"DJ.aspx");
		if(isRefersh||!f.exists()||f.length()==0)
		{
			if(f.exists())
				f.delete();
			Downloader down = new Downloader(BASE.baseUrl+"DJ.aspx", BASE.basePath+"DJ.aspx", handler);
			down.start();
		}else
		if(f.exists()&&f.length()>0) getinfo();
		
	}
	private Handler handler = new Handler(){
		 public void handleMessage(Message msg) {
			 if(msg.what==1)
			 {
				if(authorEntryList!=null)
					authorEntryList.clear();
				getinfo();
			 }else
			 if(msg.what==2)
			 {
				 Toast.makeText(father, "网络连接失败！", Toast.LENGTH_SHORT).show();
			 }
		 }
	};
}


