package com.bookmskills.activity;

import android.animation.Animator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bookmyskills.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class SplashActivity extends Activity {

	ShimmerTextView tv;
	ImageView iv;
	Shimmer shimmer;

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

		shimmer = new Shimmer();

		shimmer.setRepeatCount(10).setDuration(800).setStartDelay(300)
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
}
