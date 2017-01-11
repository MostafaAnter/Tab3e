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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.tab3e.BuildConfig;
import com.tab3e.R;
import com.tab3e.R2;
import com.tab3e.app.AppController;
import com.tab3e.model.SpinnerModel;
import com.tab3e.parser.JsonParser;
import com.tab3e.util.SweetDialogHelper;
import com.tab3e.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultOfAskAboutStudent extends AboutTab3e
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    @BindView(R2.id.toolbar)Toolbar toolbar;
    @BindView(R2.id.nav_view)NavigationView navigationView;

    @Nullable @BindView(R2.id.text1)TextView textView1;
    @Nullable @BindView(R2.id.text2)TextView textView2;
    @Nullable @BindView(R2.id.text3)TextView textView3;
    @Nullable @BindView(R2.id.text4)TextView textView4;
    @Nullable @BindView(R2.id.text5)TextView textView5;
    @Nullable @BindView(R2.id.text6)TextView textView6;
    @Nullable @BindView(R2.id.text7)TextView textView7;
    @Nullable @BindView(R2.id.text8)TextView textView8;
    @Nullable @BindView(R2.id.text9)TextView textView9;
    @Nullable @BindView(R2.id.text10)TextView textView10;
    @Nullable @BindView(R2.id.text11)TextView textView11;
    @Nullable @BindView(R2.id.text12)TextView textView12;
    @Nullable @BindView(R2.id.text13)TextView textView13;
    @Nullable @BindView(R2.id.text14)TextView textView14;
    @Nullable @BindView(R2.id.text15)TextView textView15;
    @Nullable @BindView(R2.id.text16)TextView textView16;

    @Nullable @BindView(R2.id.card_view4)CardView cardView1;
    @Nullable @BindView(R2.id.card_view5)CardView cardView2;
    @Nullable @BindView(R2.id.card_view6)CardView cardView3;
    @Nullable @BindView(R2.id.card_view7)CardView cardView4;

    @Nullable @BindView(R2.id.contact)LinearLayout contact;

    @Nullable @BindView(R2.id.progressBar1)ProgressBar progressBar;
    @Nullable @BindView(R2.id.body)View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_ask_about_student);
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

        v.setVisibility(View.GONE);

        // change text font
        changeFont();

        // set on click
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);

        contact.setOnClickListener(this);

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

    private void changeFont(){
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView2);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView3);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView4);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView5);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView6);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView7);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView8);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView9);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView10);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView11);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView12);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView13);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView14);
        //Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView15);
        //Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView16);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_view4:
                startActivity(new Intent(this, AbsentDoc.class));
                break;
            case R.id.card_view5:
                startActivity(new Intent(this, InfractionDoc.class));
                break;
            case R.id.card_view6:
                startActivity(new Intent(this, AskAboutTable.class));
                break;
            case R.id.card_view7:
                startActivity(new Intent(this, StudentDetails.class));
                break;
            case R.id.contact:
                startActivity(new Intent(this, ContactWithSchool.class));
                break;
        }
    }

    private void getStudentData() {
        /**
         * this section for fetch country
         */
        String urlBrands = BuildConfig.GET_STUDENT_DATA + getIntent().getStringExtra("sID")
                 + "&id=" + getIntent().getStringExtra("stID");
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response", response.toString());
                if (response.trim().isEmpty()){
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
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
}
