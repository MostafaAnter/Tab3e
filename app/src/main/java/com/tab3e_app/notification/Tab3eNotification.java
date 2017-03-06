package com.tab3e_app.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
        // BEGIN_INCLUDE(notificationCompat)
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // END_INCLUDE(notificationCompat)

        // BEGIN_INCLUDE(intent)
        //Create Intent to launch this Activity again if the notification is clicked.
        Intent i = new Intent(this, SplashActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);
        // END_INCLUDE(intent)

        // BEGIN_INCLUDE(ticker)
        // Sets the ticker text
        builder.setTicker(getResources().getString(R.string.custom_notification));

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.ac_logo);
        // END_INCLUDE(ticker)

        // BEGIN_INCLUDE(buildNotification)
        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();
        // END_INCLUDE(buildNotification)

        // BEGIN_INCLUDE(customLayout)
        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);

        // Set text on a TextView in the RemoteViews programmatically.
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

        String text = typeAbsent + " " + typeWith;
        contentView.setTextViewText(R.id.textView, text);

        /* Workaround: Need to set the content view here directly on the notification.
         * NotificationCompatBuilder contains a bug that prevents this from working on platform
         * versions HoneyComb.
         * See https://code.google.com/p/android/issues/detail?id=30495
         */
        notification.contentView = contentView;

        // Add a big content view to the notification if supported.
        // Support for expanded notifications was added in API level 16.
        // (The normal contentView is shown when the notification is collapsed, when expanded the
        // big content view set here is displayed.)
        if (Build.VERSION.SDK_INT >= 16) {
            // Inflate and set the layout for the expanded notification view
            RemoteViews expandedView =
                    new RemoteViews(getPackageName(), R.layout.absent_notification_expanded);
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
            String text4 = "";
            switch (item.getTypeabsent()) {
                case "small":
                    text4 = "جزئي";
                    break;
                case "all":
                    text4 = "كلي";
                    break;
            }
            String text5 = "";
            switch (item.getTypewithe()) {
                case "with":
                    text5 = "بعذر";
                    break;
                case "without":
                    text5 = "بدون عذر";
                    break;
            }
            String text6 = item.getHour1() +
                    " : " + item.getHour2();

            expandedView.setTextViewText(R.id.text1, text1);
            expandedView.setTextViewText(R.id.text2, item.getM_date() + "م");
            expandedView.setTextViewText(R.id.text3, item.getH_date() + "ه");
            expandedView.setTextViewText(R.id.text4, text4);
            expandedView.setTextViewText(R.id.text5, text5);
            expandedView.setTextViewText(R.id.text6, text6);
            notification.bigContentView = expandedView;
        }
        // END_INCLUDE(customLayout)

        // START_INCLUDE(notify)
        // Use the NotificationManager to show the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);
        // END_INCLUDE(notify)
    }

    private void createInfractionNotification(InfractionDocItem item) {
        // BEGIN_INCLUDE(notificationCompat)
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // END_INCLUDE(notificationCompat)

        // BEGIN_INCLUDE(intent)
        //Create Intent to launch this Activity again if the notification is clicked.
        Intent i = new Intent(this, SplashActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);
        // END_INCLUDE(intent)

        // BEGIN_INCLUDE(ticker)
        // Sets the ticker text
        builder.setTicker(getResources().getString(R.string.custom_notification));

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.ac_logo);
        // END_INCLUDE(ticker)

        // BEGIN_INCLUDE(buildNotification)
        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();
        // END_INCLUDE(buildNotification)

        // BEGIN_INCLUDE(customLayout)
        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);

        // Set text on a TextView in the RemoteViews programmatically.
        getNameOfClass(contentView, item, notification);
    }

    private String name1 = "";
    private String name2 = "";

    private void getNameOfClass(final RemoteViews views, final InfractionDocItem item, final Notification notification) {
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
                    getNameOfClass1(item.getAbsent(), jsonObject.optString("name"), views, notification, item);
                    name1 = jsonObject.optString("name");
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

    private void getNameOfClass1(String id, final String name, final RemoteViews views, final Notification notification, final InfractionDocItem item) {
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
                    String space = " ";
                    views.setTextViewText(R.id.textView, name + space + jsonObject.optString("name"));
                    name2 = jsonObject.optString("name");

                    notification.contentView = views;

                    // Add a big content view to the notification if supported.
                    // Support for expanded notifications was added in API level 16.
                    // (The normal contentView is shown when the notification is collapsed, when expanded the
                    // big content view set here is displayed.)
                    if (Build.VERSION.SDK_INT >= 16) {
                        // Inflate and set the layout for the expanded notification view
                        RemoteViews expandedView =
                                new RemoteViews(getPackageName(), R.layout.infraction_notification_expanded);

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
                        expandedView.setTextViewText(R.id.text1, text1);
                        expandedView.setTextViewText(R.id.text2, item.getM_date() + "م");
                        expandedView.setTextViewText(R.id.text3, item.getH_date() + "ه");
                        expandedView.setTextViewText(R.id.text4, name1);
                        expandedView.setTextViewText(R.id.text5, item.getHour1() +
                                " : " + item.getHour2());
                        expandedView.setTextViewText(R.id.text6, name2);
                        notification.bigContentView = expandedView;
                    }
                    // END_INCLUDE(customLayout)

                    // START_INCLUDE(notify)
                    // Use the NotificationManager to show the notification
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(0, notification);
                    // END_INCLUDE(notify)


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
