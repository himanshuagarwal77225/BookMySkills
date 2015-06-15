package com.bookmyskills.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.bookmyskills.MainActivity;
import com.bookmyskills.R;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class LoginFragment extends android.support.v4.app.Fragment implements
		IParseListener {

	View rootView;
	StorageClass mStorageClass;
	private Button loginButton;
	private Button registerButton;
	private EditText userNameEditText;
	private EditText passwordEditText;
	private String userName;
	private String password;
	private Dialog dialog;

	public LoginFragment() {
		// Thread.setDefaultUncaughtExceptionHandler(new
		// UnCaughtException(getActivity()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_login, container, false);
		((MainActivity) getActivity()).setTitle("Login");

		registerButton = (Button) rootView.findViewById(R.id.registerButton);
		loginButton = (Button) rootView.findViewById(R.id.loginButton);
		userNameEditText = (EditText) rootView
				.findViewById(R.id.userNameEditText);
		passwordEditText = (EditText) rootView
				.findViewById(R.id.passwordEditText);

		mStorageClass = StorageClass.getInstance(getActivity());
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				android.support.v4.app.Fragment frag = new RegisterFragment();

				FragmentTransaction ft = getActivity()
						.getSupportFragmentManager().beginTransaction();
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

				if (userName.equalsIgnoreCase("")) {
					userNameEditText.setError("Enter username");
					userNameEditText.requestFocus();
				} else if (password.equalsIgnoreCase("")) {
					passwordEditText.setError("Enter Password");
					passwordEditText.requestFocus();
				} else {
					initiateLoginApi();
				}
				/*
				 * 
				 * if (userName != null && !TextUtils.isEmpty(userName)) { if
				 * (password != null && !TextUtils.isEmpty(password)) {
				 * mStore_pref.set_user_name(userName); //we need to refresh the
				 * drawer here instead of re-initializing the MainActivity //
				 * Intent i = new Intent(getActivity(), MainActivity.class);
				 * 
				 * Intent intent = getActivity().getIntent();
				 * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
				 * Intent.FLAG_ACTIVITY_NEW_TASK |
				 * Intent.FLAG_ACTIVITY_NO_ANIMATION);
				 * getActivity().overridePendingTransition(0, 0);
				 * getActivity().finish();
				 * 
				 * getActivity().overridePendingTransition(0, 0);
				 * startActivity(intent);
				 * 
				 * } else { passwordEditText.setError("Enter Password");
				 * passwordEditText.requestFocus(); }
				 * 
				 * } else { userNameEditText.setError("Enter User Name");
				 * userNameEditText.requestFocus(); }
				 */
			}
		});

		((TextView) rootView.findViewById(R.id.forgotPassword))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						showForgotPasswordDialog();
					}

				});

		return rootView;
	}

	private void showForgotPasswordDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setTitle("Forgot Password");
		alert.setMessage("Enter your email");
		final EditText email = new EditText(getActivity());
		email.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		email.setHint("Email.");
		email.setSingleLine();
		FrameLayout container = new FrameLayout(getActivity());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.leftMargin = 35;
		params.topMargin = 15;
		params.rightMargin = 35;
		email.setLayoutParams(params);
		container.addView(email);
		alert.setView(container);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String inputEmail = email.getText().toString().trim();
				if (inputEmail != null && !TextUtils.isEmpty(inputEmail)) {
					if (android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail)
							.matches()) {
						initForgotPasswordApi(inputEmail);
						dialog.dismiss();
					} else {
						showEmailEror("Invalid Email Id");
					}
				} else {
					showEmailEror("Invalid Email Id");
				}
			}

		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		alert.show();
	}

	private void showEmailEror(String message) {
		new AlertDialog.Builder(getActivity())
				.setTitle("Error")
				.setMessage(message)
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						}).create().show();
	}

	private void initiateLoginApi() {

		showProgress();

		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		params.putString("user", userName);
		params.putString("password", password);
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.LOGIN, params),
				StaticInfo.LOGIN_RESPONSE_CODE, this);
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

	private void initForgotPasswordApi(String inputEmail) {
		showProgress();

		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		params.putString("user", userName);
		params.putString("password", password);
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.RESET_PASSWORD, params),
				StaticInfo.RESET_PASSWORD_RESPONSE_CODE, this);

	}

	@Override
	public void SuccessResponse(JSONObject response, int requestCode) {
		switch (requestCode) {
		case StaticInfo.LOGIN_RESPONSE_CODE:
			responseForLoginApi(response);
			break;
		case StaticInfo.RESET_PASSWORD_RESPONSE_CODE:
			responseFoResetPasswordApi(response);
		default:
			break;
		}
	}

	private void responseFoResetPasswordApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					if (getActivity() != null
							&& getActivity() instanceof MainActivity) {
						String message = response.getString(StaticInfo.STATUS)
								.replaceAll("[0-9]", "");
						((MainActivity) getActivity())
								.showErrorAlertDialog(message);
					}

				} else {
					if (getActivity() != null
							&& getActivity() instanceof MainActivity) {
						String message = response.getString(StaticInfo.STATUS)
								.replaceAll("[0-9]", "");
						((MainActivity) getActivity())
								.showErrorAlertDialog(message);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void responseForLoginApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					JSONObject loginInfoObj = response.optJSONArray(
							StaticInfo.LOGIN).optJSONObject(0);
					if (loginInfoObj != null) {
						String userId = loginInfoObj.optString(StaticInfo.ID);
						String userToken = loginInfoObj
								.optString(StaticInfo.TOKEN);
						String firstName = loginInfoObj
								.optString(StaticInfo.FIRST_NAME);
						String lastName = loginInfoObj
								.optString(StaticInfo.LAST_NAME);
						mStorageClass.setUserId(Integer.parseInt(userId));
						mStorageClass.setUserLoggedIn(true);
						mStorageClass.setFirstName(firstName);
						mStorageClass.setLastName(lastName);
						mStorageClass.setToken(userToken);
						Intent intent = getActivity().getIntent();
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_NO_ANIMATION);
						getActivity().overridePendingTransition(0, 0);
						getActivity().finish();

						getActivity().overridePendingTransition(0, 0);
						startActivity(intent);
					}
				} else {
					if (getActivity() != null
							&& getActivity() instanceof MainActivity) {
						String message = response.getString(StaticInfo.STATUS)
								.replaceAll("[0-9]", "");
						((MainActivity) getActivity())
								.showErrorAlertDialog(message);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}