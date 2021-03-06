package com.bookmyskills.profiile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.androidquery.AQuery;
import com.bookmyskills.MainActivity;
import com.bookmyskills.R;
import com.bookmyskills.inbox.ComposeEmailFragment;
import com.customcontrols.fadingactionbar.FadingActionBarHelper;
import com.customcontrols.shimmertextview.ShimmerTextView;
import com.models.SkillsModel;
import com.models.UserSearchModel;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class ViewProfileFragment extends android.support.v4.app.Fragment
		implements View.OnClickListener, IParseListener {

	private FadingActionBarHelper mFadingHelper;

	private UserSearchModel mUserInfo;

	Activity myContext;
	StorageClass mStroageClass;
	AQuery mQuery;
	EditText firstName = null;
	EditText lastName = null;
	EditText livesInEditText = null;
	EditText introductionEditText = null;
	EditText emailEditText = null;
	EditText phoneEditText = null;
	EditText addressEditText = null;
	EditText birthdayEditText = null;
	EditText educationEditText = null;
	EditText certificationEditText = null;
	AutoCompleteTextView skillEditText = null;
	private ImageView mimgUserAvatar;
	private TextView txtAddPhoto;
	Spinner genderSpinner = null, ratingSpinner = null,
			workingHourSpinner = null;

	private LinearLayout mlayoutContactInfo;

	private ShimmerTextView userFullNameTextView;
	private TextView livesInTextView, introductionTextView, emailTextView,
			phoneTextView, addressTextView, genderTextView, birthdayTextView,
			educationTextView, certificationTextView, skillNameTextView,
			ratingTextView, workingHourTextView;

	private String[] genderArray = { "Male", "Female" };

	private String[] ratingArray = { "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10" };

	private String[] workingHourArray = { "2 Hours", "4 Hours", "6 Hours",
			"8 Hours", "10 Hours", "12 Hours", "14 Hours", "16 Hours",
			"18 Hours", "20 Hours" };

	private Dialog dialog;

	private CheckBox emaileCheckBox;
	private CheckBox phoneCheckBox;
	private CheckBox pushNotificationCheckBox;
	private LinearLayout mContactMedium;
	private Button mbtnBukMySkills;

	private enum VIEW_DIALOG {
		EDIT_PROFILE, ABOUT_ME, CONTACT_INFO, BASIC_INFO, EDUCATION_INFO, SKILL_INFO
	}

	public ViewProfileFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mFadingHelper = new FadingActionBarHelper()
				.actionBarBackground(R.drawable.bms_background)
				.headerLayout(R.layout.bms_profile_header)
				.contentLayout(R.layout.fragment_view_profile);
		mFadingHelper.initActionBar(getActivity());
		View view = mFadingHelper.createView(inflater);
		ImageView img = (ImageView) view.findViewById(R.id.image_header);
		img.setImageResource(R.drawable.header);
		setUpViews(view);
		setUpListeners(view);
		mQuery = new AQuery(getActivity());
		initiateGetProfilApi();

		ActionBar mActionBar = ((MainActivity) getActivity())
				.getSupportActionBar();
		mActionBar.setBackgroundDrawable(new ColorDrawable(getResources()
				.getColor(R.color.app_color)));
		mActionBar.setTitle("Profile");

		return view;
	}

	private void initiateGetProfilApi() {
		showProgress();
		String userId = "";
		if (getArguments() != null) {
			userId = getArguments().getString("userId");
		}

		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		params.putString("id", userId);
		params.putString("token", mStroageClass.getToken());
		params.putString("user_id", String.valueOf(mStroageClass.getUserId()));
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.GETPROFILE, params),
				StaticInfo.GETPROFILE_RESPONSE_CODE, this);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		myContext = activity;
		mStroageClass = StorageClass.getInstance(getActivity());
	}

	private void setUpViews(View view) {
		mlayoutContactInfo = (LinearLayout) view.findViewById(R.id.contactInfo);
		txtAddPhoto = (TextView) view.findViewById(R.id.photoUserTextView);
		mimgUserAvatar = (ImageView) view.findViewById(R.id.photoUserImageView);
		((TextView) view.findViewById(R.id.photoUserTextView))
				.setVisibility(View.GONE);
		userFullNameTextView = (ShimmerTextView) view
				.findViewById(R.id.userFullNameTextView);
		livesInTextView = (TextView) view.findViewById(R.id.livesInTextView);
		introductionTextView = (TextView) view
				.findViewById(R.id.introductionTextView);
		emailTextView = (TextView) view.findViewById(R.id.emailTextView);
		phoneTextView = (TextView) view.findViewById(R.id.phoneTextView);
		addressTextView = (TextView) view.findViewById(R.id.addressTextView);
		genderTextView = (TextView) view.findViewById(R.id.genderTextView);
		birthdayTextView = (TextView) view.findViewById(R.id.birthdayTextView);
		educationTextView = (TextView) view
				.findViewById(R.id.educationTextView);
		certificationTextView = (TextView) view
				.findViewById(R.id.certificationTextView);
		skillNameTextView = (TextView) view
				.findViewById(R.id.skillNameTextView);
		ratingTextView = (TextView) view.findViewById(R.id.ratingTextView);
		workingHourTextView = (TextView) view
				.findViewById(R.id.workingHourTextView);

		emaileCheckBox = (CheckBox) view.findViewById(R.id.emaileCheckBox);
		phoneCheckBox = (CheckBox) view.findViewById(R.id.phoneCheckBox);
		pushNotificationCheckBox = (CheckBox) view
				.findViewById(R.id.pushNotificationCheckBox);

		emaileCheckBox.setEnabled(false);
		phoneCheckBox.setEnabled(false);
		pushNotificationCheckBox.setEnabled(false);
		mContactMedium = (LinearLayout) view.findViewById(R.id.contactMedium);
		mContactMedium.setVisibility(View.GONE);
		mbtnBukMySkills = (Button) view.findViewById(R.id.btnBookMySkills);
		mbtnBukMySkills.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showContactMediumDialog();
			}

		});
	}

	@SuppressWarnings("rawtypes")
	private void showContactMediumDialog() {

		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
				getActivity());
		builderSingle.setTitle("Select Contact Medium");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.select_dialog_item);
		String[] itemsToShow = null;
		if (mUserInfo.getContactMedium() != null
				&& !(TextUtils.isEmpty(mUserInfo.getContactMedium()))) {
			String[] mArray = mUserInfo.getContactMedium().split(",");
			if (mArray != null) {
				itemsToShow = new String[mArray.length];
				itemsToShow = mArray;
			}
		}

		if (itemsToShow != null && itemsToShow.length > 0) {
			for (int i = 0; i < itemsToShow.length; i++) {
				arrayAdapter.add(itemsToShow[i]);
			}
		}

		builderSingle.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		if (itemsToShow != null && itemsToShow.length > 0) {
			builderSingle.setAdapter(arrayAdapter,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							String strName = arrayAdapter.getItem(which);
							if (strName.equalsIgnoreCase("email")) {
								Intent emailIntent = new Intent(
										Intent.ACTION_SENDTO, Uri.fromParts(
												"mailto", mUserInfo.getEmail(),
												null));
								emailIntent.putExtra(Intent.EXTRA_SUBJECT,
										"BMS: New Skill Request");
								startActivity(Intent.createChooser(emailIntent,
										"Send email..."));
							} else if (strName.equalsIgnoreCase("phone")) {
								if (mUserInfo != null) {
									if (mUserInfo.getMobile() != null
											&& TextUtils.isEmpty(mUserInfo
													.getMobile())) {
										Intent intent = new Intent(
												Intent.ACTION_CALL,
												Uri.parse("tel:" + "+"
														+ mUserInfo.getMobile()));
										startActivity(intent);
									} else {
										Intent intent = new Intent(
												Intent.ACTION_CALL,
												Uri.parse("tel:"
														+ "+34666777888"));
										startActivity(intent);
									}
								}
							} else if (strName
									.equalsIgnoreCase("push notification")) {
								navigateToComposeFragment();
							}
						}

					});
			builderSingle.show();
		}

	}

	private void navigateToComposeFragment() {
		ComposeEmailFragment nextFrag = new ComposeEmailFragment();
		String userId = "";
		if (getArguments() != null) {
			Bundle mBundle = new Bundle();
			userId = getArguments().getString("userId");
			mBundle.putString("userId", userId);
			nextFrag.setArguments(mBundle);
		}
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_container, nextFrag, "compose")
				.addToBackStack(null).commitAllowingStateLoss();
	}

	private void setUserInfo() {
		userFullNameTextView.setText(mUserInfo.getFirstName() + " "
				+ mUserInfo.getLastName());
		introductionTextView.setText(mUserInfo.getIntroduction());
		emailTextView.setText(mUserInfo.getEmail());
		phoneTextView.setText(mUserInfo.getMobile());
		educationTextView.setText(mUserInfo.getEducation());
		certificationTextView.setText(mUserInfo.getCertification());
		skillNameTextView.setText(mUserInfo.getSkill());
		addressTextView.setText(mUserInfo.getAddress());
		ratingTextView.setText(mUserInfo.getRating());
		workingHourTextView.setText(mUserInfo.getWorkingHour());

		if (mUserInfo.getLatitude() != null && mUserInfo.getLongitude() != null) {
			double lng = Double.parseDouble(mUserInfo.getLongitude());
			double lat = Double.parseDouble(mUserInfo.getLatitude());
			Geocoder geocoder;
			List<Address> addresses = null;
			geocoder = new Geocoder(getActivity(), Locale.getDefault());

			try {
				addresses = geocoder.getFromLocation(lat, lng, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (addresses != null) {
				String address = addresses.get(0).getAddressLine(0);
				String city = addresses.get(0).getLocality();
				String state = addresses.get(0).getAdminArea();
				String country = addresses.get(0).getCountryName();
				String postalCode = addresses.get(0).getPostalCode();
				String knownName = addresses.get(0).getFeatureName();
				livesInTextView.setText("Lives in " + city);
			}
		}

	}

	private void setUpListeners(View view) {
		view.findViewById(R.id.editProfileBtn).setVisibility(View.GONE);
		view.findViewById(R.id.editAboutMeBtn).setVisibility(View.GONE);
		view.findViewById(R.id.editContactInfoBtn).setVisibility(View.GONE);
		view.findViewById(R.id.editBasicInfoBtn).setVisibility(View.GONE);
		view.findViewById(R.id.editEducationInfoBtn).setVisibility(View.GONE);
		view.findViewById(R.id.editSkillInfoBtn).setVisibility(View.GONE);
		view.findViewById(R.id.editProfileBtn).setOnClickListener(this);
		view.findViewById(R.id.editAboutMeBtn).setOnClickListener(this);
		view.findViewById(R.id.editContactInfoBtn).setOnClickListener(this);
		view.findViewById(R.id.editBasicInfoBtn).setOnClickListener(this);
		view.findViewById(R.id.editEducationInfoBtn).setOnClickListener(this);
		view.findViewById(R.id.editSkillInfoBtn).setOnClickListener(this);
	}

	private void openDialog(VIEW_DIALOG view_dialog) {

		boolean wrapInScrollView = true;

		MaterialDialog.Builder materialDialogBuilder = new MaterialDialog.Builder(
				getActivity());
		materialDialogBuilder.positiveText("Save");
		materialDialogBuilder.negativeText("Cancel");

		switch (view_dialog) {
		case EDIT_PROFILE:
			materialDialogBuilder.title("Profile");
			materialDialogBuilder.customView(R.layout.dialog_profile,
					wrapInScrollView);
			materialDialogBuilder.callback(new MaterialDialog.ButtonCallback() {
				@Override
				public void onPositive(MaterialDialog dialog) {
					super.onPositive(dialog);
					String strFirstName = firstName.getText().toString();
					String strLastName = lastName.getText().toString();

					mUserInfo.setFirstName(strFirstName);
					mUserInfo.setLastName(strLastName);

					userFullNameTextView.setText(mUserInfo.getFirstName() + " "
							+ mUserInfo.getLastName());

					String livesInText = livesInEditText.getText().toString();
					livesInTextView.setText(livesInText);
				}
			});

			break;
		case ABOUT_ME:
			materialDialogBuilder.title("About Me");
			materialDialogBuilder.customView(R.layout.dialog_about_me,
					wrapInScrollView);
			materialDialogBuilder.callback(new MaterialDialog.ButtonCallback() {
				@Override
				public void onPositive(MaterialDialog dialog) {
					super.onPositive(dialog);

					String introductionText = introductionEditText.getText()
							.toString();
					introductionTextView.setText(introductionText);
					mUserInfo.setIntroduction(introductionText);
					initiateEditProfileApi();
				}
			});

			break;

		case CONTACT_INFO:
			materialDialogBuilder.title("Contact Information");
			materialDialogBuilder.customView(R.layout.dialog_contact_info,
					wrapInScrollView);
			materialDialogBuilder.callback(new MaterialDialog.ButtonCallback() {
				@Override
				public void onPositive(MaterialDialog dialog) {
					super.onPositive(dialog);

					String emailText = emailEditText.getText().toString();
					mUserInfo.setEmail(emailText);
					emailTextView.setText(emailText);

					String phoneText = phoneEditText.getText().toString();
					mUserInfo.setMobile(phoneText);
					phoneTextView.setText(phoneText);

					String addressText = addressEditText.getText().toString();
					mUserInfo.setAddress(addressText);
					addressTextView.setText(addressText);
					initiateEditProfileApi();
				}
			});

			break;

		case BASIC_INFO:
			materialDialogBuilder.title("Basic Information");
			materialDialogBuilder.customView(R.layout.dialog_basic_info,
					wrapInScrollView);

			materialDialogBuilder.callback(new MaterialDialog.ButtonCallback() {
				@Override
				public void onPositive(MaterialDialog dialog) {
					super.onPositive(dialog);

					String genderText = genderSpinner.getSelectedItem()
							.toString();
					genderTextView.setText(genderText);

					birthdayEditText
							.setOnFocusChangeListener(new View.OnFocusChangeListener() {
								@Override
								public void onFocusChange(View view, boolean b) {
									Calendar calendar = Calendar.getInstance();

									new DatePickerDialog(
											getActivity(),
											new DatePickerDialog.OnDateSetListener() {
												@Override
												public void onDateSet(
														DatePicker datePicker,
														int year, int month,
														int day) {
													birthdayEditText
															.setText(new StringBuilder()
																	.append(day)
																	.append("/")
																	.append(month + 1)
																	.append("/")
																	.append(year));
												}
											}, calendar.get(Calendar.YEAR),
											calendar.get(Calendar.MONTH),
											calendar.get(Calendar.DAY_OF_MONTH));
								}
							});

					String birthdayText = birthdayEditText.getText().toString();
					birthdayTextView.setText(birthdayText);
				}

			});

			break;

		case EDUCATION_INFO:
			materialDialogBuilder.title("Education Information");
			materialDialogBuilder.customView(R.layout.dialog_education_info,
					wrapInScrollView);

			materialDialogBuilder.callback(new MaterialDialog.ButtonCallback() {
				@Override
				public void onPositive(MaterialDialog dialog) {
					super.onPositive(dialog);

					String educationText = educationEditText.getText()
							.toString();
					educationTextView.setText(educationText);

					String certificationText = certificationEditText.getText()
							.toString();
					certificationTextView.setText(certificationText);

					mUserInfo.setEducation(educationText);
					mUserInfo.setCertification(certificationText);
					initiateEditProfileApi();

				}
			});

			break;

		case SKILL_INFO:
			materialDialogBuilder.title("Skill Information");
			materialDialogBuilder.customView(R.layout.dialog_skill_info,
					wrapInScrollView);

			materialDialogBuilder.callback(new MaterialDialog.ButtonCallback() {
				@Override
				public void onPositive(MaterialDialog dialog) {
					super.onPositive(dialog);

					String skillText = skillEditText.getText().toString();
					skillNameTextView.setText(skillText);

					String ratingText = ratingSpinner.getSelectedItem()
							.toString();
					ratingTextView.setText(ratingText);

					String workingHourText = workingHourSpinner
							.getSelectedItem().toString();
					workingHourTextView.setText(workingHourText);

				}
			});

			break;

		}

		MaterialDialog materialDialog = materialDialogBuilder.show();

		View view = materialDialog.getCustomView();

		switch (view_dialog) {

		case EDIT_PROFILE:
			firstName = (EditText) (view.findViewById(R.id.firstName));
			firstName.setText(mUserInfo.getFirstName());

			lastName = (EditText) (view.findViewById(R.id.lastName));
			lastName.setText(mUserInfo.getLastName());

			livesInEditText = (EditText) (view
					.findViewById(R.id.livesInEditText));
			livesInEditText.setText(livesInTextView.getText());

			break;

		case ABOUT_ME:
			introductionEditText = (EditText) (view
					.findViewById(R.id.introductionEditText));
			introductionEditText.setText(introductionTextView.getText());

			break;
		case CONTACT_INFO:
			emailEditText = (EditText) (view.findViewById(R.id.emailEditText));
			emailEditText.setText(emailTextView.getText());

			phoneEditText = (EditText) (view.findViewById(R.id.phoneEditText));
			phoneEditText.setText(phoneTextView.getText());

			addressEditText = (EditText) (view
					.findViewById(R.id.addressEditText));
			addressEditText.setText(addressTextView.getText());

			break;
		case BASIC_INFO:

			int position = Arrays.asList(genderArray).indexOf(
					genderTextView.getText());

			genderSpinner = (Spinner) (view.findViewById(R.id.genderSpinner));

			ArrayAdapter<String> adapter = new ArrayAdapter<>(
					this.getActivity(), android.R.layout.simple_spinner_item,
					genderArray);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			genderSpinner.setAdapter(adapter);
			genderSpinner.setSelection(position, true);

			birthdayEditText = (EditText) (view
					.findViewById(R.id.birthdayEditText));
			birthdayEditText.setText(mUserInfo.getDateOfBirth());
			birthdayEditText
					.setOnFocusChangeListener(new View.OnFocusChangeListener() {
						@Override
						public void onFocusChange(View view, boolean hasFocus) {

							if (hasFocus) {
								Calendar calendar = Calendar.getInstance();

								new DatePickerDialog(
										getActivity(),
										new DatePickerDialog.OnDateSetListener() {
											@Override
											public void onDateSet(
													DatePicker datePicker,
													int year, int month, int day) {
												birthdayEditText
														.setText(new StringBuilder()
																.append(month + 1)
																.append("-")
																.append(day)
																.append("-")
																.append(year)
																.append(" "));
											}
										}, calendar.get(Calendar.YEAR),
										calendar.get(Calendar.MONTH), calendar
												.get(Calendar.DAY_OF_MONTH))
										.show();
							}
						}
					});

			birthdayEditText.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Calendar calendar = Calendar.getInstance();

					new DatePickerDialog(getActivity(),
							new DatePickerDialog.OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker datePicker,
										int year, int month, int day) {
									birthdayEditText
											.setText(new StringBuilder()
													.append(month + 1)
													.append("-").append(day)
													.append("-").append(year)
													.append(" "));
								}
							}, calendar.get(Calendar.YEAR), calendar
									.get(Calendar.MONTH), calendar
									.get(Calendar.DAY_OF_MONTH)).show();
				}
			});

			break;
		case EDUCATION_INFO:

			educationEditText = (EditText) (view
					.findViewById(R.id.educationEditText));
			educationEditText.setText(educationTextView.getText());

			certificationEditText = (EditText) (view
					.findViewById(R.id.certificationEditText));
			certificationEditText.setText(certificationTextView.getText());

			break;
		case SKILL_INFO:

			skillEditText = (AutoCompleteTextView) (view
					.findViewById(R.id.skillEditText));
			String[] allSkills = getSkills();
			adapter = new ArrayAdapter<>(getActivity(),
					android.R.layout.simple_list_item_1, allSkills);
			skillEditText.setAdapter(adapter);
			skillEditText.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

				}
			});
			skillEditText.setThreshold(2);

			skillEditText.setText(skillNameTextView.getText());

			int ratingPos = Arrays.asList(ratingArray).indexOf(
					ratingTextView.getText());

			ratingSpinner = (Spinner) (view.findViewById(R.id.ratingSpinner));
			ArrayAdapter<String> ratingAdapter = new ArrayAdapter<String>(
					this.getActivity(), android.R.layout.simple_spinner_item,
					ratingArray);
			ratingAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			ratingSpinner.setAdapter(ratingAdapter);
			ratingSpinner.setSelection(ratingPos, true);

			int workingHourPos = Arrays.asList(workingHourArray).indexOf(
					workingHourTextView.getText());

			workingHourSpinner = (Spinner) (view
					.findViewById(R.id.workingHourSpinner));
			ArrayAdapter<String> workingHourAdapter = new ArrayAdapter<>(
					this.getActivity(), android.R.layout.simple_spinner_item,
					workingHourArray);
			workingHourAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			workingHourSpinner.setAdapter(workingHourAdapter);
			workingHourSpinner.setSelection(workingHourPos, true);
			break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.editProfileBtn:
			openDialog(VIEW_DIALOG.EDIT_PROFILE);
			break;
		case R.id.editAboutMeBtn:
			openDialog(VIEW_DIALOG.ABOUT_ME);
			break;
		case R.id.editContactInfoBtn:
			openDialog(VIEW_DIALOG.CONTACT_INFO);
			break;
		case R.id.editBasicInfoBtn:
			openDialog(VIEW_DIALOG.BASIC_INFO);
			break;
		case R.id.editEducationInfoBtn:
			openDialog(VIEW_DIALOG.EDUCATION_INFO);
			break;
		case R.id.editSkillInfoBtn:
			openDialog(VIEW_DIALOG.SKILL_INFO);
			break;
		}
	}

	public String[] getSkills() {

		ArrayList<SkillsModel> skillSet = mStroageClass.getSkillsArray();

		if (skillSet != null && skillSet.size() > 0) {
			String skills[] = new String[skillSet.size()];
			for (int i = 0; i < skillSet.size(); i++) {
				SkillsModel skill = skillSet.get(i);
				skills[i] = skill.getSkill();
			}
			return skills;
		} else {
			String[] mDummy = new String[0];
			return mDummy;
		}

	}

	@Override
	public void ErrorResponse(VolleyError error, int requestCode) {
		hideProgress();
	}

	@Override
	public void SuccessResponse(JSONObject response, int requestCode) {
		switch (requestCode) {
		case StaticInfo.GETPROFILE_RESPONSE_CODE:
			responseForGetProfileApi(response);
			break;
		case StaticInfo.EDITPROFILE_RESPONSE_CODE:
			responseForEditPorfileApi(response);
			break;
		default:
			break;
		}
	}

	private void responseForEditPorfileApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					initiateGetProfilApi();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void responseForGetProfileApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					JSONArray profileArray = response
							.optJSONArray(StaticInfo.PROFILE);
					if (profileArray != null) {
						JSONObject mProfileInfo = profileArray.optJSONObject(0);
						mUserInfo = new UserSearchModel();
						mUserInfo.setIntroduction(mProfileInfo
								.optString(StaticInfo.INTRODUCTION));
						mUserInfo.setPersonalDetail(mProfileInfo
								.optString(StaticInfo.PERSONAL_DETAIL));
						mUserInfo.setOtherQualification(mProfileInfo
								.optString(StaticInfo.OTHER_QUALIFICATION));
						mUserInfo.setCertification(mProfileInfo
								.optString(StaticInfo.CERTIFICATION));
						mUserInfo.setEducation(mProfileInfo
								.optString(StaticInfo.EDUCATION));
						mUserInfo.setState(mProfileInfo
								.optString(StaticInfo.STATE));
						mUserInfo.setCountry(mProfileInfo
								.optString(StaticInfo.COUNTRY));
						mUserInfo.setWebSite(mProfileInfo
								.optString(StaticInfo.WEBSITE));
						mUserInfo.setOrganisation(mProfileInfo
								.optString(StaticInfo.ORGANIZATION));
						mUserInfo
								.setContactMedium(mProfileInfo
										.optString(StaticInfo.PREFERRED_CONTACT_MEDIUM));
						mUserInfo.setMobile(mProfileInfo
								.optString(StaticInfo.MOBILE));
						mUserInfo.setPostalCode(mProfileInfo
								.optString(StaticInfo.POSTAL_CODE));
						mUserInfo.setAddress(mProfileInfo
								.optString(StaticInfo.ADDRESS));
						mUserInfo.setUserImage(mProfileInfo
								.optString(StaticInfo.IMAGE));
						mUserInfo.setAccuracy(mProfileInfo
								.optString(StaticInfo.ACCURACY));
						mUserInfo.setLongitude(mProfileInfo
								.optString(StaticInfo.LONGITUDE));
						mUserInfo.setLatitude(mProfileInfo
								.optString(StaticInfo.LATITUDE));
						mUserInfo.setDateOfBirth(mProfileInfo
								.optString(StaticInfo.DOB));

						if (mProfileInfo.optString("image") == null
								|| mProfileInfo.optString("image")
										.equalsIgnoreCase("null")
								|| TextUtils.isEmpty(mProfileInfo
										.optString("image"))) {

						} else {
							String mImageUrl = WebUtils.IMAGE_URL
									+ mProfileInfo.optString("image");
							mQuery.id(mimgUserAvatar).image(mImageUrl);
							txtAddPhoto.setVisibility(View.GONE);
						}

						String mSkills = mProfileInfo
								.optString(StaticInfo.SKILL);
						String[] mSkillsArray = mSkills.split("/");
						if (mSkillsArray != null) {
							for (int k = 0; k < mSkillsArray.length; k++) {
								if (k == 0)
									mUserInfo.setSkill(mSkillsArray[0]);
								if (k == 1)
									mUserInfo.setRating(mSkillsArray[1]
											.replace(",", ""));
								if (k == 2)
									mUserInfo.setWorkingHour(mSkillsArray[2]
											.replace(",", ""));
								if (k == 3)
									mUserInfo.setExperience(mSkillsArray[3]
											.replace(",", ""));
							}
						}
						mUserInfo.setUserType(mProfileInfo
								.optString(StaticInfo.USER_TYPE));
						mUserInfo.setEmail(mProfileInfo
								.optString(StaticInfo.EMAIL));
						mUserInfo.setUserName(mProfileInfo
								.optString(StaticInfo.USERNAME));
						mUserInfo.setLastName(mProfileInfo
								.optString(StaticInfo.LAST_NAME));
						mUserInfo.setFirstName(mProfileInfo
								.optString(StaticInfo.FIRST_NAME));
						mUserInfo.setId(Integer.parseInt(mProfileInfo
								.optString(StaticInfo.ID)));

						if (mUserInfo.getEmail() == null
								&& mUserInfo.getMobile() == null) {
							mlayoutContactInfo.setVisibility(View.GONE);
						} else {
							if (mUserInfo.getEmail().equalsIgnoreCase("null")
									&& mUserInfo.getMobile().equalsIgnoreCase(
											"null")) {
								mlayoutContactInfo.setVisibility(View.GONE);
							} else {
								if (TextUtils.isEmpty(mUserInfo.getEmail())
										&& TextUtils.isEmpty(mUserInfo
												.getMobile())) {
									mlayoutContactInfo.setVisibility(View.GONE);
								}
							}
						}

						setUserInfo();
					}
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void initiateEditProfileApi() {
		showProgress();
		String userId = "";
		if (getArguments() != null) {
			userId = getArguments().getString("userId");
		}

		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		params.putString("user_id", userId);
		params.putString("login_id", mStroageClass.getToken());
		params.putString("first_name", mUserInfo.getFirstName());
		params.putString("last_name", mUserInfo.getLastName());
		params.putString("country_id", "1");
		params.putString("state_id", "2");
		params.putString("city_id", "1");
		params.putString("address", mUserInfo.getAddress());
		params.putString("postal_code", mUserInfo.getPostalCode());
		params.putString("mobile", mUserInfo.getMobile());
		params.putString("device_email_id", mUserInfo.getEmail());
		params.putString("organization", mUserInfo.getOrganisation());
		params.putString("website", mUserInfo.getWebSite());
		TelephonyManager telephonyManager = (TelephonyManager) getActivity()
				.getSystemService(getActivity().TELEPHONY_SERVICE);
		params.putString("imei", String.valueOf(telephonyManager.getDeviceId()));
		params.putString("preferred_contact_medium",
				mUserInfo.getContactMedium());

		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.EDIT_PROFILE, params),
				StaticInfo.EDITPROFILE_RESPONSE_CODE, this);

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
}
