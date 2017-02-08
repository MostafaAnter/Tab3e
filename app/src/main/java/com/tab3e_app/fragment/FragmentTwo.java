package com.tab3e_app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.tab3e_app.R;
import com.tab3e_app.R2;
import com.tab3e_app.app.AppController;
import com.tab3e_app.model.TableItem;
import com.tab3e_app.util.SweetDialogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mostafa_anter on 1/8/17.
 */

public class FragmentTwo extends Fragment{
    @Nullable
    @BindView(R2.id.text3)
    TextView textView3;
    @Nullable
    @BindView(R2.id.text5)
    TextView textView5;
    @Nullable
    @BindView(R2.id.text7)
    TextView textView7;
    @Nullable
    @BindView(R2.id.text9)
    TextView textView9;
    @Nullable
    @BindView(R2.id.text11)
    TextView textView11;
    @Nullable
    @BindView(R2.id.text13)
    TextView textView13;
    @Nullable
    @BindView(R2.id.text15)
    TextView textView15;
    @Nullable
    @BindView(R2.id.text17)
    TextView textView17;

    private TableItem tableItem;

    public FragmentTwo(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableItem = getArguments().getParcelable("item");

        if (tableItem != null)
            bindData();
    }

    private void bindData(){
        getNameOfClass(tableItem.getFirest(), textView3);
        getNameOfClass(tableItem.getSecond(), textView5);
        getNameOfClass(tableItem.getTherd(), textView7);
        textView9.setText(tableItem.getFree());
        getNameOfClass(tableItem.getFourth(), textView11);
        getNameOfClass(tableItem.getFifth(), textView13);
        getNameOfClass(tableItem.getSixth(), textView15);
        getNameOfClass(tableItem.getSeventh(), textView17);
    }

    private void getNameOfClass(String id, final TextView tv) {
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
                    String name = jsonObject.optString("name");
                    tv.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
                new SweetDialogHelper(getActivity()).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }
}
