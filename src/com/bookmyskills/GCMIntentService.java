/*
 * Copyright 2012 Google Inc. Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.bookmyskills;

import static com.google.android.gcm.CommonUtilities.SENDER_ID;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gcm.CommonUtilities;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.ServerUtilities;
import com.utils.StorageClass;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	private static NotificationManager notificationManager;
	public final static int NOTIFICATION_ID = 100;

	private static final String TAG = "GCMIntentService";
	private int startId;

	// static JSONObject mJsonObject;

	public GCMIntentService() {
		super(SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);

		StorageClass userUtils = StorageClass.getInstance(context);
		userUtils.setDeviceToken(registrationId);
		ServerUtilities.register(context, registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			ServerUtilities.unregister(context, registrationId);
		} else {
			// This callback results from the call to unregister made on
			// ServerUtilities when the registration to the server failed.
			Log.i(TAG, "Ignoring unregister callback");
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		this.startId = startId;
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	protected void onMessage(final Context context, final Intent intent) {
		if (intent != null) {
			Bundle b = intent.getExtras();
			if (b != null) {
				String message_noti = b.getString("message");
				Intent notificationIntent = new Intent(context,
						MainActivity.class);
				generateNotification(getApplicationContext(), message_noti,
						"0", notificationIntent);
			}
		}
	}

	public JSONObject getotherparam(Bundle mPushBundle) {
		JSONObject jsonObject = new JSONObject();
		if (mPushBundle != null) {
			Set<String> set = mPushBundle.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = mPushBundle.get(key);
				try {
					jsonObject.put(key, value);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return jsonObject;
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");

	}

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.i(TAG, "Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

	private static void generateNotification(Context context, String message,
			String number, Intent notificationIntent) {

		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		String title = context.getString(R.string.app_name);

		int i = 0;
		if (!TextUtils.isEmpty(number) && TextUtils.isDigitsOnly(number)) {
			try {
				i = Integer.parseInt(number);
			} catch (NumberFormatException e) {
			}
		}
		NotificationCompat.Builder noti = new NotificationCompat.Builder(
				context).setContentTitle(title).setContentText(message)
				.setSmallIcon(icon).setAutoCancel(true).setWhen(when);
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context,
				NOTIFICATION_ID, notificationIntent, 0);
		noti.setContentIntent(intent);
		Notification notification = noti.build();
		notificationManager.notify(NOTIFICATION_ID, notification);
	}

}
