package in.futuretrucks.services;

import android.content.Intent;

import android.app.Service;
import android.os.IBinder;

public class UploadFile extends Service {
	private static final String TAG = "Location Service";
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onStart(Intent intent, int startid) {

	}
}
