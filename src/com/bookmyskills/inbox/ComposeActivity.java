//package com.bookmyskills.inbox;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity;
//
//import com.bookmyskills.R;
//
//public class ComposeActivity extends ActionBarActivity {
//
//	public static ComposeEmailFragment createFragment(Intent intent, Context ctx) {
//		return new ComposeEmailFragment();
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		ActionBar mActionBar = getSupportActionBar();
//		mActionBar.setDisplayHomeAsUpEnabled(true);
//		mActionBar.setHomeButtonEnabled(true);
//		mActionBar.setBackgroundDrawable(new ColorDrawable(getResources()
//				.getColor(R.color.app_color)));
//		mActionBar.setDisplayShowHomeEnabled(true);
//
//		if (savedInstanceState == null) {
//			Intent intent = getIntent();
//			if (intent != null) {
//				ComposeEmailFragment fragment = createFragment(intent, this);
//				if (fragment != null) {
//					getSupportFragmentManager().beginTransaction()
//							.add(android.R.id.content, fragment, "compose")
//							.addToBackStack(null).commitAllowingStateLoss();
//				} else {
//					finish();
//				}
//			} else {
//				finish();
//			}
//		}
//	}
//
// }
