package com.tab3e.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.tab3e.BuildConfig;
import com.tab3e.R;
import com.tab3e.R2;
import com.tab3e.adapter.SpinnerCustomAdapter;
import com.tab3e.app.AppController;
import com.tab3e.model.SpinnerModel;
import com.tab3e.parser.JsonParser;
import com.tab3e.util.SweetDialogHelper;
import com.tab3e.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AskAboutTable extends AboutTab3e
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

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
    @BindView(R2.id.spinner1)
    Spinner spinner1;

    @Nullable
    @BindView(R2.id.spinner2)
    Spinner spinner2;
    @Nullable
    @BindView(R2.id.spinner3)
    Spinner spinner3;
    @Nullable
    @BindView(R2.id.spinner4)
    Spinner spinner4;
    @Nullable
    @BindView(R2.id.spinner5)
    Spinner spinner5;
    @Nullable
    @BindView(R2.id.spinner6)
    Spinner spinner6;

    @Nullable @BindView(R2.id.card_view1)CardView cardView1;

    private String schoolID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_about_table);
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

        cardView1.setOnClickListener(this);

        // change font
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView2);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView3);

        // set spinner
        List<SpinnerModel> models = new ArrayList<>();
        models.add(new SpinnerModel("المدرسة", ""));
        populateSpinner1(models);

        models = new ArrayList<>();
        models.add(new SpinnerModel("العام الدراسي", ""));
        populateSpinner2(models);

        models = new ArrayList<>();
        models.add(new SpinnerModel("الفصل الدراسي", ""));
        populateSpinner3(models);

        models = new ArrayList<>();
        models.add(new SpinnerModel("المرحلة", ""));
        populateSpinner4(models);

        models = new ArrayList<>();
        models.add(new SpinnerModel("الصف", ""));
        populateSpinner5(models);

        models = new ArrayList<>();
        models.add(new SpinnerModel("الشعبة", ""));
        populateSpinner6(models);


        if (Util.isOnline(this)) {
            getSchools();
        } else {
            // show error message
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ناسف...")
                    .setContentText("هناك مشكله بشبكة الانترنت حاول مره اخرى")
                    .show();
        }
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

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, TableTabs.class));
    }

    private void populateSpinner1(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(spinnerArrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
                }

                if (position > 0) {
                    // doSome things
                    schoolID = selectedItem.getId();
                } else {
                    schoolID = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void populateSpinner2(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item_start, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_start);
        spinner2.setAdapter(spinnerArrayAdapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void populateSpinner3(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item_start, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_start);
        spinner3.setAdapter(spinnerArrayAdapter);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void populateSpinner4(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item_start, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_start);
        spinner4.setAdapter(spinnerArrayAdapter);

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void populateSpinner5(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item_start, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_start);
        spinner5.setAdapter(spinnerArrayAdapter);

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void populateSpinner6(List<SpinnerModel> mlist) {

        SpinnerCustomAdapter spinnerArrayAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item_start, mlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_start);
        spinner6.setAdapter(spinnerArrayAdapter);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                SpinnerModel selectedItem = (SpinnerModel) parent.getItemAtPosition(position);
                if (position > 0) {
                    // doSome things
//                    countryId = selectedItem.getId();
//                    cityId = null;
//                    getCities(countryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void getSchools() {
        /**
         * this section for fetch country
         */
        String urlBrands = BuildConfig.SHOW_ALL_SCHOL_URL;
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                List<SpinnerModel> spinnerItemList = JsonParser.parseSpinnerFeed(response);
                if (spinnerItemList != null && spinnerItemList.size() > 0) {
                    spinnerItemList.add(0, new SpinnerModel("المدرسة", ""));
                    populateSpinner1(spinnerItemList);
                } else {
                    new SweetDialogHelper(AskAboutTable.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
                }
                Log.d("response", response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                new SweetDialogHelper(AskAboutTable.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

}
