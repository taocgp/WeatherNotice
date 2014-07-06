package com.justao.weathernotice.ultil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

public class Common {
	
	//д�ļ���SD��
	public static void writeFileSdcardFile(String fileName,String write_str){ 
	 try{
       FileOutputStream fout = new FileOutputStream(fileName);
       byte [] bytes = write_str.getBytes();
       fout.write(bytes);
       fout.close();
     }catch(Exception e){
    	 e.printStackTrace();
	     }
	 } 
	//��SD�е��ļ�
	public String readFileSdcardFile(String fileName) throws IOException{ 
	  String res=""; 
	  try{ 
	         FileInputStream fin = new FileInputStream(fileName); 
	         int length = fin.available(); 
	         byte [] buffer = new byte[length]; 
	         fin.read(buffer);
	         res = EncodingUtils.getString(buffer, "UTF-8"); 
	         fin.close();
	  }catch(Exception e){
	         e.printStackTrace();
	         } 
	        return res; 
	}
	
	public static int getWeekIndexFromStr(String weekDay) {
		if ("������".equals(weekDay))return 0;
		if ("����һ".equals(weekDay))return 1;
		if ("���ڶ�".equals(weekDay))return 2;
		if ("������".equals(weekDay))return 3;
		if ("������".equals(weekDay))return 4;
		if ("������".equals(weekDay))return 5;
		if ("������".equals(weekDay))return 6;
		return -1;
	}
	
	public static int getTempraFromRangeStr(String TempaRange) {
		String[] tempraStrs = TempaRange.split("~");
		int topEndIndex = tempraStrs[0].indexOf("��");
		int lowEndIndex = tempraStrs[1].indexOf("��");
		int topTmepra = Integer.valueOf(tempraStrs[0].substring(0,topEndIndex));
		int lowTempra = Integer.valueOf(tempraStrs[1].substring(0,lowEndIndex));
		return (topTmepra+lowTempra)/2;
	}
}
