package com.example.braintraininggame;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

// Settings screen
public class PreferencesActivity extends PreferenceActivity {
	
	private static final String OPT_HINTS = "hints";
	private static final boolean OPT_HINTS_DEF = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
	
	public static boolean getHints(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_HINTS, OPT_HINTS_DEF);
	}
}
