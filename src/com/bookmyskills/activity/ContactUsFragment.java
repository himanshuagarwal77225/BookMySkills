package com.bookmyskills.activity;

import java.io.File;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bookmyskills.R;
import com.romainpiel.shimmer.ShimmerTextView;

@SuppressLint("NewApi")
public class ContactUsFragment extends Fragment {

	public ContactUsFragment() {
	}

	ShimmerTextView tv;
	private TextView webLinkTextView;
	private TextView emailTextView;
	View rootView;	
	private Context context;
	StringBuilder message;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_contact_us,
				container, false);
		
		tv = (ShimmerTextView) rootView.findViewById(R.id.shimmer_tv);
		webLinkTextView =(TextView)rootView.findViewById(R.id.webLinkTextView);
		emailTextView = (TextView)rootView.findViewById(R.id.emailTextView);
		context = getActivity();
		webLinkTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("http://www.bookmyskills.com");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		
		emailTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addInformation();
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "info@bookmyskills.com" });
				intent.putExtra(Intent.EXTRA_SUBJECT, "Query");
				intent.putExtra(Intent.EXTRA_TEXT, message.toString());
				startActivity(Intent.createChooser(intent, ""));
			}
		});
		
		return rootView;
	}
	
	private void addInformation() {
		message = new StringBuilder();
		Date curDate = new Date();
		message.append("\n**** Don't Write Below this.***");
		message.append("\n**** Start of current Report ***");
		message.append("\n\nSystem Report Log : ")
				.append(curDate.toString()).append('\n').append('\n');
		message.append("Informations :").append('\n');
		message.append("Locale: ").append(Locale.getDefault()).append('\n');
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi;
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			message.append("Version: ").append(pi.versionName).append('\n');
			message.append("Package: ").append(pi.packageName).append('\n');

		} catch (Exception e) {
			Log.e("CustomExceptionHandler", "Error", e);
			message.append("Could not get Version information for ").append(
					context.getPackageName());
		}
		message.append("Phone Model: ").append(android.os.Build.MODEL)
				.append('\n');
		message.append("Android Version: ")
				.append(android.os.Build.VERSION.RELEASE).append('\n');
		message.append("Board: ").append(android.os.Build.BOARD).append('\n');
		message.append("Brand: ").append(android.os.Build.BRAND).append('\n');
		message.append("Device: ").append(android.os.Build.DEVICE).append('\n');
		message.append("Host: ").append(android.os.Build.HOST).append('\n');
		message.append("ID: ").append(android.os.Build.ID).append('\n');
		message.append("Model: ").append(android.os.Build.MODEL).append('\n');
		message.append("Email ID: ").append(MainActivity.reg_account)
				.append('\n');
		message.append("Product: ").append(android.os.Build.PRODUCT)
				.append('\n');
		message.append("Type: ").append(android.os.Build.TYPE).append('\n');
		StatFs stat = getStatFs();
		message.append("Total Internal memory: ")
				.append(getTotalInternalMemorySize(stat)).append('\n');
		message.append("Available Internal memory: ")
				.append(getAvailableInternalMemorySize(stat)).append('\n');
		message.append('\n');
		message.append("**** End of current Report ***");
	}
	
	private StatFs getStatFs() {
		File path = Environment.getDataDirectory();
		return new StatFs(path.getPath());
	}

	private long getAvailableInternalMemorySize(StatFs stat) {
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	private long getTotalInternalMemorySize(StatFs stat) {
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}
}
