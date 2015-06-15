package com.bookmyskills.home;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.bookmyskills.R;
import com.customcontrols.dialog.CustomDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
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
import com.utils.misc.CustomTimerTaskMapMarker;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements OnMapLoadedCallback,
		OnMarkerClickListener, OnMarkerDragListener, SensorEventListener,
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, LocationListener,
		OnMapReadyCallback, com.google.android.gms.location.LocationListener,
		IParseListener {

	// LogCat tag
	private static final String TAG = HomeFragment.class.getSimpleName();
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
	private static View view;
	// Location updates intervals in sec
	private static int UPDATE_INTERVAL = 10000; // 10 sec
	private static int FATEST_INTERVAL = 5000; // 5 sec
	private static int DISPLACEMENT = 10; // 10 meters
	SupportMapFragment mMapFragment;
	GoogleMap map;
	// public static LocationClient mLocationClient;
	Location mCurrentLocation;
	double latitude = 28.5700; // default noida
	double longitude = 77.3200; // default noida
	private FragmentActivity myContext;
	private AutoCompleteTextView searchEditText;
	private ArrayAdapter<String> adapter;
	private Location mLastLocation;
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
	// boolean flag to toggle periodic location updates
	private boolean mRequestingLocationUpdates = false;
	private LocationRequest mLocationRequest;
	private Dialog dialog;

	private StorageClass mStorageClass;

	private SupportMapFragment mapFrag;
	private ArrayList<UserSearchModel> mArrayUsers;

	public HomeFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mStorageClass = StorageClass.getInstance(getActivity());

	}

	@Override
	public void onAttach(Activity activity) {
		myContext = (FragmentActivity) activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {

			view = inflater.inflate(R.layout.fragment_home, container, false);
			// First we need to check availability of play services
			if (checkPlayServices()) {

				// Building the GoogleApi client
				buildGoogleApiClient();

				createLocationRequest();
			}

			// mLocationClient = new LocationClient(getActivity(), this, this);
			displayLocation();
			initializeMap();

			searchEditText = (AutoCompleteTextView) view
					.findViewById(R.id.searchEditText);
			String[] allSkills = getSkills();
			if (allSkills != null) {
				adapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1, allSkills);
				searchEditText.setAdapter(adapter);
				searchEditText.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {

					}
				});
				searchEditText.setThreshold(2);
			}
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
			e.printStackTrace();
		}

		initSearchApi();

		return view;

	}

	private void initializeMap() {
		mapFrag = ((SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map));
		mapFrag.getMapAsync(this);
	}

	public void updateCamera(float bearing) {
		CameraPosition currentPlace = new CameraPosition.Builder()
				.target(new LatLng(map.getMyLocation().getLatitude(), map
						.getMyLocation().getLongitude())).bearing(bearing)
				.tilt(65.5f).zoom(18f).build();
		map.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
		map.animateCamera(CameraUpdateFactory.newCameraPosition(currentPlace));

	}

	@Override
	public void onMapReady(GoogleMap gMap) {
		this.map = gMap;

		if (map == null) {
			Toast.makeText(getActivity(), "Sorry! unable to create maps",
					Toast.LENGTH_SHORT).show();
		} else {
			map.setMyLocationEnabled(true);
			map.getUiSettings().setCompassEnabled(true);
			map.getUiSettings().setMyLocationButtonEnabled(true);
			map.getUiSettings().setRotateGesturesEnabled(true);
			centerMapOnMyLocation();
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(latitude, longitude)).zoom(15).build();

			map.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
			// addMarker();
			// infoWindow();
			// }
			// map.addMarker(new MarkerOptions().position(
			// new LatLng(latitude, longitude)).title("Hello world"));
			// addMarker();
			// infoWindow();

		}
	}

	private void loadAllMarkers() {
		map.clear();
		mArrayUsers = mStorageClass.getSearchUsersArray();
		if (mArrayUsers != null) {
			for (UserSearchModel mModel : mArrayUsers) {
				MarkerOptions markerOptions = new MarkerOptions();
				LatLng mLatLong = null;
				if (mModel.getLatitude() != null
						&& mModel.getLongitude() != null) {
					double lng = Double.parseDouble(mModel.getLongitude());
					double lat = Double.parseDouble(mModel.getLatitude());
					mLatLong = new LatLng(lat, lng);
				}
				if (mLatLong != null) {
					markerOptions.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.marker1));
					Marker marker = map.addMarker(markerOptions);
					marker.setTitle("" + mModel.getId());
					marker.showInfoWindow();
					bounceMarker(mLatLong, marker);
				}
			}
		}
	}

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		updateCamera(90);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		mapFrag = ((SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map));

		if (this.mapFrag != null
				&& getFragmentManager().findFragmentById(this.mapFrag.getId()) != null) {

			getFragmentManager().beginTransaction().remove(this.mapFrag)
					.commit();
			this.mapFrag = null;
		}
	}

	public void infoWindow() {
		map.setInfoWindowAdapter(new InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			@Override
			public View getInfoContents(Marker arg0) {
				View v = getActivity().getLayoutInflater().inflate(
						R.layout.map_info_window, null);
				LatLng latLng = arg0.getPosition();
				TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
				TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
				tvLat.setText("Latitude:" + latLng.latitude);
				tvLng.setText("Longitude:" + latLng.longitude);
				makeToast("Latitude:" + latLng.latitude + " " + "Longitude:"
						+ latLng.longitude);
				return v;

			}
		});
	}

	public void makeToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	private void setMarkerBounce(final Marker marker) {
		final Handler handler = new Handler();
		final long startTime = SystemClock.uptimeMillis();
		final long duration = 2000;
		final Interpolator interpolator = new BounceInterpolator();
		handler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - startTime;
				float t = Math.max(
						1 - interpolator.getInterpolation((float) elapsed
								/ duration), 0);
				marker.setAnchor(0.5f, 1.0f + t);

				if (t > 0.0) {
					handler.postDelayed(this, 16);
				} else {
					setMarkerBounce(marker);
				}
			}
		});
	}

	public void bounceMarker(LatLng latLng, Marker marker) {
		Timer timer = new Timer();
		TimerTask updateProfile = new CustomTimerTaskMapMarker(getActivity(),
				marker);
		timer.scheduleAtFixedRate(updateProfile, 1, 5000);

		// map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
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
			return null;
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		checkPlayServices();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();

			// Resuming the periodic location updates
			if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
				startLocationUpdates();
				initializeMap();
			}
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		/*
		 * if (mGoogleApiClient.isConnected()) { mGoogleApiClient.disconnect();
		 * }
		 */
	}

	@Override
	public void onPause() {
		super.onPause();
		// stopLocationUpdates();
	}

	/**
	 * Method to display the location on UI
	 */
	private void displayLocation() {

		mLastLocation = LocationServices.FusedLocationApi
				.getLastLocation(mGoogleApiClient);

		if (mLastLocation != null) {
			double latitude = mLastLocation.getLatitude();
			double longitude = mLastLocation.getLongitude();

			this.latitude = latitude;
			this.longitude = longitude;

			// lblLocation.setText(latitude + ", " + longitude);

		} else {

			makeToast("(Couldn't get the location. Make sure location is enabled on the device)");
		}
	}

	/**
	 * Method to toggle periodic location updates
	 */
	private void togglePeriodicLocationUpdates() {
		if (!mRequestingLocationUpdates) {
			// Changing the button text
			// btnStartLocationUpdates.setText(getString(R.string.btn_stop_location_updates));

			mRequestingLocationUpdates = true;

			// Starting the location updates
			startLocationUpdates();

			Log.d(TAG, "Periodic location updates started!");

		} else {
			// Changing the button text
			// btnStartLocationUpdates.setText(getString(R.string.btn_start_location_updates));

			mRequestingLocationUpdates = false;

			// Stopping the location updates
			stopLocationUpdates();

			Log.d(TAG, "Periodic location updates stopped!");
		}
	}

	/**
	 * Creating google api client object
	 */
	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	/**
	 * Creating location request object
	 */
	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FATEST_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
	}

	/**
	 * Method to verify google play services on the device
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode,
						getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Toast.makeText(getActivity().getApplicationContext(),
						"This device is not supported.", Toast.LENGTH_LONG)
						.show();
				getActivity().finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Starting the location updates
	 */
	protected void startLocationUpdates() {

		LocationServices.FusedLocationApi.requestLocationUpdates(
				mGoogleApiClient, mLocationRequest, this);

	}

	/**
	 * Stopping location updates
	 */
	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(
				mGoogleApiClient, this);
	}

	/**
	 * Google api callback methods
	 */
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
				+ result.getErrorCode());
	}

	@Override
	public void onConnected(Bundle arg0) {

		// Once connected with google api, get the location
		displayLocation();

		if (mRequestingLocationUpdates) {
			startLocationUpdates();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onLocationChanged(Location location) {
		// Assign the new location
		mLastLocation = location;

		Toast.makeText(getActivity().getApplicationContext(),
				"Location changed!", Toast.LENGTH_SHORT).show();

		// Displaying the new location on UI
		displayLocation();
	}

	private void centerMapOnMyLocation() {

		map.setMyLocationEnabled(true);

		Location location = map.getMyLocation();

		LatLng myLocation = new LatLng(0, 0);
		if (location != null) {
			myLocation = new LatLng(location.getLatitude(),
					location.getLongitude());
		}
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13f));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	private void initSearchApi() {
		showProgress();

		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		params.putString("search", "Android");
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
							mModel.setSkill(mSearchObj
									.optString(StaticInfo.SKILL));
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
							mUserSearchArray.add(mModel);
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
			loadAllMarkers();
		}
	}

}
