package com.justao.weathernotice.data;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.justao.weathernotice.ultil.Weather;

public class WeatherSaxPaser extends DefaultHandler {
	private String preTag = null;
    private Weather weather = null;
    
    public Weather getWeather() {
    	return this.weather;
	}

    public static Weather getWeather(InputStream xmlStream) throws Exception{  
        SAXParserFactory factory = SAXParserFactory.newInstance();  
        SAXParser parser = factory.newSAXParser();  
        WeatherSaxPaser handler = new WeatherSaxPaser();  
        parser.parse(xmlStream, handler);  
        return handler.getWeather();  
    }
    
    @Override  
    public void startDocument() throws SAXException {
    	weather = new Weather();
    }

    @Override  
    public void startElement(String uri, String localName, String qName, Attributes attributes) 
    		throws SAXException {
    	
    	preTag = qName;
    }
    
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {
    	
    	preTag = null;
    }

    @Override  
    public void characters(char[] ch, int start, int length) 
    		throws SAXException {
        if(preTag!=null)
        {
        	/*
            String content = new String(ch,start,length);
            if ( "wendu".equals(preTag) ) 
            {
				weather.setTemperature(content);
			}
			*/
        }
    }
    
}
