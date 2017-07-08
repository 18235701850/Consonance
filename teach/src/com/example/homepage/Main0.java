package com.example.homepage;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.example.teacher.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main0 extends Activity {
	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;
	private LinearLayout btn_change,btn_sex,btn_age,btn_grades,btn_subject,btn_name,btn_school,btn_major;
	private Button btn_save;
	private String sexdata[]=new String[]{"男","女"};
	private String agedata[]=new String[]{"5岁","6岁","7岁","8岁","9岁","10岁","11岁","12岁","13岁","14岁","15岁","16岁","17岁","18岁","19岁","20岁","21岁","22岁","23岁","24岁","25岁"};
	private String gradesdata[]=new String[]{"小学一年级","小学二年级","小学三年级","小学四年级","小学五年级","小学六年级",
			                                 "初中一年级","初中二年级","初中三年级",
			                                 "高中一年级","高中二年级","高中三年级",
			                                 "大一","大二","大三","大四"};
	private String subjectdata[]=new String[]{"数学","语文","英语","物理","生物","化学","政治","历史","地理"};
	private TextView mychsex,mychage,mychgrades,mysubject;
	private EditText name,school,major;
	private Intent intent;
	private Bundle bundle;
	Boolean fag=false;
	Bitmap photo;
	String img;
	Editor editor;
	Editor editor1;
	Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main0);
		intent=this.getIntent();
		bundle=intent.getExtras();
		bitmap=intent.getParcelableExtra("image");
		ImageView image=(ImageView)findViewById(R.id.image);
		image.setImageBitmap(bitmap);//显示在imageview里
		String name1=bundle.getString("name");
		EditText name=(EditText)findViewById(R.id.editname);
		SpannableString ss = new SpannableString(name1);
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(14,true);
		ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		name.setHint(new SpannedString(ss));
		String sex1=bundle.getString("sex");
		TextView sex=(TextView)findViewById(R.id.sexView);
		sex.setText(sex1);
		String age1=bundle.getString("age");
		TextView age=(TextView)findViewById(R.id.ageView);
		age.setText(age1);
		String school1=bundle.getString("school");
		EditText school=(EditText)findViewById(R.id.editschool);
		SpannableString aa = new SpannableString(school1);
		AbsoluteSizeSpan aaa = new AbsoluteSizeSpan(14,true);
		aa.setSpan(aaa, 0, aa.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		school.setHint(new SpannedString(aa));
		String major1=bundle.getString("major");
		EditText major=(EditText)findViewById(R.id.editmajor);
		SpannableString bb = new SpannableString(major1);
		AbsoluteSizeSpan bbb = new AbsoluteSizeSpan(14,true);
		bb.setSpan(bbb, 0, bb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		major.setHint(new SpannedString(bb));
		String grades1=bundle.getString("grades");
		TextView grades=(TextView)findViewById(R.id.gradesView);
		grades.setText(grades1);
		String subject1=bundle.getString("subject");
		TextView subject=(TextView)findViewById(R.id.subjectView);
		subject.setText(subject1);
		this.mychsex=(TextView)findViewById(R.id.sexView);
		btn_change = (LinearLayout)findViewById(R.id.linear);
		btn_change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showChoosePicDialog();
				fag=true;
			}
		});
		this.btn_sex=(LinearLayout)findViewById(R.id.sex);
		this.btn_sex.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(Main0.this) // 实例化对象
						// 设置显示图片
				 	// 设置显示标题
				.setNegativeButton("取消", 		// 增加取消按钮
					new DialogInterface.OnClickListener() {// 设置操作监听
						public void onClick(DialogInterface dialog,
							int whichButton) { 	// 单击事件
						}})
				.setItems(sexdata,		// 设置列表选项
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
							int which) {	// 设置显示信息
									Main0.this.mychsex.setText(sexdata[which]);
									Main0.this.mychsex.setTextSize(18);
									Main0.this.mychsex.setTextColor(0xff000000);// 设置文字
					}}).create(); 	// 创建Dialog
			dialog.show(); 			// 显示对话框
			}
		});
		this.mychage=(TextView)findViewById(R.id.ageView);
		this.btn_age=(LinearLayout)findViewById(R.id.age);
		this.btn_age.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(Main0.this) // 实例化对象
						// 设置显示图片
				 	// 设置显示标题
				.setNegativeButton("取消", 		// 增加取消按钮
					new DialogInterface.OnClickListener() {// 设置操作监听
						public void onClick(DialogInterface dialog,
							int whichButton) { 	// 单击事件
						}})
				.setItems(agedata,		// 设置列表选项
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
							int which) {	// 设置显示信息
									Main0.this.mychage.setText(agedata[which]);
									Main0.this.mychage.setTextSize(18);
									Main0.this.mychage.setTextColor(0xff000000);// 设置文字
					}}).create(); 	// 创建Dialog
			dialog.show(); 			// 显示对话框
			}
		});
		this.mychgrades=(TextView)findViewById(R.id.gradesView);
		this.btn_grades=(LinearLayout)findViewById(R.id.grades);
		this.btn_grades.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(Main0.this) // 实例化对象
						// 设置显示图片
				 	// 设置显示标题
				.setNegativeButton("取消", 		// 增加取消按钮
					new DialogInterface.OnClickListener() {// 设置操作监听
						public void onClick(DialogInterface dialog,
							int whichButton) { 	// 单击事件
						}})
				.setItems(gradesdata,		// 设置列表选项
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
							int which) {	// 设置显示信息
									Main0.this.mychgrades.setText(gradesdata[which]);
									Main0.this.mychgrades.setTextSize(18);
									Main0.this.mychgrades.setTextColor(0xff000000);// 设置文字
					}}).create(); 	// 创建Dialog
			dialog.show(); 			// 显示对话框
			}
		});

		this.mysubject=(TextView)findViewById(R.id.subjectView);
		this.btn_subject=(LinearLayout)findViewById(R.id.subject);
		this.btn_subject.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(Main0.this) // 实例化对象
						// 设置显示图片
				 	// 设置显示标题
				.setNegativeButton("取消", 		// 增加取消按钮
					new DialogInterface.OnClickListener() {// 设置操作监听
						public void onClick(DialogInterface dialog,
							int whichButton) { 	// 单击事件
						}})
				.setItems(subjectdata,		// 设置列表选项
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
							int which) {	// 设置显示信息
									Main0.this.mysubject.setText(subjectdata[which]);
									Main0.this.mysubject.setTextSize(18);
									Main0.this.mysubject.setTextColor(0xff000000);// 设置文字
					}}).create(); 	// 创建Dialog
			dialog.show(); 			// 显示对话框
			}
		});
		this.name=(EditText)findViewById(R.id.editname);
		this.btn_name=(LinearLayout)findViewById(R.id.name);
		this.btn_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText nameview=(EditText)findViewById(R.id.editname);
				nameview.findFocus();
				nameview.setText(nameview.getHint().toString());
				nameview.setSelection(nameview.getHint().toString().length());
				InputMethodManager m = (InputMethodManager) nameview.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
			
		});
		this.school=(EditText)findViewById(R.id.editschool);
		this.btn_school=(LinearLayout)findViewById(R.id.school);
		this.btn_school.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText schoolview=(EditText)findViewById(R.id.editschool);
				schoolview.findFocus();
				schoolview.setText(schoolview.getHint().toString());
				schoolview.setSelection(schoolview.getHint().toString().length());
				InputMethodManager m = (InputMethodManager) schoolview.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
			
		});
		this.major=(EditText)findViewById(R.id.editmajor);
		this.btn_major=(LinearLayout)findViewById(R.id.major);
		this.btn_major.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText majorview=(EditText)findViewById(R.id.editmajor);
				majorview.findFocus();
				majorview.setText(majorview.getHint().toString());
				majorview.setSelection(majorview.getHint().toString().length());
				InputMethodManager m = (InputMethodManager) majorview.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
			
		});
		this.btn_save=(Button)findViewById(R.id.save);
		this.btn_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {			
				ImageView image=(ImageView)findViewById(R.id.image);
				EditText nameview=(EditText)findViewById(R.id.editname);
				TextView sexview=(TextView)findViewById(R.id.sexView);
				TextView ageview=(TextView)findViewById(R.id.ageView);
				EditText schoolview=(EditText)findViewById(R.id.editschool);
				EditText majorview=(EditText)findViewById(R.id.editmajor);
				TextView gradesview=(TextView)findViewById(R.id.gradesView);
				TextView subjectview=(TextView)findViewById(R.id.subjectView);
				String name,school,major;
				if(nameview.length()==0){
					name=nameview.getHint().toString();
				}
				else{
					name=nameview.getText().toString();
				}
				String sex=sexview.getText().toString().trim();
				String age=ageview.getText().toString();
				if(schoolview.length()==0){
					 school=schoolview.getHint().toString();
				}
				else{
					 school=schoolview.getText().toString();
				}
				if(majorview.length()==0){
					 major=majorview.getHint().toString();
					 }
				else{
					 major=majorview.getText().toString();
				}
				String grades=gradesview.getText().toString();
				String subject=subjectview.getText().toString();
			if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
					SharedPreferences sp=getSharedPreferences("test",MODE_PRIVATE);
					editor=sp.edit();
					editor.putString("name", name);
					editor.putString("sex", sex);
					editor.putString("age", age);
					editor.putString("school", school);
					editor.putString("major", major);
					editor.putString("grades", grades);
					editor.putString("subject", subject);
					if(fag){
						bitmap=photo;
					}
					bitmap= Utils.toRoundBit(bitmap);
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
					byte[] b = stream.toByteArray();
					// 将图片流以字符串形式存储下来
					String tp = Base64.encodeToString(b,Base64.DEFAULT);
					editor.putString("image", tp);
					Toast.makeText(getApplicationContext(), "保存成功",Toast.LENGTH_SHORT).show();
					}
				else{
					Toast.makeText(getApplicationContext(), "sd卡不可用",Toast.LENGTH_SHORT ).show();
				}
				editor.commit();
				bundle.putString("name", name);
				Toast.makeText(getApplicationContext(), "保存成功1",Toast.LENGTH_SHORT).show();
				bundle.putString("sex", sex);
				bundle.putString("age", age);
				bundle.putString("school", school);
				bundle.putString("major", major);
				bundle.putString("grades", grades);
				bundle.putString("subject", subject);
				intent.putExtras(bundle);
				image.setDrawingCacheEnabled(true);
				Bitmap img = Bitmap.createBitmap(image.getDrawingCache());
				image.setDrawingCacheEnabled(false);
				intent.putExtra("image", img);
				Main0.this.setResult(RESULT_OK,intent);
				Main0.this.finish();
			}
			
		});
	}

	/**
	 * 显示修改头像的对话框
	 */
	protected void showChoosePicDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("设置头像");
		String[] items = { "选择本地照片", "拍照" };
		builder.setNegativeButton("取消", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case CHOOSE_PICTURE: // 选择本地照片
					Intent openAlbumIntent = new Intent(
							Intent.ACTION_GET_CONTENT);
					openAlbumIntent.setType("image/*");
					startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
					break;
				case TAKE_PICTURE: // 拍照
					Intent openCameraIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					tempUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), "image.jpg"));
					// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
					break;
				}
			}
		});
		builder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // 如果返回码是可以用的
			switch (requestCode) {
			case TAKE_PICTURE:
				startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
				break;
			case CHOOSE_PICTURE:
				startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
				break;
			case CROP_SMALL_PICTURE:
				if (data != null) {
					setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
				}
				break;
			}
		}
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param
	 * 
	 * @param picdata
	 */
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
		    photo = extras.getParcelable("data");
			photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
			ImageView image=(ImageView)findViewById(R.id.image);
			image.setImageBitmap(photo);
			uploadPic(photo);
		}
	}

	private void uploadPic(Bitmap bitmap) {
		// 上传至服务器
		// ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
		// 注意这里得到的图片已经是圆形图片了
		// bitmap是没有做个圆形处理的，但已经被裁剪了

		String imagePath = Utils.savePhoto(bitmap, Environment
				.getExternalStorageDirectory().getAbsolutePath(), String
				.valueOf(System.currentTimeMillis()));
		Log.e("imagePath", imagePath+"");
		if(imagePath != null){
			// 拿着imagePath上传了
			// ...
		}
	}


}