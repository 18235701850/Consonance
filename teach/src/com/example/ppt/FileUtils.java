package com.example.ppt;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils 
{
	private String SDPATH;
	@SuppressWarnings("unused")
	public String getSDPATH()
	{
		return SDPATH;
	}
	public FileUtils()
	{
		//得到当前外部存储设备的目录
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}
	
	public File createSDFile(String fileName) throws IOException
	{
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}
	
	/*
	 * 在SD卡上创建目录
	 * */
	public File createSDDir(String dirName)
	{
		File dir = new File(SDPATH + dirName);
		dir.mkdir();
		return dir;
		
		
	}
	
	/*
	 * 判断SD卡上文件夹是否存在
	 * 
	 * */
	public boolean isFileExist(String fileName)
	{
		File file = new File(SDPATH + fileName);
		return file.exists();
	}
	
	/*
	 * 将一个InputStream里面的数据写入到SD卡中
	 * */
	public File write2SDFromINput(String path,String fileName,InputStream input)
	{
		File file = null;
		OutputStream output = null;
		try
		{
			
			 byte[] arr = new byte[1];
	          ByteArrayOutputStream baos = new ByteArrayOutputStream();
	          BufferedOutputStream bos = new BufferedOutputStream(baos);
	          int n = input.read(arr);
	          while (n > 0) {
	            bos.write(arr);
	            n = input.read(arr);
	          }
	          bos.close();
	          
	          
	          
	          createSDDir(path);
			  file = createSDFile(path + fileName);
			  output = new FileOutputStream(file);
			  output.write(baos.toByteArray());
			  
			  output.flush();
			  baos.close();
			/*
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[10 * 1024*1024];
			while((input.read(buffer)) != -1)
			{
				output.write(buffer);
			}
			output.flush();
			*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				output.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return file;
	}
	
	
}






































