package com.bookmyskills;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.customcontrols.shimmertextview.Shimmer;
import com.customcontrols.shimmertextview.ShimmerTextView;
import com.google.android.gcm.CommonUtilities;
import com.google.android.gcm.GCMRegistrar;
import com.models.SkillsModel;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.UnCaughtException;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

public class SplashActivity extends ActionBarActivity implements IParseListener {

	private ShimmerTextView mtxtShimmer;
	private ImageView mImgLogo;
	private FrameLayout mLayoutContent;
	private Shimmer mShimmer;
	private InternalApp mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(
		// SplashActivity.this));
		mApplication = (InternalApp) getApplication();
		if (!mApplication.isTabletLayout()) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			// Automatically registers application on startup.
			GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
		}

		initiateSkillsApi();
		hideActionbar();
		getAllReferences();
		loadControls();
		loadAnimation();
	}

	@SuppressLint("NewApi")
	private void hideActionbar() {
		getSupportActionBar().hide();
		MiscUtils.setStatusBarColor(this,
				getResources().getColor(R.color.app_color));
	}

	private void initiateSkillsApi() {
		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(this);
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.GETSKILL, new Bundle()),
				StaticInfo.GETSKILLS_RESPONSE_CODE, this);
	}

	private void loadControls() {
		mShimmer = new Shimmer();
		mShimmer.setRepeatCount(10).setStartDelay(300)
				.setDirection(Shimmer.ANIMATION_DIRECTION_LTR)
				.setAnimatorListener(new AnimatorListener() {

					@Override
					public void onAnimationStart(Animator arg0) {

					}

					@Override
					public void onAnimationRepeat(Animator arg0) {

					}

					@Override
					public void onAnimationEnd(Animator arg0) {
						navigateToMainActivity();
					}

					@Override
					public void onAnimationCancel(Animator arg0) {

					}
				});
	}

	private void getAllReferences() {
		mtxtShimmer = (ShimmerTextView) findViewById(R.id.txtShimmer);
		mImgLogo = (ImageView) findViewById(R.id.imgLogo);
		mLayoutContent = (FrameLayout) findViewById(R.id.mainContent);
	}

	private void loadAnimation() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		mLayoutContent.clearAnimation();
		mLayoutContent.startAnimation(anim);
		anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		anim.reset();
		mImgLogo.clearAnimation();
		mImgLogo.startAnimation(anim);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mImgLogo.setVisibility(View.GONE);
				mtxtShimmer.setVisibility(View.VISIBLE);
				mShimmer.start(mtxtShimmer);
			}
		});

	}

	private void navigateToMainActivity() {
		Intent mIntent = new Intent(this, MainActivity.class);
		startActivity(mIntent);
		finish();
	}

	@Override
	public void ErrorResponse(VolleyError error, int requestCode) {

	}

	@Override
	public void SuccessResponse(JSONObject response, int requestCode) {
		switch (requestCode) {
		case StaticInfo.GETSKILLS_RESPONSE_CODE:
			responseForSkillsApi(response);
			break;

		default:
			break;
		}
	}

	private void responseForSkillsApi(JSONObject response) {
		if (response != null) {
			if (response.optString(StaticInfo.STATUS).equalsIgnoreCase(
					StaticInfo.SUCCESS_STRING)) {
				ArrayList<SkillsModel> responseSkillsArray = new ArrayList<SkillsModel>();
				JSONArray skillsArray = null;
				try {
					skillsArray = response.getJSONArray(StaticInfo.SKILL);
					if (skillsArray != null) {
						for (int i = 0; i < skillsArray.length(); i++) {
							JSONObject skillsObj = (JSONObject) skillsArray
									.get(i);
							SkillsModel mSkillModel = new SkillsModel();
							mSkillModel.setId(skillsObj.getInt(StaticInfo.ID));
							mSkillModel.setType(skillsObj
									.getString(StaticInfo.TYPE));
							mSkillModel
									.setOrd(skillsObj.getInt(StaticInfo.ORD));
							mSkillModel.setDate(skillsObj
									.getString(StaticInfo.CREATE_ON));
							mSkillModel.setSkill(skillsObj
									.getString(StaticInfo.SKILL));
							mSkillModel.setStatus(skillsObj
									.getString(StaticInfo.STATUS));
							responseSkillsArray.add(mSkillModel);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				StorageClass.getInstance(this).setSkillsArray(
						responseSkillsArray);
			}
		}
	}

}
