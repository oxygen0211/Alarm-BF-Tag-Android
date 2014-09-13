package com.engelhardt.BF.Alarm.App;

import android.content.SharedPreferences;

public class PropertyManager {
	private static String HOST = "lastHost";

    private SharedPreferences preferences;

	public PropertyManager(SharedPreferences preferences)
    {
        this.preferences = preferences;
    }

	public void saveLasthost(String host) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(HOST, host);
        editor.commit();
	}

	public String getLastHost() {
        return preferences.getString(HOST,"");
	}
}
