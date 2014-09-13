package com.engelhardt.BF.Alarm.App;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.os.StrictMode;
import android.widget.Toast;

public class AlarmServiceClient {

	public void fireAlarm(Activity caller, String serverAdress, int delay, String group) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("http://");
		queryBuilder.append(serverAdress);
		queryBuilder.append(":8080/AlarmServer/");
		queryBuilder.append(delay);
        queryBuilder.append("/");
        queryBuilder.append(group);

		try {
			URL url = new URL(queryBuilder.toString().replace(" ", "%20"));
			InputStream response = url.openStream();
			Toast firingToast = Toast.makeText(caller.getApplicationContext(),
					"Antwort: " + checkResponse(response), Toast.LENGTH_SHORT);
			firingToast.show();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String checkResponse(InputStream response) {
		try {
			ArrayList<Byte> responseBytes = new ArrayList<Byte>();
			byte[] bytes = new byte[1024];
			while (response.read(bytes) > 0) {
				for (byte b : bytes) {
					responseBytes.add(b);
				}
			}
			bytes = new byte[responseBytes.size()];
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = responseBytes.get(i);
			}
			return new String(bytes);

		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
