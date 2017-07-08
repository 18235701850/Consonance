package com.example.upordown;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class uploadvideo implements OnUploadListener{
	private String urlString = "http://120.24.80.209:8080/web/upvideo";
	String filePath;
	String fileName;
	ProgressDialog dialog;
	int oldProcess;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg){
			Log.i("process", "process" + msg.arg1);
			dialog.setProgress(msg.arg1);
			if (!dialog.isShowing()) {
				Log.i("process", "show");
				dialog.show();
			} else {
				if (msg.arg1 == 100) {
					dialog.dismiss();
					//toast("上传完成");
				}
			}
		};
	};
	public void upLoad(String path,String name,final OnUploadListener listener) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			filePath = path;
			File file = new File(filePath);
			filePath += name;
			fileName=name;
			Log.i("file.size", "size=" + file.list().length + "filePath"
					+ filePath);
		} else {
			//toast("没有sd卡");
			return;
		}
		new Thread() {
			public void run() {
				try {
					String response = HttpUtil.sendFile(urlString, filePath,
							fileName,listener);
					Log.i("response", "response" + response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	@Override
	public void onUpload(double process) {
		process = process * 100;
		int currentProcess = (int) process;
		dialog.setProgress(currentProcess);
		if (currentProcess > oldProcess) {
			Message msg = handler.obtainMessage();
			msg.arg1 = (int) process;
			handler.sendMessage(msg);
		}
		oldProcess = currentProcess;
	}
	//public void initProgressDialog() {
	//	dialog = new ProgressDialog(this);
	//	dialog.setMax(100);
	//	dialog.setProgress(0);
	//	dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	//	dialog.setCancelable(false);
	//	dialog.setCanceledOnTouchOutside(false);
	//	dialog.setTitle("正在努力上传...");
	//}
	//public void toast(String text) {
	//	Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	//}
}

