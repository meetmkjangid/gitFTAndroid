package in.futuretrucks;

import in.futuretrucks.services.FusedLocationService;

public class Application extends android.support.multidex.MultiDexApplication {

    FusedLocationService fusedLocationService;
	
	@Override
	public void onCreate() {
		super.onCreate();
		fusedLocationService = new FusedLocationService(this);

	}
}