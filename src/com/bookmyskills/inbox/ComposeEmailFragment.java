package com.bookmyskills.inbox;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.jsonclasses.IParseListener;
import com.android.jsonclasses.JSONRequestResponse;
import com.android.volley.VolleyError;
import com.bookmyskills.MainActivity;
import com.bookmyskills.R;
import com.models.UserSearchModel;
import com.utils.StaticInfo;
import com.utils.StorageClass;
import com.utils.network.MiscUtils;
import com.utils.network.WebUtils;

@SuppressLint("NewApi")
public class ComposeEmailFragment extends Fragment implements OnClickListener,
		IParseListener {

	private AutoCompleteTextView mtxtTo;
	private EditText mEditSubject;
	private EditText mEditBody;
	private Button mbtnSend;
	private View mRootView;
	private String selectedUserId = "";

	private StorageClass mStorageClass;
	private ArrayList<UserSearchModel> mArrayUsers = new ArrayList<UserSearchModel>();
	private Dialog dialog;

	public ComposeEmailFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.fragment_compose_mail, container,
				false);
		getReferences();
		mStorageClass = StorageClass.getInstance(getActivity());
		mArrayUsers = mStorageClass.getSearchUsersArray();
		setAdapter();
		return mRootView;
	}

	private void setAdapter() {
		String[] mUsers = getUsersArray();
		if (mUsers != null) {
			ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, mUsers);
			mtxtTo.setAdapter(adapter);
			mtxtTo.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					for (UserSearchModel mModel : mArrayUsers) {
						if (mtxtTo
								.getText()
								.toString()
								.equalsIgnoreCase(
										mModel.getFirstName() + " "
												+ mModel.getLastName())) {
							selectedUserId = String.valueOf(mModel.getId());
							break;
						}
					}
				}
			});
			mtxtTo.setThreshold(1);
		}
	}

	private String[] getUsersArray() {
		if (mArrayUsers != null) {
			String users[] = new String[mArrayUsers.size()];
			for (int i = 0; i < mArrayUsers.size(); i++) {
				UserSearchModel mModel = mArrayUsers.get(i);
				users[i] = mModel.getFirstName() + " " + mModel.getLastName();
			}
			return users;
		}
		return null;
	}

	private void getReferences() {
		mtxtTo = (AutoCompleteTextView) mRootView
				.findViewById(R.id.txtUserName);
		mEditSubject = (EditText) mRootView.findViewById(R.id.editSubject);
		mEditBody = (EditText) mRootView.findViewById(R.id.bodyEditText);
		mbtnSend = (Button) mRootView.findViewById(R.id.sendButton);
		mbtnSend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (TextUtils.isEmpty(selectedUserId)) {
			return;
		} else if (TextUtils.isEmpty(mEditSubject.getText().toString())) {
			return;
		} else if (TextUtils.isEmpty(mEditBody.getText().toString())) {
			return;
		} else {
			initSendMessageApi();
		}
	}

	private void initSendMessageApi() {
		showProgress();
		Bundle mParams = new Bundle();
		mParams.putString("user_id", String.valueOf(mStorageClass.getUserId()));
		mParams.putString("token", mStorageClass.getToken());
		mParams.putString("to_id", selectedUserId);
		mParams.putString("subject", mEditSubject.getText().toString());
		mParams.putString("body", mEditBody.getText().toString());
		mParams.putString("from_id", "");
		JSONRequestResponse mJsonRequestResponse = new JSONRequestResponse(
				getActivity());

		mJsonRequestResponse.getResponse(
				MiscUtils.encodeUrl(WebUtils.SEND_MESSAGE, mParams),
				StaticInfo.SEND_MESSAGE_RESPONSE_CODE, this);

	}

	@Override
	public void ErrorResponse(VolleyError error, int requestCode) {
		hideProgress();
	}

	@Override
	public void SuccessResponse(JSONObject response, int requestCode) {
		switch (requestCode) {
		case StaticInfo.SEND_MESSAGE_RESPONSE_CODE:
			resposneForSendMessageApi(response);
			break;

		default:
			break;
		}
	}

	private void resposneForSendMessageApi(JSONObject response) {
		hideProgress();
		if (response != null) {
			try {
				if (response.getString(StaticInfo.STATUS).equalsIgnoreCase(
						StaticInfo.SUCCESS_STRING)) {
					if (getActivity() != null
							&& getActivity() instanceof MainActivity) {
						String message = response.getString(StaticInfo.STATUS)
								.replaceAll("[0-9]", "");
						((MainActivity) getActivity())
								.showSuccessAlertDialog(message);
						getActivity().getSupportFragmentManager()
								.popBackStackImmediate();

					}
				} else {
					if (getActivity() != null
							&& getActivity() instanceof MainActivity) {
						String message = response.getString(StaticInfo.STATUS)
								.replaceAll("[0-9]", "");
						((MainActivity) getActivity())
								.showErrorAlertDialog(message);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			getActivity().finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
