package com.bookmyskills.library;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.bookmyskills.R;
import com.bookmyskills.activity.MainActivity;

public class MyDialog {
	
	Context mContext;
	public MyDialog(Context mContext){
		this.mContext = mContext;
	}

	
	public void successDiaglog(String title, String message) {
		final Dialog dialog = new Dialog(mContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.info_dialog);
		// dialog.setTitle("Title...");

		// set the custom dialog components - text, image and button

		TextView okButton = (TextView) dialog.findViewById(R.id.okTextView);
		TextView titleTextView = (TextView) dialog
				.findViewById(R.id.titleTextView);
		TextView msgTextView = (TextView) dialog.findViewById(R.id.msgTextView);

		titleTextView.setText(title);
		msgTextView.setText(message);
		okButton.setPaintFlags(okButton.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);
		okButton.setText("OK");

		// if button is clicked, close the custom dialog
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});

		dialog.show();
	}
}
