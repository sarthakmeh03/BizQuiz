package in.bizquiz.app;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;


public class DisplayNotification extends Activity {	 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	

        SharedPreferences sp;
        
		
        sp = PreferenceManager.getDefaultSharedPreferences(this);
		boolean notify = sp.getBoolean("notify", false);
		
		
		
		      
      //---get the notification ID for the notification; 
        // passed in by the MainActivity---
        int notifID = getIntent().getExtras().getInt("NotifID");
        
        Intent intent = new Intent(this,Categories.class);
        PendingIntent displayIntent = PendingIntent.getActivity(
 	           this, 0, intent, 0);        
 
        if(notify){
        	
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);;
        
        NotificationCompat.Builder builder=
        		new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.logo)
        .setContentIntent(displayIntent)
        .setContentTitle("Message")
        .setContentText("Notification");
        nm.notify(notifID, builder.build());
        
        }
        
        
        finish();
	}
         
}