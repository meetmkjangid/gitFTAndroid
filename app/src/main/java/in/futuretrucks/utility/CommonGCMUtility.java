package in.futuretrucks.utility;


import android.content.Context;
import android.content.Intent;

public final class CommonGCMUtility {

    // Google project id
	public static final String SENDER_ID = "1042437914678";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "Future Trucks GCM";

   public static final String DISPLAY_MESSAGE_ACTION = "in.futuretrucks.DISPLAY_MESSAGE";

    public static final String EXTRA_MESSAGE = "message";
    
    public static final String REGISTRATION_SUCCESS = "in.futuretrucks.REGISTRATION_SUCCESS";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }


    public static void registrationSuccess(Context context, String message) {
        Intent intent = new Intent(REGISTRATION_SUCCESS);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
