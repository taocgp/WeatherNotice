package com.justao.weathernotice.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocationData {
	protected static String APP_FILE_DIR = android.os.Environment.getExternalStorageDirectory()+"/weatherNotice/";
	protected static String LOC_DATA_FILE = "locationData.db";

	public static void WriteLocData2SD(Context context){
		File file = new File(APP_FILE_DIR + LOC_DATA_FILE);
		if(file.exists())
		{
			return;
		}
		writeAssetsFile2SD(context,LOC_DATA_FILE,APP_FILE_DIR);
	}

	public static List<Map<String, String>> getProvinces(){
		String dBfile = APP_FILE_DIR+ LOC_DATA_FILE;
		String SQL = "select DISTINCT province from addressIdTbl";
		return queryDB(dBfile,SQL,new String[] {"address"});
	}

	public static List<Map<String, String>> getCities(String province){
		String dBfile = APP_FILE_DIR+ LOC_DATA_FILE;
		String SQL = "select DISTINCT city from addressIdTbl where province = "+"'"+province+"'";
		return queryDB(dBfile,SQL,new String[] {"address"});
	}
	
	public static List<Map<String, String>> getCountries(String city){
		String dBfile = APP_FILE_DIR+ LOC_DATA_FILE;
		String SQL = "select country from addressIdTbl where city = "+"'"+city+"'";
		return queryDB(dBfile,SQL,new String[] {"address"});
	}
	
	public static List<Map<String, String>> getCountryId(String country){
		String dBfile = APP_FILE_DIR+ LOC_DATA_FILE;
		String SQL = "select addressId from addressIdTbl where country = "+"'"+country+"'";
		return queryDB(dBfile,SQL,new String[] {"ID"});
	}
	
	public static List<Map<String, String>> getCitiesAndIds(){
		String dBfile = APP_FILE_DIR+ LOC_DATA_FILE;
		String SQL = "select city,addressId from cityTbl";
		return queryDB(dBfile,SQL,new String[] {"address","id"});
	}

	//保存添加的地区和id号
	public static void saveCityAndId(String city, String id){
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(APP_FILE_DIR+ LOC_DATA_FILE, null);
		ContentValues values = new ContentValues();
		values.put("city", city);
		values.put("addressId", id);
		database.insert("cityTbl", null, values);
		database.close();
	}
	
	//删除指定id的地区记录s
	public static void deleteCityAndId(String id){
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(APP_FILE_DIR+ LOC_DATA_FILE, null);
		String SQL = "delete from cityTbl where addressId = '" + id + "'";
		database.execSQL(SQL);
        database.close();
	}

	public static List<Map<String, String>> queryDB(String DBfile,String SQLstr,String[] keys){
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBfile, null);
		Cursor cursor = database.rawQuery(SQLstr, null);
        cursor.moveToFirst();
        int columnCount = cursor.getColumnCount();
        if(columnCount != keys.length)
        {
        	Log.i("SQLLite", "Keys' counts doesn't match SQL strings");
        	return results;
        }
        do{
        	Map<String, String> map = new HashMap<String, String>();
        	for(int i=0; i<columnCount; ++i )
        		map.put(keys[i], cursor.getString(i));
        	results.add(map);
        }while(cursor.moveToNext());
        cursor.close();
        database.close();
		return results;
	}
	public static void writeAssetsFile2SD(Context context,String filename,String desDir){
		InputStream inputStream;
		try {
			inputStream = context.getResources().getAssets().open(filename, 3);
			File file = new File(desDir);
			if(!file.exists()){
				file.mkdirs();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(desDir + filename);
			byte[] buffer = new byte[512];
			int count = 0;
			while((count = inputStream.read(buffer)) > 0){
				fileOutputStream.write(buffer, 0 ,count);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
