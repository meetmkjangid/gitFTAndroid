package in.futuretrucks.services;/*
package com.futuretrucks.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.lirataxi.Constant.Constant;
import com.lirataxi.Utility.PreferenceClass;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GetLocationService extends Service {
	private static final String TAG = "Location Service";
	Timer timer = new Timer();;

	TimerTask Task;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDestroy() {
		timer.cancel();
	}

	@Override
	public void onStart(Intent intent, int startid) {

		Task = new TimerTask() {

			@Override
			public void run() {
				updateLocation();
			}
		};
		timer.schedule(Task, 1000, 3000);

	}

	public void updateLocation() {

		AQuery aq = new AQuery(getApplicationContext());
		String url = Constant.serverUrl+Constant.GET_LOCATION;
		Map<String, Object> params = new HashMap<>();

		params.put("user_id", PreferenceClass.getStringPreferences(getApplicationContext(),Constant.CURRENT_LOGIN_USER_ID));
		params.put("lat", PreferenceClass.getStringPreferences(getApplicationContext(),Constant.CURRENT_LATITUDE));
		params.put("lng", PreferenceClass.getStringPreferences(getApplicationContext(),Constant.CURRENT_LONGITUDE));

		aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jObject, AjaxStatus status) {
				try {
					if (jObject != null && jObject.length() > 0) {

					} else {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
*/
