package com.bookmyskills;

import android.app.Application;
import android.content.res.Configuration;

import com.android.volley.RequestQueue;
import com.android.volley.examples.toolbox.MyVolley;
import com.android.volley.toolbox.Volley;

public class InternalApp extends Application {

	RequestQueue queue;

	@Override
	public void onCreate() {
		super.onCreate();
		MyVolley.init(this);
		queue = Volley.newRequestQueue(this);
	}

	public RequestQueue getQueue() {
		return queue;
	}

	public boolean isTabletLayout() {
		int screenLayout = getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK;
		return screenLayout != Configuration.SCREENLAYOUT_SIZE_SMALL
				&& screenLayout != Configuration.SCREENLAYOUT_SIZE_NORMAL;
	}
}
