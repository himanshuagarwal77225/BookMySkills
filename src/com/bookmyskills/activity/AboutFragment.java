package com.bookmyskills.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.bookmyskills.R;

public class AboutFragment extends Fragment {

	// Mandatory Constructor
	public AboutFragment() {
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	private Button privacyPolicyButton;
	private Button termsNConditionButton;
	private Button contactUsButton;

	View rootView;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_about, container, false);

		privacyPolicyButton = (Button)rootView.findViewById(R.id.privacyPolicyButton);
		termsNConditionButton = (Button)rootView.findViewById(R.id.termsNConditionButton);
		contactUsButton = (Button)rootView.findViewById(R.id.contactUsButton);
		
		privacyPolicyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PrivacyPolicyFragment nextFrag= new PrivacyPolicyFragment();
			     getActivity().getFragmentManager().beginTransaction()
			     .replace(R.id.frame_container, nextFrag,"privacy")
			     .addToBackStack(null)
			     .commit();
			}
		});
		
		termsNConditionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TermsConditionFragment nextFrag= new TermsConditionFragment();
			     getActivity().getFragmentManager().beginTransaction()
			     .replace(R.id.frame_container, nextFrag,"TnC")
			     .addToBackStack(null)
			     .commit();
			     
			}
		});
		
		contactUsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContactUsFragment nextFrag= new ContactUsFragment();
				getActivity().getFragmentManager().beginTransaction()
			     .replace(R.id.frame_container, nextFrag,"contactUs")
			     .addToBackStack(null)
			     .commit();
			}
		});

		return rootView;
	}

}
