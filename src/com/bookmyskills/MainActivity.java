package com.bookmyskills;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
import com.bookmyskills.home.HomeFragment;
import com.bookmyskills.home.HomeProfileFragment;
import com.bookmyskills.inbox.InboxFragment;
import com.bookmyskills.login.LoginFragment;
import com.bookmyskills.profiile.MyProfileFragment;
import com.bookmyskills.settings.SettingsFragment;
import com.models.NavDrawerModel;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.UnCaughtException;
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

	private SoftReference<HomeProfileFragment> refHomeProfileFragment;
	private SoftReference<LoginFragment> refLoginFragment;
	private SoftReference<AboutFragment> refAboutFragment;
	private SoftReference<MyProfileFragment> refMyProfileFragment;
	private SoftReference<InboxFragment> refInboxFragment;
	private SoftReference<SettingsFragment> refSettingsFragment;

	private int currentFragment = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (InternalApp) getApplication();

		setContentView(R.layout.activity_main);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(
				MainActivity.this));
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
		FragmentTransaction mFragTransaction = mFragmentManager
				.beginTransaction();
		if (!userLoggedIn) {
			switch (position) {
			case 0:
				clearStack();
				if (refHomeProfileFragment == null
						|| refHomeProfileFragment.get() == null) {
					currentFragment = 0;
					refHomeProfileFragment = new SoftReference<>(
							new HomeProfileFragment());
					mFragTransaction.replace(R.id.frame_container,
							refHomeProfileFragment.get(), "Home");
				} else {
					if (currentFragment == 0) {

					} else {
						currentFragment = 0;
						mFragTransaction.replace(R.id.frame_container,
								refHomeProfileFragment.get(), "Home");
					}
				}
				break;
			case 1:
				clearStack();
				if (refLoginFragment == null || refLoginFragment.get() == null) {
					currentFragment = 1;
					refLoginFragment = new SoftReference<>(new LoginFragment());
				}
				mFragTransaction.replace(R.id.frame_container,
						refLoginFragment.get(), "Login");
				break;
			case 2:
				clearStack();
				if (refAboutFragment == null || refAboutFragment.get() == null) {
					currentFragment = 2;
					refAboutFragment = new SoftReference<>(new AboutFragment());
				}
				mFragTransaction.replace(R.id.frame_container,
						refAboutFragment.get(), "About");
				break;
			default:
				if (refHomeProfileFragment == null
						|| refHomeProfileFragment.get() == null) {
					currentFragment = 0;
					refHomeProfileFragment = new SoftReference<>(
							new HomeProfileFragment());
				} else {
					currentFragment = 0;
					mFragTransaction.replace(R.id.frame_container,
							refHomeProfileFragment.get(), "Home");
				}
				break;
			}
		} else {
			switch (position) {
			case 1:
				// clearStack();
				if (refHomeProfileFragment == null
						|| refHomeProfileFragment.get() == null) {
					currentFragment = 0;
					refHomeProfileFragment = new SoftReference<>(
							new HomeProfileFragment());
					mFragTransaction.replace(R.id.frame_container,
							refHomeProfileFragment.get(), "Home");
				} else {
					if (currentFragment == 0) {

					} else {
						currentFragment = 0;
						mFragTransaction.replace(R.id.frame_container,
								refHomeProfileFragment.get(), "Home");
					}
				}
				break;
			case 2:
				clearStack();

				if (refMyProfileFragment == null
						|| refMyProfileFragment.get() == null) {
					MyProfileFragment mProfile = new MyProfileFragment();
					Bundle mBundle = new Bundle();
					mBundle.putString("userId", String.valueOf(StorageClass
							.getInstance(this).getUserId()));
					mProfile.setArguments(mBundle);
					refMyProfileFragment = new SoftReference<>(mProfile);
				}
				mFragTransaction.replace(R.id.frame_container,
						refMyProfileFragment.get(), "My Profile");
				currentFragment = 1;
				break;
			case 3:
				clearStack();
				if (refInboxFragment == null || refInboxFragment.get() == null) {
					refInboxFragment = new SoftReference<>(new InboxFragment());
				}
				mFragTransaction.replace(R.id.frame_container,
						refInboxFragment.get(), "Inbox");
				currentFragment = 2;
				break;
			case 4:
				clearStack();
				if (refAboutFragment == null || refAboutFragment.get() == null) {
					refAboutFragment = new SoftReference<>(new AboutFragment());
				}
				mFragTransaction.replace(R.id.frame_container,
						refAboutFragment.get(), "About");
				currentFragment = 3;
				break;
			case 5:
				clearStack();
				if (refSettingsFragment == null
						|| refSettingsFragment.get() == null) {
					refSettingsFragment = new SoftReference<>(
							new SettingsFragment());
				}
				mFragTransaction.replace(R.id.frame_container,
						refSettingsFragment.get(), "Settings");
				currentFragment = 4;
				break;
			case 6:
				initLogoutApi();
				break;
			default:
				clearStack();
				if (refHomeProfileFragment == null
						|| refHomeProfileFragment.get() == null) {
					refHomeProfileFragment = new SoftReference<>(
							new HomeProfileFragment());
				}
				mFragTransaction.replace(R.id.frame_container,
						refHomeProfileFragment.get(), "Home");
				break;
			}
		}
		mFragTransaction.commit();
		mDrawerLayout.closeDrawers();

		// if (fragment != null) {
		// mFragmentManager.beginTransaction()
		// .replace(R.id.frame_container, fragment).commit();
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
		// } else {
		// }
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

	// @Override
	// public void onBackPressed() {
	//
	// if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
	// mDrawerLayout.closeDrawers();
	// } else {
	// if (mFragmentManager.getBackStackEntryCount() != 0) {
	// mFragmentManager.popBackStackImmediate();
	// } else {
	//
	// // MaterialDialog.Builder materialDialogBuilder = new
	// // MaterialDialog.Builder(
	// // this);
	// // materialDialogBuilder.positiveText("Yes");
	// // materialDialogBuilder.negativeText("No");
	// // materialDialogBuilder.title(R.string.exit_application_message);
	// // materialDialogBuilder
	// // .callback(new MaterialDialog.ButtonCallback() {
	// // @Override
	// // public void onPositive(MaterialDialog dialog) {
	// // super.onPositive(dialog);
	// // MainActivity.super.onBackPressed();
	// // }
	// // });
	// // materialDialogBuilder.show();
	//
	// new AlertDialog.Builder(this)
	// .setTitle(R.string.exit_application)
	// .setMessage(R.string.exit_application_message)
	// .setPositiveButton(android.R.string.yes,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// dialog.cancel();
	//
	// }
	// })
	// .setNegativeButton(android.R.string.no,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// dialog.cancel();
	// }
	// }).create().show();
	// }
	// }
	// }

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

	public void showSuccessAlertDialog(String message) {

		new AlertDialog.Builder(this)
				.setTitle("BookMySkills")
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
					if (message.equalsIgnoreCase(" Autentication Failed")) {
						clearStack();
						StorageClass.getInstance(this).setUserLoggedIn(false);
						StorageClass.getInstance(this).clear();
						Intent intent = new Intent(this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						loadFragment(1);
					}
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

	public void loadChildFragment(Fragment fragment, Bundle args) {
		mDrawerLayout.closeDrawer(mDrawerList);
		if (fragment != null) {
			if (args != null)
				fragment.setArguments(args);
			mFragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment, "main")
					.addToBackStack("main").commit();
			hideKeyboard(MainActivity.this);
		}
	}

	public void hideKeyboard(Activity activity) {

		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}

	// @Override
	// public void onBackPressed() {
	// String fr = get_FragmentTag();
	//
	// Log.i("fragment Count ", mFragmentManager.getBackStackEntryCount()
	// + "==");
	//
	// if (fr != null) {
	// if (fr.equals(HomeFragment.class.getName())) {
	// super.onBackPressed();
	// }
	// // else if (fr.equals(Home2Fragment.class.getName())) {
	// // Log.i("Call  ", "fr.equals(main)");
	// // } else if (mFragmentManager.getBackStackEntryCount() == 1) {
	// // Log.i("Call  ", "fr.equals(main)");
	// // }
	// else {
	// super.onBackPressed();
	// }
	// } else {
	// super.onBackPressed();
	// }
	// }

	private String get_FragmentTag() {
		if (mFragmentManager.getBackStackEntryCount() == 0) {
			return null;
		}
		String tag = mFragmentManager.getBackStackEntryAt(
				mFragmentManager.getBackStackEntryCount() - 1).getName();
		return tag;
	}

	private final static String CAPTURED_PHOTO_PATH_KEY = "mCurrentPhotoPath";
	private final static String CAPTURED_PHOTO_URI_KEY = "mCapturedImageURI";
	// Required for camera operations in order to save the image file on resume.
	private String mCurrentPhotoPath = null;
	private Uri mCapturedImageURI = null;

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
