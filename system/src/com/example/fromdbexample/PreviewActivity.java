package com.example.fromdbexample;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PreviewActivity extends ActionBarActivity {
	
	public static String name = "";
	public static String phone = "";
	public static String response = "";
	public static String typedtext = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		
		TextView textName = (TextView) findViewById(R.id.name);
		TextView textPhone = (TextView) findViewById(R.id.phone);
		TextView textresponse = (TextView) findViewById(R.id.response);
		TextView tvinput = (TextView) findViewById(R.id.tvinput);
		Intent in= getIntent();
		Bundle b=in.getExtras();
		
		String typedtext = b.getString("typedtext");
		String name = b.getString("name");
		String response = b.getString("response");
		String phone = b.getString("phone");
		
		textName.setText(name);
		textPhone.setText(phone);
		textresponse.setText(response);
		tvinput.setText(typedtext);
	}
}