//package com.bookmyskills.adapters;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bookmyskills.R;
//import com.models.InboxModel;
//
///**
// * Created by Himanshu on 17/05/15.
// */
//public class InboxAdapter extends BaseAdapter {
//
//	ArrayList<InboxModel> mMessagesArray = new ArrayList<InboxModel>();
//	private Context mContext;
//
//	public InboxAdapter(Context mContext) {
//		this.mContext = mContext;
//	}
//
//	public void clear() {
//		this.mMessagesArray.clear();
//	}
//
//	public void addItems(ArrayList<InboxModel> mArrayUsers) {
//		this.mMessagesArray = mArrayUsers;
//		notifyDataSetChanged();
//	}
//
//	@Override
//	public int getCount() {
//		return mMessagesArray.size();
//	}
//
//	@Override
//	public InboxModel getItem(int position) {
//		return mMessagesArray.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return 0;
//	}
//
//	private class Holder {
//		TextView mtxtUsername, mtxtLocation, mtxtSkills, mtxtAccuracy;
//		ImageView mImgAvatar;
//
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Holder mHolder;
//		if (convertView == null) {
//			mHolder = new Holder();
//			convertView = LayoutInflater.from(mContext).inflate(
//					R.layout.item_list_app, null);
//			mHolder.mtxtUsername = (TextView) convertView
//					.findViewById(R.id.userName);
//			mHolder.mtxtLocation = (TextView) convertView
//					.findViewById(R.id.userLocation);
//			mHolder.mImgAvatar = (ImageView) convertView
//					.findViewById(R.id.userPhoto);
//			mHolder.mtxtSkills = (TextView) convertView
//					.findViewById(R.id.userSkills);
//			mHolder.mtxtAccuracy = (TextView) convertView
//					.findViewById(R.id.person_age);
//			convertView.setTag(mHolder);
//		} else {
//			mHolder = (Holder) convertView.getTag();
//		}
//
//		InboxModel mModel = getItem(position);
//		mHolder.mtxtUsername.setText(mModel.getFirstName() + " "
//				+ mModel.getLastName());
//		mHolder.mtxtSkills.setText(mModel.getSkill());
//		if (mModel.getUserImage() == null
//				|| TextUtils.isEmpty(mModel.getUserImage())) {
//
//		} else {
//
//		}
//		if (mModel.getLocation() != null) {
//			mHolder.mtxtLocation.setText(mModel.getLocation());
//		} else {
//
//		}
//
//		mHolder.mtxtAccuracy.setText(mModel.getAccuracy());
//
//		return convertView;
//	}
// }
