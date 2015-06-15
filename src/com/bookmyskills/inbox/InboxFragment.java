package com.bookmyskills.inbox;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.bookmyskills.R;
import com.customcontrols.dialog.CustomDialog;
import com.customcontrols.floatingactionbutton.FloatingActionButton;
import com.customcontrols.swipemenu.SwipeMenu;
import com.customcontrols.swipemenu.SwipeMenuCreator;
import com.customcontrols.swipemenu.SwipeMenuItem;
import com.customcontrols.swipemenu.SwipeMenuListView;
import com.customcontrols.swipemenu.SwipeMenuListView.OnMenuItemClickListener;
import com.customcontrols.swipemenu.SwipeMenuListView.OnSwipeListener;
import com.models.UserSearchModel;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.misc.DataItem;
import com.utils.misc.DataSource;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class InboxFragment extends android.support.v4.app.Fragment implements
		IParseListener {

	View rootView;
	SwipeRefreshLayout swipeLayout;
	private List<ApplicationInfo> mAppList;
	private DataSource mDataSource;
	private SampleAdapter mAdapter;
	private SwipeMenuListView mListView;
	private Dialog dialog;
	private StorageClass mStorageClass;

	public InboxFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
		mStorageClass = StorageClass.getInstance(getActivity());
		swipeLayout = (SwipeRefreshLayout) rootView
				.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		swipeLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						swipeLayout.setRefreshing(true);
						(new Handler()).postDelayed(new Runnable() {
							@Override
							public void run() {
								swipeLayout.setRefreshing(false);
								mAdapter.notifyDataSetChanged();
							}
						}, 5000);
					}
				});
		FloatingActionButton fab = (FloatingActionButton) rootView
				.findViewById(R.id.fab);
		inbox();
		fab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				navigateToComposeFragment();
			}

		});
		return rootView;
	}

	private void navigateToComposeFragment() {
		ComposeEmailFragment nextFrag = new ComposeEmailFragment();
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_container, nextFrag, "compose")
				.addToBackStack(null).commitAllowingStateLoss();
	}

	public void swipeList() {
		swipeLayout.setEnabled(false);
		ListView lView = (ListView) rootView.findViewById(R.id.guides_list);
		ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getActivity()
						.getResources().getStringArray(R.array.country_list));
		lView.setAdapter(adp);

		swipeLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						swipeLayout.setRefreshing(true);
						(new Handler()).postDelayed(new Runnable() {
							@Override
							public void run() {
								swipeLayout.setRefreshing(false);
								mAdapter.notifyDataSetChanged();
							}
						}, 5000);
					}
				});

		lView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int i) {

			}

			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0)
					swipeLayout.setEnabled(true);
				else
					swipeLayout.setEnabled(false);
			}
		});

	}

	public void inbox() {
		mAppList = getActivity().getPackageManager()
				.getInstalledApplications(0);

		mListView = (SwipeMenuListView) rootView.findViewById(R.id.listView);

		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int i) {

			}

			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0)
					swipeLayout.setEnabled(true);
				else
					swipeLayout.setEnabled(false);
			}
		});
		mAdapter = new SampleAdapter();
		// mListView.setAdapter(mAdapter);
		mDataSource = new DataSource(getActivity());
		mListView.setAdapter(mAdapter);

		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("Open");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity()
						.getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu,
					int index) {
				ApplicationInfo item = mAppList.get(position);
				switch (index) {
				case 0:
					// open
					open(item);
					break;
				case 1:
					// delete
					// delete(item);
					mAppList.remove(position);
					mAdapter.notifyDataSetChanged();
					break;
				}
				return false;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				MessageFragment nextFrag = new MessageFragment();
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.frame_container, nextFrag, "message")
						.addToBackStack(null).commitAllowingStateLoss();

			}
		});

		// set SwipeListener
		mListView.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});

		// other setting
		// listView.setCloseInterpolator(new BounceInterpolator());

		// test item long click
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity().getApplicationContext(),
						position + " long click", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	private void delete(ApplicationInfo item) {
		// delete app
		try {
			Intent intent = new Intent(Intent.ACTION_DELETE);
			intent.setData(Uri.fromParts("package", item.packageName, null));
			startActivity(intent);
		} catch (Exception e) {
		}
	}

	private void open(ApplicationInfo item) {
		// open app
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(item.packageName);
		List<ResolveInfo> resolveInfoList = getActivity().getPackageManager()
				.queryIntentActivities(resolveIntent, 0);
		if (resolveInfoList != null && resolveInfoList.size() > 0) {
			ResolveInfo resolveInfo = resolveInfoList.get(0);
			String activityPackageName = resolveInfo.activityInfo.packageName;
			String className = resolveInfo.activityInfo.name;

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ComponentName componentName = new ComponentName(
					activityPackageName, className);

			intent.setComponent(componentName);
			startActivity(intent);
		}
	}

	private void initInboxApi() {
		showProgress();

		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		params.putString("user_id", String.valueOf(mStorageClass.getUserId()));
		params.putString("token", mStorageClass.getToken());
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.INBOX, params),
				StaticInfo.INBOX_RESPONSE_CODE, this);

	}

	private void showProgress() {
		dialog = new Dialog(getActivity());
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

	@Override
	public void ErrorResponse(VolleyError error, int requestCode) {
		hideProgress();
	}

	@Override
	public void SuccessResponse(JSONObject response, int requestCode) {
		switch (requestCode) {
		case StaticInfo.INBOX_RESPONSE_CODE:
			responseForInboxApi(response);
			break;

		default:
			break;
		}
	}

	private void responseForInboxApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {

				} else {

					CustomDialog myDialog = new CustomDialog(getActivity());
					myDialog.createDialog("Unable to authenticate", "Reason : "
							+ response.getString("status"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	private static class ViewHolder {

		private ImageView imageView;

		private TextView textView;

		private ViewHolder(View view) {
			imageView = (ImageView) view.findViewById(R.id.imageView);
			textView = (TextView) view.findViewById(R.id.textView);
		}
	}

	private class SampleAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mDataSource.getCount();
		}

		@Override
		public DataItem getItem(int position) {
			return mDataSource.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.item_list_app, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			DataItem item = getItem(position);

			final Drawable drawable = item.getDrawable();
			holder.imageView.setImageDrawable(drawable);
			holder.textView.setText(item.getLabel());

			// if navigation is supported, show the ">" navigation icon
			if (item.getNavigationInfo() != DataSource.NO_NAVIGATION) {
				holder.textView.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getResources().getDrawable(
								R.drawable.ic_action_next_item), null);
			} else {
				holder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
						null, null, null);
			}

			// fix for animation not playing for some below 4.4 devices
			if (drawable instanceof AnimationDrawable) {
				holder.imageView.post(new Runnable() {
					@Override
					public void run() {
						((AnimationDrawable) drawable).stop();
						((AnimationDrawable) drawable).start();
					}
				});
			}

			return convertView;
		}
	}
}
