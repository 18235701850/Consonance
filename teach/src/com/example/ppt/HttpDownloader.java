package com.example.ppt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownloader 
{
	private URL url = null;
	FileUtils fileUtils = new FileUtils();
	public int downfile(String urlStr,String path,String fileName)
	{
		if(fileUtils.isFileExist(path + fileName))
		{
			return 1;
		}
		else
		{
			try{
				InputStream input = null;
				input = getInputStream(urlStr);
				File resultFile = fileUtils.write2SDFromINput(path, fileName, input);
				if(resultFile == null)
				{
					return -1;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return 0;
	}
	  //由于得到一个InputStream对象是所有文件处理前必须的操作，所以将这个操作封装成了一个方法  
	public InputStream getInputStream(String urlStr) throws IOException
	{
		InputStream is = null;
		try
		{
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setConnectTimeout(5000);
			urlConn.setReadTimeout(5000);
			
			
			//实现连接
			urlConn.connect();
			if (urlConn.getResponseCode() == 200) 
			{
		          
				is  = urlConn.getInputStream()	;
			}
			
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		return is;
	}	
}















