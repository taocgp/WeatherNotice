package com.justao.weathernotice.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.justao.weathernotice.ultil.Common;
import com.justao.weathernotice.ultil.Weather;

public class WeatherReceiver {
	public static String LOC_FROM_IP_URL = "http://61.4.185.48:81/g/";
	
	public static String getStrFromHttp(String strUrl)
			throws ClientProtocolException,IOException{
		// 获取HttpGet对象s
		HttpGet httpRequest = new HttpGet(strUrl);
		String strResult = null;
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				// 取得返回的数据
				strResult = EntityUtils.toString(httpResponse.getEntity());
		return strResult;// 返回结果
	}
	
	public static Weather getWeatherFromJson(String strJson) {
		Weather weather = null;
		try {	
			JSONObject weatherObj = new JSONObject(strJson).getJSONObject("weatherinfo");
			weather = new Weather();
			weather.setFirstDate(weatherObj.getString("date_y"));
			int weekIndex = Common.getWeekIndexFromStr(weatherObj.getString("week"));
			weather.setFirstDay(weekIndex);
			for (int i = 1; i <= 4; i++) {
				String tempRangeStr = weatherObj.getString( "temp"+i );
				int temp = Common.getTempraFromRangeStr(tempRangeStr);
				
				weather.getTemparatureList()
					.add(temp);
				weather.getLowHighList()
					.add(tempRangeStr);
				weather.getWeatherList()
					.add(weatherObj.getString( "weather"+i ));
				weather.getTopImgList()
					.add(weatherObj.getInt( "img"+(2*i-1) ));
				weather.getLowImgList()
					.add(weatherObj.getInt( "img"+(2*i) ));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return weather;
	}
	
}
