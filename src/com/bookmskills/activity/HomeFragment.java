package com.bookmskills.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bookmyskills.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

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
				}
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
}
