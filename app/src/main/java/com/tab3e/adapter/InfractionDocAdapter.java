package com.tab3e.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.tab3e.R;
import com.tab3e.R2;
import com.tab3e.activity.InfractionDoc;
import com.tab3e.app.AppController;
import com.tab3e.model.AbsentDocItem;
import com.tab3e.model.InfractionDocItem;
import com.tab3e.util.SweetDialogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class InfractionDocAdapter extends RecyclerView.Adapter<InfractionDocAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<InfractionDocItem> mDataSet;
    private Context mContext;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.text1)
        TextView textView1;
        @BindView(R2.id.text2)
        TextView textView2;
        @BindView(R2.id.text3)
        TextView textView3;
        @BindView(R2.id.text4)
        TextView textView4;
        @BindView(R2.id.text5)
        TextView textView5;
        @BindView(R2.id.text6)
        TextView textView6;

        public TextView getTextView1() {
            return textView1;
        }

        public TextView getTextView2() {
            return textView2;
        }

        public TextView getTextView3() {
            return textView3;
        }

        public TextView getTextView4() {
            return textView4;
        }

        public TextView getTextView5() {
            return textView5;
        }

        public TextView getTextView6() {
            return textView6;
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
    public InfractionDocAdapter(Context mContext, List<InfractionDocItem> dataSet) {
        this.mContext = mContext;
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.infraction_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        switch (mDataSet.get(position).getDay()) {
            case "sunday":
                viewHolder.getTextView1().setText("الأحد");
                break;
            case "monday":
                viewHolder.getTextView1().setText("الأثنين");
                break;
            case "tuesday":
                viewHolder.getTextView1().setText("الثلاثاء");
                break;
            case "wednesday":
                viewHolder.getTextView1().setText("الأربعاء");
                break;
            case "thursday":
                viewHolder.getTextView1().setText("الخميس");
                break;
        }
        viewHolder.getTextView2().setText(mDataSet.get(position).getM_date() + "م");
        viewHolder.getTextView3().setText(mDataSet.get(position).getH_date() + "ه");


        getNameOfClass(mDataSet.get(position).getTypeabsent(), viewHolder.getTextView4(), (FragmentActivity) mContext);
        viewHolder.getTextView5().setText(mDataSet.get(position).getHour1() +
                " : " + mDataSet.get(position).getHour2());
        getNameOfClass(mDataSet.get(position).getAbsent(), viewHolder.getTextView6(), (FragmentActivity) mContext);

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void getNameOfClass(String id, final TextView tv, final FragmentActivity mContext) {
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
                new SweetDialogHelper(mContext).showErrorMessage("عفوا", "قم بإغلاق الصفحة واعادة فتحهامن جديد");
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }
}