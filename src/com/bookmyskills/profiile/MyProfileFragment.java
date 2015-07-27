package com.bookmyskills.profiile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.androidquery.AQuery;
import com.bookmyskills.MainActivity;
import com.bookmyskills.R;
import com.customcontrols.fadingactionbar.FadingActionBarHelper;
import com.customcontrols.shimmertextview.ShimmerTextView;
import com.models.SkillsModel;
import com.models.UserSearchModel;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class MyProfileFragment extends android.support.v4.app.Fragment
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
	private CheckBox emaileCheckBox;
	private CheckBox phoneCheckBox;
	private CheckBox pushNotificationCheckBox;

	static final int REQUEST_TAKE_PHOTO = 11111;
	private static final int IMAGE_PICKER_SELECT = 1;

	private ImageView mimgUserAvatar;
private TextView txtAddPhoto;
	private String currentPhotoPath = "";

	Spinner genderSpinner = null, ratingSpinner = null,
			workingHourSpinner = null;

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

	private enum VIEW_DIALOG {
		EDIT_PROFILE, ABOUT_ME, CONTACT_INFO, BASIC_INFO, EDUCATION_INFO, SKILL_INFO
	}

	public MyProfileFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

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

		mFadingHelper = new FadingActionBarHelper()
				.actionBarBackground(R.drawable.bms_background)
				.headerLayout(R.layout.bms_profile_header)
				.contentLayout(R.layout.fragment_profile);
		mFadingHelper.initActionBar(getActivity());

		myContext = activity;
		mStroageClass = StorageClass.getInstance(getActivity());
	}

	private void setUpViews(View view) {
		txtAddPhoto = (TextView) view.findViewById(R.id.photoUserTextView);
		mimgUserAvatar = (ImageView) view.findViewById(R.id.photoUserImageView);
		mimgUserAvatar.setOnClickListener(this);
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
		emaileCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

					}
				});
	}

	private void setUserInfo() {
		userFullNameTextView.setText(mUserInfo.getFirstName() + " "
				+ mUserInfo.getLastName());
		if (mUserInfo.getIntroduction() == null
				|| mUserInfo.getIntroduction().equalsIgnoreCase("null")) {
			introductionTextView.setText("");
		} else {
			introductionTextView.setText(mUserInfo.getIntroduction());
		}

		if (mUserInfo.getGender() == null
				|| mUserInfo.getGender().equalsIgnoreCase("null")
				|| TextUtils.isEmpty(mUserInfo.getGender())) {

		} else {
			genderTextView.setText(mUserInfo.getGender());
		}

		emailTextView.setText(mUserInfo.getEmail());
		phoneTextView.setText(mUserInfo.getMobile());
		if (mUserInfo.getEducation() == null
				|| mUserInfo.getEducation().equalsIgnoreCase("null")) {
			educationTextView.setText("");
		} else {
			educationTextView.setText(mUserInfo.getEducation());
		}
		if (mUserInfo.getCertification() == null
				|| mUserInfo.getCertification().equalsIgnoreCase("null")) {
			certificationTextView.setText("");
		} else {
			certificationTextView.setText(mUserInfo.getCertification());
		}
		skillNameTextView.setText(mUserInfo.getSkill());
		addressTextView.setText(mUserInfo.getAddress());
		ratingTextView.setText(mUserInfo.getRating());
		workingHourTextView.setText(mUserInfo.getWorkingHour());
		birthdayTextView.setText(mUserInfo.getDateOfBirth());

		if (mUserInfo.getLatitude() != null && mUserInfo.getLongitude() != null) {
			if (TextUtils.isEmpty(mUserInfo.getLongitude())
					|| TextUtils.isEmpty(mUserInfo.getLatitude())
					|| mUserInfo.getLatitude().equalsIgnoreCase("null")
					|| mUserInfo.getLongitude().equalsIgnoreCase("null")) {
				livesInTextView.setText("");
			} else {
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
				if (addresses != null && addresses.size() != 0) {
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

		if (mUserInfo.getContactMedium() != null) {
			String[] mArray = mUserInfo.getContactMedium().split(",");
			if (mArray != null) {
				for (int i = 0; i < mArray.length; i++) {
					if (mArray[i].equalsIgnoreCase("email")) {
						emaileCheckBox.setChecked(true);
					} else if (mArray[i].equalsIgnoreCase("phone")) {
						phoneCheckBox.setChecked(true);
					} else if (mArray[i].equalsIgnoreCase("push notification")) {
						pushNotificationCheckBox.setChecked(true);
					}
				}
			}
		}

	}

	private void setUpListeners(View view) {
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

					initiateEditProfileApi();
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

					// birthdayEditText
					// .setOnFocusChangeListener(new
					// View.OnFocusChangeListener() {
					// @Override
					// public void onFocusChange(View view, boolean b) {
					// Calendar calendar = Calendar.getInstance();
					//
					// new DatePickerDialog(
					// getActivity(),
					// new DatePickerDialog.OnDateSetListener() {
					// @Override
					// public void onDateSet(
					// DatePicker datePicker,
					// int year, int month,
					// int day) {
					// birthdayEditText
					// .setText(new StringBuilder()
					// .append(day)
					// .append("/")
					// .append(month + 1)
					// .append("/")
					// .append(year));
					// }
					// }, calendar.get(Calendar.YEAR),
					// calendar.get(Calendar.MONTH),
					// calendar.get(Calendar.DAY_OF_MONTH));
					// }
					// });

					String birthdayText = birthdayEditText.getText().toString();
					mUserInfo.setDateOfBirth(birthdayText);
					birthdayTextView.setText(birthdayText);
					// birthdayTextView.clearFocus();
					initiateEditProfileApi();
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
					mUserInfo.setEducation(educationText);

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
			emailEditText.setEnabled(false);
			emailEditText.setFocusable(false);
			phoneEditText = (EditText) (view.findViewById(R.id.phoneEditText));
			phoneEditText.setText(phoneTextView.getText());
			phoneEditText.requestFocus();
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
			// birthdayEditText.clearFocus();
			// birthdayEditText
			// .setOnFocusChangeListener(new View.OnFocusChangeListener() {
			// @Override
			// public void onFocusChange(View view, boolean hasFocus) {
			//
			// if (hasFocus) {
			// Calendar calendar = Calendar.getInstance();
			//
			// new DatePickerDialog(
			// getActivity(),
			// new DatePickerDialog.OnDateSetListener() {
			// @Override
			// public void onDateSet(
			// DatePicker datePicker,
			// int year, int month, int day) {
			// birthdayEditText
			// .setText(new StringBuilder()
			// .append(month + 1)
			// .append("-")
			// .append(day)
			// .append("-")
			// .append(year)
			// .append(" "));
			// }
			// }, calendar.get(Calendar.YEAR),
			// calendar.get(Calendar.MONTH), calendar
			// .get(Calendar.DAY_OF_MONTH))
			// .show();
			// }
			// }
			// });
			birthdayEditText.setText(mUserInfo.getDateOfBirth());

			birthdayEditText.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Calendar calendar = Calendar.getInstance();

					new DatePickerDialog(getActivity(),
							new DatePickerDialog.OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker datePicker,
										int year, int month, int day) {
									// birthdayEditText
									// .setText(new StringBuilder()
									// .append(month + 1)
									// .append("-").append(day)
									// .append("-").append(year)
									// .append(" "));

									birthdayEditText
											.setText(new StringBuilder()
													.append(year).append("/")
													.append(month + 1)
													.append("/").append(day));
									mUserInfo.setDateOfBirth(birthdayEditText
											.getText().toString());
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
		case R.id.photoUserImageView:
			imageProfileClicked();
			break;
		}
	}

	private void imageProfileClicked() {
		openCameraDialog();
	}

	private void openCameraDialog() {
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
				Uri fileUri = Uri.fromFile(photoFile);
				activity.setCapturedImageURI(fileUri);
				activity.setCurrentPhotoPath(fileUri.getPath());
				currentPhotoPath = fileUri.getPath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						activity.getCapturedImageURI());
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
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
			MainActivity activity = (MainActivity) getActivity();
			// Show the full sized image.
			setFullImageFromFilePath(activity.getCurrentPhotoPath(),
					mimgUserAvatar);

		} else if (requestCode == IMAGE_PICKER_SELECT
				&& resultCode == Activity.RESULT_OK) {
			String path = getPathFromCameraData(data, this.getActivity());
			Log.i("PICTURE", "Path: " + path);
			if (path != null) {
				setFullImageFromFilePath(path, mimgUserAvatar);
			}
		} else {
			Toast.makeText(getActivity(), "Image Capture Failed",
					Toast.LENGTH_SHORT).show();
		}
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
		currentPhotoPath = imagePath;
		initiateEditProfileApi();
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
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					initiateGetProfilApi(false);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void initiateGetProfilApi(boolean b) {
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
						if (mProfileInfo.has("gender")) {
							mUserInfo.setGender(mProfileInfo
									.optString("gender"));
						}

						mUserInfo.setLatitude(mProfileInfo
								.optString(StaticInfo.LATITUDE));
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
						mUserInfo.setDateOfBirth(mProfileInfo
								.optString(StaticInfo.DOB));

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
		if (mUserInfo != null) {
			JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
					getActivity());
			Bundle params = new Bundle();
			params.putString("user_id", userId);
			params.putString("token", mStroageClass.getToken());

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
			params.putString("introduction", mUserInfo.getIntroduction());
			params.putString("education", mUserInfo.getEducation());
			params.putString("certification", mUserInfo.getCertification());
			params.putString("dob", mUserInfo.getDateOfBirth());
			params.putString("gender", genderTextView.getText().toString());

			TelephonyManager telephonyManager = (TelephonyManager) getActivity()
					.getSystemService(getActivity().TELEPHONY_SERVICE);
			params.putString("imei",
					String.valueOf(telephonyManager.getDeviceId()));
			params.putString("preferred_contact_medium",
					mUserInfo.getContactMedium());

			HashMap<String, File> mAttachFileList = new HashMap<String, File>();
			if (currentPhotoPath != null
					&& !(TextUtils.isEmpty(currentPhotoPath))) {
				File mFile = new File(currentPhotoPath);
				mAttachFileList.put("image", mFile);
				// mJsonRequestResponse.setFile("image", currentPhotoPath);
				mJsonRequestResponse.setAttachFileList(mAttachFileList);
			}

			mJsonRequestResponse.getResponse(
					MiscUtils.encodeUrl(WebUtils.EDIT_PROFILE, params),
					StaticInfo.EDITPROFILE_RESPONSE_CODE, this);
		}

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
