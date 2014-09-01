package com.example.fromdbexample;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.fromdbexample.library.ServiceHandler;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ActionBarActivity {
	private String URL = "http://10.0.2.2/android_login_api/login.php";

	private ArrayList<ResponseModel> responseModel = new ArrayList<ResponseModel>();
	CustomResponseAdapter custRAdapter;

	ListView lvResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);

		new getResponses().execute();
	}

	public void setdataonlist() {
		lvResponse = (ListView) findViewById(R.id.listView);

		custRAdapter = new CustomResponseAdapter(getApplicationContext(),
				responseModel);
		lvResponse.setAdapter(custRAdapter);

		lvResponse.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ResponseModel response = responseModel.get(position);

				ReplyActivity.name = response.getName();
				ReplyActivity.phone = response.getPhone();
				ReplyActivity.response = response.getResponse();
				
				

				Intent intent = new Intent(MainActivity.this,
						ReplyActivity.class);
				startActivity(intent);
			}
		});

	}

	private class getResponses extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// show progress bar
			setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Void doInBackground(String... arg0) {
			ServiceHandler sh = new ServiceHandler();
			String jsonresults = sh.makeServiceCall(URL, sh.GET);

			Log.i("Response From Server: ", "> " + jsonresults);
			if (jsonresults != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonresults);
					if (jsonObj != null) {
						JSONArray arrresponse = jsonObj
								.getJSONArray("responses");
						for (int i = 0; i < arrresponse.length(); i++) {
							JSONObject jo = arrresponse.getJSONObject(i);

							String name = jo.getString("names");
							String phone = jo.getString("phoneno");
							String response = jo.getString("response");

							Log.i("Response Name From JSON Array: ", "> "
									+ name);
							Log.i("Response Phone From JSON Array: ", "> "
									+ phone);
							Log.i("Response From JSON Array: ", "> " + response);
							ResponseModel rModel = new ResponseModel(name,
									phone, response);
							responseModel.add(rModel);

						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setProgressBarIndeterminateVisibility(false);
			setdataonlist();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
