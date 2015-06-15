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
public class TermsConditionFragment extends Fragment implements IParseListener {

	private WebView mWebView;
	private String mTermsConditions = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_terms_conditions,
				container, false);
		mWebView = (WebView) rootView.findViewById(R.id.webView);
		initiateTermsConditionsApi();

		return rootView;
	}

	private void initiateTermsConditionsApi() {
		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());
		Bundle params = new Bundle();
		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.GETTERMSCONDITIONS, params),
				StaticInfo.TERMS_CONDITIONS_RESPONSE_CODE, this);
	}

	@Override
	public void ErrorResponse(VolleyError error, int requestCode) {

	}

	@Override
	public void SuccessResponse(JSONObject response, int requestCode) {
		switch (requestCode) {
		case StaticInfo.TERMS_CONDITIONS_RESPONSE_CODE:
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
					JSONArray mTerms = response
							.optJSONArray(StaticInfo.TERMS_CONDITIONS);
					JSONObject mTermsObj = mTerms.optJSONObject(0);
					if (mTermsObj != null) {
						mTermsConditions = mTermsObj
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

		StaticUtils.loadHtmlContent(mWebView, mTermsConditions, 16);
	}
}
