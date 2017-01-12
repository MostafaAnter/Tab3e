package com.tab3e.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tab3e.BuildConfig;
import com.tab3e.R;
import com.tab3e.R2;
import com.tab3e.adapter.AbsentDocAdapter;
import com.tab3e.adapter.ContactAdapter;
import com.tab3e.adapter.SpinnerCustomAdapter;
import com.tab3e.app.AppController;
import com.tab3e.model.AbsentDocItem;
import com.tab3e.model.ContactItem;
import com.tab3e.model.SpinnerModel;
import com.tab3e.store.Tab3ePrefStore;
import com.tab3e.util.Constants;
import com.tab3e.util.SweetDialogHelper;
import com.tab3e.util.Util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactWithSchool extends AboutTab3e
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
    @BindView(R2.id.spinner1)
    Spinner spinner1;


    @Nullable
    @BindView(R.id.noData)
    LinearLayout noDataView;
    @Nullable
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Nullable
    @BindView(R2.id.progressBar1)
    ProgressBar progressBar;


    // for recycler view
    private static final String TAG = "ProviderChatsFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    protected LayoutManagerType mCurrentLayoutManagerType;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected ContactAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<ContactItem> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_with_school);
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

        // set spinner
        List<SpinnerModel> models = new ArrayList<>();
        models.add(new SpinnerModel("المدرسة", ""));
        populateSpinner1(models);

        //change text font
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView2);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView3);


        // set recyclerView
        setRecyclerView(savedInstanceState);

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void setRecyclerView(Bundle savedInstanceState) {
        // initiate mDataSet
        mDataset = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(this);

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new ContactAdapter(this, mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);


        // get data
        addFakeItems();
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private void addFakeItems() {
        /**
         * this section for fetch country
         */
        String urlBrands = "http://followson.com/rest/showContact?id=" +
                new Tab3ePrefStore(this).getPreferenceValue(Constants.SCHOOL_ID) ;
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                Type listType = new TypeToken<ArrayList<ContactItem>>() {
                }.getType();
                List<ContactItem> yourClassList = new Gson().fromJson(response, listType);

                mDataset.addAll(yourClassList);
                mAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                if (mDataset.size() < 1) {
                    noDataView.setVisibility(View.VISIBLE);
                } else {
                    noDataView.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                new SweetDialogHelper(ContactWithSchool.this).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);

    }
}
