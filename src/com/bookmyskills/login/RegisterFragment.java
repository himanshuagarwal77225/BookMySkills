package com.bookmyskills.login;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.bookmyskills.MainActivity;
import com.bookmyskills.R;
import com.customcontrols.circleimageview.CircleImageView;
import com.models.CityModel;
import com.models.CountryModel;
import com.models.SkillsModel;
import com.models.StateModel;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class RegisterFragment extends android.support.v4.app.Fragment implements
		IParseListener {

	static final int DATE_PICKER_ID = 1111;
	// Activity result key for camera
	static final int REQUEST_TAKE_PHOTO = 11111;
	private static final int IMAGE_PICKER_SELECT = 1;
	private int stage = 1;
	View rootView;
	int educationid = 0;
	int certificationId = 0;
	int skillsRatingID = 0;
	int skillID = 0;
	int skillYearID = 0;
	int skillWorkHrID = 0;
	private FragmentActivity myContext;
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
	private LinearLayout stageOrganizationLinearLayout;
	// Stage 2 varaiables
	private EditText educationEditText;
	private EditText certificationsEditText;
	private AutoCompleteTextView skills1EditText;
	private AutoCompleteTextView skills2EditText;
	private EditText workHr1EditText;
	private EditText workHr2EditText;
	private Spinner rating1Spinner;
	private Spinner rating2Spinner;
	private Spinner lastUsed1Spinner;
	private Spinner lastUsed2Spinner;
	private Button addMoreEducationButton;
	private Button addMoreCertificateButton;
	private Button addMoreSkillsButton;
	private String education[];
	private String workingHrs[];
	private String certifications[];
	private String skills[];
	private String ratingLevel[];
	private String lastUsed[];
	private Spinner lastUserdSpinner, lastUserd2Spinner;
	private ArrayList<EditText> educationMoreEditText = new ArrayList<EditText>();
	private ArrayList<EditText> certificateMoreEditText = new ArrayList<EditText>();
	private ArrayList<EditText> skillsMoreEditText = new ArrayList<EditText>();
	private ArrayList<EditText> skillsWorkHrMoreEditText = new ArrayList<EditText>();
	private ArrayList<Spinner> skillRatingMoreSpinners = new ArrayList<Spinner>();
	private ArrayList<Spinner> skillLastUsedMoreSpinners = new ArrayList<Spinner>();
	private LinearLayout educationLayout;
	private LinearLayout certificationLayout;
	private LinearLayout skillsLayout;
	private LinearLayout skillsSubLayout;
	// Stage 3 Variables
	private EditText addressEditText;
	private AutoCompleteTextView cityEditText;
	private AutoCompleteTextView stateEditText;
	private EditText pincodeEditText;
	private EditText phnNumberEditText;
	private EditText dobEditText;
	private EditText workingHrsEditText;
	// private EditText priceEditText;
	private AutoCompleteTextView citizenship;
	private CheckBox emaileCheckBox;
	private CheckBox phoneCheckBox;
	private CheckBox pushNotificationCheckBox;
	private CheckBox termsNPolicyCheckBox;
	private CircleImageView profilePicImageView;
	private TextView photoUserTextView;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String phoneNumber;
	private String dob;
	private String price;
	private String country;
	private String contactType;
	private int year;
	private int month;
	private int day;
	// private DatePickerDialog.OnDateSetListener pickerListener = new
	// DatePickerDialog.OnDateSetListener() {
	//
	// // when dialog box is closed, below method will be called.
	// @Override
	// public void onDateSet(DatePicker view, int selectedYear,
	// int selectedMonth, int selectedDay) {
	//
	// year = selectedYear;
	// month = selectedMonth;
	// day = selectedDay;
	//
	// // Show selected date
	// dobEditText.setText(new StringBuilder().append(month + 1)
	// .append("-").append(day).append("-").append(year)
	// .append(" "));
	//
	// }
	// };
	// Stage Organization Layout
	private EditText fullNameEditText;
	private EditText organizationEditText;
	private EditText websiteEditText;
	private String fullName;
	private String organization;
	private String website;
	private String selectedCountryId = "";
	private String selectedCityId = "";
	private String selectedStateId = "";

	private StorageClass mStorageClass;

	private ArrayAdapter<String> skillsAdapter;
	private Dialog dialog;

	private ArrayList<CountryModel> mCountryList = new ArrayList<CountryModel>();
	private ArrayList<StateModel> mStateList = new ArrayList<StateModel>();
	private ArrayList<CityModel> mCityList = new ArrayList<CityModel>();

	public RegisterFragment() {
	}

	public static String getPathFromCameraData(Intent data, Context context) {
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}

	@Override
	public void onAttach(Activity activity) {
		myContext = (FragmentActivity) activity;
		mStorageClass = StorageClass.getInstance(getActivity());

		// Thread.setDefaultUncaughtExceptionHandler(new
		// UnCaughtException(myContext));
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		((MainActivity) getActivity()).setTitle("Register");
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
		stageOrganizationLinearLayout = (LinearLayout) rootView
				.findViewById(R.id.stageOrganizationLinearLayout);

		// stage 2
		educationEditText = (EditText) rootView
				.findViewById(R.id.educationEditText);
		certificationsEditText = (EditText) rootView
				.findViewById(R.id.certificationsEditText);
		skills1EditText = (AutoCompleteTextView) rootView
				.findViewById(R.id.skills1EditText);
		skills2EditText = (AutoCompleteTextView) rootView
				.findViewById(R.id.skills2EditText);
		workHr1EditText = (EditText) rootView
				.findViewById(R.id.workHr1EditText);

		ArrayAdapter<CharSequence> ratingAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.rating_array,
						android.R.layout.simple_spinner_item);
		ratingAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		rating1Spinner = (Spinner) rootView.findViewById(R.id.rating1Spinner);
		rating1Spinner.setAdapter(ratingAdapter);

		rating2Spinner = (Spinner) rootView.findViewById(R.id.rating2Spinner);
		rating2Spinner.setAdapter(ratingAdapter);

		ArrayAdapter<CharSequence> lastUsedAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.experience_array,
						android.R.layout.simple_spinner_item);
		lastUsedAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		lastUsed1Spinner = (Spinner) rootView
				.findViewById(R.id.lastUsed1Spinner);
		lastUsed1Spinner.setAdapter(lastUsedAdapter);

		lastUsed2Spinner = (Spinner) rootView
				.findViewById(R.id.lastUsed2Spinner);
		lastUsed2Spinner.setAdapter(lastUsedAdapter);

		workHr2EditText = (EditText) rootView
				.findViewById(R.id.workHr2EditText);
		addMoreEducationButton = (Button) rootView
				.findViewById(R.id.addMoreEducationButton);
		addMoreCertificateButton = (Button) rootView
				.findViewById(R.id.addMoreCertificateButton);
		addMoreSkillsButton = (Button) rootView
				.findViewById(R.id.addMoreSkillsButton);
		lastUserdSpinner = (Spinner) rootView
				.findViewById(R.id.lastUserdSpinner);
		lastUserd2Spinner = (Spinner) rootView
				.findViewById(R.id.lastUserd2Spinner);
		educationLayout = (LinearLayout) rootView
				.findViewById(R.id.educationLayout);
		certificationLayout = (LinearLayout) rootView
				.findViewById(R.id.certificationLayout);
		skillsLayout = (LinearLayout) rootView.findViewById(R.id.skillsLayout);

		skillsAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getSkills());
		skills2EditText.setAdapter(skillsAdapter);
		skills2EditText.setThreshold(2);
		skills1EditText.setAdapter(skillsAdapter);
		skills1EditText.setThreshold(2);
		// skillsSubLayout = (LinearLayout)
		// rootView.findViewById(R.id.skillsSubLayout);
		// lastUsedSpinnerPopulate();

		// stage 3
		addressEditText = (EditText) rootView
				.findViewById(R.id.addressEditText);
		cityEditText = (AutoCompleteTextView) rootView
				.findViewById(R.id.cityEditText);
		stateEditText = (AutoCompleteTextView) rootView
				.findViewById(R.id.stateEditText);
		pincodeEditText = (EditText) rootView
				.findViewById(R.id.pincodeEditText);
		phnNumberEditText = (EditText) rootView
				.findViewById(R.id.phnNumberEditText);
		dobEditText = (EditText) rootView.findViewById(R.id.dobEditText);
		// workingHrsEditText = (EditText)
		// rootView.findViewById(R.id.workingHrsEditText);
		// priceEditText = (EditText) rootView.findViewById(R.id.priceEditText);
		emaileCheckBox = (CheckBox) rootView.findViewById(R.id.emaileCheckBox);
		phoneCheckBox = (CheckBox) rootView.findViewById(R.id.phoneCheckBox);
		pushNotificationCheckBox = (CheckBox) rootView
				.findViewById(R.id.pushNotificationCheckBox);
		termsNPolicyCheckBox = (CheckBox) rootView
				.findViewById(R.id.termsNPolicyCheckBox);

		citizenship = (AutoCompleteTextView) rootView
				.findViewById(R.id.countryEditText);

		profilePicImageView = (CircleImageView) rootView
				.findViewById(R.id.profilePicImageView);
		photoUserTextView = (TextView) rootView
				.findViewById(R.id.photoUserTextView);

		// Stage Organization Layout
		fullNameEditText = (EditText) rootView
				.findViewById(R.id.fullNameEditText);
		organizationEditText = (EditText) rootView
				.findViewById(R.id.organizationEditText);
		websiteEditText = (EditText) rootView
				.findViewById(R.id.websiteEditText);

		profilePicImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cameraDialog();
			}
		});

		if (stage == 1) {
			stage1LinearLayout.setVisibility(View.VISIBLE);
			stage2LinearLayout.setVisibility(View.GONE);
			addMoreSkillsButton.setVisibility(View.GONE);
			stage3LinearLayout.setVisibility(View.GONE);
		}

		// getCountry();

		initCountryApi();

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (stage == 1) {
					stage1Validation();
				} else if (stage == 2 && userType.equalsIgnoreCase("Work")) {
					stage2Validation();
				} else if (stage == 2 && userType.equalsIgnoreCase("Hire")) {
					stageOrganizationValidation();
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
					addMoreSkillsButton.setVisibility(View.GONE);
					stage3LinearLayout.setVisibility(View.GONE);
					stageOrganizationLinearLayout.setVisibility(View.GONE);
				} else if (stage == 2 && userType.equalsIgnoreCase("Work")) {
					stage1LinearLayout.setVisibility(View.VISIBLE);
					stage2LinearLayout.setVisibility(View.GONE);
					addMoreSkillsButton.setVisibility(View.GONE);
					stage3LinearLayout.setVisibility(View.GONE);
					stageOrganizationLinearLayout.setVisibility(View.GONE);
				} else if (stage == 3 && userType.equalsIgnoreCase("Work")) {
					stage3Validation();
					stage1LinearLayout.setVisibility(View.GONE);
					stage2LinearLayout.setVisibility(View.VISIBLE);
					addMoreSkillsButton.setVisibility(View.VISIBLE);
					stage3LinearLayout.setVisibility(View.GONE);
					stageOrganizationLinearLayout.setVisibility(View.GONE);
				} else if (stage == 2 && userType.equalsIgnoreCase("Hire")) {
					stage1LinearLayout.setVisibility(View.VISIBLE);
					stage2LinearLayout.setVisibility(View.GONE);
					addMoreSkillsButton.setVisibility(View.GONE);
					stage3LinearLayout.setVisibility(View.GONE);
					stageOrganizationLinearLayout.setVisibility(View.GONE);
				} else if (stage == 3 && userType.equalsIgnoreCase("Hire")) {
					stage3Validation();
					stage1LinearLayout.setVisibility(View.GONE);
					stage2LinearLayout.setVisibility(View.GONE);
					addMoreSkillsButton.setVisibility(View.GONE);
					stage3LinearLayout.setVisibility(View.GONE);
					stageOrganizationLinearLayout.setVisibility(View.VISIBLE);
				}
				stage--;
			}
		});

		addMoreEducationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				educationLayout.addView(addMoreEditText("Qualification"));
			}
		});

		addMoreCertificateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				certificationLayout.addView(addMoreEditText("Certification"));
			}
		});

		addMoreSkillsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// LinearLayout.LayoutParams params = new
				// LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				// LayoutParams.WRAP_CONTENT);
				//
				// LinearLayout skillsSubLayout = new LinearLayout(myContext);
				// skillsSubLayout.setOrientation(LinearLayout.VERTICAL);
				// skillsSubLayout.setLayoutParams(params);
				// skillsLayout.setLayoutParams(params);
				// skillsLayout.addView(addMoreEditText("Skills"));
				// skillsLayout.addView(addMoreEditText("Working Hours"));
				// skillsLayout.addView(skillsSubLayout);
				//
				// skillsSubLayout = new LinearLayout(myContext);
				// skillsSubLayout.setOrientation(LinearLayout.VERTICAL);
				// skillsLayout.addView(addMoreSpinner("Rating"));
				// skillsLayout.addView(addMoreSpinner("Last Used"));
				// skillsLayout.addView(skillsSubLayout);

				LinearLayout linearLayout = new LinearLayout(getActivity());
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(0, 0, 0, 5);
				linearLayout.setBackground(getResources().getDrawable(
						R.drawable.skills_gradient_bg));
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				linearLayout.setLayoutParams(params);

				// linearLayout.addView(addMoreSeparatorView());
				linearLayout.addView(addMoreEditText("Skills"));
				linearLayout.addView(addMoreEditText("Working Hours"));
				linearLayout.addView(addMoreSpinner("Rating"));
				linearLayout.addView(addMoreSpinner("Last Used"));

				skillsLayout.addView(linearLayout);

			}
		});

		dobEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean hasFocus) {

				if (hasFocus) {
					Calendar calendar = Calendar.getInstance();

					new DatePickerDialog(getActivity(),
							new DatePickerDialog.OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker datePicker,
										int year, int month, int day) {
									dobEditText.setText(new StringBuilder()
											.append(month + 1).append("-")
											.append(day).append("-")
											.append(year).append(" "));
								}
							}, calendar.get(Calendar.YEAR), calendar
									.get(Calendar.MONTH), calendar
									.get(Calendar.DAY_OF_MONTH)).show();
				}
			}
		});

		dobEditText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Calendar calendar = Calendar.getInstance();

				new DatePickerDialog(getActivity(),
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker datePicker,
									int year, int month, int day) {

								dobEditText.setText(new StringBuilder()
										.append(year).append("/")
										.append(month + 1).append("/")
										.append(day));

								// dobEditText.setText(new StringBuilder()
								// .append(month + 1).append("-")
								// .append(day).append("-").append(year)
								// .append(" "));
							}
						}, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH)).show();
				;
			}
		});

		return rootView;
	}

	private void initCountryApi() {
		showProgress();
		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.GETCOUNTRY, null),
				StaticInfo.GETCOUNTRY_RESPONSE_CODE, this);
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

	@Override
	public void SuccessResponse(JSONObject response, int requestCode) {
		switch (requestCode) {
		case StaticInfo.GETCOUNTRY_RESPONSE_CODE:
			responseForGetCountryApi(response);
			break;
		case StaticInfo.GETSTATE_RESPONSE_CODE:
			responseForStateAPi(response);
			break;
		case StaticInfo.GETCITY_RESPONSE_CODE:
			responseForCityApi(response);
			break;
		case StaticInfo.GETREGISTER_RESPONSE_CODE:
			responseForRegisterApi(response);
		default:
			break;
		}
	}

	private void responseForRegisterApi(JSONObject response) {
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
						getActivity().getSupportFragmentManager()
								.popBackStackImmediate();

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

	private void responseForCityApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					mCityList = new ArrayList<CityModel>();
					JSONArray mCountryArray = response
							.optJSONArray(StaticInfo.CITY);
					if (mCountryArray != null) {
						for (int i = 0; i < mCountryArray.length(); i++) {
							CityModel mModel = new CityModel();
							JSONObject mObj = mCountryArray.optJSONObject(i);
							if (mObj != null) {
								mModel.setId(mObj.optString(StaticInfo.ID));
								mModel.setCity(mObj.optString(StaticInfo.CITY));
								mModel.setStatus(mObj
										.optString(StaticInfo.STATUS));
								mCityList.add(mModel);
							}
						}
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
		String[] cityList = getCityList();
		if (cityList != null) {
			ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, cityList);
			cityEditText.setAdapter(adapter);
			cityEditText.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					for (CityModel mModel : mCityList) {
						if (cityEditText.getText().toString()
								.equalsIgnoreCase(mModel.getCity())) {
							selectedCityId = mModel.getId();
							break;
						}
					}
				}
			});
			cityEditText.setThreshold(1);
		}

	}

	private void responseForStateAPi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					mStateList = new ArrayList<StateModel>();
					JSONArray mCountryArray = response
							.optJSONArray(StaticInfo.STATE);
					if (mCountryArray != null) {
						for (int i = 0; i < mCountryArray.length(); i++) {
							StateModel mModel = new StateModel();
							JSONObject mObj = mCountryArray.optJSONObject(i);
							if (mObj != null) {
								mModel.setId(mObj.optString(StaticInfo.ID));
								mModel.setState(mObj
										.optString(StaticInfo.STATE));
								mModel.setStatus(mObj
										.optString(StaticInfo.STATUS));
								mStateList.add(mModel);
							}
						}
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
		String[] stateList = getStateList();
		if (stateList != null) {
			ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, stateList);
			stateEditText.setAdapter(adapter);
			stateEditText.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					for (StateModel mModel : mStateList) {
						if (stateEditText.getText().toString()
								.equalsIgnoreCase(mModel.getState())) {
							selectedStateId = mModel.getId();
							initCityApi(selectedCountryId, selectedStateId);
							break;
						}
					}
				}
			});
			stateEditText.setThreshold(1);
		}

	}

	private void initCityApi(String selectedCountryId, String selectedStateId) {
		showProgress();
		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		params.putString("country_id", selectedCountryId);
		params.putString("state_id", selectedStateId);
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.GETCITY, params),
				StaticInfo.GETCITY_RESPONSE_CODE, this);
	}

	private void responseForGetCountryApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					mCountryList = new ArrayList<CountryModel>();
					JSONArray mCountryArray = response
							.optJSONArray(StaticInfo.COUNTRY);
					if (mCountryArray != null) {
						for (int i = 0; i < mCountryArray.length(); i++) {
							CountryModel mModel = new CountryModel();
							JSONObject mObj = mCountryArray.optJSONObject(i);
							if (mObj != null) {
								mModel.setId(mObj.optString(StaticInfo.ID));
								mModel.setCountry(mObj
										.optString(StaticInfo.COUNTRY));
								mModel.setAddressFormat(mObj
										.optString(StaticInfo.ADDRESS_FORMAT));
								mModel.setIsoCode2(mObj
										.optString(StaticInfo.ISO_CODE_2));
								mModel.setIsoCode3(mObj
										.optString(StaticInfo.ISO_CODE_3));
								mModel.setStatus(mObj
										.optString(StaticInfo.STATUS));
								mModel.setPostCodeRequired(mObj
										.optString(StaticInfo.POSTCODE_REQUIRED));
								mCountryList.add(mModel);
							}
						}
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
		String[] countryList = getCountryList();
		if (countryList != null) {
			ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, countryList);
			citizenship.setAdapter(adapter);
			citizenship.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					for (CountryModel mModel : mCountryList) {
						if (citizenship.getText().toString()
								.equalsIgnoreCase(mModel.getCountry())) {
							selectedCountryId = mModel.getId();
							initStateApi(selectedCountryId);
							break;
						}
					}
				}

			});
			citizenship.setThreshold(1);
		}

	}

	private void initStateApi(String selectedCountryId) {
		showProgress();
		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		params.putString("country_id", selectedCountryId);
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.GETSTATE, params),
				StaticInfo.GETSTATE_RESPONSE_CODE, this);
	}

	private String[] getCountryList() {

		if (mCountryList != null) {
			String countries[] = new String[mCountryList.size()];
			for (int i = 0; i < mCountryList.size(); i++) {
				countries[i] = mCountryList.get(i).getCountry();
			}
			return countries;
		}
		return null;
	}

	private String[] getStateList() {

		if (mStateList != null) {
			String states[] = new String[mStateList.size()];
			for (int i = 0; i < mStateList.size(); i++) {
				states[i] = mStateList.get(i).getState();
			}
			return states;
		}
		return null;
	}

	private String[] getCityList() {

		if (mCityList != null) {
			String cities[] = new String[mCityList.size()];
			for (int i = 0; i < mCityList.size(); i++) {
				cities[i] = mCityList.get(i).getCity();
			}
			return cities;
		}
		return null;
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
									if (userType.equalsIgnoreCase("Work")) {
										stage2LinearLayout
												.setVisibility(View.VISIBLE);
										addMoreSkillsButton
												.setVisibility(View.VISIBLE);

										ratingLevel = new String[skillRatingMoreSpinners
												.size() + 2];

										rating1Spinner
												.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
													@Override
													public void onItemSelected(
															AdapterView<?> adapterView,
															View view, int pos,
															long l) {
														if (pos > 0) {
															ratingLevel[0] = rating1Spinner
																	.getSelectedItem()
																	.toString();
														} else {
															// Toast.makeText(getActivity(),
															// "Please rate yourself",
															// Toast.LENGTH_SHORT).show();
														}
													}

													@Override
													public void onNothingSelected(
															AdapterView<?> adapterView) {
													}
												});

										rating2Spinner
												.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
													@Override
													public void onItemSelected(
															AdapterView<?> adapterView,
															View view, int pos,
															long l) {
														if (pos > 0) {
															ratingLevel[1] = rating2Spinner
																	.getSelectedItem()
																	.toString();
														} else {
															// Toast.makeText(getActivity(),
															// "Please rate yourself",
															// Toast.LENGTH_SHORT).show();
														}
													}

													@Override
													public void onNothingSelected(
															AdapterView<?> adapterView) {
													}
												});

										lastUsed = new String[skillLastUsedMoreSpinners
												.size() + 2];

										lastUsed1Spinner
												.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
													@Override
													public void onItemSelected(
															AdapterView<?> adapterView,
															View view, int pos,
															long l) {
														if (pos > 0) {
															lastUsed[0] = lastUsed1Spinner
																	.getSelectedItem()
																	.toString();
														} else {
															// Toast.makeText(getActivity(),
															// "Please fill working hours",
															// Toast.LENGTH_SHORT).show();
														}
													}

													@Override
													public void onNothingSelected(
															AdapterView<?> adapterView) {
													}
												});
										lastUsed2Spinner
												.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
													@Override
													public void onItemSelected(
															AdapterView<?> adapterView,
															View view, int pos,
															long l) {
														if (pos > 0) {
															lastUsed[1] = lastUsed2Spinner
																	.getSelectedItem()
																	.toString();
														} else {
															// Toast.makeText(getActivity(),
															// "Please fill working hours",
															// Toast.LENGTH_SHORT).show();
														}
													}

													@Override
													public void onNothingSelected(
															AdapterView<?> adapterView) {
													}
												});

									} else {
										stageOrganizationLinearLayout
												.setVisibility(View.VISIBLE);
									}

									previousButton.setVisibility(View.VISIBLE);
								} else {
									makeToast("Select Type");
									radioTypeGroup.requestFocus();
								}
							} else {
								makeToast("Password Does't Match");
								cnfPasswordEditText.requestFocus();
							}
						} else {
							makeToast("Enter Confirm Password");
							cnfPasswordEditText.requestFocus();
						}
					} else {
						makeToast("Enter Password");
						passwordEditText.requestFocus();
					}
				} else {
					makeToast("Invalid Email");
					emailNameEditText.requestFocus();
				}
			} else {
				makeToast("Enter Email");
				emailNameEditText.requestFocus();
			}

		} else {
			makeToast("Enter User Name");
			userNameEditText.requestFocus();
		}
	}

	public void stage2Validation() {

		education = new String[educationMoreEditText.size() + 1];
		education[0] = educationEditText.getText().toString().trim();
		certifications = new String[certificateMoreEditText.size() + 1];
		certifications[0] = certificationsEditText.getText().toString().trim();
		skills = new String[skillsMoreEditText.size() + 2];
		skills[0] = skills1EditText.getText().toString().trim();
		skills[1] = skills2EditText.getText().toString().trim();

		workingHrs = new String[skillsWorkHrMoreEditText.size() + 2];
		workingHrs[0] = workHr1EditText.getText().toString().trim();
		workingHrs[1] = workHr2EditText.getText().toString().trim();

		for (int i = 0; i < educationMoreEditText.size(); i++) {
			EditText educationEditText = educationMoreEditText.get(i);
			String educationText = educationEditText.getText().toString()
					.trim();
			if (educationText != null && !TextUtils.isEmpty(educationText)) {
				education[i + 1] = educationText;
			}
		}
		for (int i = 0; i < certificateMoreEditText.size(); i++) {
			EditText certificationEditText = certificateMoreEditText.get(i);
			String certificationText = certificationEditText.getText()
					.toString().trim();
			if (certificationText != null
					&& !TextUtils.isEmpty(certificationText)) {
				certifications[i + 1] = certificationText;
			}
		}
		for (int i = 0; i < skillsMoreEditText.size(); i++) {
			EditText skillsEditText = skillsMoreEditText.get(i);
			String skillsText = skillsEditText.getText().toString().trim();
			if (skillsEditText != null && !TextUtils.isEmpty(skillsText)) {
				skills[i + 2] = skillsText;
			}
		}
		for (int i = 0; i < skillsWorkHrMoreEditText.size(); i++) {
			EditText skillsHrEditText = skillsWorkHrMoreEditText.get(i);
			String skillsHrText = skillsHrEditText.getText().toString().trim();
			if (skillsHrEditText != null && !TextUtils.isEmpty(skillsHrText)) {
				workingHrs[i + 2] = skillsHrText;
			}
		}
		for (int i = 0; i < skillRatingMoreSpinners.size(); i++) {
			Spinner skillsRatingSpinner = skillRatingMoreSpinners.get(i);
			String skillsRatingSpinnerText = skillsRatingSpinner
					.getSelectedItem().toString().trim();
			if (skillsRatingSpinner != null
					&& !skillsRatingSpinnerText
							.equalsIgnoreCase("Proficiency Level")) {
				ratingLevel[i + 2] = skillsRatingSpinnerText;
			}
		}
		for (int i = 0; i < skillLastUsedMoreSpinners.size(); i++) {
			Spinner skillsLastUsedSpinner = skillLastUsedMoreSpinners.get(i);
			String skillsLastUsedSpinnerText = skillsLastUsedSpinner
					.getSelectedItem().toString().trim();
			if (skillsLastUsedSpinner != null
					&& !skillsLastUsedSpinnerText
							.equalsIgnoreCase("Year of Experience")) {
				lastUsed[i + 2] = skillsLastUsedSpinnerText;
			}
		}

		// if (education != null && !TextUtils.isEmpty(education[0])) {
		if (skills[0] != null && !TextUtils.isEmpty(skills[0])) {
			if (skills[1] != null && !TextUtils.isEmpty(skills[1])) {
				if (workingHrs[0] != null && !TextUtils.isEmpty(workingHrs[0])) {
					if (workingHrs[1] != null
							&& !TextUtils.isEmpty(workingHrs[1])) {
						if (ratingLevel[0] != null && ratingLevel[1] != null) {
							if (lastUsed[0] != null && lastUsed[1] != null) {
								stage2LinearLayout.setVisibility(View.GONE);
								addMoreSkillsButton.setVisibility(View.GONE);
								stage3LinearLayout.setVisibility(View.VISIBLE);
								stage++;
							} else {
								makeToast("Select your proficiency");
							}
						} else {
							makeToast("Rate Your Skills");
						}
					} else {
						workHr2EditText.requestFocus();
						makeToast("Enter Working Hours");
					}
				} else {
					workHr1EditText.requestFocus();
					makeToast("Enter Working Hours");
				}

			} else {
				skills2EditText.requestFocus();
				makeToast("Enter Skill");
			}
		} else {
			skills1EditText.requestFocus();
			makeToast("Enter Skill");
		}
		// } else {
		// makeToast("Enter Education");
		// educationEditText.requestFocus();
		// }

	}

	public void stageOrganizationValidation() {
		fullName = fullNameEditText.getText().toString().trim();
		organization = organizationEditText.getText().toString().trim();
		website = websiteEditText.getText().toString().trim();

		if (fullName != null && !TextUtils.isEmpty(fullName)) {
			if (organization != null && !TextUtils.isEmpty(organization)) {
				stageOrganizationLinearLayout.setVisibility(View.GONE);
				stage3LinearLayout.setVisibility(View.VISIBLE);
				stage++;
			} else {
				organizationEditText.requestFocus();
				makeToast("Enter Organization Name");
			}
		} else {
			fullNameEditText.requestFocus();
			makeToast("Enter Full Name");
		}
	}

	public void stage3Validation() {
		address = addressEditText.getText().toString().trim();
		city = cityEditText.getText().toString().trim();
		state = stateEditText.getText().toString().trim();
		country = citizenship.getText().toString().trim();
		pincode = pincodeEditText.getText().toString().trim();
		phoneNumber = phnNumberEditText.getText().toString().trim();
		dob = dobEditText.getText().toString().trim();
		// workingHrs = workingHrsEditText.getText().toString().trim();
		// price = priceEditText.getText().toString().trim();
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
									// if (workingHrs != null &&
									// !TextUtils.isEmpty(workingHrs)) {
									// if (price != null &&
									// !TextUtils.isEmpty(price)) {
									if (contactType != null
											&& !TextUtils.isEmpty(contactType)) {
										if (termsNPolicyCheckBox.isChecked()) {
											initRegisterApi();
											// showDialog();
											// makeToast("Thank You for registration");
										} else {
											termsNPolicyCheckBox.requestFocus();
											makeToast("Please accept  the Terms and Conditions and Privacy Policy");
										}
									} else {
										emaileCheckBox.requestFocus();
										makeToast("Select Contact(s) Medium");
									}
									// } else {
									// priceEditText.requestFocus();
									// makeToast("Enter Price");
									// }
									// } else {
									// workingHrsEditText.requestFocus();
									// makeToast("Enter Working Hours");
									// }
								} else {
									dobEditText.requestFocus();
									makeToast("Enter DOB");
								}
							} else {
								phnNumberEditText.requestFocus();
								makeToast("Enter Phone#");
							}
						} else {
							makeToast("Enter Pincode");
							pincodeEditText.requestFocus();
						}
					} else {
						citizenship.requestFocus();
						makeToast("Select Country");
					}
				} else {
					stateEditText.requestFocus();
					makeToast("Enter State");
				}
			} else {
				cityEditText.requestFocus();
				makeToast("Enter City");
			}
		} else {
			addressEditText.requestFocus();
			makeToast("Enter Address");
		}

	}

	public void makeToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	// protected Dialog onCreateDialog(int id) {
	// switch (id) {
	// case DATE_PICKER_ID:
	//
	// // open datepicker dialog.
	// // set date picker for current date
	// // add pickerListener listner to date picker
	// return new DatePickerDialog(getActivity(), pickerListener, year,
	// month, day);
	// }
	// return null;
	// }

	public void lastUsedSpinnerPopulate() {
		ArrayList<String> years = new ArrayList<String>();
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = thisYear; i >= 1900; i--) {
			years.add(Integer.toString(i));
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, years);

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, years);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		lastUserdSpinner.setAdapter(adapter1);

		lastUserd2Spinner.setAdapter(adapter);
	}

	public void cameraDialog() {
		AlertDialog.Builder getImageFrom = new AlertDialog.Builder(
				getActivity());
		getImageFrom.setTitle("Select:");
		final CharSequence[] opsChars = { "Take Picture", "Open Gallery",
				"Cancel" };
		getImageFrom.setItems(opsChars, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					dispatchTakePictureIntent();
				} else if (which == 1) {
					Intent i = new Intent(Intent.ACTION_PICK,
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, IMAGE_PICKER_SELECT);
				} else {
					dialog.cancel();
				}
				dialog.dismiss();
			}
		});
		getImageFrom.create().show();
	}

	/**
	 * Start the camera by dispatching a camera intent.
	 */
	protected void dispatchTakePictureIntent() {
		// Check if there is a camera.
		Context context = getActivity();
		PackageManager packageManager = context.getPackageManager();
		if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
			Toast.makeText(getActivity(),
					"This device does not have a camera.", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// Camera exists? Then proceed...
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		MainActivity activity = (MainActivity) getActivity();
		if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
			// Create the File where the photo should go.
			// If you don't do this, you may get a crash in some devices.
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
				Toast toast = Toast.makeText(activity,
						"There was a problem saving the photo...",
						Toast.LENGTH_SHORT);
				toast.show();
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				// Uri fileUri = Uri.fromFile(photoFile);
				// activity.setCapturedImageURI(fileUri);
				// activity.setCurrentPhotoPath(fileUri.getPath());
				// takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				// activity.getCapturedImageURI());
				// startActivityForResult(takePictureIntent,
				// REQUEST_TAKE_PHOTO);
			}
		}
	}

	/**
	 * The activity returns with the photo.
	 *
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_TAKE_PHOTO
				&& resultCode == Activity.RESULT_OK) {
			addPhotoToGallery();
			MainActivity activity = (MainActivity) getActivity();
			// Show the full sized image.
			// setFullImageFromFilePath(activity.getCurrentPhotoPath(),
			// profilePicImageView);

		} else if (requestCode == IMAGE_PICKER_SELECT
				&& resultCode == Activity.RESULT_OK) {
			String path = getPathFromCameraData(data, this.getActivity());
			Log.i("PICTURE", "Path: " + path);
			if (path != null) {
				setFullImageFromFilePath(path, profilePicImageView);
			}
		} else {
			Toast.makeText(getActivity(), "Image Capture Failed",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Creates the image file to which the image must be saved.
	 *
	 * @return
	 * @throws java.io.IOException
	 */
	protected File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date(System.currentTimeMillis()));
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);
		// Save a file: path for use with ACTION_VIEW intents
		// MainActivity activity = (MainActivity) getActivity();
		// activity.setCurrentPhotoPath("file:" + image.getAbsolutePath());
		return image;
	}

	/**
	 * Add the picture to the photo gallery. Must be called on all camera images
	 * or they will disappear once taken.
	 */
	protected void addPhotoToGallery() {
		// Intent mediaScanIntent = new Intent(
		// Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		// MainActivity activity = (MainActivity) getActivity();
		// File f = new File(activity.getCurrentPhotoPath());
		// Uri contentUri = Uri.fromFile(f);
		// mediaScanIntent.setData(contentUri);
		// this.getActivity().sendBroadcast(mediaScanIntent);
	}

	/**
	 * Scale the photo down and fit it to our image views.
	 * <p/>
	 * "Drastically increases performance" to set images using this technique.
	 * Read more:http://developer.android.com/training/camera/photobasics.html
	 */
	private void setFullImageFromFilePath(String imagePath, ImageView imageView) {
		// Get the dimensions of the View
		int targetW = imageView.getWidth();
		int targetH = imageView.getHeight();
		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
		imageView.setImageBitmap(bitmap);
		photoUserTextView.setVisibility(View.GONE);
	}

	public AutoCompleteTextView addMoreEditText(String hint) {

		AutoCompleteTextView editText = new AutoCompleteTextView(getActivity());

		LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// if (hint.equalsIgnoreCase("Qualification")) {
		// editText.setId(educationid);
		// editText.setCompoundDrawablesWithIntrinsicBounds(
		// R.drawable.education, 0, 0, 0);
		// educationMoreEditText.add(editText);
		// educationid++;
		// editText.setHint(hint + " " + educationid);
		// } else if (hint.equalsIgnoreCase("Certification")) {
		// editText.setId(certificationId);
		// editText.setCompoundDrawablesWithIntrinsicBounds(
		// R.drawable.certificate, 0, 0, 0);
		// certificateMoreEditText.add(editText);
		// certificationId++;
		// editText.setHint(hint + " " + certificationId);
		// } else ...

		if (hint.equalsIgnoreCase("Skills")) {
			editText.setId(skillID);
			editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.skills,
					0, 0, 0);
			skillsMoreEditText.add(editText);
			skillID++;
			editText.setHint(hint + " " + (skillID + 2));
			// editTextLayoutParams = new
			// LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
			// LayoutParams.WRAP_CONTENT);

			skillsAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, getSkills());
			editText.setAdapter(skillsAdapter);
			editText.setThreshold(2);
		} else if (hint.equalsIgnoreCase("Working Hours")) {
			editText.setId(skillWorkHrID);
			editText.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.working_hours, 0, 0, 0);
			skillsWorkHrMoreEditText.add(editText);
			skillWorkHrID++;
			editText.setHint(hint);
			editText.setInputType(InputType.TYPE_CLASS_DATETIME);
			// editTextLayoutParams = new
			// LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
			// LayoutParams.WRAP_CONTENT);
		}

		// editTextLayoutParams.setMargins(10, 10, 10, 10);
		editText.setCompoundDrawablePadding(10);
		editText.setLayoutParams(editTextLayoutParams);

		return editText;
	}

	public Spinner addMoreSpinner(String hint) {

		Spinner spinner = new Spinner(getActivity());
		if (hint.equalsIgnoreCase("Rating")) {
			spinner.setId(skillsRatingID);
			skillRatingMoreSpinners.add(spinner);
			skillsRatingID++;
			ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(getActivity(), R.array.rating_array,
							android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			spinner.setPromptId(R.string.rating_prompt);

		} else if (hint.equalsIgnoreCase("Last Used")) {
			spinner.setId(skillYearID);
			skillLastUsedMoreSpinners.add(spinner);
			skillYearID++;
			ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(getActivity(),
							R.array.experience_array,
							android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			spinner.setPromptId(R.string.last_used);
		}
		LinearLayout.LayoutParams spinnerLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// spinnerLayoutParams.setMargins(10, 10, 10, 10);
		spinner.setLayoutParams(spinnerLayoutParams);

		return spinner;
	}

	public String[] getSkills() {
		ArrayList<SkillsModel> skillSet = mStorageClass.getSkillsArray();

		if (skillSet != null && skillSet.size() > 0) {
			String skills[] = new String[skillSet.size()];
			for (int i = 0; i < skillSet.size(); i++) {
				SkillsModel skill = skillSet.get(i);
				skills[i] = skill.getSkill();
			}
			return skills;
		} else {
			return null;
		}

	}

	private void initRegisterApi() {
		showProgress();
		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle mParams = new Bundle();
		if (userType.equalsIgnoreCase("Work")) {
			mParams.putString("user_type", "work_seeker");
			mParams.putString("username", userName);
			mParams.putString("email", email);
			mParams.putString("password", password);
			mParams.putString("first_name", fullName);
			mParams.putString("country_id", selectedCountryId);
			mParams.putString("state_id", selectedStateId);
			mParams.putString("city_id", selectedCityId);
			mParams.putString("address", address);
			mParams.putString("dob", dobEditText.getText().toString());
			mParams.putString("postal_code", pincode);
			mParams.putString("mobile", phoneNumber);
			TelephonyManager telephonyManager = (TelephonyManager) getActivity()
					.getSystemService(getActivity().TELEPHONY_SERVICE);
			mParams.putString("imei",
					String.valueOf(telephonyManager.getDeviceId()));
			String possibleEmail = "";
			Pattern emailPattern = Patterns.EMAIL_ADDRESS;
			Account[] accounts = AccountManager.get(getActivity())
					.getAccounts();
			for (Account account : accounts) {
				if (emailPattern.matcher(account.name).matches()) {
					possibleEmail = account.name;
					break;
				}
			}
			mParams.putString("device_email_id", possibleEmail);
			mParams.putString("skill_id1", skills[0]);
			mParams.putString("skill_id2", skills[1]);
			mParams.putString("working_hr1", workingHrs[0]);
			mParams.putString("working_hr2", workingHrs[1]);
			mParams.putString("proficiency_level1", ratingLevel[0]);
			mParams.putString("proficiency_level2", ratingLevel[1]);
			mParams.putString("year_of_experience1", lastUsed[0]);
			mParams.putString("year_of_experience2", lastUsed[1]);
		} else {
			mParams.putString("user_type", "skill_seeker");
			mParams.putString("username", userName);
			mParams.putString("email", email);
			mParams.putString("password", password);
			mParams.putString("first_name", fullName);
			mParams.putString("country_id", selectedCountryId);
			mParams.putString("state_id", selectedStateId);
			mParams.putString("city_id", selectedCityId);
			mParams.putString("address", address);
			mParams.putString("organization", organization);
			mParams.putString("website", website);
			mParams.putString("dob", dobEditText.getText().toString());
			mParams.putString("postal_code", pincode);
			mParams.putString("mobile", phoneNumber);
			TelephonyManager telephonyManager = (TelephonyManager) getActivity()
					.getSystemService(getActivity().TELEPHONY_SERVICE);
			mParams.putString("imei",
					String.valueOf(telephonyManager.getDeviceId()));
			String possibleEmail = "";
			Pattern emailPattern = Patterns.EMAIL_ADDRESS;
			Account[] accounts = AccountManager.get(getActivity())
					.getAccounts();
			for (Account account : accounts) {
				if (emailPattern.matcher(account.name).matches()) {
					possibleEmail = account.name;
					break;
				}
			}
			mParams.putString("device_email_id", possibleEmail);
		}

		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.GETREGISTER, mParams),
				StaticInfo.GETREGISTER_RESPONSE_CODE, this);
	}
}
