package com.bookmyskills.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bookmyskills.R;
import com.models.NavDrawerModel;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<NavDrawerModel> mArrayList = new ArrayList<NavDrawerModel>();

	public NavDrawerListAdapter(Context context) {
		this.mContext = context;
	}

	public void addItems(ArrayList<NavDrawerModel> dataSource) {
		this.mArrayList = dataSource;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mArrayList.size();
	}

	@Override
	public NavDrawerModel getItem(int position) {
		return mArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.drawer_list_item, null);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
			holder.txtCount = (TextView) convertView.findViewById(R.id.counter);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtTitle.setText(mArrayList.get(position).getTitle());

		if (mArrayList.get(position).getCounterVisibility()) {
			holder.txtCount.setText(mArrayList.get(position).getCount());
			holder.txtCount.setVisibility(View.VISIBLE);
		} else {
			holder.txtCount.setVisibility(View.GONE);
		}

		return convertView;
	}

	class ViewHolder {
		public TextView txtTitle;
		public TextView txtCount;
	}

}
