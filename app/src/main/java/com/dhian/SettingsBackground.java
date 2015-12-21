package com.dhian;
import android.os.*;
import android.os.Process;
import android.app.AlertDialog;
import android.preference.PreferenceActivity;
import android.content.DialogInterface;
import android.widget.*;
import android.view.*;
import android.support.v7.widget.Toolbar;
import android.content.*;
import android.content.SharedPreferences.*;
import android.preference.*;
import android.preference.Preference.*;
import java.io.*;
import android.text.method.*;

import android.util.*;
import android.net.*;
import android.content.pm.*;
import android.widget.LinearLayout.*;
import android.graphics.*;
import android.provider.*;
import java.util.*;
import android.text.*;
import android.text.style.*;
import org.apache.commons.codec.binary.Base64;

public class SettingsBackground extends PreferenceActivity
{
	private Preference b;
	private Preference c;
	private Preference d;
	private Preference e;
	private Preference f;
	private Preference g;
	private Preference h;
	private Preference i;
	private Uri uri,uri2,uri3;
	private Bitmap bitmap;
	private Runnable mTicker;
    private Handler mHandler;
	private String backgroundType,numOfColor,orientation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	
		addPreferencesFromResource(getID("background_settings","xml"));		
		
		b = findPreference("number_of_colors");
		c = findPreference("gradient_orientation");
		d = findPreference("color1");
		e = findPreference("color2");
		f = findPreference("color3");
		g = findPreference("background_type");
		h = findPreference("background_color");
		i = findPreference("background_picture");
		
		
		
		final SeekBarPreference dp = (SeekBarPreference) findPreference("background_opacity");
		String sum = dp.getStringValue();
		dp.setSummary(sum);
		
		mHandler = new Handler();
		mTicker = new Runnable() {
			public void run() {
		SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		backgroundType = preferences2.getString("background_type","0");
		numOfColor = preferences2.getString("number_of_colors","0");
		orientation = preferences2.getString("gradient_orientation","0");
				long now = SystemClock.uptimeMillis();
				long next = now + (1000 - now % 1000);
				mHandler.postAtTime(mTicker, next);
			}
		};
        mTicker.run();
		
		if(backgroundType.equals("0")){
			g.setSummary("Transparent");
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			preferenceScreen.removePreference(h);
			preferenceScreen.removePreference(i);
			preferenceScreen.removePreference(b);
			preferenceScreen.removePreference(c);
			preferenceScreen.removePreference(d);
			preferenceScreen.removePreference(e);
			preferenceScreen.removePreference(f);
			preferenceScreen.addPreference(dp);
		}
		if(backgroundType.equals("1")){
			g.setSummary("Wallpaper");
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			preferenceScreen.removePreference(h);
			preferenceScreen.removePreference(i);
			preferenceScreen.removePreference(dp);
			preferenceScreen.removePreference(b);
			preferenceScreen.removePreference(c);
			preferenceScreen.removePreference(d);
			preferenceScreen.removePreference(e);
			preferenceScreen.removePreference(f);
		}
		if(backgroundType.equals("2")){
			g.setSummary("Custom Color");
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			preferenceScreen.removePreference(dp);
			preferenceScreen.removePreference(i);
			preferenceScreen.removePreference(b);
			preferenceScreen.removePreference(c);
			preferenceScreen.removePreference(d);
			preferenceScreen.removePreference(e);
			preferenceScreen.removePreference(f);
			preferenceScreen.addPreference(h);
		}
		if(backgroundType.equals("3")){
			g.setSummary("Custom Picture");
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			preferenceScreen.removePreference(h);
			preferenceScreen.removePreference(dp);
			preferenceScreen.removePreference(b);
			preferenceScreen.removePreference(c);
			preferenceScreen.addPreference(i);
			preferenceScreen.removePreference(d);
			preferenceScreen.removePreference(e);
			preferenceScreen.removePreference(f);
		}
		if(backgroundType.equals("4")){
			g.setSummary("Gradient Color");
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			preferenceScreen.removePreference(h);
			preferenceScreen.removePreference(dp);
			preferenceScreen.removePreference(i);
			preferenceScreen.addPreference(b);
			preferenceScreen.addPreference(c);
			preferenceScreen.addPreference(d);
			preferenceScreen.addPreference(e);
			if(numOfColor.equals("1")){
				preferenceScreen.addPreference(f);
			}
			
		}
		if(numOfColor.equals("0")){
			b.setSummary("Two Colors");
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			preferenceScreen.removePreference(f);
		}
		if(numOfColor.equals("1")){
			b.setSummary("Three Colors");
			if(backgroundType.equals("4")){
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			preferenceScreen.addPreference(f);
			}
		}
		
