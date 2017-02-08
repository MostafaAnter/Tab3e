package com.tab3e_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.tab3e_app.BuildConfig;
import com.tab3e_app.R;
import com.tab3e_app.R2;
import com.tab3e_app.app.AppController;
import com.tab3e_app.model.StudentData;
import com.tab3e_app.store.Tab3ePrefStore;
import com.tab3e_app.util.Constants;
import com.tab3e_app.util.SweetDialogHelper;
import com.tab3e_app.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentDetails extends AboutTab3e
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.nav_view)
    NavigationView navigationView;

    @Nullable
    @BindView(R2.id.text1)
    TextView textView1;
    @Nullable
    @BindView(R2.id.text2)
    TextView textView2;
    @Nullable
    @BindView(R2.id.text3)
    TextView textView3;

    @Nullable
    @BindView(R2.id.text4)
    TextView textView4;
    @Nullable
    @BindView(R2.id.text5)
    TextView textView5;
    @Nullable
    @BindView(R2.id.text6)
    TextView textView6;
    @Nullable
    @BindView(R2.id.text7)
    TextView textView7;
    @Nullable
    @BindView(R2.id.text8)
    TextView textView8;
    @Nullable
    @BindView(R2.id.text9)
    TextView textView9;
    @Nullable
    @BindView(R2.id.text10)
    TextView textView10;
    @Nullable
    @BindView(R2.id.text11)
    TextView textView11;
    @Nullable
    @BindView(R2.id.text12)
    TextView textView12;
    @Nullable
    @BindView(R2.id.text13)
    TextView textView13;
    @Nullable
    @BindView(R2.id.text14)
    TextView textView14;
    @Nullable
    @BindView(R2.id.text15)
    TextView textView15;
    @Nullable
    @BindView(R2.id.text16)
    TextView textView16;
    @Nullable
    @BindView(R2.id.text17)
    TextView textView17;
    @Nullable
    @BindView(R2.id.text18)
    TextView textView18;
    @Nullable
    @BindView(R2.id.text19)
    TextView textView19;
    @Nullable
    @BindView(R2.id.text20)
    TextView textView20;
    @Nullable
    @BindView(R2.id.text21)
    TextView textView21;
    @Nullable
    @BindView(R2.id.text22)
    TextView textView22;
    @Nullable
    @BindView(R2.id.text23)
    TextView textView23;

    @Nullable
    @BindView(R2.id.progressBar1)
    ProgressBar progressBar;
    @Nullable
    @BindView(R2.id.view)
    View v;

    private StudentData studentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        ButterKnife.bind(this);
        setToolbar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        changeFontOfNavigation();

        //change text font
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView2);

        v.setVisibility(View.GONE);
        getStudentData();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void getStudentData() {
        /**
         * this section for fetch country
         */

        String urlBrands = "http://followson.com/rest/showStudent?school=" +
                new Tab3ePrefStore(this).getPreferenceValue(Constants.SCHOOL_ID)
                + "&id_card=" + new Tab3ePrefStore(this).getPreferenceValue(Constants.STUDENT_ID_CARD);
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                if (response.trim().isEmpty() || statusFailed(response)) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.optJSONObject(0);
                        Gson gson = new Gson();
                        studentData = gson.fromJson(jsonObject.toString(), StudentData.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (studentData != null)
                        bindData(studentData);
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private boolean statusFailed(String feed) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(feed);
            JSONObject jsonObject = jsonArray.optJSONObject(0);
            String status = jsonObject.optString("status");

            return status.equalsIgnoreCase("failed");

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void bindData(StudentData s) {
        textView3.setText(s.getName());
        getCountry(s.getCountry());
        textView7.setText(s.getId_card());
        textView9.setText(s.getB_Date() + "-" + s.getH_date());
        textView11.setText(s.getMobile());
        textView13.setText(s.getEmail());

        switch (s.getLevel()) {
            case "primary":
                textView15.setText("الأبتدائية");
                break;
            case "preparatory":
                textView15.setText("المتوسطة");
                break;
            case "secondary":
                textView15.setText("الثانوية");
                break;
        }

        switch (s.getTerm()) {
            case "1":
                textView23.setText("الفصل الأول");
                break;
            case "2":
                textView23.setText("الفصل الثاني");
                break;
        }

        getRow(s.getRow());
        getSection(s.getSection());
        getYear(s.getYear());

    }

    private void getSection(String section) {
        /**
         * this section for fetch country
         */
        String urlBrands = BuildConfig.GET_SECTION + section;
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.optJSONObject(0);
                    String name = jsonObject.optString("name");
                    textView19.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                new SweetDialogHelper(StudentDetails.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private void getYear(String year) {
        /**
         * this section for fetch country
         */
        String urlBrands = "http://followson.com/rest/getYear?id=" + year;
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.optJSONObject(0);
                    String h_year = jsonObject.optString("h_year");
                    String m_year = jsonObject.optString("m_year");
                    textView21.setText(m_year + "-" + h_year);
                    v.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                new SweetDialogHelper(StudentDetails.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private void getRow(String row) {
        /**
         * this section for fetch country
         */
        String urlBrands = BuildConfig.GET_ROW_DATA + row;
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.optJSONObject(0);
                    String name = jsonObject.optString("name");
                    textView17.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                new SweetDialogHelper(StudentDetails.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private void getCountry(String country) {
        /**
         * this section for fetch country
         */
        String urlBrands = "http://followson.com/rest/getCountry?id=" + country;
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.optJSONObject(0);
                    String name = jsonObject.optString("name");
                    textView5.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                new SweetDialogHelper(StudentDetails.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }
}
