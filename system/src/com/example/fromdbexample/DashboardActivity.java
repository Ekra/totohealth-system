package com.example.fromdbexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fromdbexample.library.UserFunctions;

public class DashboardActivity extends Activity {

	private String jsonResult;
	private String url = "http://10.0.2.2/android_login_api/login.php";
	private ListView listView;
	private TextView textv1;
	private List<Map<String, String>> responsesList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		listView = (ListView) findViewById(R.id.listView1);
		textv1 = (TextView) findViewById(R.id.textView1);
		accessWebService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Async Task to access the web
	private class JsonReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

		@Override
		protected void onPostExecute(String result) {
			ListDrwaer();
		}
	}// end async task

	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		// passes values for the urls string array
		task.execute(new String[] { url });
	}

	// build hash set for list view
	public void ListDrwaer() {
		responsesList = new ArrayList<Map<String, String>>();

        try {
         JSONObject jsonResponse = new JSONObject(jsonResult);
         JSONArray jsonMainNode = jsonResponse.optJSONArray("responses");
         for (int i = 0; i < jsonMainNode.length(); i++) {
          JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
          String response = jsonChildNode.optString("response");
          String names = jsonChildNode.optString("names");          
          String phoneno = jsonChildNode.optString("phoneno");
          String outPut = names + "-" + response + "-" + phoneno;
         textv1.setText(names);
         textv1.setText(jsonResult);
          //String name = jsonChildNode.getString("names");
          //System.out.println(name);
         //responsesList.add("responses");
         }
        } catch (JSONException e) {
         Toast.makeText(getApplicationContext(), "Error" + e.toString(),
           Toast.LENGTH_SHORT).show();
        }

        SimpleAdapter simpleAdapter = 
        		new SimpleAdapter(this, responsesList,
          android.R.layout.simple_list_item_1,
          new String[] { "jsonResult" }, new int[] { android.R.id.text1 });
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Map<String, String> response = responsesList.get(position);
			
				
				ReplyActivity.name = "Ekra>>"+response.get("names");
				
				Intent intent = new Intent(DashboardActivity.this, ReplyActivity.class);
				startActivity(intent);
			}
		});
       }
	/*
	 * private Map<String, String> createResponses(String string) { // TODO
	 * Auto-generated method stub return null;
	 */

	/*
	 * private HashMap<String, String> createResponses(String response) {
	 * HashMap<String, String> responsesNameNoResp = new HashMap<String,
	 * String>(); responsesNameNoResp.put( response, response); return
	 * responsesNameNoResp; }
	 */
}
