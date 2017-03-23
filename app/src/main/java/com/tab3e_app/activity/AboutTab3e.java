package com.tab3e_app.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.tab3e_app.R;
import com.tab3e_app.R2;
import com.tab3e_app.app.AppController;
import com.tab3e_app.store.AutoCompleteStore;
import com.tab3e_app.store.Tab3ePrefStore;
import com.tab3e_app.util.CustomTypefaceSpan;
import com.tab3e_app.util.SweetDialogHelper;
import com.tab3e_app.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutTab3e extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R2.id.nav_view)
    NavigationView navigationView;


    @Nullable
    @BindView(R2.id.card_view1)
    CardView cardView1;

    @Nullable
    @BindView(R2.id.text1)
    TextView textView1;
    @Nullable
    @BindView(R2.id.text2)
    TextView textView2;

    @Nullable
    @BindView(R2.id.progressBar1)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_tab3e);
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

        if (AboutTab3e.this.getClass().getSimpleName().equalsIgnoreCase("AboutTab3e")) {
            //change text font
            Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);
            Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView2);

            if (Util.isOnline(this)){
                getSubject();
            }else {
                progressBar.setVisibility(View.GONE);
            }

        }
    }

    public void setToolbar() {
        Util.manipulateToolbar(this, toolbar, 0, null, true);
        ImageView backButton = (ImageView) toolbar.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //change font of drawer
    public void changeFontOfNavigation() {
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    public void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ask_about_student) {
            // Handle the camera action
            startActivity(new Intent(AboutTab3e.this, AskAboutStudent.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (id == R.id.tables) {
            startActivity(new Intent(AboutTab3e.this, TableTabs.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        }else if (id == R.id.child_list) {
            startActivity(new Intent(AboutTab3e.this, ChildrenListActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } else if (id == R.id.contact) {
            startActivity(new Intent(AboutTab3e.this, ContactWithSchool.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } else if (id == R.id.absent_list) {
            startActivity(new Intent(AboutTab3e.this, AbsentRoles.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } else if (id == R.id.infection_list) {
            startActivity(new Intent(AboutTab3e.this, InfractionRoles.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } else if (id == R.id.about_tab3e) {
            startActivity(new Intent(AboutTab3e.this, AboutTab3e.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } else if (id == R.id.log_out) {
            new Tab3ePrefStore(this).clearPreference();
            new AutoCompleteStore(this).clearPreference();

            startActivity(new Intent(AboutTab3e.this, SplashActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Tab3e");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.tab3e \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }

    }

    private void getSubject() {
        /**
         * this section for fetch country
         */
        String urlBrands = "http://followson.com/rest/getPage?id=1";
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.optJSONObject(0);
                    String name = jsonObject.optString("subject");
                    textView2.setText(name);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                progressBar.setVisibility(View.GONE);
                new SweetDialogHelper(AboutTab3e.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }
}
