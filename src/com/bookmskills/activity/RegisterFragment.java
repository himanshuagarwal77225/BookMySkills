package com.bookmskills.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bookmyskills.R;

@SuppressLint("NewApi")
public class RegisterFragment extends Fragment {

	public RegisterFragment() {
	}

	View rootView;
	private Button registerButton;
	private Button previousButton;
	private EditText userNameEditText;
	private EditText emailNameEditText;
	private EditText passwordEditText;
	private EditText cnfPasswordEditText;
	private RadioGroup radioTypeGroup;
	private RadioButton typeRadioButton;

	private String userName;
	private String email;
	private String password;
	private String cnfPassword;
	private String userType;
	private LinearLayout stage1LinearLayout;
	private LinearLayout stage2LinearLayout;
	private LinearLayout stage3LinearLayout;
	private boolean stage1 = false;
	private boolean stage2 = false;
	private boolean stage3 = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getActivity().getActionBar().setTitle("Register");
		rootView = inflater.inflate(R.layout.fragment_register, container,
				false);

		registerButton = (Button) rootView.findViewById(R.id.registerButton);
		previousButton = (Button) rootView.findViewById(R.id.previousButton);
		userNameEditText = (EditText) rootView
				.findViewById(R.id.userNameEditText);
		passwordEditText = (EditText) rootView
				.findViewById(R.id.passwordEditText);
		emailNameEditText = (EditText) rootView
				.findViewById(R.id.emailNameEditText);
		cnfPasswordEditText = (EditText) rootView
				.findViewById(R.id.cnfPasswordEditText);
		radioTypeGroup = (RadioGroup) rootView.findViewById(R.id.radioType);
		stage1LinearLayout = (LinearLayout) rootView
				.findViewById(R.id.stage1LinearLayout);
		stage2LinearLayout = (LinearLayout) rootView
				.findViewById(R.id.stage2LinearLayout);

		stage3LinearLayout = (LinearLayout) rootView
				.findViewById(R.id.stage3LinearLayout);

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = userNameEditText.getText().toString().trim();
				email = emailNameEditText.getText().toString().trim();
				password = passwordEditText.getText().toString().trim();
				cnfPassword = cnfPasswordEditText.getText().toString().trim();
				int selectedRadioID = radioTypeGroup.getCheckedRadioButtonId();
				typeRadioButton = (RadioButton) rootView
						.findViewById(selectedRadioID);
				userType = typeRadioButton.getText().toString().trim();

				if (userName != null && !TextUtils.isEmpty(userName)) {
					if (email != null && !TextUtils.isEmpty(email)) {
						if (android.util.Patterns.EMAIL_ADDRESS.matcher(email)
								.matches()) {
							if (password != null
									&& !TextUtils.isEmpty(password)) {
								if (cnfPassword != null
										&& !TextUtils.isEmpty(cnfPassword)) {
									if (password.equals(cnfPassword)) {
										if (userType != null
												&& !TextUtils.isEmpty(userType)) {
											showDialog();
											stage1 = true;
											stage1LinearLayout
													.setVisibility(View.GONE);
											stage2LinearLayout
													.setVisibility(View.VISIBLE);
											stage3LinearLayout
													.setVisibility(View.VISIBLE);
											previousButton
													.setVisibility(View.VISIBLE);
										} else {
											typeRadioButton
													.setError("Select Type");
											radioTypeGroup.requestFocus();
										}
									} else {
										cnfPasswordEditText
												.setError("Password Does't Match");
										cnfPasswordEditText.requestFocus();
									}
								} else {
									cnfPasswordEditText
											.setError("Enter Confirm Password");
									cnfPasswordEditText.requestFocus();
								}
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
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();
	}
}
