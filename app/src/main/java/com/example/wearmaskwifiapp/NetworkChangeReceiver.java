package com.example.wearmaskwifiapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NetworkChangeReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if(isOnline(context)){
                Toast.makeText(context,"Network Connected",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"No Network Connected",Toast.LENGTH_SHORT).show();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Wear Mask");
                builder.setContentTitle("Wear Mask");
                builder.setContentText("I think you are outside your home and hence you should wear a mask");
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText("I think you are outside your home and hence you should wear a mask"));
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
                managerCompat.notify(1,builder.build());

                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    vibrator.vibrate(VibrationEffect.createOneShot(300,VibrationEffect.DEFAULT_AMPLITUDE));
                }

            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isOnline(Context context){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo!=null && networkInfo.isConnected());
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
            return  false;
        }
    }
}
