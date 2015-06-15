package com.utils.font;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bookmyskills.R;

public class CustomTextView extends TextView {

	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		try {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.font, defStyle, 0);

			String str = a.getString(R.styleable.font_fonttype);
			a.recycle();
			switch (Integer.parseInt(str)) {
			case 0:
				str = "fonts/Roboto-Regular.ttf";
				break;
			case 1:
				str = "fonts/Roboto-Medium.ttf";
				break;
			case 2:
				str = "fonts/Roboto_Thin.ttf";
				break;
			case 3:
				str = "fonts/Roboto-Light.ttf";
				break;
			default:
				break;
			}

			setTypeface(FontManager.getInstance(getContext()).loadFont(str));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressWarnings("unused")
	private void internalInit(Context context, AttributeSet attrs) {

	}
}
