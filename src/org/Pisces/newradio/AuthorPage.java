package org.Pisces.newradio;

import org.Pisces.GUI.AuthorListView;
import org.Pisces.IO.DirHelper;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.ReportPolicy;
import com.umeng.update.UmengUpdateAgent;

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
        setContentView(R.layout.win8);
        MobclickAgent.onError(this);
        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.setDefaultReportPolicy(this, ReportPolicy.BATCH_AT_LAUNCH);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        
        if(!DirHelper.isExist(".NewRadio/"))
        {
        	DirHelper.createDir(".NewRadio/");
        }
        
        
        listView = new AuthorListView(AuthorPage.this);
        
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
