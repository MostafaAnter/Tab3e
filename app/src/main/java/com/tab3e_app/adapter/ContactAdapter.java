package com.tab3e_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tab3e_app.R;
import com.tab3e_app.R2;
import com.tab3e_app.model.ContactItem;
import com.tab3e_app.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<ContactItem> mDataSet;
    private Context mContext;

    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

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
        @BindView(R2.id.linear1)
        LinearLayout linearLayout1;
        @BindView(R2.id.linear2)
        LinearLayout linearLayout2;

        public LinearLayout getLinearLayout2() {
            return linearLayout2;
        }

        public LinearLayout getLinearLayout1() {
            return linearLayout1;
        }

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
    public ContactAdapter(Context mContext, List<ContactItem> dataSet) {
        this.mContext = mContext;
        mDataSet = dataSet;
        permissionStatus = mContext.getSharedPreferences("permissionStatus", MODE_PRIVATE);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");


        viewHolder.getTextView1().setText(mDataSet.get(position).getJob_name() + "/ ");
        viewHolder.getTextView2().setText(mDataSet.get(position).getName());
        viewHolder.getTextView3().setText(mDataSet.get(position).getMobile());
        viewHolder.getTextView4().setText(mDataSet.get(position).getEmail());

        //change text font
        Util.changeViewTypeFace(mContext, "fonts/DroidKufi-Regular.ttf", viewHolder.getTextView1());
        Util.changeViewTypeFace(mContext, "fonts/DroidKufi-Regular.ttf", viewHolder.getTextView2());
        Util.changeViewTypeFace(mContext, "fonts/DroidKufi-Regular.ttf", viewHolder.getTextView3());
        Util.changeViewTypeFace(mContext, "fonts/DroidKufi-Regular.ttf", viewHolder.getTextView4());

        viewHolder.getLinearLayout1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnCall(viewHolder);

            }
        });

        viewHolder.getLinearLayout2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Send email", "");
                String[] TO = {viewHolder.getTextView4().getText().toString()};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "تابع");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                try {
                    mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Get element from your dataset at this position and replace the contents of the view
        // with that element
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void clickOnCall(ViewHolder viewHolder) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + viewHolder.getTextView3().getText().toString()));
        mContext.startActivity(callIntent);
    }
}