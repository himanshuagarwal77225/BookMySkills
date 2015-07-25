package com.bookmyskills.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookmyskills.R;
import com.customcontrols.multiplemodel.ModelPagerAdapter;
import com.customcontrols.multiplemodel.PagerManager;
import com.customcontrols.springindicator.ScrollerViewPager;
import com.customcontrols.springindicator.SpringIndicator;

public class HomeProfileFragment extends Fragment implements
		OnPageChangeListener {

	private ScrollerViewPager viewPager;
	private MyHomeFragment mObjHome;
	private SearchUserFragment mObjSearchUser;
	private View mRootView;

	public HomeProfileFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.fragment_home_profile, container,
				false);

		return mRootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (mRootView != null) {
			viewPager = (ScrollerViewPager) mRootView
					.findViewById(R.id.view_pager);
			SpringIndicator springIndicator = (SpringIndicator) mRootView
					.findViewById(R.id.indicator);

			PagerManager manager = new PagerManager();
			mObjHome = new MyHomeFragment();
			mObjSearchUser = new SearchUserFragment();
			manager.addFragment(mObjHome, "Home");
			manager.addFragment(mObjSearchUser, "Profile");
			ModelPagerAdapter adapter = new ModelPagerAdapter(
					getChildFragmentManager(), manager);
			viewPager.setAdapter(adapter);
			viewPager.fixScrollSpeed();
			springIndicator.setViewPager(viewPager);
			springIndicator.setOnPageChangeListener(this);

		} else {
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == 1) {
			mObjSearchUser.loadData();
		}
	}

}
