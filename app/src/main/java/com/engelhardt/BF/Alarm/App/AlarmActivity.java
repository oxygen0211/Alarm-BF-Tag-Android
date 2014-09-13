package com.engelhardt.BF.Alarm.App;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AlarmActivity extends Activity {
    static final ConcurrentMap <String, String> groups = new ConcurrentHashMap <String, String> ();

    static
    {
        groups.put("Alle", "all");
        groups.put("Gruppe 1", "1");
        groups.put("Gruppe 2", "2");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_aktivity);
        SharedPreferences preferences = getSharedPreferences("AlarmBF", 0);
        final PropertyManager propManager = new PropertyManager(preferences);

        final CheckBox delayCheck = (CheckBox) findViewById(R.id.delayCheck);
        final EditText delayField = (EditText) findViewById(R.id.delayField);
        final TextView minLabel = (TextView) findViewById(R.id.minuteLabel);
        final EditText serverField = (EditText) findViewById(R.id.ServerField);
        Button fireAlarmButton = (Button) findViewById(R.id.fireAlarmButton);
        final Spinner groupSpinner = (Spinner) findViewById(R.id.groupSpinner);

        String[] groupArray = groups.keySet().toArray(new String[groups.size()]);
        groupSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, groupArray));

        
        delayField.setVisibility(View.GONE);
        minLabel.setVisibility(View.GONE);
        
        delayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
				{
					delayField.setVisibility(View.VISIBLE);
					minLabel.setVisibility(View.VISIBLE);
				}
				
				else
				{
					delayField.setVisibility(View.GONE);
			        minLabel.setVisibility(View.GONE);
				}
			}
		});
        
        fireAlarmButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                String group = groupSpinner.getSelectedItem().toString();

				int delay = 0;
				if(delayCheck.isChecked())
				{
					delay = Integer.valueOf(delayField.getText().toString());
				}
				String host = serverField.getText().toString() ;
				if(host==null || host.equals(""))
				{
					Toast firingToast = Toast.makeText(AlarmActivity.this,
							"Erst server angeben!", Toast.LENGTH_SHORT);
					firingToast.show();
				}
				else
				{
					propManager.saveLasthost(host);
					new AlarmServiceClient().fireAlarm(AlarmActivity.this,host,delay, groups.get(group));
				}
			}
		});
        
       serverField.setText(propManager.getLastHost());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_alarm_aktivity, menu);
        return true;
    }
}
