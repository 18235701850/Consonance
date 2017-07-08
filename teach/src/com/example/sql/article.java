package com.example.sql;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import android.util.Log;
public class article {
	private String title;
	private String biao;
	private String body;
	private String phone;
	public  article(){}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBiao() {
		return biao;
	}
	public void setBiao(String biao) {
		this.biao = biao;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public article(String title, String biao, String body, String phone) {
		this.title = title;
		this.biao = biao;
		this.body = body;
		this.phone = phone;
	}
	public  String articleby(String title, String biao, String body, String phone,String path) {
        try {  
        	String params ="title="+URLEncoder.encode(title,"UTF-8")+"&biao="+URLEncoder.encode(biao,"UTF-8")+"&body="+URLEncoder.encode(body,"UTF-8")+"&phone="+URLEncoder.encode(phone,"UTF-8");
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
}
