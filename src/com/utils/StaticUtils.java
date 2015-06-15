package com.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class StaticUtils {

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void loadHtmlContent(WebView view, String appendText,
			float textSize) {
		view.setBackgroundColor(Color.TRANSPARENT);

		String color;
		color = "#00000";

		appendText = appendText.replace("\n", "<br>");// #363636
		String s = "<html><head><style type='text/css'>@font-face {font-family: MyFont;src: url('file:///android_asset/fonts/Roboto_Thin.ttf')}body {margin:0px;color:"
				+ color
				+ ";font-family: MyFont;font-size: "
				+ textSize
				+ "px;text-align: justify;}</style></head><body>"
				+ appendText
				+ "</body></html>";
		view.loadDataWithBaseURL("", s, "text/html", "utf-8", null);
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		view.setFocusable(false);
		view.setFocusableInTouchMode(false);
	}

}
