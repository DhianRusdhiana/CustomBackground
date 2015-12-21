package com.dhian;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.preference.DialogPreference;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.graphics.*;
import java.text.*;


public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener
{
	private static final String androidns="http://schemas.android.com/apk/res/android";

	private SeekBar mSeekBar;
	private TextView mSplashText,mValueText;
	private Context mContext;
	private LinearLayout alpha, preview;
	private AlphaPatternDrawable alphaDrawable;
	private String mDialogMessage, mSuffix, t;
	private int opacity, summary, mDefault, mMax, mValue = 80;
	private float mDensity = 1f;


	public SeekBarPreference(Context context, AttributeSet attrs) { 
		super(context,attrs); 
		mContext = context;

		mDialogMessage = attrs.getAttributeValue(androidns,"dialogMessage");
		mSuffix = attrs.getAttributeValue(androidns,"text");
		mDefault = attrs.getAttributeIntValue(androidns,"defaultValue", 0);
		mMax = attrs.getAttributeIntValue(androidns,"max", 100);

	}
	@Override 
	protected View onCreateDialogView() {
		LinearLayout.LayoutParams params;
		LinearLayout.LayoutParams params2;
		LinearLayout.LayoutParams params3;
		mDensity = getContext().getResources().getDisplayMetrics().density;

		LinearLayout layout = new LinearLayout(mContext);
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(6,6,6,6);

		mSplashText = new TextView(mContext);
		mSplashText.setGravity(Gravity.CENTER_HORIZONTAL);
		if (mDialogMessage != null)
			mSplashText.setText(mDialogMessage);
		layout.addView(mSplashText);

		alpha = new LinearLayout(mContext);
		alpha.setGravity(Gravity.CENTER);
        alphaDrawable = new AlphaPatternDrawable((int)(5 * mDensity));

		alpha.setBackgroundDrawable(alphaDrawable);
		params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
												80);
		params2.setMargins(10,0,10,0);
		layout.addView(alpha, params2);

		preview = new LinearLayout(mContext);
		preview.setGravity(Gravity.CENTER);
		preview.setBackgroundColor(0x60000000);
		params3 = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT, 
			LinearLayout.LayoutParams.FILL_PARENT);
		alpha.addView(preview,params3);

		mValueText = new TextView(mContext);
		mValueText.setGravity(Gravity.CENTER_HORIZONTAL);
		mValueText.setTextSize(30);
		mValueText.setTextColor(Color.WHITE);
		params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT, 
			LinearLayout.LayoutParams.WRAP_CONTENT);
		preview.addView(mValueText, params);



		mSeekBar = new SeekBar(mContext);
		mSeekBar.setOnSeekBarChangeListener(this);
		layout.addView(mSeekBar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		if (shouldPersist())
			mValue = getPersistedInt(mDefault);

		mSeekBar.setMax(mMax);
		mSeekBar.setProgress(mValue);
		return layout;
	}
	@Override 
	protected void onBindDialogView(View v) {
		super.onBindDialogView(v);
		mSeekBar.setMax(mMax);
		mSeekBar.setProgress(mValue);
	}
	@Override
	protected void onSetInitialValue(boolean restore, Object defaultValue)  
	{
		super.onSetInitialValue(restore, defaultValue);
		if (restore) 
			mValue = shouldPersist() ? getPersistedInt(mDefault) : 0;
		else 
			mValue = (Integer)defaultValue;
	}

	public void onProgressChanged(SeekBar seek, int value, boolean fromTouch)
	{
		summary = value;
		double d = (int) value;
		double dd = 2.55;
		double ddd = d / dd;
		DecimalFormat df = new DecimalFormat("##0");


		t = String.valueOf(df.format((Math.round(ddd * 100.0) / 100.0)));
		mValueText.setText(mSuffix == null ? t : t.concat(mSuffix));
		opacity = value*0x01000000;
		preview.setBackgroundColor(opacity+0x000000);
		if (shouldPersist())
			persistInt(value);
		callChangeListener(new Integer(value));
	}
	public void onStartTrackingTouch(SeekBar seek) {}
	public void onStopTrackingTouch(SeekBar seek) {}

	public void setMax(int max) { mMax = max; }
	public int getMax() { return mMax; }

	public void setProgress(int progress) { 
		mValue = progress;
		if (mSeekBar != null)
			mSeekBar.setProgress(progress); 
	}
	public int getProgress() { return mValue; }
	public String getStringValue(){

		double a = (int) mValue;
		double aa = 2.55;
		double aaa = a / aa;
		DecimalFormat df = new DecimalFormat("##0");
		String valueSt = "Opacity "+String.valueOf(df.format((Math.round(aaa * 100.0) / 100.0)))+ "%";

		return valueSt;
	}
	@Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (callChangeListener(positiveResult)) {
            double a = (int) summary;
			double aa = 2.55;
			double aaa = a / aa;
			DecimalFormat df = new DecimalFormat("##0");
			String valueSt = "Opacity "+String.valueOf(df.format((Math.round(aaa * 100.0) / 100.0)))+ "%";

			this.setSummary(valueSt);
        }
    }
}
