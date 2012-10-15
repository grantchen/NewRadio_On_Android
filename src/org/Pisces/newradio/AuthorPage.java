package org.Pisces.newradio;

import org.Pisces.GUI.AuthorListView;
import org.Pisces.IO.DBHelper;
import org.Pisces.IO.Dao;
import org.Pisces.IO.DirHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class AuthorPage extends Activity {

	//var
	AuthorListView listView;
	//var
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorpage);
        
        if(!DirHelper.isFileExist(".NewRadio"))
        {
        	DirHelper.creatSDDir(".NewRadio");
        }
        
        
        listView = new AuthorListView(this);
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_radio, menu);
        return true;
    }
    
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK 
    			&&event.getAction() == KeyEvent.ACTION_DOWN){   
    	    if((System.currentTimeMillis()-exitTime) > 2000){  
    	        Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
    	        exitTime = System.currentTimeMillis();   
    		} else {
    			finish();
    			
    			System.exit(0);
    		}
    	    return true;   
        }
    	return super.onKeyDown(keyCode, event);
    }
    
    
    public void about(View v)
    {
    	Intent intent = new Intent();
		intent.setClass(this, About.class);
		this.startActivity(intent);
    }
    
    public void refersh(View v)
    {
    	listView.refersh(v);
    }
    
    
}
