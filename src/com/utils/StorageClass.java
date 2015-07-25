package com.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.models.SkillsModel;
import com.models.UserSearchModel;

public class StorageClass {

	public static final String PREF_NAME = "user_data";
	public static String EMAIL = "email";
	public static String PASSWORD = "password";

	public static String REMEMBERME = "is_remember";
	public static String USER_ID = "user_id";
	public static String USER_TYPE = "user_type";
	public static String BRAND_NAME = "brand_name";
	public static String AUTOLOGIND = "auto_login";

	private static StorageClass instance;
	private SharedPreferences sh;
	private Editor edit;
	private boolean isViewed = false;
	private ArrayList<SkillsModel> responseSkillsArray;
	private ArrayList<UserSearchModel> userSearchArray;

	private StorageClass(Context mContext) {
		sh = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		edit = sh.edit();
	}

	public static synchronized StorageClass getInstance(Context mContext) {
		if (instance == null) {
			instance = new StorageClass(mContext);
		}
		return instance;
	}

	public void setUserName(String membertype) {
		edit.putString("username", membertype).commit();
	}

	public String getUserName() {
		return sh.getString("username", "");
	}

	public void setFirstName(String membertype) {
		edit.putString("firstname", membertype).commit();
	}

	public String getFirstName() {
		return sh.getString("firstname", "");
	}

	public void setLastName(String membertype) {
		edit.putString("lastname", membertype).commit();
	}

	public String getLastName() {
		return sh.getString("lastname", "");
	}

	public void setToken(String membertype) {
		edit.putString("token", membertype).commit();
	}

	public String getToken() {
		return sh.getString("token", "");
	}

	public void setAutoLogin(boolean membertype) {
		edit.putBoolean("auto_login", membertype).commit();
	}

	public boolean getAutoLogin() {
		return sh.getBoolean("auto_login", false);
	}

	public void setUserLoggedIn(boolean membertype) {
		edit.putBoolean("user_logged_in", membertype).commit();
	}

	public boolean getUserLoggedIn() {
		return sh.getBoolean("user_logged_in", false);
	}

	public int getUserId() {
		return sh.getInt("userid", -1);
	}

	public void setUserId(int userid) {
		edit.putInt("userid", userid).commit();
	}

	public void clear() {
		edit.clear().commit();
	}

	public void setRemeber(boolean isflag) {
		edit.putBoolean("remember", isflag).commit();
	}

	public boolean isRemember() {
		return sh.getBoolean("remember", false);
	}

	public String getUserEmail() {
		return sh.getString("useremail", "");
	}

	public void setUserEmail(String useremail) {
		edit.putString("useremail", useremail).commit();
	}

	public String getPassword() {
		return sh.getString("password", "");
	}

	public void setPassword(String password) {
		edit.putString("password", password).commit();
	}

	public void setSkillsArray(ArrayList<SkillsModel> responseSkillsArray) {
		this.responseSkillsArray = responseSkillsArray;

	}

	public ArrayList<SkillsModel> getSkillsArray() {
		return this.responseSkillsArray;

	}

	public void setSearchUsersArray(ArrayList<UserSearchModel> userSearchArray) {
		this.userSearchArray = userSearchArray;

	}

	public ArrayList<UserSearchModel> getSearchUsersArray() {
		return this.userSearchArray;

	}

	public void setDeviceToken(String registrationId) {
		edit.putString("device_token", registrationId).commit();
	}

	public String gettDeviceToken() {
		return sh.getString("device_token", "");
	}

}
