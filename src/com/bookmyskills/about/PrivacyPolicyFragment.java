package com.bookmyskills.about;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.bookmyskills.R;
import com.customcontrols.dialog.CustomDialog;
import com.utils.StaticInfo;
import com.utils.StaticUtils;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class PrivacyPolicyFragment extends Fragment implements IParseListener {

	private WebView mWebView;
	private String mPrivacyPolicyContent = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_privacy_policy,
				container, false);

		mWebView = (WebView) rootView.findViewById(R.id.webView);
		initiatePrivacyPolicyApi();

		return rootView;
	}

	private void initiatePrivacyPolicyApi() {
		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.GETPRIVACYPOLICY, params),
				StaticInfo.PRIVACY_POLICY_RESPONSE_CODE, this);
	}

	@Override
	public void ErrorResponse(VolleyError error, int requestCode) {

	}

	@Override
	public void SuccessResponse(JSONObject response, int requestCode) {
		switch (requestCode) {
		case StaticInfo.PRIVACY_POLICY_RESPONSE_CODE:
			responseForPrivacyPolicyApi(response);
			break;

		default:
			break;
		}
	}

	private void responseForPrivacyPolicyApi(JSONObject response) {
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					JSONArray mPrivacyPolicy = response
							.optJSONArray(StaticInfo.PRIVACY_POLICY);
					JSONObject mPolicyObj = mPrivacyPolicy.optJSONObject(0);
					if (mPolicyObj != null) {
						mPrivacyPolicyContent = mPolicyObj
								.optString(StaticInfo.CONTENT);
					}
				} else {
					CustomDialog myDialog = new CustomDialog(getActivity());
					myDialog.createDialog("Unable to authenticate", "Reason : "
							+ response.getString("status"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		StaticUtils.loadHtmlContent(mWebView, mPrivacyPolicyContent, 16);
	}
}
