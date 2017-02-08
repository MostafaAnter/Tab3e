package com.tab3e_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tab3e_app.R;
import com.tab3e_app.R2;
import com.tab3e_app.model.AbsentDocItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class AbsentDocAdapter extends RecyclerView.Adapter<AbsentDocAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<AbsentDocItem> mDataSet;
    private Context mContext;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public  class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.text1)TextView textView1;
        @BindView(R2.id.text2)TextView textView2;
        @BindView(R2.id.text3)TextView textView3;
        @BindView(R2.id.text4)TextView textView4;
        @BindView(R2.id.text5)TextView textView5;
        @BindView(R2.id.text6)TextView textView6;

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
    public AbsentDocAdapter(Context mContext, List<AbsentDocItem> dataSet) {
        this.mContext = mContext;
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.absent_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        switch (mDataSet.get(position).getDay()){
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
        switch (mDataSet.get(position).getTypeabsent()){
            case "small":
                viewHolder.getTextView4().setText("جزئي");
                break;
            case "all":
                viewHolder.getTextView4().setText("كلي");
                break;
        }

        switch (mDataSet.get(position).getTypewithe()){
            case "with":
                viewHolder.getTextView5().setText("بعذر");
                break;
            case "without":
                viewHolder.getTextView5().setText("بدون عذر");
                break;
        }

        viewHolder.getTextView6().setText(mDataSet.get(position).getHour1() +
        " : " + mDataSet.get(position).getHour2());

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}