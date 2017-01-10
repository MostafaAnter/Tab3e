package com.tab3e.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tab3e.R;
import com.tab3e.R2;
import com.tab3e.store.Tab3ePrefStore;
import com.tab3e.util.Constants;
import com.tab3e.util.Util;

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
                                if (new Tab3ePrefStore(SplashActivity.this).getPreferenceValue(Constants.USER_ID).trim().isEmpty()) {
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
}
