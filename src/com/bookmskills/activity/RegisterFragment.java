package com.bookmskills.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bookmyskills.R;

@SuppressLint("NewApi")
public class RegisterFragment extends Fragment {

	public RegisterFragment() {
	}

	private FragmentActivity myContext;
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
	private static int stage = 1;

	// Stage 2 varaiables
	private EditText educationEditText;
	private EditText certificationsEditText;
	private EditText skills1EditText;
	private EditText skills2EditText;
	private Button addMoreEducationButton;
	private Button addMoreCertificateButton;
	private Button addMoreSkillsButton;
	private String education;
	private String certifications;
	private String skills1;
	private String skills2;

	// Stage 3 Variables
	private EditText addressEditText;
	private EditText cityEditText;
	private EditText stateEditText;
	private EditText pincodeEditText;
	private EditText phnNumberEditText;
	private EditText dobEditText;
	private EditText workingHrsEditText;
	private EditText priceEditText;
	private Spinner citizenship;
	private CheckBox emaileCheckBox;
	private CheckBox phoneCheckBox;
	private CheckBox pushNotificationCheckBox;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String phoneNumber;
	private String dob;
	private String workingHrs;
	private String price;
	private String country;
	private String contactType;
	private int year;
	private int month;
	private int day;
	static final int DATE_PICKER_ID = 1111;
	
	@Override
	public void onAttach(Activity activity) {
		myContext = (FragmentActivity) activity;
		super.onAttach(activity);
	}

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

		// stage 2
		educationEditText = (EditText) rootView
				.findViewById(R.id.educationEditText);
		certificationsEditText = (EditText) rootView
				.findViewById(R.id.certificationsEditText);
		skills1EditText = (EditText) rootView
				.findViewById(R.id.skills1EditText);
		skills2EditText = (EditText) rootView
				.findViewById(R.id.skills2EditText);
		addMoreEducationButton = (Button) rootView
				.findViewById(R.id.addMoreEducationButton);
		addMoreCertificateButton = (Button) rootView
				.findViewById(R.id.addMoreCertificateButton);
		addMoreSkillsButton = (Button) rootView
				.findViewById(R.id.addMoreSkillsButton);

		// stage 3
		addressEditText = (EditText) rootView
				.findViewById(R.id.addressEditText);
		cityEditText = (EditText) rootView.findViewById(R.id.cityEditText);
		stateEditText = (EditText) rootView.findViewById(R.id.stateEditText);
		pincodeEditText = (EditText) rootView
				.findViewById(R.id.pincodeEditText);
		phnNumberEditText = (EditText) rootView
				.findViewById(R.id.phnNumberEditText);
		dobEditText = (EditText) rootView.findViewById(R.id.dobEditText);
		workingHrsEditText = (EditText) rootView
				.findViewById(R.id.workingHrsEditText);
		priceEditText = (EditText) rootView.findViewById(R.id.priceEditText);
		emaileCheckBox = (CheckBox) rootView.findViewById(R.id.emaileCheckBox);
		phoneCheckBox = (CheckBox) rootView.findViewById(R.id.phoneCheckBox);
		pushNotificationCheckBox = (CheckBox) rootView
				.findViewById(R.id.pushNotificationCheckBox);
		citizenship = (Spinner) rootView.findViewById(R.id.countryEditText);

		if (stage == 1) {
			stage1LinearLayout.setVisibility(View.VISIBLE);
			stage2LinearLayout.setVisibility(View.GONE);
			stage3LinearLayout.setVisibility(View.GONE);
		}

