package mesa.raydel.com.dialogs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SampleDialogFragment.OnMyDialogConfirmedListener, InputDialog.OnInputEnteredListener{
    private static final String TAG = "ANDROID LIFECYCLE ";

    private static final String INT_TO_BE_SAVED = "INSTANCE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "In the onCreate() method");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // Save the int in a bundle
        outState.putInt(INT_TO_BE_SAVED, 3);
        Log.d(TAG, "Saved in Bundle: " + outState.getInt(INT_TO_BE_SAVED) );

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        Log.d(TAG, "Restored from bundle: " + savedInstanceState.getInt(INT_TO_BE_SAVED) );
    }


    public void showDialog(View view) {
        SampleDialogFragment newFragment = SampleDialogFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "confirmDeleteArtPiece");
    }

    public void getName(View view) {
        InputDialog newFragment = InputDialog.newInstance();
        newFragment.show(getSupportFragmentManager(), "inputDialog");
    }

    public void sendNotification(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "ACTION_NOTIFY_ART_PIECE_CLOSE", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("ACTION_NOTIFY_ART_PIECE_CLOSE");
            channel.setLightColor(Color.LTGRAY);
            channel.canShowBadge();
            channel.setShowBadge(true);
            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setTicker("An art piece is close!")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Hello!")
                .setContentText("Very close")
                .setContentIntent(pi)
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("I hope you are having an amazing day!"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, notification);
    }


    @Override
    public void onDialogConfirmed() {
        Toast.makeText(this, "Alert confirmed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void greeting(String name) {
        Toast.makeText(this, "Hello, " + name + ". Nice to meet you!", Toast.LENGTH_LONG).show();
    }
}
