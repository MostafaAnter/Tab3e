package com.tab3e.activity;

import android.content.Intent;
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
import com.tab3e.store.Tab3ePrefStore;
import com.tab3e.util.Constants;
import com.tab3e.util.SweetDialogHelper;
import com.tab3e.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity implements View.OnFocusChangeListener,
        View.OnClickListener{

    @BindView(R2.id.text1)TextView textView1;
    @BindView(R2.id.text2)TextView textView2;
    @BindView(R2.id.text3)TextView textView3;
    @BindView(R2.id.text4)TextView textView4;
    @BindView(R2.id.text5)TextView textView5;
    @BindView(R2.id.text6)TextView textView6;
    @BindView(R2.id.editText1)EditText editText1;
    @BindView(R2.id.editText2)EditText editText2;
    @BindView(R2.id.checkbox1)CheckBox checkBox1;
    @BindView(R2.id.linear1)LinearLayout linear1;
    @BindView(R2.id.linear2)LinearLayout linear2;

    @BindView(R2.id.card_view1)CardView cardView1;
    @BindView(R2.id.card_view2)CardView cardView2;


    private String email, password, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // change font
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView2);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView3);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView4);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView5);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", textView6);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", editText1);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", editText2);
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", checkBox1);

        // set focuse
        editText1.setOnFocusChangeListener(this);
        editText2.setOnFocusChangeListener(this);

        // on click
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);

        String email = getIntent().getStringExtra("email") != null ?
                getIntent().getStringExtra("email") : "";
        String password = getIntent().getStringExtra("password") != null ?
                getIntent().getStringExtra("password") : "";

        editText1.setText(email);
        editText2.setText(password);
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
                }
            }else {
                switch (view.getId()){
                    case R.id.editText1:
                        linear1.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                    case R.id.editText2:
                        linear2.setBackgroundResource(R.drawable.border_shape_gray);
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_view1:
                login();
                break;
            case R.id.card_view2:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
            case R.id.text3:
                startActivity(new Intent(this, ForgetPassword.class));
                break;
            case R.id.text4:
                startActivity(new Intent(this, ForgetPassword.class));
                break;
        }
    }

    private boolean validateDate(){
        email = editText1.getText().toString().trim();
        password = editText2.getText().toString().trim();


        if (email != null && !email.trim().isEmpty()
                && password != null && !password.trim().isEmpty()){

            return true;

        }else {
            // show error message
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("نأسف !")
                    .setContentText("قم بإكمال تسجيل البيانات")
                    .show();
            return false;
        }
    }

    private void login() {
        if (Util.isOnline(this)) {
            if (validateDate()) {
                // Set up a progress dialog
                final SweetDialogHelper sdh = new SweetDialogHelper(this);
                sdh.showMaterialProgress("تحميل..");

                // Tag used to cancel the request
                String tag_string_req = "string_req";
                String url = BuildConfig.LOGIN_URL;

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        sdh.dismissDialog();
                       // parseFeed(response);
                        Log.d("response", response);
                        if (isSuccess(response)) {
                            Intent intent = new Intent(LoginActivity.this, AskAboutStudent.class);
                            if (checkBox1.isChecked())
                                new Tab3ePrefStore(LoginActivity.this).addPreference(Constants.USER_ID, userId);
                            startActivity(intent);
                            finish();
                        } else {
                            sdh.showErrorMessage("خطأ", "أسم أو باسورد خطأ الرجاء إعادة المحاولة");
                        }
                      //  startActivity(new Intent(SignInActivity.this, ClientHomeActivity.class));
                       // finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sdh.dismissDialog();
                        // show error message
                        sdh.showErrorMessage("خطأ", "الأسم أو الرقم السري ربما غير صالح");
                    }
                }) {


                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uname", email);
                        params.put("pwd", password);
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

    private boolean isSuccess(String feed) {

        try {
            JSONArray jsonArray = new JSONArray(feed);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String id = jsonObject.optString("ID");

            if (id.trim().isEmpty()) {
                return false;
            } else {
                userId = id;
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

}
