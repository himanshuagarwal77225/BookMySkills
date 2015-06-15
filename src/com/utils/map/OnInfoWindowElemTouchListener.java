package com.utils.map;

import java.util.LinkedHashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.google.android.gms.maps.model.Marker;

public abstract class OnInfoWindowElemTouchListener implements OnTouchListener {
	private final View view;
	Drawable bgDrawableNormal;
	Drawable bgDrawablePressed;
	private final Handler handler = new Handler();

	private Marker marker;
	private boolean pressed = false;

	// public OnInfoWindowElemTouchListener(View view, Drawable
	// bgDrawableNormal,
	// Drawable bgDrawablePressed) {
	// this.view = view;
	// // this.view.setPressed(true);
	// this.bgDrawableNormal = bgDrawableNormal;
	// this.bgDrawablePressed = bgDrawablePressed;
	// }

	public OnInfoWindowElemTouchListener(View view, Marker marker,
			LinkedHashMap<String, String> mData) {
		this.marker = marker;
		this.view = view;
		this.mData = mData;
	}

	public OnInfoWindowElemTouchListener(View view, Marker marker) {
		this.marker = marker;
		this.view = view;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	@Override
	public boolean onTouch(View vv, MotionEvent event) {
		if (0 <= event.getX() && event.getX() <= view.getWidth()
				&& 0 <= event.getY() && event.getY() <= view.getHeight()) {
			switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				startPress();
				break;
			case MotionEvent.ACTION_UP:
				handler.postDelayed(confirmClickRunnable, 150);
				break;
			case MotionEvent.ACTION_CANCEL:
				endPress();
				break;
			default:
				break;
			}
		} else {
			// If the touch goes outside of the view's area
			// (like when moving finger out of the pressed button)
			// just release the press
			endPress();
		}
		return false;
	}

	private void startPress() {
		if (!pressed) {
			pressed = true;
			handler.removeCallbacks(confirmClickRunnable);
			// TODO
			// view.setBackgroundDrawable(bgDrawablePressed);
			// if (marker != null)
			// marker.showInfoWindow();

		}
	}

	private boolean endPress() {
		if (pressed) {
			this.pressed = false;
			handler.removeCallbacks(confirmClickRunnable);
			// view.setBackgroundDrawable(bgDrawableNormal);
			// if (marker != null)
			// marker.showInfoWindow();
			return true;
		} else
			return false;
	}

	private final Runnable confirmClickRunnable = new Runnable() {
		public void run() {
			if (endPress()) {
				if (marker != null)
					marker.hideInfoWindow();
				onClickConfirmed(view, marker, mData);
			}
		}
	};

	public LinkedHashMap<String, String> mData;

	/**
	 * This is called after a successful click
	 */
	// public abstract void onClickConfirmed(View v, Marker marker);

	public abstract void onClickConfirmed(View v, Marker marker,
			LinkedHashMap<String, String> mData);
}