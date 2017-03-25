package com.tab3e_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.tab3e_app.activity.StudentDetails;
import com.tab3e_app.app.AppController;
import com.tab3e_app.model.ChildItem;
import com.tab3e_app.model.StudentData;
import com.tab3e_app.store.Tab3ePrefStore;
import com.tab3e_app.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<ChildItem> mDataSet;
    private Context mContext;

    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text2)
        TextView text2;
        @BindView(R.id.text3)
        TextView text3;
        @BindView(R.id.card_view1)
        CardView cardView1;

        public TextView getText2() {
            return text2;
        }

        public TextView getText3() {
            return text3;
        }

        public CardView getCardView1() {
            return cardView1;
        }

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });

        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public ChildrenAdapter(Context mContext, List<ChildItem> dataSet) {
        this.mContext = mContext;
        mDataSet = dataSet;
        permissionStatus = mContext.getSharedPreferences("permissionStatus", MODE_PRIVATE);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.children_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        getStudentData(mDataSet.get(position), viewHolder);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void getStudentData(ChildItem item, final ViewHolder viewHolder ) {
        /**
         * this section for fetch country
         */
        String studentID = item.getId_son();
        String schoolID = item.getId_school();

        String urlBrands = BuildConfig.GET_STUDENT_DATA + schoolID
                + "&id_card=" + studentID ;
        // making fresh volley request and getting jsonstatus_request
        StringRequest jsonReq = new StringRequest(Request.Method.GET,
                urlBrands, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response", response);

                JSONArray array = null;
                try {
                    array = new JSONArray(response);
                    JSONObject object = array.optJSONObject(0);
                    String status = object.optString("status");
                    if (!status.equalsIgnoreCase("Failed")) {

                        if (!response.trim().isEmpty() || !statusFailed(response)) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.optJSONObject(0);
                                Gson gson = new Gson();
                                final StudentData s = gson.fromJson(jsonObject.toString(), StudentData.class);
                                if (s != null){
                                    viewHolder.getText2().setText(s.getName());
                                    viewHolder.getText3().setText(s.getId_card());
                                    viewHolder.getCardView1().setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new Tab3ePrefStore(mContext).addPreference(Constants.STUDENT_ID, s.getID());
                                            new Tab3ePrefStore(mContext).addPreference(Constants.SCHOOL_ID, s.getId_school());
                                            new Tab3ePrefStore(mContext).addPreference(Constants.YEAR_ID, s.getYear());
                                            new Tab3ePrefStore(mContext).addPreference(Constants.SECTION_ID, s.getSection());
                                            new Tab3ePrefStore(mContext).addPreference(Constants.ROW_ID, s.getRow());
                                            new Tab3ePrefStore(mContext).addPreference(Constants.TERM_ID, s.getTerm());
                                            new Tab3ePrefStore(mContext).addPreference(Constants.STUDENT_ID_CARD, s.getId_card());
                                            mContext.startActivity(new Intent(mContext, StudentDetails.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }else {
                        mDataSet.remove(viewHolder.getPosition());
                        notifyDataSetChanged();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
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
}