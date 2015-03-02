package com.bookmyskills.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bookmyskills.R;
import com.bookmyskills.library.Store_pref;

@SuppressLint("NewApi")
public class LoginFragment extends Fragment {

	public LoginFragment() {
	}

	View rootView;

	private Button loginButton;
	private Button registerButton;
	private EditText userNameEditText;
	private EditText passwordEditText;
    Store_pref mStore_pref;

	private String userName;
	private String password;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_login, container, false);
		getActivity().getActionBar().setTitle("Login");

		registerButton = (Button) rootView.findViewById(R.id.registerButton);
		loginButton = (Button) rootView.findViewById(R.id.loginButton);
		userNameEditText = (EditText) rootView
				.findViewById(R.id.userNameEditText);
		passwordEditText = (EditText) rootView
				.findViewById(R.id.passwordEditText);
		
		mStore_pref = new Store_pref(getActivity());
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment frag = new RegisterFragment();

				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, frag);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = userNameEditText.getText().toString().trim();
				password = passwordEditText.getText().toString().trim();

				if (userName != null && !TextUtils.isEmpty(userName)) {
					if (password != null && !TextUtils.isEmpty(password)) {
                        mStore_pref.set_user_name(userName);
                        //we need to refresh the drawer here instead of re-initializing the MainActivity
                        Intent i = new Intent(getActivity(),
                                MainActivity.class);
                        getActivity().startActivity(i);
					} else {
						passwordEditText.setError("Enter Password");
						passwordEditText.requestFocus();
					}

				} else {
					userNameEditText.setError("Enter User Name");
					userNameEditText.requestFocus();
				}
			}
		});

		return rootView;
	}

}
