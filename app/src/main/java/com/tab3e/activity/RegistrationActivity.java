package com.tab3e.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tab3e.BuildConfig;
import com.tab3e.R;
import com.tab3e.R2;
import com.tab3e.app.AppController;
import com.tab3e.util.SweetDialogHelper;
import com.tab3e.util.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegistrationActivity extends AppCompatActivity implements View.OnFocusChangeListener,
        View.OnClickListener {

    @BindView(R2.id.text1)
    TextView textView1;
    @BindView(R2.id.text2)
    TextView textView2;
    @BindView(R2.id.text3)
    TextView textView3;

    @BindView(R2.id.editText1)
    EditText editText1;
    @BindView(R2.id.editText2)
    EditText editText2;
    @BindView(R2.id.editText3)
    EditText editText3;
    @BindView(R2.id.editText4)
    EditText editText4;
    @BindView(R2.id.editText5)
    EditText editText5;

    @BindView(R2.id.linear1)
    LinearLayout linear1;
    @BindView(R2.id.linear2)
    LinearLayout linear2;
    @BindView(R2.id.linear3)
    LinearLayout linear3;
    @BindView(R2.id.linear4)
    LinearLayout linear4;
    @BindView(R2.id.linear5)
    LinearLayout linear5;

    @BindView(R2.id.checkbox1)
    CheckBox checkBox1;

    @BindView(R2.id.card_view1)
    CardView cardView1;

    private String password, userName, mobile, email;

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
        if (view instanceof EditText) {
            if (b) {
                switch (view.getId()) {
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
            } else {
                switch (view.getId()) {
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
        switch (view.getId()) {
            case R.id.card_view1:
                register();
                break;
        }
    }

    private void register() {
        if (Util.isOnline(this)) {
            if (registerConditionsIsOk()) {
                // Set up a progress dialog
                final SweetDialogHelper sdh = new SweetDialogHelper(this);
                sdh.showMaterialProgress("تحميل..");

                // Tag used to cancel the request
                String tag_string_req = "string_req";
                String url = BuildConfig.REGISTER_PARENT_URL;

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        sdh.dismissDialog();
                        // parseFeed(response);
                        Log.d("response", response);
                        //  startActivity(new Intent(SignInActivity.this, ClientHomeActivity.class));
                        // finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sdh.dismissDialog();
                        // show error message
                        sdh.showErrorMessage("خطأ", "الرجاء إعادة المحاولة");
                    }
                }) {


                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("pass", password);
                        params.put("uname", userName);
                        params.put("mobile", mobile);
                        return params;

                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
            }
        } else {
            // show error message
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ناسف...")
                    .setContentText("هناك مشكله بشبكة الانترنت حاول مره اخرى")
                    .show();
        }
    }

    private boolean registerConditionsIsOk() {

        if (!checkBox1.isChecked()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "الرجاء الموافقة على الشروط والأحكام");
            return false;
        }

        userName = editText1.getText().toString().trim();
        mobile = editText2.getText().toString().trim();
        email = editText3.getText().toString().trim();
        password = editText4.getText().toString().trim();
        String password_confirmation = editText5.getText().toString().trim();

        if (userName == null || userName.trim().isEmpty()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "الرجاء أدخال أسم المستخدم");
            return false;
        }
        if (mobile == null || mobile.trim().isEmpty()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "الرجاء أدخال رقم الهاتف");
            return false;
        } else if (!checkPhoneNumber(mobile)) {
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "الرجاء إدخال البريد الألكتروني");
            return false;
        }
        // first check mail format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "قم بإدخال بريد صالح!");
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "الرجاء إدخال الرقم السري");
            return false;
        }

//        if (!password.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$") &&
//                !password.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$")){
//            new SweetDialogHelper(this).showErrorMessage(getString(R.string.error), getString(R.string.password_should));
//            return false;
//
//        }

        if (!password.equalsIgnoreCase(password_confirmation)) {
            new SweetDialogHelper(this).showErrorMessage("خطأ", "الرقم السري غير متطابق");
            return false;
        }
        return true;

    }

    private boolean checkPhoneNumber(String phoneNumer) {
        if (Util.LongNationalNumber(phoneNumer) == null) {
            new SweetDialogHelper(this).showErrorMessage("خطأ",
                    "أدخل رقم هاتف يحوي كود الدولة");
            return false;
        } else if (phoneNumer.charAt(0) != '+') {
            new SweetDialogHelper(this).showErrorMessage("خطأ",
                    "أدخل رقم هاتف يحوي كود الدولة");
            return false;
        } else
            return true;

    }

}
