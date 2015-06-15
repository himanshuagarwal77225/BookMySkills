package com.models;

public class NavDrawerModel {

	private String title;
	private String count = "0";
	private boolean isCounterVisible = false;

	public NavDrawerModel() {
	}

	public NavDrawerModel(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public String getCount() {
		return this.count;
	}

	public boolean getCounterVisibility() {
		return this.isCounterVisible;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void setCounterVisibility(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}
}
