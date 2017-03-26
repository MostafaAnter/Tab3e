package com.tab3e_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tab3e_app.R;
import com.tab3e_app.R2;
import com.tab3e_app.store.AutoCompleteStore;
import com.tab3e_app.store.Tab3ePrefStore;
import com.tab3e_app.util.Constants;
import com.tab3e_app.util.SweetDialogHelper;
import com.tab3e_app.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R2.id.text1)TextView loadingText;
    @BindView(R2.id.progressBar)ProgressBar progressBar;

    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // change text font
        Util.changeViewTypeFace(this, "fonts/DroidKufi-Regular.ttf", loadingText);

        // loading task
        loadingTask();
    }

    private void loadingTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    // Update the progress status
                    progressStatus +=1;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            if (progressStatus == 100){
                                if (getIntent().getExtras() != null && !getIntent().getExtras().getString("id_card", "").isEmpty()){
                                    Intent intent = new Intent(SplashActivity.this, ResultOfAskAboutStudent.class);
                                    intent.putExtra("sID", getIntent().getExtras().getString("id_school", ""));
                                    intent.putExtra("stID", getIntent().getExtras().getString("id_card", ""));
                                    intent.putExtra("name", "");
                                    startActivityForResult(intent, 101);

                                }else if (new Tab3ePrefStore(SplashActivity.this).getPreferenceValue(Constants.USER_ID).trim().isEmpty()) {
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                } else {
                                    startActivity(new Intent(SplashActivity.this, AskAboutStudent.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                }
                            }

                        }
                    });
                }
            }
        }).start(); // Start the operation
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                //---get the result using getIntExtra()---
                new SweetDialogHelper(SplashActivity.this).showWarningMessage("عفوا", "لاتوجد نتأج تأكد من بيانات الطالب", "موافق");
            }
        }
    }
}
