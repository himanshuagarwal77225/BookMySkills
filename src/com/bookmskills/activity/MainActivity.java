package com.bookmskills.activity;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bookmyskills.R;
import com.bookmyskills.adapter.NavDrawerListAdapter;
import com.bookmyskills.model.NavDrawerItem;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

public class MainActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	public static String reg_account;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setDisplayShowHomeEnabled(true);
		ab.setIcon(R.drawable.ic_launcher);

		if (getAccount() != null) {
			reg_account = getAccount();
		}

		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.navdrawer);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				drawerArrow, R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	@SuppressLint("NewApi")
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new LoginFragment();
			break;
		case 2:
			fragment = new SearchFragment();
			break;
		case 3:
			fragment = new PrivacyPolicyFragment();
			break;
		case 4:
			fragment = new TermsConditionFragment();
			break;
		case 5:
			fragment = new ContactUsFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	public void onBackPressed() {

		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			super.onBackPressed();
		}
	}

	public String getAccount() {
		AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
		Account[] list = manager.getAccountsByType("com.google");
		if (list.length != 0) {
			String email = list[0].name;
			return email;
		} else {
			return null;
		}
	}
	
	
	
	
	// Storage for camera image URI components
		private final static String CAPTURED_PHOTO_PATH_KEY = "mCurrentPhotoPath";
		private final static String CAPTURED_PHOTO_URI_KEY = "mCapturedImageURI";
		// Required for camera operations in order to save the image file on resume.
		private String mCurrentPhotoPath = null;
		private Uri mCapturedImageURI = null;

		@Override
		public void onSaveInstanceState(Bundle savedInstanceState) {
			if (mCurrentPhotoPath != null) {
				savedInstanceState.putString(CAPTURED_PHOTO_PATH_KEY,
						mCurrentPhotoPath);
			}
			if (mCapturedImageURI != null) {
				savedInstanceState.putString(CAPTURED_PHOTO_URI_KEY,
						mCapturedImageURI.toString());
			}
			super.onSaveInstanceState(savedInstanceState);
		}

		@Override
		protected void onRestoreInstanceState(Bundle savedInstanceState) {
			if (savedInstanceState.containsKey(CAPTURED_PHOTO_PATH_KEY)) {
				mCurrentPhotoPath = savedInstanceState
						.getString(CAPTURED_PHOTO_PATH_KEY);
			}
			if (savedInstanceState.containsKey(CAPTURED_PHOTO_URI_KEY)) {
				mCapturedImageURI = Uri.parse(savedInstanceState
						.getString(CAPTURED_PHOTO_URI_KEY));
			}
			super.onRestoreInstanceState(savedInstanceState);
		}

		/**
		 * Getters and setters.
		 */
		public String getCurrentPhotoPath() {
			return mCurrentPhotoPath;
		}

		public void setCurrentPhotoPath(String mCurrentPhotoPath) {
			this.mCurrentPhotoPath = mCurrentPhotoPath;
		}

		public Uri getCapturedImageURI() {
			return mCapturedImageURI;
		}

		public void setCapturedImageURI(Uri mCapturedImageURI) {
			this.mCapturedImageURI = mCapturedImageURI;
		}

}
