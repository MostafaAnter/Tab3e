package com.tab3e_app.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tab3e_app.BuildConfig;
import com.tab3e_app.R;
import com.tab3e_app.activity.AbsentDoc;
import com.tab3e_app.activity.InfractionDoc;
import com.tab3e_app.activity.SplashActivity;
import com.tab3e_app.app.AppController;
import com.tab3e_app.model.AbsentDocItem;
import com.tab3e_app.model.InfractionDocItem;
import com.tab3e_app.store.Tab3ePrefStore;
import com.tab3e_app.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mostafa on 25/03/16.
 */
public class Tab3eNotification extends IntentService {
    // default constructor
    public Tab3eNotification() {
        super("Tab3eNotification");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("service", "service started");
        if (new Tab3ePrefStore(getApplicationContext())
                .getPreferenceValue(Constants.PUSH_NOTIFICATION)
                .equalsIgnoreCase("true")){
            getAbsentDoc();
            getInfractionDoc();
        }
    }


    static public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String status = NetworkUtil.getConnectivityStatusString(context);

            // this broadcast will receive signal when mobile connect with internet or call it manually from activity
            if (status.equalsIgnoreCase("Wifi enabled") ||
                    status.equalsIgnoreCase("Mobile data enabled")) {
                Intent sendIntent = new Intent(context, Tab3eNotification.class);
                context.startService(sendIntent);
            }

        }
    }

    private void getAbsentDoc() {
        /**
         * this section for fetch country
         */
        String urlBrands = BuildConfig.ABSENT_DETAILS + new Tab3ePrefStore(this).getPreferenceValue(Constants.SCHOOL_ID)
                + "&id=" + new Tab3ePrefStore(this).getPreferenceValue(Constants.STUDENT_ID);
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                JSONArray responseArray = null;
                try {
                    responseArray = new JSONArray(response);
                    JSONObject jsonObject = responseArray.optJSONObject(0);
                    if (jsonObject != null) {
                        String status = jsonObject.optString("status");
                        if (status.equalsIgnoreCase("Failed")) {
                            return;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Type listType = new TypeToken<ArrayList<AbsentDocItem>>() {
                }.getType();
                List<AbsentDocItem> yourClassList = new Gson().fromJson(response, listType);

                if (yourClassList != null) {
                    if (yourClassList.size() > 0) {
                        AbsentDocItem item = yourClassList.get(0);
                        String newItem = item.getID() + item.getH_date() + item.getDay();
                        if (!newItem.equalsIgnoreCase(new Tab3ePrefStore(getApplicationContext()).getPreferenceValue(Constants.LAST_ABSENT))) {
                            createAbsentNotification(item);
                            new Tab3ePrefStore(getApplicationContext()).addPreference(Constants.LAST_ABSENT,
                                    item.getID() + item.getH_date() + item.getDay());
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);

    }

    private void getInfractionDoc() {
        /**
         * this section for fetch country
         */
        String urlBrands = BuildConfig.INFRACTION_DETAILS + new Tab3ePrefStore(this).getPreferenceValue(Constants.SCHOOL_ID)
                + "&id=" + new Tab3ePrefStore(this).getPreferenceValue(Constants.STUDENT_ID);
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("response", response);

                JSONArray responseArray = null;
                try {
                    responseArray = new JSONArray(response);
                    JSONObject jsonObject = responseArray.optJSONObject(0);
                    if (jsonObject != null) {
                        String status = jsonObject.optString("status");
                        if (status.equalsIgnoreCase("Failed")) {
                            return;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Type listType = new TypeToken<ArrayList<InfractionDocItem>>() {
                }.getType();
                List<InfractionDocItem> yourClassList = new Gson().fromJson(response, listType);

                if (yourClassList != null) {
                    if (yourClassList.size() > 0) {
                        InfractionDocItem item = yourClassList.get(0);
                        String newItem = item.getID() + item.getH_date() + item.getDay();
                        if (!newItem.equalsIgnoreCase(new Tab3ePrefStore(getApplicationContext()).getPreferenceValue(Constants.LAST_INFRACTION))) {
                            createInfractionNotification(item);
                            new Tab3ePrefStore(getApplicationContext()).addPreference(Constants.LAST_INFRACTION,
                                    item.getID() + item.getH_date() + item.getDay());
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);

    }

    private void createAbsentNotification(AbsentDocItem item) {

        // BEGIN_INCLUDE(build_action)
        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a {@link android.app.PendingIntent} so that the
         * notification service can fire it on our behalf.
         */
        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // END_INCLUDE(build_action)

        // BEGIN_INCLUDE (build_notification)
        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.ic_notification);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         *Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ac_logo));
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        /**
         * Set the text of the notification. This sample sets the three most commononly used
         * text areas:
         * 1. The content title, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the title
         * 3. The subtext, which appears under the text on newer devices. Devices running
         *    versions of Android prior to 4.2 will ignore this field, so don't use it for
         *    anything vital!
         */

        String text1 = "";
        switch (item.getDay()) {
            case "sunday":
                text1 = "الأحد";
                break;
            case "monday":
                text1 = "الأثنين";
                break;
            case "tuesday":
                text1 = "الثلاثاء";
                break;
            case "wednesday":
                text1 = "الأربعاء";
                break;
            case "thursday":
                text1 = "الخميس";
                break;
        }

        String typeAbsent = "";
        switch (item.getTypeabsent()) {
            case "small":
                typeAbsent = "غياب جزئي";
                break;
            case "all":
                typeAbsent = "غياب كلي";
                break;
        }
        String typeWith = "";
        switch (item.getTypewithe()) {
            case "with":
                typeWith = "بعذر";
                break;
            case "without":
                typeWith = "بدون عذر";
                break;
        }

        builder.setContentTitle("غياب يوم " + text1);
        builder.setContentText(typeAbsent);
        builder.setSubText(typeWith);

        // END_INCLUDE (build_notification)

        // BEGIN_INCLUDE(send_notification)
        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
        // END_INCLUDE(send_notification)





    }

    private void createInfractionNotification(InfractionDocItem item) {
        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // END_INCLUDE(build_action)

        // BEGIN_INCLUDE (build_notification)
        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.ic_notification);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         *Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ac_logo));
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        getNameOfClass(item, builder);


    }

    private void getNameOfClass(final InfractionDocItem item, final NotificationCompat.Builder builder) {
        /**
         * this section for fetch country
         */
        String urlBrands = "http://followson.com/rest/getAlltypes?id=" + item.getTypeabsent();
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.optJSONObject(0);
                    getNameOfClass1(item.getAbsent(), jsonObject.optString("name"), builder, item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private void getNameOfClass1(String id, final String name, final NotificationCompat.Builder builder, final InfractionDocItem item) {
        /**
         * this section for fetch country
         */
        String urlBrands = "http://followson.com/rest/getAlltypes?id=" + id;
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.optJSONObject(0);



                    /****/
                    String text1 = "";
                    switch (item.getDay()) {
                        case "sunday":
                            text1 = "الأحد";
                            break;
                        case "monday":
                            text1 = "الأثنين";
                            break;
                        case "tuesday":
                            text1 = "الثلاثاء";
                            break;
                        case "wednesday":
                            text1 = "الأربعاء";
                            break;
                        case "thursday":
                            text1 = "الخميس";
                            break;
                    }
                    builder.setContentTitle("مخالفة يوم " + text1);
                    builder.setContentText(name);
                    builder.setSubText(jsonObject.optString("name"));

                    // END_INCLUDE (build_notification)

                    // BEGIN_INCLUDE(send_notification)
                    /**
                     * Send the notification. This will immediately display the notification icon in the
                     * notification bar.
                     */
                    NotificationManager notificationManager = (NotificationManager) getSystemService(
                            NOTIFICATION_SERVICE);
                    notificationManager.notify(2, builder.build());
                    /****/



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }


}
