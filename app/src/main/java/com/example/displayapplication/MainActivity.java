package com.example.displayapplication;

import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
	
	private Button updateButton;
	private Button aboutButton;
    private ListView listView;
    private List<EventItem> eventItems;
    private EventListAdapter eventAdapter;
    private EventDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		updateButton = (Button) findViewById(R.id.updateBtn);
		OnClickListener updateButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoadDataTask task = new LoadDataTask();
                task.setAdapter(eventAdapter);
                task.setDatasource(datasource);
                task.execute("http://metovaweb.watchitoo.com/app_data.json");
			}
		};
		updateButton.setOnClickListener(updateButtonListener);
		
		aboutButton = (Button) findViewById(R.id.aboutBtn);
		OnClickListener aboutButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
                String versionName = "No version name";
                int versionCode = -1;
                try {
                    versionName = MainActivity.this.getPackageManager()
                            .getPackageInfo(MainActivity.this.getPackageName(), 0).versionName;
                    versionCode = MainActivity.this.getPackageManager()
                            .getPackageInfo(MainActivity.this.getPackageName(), 0).versionCode;
                }
                catch (PackageManager.NameNotFoundException e) {
                    Log.e("MainActivity", e.getMessage());
                }
				Toast.makeText(getApplicationContext(), "Version name: " + versionName +
                                                        ", version code: " + versionCode, Toast.LENGTH_SHORT).show();
			}
		};
		aboutButton.setOnClickListener(aboutButtonListener);

        listView = (ListView) findViewById(R.id.listView);
        eventItems = new ArrayList<EventItem>();
        eventItems.add(new EventItem("No events", "", "", "", ""));

        datasource = new EventDataSource(this);
        datasource.open();
        List<EventItem> eventsFromDB = datasource.getAllEvents();
        if(eventsFromDB.size() > 0){
            eventItems = eventsFromDB;
        }

        eventAdapter = new EventListAdapter(this, R.layout.event_item, eventItems );
        listView.setAdapter(eventAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventItem item = eventItems.get(position);
        Toast toast = Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        datasource.close();
        super.onDestroy();
    }
}
