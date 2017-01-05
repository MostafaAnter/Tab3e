package com.tab3e.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tab3e.R;
import com.tab3e.R2;
import com.tab3e.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnFocusChangeListener,
        View.OnClickListener{

    @BindView(R2.id.text1)TextView textView1;
    @BindView(R2.id.text2)TextView textView2;
    @BindView(R2.id.text3)TextView textView3;

    @BindView(R2.id.editText1)EditText editText1;
    @BindView(R2.id.editText2)EditText editText2;
    @BindView(R2.id.editText3)EditText editText3;
    @BindView(R2.id.editText4)EditText editText4;
    @BindView(R2.id.editText5)EditText editText5;

    @BindView(R2.id.linear1)LinearLayout linear1;
    @BindView(R2.id.linear2)LinearLayout linear2;
    @BindView(R2.id.linear3)LinearLayout linear3;
    @BindView(R2.id.linear4)LinearLayout linear4;
    @BindView(R2.id.linear5)LinearLayout linear5;

    @BindView(R2.id.checkbox1)CheckBox checkBox1;

    @BindView(R2.id.card_view1)CardView cardView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        // change font
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView2);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView3);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", editText3);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", editText4);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", editText5);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", editText1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", editText2);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", checkBox1);

        // set focuse
        editText1.setOnFocusChangeListener(this);
        editText2.setOnFocusChangeListener(this);
        editText3.setOnFocusChangeListener(this);
        editText4.setOnFocusChangeListener(this);
        editText5.setOnFocusChangeListener(this);

        // on click
        cardView1.setOnClickListener(this);

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view instanceof EditText){
            if (b){
                switch (view.getId()){
                    case R.id.editText1:
                        linear1.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                    case R.id.editText2:
                        linear2.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                    case R.id.editText3:
                        linear3.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                    case R.id.editText4:
                        linear4.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                    case R.id.editText5:
                        linear5.setBackgroundResource(R.drawable.border_shape_blue);
                        break;
                }
            }else {
                switch (view.getId()){
                    case R.id.editText1:
                        linear1.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                    case R.id.editText2:
                        linear2.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                    case R.id.editText3:
                        linear3.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                    case R.id.editText4:
                        linear4.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                    case R.id.editText5:
                        linear5.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_view1:
                break;
        }
    }
}
