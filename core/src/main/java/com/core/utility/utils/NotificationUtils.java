package com.core.utility.utils;


/**
 * Created by Le Duc Chung on 2017-10-24.
 * on device 'shoot'
 */

public class NotificationUtils {
    public class TYPE {
        public static final int DISCONNECTED = 1;
        public static final int LOCATION_STOP = 2;
    }
//
//    public static void postLocalNotification(Context activity, int typeID, String header, String content) {
//        Intent intent = new Intent(activity, SplashActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent contentIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder b = new NotificationCompat.Builder(activity);
//
//        b.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.ic_my_location_white_18dp)
//                .setContentTitle(header)
//                .setContentText(content)
//                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
//                .setContentIntent(contentIntent);
//
//        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(typeID, b.build());
//    }
//
//    public static void cancelLocalNotification(Context context, int id) {
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(id);
//    }
}
