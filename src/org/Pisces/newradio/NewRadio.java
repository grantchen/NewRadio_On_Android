package org.Pisces.newradio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import XMLparser.ComparePrograms;
import XMLparser.GetXml;
import XMLparser.ProgramEntry;
import XMLparser.PullProgramHandler;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewRadio extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_radio);
        
        InputStream programStream = GetXml.getXmlFromInternet("http://bchine.com/pisces/newradio/chendan.xml");
        //InputStream programStream = readEarthquakeDataFromFile();
        
        PullProgramHandler pullHandler = new PullProgramHandler();
        
        programEntryList = pullHandler.parse(programStream);
        
        Collections.sort(programEntryList,new ComparePrograms());
        
        list = (ListView) this.findViewById(R.id.listView1);
        adapter = new ArrayAdapter<ProgramEntry>(this, android.R.layout.simple_list_item_1, programEntryList);
        list.setAdapter(adapter);  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_radio, menu);
        return true;
    }

    
    
    //var
    ListView list;  
    ArrayAdapter<ProgramEntry> adapter;  
    ArrayList<ProgramEntry> programEntryList; 
    
}
