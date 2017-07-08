package com.example.sql;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Xml;

public class sqlcontent {
	private int id;
	private String content;
	private String time;
	private String nickname;
	private String fathername;
	private String image;
	public int getId() {
		return id;
	}
	public String getFathername() {
		return fathername;
	}
	public void setFathername(String fathername) {
		this.fathername = fathername;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	public sqlcontent(){}
	public sqlcontent( String content, String time, String nickname,
			String fathername, String image) {
		
		
		this.content = content;
		this.time = time;
		this.nickname = nickname;
		
		this.image = image;
	}
	public  String sqlcon(String nickname, String content,String image,String time,String path) {
        try {  
        	//String user=URLEncoder.encode(username,"UTF-8");
        	path = ""+path+"?nickname="+URLEncoder.encode(nickname,"UTF-8")+"&content="+URLEncoder.encode(content,"UTF-8")+"&image="+image+"&time="+time+"";
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
	private  coment parseXML(InputStream instream) throws Exception{
		coment news=null;
		XmlPullParser parse=Xml.newPullParser();
		parse.setInput(instream, "UTF-8");
		int event=parse.getEventType();
		while(event!=XmlPullParser.END_DOCUMENT){
			switch(event){
			case XmlPullParser.START_TAG:
				if("nickname".equals(parse.getName())){
					news=new coment();
					news.setNickname(parse.nextText());
				}
				else if("image".equals(parse.getName())){
						news.setImage(parse.nextText());
				}
				else if("time".equals(parse.getName())){
					news.setTime(parse.nextText());
			}
				else if("content".equals(parse.getName())){
					news.setContent(parse.nextText());
			}
				break;
			case XmlPullParser.END_TAG:
				break;		
			}
			event=parse.next();
		}
		return news;
	}
	
	

}
