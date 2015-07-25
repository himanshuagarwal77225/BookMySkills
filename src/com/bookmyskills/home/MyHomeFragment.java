package com.bookmyskills.home;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.bookmyskills.MainActivity;
import com.bookmyskills.R;
import com.bookmyskills.profiile.ViewProfileFragment;
import com.customcontrols.dialog.CustomDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.models.SkillsModel;
import com.models.UserSearchModel;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.map.MapWrapperLayout;
import com.utils.map.MySupportMapFragment;
import com.utils.map.OnInfoWindowElemTouchListener;
import com.utils.misc.CustomTimerTaskMapMarker;
//@SuppressLint("NewApi")
//public class MyHomeFragment extends Fragment implements IParseListener,
//		OnCameraChangeListener {
//
//	private StorageClass mStorageClass;
//	private ArrayList<UserSearchModel> mArrayUsers;
//	private View mRootView;
//	private Dialog dialog;
//	public static float SCALE = 1.5f;
//
//	private GoogleMap mapView;
//	private MapWrapperLayout mapWrapperLayout;
//	private LatLng currentLatlng = null;
//	private SupportMapFragment mapSupportFragment;
//
//	public MyHomeFragment() {
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		mStorageClass = StorageClass.getInstance(getActivity());
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		mRootView = LayoutInflater.from(getActivity()).inflate(
//				R.layout.fragment_my_home, container);
//		return mRootView;
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		initializeMap();
//	}
//
//	public void setMap(GoogleMap map) {
//		this.mapView = map;
//		mapView.getUiSettings().setMyLocationButtonEnabled(true);
//		mapView.getUiSettings().setCompassEnabled(true);
//		mapWrapperLayout = (MapWrapperLayout) mRootView
//				.findViewById(R.id.map_relative_layout);
//		mapWrapperLayout.init(getMap(), getPixelsFromDp(getActivity(), -20));
//		initMap();
//	}
//
//	public void initMap() {
//		mapView.setOnCameraChangeListener(this);
//	}
//
//	public GoogleMap getMap() {
//		return mapView;
//	}
//
//	public static int getPixelsFromDp(Context context, float dp) {
//		return (int) (dp * SCALE + 0.5f);
//	}
//
//	public void onLocatinChange(Location location) {
//		if (location != null && getMap() != null) {
//			currentLatlng = new LatLng(location.getLatitude(),
//					location.getLongitude());
//			animateToLocation(currentLatlng);
//		}
//	}
//
//	private void animateToLocation(LatLng mLatLng) {
//		CameraPosition.Builder builder = new CameraPosition.Builder();
//		builder.zoom(15);
//		builder.target(mLatLng);
//		getMap().getUiSettings().setZoomControlsEnabled(false);
//		getMap().moveCamera(
//				CameraUpdateFactory.newCameraPosition(builder.build()));
//
//	}
//
//	private void initializeMap() {
//		try {
//			MapsInitializer.initialize(getActivity());
//			if (getChildFragmentManager().findFragmentById(R.id.mapContainer) == null) {
//				mapSupportFragment = MySupportMapFragment
//						.newInstatnce(new Bundle());
//				mapSupportFragment.setTargetFragment(this, 100);
//				if (getArguments() != null)
//					mapSupportFragment.setArguments(getArguments());
//
//				getChildFragmentManager().beginTransaction()
//						.add(R.id.mapContainer, mapSupportFragment).commit();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public String[] getSkills() {
//
//		ArrayList<SkillsModel> skillSet = mStorageClass.getSkillsArray();
//
//		if (skillSet != null && skillSet.size() > 0) {
//			String skills[] = new String[skillSet.size()];
//			for (int i = 0; i < skillSet.size(); i++) {
//				SkillsModel skill = skillSet.get(i);
//				skills[i] = skill.getSkill();
//			}
//			return skills;
//		} else {
//			return null;
//		}
//
//	}
//
//	private void initSearchApi() {
//		showProgress();
//
//		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
//				getActivity());
//		Bundle params = new Bundle();
//		params.putString("search", "Android");
//		mJsonRequestResponse.getResponse(
//				MiscUtils.encodeUrl(WebUtils.GETSEARCH, params),
//				StaticInfo.SEARCH_RESPONSE_CODE, this);
//
//	}
//
//	private void showProgress() {
//		dialog = new Dialog(getActivity());
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setCancelable(false);
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.setContentView(R.layout.progress_bar);
//		dialog.getWindow().setBackgroundDrawable(
//				new ColorDrawable(android.graphics.Color.TRANSPARENT));
//		dialog.show();
//	}
//
//	private void hideProgress() {
//		if (dialog != null && dialog.isShowing()) {
//			dialog.dismiss();
//		}
//	}
//
//	@Override
//	public void ErrorResponse(VolleyError error, int requestCode) {
//		hideProgress();
//	}
//
//	@Override
//	public void SuccessResponse(JSONObject response, int requestCode) {
//		switch (requestCode) {
//		case StaticInfo.SEARCH_RESPONSE_CODE:
//			responseForSearchApi(response);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	private void responseForSearchApi(JSONObject response) {
//		hideProgress();
//		if (response != null) {
//			try {
//				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
//						StaticInfo.SUCCESS_STRING)) {
//					ArrayList<UserSearchModel> mUserSearchArray = new ArrayList<UserSearchModel>();
//					JSONArray mSearchArray = response
//							.optJSONArray(StaticInfo.SEARCH);
//					if (mSearchArray != null) {
//						for (int i = 0; i < mSearchArray.length(); i++) {
//							JSONObject mSearchObj = mSearchArray
//									.optJSONObject(i);
//							UserSearchModel mModel = new UserSearchModel();
//							mModel.setId(Integer.parseInt(mSearchObj
//									.optString(StaticInfo.ID)));
//							mModel.setFirstName(mSearchObj
//									.optString(StaticInfo.FIRST_NAME));
//							mModel.setLastName(mSearchObj
//									.optString(StaticInfo.LAST_NAME));
//							mModel.setEmail(mSearchObj
//									.optString(StaticInfo.EMAIL));
//							mModel.setUserType(mSearchObj
//									.optString(StaticInfo.USER_TYPE));
//							mModel.setSkill(mSearchObj
//									.optString(StaticInfo.SKILL));
//							mModel.setLatitude(mSearchObj
//									.optString(StaticInfo.LATITUDE));
//							mModel.setLatitude(mSearchObj
//									.optString(StaticInfo.LATITUDE));
//							mModel.setLongitude(mSearchObj
//									.optString(StaticInfo.LONGITUDE));
//							mModel.setAccuracy(mSearchObj
//									.optString(StaticInfo.ACCURACY));
//							mModel.setUserImage(mSearchObj
//									.optString(StaticInfo.IMAGE));
//							mModel.setContactMedium(mSearchObj
//									.optString(StaticInfo.PREFERRED_CONTACT_MEDIUM));
//							mUserSearchArray.add(mModel);
//						}
//					}
//					mStorageClass.setSearchUsersArray(mUserSearchArray);
//				} else {
//					CustomDialog myDialog = new CustomDialog(getActivity());
//					myDialog.createDialog("Unable to authenticate", "Reason : "
//							+ response.getString("status"));
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public void onCameraChange(CameraPosition position) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//		MapFragment mFragment = (MapFragment) getActivity()
//				.getFragmentManager().findFragmentById(R.id.mapContainer);
//		if (mFragment != null)
//			getActivity().getFragmentManager().beginTransaction()
//					.remove(mFragment).commit();
//	}
//
//}
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class MyHomeFragment extends Fragment implements OnCameraChangeListener,
		IParseListener, OnClickListener {
	private View mCurrentView;

	private GoogleMap mapView;
	private MapWrapperLayout mapWrapperLayout;
	private LatLng currentLatlng = null;
	private SupportMapFragment mapSupportFragment;
	public static float SCALE = 1.5f;
	private Dialog dialog;
	private StorageClass mStorageClass;
	private ArrayList<UserSearchModel> mArrayUsers;

	private boolean isAnimate = false;
	private AutoCompleteTextView searchEditText;
	private ArrayAdapter<String> adapter;

	public LinkedHashMap<Marker, UserSearchModel> markerMap = new LinkedHashMap<Marker, UserSearchModel>();
	public List<Marker> mArrayMarkers = new ArrayList<Marker>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mStorageClass = StorageClass.getInstance(getActivity());
		mCurrentView = inflater.inflate(R.layout.fragment_my_home, null);
		searchEditText = (AutoCompleteTextView) mCurrentView
				.findViewById(R.id.searchEditText);
		searchEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_GO) {
					initSearchApi(searchEditText.getText().toString());
					return true;
				} else {

				}
				return false;
			}
		});
		String[] allSkills = getSkills();
		if (allSkills != null) {
			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, allSkills);
			searchEditText.setAdapter(adapter);
			searchEditText.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					initSearchApi(searchEditText.getText().toString());
				}

			});
			searchEditText.setThreshold(2);
		}

		getLayoutReferences();
		setClickListeners();
		return mCurrentView;
	}

	private void setClickListeners() {

	}

	private void getLayoutReferences() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try {
			initializeMap();
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}
		setTitle();

	}

	private void setTitle() {
	}

	public void setMap(GoogleMap map) {
		this.mapView = map;
		mapView.getUiSettings().setMyLocationButtonEnabled(true);
		mapView.getUiSettings().setCompassEnabled(true);
		initSearchApi("");
		mapWrapperLayout = (MapWrapperLayout) mCurrentView
				.findViewById(R.id.map_relative_layout);
		mapWrapperLayout.init(getMap(), getPixelsFromDp(getActivity(), -20));
		initMap();
	}

	public void initMap() {
		mapView.setOnCameraChangeListener(this);
	}

	public GoogleMap getMap() {
		return mapView;
	}

	public static int getPixelsFromDp(Context context, float dp) {
		return (int) (dp * SCALE + 0.5f);
	}

	public void onLocatinChange(Location location) {
		if (location != null && getMap() != null) {
			currentLatlng = new LatLng(location.getLatitude(),
					location.getLongitude());
			animateToLocation(currentLatlng);
		}
	}

	private void animateToLocation(LatLng mLatLng) {
		CameraPosition.Builder builder = new CameraPosition.Builder();
		builder.zoom(getMap().getMaxZoomLevel());
		builder.target(mLatLng);
		getMap().getUiSettings().setZoomControlsEnabled(true);
		getMap().animateCamera(
				CameraUpdateFactory.newCameraPosition(builder.build()));
		setInfoWindowAdapter();

	}

	private void initializeMap() throws GooglePlayServicesNotAvailableException {
		try {
			MapsInitializer.initialize(getActivity());
			if (getActivity().getSupportFragmentManager().findFragmentById(
					R.id.mapContainer) == null) {
				mapSupportFragment = MySupportMapFragment
						.newInstatnce(new Bundle());
				mapSupportFragment.setTargetFragment(this, 100);
				if (getArguments() != null)
					mapSupportFragment.setArguments(getArguments());

				getActivity().getSupportFragmentManager().beginTransaction()
						.add(R.id.mapContainer, mapSupportFragment).commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (getActivity() != null) {
			Fragment mFragment = getActivity().getSupportFragmentManager()
					.findFragmentById(R.id.mapContainer);
			if (mFragment != null) {
				if (getActivity() != null
						&& getActivity().getSupportFragmentManager() != null) {
					List<Fragment> mFragmentsList = getActivity()
							.getSupportFragmentManager().getFragments();
					if (mFragmentsList.contains(mFragment)) {
						getActivity().getSupportFragmentManager()
								.beginTransaction().remove(mFragment).commit();
					}
				}
			}
		}
	}

	@Override
	public void onCameraChange(CameraPosition arg0) {

	}

	private void initSearchApi(String mSearchText) {
		showProgress();

		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		params.putString("search", mSearchText);
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.GETSEARCH, params),
				StaticInfo.SEARCH_RESPONSE_CODE, this);

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
		case StaticInfo.SEARCH_RESPONSE_CODE:
			responseForSearchApi(response);
			break;

		default:
			break;
		}
	}

	private void responseForSearchApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					ArrayList<UserSearchModel> mUserSearchArray = new ArrayList<UserSearchModel>();
					JSONArray mSearchArray = response
							.optJSONArray(StaticInfo.SEARCH);
					if (mSearchArray != null) {
						for (int i = 0; i < mSearchArray.length(); i++) {
							JSONObject mSearchObj = mSearchArray
									.optJSONObject(i);
							UserSearchModel mModel = new UserSearchModel();
							mModel.setId(Integer.parseInt(mSearchObj
									.optString(StaticInfo.ID)));
							mModel.setFirstName(mSearchObj
									.optString(StaticInfo.FIRST_NAME));
							mModel.setLastName(mSearchObj
									.optString(StaticInfo.LAST_NAME));
							mModel.setEmail(mSearchObj
									.optString(StaticInfo.EMAIL));
							mModel.setUserType(mSearchObj
									.optString(StaticInfo.USER_TYPE));
							String mSkills = mSearchObj
									.optString(StaticInfo.SKILL);
							String[] mSkillsArray = mSkills.split("/");
							if (mSkillsArray != null) {
								for (int k = 0; k < mSkillsArray.length; k++) {
									if (k == 0) {
										mModel.setSkill(mSkillsArray[0]);
									}
									if (k == 1) {
										mModel.setRating(mSkillsArray[1]
												.replace(",", ""));
									}
									if (k == 2) {
										mModel.setWorkingHour(mSkillsArray[2]
												.replace(",", ""));
									}
									if (k == 3) {
										mModel.setExperience(mSkillsArray[3]
												.replace(",", ""));
									}
								}
							}
							mModel.setLatitude(mSearchObj
									.optString(StaticInfo.LATITUDE));
							mModel.setLatitude(mSearchObj
									.optString(StaticInfo.LATITUDE));
							mModel.setLongitude(mSearchObj
									.optString(StaticInfo.LONGITUDE));
							mModel.setAccuracy(mSearchObj
									.optString(StaticInfo.ACCURACY));
							mModel.setUserImage(mSearchObj
									.optString(StaticInfo.IMAGE));
							mModel.setContactMedium(mSearchObj
									.optString(StaticInfo.PREFERRED_CONTACT_MEDIUM));
							if (mStorageClass.getUserId() == -1) {
								mUserSearchArray.add(mModel);
							} else {
								if (mModel.getId() == mStorageClass.getUserId()) {

								} else {
									mUserSearchArray.add(mModel);
								}
							}
						}
					}
					mStorageClass.setSearchUsersArray(mUserSearchArray);
				} else {
					CustomDialog myDialog = new CustomDialog(getActivity());
					myDialog.createDialog("Unable to authenticate", "Reason : "
							+ response.getString("status"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			loadAllPins();
		}
	}

	private void loadAllPins() {
		if (getMap() != null) {
			getMap().clear();
			mArrayUsers = mStorageClass.getSearchUsersArray();
			if (mArrayUsers != null) {
				for (UserSearchModel mModel : mArrayUsers) {
					if (mModel.getId() == StorageClass.getInstance(
							getActivity()).getUserId()) {

					} else {
						addPin(mModel);
					}
				}
			}
		}
	}

	InfoWindowAdapter mInfoWindowAdapter = new InfoWindowAdapter() {
		@Override
		public View getInfoWindow(Marker marker) {

			View mView = getUserInfoWindow(marker, markerMap.get(marker));
			mapWrapperLayout.setMarkerWithInfoWindow(marker, mView);
			return mView;

		}

		@Override
		public View getInfoContents(Marker marker) {

			return null;

		}
	};

	private void addPin(UserSearchModel mModel) {
		LatLng mLatLong = null;
		if (mModel.getLatitude() != null && mModel.getLongitude() != null) {
			if (TextUtils.isEmpty(mModel.getLatitude())
					|| mModel.getLongitude().isEmpty()) {
				return;
			}
			double lng = Double.parseDouble(mModel.getLongitude());
			double lat = Double.parseDouble(mModel.getLatitude());
			mLatLong = new LatLng(lat, lng);
		}
		if (mLatLong != null) {

			MarkerOptions mOptions = new MarkerOptions()
					.position(mLatLong)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.marker1))
					.title("" + mModel.getId());
			Marker marker = getMap().addMarker(mOptions);
			markerMap.put(marker, mModel);
			mArrayMarkers.add(marker);
			if (!isAnimate)
				animateToLocation(mLatLong);
			isAnimate = true;
		}

	}

	public View getUserInfoWindow(Marker marker, UserSearchModel mModel) {
		OnInfoWindowElemTouchListener infoButtonListener;
		View InfoWindow = (ViewGroup) getActivity().getLayoutInflater()
				.inflate(R.layout.info_window, null);
		RelativeLayout mLayout = (RelativeLayout) InfoWindow
				.findViewById(R.id.infoWindow);
		TextView mtxtTitle = (TextView) InfoWindow.findViewById(R.id.txtTitle);
		TextView mtxtSkills = (TextView) InfoWindow
				.findViewById(R.id.txtSkills);
		TextView mtxtRating = (TextView) InfoWindow
				.findViewById(R.id.txtRating);
		TextView mtxtWorkingHours = (TextView) InfoWindow
				.findViewById(R.id.txtWorkingHours);
		TextView mtxtExperience = (TextView) InfoWindow
				.findViewById(R.id.txtExperience);

		TextView mtxtContact = (TextView) InfoWindow
				.findViewById(R.id.txtContact);
		mtxtTitle.setText(mModel.getFirstName() + " " + mModel.getLastName());
		mtxtSkills.setText("Skills: " + " " + mModel.getSkill());
		mtxtRating.setText("Rating: " + " " + mModel.getRating());
		mtxtWorkingHours.setText("Working Hours: " + " "
				+ mModel.getWorkingHour());
		mtxtExperience.setText("Experience: " + " " + mModel.getExperience());
		mtxtContact.setText("Mobile: " + " " + mModel.getMobile());
		Timer timer = new Timer();
		TimerTask updateProfile = new CustomTimerTaskMapMarker(getActivity(),
				marker);
		timer.scheduleAtFixedRate(updateProfile, 1, 5000);
		mLayout.setOnClickListener(this);
		infoButtonListener = new OnInfoWindowElemTouchListener(mLayout, marker,
				null) {

			@Override
			public void onClickConfirmed(View v, Marker marker,
					UserSearchModel mModel) {
				if (mStorageClass.getUserLoggedIn()) {
					if (mModel == null) {
						mModel = markerMap.get(marker);
					}
					Bundle mBundle = new Bundle();
					mBundle.putString("userId", String.valueOf(mModel.getId()));
					ViewProfileFragment nextFrag = new ViewProfileFragment();
					nextFrag.setArguments(mBundle);
					getActivity().getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.frame_container, nextFrag, "message")
							.addToBackStack(null).commitAllowingStateLoss();
				} else {
					((MainActivity) getActivity()).loadFragment(1);
				}

			}
		};
		mLayout.setOnTouchListener(infoButtonListener);

		return InfoWindow;
	}

	public void setInfoWindowAdapter() {
		getMap().setInfoWindowAdapter(mInfoWindowAdapter);
	}

	public String[] getSkills() {

		ArrayList<SkillsModel> skillSet = mStorageClass.getSkillsArray();

		if (skillSet != null && skillSet.size() > 0) {
			String skills[] = new String[skillSet.size()];
			for (int i = 0; i < skillSet.size(); i++) {
				SkillsModel skill = skillSet.get(i);
				skills[i] = skill.getSkill();
			}
			return skills;
		} else {
			String[] mDummy = new String[0];
			return mDummy;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
