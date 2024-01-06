package com.project.maku_mobile_based.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.project.maku_mobile_based.R;

import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private TextView textTotalPrice;
    private String tempTotal;

    private Button buttonCancelOrder, buttonCheckStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        textTotalPrice = findViewById(R.id.txtTotalPrice);
        tempTotal = getIntent().getStringExtra("totalPrice");
        textTotalPrice.setText(tempTotal);


        buttonCancelOrder = findViewById(R.id.btnCancelOrder);

        buttonCancelOrder.setOnClickListener(v->{
                Intent intent = new Intent(CheckoutActivity.this,MenuActivity.class);
                startActivity(intent);

        });

        buttonCheckStatus = findViewById(R.id.btnCheckStatus);

        buttonCheckStatus.setOnClickListener(v->{
            showNotification("MAKU", "Payment success!");
            Intent intent = new Intent(CheckoutActivity.this,SuccessActivity.class);
            startActivity(intent);
        });
    }

    private void showNotification(String title, String message) {
        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Heads Up Notification", NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        PendingIntent pendingIntent = createPendingIntent();


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.maku_logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        notificationManager.notify(1, notificationBuilder.build());
    }


    private PendingIntent createPendingIntent() {
        Intent intent = new Intent(this, SuccessActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Menambahkan FLAG_IMMUTABLE untuk kompatibilitas dengan Android 12 dan yang lebih tinggi
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }


}