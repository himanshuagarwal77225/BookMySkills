package com.bookmyskills.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookmyskills.AppController;
import com.bookmyskills.R;
import com.bookmyskills.library.ConnectionDetector;
import com.bookmyskills.library.GlobalConst;
import com.bookmyskills.library.MyDialog;
import com.bookmyskills.model.SkillsModel;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class SplashActivity extends Activity {

	ShimmerTextView tv;
	ImageView iv;
	Shimmer shimmer;

	private static String TAG = SplashActivity.class.getSimpleName();

	public static ArrayList<SkillsModel> skillsSet = new ArrayList<SkillsModel>();

	// flag for Internet connection status
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;
	MyDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT < 16) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			View decorView = getWindow().getDecorView();
			// Hide the status bar.
			int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
			// Remember that you should never show the action bar if the
			// status bar is hidden, so hide that too if necessary.
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}

		setContentView(R.layout.activity_splash);

		tv = (ShimmerTextView) findViewById(R.id.shimmer_tv);
		
		dialog = new MyDialog(SplashActivity.this);

		shimmer = new Shimmer();

		shimmer.setRepeatCount(10).setStartDelay(300)
				.setDirection(Shimmer.ANIMATION_DIRECTION_LTR)
				.setAnimatorListener(new Animator.AnimatorListener() {

					@Override
					public void onAnimationStart(Animator animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animator animation) {
						// TODO Auto-generated method stub
						Intent i = new Intent(SplashActivity.this,
								MainActivity.class);
						SplashActivity.this.startActivity(i);
						SplashActivity.this.finish();
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animator animation) {
						// TODO Auto-generated method stub

					}
				});

		startAnimation();

		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			makeJsonObjectRequest();
		} else {
			dialog.successDiaglog("No Internet",
					"Internet not available,Please try again..");
		}
	}

	/**
	 * Method to make json object request where json response starts wtih {
	 * */
	private void makeJsonObjectRequest() {

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				GlobalConst.ServerBaseURL + "include/getSkills.php", null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());

						try {
							// Parsing json object response
							// response will be a json object

							JSONArray states = response.getJSONArray("skills");

							skillsSet = new ArrayList<SkillsModel>();

							for (int i = 0; i < states.length(); i++) {
								JSONObject skillsObj = (JSONObject) states
										.get(i);
								SkillsModel skill = new SkillsModel();
								skill.setId(skillsObj.getInt("id"));
								skill.setName(skillsObj.getString("name"));
								skillsSet.add(skill);
							}

							Log.i(TAG, skillsSet.size() + " Skill Size");

						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
									"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
						shimmer.setDuration(800);
						// hidepDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}

	private void startAnimation() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		RelativeLayout rel = (RelativeLayout) findViewById(R.id.main_layout);
		rel.clearAnimation();
		rel.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		anim.reset();
		iv = (ImageView) findViewById(R.id.logoImageView);
		iv.clearAnimation();
		iv.startAnimation(anim);

		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				iv.setVisibility(View.GONE);
				tv.setVisibility(View.VISIBLE);
				shimmer.start(tv);
				;
			}
		});

	}

	public class YourAsyncTaskGetSkills extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// show your dialog here

		}

		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

		}

	}

}
