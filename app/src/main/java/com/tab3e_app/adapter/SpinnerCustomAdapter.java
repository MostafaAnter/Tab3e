package com.tab3e_app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tab3e_app.R;
import com.tab3e_app.model.SpinnerModel;

import java.util.List;


/**
 * Created by mostafa on 20/06/16.
 */
public class SpinnerCustomAdapter extends ArrayAdapter {

    private Context mContext;
    private List<SpinnerModel> mDataset;
    private int textViewResourceId;
    LayoutInflater inflater;

    /*************
     * TeachersListAdapter Constructor
     *****************/
    public SpinnerCustomAdapter(
            Context mContext,
            int textViewResourceId,
            List<SpinnerModel> mDataset
    ) {
        super(mContext, textViewResourceId, mDataset);

        /********** Take passed values **********/
        this.mContext = mContext;
        this.mDataset = mDataset;
        this.textViewResourceId = textViewResourceId;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(textViewResourceId, parent, false);

        TextView label = (TextView) row.findViewById(R.id.label);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/DroidKufi-Regular.ttf");
        label.setTypeface(font);


        if (position == 0) {

            label.setText(mDataset.get(position).getLabel());
            label.setTextColor(Color.DKGRAY);
        } else {
            label.setText(mDataset.get(position).getLabel());
            label.setTextColor(Color.BLACK);
        }

        return row;
    }
}