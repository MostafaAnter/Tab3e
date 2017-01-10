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
import android.view.View;
import android.widget.TextView;

import com.tab3e.R;
import com.tab3e.R2;
import com.tab3e.util.Util;

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

        // change text font
        changeFont();

        // set on click
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
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
        }
    }
}
