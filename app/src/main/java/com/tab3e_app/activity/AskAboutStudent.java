package com.tab3e_app.activity;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.tab3e_app.BuildConfig;
import com.tab3e_app.R;
import com.tab3e_app.R2;
import com.tab3e_app.adapter.SpinnerCustomAdapter;
import com.tab3e_app.app.AppController;
import com.tab3e_app.model.SpinnerModel;
import com.tab3e_app.parser.JsonParser;
import com.tab3e_app.store.AutoCompleteStore;
import com.tab3e_app.store.Tab3ePrefStore;
import com.tab3e_app.util.Constants;
import com.tab3e_app.util.SweetDialogHelper;
import com.tab3e_app.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AskAboutStudent extends AboutTab3e
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnFocusChangeListener,
        View.OnClickListener {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @Nullable
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

//    @BindView(R2.id.editText1)
//    EditText editText1;
    @Nullable
    @BindView(R2.id.editText2)
    AutoCompleteTextView editText2;
    @Nullable

    @BindView(R2.id.linear1)
    LinearLayout linear1;
    @Nullable
    @BindView(R2.id.linear2)
    LinearLayout linear2;
    @Nullable

    @BindView(R2.id.card_view1)
    CardView cardView1;
    @Nullable

    @BindView(R2.id.spinner1)
    Spinner spinner1;

    private String schoolID, studentID, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_about_student);
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

        // change font
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView2);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView3);
//        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", editText1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", editText2);

        // set focuse
       // editText1.setOnFocusChangeListener(this);
        editText2.setOnFocusChangeListener(this);
        List<String> countries = new AutoCompleteStore(this).findAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,countries);
        editText2.setAdapter(adapter);
        editText2.setThreshold(1);


        // on click
        cardView1.setOnClickListener(this);

        // set spinner
        List<SpinnerModel> models = new ArrayList<>();
        models.add(new SpinnerModel("المدرسة", ""));
        populateSpinner1(models);

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
        switch (view.getId()) {
            case R.id.card_view1:
                if (checkValidation()) {
                    Intent intent = new Intent(this, ResultOfAskAboutStudent.class);
                    intent.putExtra("sID", schoolID);
                    intent.putExtra("stID", studentID);
                    intent.putExtra("name", name);

                    new AutoCompleteStore(this).update(studentID);

                    startActivityForResult(intent, 101);
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view instanceof EditText) {
            if (b) {
                switch (view.getId()) {
                    case R.id.editText1:
                        linear1.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                    case R.id.editText2:
                        linear2.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                }
            } else {
                switch (view.getId()) {
                    case R.id.editText1:
                        linear1.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                    case R.id.editText2:
                        linear2.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                }
            }
        }

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
                    schoolID = selectedItem.getId();
                    new Tab3ePrefStore(AskAboutStudent.this).addPreference(Constants.ABSENT, selectedItem.getAbsent());
                    new Tab3ePrefStore(AskAboutStudent.this).addPreference(Constants.ERRORS, selectedItem.getErrors());
                } else {
                    schoolID = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private boolean checkValidation() {
        //name = editText1.getText().toString().trim();
        studentID = editText2.getText().toString().trim();

        if (schoolID == null || schoolID.trim().isEmpty()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "الرجاء أختيار مدرسة");
            return false;
        }
        if (studentID == null || studentID.trim().isEmpty()) {

            new SweetDialogHelper(this).showErrorMessage("خطأ", "الرجاء أدخال رقم بطاقة الطالب");
            return false;
        }

        return true;
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
                    new SweetDialogHelper(AskAboutStudent.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
                }
                Log.d("response", response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                new SweetDialogHelper(AskAboutStudent.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                //---get the result using getIntExtra()---
                new SweetDialogHelper(AskAboutStudent.this).showWarningMessage("عفوا", "لاتوجد نتأج تأكد من بيانات الطالب", "موافق");
            }
        }
    }

}
