package com.bookmyskills.about;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookmyskills.R;
import com.customcontrols.multiplemodel.ModelPagerAdapter;
import com.customcontrols.multiplemodel.PagerManager;
import com.customcontrols.springindicator.ScrollerViewPager;
import com.customcontrols.springindicator.SpringIndicator;

public class AboutFragment extends Fragment {

	ScrollerViewPager viewPager;

	// Mandatory Constructor
	public AboutFragment() {

		// Thread.setDefaultUncaughtExceptionHandler(new
		// UnCaughtException(getActivity()));
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_about, container, false);

		viewPager = (ScrollerViewPager) view.findViewById(R.id.view_pager);
		SpringIndicator springIndicator = (SpringIndicator) view
				.findViewById(R.id.indicator);

		PagerManager manager = new PagerManager();
		manager.addFragment(new PrivacyPolicyFragment(), "Privacy Policy");
		manager.addFragment(new TermsConditionFragment(), "Terms And Condition");
		manager.addFragment(new ContactUsFragment(), "Contact Us");
		ModelPagerAdapter adapter = new ModelPagerAdapter(
				getChildFragmentManager(), manager);
		viewPager.setAdapter(adapter);
		viewPager.fixScrollSpeed();

		// just set viewPager
		springIndicator.setViewPager(viewPager);

		return view;
	}

}
