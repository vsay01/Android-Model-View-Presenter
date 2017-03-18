package com.vsay.demo.mvp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.vsay.demo.mvp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by vsaya on 2/11/17.
 */

public class LocalUtils {

    public static String getDate(String OurDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = format.parse(OurDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm a", Locale.US); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            OurDate = dateFormatter.format(value);
        } catch (Exception e) {
            Log.i(LocalUtils.class.getName(), e.toString());
            OurDate = "";
        }
        return OurDate;
    }

    public static boolean isTelephonyEnabled(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        return telephonyManager != null && telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public static AlertDialog.Builder buildNoNetworkDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(R.string.no_internet_title);
        builder.setMessage(R.string.no_internet_content);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        return builder;
    }
}