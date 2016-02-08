package in.futuretrucks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gcm.GCMBaseIntentService;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.utility.CommonGCMUtility;
import in.futuretrucks.utility.PreferenceClass;


public class GCMIntentService extends GCMBaseIntentService {
    public static final int notifyID = 9001;
	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(CommonGCMUtility.SENDER_ID);
	}

	/**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "My Own Device registered: regId = " + registrationId);
		PreferenceClass.setStringPreference(context, Constant.GCM_REGISTER_KEY, registrationId);
		CommonGCMUtility.registrationSuccess(context, registrationId);

	}

	/**
	 * Method called on device un registred
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		CommonGCMUtility.displayMessage(context, getString(R.string.gcm_unregistered));
	}

	/**
	 * Method called on Receiving a new message
	 * */
	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		String type = "";
		String key_id = "";
		String message = "";

		final Bundle bundle = intent.getExtras();

		for (String key : bundle.keySet()) {
			System.out.println("gcm My key: " + key);
		    	if(key.equals("type")){
			    type=bundle.get(key).toString();
			}
			if(key.equals("key")){
			    key_id=bundle.get(key).toString();
			}

			if(key.equals("message")){
			    message=bundle.getString("message");
			}
		}

	    System.out.println("Key_ID is: "+key_id);
	    System.out.println("type is: "+type);
	    System.out.println("message is: "+message);
	    if(PreferenceClass.getBooleanPreferences(context, Constant.isLogin)){
		sendNotification(context,key_id,type,message);
	    }
	}


	public void deleteLocally(Context context, String orderId) {
		//if ignore delete the db & remove notificaiton
		cancelNotification(context, 1);
		//doPlayStop();
		CommonGCMUtility.displayMessage(context, orderId);
	}

	/**
	 * Method called on receiving a deleted message
	 * */
	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		CommonGCMUtility.displayMessage(context, message);
		// notifies user
	    	sendNotification(context, "","", message);
	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		CommonGCMUtility.displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		CommonGCMUtility.displayMessage(context, getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	@SuppressWarnings({ "deprecation", "null" })
	private void sendNotification(Context context,String key_id,String type,String msg) {
	    Bitmap icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
	    String type_req="";
	    if(type.equals("truckboard")){
		PreferenceClass.setIntegerPreference(context,"TAB_REQ",1);
		type_req="Add new truck.";
	    }else if(type.equals("loadboard")){
		PreferenceClass.setIntegerPreference(context,"TAB_REQ",0);
		type_req="Add new load.";
	    }else {
		PreferenceClass.setIntegerPreference(context,"TAB_REQ",2);
		type_req="Add new offer.";
	    }

	    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
		    getApplicationContext()).setAutoCancel(true)
		    .setContentTitle("Future Trucks")
		    .setSmallIcon(R.drawable.ic_launcher)
		    .setLargeIcon(icon1)
		    .setContentText(type_req);

	    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
	    bigText.bigText(msg);
	    bigText.setBigContentTitle("Future Trucks");
	   //bigText.setSummaryText("");
	    mBuilder.setStyle(bigText);
	    mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

	    // Creates an explicit intent for an Activity in your app
	    Intent resultIntent = new Intent(getApplicationContext(),DrawerHomeActivity.class);

	    // The stack builder object will contain an artificial back
	    // stack for
	    // the
	    // started Activity.
	    // getApplicationContext() ensures that navigating backward from
	    // the Activity leads out of
	    // your application to the Home screen.
	    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

	    // Adds the back stack for the Intent (but not the Intent
	    // itself)
	    stackBuilder.addParentStack(DrawerHomeActivity.class);

	    // Adds the Intent that starts the Activity to the top of the
	    // stack
	    stackBuilder.addNextIntent(resultIntent);
	    PendingIntent resultPendingIntent = stackBuilder
		    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	    mBuilder.setContentIntent(resultPendingIntent);

	    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


	    // Set Vibrate, Sound and Light
	    int defaults = 0;
	    defaults = defaults | Notification.DEFAULT_LIGHTS;
	    defaults = defaults | Notification.DEFAULT_VIBRATE;
	    defaults = defaults | Notification.DEFAULT_SOUND;
	    mBuilder.setDefaults(defaults);
	    // mId allows you to update the notification later on.
	    mNotificationManager.notify(100, mBuilder.build());

	}

	public static void cancelNotification(Context context, int notificationId) {
		NotificationManager notificationManager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(notificationId);
	}
}

