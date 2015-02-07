package com.bookmskills.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.bookmyskills.R;

@SuppressLint("NewApi")
public class RegisterFragment extends Fragment {

	public RegisterFragment() {
	}

	View rootView;
	private Button registerButton;
	private EditText userNameEditText;
	private EditText emailNameEditText;
	private EditText passwordEditText;

	private String userName;
	private String email;
	private String password;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getActivity().getActionBar().setTitle("Register");
		rootView = inflater.inflate(R.layout.fragment_register, container,
				false);

		registerButton = (Button) rootView.findViewById(R.id.registerButton);

		userNameEditText = (EditText) rootView
				.findViewById(R.id.userNameEditText);
		passwordEditText = (EditText) rootView
				.findViewById(R.id.passwordEditText);
		emailNameEditText = (EditText) rootView
				.findViewById(R.id.emailNameEditText);

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = userNameEditText.getText().toString().trim();
				email = emailNameEditText.getText().toString().trim();
				password = passwordEditText.getText().toString().trim();

				if (userName != null && !TextUtils.isEmpty(userName)) {
					if (email != null && !TextUtils.isEmpty(email)) {
						if (android.util.Patterns.EMAIL_ADDRESS.matcher(email)
								.matches()) {
							if (password != null
									&& !TextUtils.isEmpty(password)) {
								showDialog();
							} else {
								passwordEditText.setError("Enter Password");
								passwordEditText.requestFocus();
							}
						} else {
							emailNameEditText.setError("Invalid Email");
							emailNameEditText.requestFocus();
						}
					} else {
						emailNameEditText.setError("Enter Email");
						emailNameEditText.requestFocus();
					}

				} else {
					userNameEditText.setError("Enter User Name");
					userNameEditText.requestFocus();
				}
			}
		});
		return rootView;
	}

	public void showDialog() {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.progress_bar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();
	}
}
