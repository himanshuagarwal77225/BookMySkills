package com.bookmskills.activity;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
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
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.ImageView;
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
	private String education[];
	private String certifications[];
	private String skills1;
	private String skills2;
	private Spinner lastUserdSpinner;
	private ArrayList<EditText> educationMoreEditText = new ArrayList<EditText>();
	private ArrayList<EditText> certificateMoreEditText = new ArrayList<EditText>();
	private ArrayList<EditText> skillsMoreEditText = new ArrayList<EditText>();
	private ArrayList<Spinner> skillRatingMoreSpinners = new ArrayList<Spinner>();
	private ArrayList<Spinner> skillLastUsedMoreSpinners = new ArrayList<Spinner>();
	private LinearLayout educationLayout;
	private LinearLayout certificationLayout;
	private LinearLayout skillsLayout;
	private LinearLayout skillsSubLayout;
	int educationid = 0;
	int certificationId = 0;
	int skillsRatingID = 0;
	int skillID = 0;
	int skillYearID = 0;

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
	private ImageView profilePicImageView;
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
	// Activity result key for camera
	static final int REQUEST_TAKE_PHOTO = 11111;
	private static final int IMAGE_PICKER_SELECT = 1;

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
		lastUserdSpinner = (Spinner) rootView
				.findViewById(R.id.lastUserdSpinner);
		educationLayout = (LinearLayout) rootView
				.findViewById(R.id.educationLayout);
		certificationLayout = (LinearLayout) rootView
				.findViewById(R.id.certificationLayout);
		skillsLayout = (LinearLayout) rootView.findViewById(R.id.skillsLayout);
		skillsSubLayout = (LinearLayout) rootView
				.findViewById(R.id.skillsSubLayout);
		lastUsedSpinnerPopulate();

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
		profilePicImageView = (ImageView) rootView
				.findViewById(R.id.profilePicImageView);

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
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

				skillsSubLayout.setLayoutParams(params);
				skillsLayout.setLayoutParams(params);
				skillsSubLayout.addView(addMoreEditText("Skills"));
				skillsSubLayout.addView(addMoreSpinner("Rating"));
				skillsSubLayout.addView(addMoreSpinner("Last Used"));
				
				if (skillsLayout != null) {
				    ViewGroup parent = (ViewGroup) skillsLayout.getParent();
				    if (parent != null) {
				        parent.removeView(skillsLayout);
				    }
				}
				
				
				skillsLayout.addView(skillsSubLayout);
			}
		});

		dobEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction ft = myContext.getSupportFragmentManager()
						.beginTransaction();
				DialogFragment newFragment = new DatePickerDialogFragment(
						pickerListener);
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

		education = new String[educationMoreEditText.size() + 1];
		education[0] = educationEditText.getText().toString().trim();
		certifications = new String[certificateMoreEditText.size() + 1];
		certifications[0] = certificationsEditText.getText().toString().trim();
		skills1 = skills1EditText.getText().toString().trim();
		skills2 = skills2EditText.getText().toString().trim();

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

		if (education != null && !TextUtils.isEmpty(education[0])) {
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
			return new DatePickerDialog(getActivity(), pickerListener, year,
					month, day);
		}
		return null;
	}

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

	public void cameraDialog() {
		AlertDialog.Builder getImageFrom = new AlertDialog.Builder(
				getActivity());
		getImageFrom.setTitle("Select:");
		final CharSequence[] opsChars = { "Take Picture", "Open Gallery",
				"Cancel" };
		getImageFrom.setItems(opsChars,
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							dispatchTakePictureIntent();
						} else if (which == 1) {
							Intent i = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
				Uri fileUri = Uri.fromFile(photoFile);
				activity.setCapturedImageURI(fileUri);
				activity.setCurrentPhotoPath(fileUri.getPath());
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
			addPhotoToGallery();
			MainActivity activity = (MainActivity) getActivity();
			// Show the full sized image.
			setFullImageFromFilePath(activity.getCurrentPhotoPath(),
					profilePicImageView);

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
	 * @throws IOException
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
		MainActivity activity = (MainActivity) getActivity();
		activity.setCurrentPhotoPath("file:" + image.getAbsolutePath());
		return image;
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

	/**
	 * Add the picture to the photo gallery. Must be called on all camera images
	 * or they will disappear once taken.
	 */
	protected void addPhotoToGallery() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		MainActivity activity = (MainActivity) getActivity();
		File f = new File(activity.getCurrentPhotoPath());
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.getActivity().sendBroadcast(mediaScanIntent);
	}

	/**
	 * Scale the photo down and fit it to our image views.
	 * 
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
	}

	public EditText addMoreEditText(String hint) {

		EditText editText = new EditText(getActivity());
		if (hint.equalsIgnoreCase("Qualification")) {
			editText.setId(educationid);
			editText.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.education, 0, 0, 0);
			educationMoreEditText.add(editText);
			educationid++;
			editText.setHint(hint + " " + educationid);
			LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			editTextLayoutParams.setMargins(10, 10, 10, 10);
			editText.setLayoutParams(editTextLayoutParams);
			editText.setPadding(10, 10, 10, 10);
			editText.setCompoundDrawablePadding(10);
		} else if (hint.equalsIgnoreCase("Certification")) {
			editText.setId(certificationId);
			editText.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.certificate, 0, 0, 0);
			certificateMoreEditText.add(editText);
			certificationId++;
			editText.setHint(hint + " " + certificationId);
			LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			editTextLayoutParams.setMargins(10, 10, 10, 10);
			editText.setLayoutParams(editTextLayoutParams);
			editText.setPadding(10, 10, 10, 10);
			editText.setCompoundDrawablePadding(10);
		} else if (hint.equalsIgnoreCase("Skills")) {
			editText.setId(skillID);
			editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.skills,
					0, 0, 0);
			skillsMoreEditText.add(editText);
			skillID++;
			editText.setHint(hint + " " + skillID);
			LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0.2f);
			editTextLayoutParams.setMargins(10, 10, 10, 10);
			editText.setLayoutParams(editTextLayoutParams);
			editText.setPadding(10, 10, 10, 10);
			editText.setCompoundDrawablePadding(10);
		}

		return editText;
	}

	public Spinner addMoreSpinner(String hint) {

		Spinner spinner = new Spinner(getActivity());
		if (hint.equalsIgnoreCase("Rating")) {
			spinner.setId(skillsRatingID);
			skillRatingMoreSpinners.add(spinner);
			skillsRatingID++;
			spinner.setPromptId(R.string.rating_prompt);
			ArrayAdapter<CharSequence> adapter;
			adapter = ArrayAdapter.createFromResource(getActivity(),
					R.array.rating_array, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);

		} else if (hint.equalsIgnoreCase("Last Used")) {
			spinner.setId(skillYearID);
			skillLastUsedMoreSpinners.add(spinner);
			skillYearID++;
			spinner.setPromptId(R.string.last_used);
			ArrayList<String> years = new ArrayList<String>();
			int thisYear = Calendar.getInstance().get(Calendar.YEAR);
			for (int i = thisYear; i >= 1900; i--) {
				years.add(Integer.toString(i));
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_spinner_item, years);
			spinner.setAdapter(adapter);
		}
		LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0.4f);
		spinner.setLayoutParams(editTextLayoutParams);

		return spinner;
	}

}
