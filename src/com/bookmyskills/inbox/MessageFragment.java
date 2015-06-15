package com.bookmyskills.inbox;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookmyskills.R;

@SuppressLint("NewApi")
public class MessageFragment extends Fragment {

	public MessageFragment() {
		// Thread.setDefaultUncaughtExceptionHandler(new
		// UnCaughtException(getActivity()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_message, container,
				false);

		return rootView;
	}
}
