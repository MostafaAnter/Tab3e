package com.tab3e.util;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 * Created by mostafa_anter on 1/4/17.
 */

public class Util {
    // -------------------- start of toolbar utils----------------------
    // when use animate view function you must implement this interface
    public interface NavigationOnClickListener {
        public void setNavigationOnClickListener();
    }

    /**
     * set navigation icon, hide title and subtitle
     * @param mContext
     * @param toolbar
     * @param navigationIcon equal 0 if not want to add navigation icon although set navigationOnClick null
     * @param navigationOnClickListener must implement this when use this function
     */
    public static void manipulateToolbar(@NonNull AppCompatActivity mContext,
                                         @NonNull Toolbar toolbar,
                                         int navigationIcon,
                                         @Nullable final NavigationOnClickListener navigationOnClickListener,
                                         boolean isCustoomTitle){
        mContext.setSupportActionBar(toolbar);

        if (navigationIcon != 0 && navigationOnClickListener != null) {
            toolbar.setNavigationIcon(navigationIcon);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigationOnClickListener.setNavigationOnClickListener();
                }
            });
        }

        if (isCustoomTitle) {
        /*
        * hide title
        * */
            if (mContext.getSupportActionBar() != null)
                mContext.getSupportActionBar().setDisplayShowTitleEnabled(false);
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
            toolbar.setTitle("");
            toolbar.setSubtitle("");
        }
    }

    /**
     *
     * @param mContext current context
     * @param fontPath path to font.ttf ex. "fonts/normal.ttf" if there fonts directory under assets
     * @param view that view you want to change it type face should extend text view
     */
    public static void changeViewTypeFace(Context mContext, String fontPath, TextView view){
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), fontPath);
        view.setTypeface(font);
    }

    public static boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public static Long LongNationalNumber(String mNumberStr){
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber mNumberProto = null;
        try {
            mNumberProto = phoneUtil.parse(mNumberStr, "");
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        if(mNumberProto == null){
            // there is no number show error message
            return null;
        }
        return mNumberProto.getNationalNumber();
    }
}
