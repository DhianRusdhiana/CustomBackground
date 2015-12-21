package com.dhian;

import android.app.*;
import android.app.AlertDialog;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.text.*;
import android.text.method.*;
import android.text.style.*;
import android.graphics.*;
import android.support.v4.widget.*;
import android.content.res.*;
import android.view.View.*;
import java.util.*;
import java.io.*;
import java.text.*;
import android.content.pm.*;

public class MainActivity extends AppCompatActivity
{
	private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
	ListView lv;
    Context context;

    ArrayList prgmName;
    public static int [] icon = {R.drawable.ic_settings,R.drawable.ic_about,R.drawable.ic_exit};
    public static String [] label = {"Settings","About","Exit"};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		drawer = (DrawerLayout) findViewById(R.id.drawer);
		toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
		};

		drawer.setDrawerListener(toggle);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		lv=(ListView) findViewById(R.id.listMenu);
        lv.setAdapter(new CustomAdapter(this, label,icon));
		
		
    }
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		toggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration configuration) {
		super.onConfigurationChanged(configuration);
		toggle.onConfigurationChanged(configuration);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {


			case R.id.action_settings:
				Intent intent = new Intent(MainActivity.this, SettingsBackground.class);
				startActivity(intent);
				return true;
			case R.id.action_about:
				showAbout();
				return true;
			case R.id.action_exit:
				finish();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public class CustomAdapter extends BaseAdapter{   
		String [] result;
		Context context;
		int [] imageId;
		private LayoutInflater inflater=null;
		public CustomAdapter(MainActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
			// TODO Auto-generated constructor stub
			result=prgmNameList;
			context=mainActivity;
			imageId=prgmImages;
			inflater = ( LayoutInflater )context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return result.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class Holder
		{
			TextView tv;
			ImageView img;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder=new Holder();
			View rowView;       
			rowView = inflater.inflate(R.layout.list_item, null);
			holder.tv=(TextView) rowView.findViewById(R.id.list_item_label);
			holder.img=(ImageView) rowView.findViewById(R.id.list_item_icon);       
			holder.tv.setText(result[position]);
			holder.img.setImageResource(imageId[position]);         
			rowView.setOnClickListener(new OnClickListener() {            
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(position == 0){
							Intent intent = new Intent(MainActivity.this,SettingsBackground.class);
							startActivity(intent);
							drawer.closeDrawers();
						}
						if(position == 1){
							showAbout();
							drawer.closeDrawers();
						}
						if(position == 2){
							finish();
						}
					}
				});   
			return rowView;
		}

	} 
	private void showAbout(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("About");
		builder.setCancelable(false);
		
		SpannableString ss = new SpannableString(
			"Custom Background\nVersion 1.6.0\n\nDeveloped by : Dhian Rusdhiana");
		ss.setSpan(new StyleSpan(Typeface.BOLD),0,17,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		ss.setSpan(new URLSpan("https://mobile.facebook.com/dhianz.moalpanjangpanjang?ref=bookmarks"), 48, 63,0);

		builder.setMessage(ss);
		builder.setPositiveButton("OK", null);
		AlertDialog dialog = builder.show();
		TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);
		messageText.setMovementMethod(LinkMovementMethod.getInstance());
		dialog.show();
	}
}
