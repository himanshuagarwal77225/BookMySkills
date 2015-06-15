package com.bookmyskills.home;

import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bookmyskills.MainActivity;
import com.bookmyskills.R;
import com.bookmyskills.adapters.UserProfileAdapter;
import com.bookmyskills.profiile.MyProfileFragment;
import com.bookmyskills.profiile.ViewProfileFragment;
import com.models.UserSearchModel;
import com.utils.StorageClass;

public class SearchUserFragment extends Fragment implements OnItemClickListener {

	private ListView mUsersListView;
	private ArrayList<UserSearchModel> mArrayUsers = new ArrayList<UserSearchModel>();
	private UserProfileAdapter adapter;
	private StorageClass mStorageClass;
	private Dialog dialog;

	public SearchUserFragment(ArrayList<UserSearchModel> mArrayUsers2) {
		this.mArrayUsers = mArrayUsers2;
	}

	public SearchUserFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new UserProfileAdapter(getActivity());
		mStorageClass = StorageClass.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_user, container,
				false);
		mUsersListView = (ListView) view
				.findViewById(R.id.searchUserRecyclerView);
		mUsersListView.setOnItemClickListener(this);
		mUsersListView.setAdapter(adapter);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void loadData() {
		if (mStorageClass == null)
			mStorageClass = StorageClass.getInstance(getActivity());
		mArrayUsers = mStorageClass.getSearchUsersArray();
		if (mArrayUsers == null)
			mArrayUsers = new ArrayList<UserSearchModel>();
		if (adapter != null)
			adapter.addItems(mArrayUsers);
	}

	@Override
	public void onStart() {
		super.onStart();
		loadData();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mStorageClass.getUserLoggedIn()) {
			Bundle mBundle = new Bundle();
			mBundle.putString("userId",
					String.valueOf(adapter.getItem(position).getId()));
			ViewProfileFragment nextFrag = new ViewProfileFragment();
			nextFrag.setArguments(mBundle);
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.frame_container, nextFrag, "message")
					.addToBackStack(null).commitAllowingStateLoss();
		} else {
			((MainActivity) getActivity()).loadFragment(1);
		}
	}

}
