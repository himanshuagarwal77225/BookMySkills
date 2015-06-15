package com.bookmyskills.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookmyskills.R;

@SuppressLint("NewApi")
public class SettingsFragment extends android.support.v4.app.Fragment {

	View rootView;
	private ImageView hideMapImageView;
	private ImageView shiftMapImageView;
	private ImageView showMapImageView;
	private TextView locationMsgTextView;

	public SettingsFragment() {
		// Thread.setDefaultUncaughtExceptionHandler(new
		// UnCaughtException(getActivity()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);

		hideMapImageView = (ImageView) rootView
				.findViewById(R.id.hideMapImageView);
		shiftMapImageView = (ImageView) rootView
				.findViewById(R.id.shiftMapImageView);
		showMapImageView = (ImageView) rootView
				.findViewById(R.id.showMapImageView);
		locationMsgTextView = (TextView) rootView
				.findViewById(R.id.locationMsgTextView);

		hideMapImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hideMapImageView.setImageResource(R.drawable.hide_map_selected);
				shiftMapImageView.setImageResource(R.drawable.shift_map);
				showMapImageView.setImageResource(R.drawable.show_map);
				locationMsgTextView
						.setText("Your location is not shown on map");
			}
		});

		shiftMapImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hideMapImageView.setImageResource(R.drawable.hide_map);
				shiftMapImageView
						.setImageResource(R.drawable.shift_map_selected);
				showMapImageView.setImageResource(R.drawable.show_map);
				locationMsgTextView
						.setText("Your location on map is moved approx 2-3km (1-2 mi) from your real location");
			}
		});

		showMapImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hideMapImageView.setImageResource(R.drawable.hide_map);
				shiftMapImageView.setImageResource(R.drawable.shift_map);
				showMapImageView.setImageResource(R.drawable.show_map_selected);
				locationMsgTextView
						.setText("Your real location is shown on map");
			}
		});

		return rootView;
	}
}
