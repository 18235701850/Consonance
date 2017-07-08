package com.example.message;


import com.example.teacher.R;
import com.example.teacher.R.id;
import com.example.teacher.R.layout;
import com.example.teacher.R.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MessageWebActivity extends Activity {
	private Intent intent;
	private Bundle bundle;
	private String web;

	@SuppressLint("SetJavaScriptEnabled") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_web);
		intent=this.getIntent();
		web=intent.getStringExtra("web");
		WebView webview=(WebView)findViewById(R.id.web);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient());
		webview.loadUrl(web);
		webview.setVerticalScrollbarOverlay(true); //指定的垂直滚动条有叠加样式
		WebSettings settings = webview.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		settings.setUseWideViewPort(true);//设定支持viewport
		settings.setLoadWithOverviewMode(true);
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);//设定支持缩放   
	}
}