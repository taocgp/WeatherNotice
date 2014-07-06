package com.justao.weathernotice.ultil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.justao.weathernotice.net.WeatherReceiver;

public class Weather {
	private static final String WEATHER_URL = "http://m.weather.com.cn/atad/";
	private String firstDate = null;
	private int firstDay = 0;
	private List<Integer> temparatureList;
	private List<String> lowHighList;
	private List<String> weatherList;
	private List<Integer>  topImgList;
	private List<Integer>  lowImgList;
	
	public Weather() {
		temparatureList = new ArrayList<Integer>();
		lowHighList = new ArrayList<String>();
		weatherList = new ArrayList<String>();
		topImgList = new ArrayList<Integer>();
		lowImgList = new ArrayList<Integer>();
	}
	
	public static Weather getWeatherData(String cityId)
			throws ClientProtocolException,IOException{
		Weather weatherData;
		String strJson = WeatherReceiver.getStrFromHttp(WEATHER_URL+cityId+".html");
		weatherData = WeatherReceiver.getWeatherFromJson(strJson);
		return weatherData;
	}

	public String getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}

	public int getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(int firstDay) {
		this.firstDay = firstDay;
	}

	public List<Integer> getTemparatureList() {
		return temparatureList;
	}

	public void setTemparatureList(List<Integer> temparatureList) {
		this.temparatureList = temparatureList;
	}

	public List<String> getLowHighList() {
		return lowHighList;
	}

	public void setLowHighList(List<String> lowHighList) {
		this.lowHighList = lowHighList;
	}

	public List<String> getWeatherList() {
		return weatherList;
	}

	public void setWeatherList(List<String> weatherList) {
		this.weatherList = weatherList;
	}

	public List<Integer> getTopImgList() {
		return topImgList;
	}

	public List<Integer> getLowImgList() {
		return lowImgList;
	}

	public void setTopImgList(List<Integer> imgList) {
		topImgList = imgList;
	}

	public void setLowImgList(List<Integer> imgList) {
		lowImgList = imgList;
	}
	
	
}
