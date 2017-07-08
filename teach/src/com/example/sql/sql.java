package com.example.sql;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

public class sql {
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public sql() {}
	public sql(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public  String loginByGet(String username, String password,String path) {
        try {  
        	//String user=URLEncoder.encode(username,"UTF-8");
        	path = ""+path+"?use="+URLEncoder.encode(username,"UTF-8")+"&pwd="+password+"";
            URL url = new URL(path);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setRequestMethod("GET");  
            int code = conn.getResponseCode();  
            if (code == 200) {  
                InputStream is = conn.getInputStream(); // ×Ö½ÚÁ÷×ª»»³É×Ö·û´®  
                Log.i("taa", is.toString());  
                return streamToString(is);  
            } else {  
                return "Á¬½ÓÊ§°Ü!";  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "ÍøÂç·ÃÎÊÊ§°Ü";  
        }  
    } 
	
	public  String streamToString(InputStream is) {  
        try {  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            byte[] buffer = new byte[1024];  
            int len = 0;  
            while ((len = is.read(buffer)) != -1) {  
                baos.write(buffer, 0, len);  
            }  
            baos.close();  
            is.close();  
            byte[] byteArray = baos.toByteArray();  
            return new String(byteArray);  
        } catch (Exception e) {  
            Log.e("tag", e.toString());  
            return null;  
        }  
    } 
	private  login parseXML(InputStream instream) throws Exception{
		login news=null;
		XmlPullParser parse=Xml.newPullParser();
		parse.setInput(instream, "UTF-8");
		int event=parse.getEventType();
		while(event!=XmlPullParser.END_DOCUMENT){
			switch(event){
			case XmlPullParser.START_TAG:
				if("use".equals(parse.getName())){
					news=new login();
					news.setUse(parse.nextText());
				}
				else if("pwd".equals(parse.getName())){
						news.setPwd(parse.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				break;		
			}
			event=parse.next();
		}
		return news;
	}
	public  String loginByPost(String username, String password,String path) {
        try {  
        	String params ="use="+URLEncoder.encode(username,"UTF-8")+"&pwd="+password;
            URL url = new URL(path);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Conttent-Length",String.valueOf(params.length()));
            conn.setDoOutput(true);
            conn.getOutputStream().write(params.getBytes());
            int code = conn.getResponseCode();  
            if (code == 200) {  
                InputStream is = conn.getInputStream(); // ×Ö½ÚÁ÷×ª»»³É×Ö·û´®  
                Log.i("taa", is.toString());  
                return streamToString(is);  
            } else {  
                return "Á¬½ÓÊ§°Ü!";  
            }  
        } catch (Exception e){  
            e.printStackTrace();  
            return "ÍøÂç·ÃÎÊÊ§°Ü";  
        }  
    } 
}
