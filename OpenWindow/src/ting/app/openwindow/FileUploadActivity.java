package ting.app.openwindow;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FileUploadActivity extends Activity implements OnClickListener {

	protected static final int SUCCESS = 2;
	protected static final int FAILD = 3;
	protected static int RESULT_LOAD_FILE = 1;
	private TextView cancel;
	private TextView upload;
	private EditText pathView;
	private Button buttonLoadImage;
	private String picturePath;
	private View show;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_upload);
		initView();
		initData();
	}

	private Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {

			case SUCCESS:
				show.setVisibility(View.INVISIBLE);
				picturePath = "";
				pathView.setText(picturePath);
				Toast.makeText(getApplicationContext(), "上传成功！", Toast.LENGTH_LONG).show();
				break;
			case FAILD:
				show.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
			return false;
		}

	});

	private void initView() {
		cancel = (TextView) findViewById(R.id.cancel);
		upload = (TextView) findViewById(R.id.upload);
		buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
		cancel.setOnClickListener(this);
		upload.setOnClickListener(this);
		buttonLoadImage.setOnClickListener(this);
		show = findViewById(R.id.show);
		pathView = (EditText) findViewById(R.id.file_path);
		pathView.setKeyListener(null);
	}

	private void initData() {
		picturePath = "";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			finish();
			break;
		case R.id.buttonLoadPicture:
			Intent intent = new Intent(getApplicationContext(), FileSelectActivity.class);

			startActivityForResult(intent, RESULT_LOAD_FILE);
			break;
		case R.id.upload:
			uploadFile();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_FILE && resultCode == RESULT_LOAD_FILE && data != null) {
			picturePath = data.getStringExtra("path");
			pathView.setText(picturePath);
		}
	}
	
		private String fun(String msg){
		
		int i = msg.length();
		int j = msg.lastIndexOf("/") + 1;
		
		String a = msg.substring(j, i) ;
		System.out.println(a);
		return a;
		}
	

	private void uploadFile() {
		show.setVisibility(View.VISIBLE);
		new Thread() {
			@Override
			public void run() {

				Message msg = Message.obtain();
				
				// 服务器的访问路径
				String uploadUrl = "http://192.168.0.104:8080/UploadPhoto1/UploadServlet";
				Map<String, File> files = new HashMap<String, File>();
				
				String name = fun(picturePath);
				files.put(name, new File(picturePath));
				//files.put("test.jpg", new File(picturePath));
				Log.d("str--->>>", picturePath);
				try {
					String str = HttpPost.post(uploadUrl, new HashMap<String,String>(), files);
					System.out.println("str--->>>" + str);
					msg.what = SUCCESS;
				} catch (Exception e) {
					msg.what = FAILD;
				}
				mHandler.sendMessage(msg);
				
			}
		}.start();
	}

	@Override
	protected void onDestroy() {
		show.setVisibility(View.INVISIBLE);
		super.onDestroy();
	}
}