package com.bookmyskills;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.bookmyskills.about.AboutFragment;
import com.bookmyskills.adapters.NavDrawerListAdapter;
import com.bookmyskills.home.HomeProfileFragment;
import com.bookmyskills.inbox.InboxFragment;
import com.bookmyskills.login.LoginFragment;
import com.bookmyskills.profiile.MyProfileFragment;
import com.bookmyskills.settings.SettingsFragment;
import com.models.NavDrawerModel;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity implements
		OnItemClickListener, IParseListener {

	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private NavDrawerListAdapter mAdapter;
	private boolean userLoggedIn;
	private CharSequence mTitle;
	private FragmentManager mFragmentManager;
	private StorageClass mStorageClass;
	private InternalApp mApplication;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (InternalApp) getApplication();

		setContentView(R.layout.activity_main);
		if (!mApplication.isTabletLayout()) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		MiscUtils.setStatusBarColor(this,
				getResources().getColor(R.color.app_color));
		mStorageClass = StorageClass.getInstance(this);
		userLoggedIn = mStorageClass.getUserLoggedIn();
		getAllReferences();
		setUpActionBar();
		setMenuAdapter();
		mFragmentManager = getSupportFragmentManager();
		if (savedInstanceState == null) {
			if (userLoggedIn)
				loadFragment(0);
			else
				loadFragment(0);
		}

	}

	private void setMenuAdapter() {
		mAdapter = new NavDrawerListAdapter(this);
		mAdapter.addItems(getNavItemsArray());
		mDrawerList.setAdapter(mAdapter);
		mDrawerList.setOnItemClickListener(this);
	}

	private ArrayList<NavDrawerModel> getNavItemsArray() {
		String[] navMenuTitles;
		if (userLoggedIn) {
			navMenuTitles = getResources().getStringArray(
					R.array.nav_drawer_items_logged_in);
			View header = LayoutInflater.from(this).inflate(
					R.layout.user_profile_header, null);
			TextView mTxtUserName = (TextView) header
					.findViewById(R.id.userName);
			ImageView mImageUser = (ImageView) header
					.findViewById(R.id.photoUserImageView);
			mTxtUserName.setText(mStorageClass.getFirstName() + " "
					+ mStorageClass.getLastName());
			mDrawerList.addHeaderView(header, mDrawerList, false);
		} else {
			navMenuTitles = getResources().getStringArray(
					R.array.nav_drawer_items_not_logged_in);
		}
		ArrayList<NavDrawerModel> mNavItems = new ArrayList<NavDrawerModel>();
		for (int i = 0; i < navMenuTitles.length; i++) {
			NavDrawerModel mModel = new NavDrawerModel();
			mModel.setTitle(navMenuTitles[i]);
			mModel.setCounterVisibility(false);
			mModel.setCount("0");
			mNavItems.add(mModel);
		}
		return mNavItems;
	}

	private void getAllReferences() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.navdrawer);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerOpened(android.view.View drawerView) {
			};

			public void onDrawerClosed(android.view.View drawerView) {
			};
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

	}

	private void setUpActionBar() {
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setBackgroundDrawable(new ColorDrawable(getResources()
				.getColor(R.color.app_color)));
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setIcon(R.drawable.ic_launcher);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		loadFragment(position);
	}

	@SuppressLint("NewApi")
	public void loadFragment(int position) {
		android.support.v4.app.Fragment fragment = null;
		if (!userLoggedIn) {
			switch (position) {
			case 0:
				clearStack();
				fragment = new HomeProfileFragment();
				break;
			case 1:
				clearStack();
				fragment = new LoginFragment();
				break;
			case 2:
				clearStack();
				fragment = new AboutFragment();
				break;
			default:
				fragment = new HomeProfileFragment();
				break;
			}
		} else {
			switch (position) {
			case 1:
				clearStack();
				fragment = new HomeProfileFragment();
				break;
			case 2:
				clearStack();
				fragment = new MyProfileFragment();
				Bundle mBundle = new Bundle();
				mBundle.putString("userId", String.valueOf(StorageClass
						.getInstance(this).getUserId()));
				fragment.setArguments(mBundle);
				break;
			case 3:
				clearStack();
				fragment = new InboxFragment();
				break;
			case 4:
				clearStack();
				fragment = new AboutFragment();
				break;
			case 5:
				clearStack();
				fragment = new SettingsFragment();
				break;
			case 6:
				initLogoutApi();
				break;
			default:
				clearStack();
				fragment = new HomeProfileFragment();
				break;
			}
		}
		if (fragment != null) {
			mFragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			if (userLoggedIn) {
				if (position == 0)
					setTitle(mAdapter.getItem(position).getTitle());
				else
					setTitle(mAdapter.getItem(position - 1).getTitle());
			} else {
				setTitle(mAdapter.getItem(position).getTitle());
			}
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
		}
	}

	private void initLogoutApi() {
		showProgress();
		Bundle mParams = new Bundle();
		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(this);
		mParams.putString("user_id", String.valueOf(mStorageClass.getUserId()));
		mParams.putString("token", mStorageClass.getToken());
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.LOGOUT, mParams),
				StaticInfo.LOGOUT_RESPONSE_CODE, this);

	}

	public void clearStack() {
		for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
			mFragmentManager.popBackStackImmediate();
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	public void onBackPressed() {

		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawers();
		} else {
			if (mFragmentManager.getBackStackEntryCount() != 0) {
				mFragmentManager.popBackStackImmediate();
			} else {
				new AlertDialog.Builder(this)
						.setTitle(R.string.exit_application)
						.setMessage(R.string.exit_application_message)
						.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										MainActivity.super.onBackPressed();
									}
								})
						.setNegativeButton(android.R.string.no,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								}).create().show();
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	public void showErrorAlertDialog(String message) {
		new AlertDialog.Builder(this)
				.setTitle("Error")
				.setMessage(message)
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						}).create().show();
	}

	@Override
	public void ErrorResponse(VolleyError error, int requestCode) {
		hideProgress();
	}

	@Override
	public void SuccessResponse(JSONObject response, int requestCode) {
		switch (requestCode) {
		case StaticInfo.LOGOUT_RESPONSE_CODE:
			resposneForLogoutApi(response);
			break;

		default:
			break;
		}
	}

	private void resposneForLogoutApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					String message = response.getString(StaticInfo.STATUS)
							.replaceAll("[0-9]", "");
					clearStack();
					StorageClass.getInstance(this).setUserLoggedIn(false);
					StorageClass.getInstance(this).clear();
					Intent intent = new Intent(this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} else {
					String message = response.getString(StaticInfo.STATUS)
							.replaceAll("[0-9]", "");
					showErrorAlertDialog(message);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void showProgress() {
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(R.layout.progress_bar);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();
	}

	private void hideProgress() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
