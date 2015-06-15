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

	public HomeProfileFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home_profile, container,
				false);

		viewPager = (ScrollerViewPager) view.findViewById(R.id.view_pager);
		SpringIndicator springIndicator = (SpringIndicator) view
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

		return view;
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