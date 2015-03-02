package com.bookmyskills.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.bookmyskills.R;
import com.bookmyskills.library.CustomTimerTaskMapMarker;
import com.bookmyskills.model.SkillsModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements OnMapLoadedCallback,
		OnMarkerClickListener, OnMarkerDragListener, SensorEventListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	public HomeFragment() {
	}

	private static View view;
	private FragmentActivity myContext;
	MapFragment mMapFragment;
	GoogleMap map;
	public static LocationClient mLocationClient;
	Location mCurrentLocation;
	double latitude;
	double longitude;

	private AutoCompleteTextView searchEditText;
	private ArrayAdapter<String> adapter;

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
			mLocationClient = new LocationClient(getActivity(), this, this);
			initilizeMap();

			searchEditText = (AutoCompleteTextView) view
					.findViewById(R.id.searchEditText);
			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, getSkills());
			searchEditText.setAdapter(adapter);
			searchEditText.setThreshold(2);

		} catch (InflateException e) {
			/* map is already there, just return view as it is */
			e.printStackTrace();
		}

		return view;
	}

	private void initilizeMap() {
		if (map == null) {
			map = ((SupportMapFragment) myContext.getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// mMapFragment = MapFragment.newInstance();
			// FragmentTransaction fragmentTransaction = getFragmentManager()
			// .beginTransaction();
			// fragmentTransaction.add(R.id.frame_container, mMapFragment);
			// fragmentTransaction.commit();
			// map = mMapFragment.getMap();

			// check if map is created successfully or not
			if (map == null) {
				Toast.makeText(getActivity(), "Sorry! unable to create maps",
						Toast.LENGTH_SHORT).show();
			} else {
				map.setMyLocationEnabled(true);
				map.getUiSettings().setCompassEnabled(true);
				map.getUiSettings().setMyLocationButtonEnabled(true);
				map.getUiSettings().setRotateGesturesEnabled(true);
				if (mLocationClient.isConnected()) {
					mCurrentLocation = mLocationClient.getLastLocation();

					latitude = mCurrentLocation.getLatitude();// googleMap.getMyLocation().getLatitude();
					longitude = mCurrentLocation.getLongitude();
					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(latitude, longitude)).zoom(15)
							.build();

					map.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
					addMarker();
					infoWindow();
				}
				map.addMarker(new MarkerOptions().position(new LatLng(10, 10))
						.title("Hello world"));
				addMarker();
				infoWindow();

			}
		}
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
	public void onMapLoaded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		super.onResume();
		mLocationClient = new LocationClient(getActivity(), this, this);
		initilizeMap();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnected() {
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
	public void onStart() {
		super.onStart();
		// Connect the client.
		mLocationClient.connect();
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	public void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	@Override
	public void onDestroyView() {

		Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.map);
		if (f != null) {
			getFragmentManager().beginTransaction().remove(f).commit();
		}

		if (!getActivity().isFinishing()) {
			if (f != null) {
				getFragmentManager().beginTransaction().remove(f).commit();
			}
		}

		super.onDestroyView();
	}

	public void addMarker() {
		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {
				// Clears any existing markers from the GoogleMap
				map.clear();

				// Creating an instance of MarkerOptions to set position
				MarkerOptions markerOptions = new MarkerOptions();

				// Setting position on the MarkerOptions
				markerOptions.position(arg0);
				markerOptions.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker1));
				makeToast(arg0 + "");

				// Animating to the currently touched position
				map.animateCamera(CameraUpdateFactory.newLatLng(arg0));

				// Adding marker on the GoogleMap
				Marker marker = map.addMarker(markerOptions);

				// Showing InfoWindow on the GoogleMap
				marker.showInfoWindow();
				// setMarkerBounce(marker);
				bounseMarker(arg0, marker);
			}
		});
	}

	public void infoWindow() {
		map.setInfoWindowAdapter(new InfoWindowAdapter() {

			// Use default InfoWindow frame
			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			// Defines the contents of the InfoWindow
			@Override
			public View getInfoContents(Marker arg0) {

				// Getting view from the layout file info_window_layout
				View v = getActivity().getLayoutInflater().inflate(
						R.layout.map_info_window, null);

				// Getting the position from the marker
				LatLng latLng = arg0.getPosition();

				// Getting reference to the TextView to set latitude
				TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);

				// Getting reference to the TextView to set longitude
				TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

				// Setting the latitude
				tvLat.setText("Latitude:" + latLng.latitude);

				// Setting the longitude
				tvLng.setText("Longitude:" + latLng.longitude);
				makeToast("Latitude:" + latLng.latitude + " " + "Longitude:"
						+ latLng.longitude);

				// Returning the view containing InfoWindow contents
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

	public void bounseMarker(LatLng latLng, Marker marker) {
		Timer timer = new Timer();
		TimerTask updateProfile = new CustomTimerTaskMapMarker(getActivity(),
				marker);
		timer.scheduleAtFixedRate(updateProfile, 1, 5000);

		// map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
	}

	public String[] getSkills() {
		if (SplashActivity.skillsSet != null
				&& SplashActivity.skillsSet.size() > 0) {
			String skills[] = new String[SplashActivity.skillsSet.size()];
			for (int i = 0; i < SplashActivity.skillsSet.size(); i++) {
				SkillsModel skill = SplashActivity.skillsSet.get(i);
				skills[i] = skill.getName();
			}
			return skills;
		} else {
			return null;
		}

	}
}
