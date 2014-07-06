package com.justao.weathernotice.ui;

import java.util.List;
import java.util.Map;

import com.justao.weathernotice.R;
import com.justao.weathernotice.data.LocationData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class LocationActivity extends Activity {
	private List<Map<String, String>> list;
	private ListView listView;	//数据显示列表
	private SimpleAdapter adapter;
	private int state = 0;
	private String curProvince;
	private String curCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		listView = (ListView)findViewById(R.id.locationlist);
		list = LocationData.getProvinces();
		String[] from = new String[]{"address"};
		int[] to = new int[]{android.R.id.text1};
		adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1, from, to);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String temp = list.get(arg2).get("address");
				switch(state){
				case 0:	//查询市
					replacelistViewData(LocationData.getCities(temp));
					curProvince = temp;
					getActionBar().setTitle("选择地区："+curProvince+">");
					state = 1;
					break;
				case 1:	//查询县（区）
					replacelistViewData(LocationData.getCountries(temp));
					curCity = temp;
					getActionBar().setTitle("选择地区："+curProvince+">"+curCity+">");
					state = 2;
					break;
				case 2:	//查询ID并返回数据
					String id = LocationData.getCountryId(temp).get(0).get("ID");
					Intent intent = new Intent();
		        	intent.putExtra("id", id);
		        	intent.putExtra("address", temp);
		        	setResult(1, intent);
		        	finish();
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		switch(state){
		case 0:
			finish();
			break;
		case 1:	//显示省
			replacelistViewData(LocationData.getProvinces());
			getActionBar().setTitle("选择地区");
			state = 0;
			break;
		case 2:	//显示市
			replacelistViewData(LocationData.getCities(curProvince));
			getActionBar().setTitle("选择地区："+curProvince+">");
			state = 1;
			break;
		default:
			break;
		}
	}
	
	private void replacelistViewData(List<Map<String, String>> newList) {
		list.clear();
		list.addAll(newList);
		adapter.notifyDataSetChanged();
	}

}
