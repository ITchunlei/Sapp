package com.chunlei.sapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {
	private Button mSearchBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		initData();
	}
	

	@Override
	protected void initUI() {
		mSearchBtn = (Button) findViewById(R.id.btn_search_friend);
		mSearchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RadarSearchActivity.class);
				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	protected void initData() {
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
	}

}
