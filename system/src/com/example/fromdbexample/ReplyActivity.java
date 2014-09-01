package com.example.fromdbexample;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReplyActivity extends ActionBarActivity implements OnClickListener {
	public static String name = "";
	public static String phone = "";
	public static String response = "";
	
	EditText etrep;
	Button  btrep;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reply);
		
		btrep = (Button) findViewById(R.id.btn_reply);
		btrep.setOnClickListener(this);
		
		etrep = (EditText) findViewById(R.id.etreply);
		String typedtext = etrep.getText().toString();
		Log.e("message", typedtext);
		
		TextView textName = (TextView) findViewById(R.id.name);
		TextView textPhone = (TextView) findViewById(R.id.phone);
		TextView textresponse = (TextView) findViewById(R.id.response);
		
		
		
		textName.setText(name);
		textPhone.setText(phone);
		textresponse.setText(response);
		}


	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_reply:
			etrep = (EditText) findViewById(R.id.etreply);
			String typedtext = etrep.getText().toString();
				Log.e("message", typedtext);

			TextView textPhone = (TextView) findViewById(R.id.phone);
			TextView textname = (TextView) findViewById(R.id.name);
			TextView textresponse = (TextView) findViewById(R.id.response);
		    String response = textresponse.getText().toString();
		    String phone =textPhone.getText().toString();
		    String name =textname.getText().toString();
		    
			Intent intent = new Intent(ReplyActivity.this,
					PreviewActivity.class);
			Bundle bundle=new Bundle();
			bundle.putString("typedtext", typedtext);
			bundle.putString("name", name);
			bundle.putString("response", response);
			bundle.putString("phone", phone);
			intent.putExtras(bundle);
			startActivity(intent);
	
			break;

		default:
			break;
		}
		
		

		
		

		
	}

	
}