		if(orientation.equals("0")){
			c.setSummary("Horizontal");
			}
		if(orientation.equals("1")){
			c.setSummary("Vertical");
		}
		if(orientation.equals("2")){
			c.setSummary("Diagonal");
		}
		
		
		
		
		g.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener)new Preference.OnPreferenceChangeListener(){


			public boolean onPreferenceChange(Preference preference, Object object) {
				if (object.toString().equals((Object)"0")) {
					g.setSummary("Transparent");
					PreferenceScreen preferenceScreen = getPreferenceScreen();
					preferenceScreen.removePreference(h);
					preferenceScreen.removePreference(i);
					preferenceScreen.removePreference(b);
					preferenceScreen.removePreference(c);
					preferenceScreen.removePreference(d);
					preferenceScreen.removePreference(e);
					preferenceScreen.removePreference(f);
					preferenceScreen.addPreference(dp);
					return true;
				}
				if (object.toString().equals((Object)"1")) {
					g.setSummary("Wallpaper");
					PreferenceScreen preferenceScreen = getPreferenceScreen();
					preferenceScreen.removePreference(h);
					preferenceScreen.removePreference(i);
					preferenceScreen.removePreference(dp);
					preferenceScreen.removePreference(b);
					preferenceScreen.removePreference(c);
					preferenceScreen.removePreference(d);
					preferenceScreen.removePreference(e);
					preferenceScreen.removePreference(f);
												
					return true;
			    }
				
				if (object.toString().equals((Object)"2")) {
					g.setSummary("Custom Color");
					PreferenceScreen preferenceScreen = getPreferenceScreen();
					preferenceScreen.removePreference(dp);
					preferenceScreen.removePreference(i);
					preferenceScreen.removePreference(b);
					preferenceScreen.removePreference(c);
					preferenceScreen.removePreference(d);
					preferenceScreen.removePreference(e);
					preferenceScreen.removePreference(f);
					preferenceScreen.addPreference(h);

					return true;
				}
				if (object.toString().equals((Object)"3")) {
					g.setSummary("Custom Picture");
					PreferenceScreen preferenceScreen = getPreferenceScreen();
					preferenceScreen.removePreference(h);
					preferenceScreen.removePreference(dp);
					preferenceScreen.removePreference(b);
					preferenceScreen.removePreference(c);
					preferenceScreen.addPreference(i);
					preferenceScreen.removePreference(d);
					preferenceScreen.removePreference(e);
					preferenceScreen.removePreference(f);

					return true;
				}
				if(object.toString().equals((Object)"4")){
					g.setSummary("Gradient Color");
					PreferenceScreen preferenceScreen = getPreferenceScreen();
					preferenceScreen.removePreference(h);
					preferenceScreen.removePreference(dp);
					preferenceScreen.removePreference(i);
					preferenceScreen.addPreference(b);
					preferenceScreen.addPreference(c);
					preferenceScreen.addPreference(d);
					preferenceScreen.addPreference(e);
					if(numOfColor.equals("1")){
						preferenceScreen.addPreference(f);
					}

					return true;
				}
				

				
			return true;
		}
	});
				
		
		b.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener)new Preference.OnPreferenceChangeListener(){


											public boolean onPreferenceChange(Preference preference, Object object) {
												if(object.toString().equals((Object)"0")){
													b.setSummary("Two Colors");
													PreferenceScreen preferenceScreen = getPreferenceScreen();
													preferenceScreen.removePreference(f);
													return true;
												}
												if(object.toString().equals((Object)"1")){
													b.setSummary("Three Colors");
													PreferenceScreen preferenceScreen = getPreferenceScreen();
													preferenceScreen.addPreference(f);
													return true;
												}
												return true;
											}
										});
												
				
		c.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener)new Preference.OnPreferenceChangeListener(){


											public boolean onPreferenceChange(Preference preference, Object object) {
												if(object.toString().equals((Object)"0")){
													c.setSummary("Horizontal");
													return true;
												}
												if(object.toString().equals((Object)"1")){
													c.setSummary("Vertical");
													return true;
												}
												if(object.toString().equals((Object)"2")){
													c.setSummary("Diagonal");
													return true;
												}
												return true;
											}
										});
		i.setOnPreferenceClickListener (new Preference.OnPreferenceClickListener(){
				public boolean onPreferenceClick(Preference preference){
					showAlert();
					return true;
				}
			});

	}
	private void photoCropGallery() {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(this.uri, "image/*");
            intent.putExtra("crop", "true");         
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 2);
            return;
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText((Context)this, (CharSequence)"Sorry.. Your device not suport crop picture", (int)1).show();
            return;
        }
	}
	
	public void cropCapturedImage(Uri picUri){
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		cropIntent.setDataAndType(picUri, "image/*");
		cropIntent.putExtra("crop", "true");
		cropIntent.putExtra("return-data", true);
		startActivityForResult(cropIntent, 2);
	}

	private void setBg(Bitmap bmp) {

		File dir = new File(SettingsBackground.this.getApplicationContext().getCacheDir(), "photo.png");
		try {
	        FileOutputStream fileOutputStream = new FileOutputStream(dir);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		}
		catch (IOException e) {
		}
		do {
			uri2 = Uri.parse((String)("file://" + (Object)dir));

			return;

		} while (true);
	}

	private void setPrev(Bitmap btp) {

		File dir = new File(SettingsBackground.this.getApplicationContext().getCacheDir(), "prev.png");
		try {
	        FileOutputStream fileOutputStream = new FileOutputStream(dir);
			btp.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		}
		catch (IOException e) {
		}
		do {
			uri3 = Uri.parse((String)("file://" + (Object)dir));

			return;

		} while (true);
	}

	protected void onActivityResult(int n, int n2, Intent intent) {
        if(n2 == RESULT_OK){
		if(n == 1 && intent != null){
			uri = intent.getData();
			photoCropGallery();
			return;
		}
		
		if (n == 3){
			File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.jpg");
			try {
				cropCapturedImage(Uri.fromFile(file));
			}
			catch(ActivityNotFoundException aNFE){
				String errorMessage = "Sorry - your device doesn't support the crop action!";
				Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		if (n == 2) {
            
            
				bitmap = (Bitmap)intent.getExtras().getParcelable("data");
                Bundle bnd = new Bundle();
                bnd.putParcelable("BITMAP_1", (Parcelable)bitmap);

				setPrev(bitmap);

				return;
									
		}
		}
	}

	private void showAlert() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		LinearLayout layout       = new LinearLayout(this);
		LinearLayout tempatBtn = new LinearLayout(this);
		Button gallery = new Button(this);
		Button camera = new Button(this);
		LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT, 
			LinearLayout.LayoutParams.WRAP_CONTENT);
		LayoutParams params2 =	new LinearLayout.LayoutParams(
			0, 
			LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
		LayoutParams params3 =	new LinearLayout.LayoutParams(
			120, 200);
		final ImageView iv  = new ImageView(this);


		mHandler = new Handler();

		mTicker = new Runnable() {
			public void run() {
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsBackground.this);
				String img = preferences.getString("picture_background",null);
				if(img == null && uri3 == null){
					iv.setImageResource(getID("default_picture","drawable"));
				}else{
					if(uri3 == null){
						iv.setImageURI(Uri.parse((String)img));
					}else{

						iv.setImageURI(uri3);
					}
				}

				long now = SystemClock.uptimeMillis();
				long next = now + (1000 - now % 1000);
				mHandler.postAtTime(mTicker, next);

			}
		};
        mTicker.run();


		gallery.setOnClickListener((View.OnClickListener)new View.OnClickListener(){

									   public void onClick(View view) {
										   Intent intent = new Intent();
										   intent.setType("image/*");
										   intent.setAction("android.intent.action.GET_CONTENT");
										   SettingsBackground.this.startActivityForResult(Intent.createChooser((Intent)intent, (CharSequence)"Select Picture"), 1);
									   }
								   });
		camera.setOnClickListener((View.OnClickListener)new View.OnClickListener(){
									  public void onClick(View view) {
										  Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
										  File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.jpg");
										  intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
										  startActivityForResult(intent, 3);
									  }
								  });

		gallery.setText("From Gallery");
		camera.setText("From Camera");
		tempatBtn.setOrientation(LinearLayout.HORIZONTAL);
		params.setMargins(0,15,0,0);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(15,15,15,15);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.addView(iv,params3);
		layout.addView(tempatBtn,params);
		tempatBtn.addView(gallery,params2);
		tempatBtn.addView(camera,params2);
		alert.setTitle("Pick Picture");
		alert.setView(layout);

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});

		alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					setBg(bitmap);
					SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SettingsBackground.this).edit();
					editor.putString("picture_background", uri2.toString());
					editor.commit(); 
				}
			});

		alert.show();
		
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(getID("view_toolbar","layout"), root, false);
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
    }
	


	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		
		
		
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		
		
		
	}
	
	//private String ckck(final String string){
		//byte[] byteArray = Base64.decodeBase64(string.getBytes());
		//return new String(byteArray);
	//}
	
	public int getID(String usul, String Type) {
		return getBaseContext().getResources().getIdentifier(usul, Type, getBaseContext().getPackageName());
	}
	
}
