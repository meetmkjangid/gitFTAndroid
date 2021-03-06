package in.futuretrucks.services;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.utility.PreferenceClass;

public class FusedLocationService implements LocationListener,
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {
	private static final long INTERVAL = 0 ;//1000
	private static final long FASTEST_INTERVAL = 0;//500
	//	Activity locationActivity;
	Context locationActivity;
	private LocationRequest locationRequest;
	private GoogleApiClient googleApiClient;
	private Location location;
	private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;

	public FusedLocationService(Context context) {
		locationRequest = LocationRequest.create();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(INTERVAL);
		locationRequest.setFastestInterval(FASTEST_INTERVAL);
		this.locationActivity = context;

		googleApiClient = new GoogleApiClient.Builder(context)
				.addApi(LocationServices.API).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).build();


		if (googleApiClient != null) {
			googleApiClient.connect();
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Location currentLocation = fusedLocationProviderApi
				.getLastLocation(googleApiClient);
		if(currentLocation != null)
		{

			System.out.println("Current Lat is: "+currentLocation.getLatitude());
			System.out.println("Current Long is: " + currentLocation.getLongitude());

			PreferenceClass.setStringPreference(locationActivity, Constant.CURRENT_LATITUDE, "" + currentLocation.getLatitude());
			PreferenceClass.setStringPreference(locationActivity, Constant.CURRENT_LONGITUDE, "" + currentLocation.getLongitude());
		}
		fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, this);
	}

	@Override
	public void onLocationChanged(Location location) {

		PreferenceClass.setStringPreference(locationActivity, Constant.CURRENT_LATITUDE, "" + location.getLatitude());
		PreferenceClass.setStringPreference(locationActivity, Constant.CURRENT_LONGITUDE, "" + location.getLongitude());
	}

	public Location getLocation() {
		return this.location;
	}

	@Override
	public void onConnectionSuspended(int i) {
		googleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		googleApiClient.connect();
	}

}