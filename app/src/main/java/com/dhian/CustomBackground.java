package com.dhian;

import android.content.*;
import android.util.*;
import android.widget.*;
import android.os.*;
import android.preference.*;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.*;
import android.graphics.*;
import android.net.*;
import android.provider.*;
import java.io.*;
import android.view.*;

public class CustomBackground extends LinearLayout
{
	private Runnable mTicker;
    private Handler mHandler;
	private int bgColor,opacity ,backgroundOpacity;
	private String backgroundType,numOfColors,GdOrientation;
	private int width,height,color1,color2,color3;
	private int[] color;
	
	public CustomBackground(final Context context, AttributeSet attrs){
		super(context,attrs);
		
		
		
		mHandler = new Handler();
		mTicker = new Runnable() {
			public void run() {
				
				SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(getContext());
				backgroundType = preferences2.getString("background_type","0");
				bgColor = preferences2.getInt("background_color",0xff000000);
				opacity = preferences2.getInt("background_opacity",150);
				backgroundOpacity = opacity * 0x01000000;
				color1 = preferences2.getInt("color1",0xffff0000);
				color2 = preferences2.getInt("color2",0xffffff00);
				color3 = preferences2.getInt("color3",0xff00ff00);
				numOfColors = preferences2.getString("number_of_colors","0");
				GdOrientation = preferences2.getString("gradient_orientation","0");

				if(backgroundType.equals("0")){
					CustomBackground.this.setBackgroundColor(backgroundOpacity + 0x000000);
				}
				if(backgroundType.equals("1")){
					Drawable dr = getContext().getWallpaper();
					ShapeDrawable mask = new ShapeDrawable(new RoundRectShape(null, null, null));
					mask.getPaint().setColor(0x70000000);

					Drawable[] d = {dr, mask};
					LayerDrawable wp = new LayerDrawable(d);
					CustomBackground.this.setBackgroundDrawable(wp);

				}
				if(backgroundType.equals("2")){
					CustomBackground.this.setBackgroundColor(bgColor);


				}
				if(backgroundType.equals("3")){
					String img;
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
					img = preferences.getString("picture_background",null);
					if(img == null){
						Bitmap bmp = BitmapFactory.decodeResource(getResources(),getID("default_picture","drawable"));
						Drawable dr = new BitmapDrawable(bmp);
						ShapeDrawable mask = new ShapeDrawable(new RoundRectShape(null, null, null));
						mask.getPaint().setColor(0x60000000);

						Drawable[] d = {dr, mask};
						LayerDrawable im = new LayerDrawable(d);
						CustomBackground.this.setBackground(im);
					}else{
						try{
							Uri imageUri = Uri.parse((String)img);
							Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
							Drawable dr = new BitmapDrawable(bitmap);
							ShapeDrawable mask = new ShapeDrawable(new RoundRectShape(null, null, null));
							mask.getPaint().setColor(0x60000000);

							Drawable[] d = {dr, mask};
							LayerDrawable im = new LayerDrawable(d);
							CustomBackground.this.setBackground(im);
						} catch (FileNotFoundException e) {
							e.printStackTrace(); 
						} catch (IOException e){
							e.printStackTrace();
						}
					}

				}
				if(backgroundType.equals("4")){
					FillCustomGradient(CustomBackground.this,GdOrientation,numOfColors);
				}
				
				invalidate();
				long now = SystemClock.uptimeMillis();
				long next = now + (1000 - now % 1000);
				mHandler.postAtTime(mTicker, next);
			}
		};
        mTicker.run();
		
	}
	
	private void FillCustomGradient(View v, final String value,final String numcolor) {
        final View view = v;

        Drawable[] layers = new Drawable[1];

        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int w, int h) {

				if(value.equals("0")){
					width =view.getWidth();
					height=0;
				}
				if(value.equals("1")){
					width=0;
					height=view.getHeight();
				}
				if(value.equals("2")){
					width=view.getWidth();
					height=view.getHeight();
				}
				if(numcolor.equals("0")){
					color = new int[]{color1,color2};
				}
				if(numcolor.equals("1")){
					color = new int[]{color1,color2,color3};
				}
                LinearGradient lg = new LinearGradient(0,0,width,height,color,
													   null,
													   Shader.TileMode.CLAMP);

                return lg;
            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        p.setCornerRadii(new float[] { 5, 5, 5, 5, 0, 0, 0, 0 });
        layers[0] = (Drawable) p;

        LayerDrawable composite = new LayerDrawable(layers);
        view.setBackgroundDrawable(composite);
    }
	
	public int getID(String usul, String Type) {
		return getContext().getResources().getIdentifier(usul, Type, getContext().getPackageName());
	}
}
