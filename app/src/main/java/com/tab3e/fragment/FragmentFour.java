package com.tab3e.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tab3e.R;
import com.tab3e.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mostafa_anter on 1/8/17.
 */

public class FragmentFour extends Fragment{

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

    public FragmentFour(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        ButterKnife.bind(this,view);

        return view;
    }
}