		getCountry();

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (stage == 1) {
					stage1Validation();
				} else if (stage == 2) {
					stage2Validation();
				} else if (stage == 3) {
					stage3Validation();
				}
			}
		});

		previousButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (stage == 1) {
					previousButton.setVisibility(View.GONE);
					stage1LinearLayout.setVisibility(View.VISIBLE);
					stage2LinearLayout.setVisibility(View.GONE);
					stage3LinearLayout.setVisibility(View.GONE);
				} else if (stage == 2) {
					stage1LinearLayout.setVisibility(View.VISIBLE);
					stage2LinearLayout.setVisibility(View.GONE);
					stage3LinearLayout.setVisibility(View.GONE);
				} else if (stage == 3) {
					stage3Validation();
					stage1LinearLayout.setVisibility(View.GONE);
					stage2LinearLayout.setVisibility(View.VISIBLE);
					stage3LinearLayout.setVisibility(View.GONE);
				}
				stage--;
			}
		});
		
		dobEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction ft = myContext.getSupportFragmentManager().beginTransaction();
				DialogFragment newFragment = new DatePickerDialogFragment(pickerListener);
				newFragment.show(ft, "date_dialog"); 
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

	public void getCountry() {
		Locale[] locale = Locale.getAvailableLocales();
		ArrayList<String> countries = new ArrayList<String>();
		String country;
		for (Locale loc : locale) {
			country = loc.getDisplayCountry();
			if (country.length() > 0 && !countries.contains(country)) {
				countries.add(country);
			}
		}
		Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, countries);
		citizenship.setAdapter(adapter);
	}

	public void stage1Validation() {
		userName = userNameEditText.getText().toString().trim();
		email = emailNameEditText.getText().toString().trim();
		password = passwordEditText.getText().toString().trim();
		cnfPassword = cnfPasswordEditText.getText().toString().trim();
		int selectedRadioID = radioTypeGroup.getCheckedRadioButtonId();
		typeRadioButton = (RadioButton) rootView.findViewById(selectedRadioID);
		userType = typeRadioButton.getText().toString().trim();

		if (userName != null && !TextUtils.isEmpty(userName)) {
			if (email != null && !TextUtils.isEmpty(email)) {
				if (android.util.Patterns.EMAIL_ADDRESS.matcher(email)
						.matches()) {
					if (password != null && !TextUtils.isEmpty(password)) {
						if (cnfPassword != null
								&& !TextUtils.isEmpty(cnfPassword)) {
							if (password.equals(cnfPassword)) {
								if (userType != null
										&& !TextUtils.isEmpty(userType)) {

									stage++;

									stage1LinearLayout.setVisibility(View.GONE);
									stage2LinearLayout
											.setVisibility(View.VISIBLE);

									previousButton.setVisibility(View.VISIBLE);
								} else {
									typeRadioButton.setError("Select Type");
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

	public void stage2Validation() {

		education = educationEditText.getText().toString().trim();
		certifications = certificationsEditText.getText().toString().trim();
		skills1 = skills1EditText.getText().toString().trim();
		skills2 = skills2EditText.getText().toString().trim();

		if (education != null && !TextUtils.isEmpty(education)) {
			if (skills1 != null && !TextUtils.isEmpty(skills1)) {
				if (skills2 != null && !TextUtils.isEmpty(skills2)) {

					stage2LinearLayout.setVisibility(View.GONE);
					stage3LinearLayout.setVisibility(View.VISIBLE);
					stage++;

				} else {
					skills2EditText.requestFocus();
					skills2EditText.setError("Enter Skill");
				}
			} else {
				skills1EditText.requestFocus();
				skills1EditText.setError("Enter Skill");
			}
		} else {
			educationEditText.setError("Enter Education");
			educationEditText.requestFocus();
		}

	}

	public void stage3Validation() {
		address = addressEditText.getText().toString().trim();
		city = cityEditText.getText().toString().trim();
		state = stateEditText.getText().toString().trim();
		country = citizenship.getSelectedItem().toString().trim();
		pincode = pincodeEditText.getText().toString().trim();
		phoneNumber = phnNumberEditText.getText().toString().trim();
		dob = dobEditText.getText().toString().trim();
		workingHrs = workingHrsEditText.getText().toString().trim();
		price = priceEditText.getText().toString().trim();
		contactType = "";
		if (emaileCheckBox.isChecked()) {
			contactType += emaileCheckBox.getText().toString().trim() + ",";
		}
		if (phoneCheckBox.isChecked()) {
			contactType += phoneCheckBox.getText().toString().trim() + ",";
		}
		if (pushNotificationCheckBox.isChecked()) {
			contactType += pushNotificationCheckBox.getText().toString().trim()
					+ ",";
		}

		if (address != null && !TextUtils.isEmpty(address)) {
			if (city != null && !TextUtils.isEmpty(city)) {
				if (state != null && !TextUtils.isEmpty(state)) {
					if (country != null && !TextUtils.isEmpty(country)) {
						if (pincode != null && !TextUtils.isEmpty(pincode)) {
							if (phoneNumber != null
									&& !TextUtils.isEmpty(phoneNumber)) {
								if (dob != null && !TextUtils.isEmpty(dob)) {
									if (workingHrs != null
											&& !TextUtils.isEmpty(workingHrs)) {
										if (price != null
												&& !TextUtils.isEmpty(price)) {
											if (contactType != null
													&& !TextUtils
															.isEmpty(contactType)) {
												showDialog();
											} else {
												emaileCheckBox.requestFocus();
												makeToast("Select Contact(s) Medium");
											}
										} else {
											priceEditText.requestFocus();
											priceEditText
													.setError("Enter Price");
										}
									} else {
										workingHrsEditText.requestFocus();
										workingHrsEditText
												.setError("Enter Working Hours");
									}
								} else {
									dobEditText.requestFocus();
									dobEditText.setError("Enter DOB");
								}
							} else {
								phnNumberEditText.requestFocus();
								phnNumberEditText.setError("Enter Phone#");
							}
						} else {
							pincodeEditText.setError("Enter Pincode");
							pincodeEditText.requestFocus();
						}
					} else {
						citizenship.requestFocus();
						makeToast("Select Country");
					}
				} else {
					stateEditText.requestFocus();
					stateEditText.setError("Enter State");
				}
			} else {
				cityEditText.requestFocus();
				cityEditText.setError("Enter City");
			}
		} else {
			addressEditText.requestFocus();
			addressEditText.setError("Enter Address");
		}

	}

	public void makeToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}
	
	
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(getActivity(), pickerListener, year, month, day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// Show selected date
			dobEditText.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

		}
	};
}
