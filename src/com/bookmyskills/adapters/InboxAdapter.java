package com.bookmyskills.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookmyskills.R;
import com.models.InboxModel;
import com.utils.misc.DataItem;

/**
 * Created by Himanshu on 17/05/15.
 */
public class InboxAdapter extends BaseAdapter {

	ArrayList<InboxModel> mMessagesArray = new ArrayList<InboxModel>();
	private Context mContext;

	public InboxAdapter(Context mContext) {
		this.mContext = mContext;
	}

	public void clear() {
		this.mMessagesArray.clear();
	}

	public void addItems(ArrayList<InboxModel> mArrayUsers) {
		this.mMessagesArray = mArrayUsers;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mMessagesArray.size();
	}

	@Override
	public InboxModel getItem(int position) {
		return mMessagesArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private class Holder {
		TextView mtxtSubject;
		ImageView mImgAvatar;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder mHolder;
		if (convertView == null) {
			mHolder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_list_app, null);
			mHolder.mImgAvatar = (ImageView) convertView
					.findViewById(R.id.imageView);
			mHolder.mtxtSubject = (TextView) convertView
					.findViewById(R.id.textView);
			convertView.setTag(mHolder);
		} else {
			mHolder = (Holder) convertView.getTag();
		}

		InboxModel mModel = getItem(position);

		mHolder.mImgAvatar.setImageDrawable(mModel.getDrawable());
		mHolder.mtxtSubject.setText(mModel.getSubject());

		return convertView;
	}
}
