package com.tab3e.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tab3e.R;
import com.tab3e.R2;
import com.tab3e.app.AppController;
import com.tab3e.fragment.FragmentFive;
import com.tab3e.fragment.FragmentFour;
import com.tab3e.fragment.FragmentOne;
import com.tab3e.fragment.FragmentThree;
import com.tab3e.fragment.FragmentTwo;
import com.tab3e.model.TableItem;
import com.tab3e.store.Tab3ePrefStore;
import com.tab3e.util.Constants;
import com.tab3e.util.SweetDialogHelper;
import com.tab3e.util.Util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableTabs extends AboutTab3e
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R2.id.toolbar)Toolbar toolbar;
    @BindView(R2.id.nav_view)NavigationView navigationView;

    @Nullable @BindView(R2.id.tabs)
    TabLayout tabLayout;

    @Nullable @BindView(R2.id.viewpager)
    ViewPager viewPager;

    @Nullable @BindView(R2.id.text1)
    TextView textView1;

    @Nullable
    @BindView(R2.id.progressBar1)
    ProgressBar progressBar;

    @Nullable
    @BindView(R.id.noData)
    LinearLayout noDataView;

    protected List<TableItem> mDataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_tabs);
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
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);

        mDataset = new ArrayList<>();
        addFakeItems();

    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    Typeface makOnWayFont = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Regular.ttf");
                    ((TextView) tabViewChild).setTypeface(makOnWayFont);
                }
            }
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        FragmentOne fragmentOne = new FragmentOne();
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", mDataset.get(0));
        fragmentOne.setArguments(bundle);

        FragmentTwo fragmentTwo = new FragmentTwo();
        bundle = new Bundle();
        bundle.putParcelable("item", mDataset.get(1));
        fragmentTwo.setArguments(bundle);

        FragmentThree fragmentThree = new FragmentThree();
        bundle = new Bundle();
        bundle.putParcelable("item", mDataset.get(2));
        fragmentThree.setArguments(bundle);

        FragmentFour fragmentFour = new FragmentFour();
        bundle = new Bundle();
        bundle.putParcelable("item", mDataset.get(3));
        fragmentFour.setArguments(bundle);

        FragmentFive fragmentFive = new FragmentFive();
        bundle = new Bundle();
        bundle.putParcelable("item", mDataset.get(4));
        fragmentFive.setArguments(bundle);

        adapter.addFragment(fragmentOne, "الأحد");
        adapter.addFragment(fragmentTwo, "الأثنين");
        adapter.addFragment(fragmentThree, "الثلاثاء");
        adapter.addFragment(fragmentFour, "الأربعاء");
        adapter.addFragment(fragmentFive, "الخميس");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void addFakeItems(){
        /**
         * this section for fetch country
         */
        String urlBrands = "http://followson.com/rest/showStutable?id=" + new Tab3ePrefStore(this).getPreferenceValue(Constants.SCHOOL_ID)
                + "&year=" + new Tab3ePrefStore(this).getPreferenceValue(Constants.YEAR_ID)
                + "&term=" + new Tab3ePrefStore(this).getPreferenceValue(Constants.TERM_ID)
                + "&section=" + new Tab3ePrefStore(this).getPreferenceValue(Constants.SECTION_ID)
                + "&row=" + new Tab3ePrefStore(this).getPreferenceValue(Constants.ROW_ID);

        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("response", response);

                if (response.trim().isEmpty()){
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                    return;
                }

                Type listType = new TypeToken<ArrayList<TableItem>>(){}.getType();
                List<TableItem> yourClassList = new Gson().fromJson(response, listType);

                mDataset.addAll(yourClassList);
                progressBar.setVisibility(View.GONE);
                if (mDataset.size()<5){
                    noDataView.setVisibility(View.VISIBLE);
                }else if(mDataset.size() == 5) {
                    noDataView.setVisibility(View.GONE);

                    // set tab
                    setupViewPager(viewPager);
                    tabLayout.setupWithViewPager(viewPager);
                    changeTabsFont();
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
