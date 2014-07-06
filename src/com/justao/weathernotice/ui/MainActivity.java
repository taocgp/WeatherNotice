package com.justao.weathernotice.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.justao.weathernotice.R;
import com.justao.weathernotice.data.LocationData;
import com.justao.weathernotice.data.WeatherPic;
import com.justao.weathernotice.ultil.TrendView;
import com.justao.weathernotice.ultil.Weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String CityId = "101200101";
	String curAddres = "武汉";
    private int FragmentIndex = 1;
    
	TrendView mTrendView;
	TextView mWeatherText;
	TextView mTempraRangeText;
	TextView mTempraText;
	ImageView mTempImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LocationData.WriteLocData2SD(this);
		findViews();
		refreshWeather();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		
		switch(item.getItemId()){
		case R.id.action_loc:
			intent = new Intent(this,LocationActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.action_settings:
			intent = new Intent(this,SettingsActivity.class);
			startActivityForResult(intent, 2);
			break;
		case R.id.action_refresh:
			refreshWeather();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		case 1:
			if(data != null){
				CityId = data.getExtras().getString("id");
				curAddres = data.getExtras().getString("address");
				Map<String, String> map = new HashMap<String, String>();
				map.put("address",curAddres );
				map.put("id", CityId);
				invalidateOptionsMenu();
				refreshWeather();
				
				/*
				if(addressList.contains(map)){
					Toast.makeText(getApplicationContext(), "地区已存在", Toast.LENGTH_SHORT).show();
					break;
				}
				addressList.add(map);
				refresh();
				adapter.notifyDataSetChanged();
				menuListView.setItemChecked(menuListView.getCount()-1, true);
				
				DB.saveCityAndId(map.get("address"), id);	//保存添加的地区*/
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu); 
		menu.findItem(R.id.action_loc).setTitle(curAddres);
		return true; 
	}
	
	private void refreshWeather() {
		GetWeatherTask task = new GetWeatherTask();
		task.execute(0);
	}
	
	private void findViews(){
		mTrendView = (TrendView)findViewById(R.id.trendView);
		mTempraText = (TextView)findViewById(R.id.tempraText);
		mWeatherText = (TextView)findViewById(R.id.weatherText);
		mTempraRangeText = (TextView) findViewById(R.id.tempraRangeText);
		mTempImageView = (ImageView)findViewById(R.id.tempraPic);
	}
	
	class GetWeatherTask extends AsyncTask<Integer, Integer, Integer>{
		Weather weather;
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				weather = Weather.getWeatherData(CityId);
			} catch (ClientProtocolException e) {
				Toast.makeText(MainActivity.this, "网络不佳，无法获取天气数据", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				Toast.makeText(MainActivity.this, "网络不佳，无法获取天气数据", Toast.LENGTH_LONG).show();
			}
			return 0;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			//weather.getFirstDate();
			//weather.getFirstDay();
			
			mTempraText.setText(weather.getTemparatureList().get(FragmentIndex)+"℃");
			mWeatherText.setText(weather.getWeatherList().get(FragmentIndex));
			mTempraRangeText.setText(weather.getLowHighList().get(FragmentIndex));
			
			Bitmap tempBmp = WeatherPic.getPic(getApplicationContext(), 
					weather.getTopImgList().get(FragmentIndex), 0);
			mTempImageView.setImageBitmap(tempBmp);
			
	        mTrendView.setBitmap(weather.getTopImgList(), weather.getLowImgList());
	        mTrendView.setNumsAndDraw(weather.getTemparatureList());
		}
		
	}
}
