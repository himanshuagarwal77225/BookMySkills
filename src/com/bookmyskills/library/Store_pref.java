package com.bookmyskills.library;

import android.content.Context;
import android.content.SharedPreferences;

public class Store_pref {

	SharedPreferences pref_master;
	SharedPreferences.Editor editor_pref_master;
	Context c;
	public Store_pref(Context con) {
		c=con;
		pref_master=con.getSharedPreferences("Tele_Landmark_pref", 0);
	}
	
	public void open_editor() {
		// TODO Auto-generated method stub
		editor_pref_master=pref_master.edit();
	}
	
	public void set_user_name(String user_name) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("user_name", user_name);
		editor_pref_master.commit();
	}
	public String get_user_name() {
		// TODO Auto-generated method stub
		return pref_master.getString("user_name", "");
	}
	
	public void set_UserId(String UserId) {
		// TODO Auto-generated method stub
		open_editor();
		editor_pref_master.putString("UserId", UserId);
		editor_pref_master.commit();
	}
	public String get_UserId() {
		// TODO Auto-generated method stub
		return pref_master.getString("UserId", "");
	}

	public void Clear_data() {
		open_editor();
		editor_pref_master.putString("UserId", "");
		editor_pref_master.putString("user_name","");
		editor_pref_master.commit();
	}
}
