package com.example.sql;
import android.content.Context;
public class talk extends insert{
	public talk(int id, String content, String time, String nickname,
			String image, Context context) {
		super(id, content, time, nickname, image, context);
		
	}
		public void trade_add(){
			super.settablename("Content");
			super.trade_add();
		}
		public void trade(){
			super.settablename("childContent");
			super.trade_add();
		}
	}

