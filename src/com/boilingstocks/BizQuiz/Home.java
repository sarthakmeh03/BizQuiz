package com.boilingstocks.BizQuiz;

import java.sql.Date;
import java.util.Calendar;

import com.BizQuiz.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {
	
	SharedPreferences sp;
	
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_home);
       
       sp=this.getSharedPreferences("First_run", MODE_PRIVATE);
       SharedPreferences.Editor editor = sp.edit();
       boolean clear_prefs=sp.getBoolean("clear_prefrences", true);
       
       Calendar c = Calendar.getInstance();   // this takes current date
       int date_of_month=c.get(Calendar.DAY_OF_MONTH);

       if(date_of_month==1 && clear_prefs ){
    	
    	   this.getSharedPreferences("First_run", MODE_PRIVATE).edit().clear().commit();
    	   Log.d("prefrences","cleared");
    	   System.out.println(c.getTime()); 
    	   
    	   editor.putBoolean("clear_prefrences", false);
    	   editor.commit();
          }else if(date_of_month>1){
        	  editor.putBoolean("clear_prefrences", true);
        	  editor.commit();
          }
          
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);                 
	     
		   Date date=new Date(0);  
	       //---get current date and time---
	       Calendar cal_alarm = Calendar.getInstance();       
	       Calendar cal_now=Calendar.getInstance();
	       //---sets the time for the alarm to trigger---      
	       cal_alarm.set(Calendar.HOUR_OF_DAY, 17);
	       cal_alarm.set(Calendar.MINUTE, 00);
	       cal_alarm.set(Calendar.SECOND, 00);
	       if(cal_alarm.before(cal_now)){//if its in the past increment
	           cal_alarm.add(Calendar.DATE,1);
	       }
	       //---PendingIntent to launch activity when the alarm triggers---                    
	       Intent i = new Intent(this,DisplayNotification.class);

	       //---assign an ID of 1---
	       i.putExtra("NotifID", 1);                                

	       PendingIntent displayIntent = PendingIntent.getActivity(
	           this, 0, i, 0);               

	       //---sets the alarm to trigger---
	       alarmManager.set(AlarmManager.RTC_WAKEUP, 
	           cal_alarm.getTimeInMillis(), displayIntent);  

	       

       
       Boolean check=sp.getBoolean("Firstrun", true);
       
       Log.d("firstrun",Boolean.toString(check));
       if(check){

    	    mCountDown.start();
   
       }else{
 
    	   Intent categories=new Intent(Home.this,Categories.class);
			startActivity(categories);

       }
	
	}       
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
   		// Inflate the menu; this adds items to the action bar if it is present.
   		getMenuInflater().inflate(R.menu.activity_home, menu);
   		return true;
   	}
        

      
	public boolean onOptionsItemSelected(MenuItem item) {

   		switch (item.getItemId()) {
   		case R.id.menu_settings:
   			startActivity(new Intent(this, Settings.class));
   			return true;
   		case R.id.menu_about:
   			startActivity(new Intent(this, AboutUs.class));
   			return true;
   		case R.id.menu_exit:
   			Intent intent = new Intent(Intent.ACTION_MAIN);
   			intent.addCategory(Intent.CATEGORY_HOME);
   			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   			startActivity(intent);
   			return true;	
   		case R.id.menu_feedback:
   			Intent intent1=new Intent(this,Feedback.class);
   			startActivity(intent1);
   			return true;
   		case R.id.menu_statistics:
 		     startActivity(new Intent(this,Statistics.class));
 		     return true;
   		case R.id.menu_archive:
   			Intent intent3 = new Intent(this,ArchiveMonthsActivity.class);
   			startActivity(intent3);
   			return true;
 		     
   		default:
   			return super.onOptionsItemSelected(item);
   		}
    
       }
	protected CountDownTimer mCountDown = new CountDownTimer(10000, 1000)
    {

        @Override
        public void onTick(long millisUntilFinished)
        {
        	
        	Log.d("Timer","time for 10sec");
        	       	            
        	        }

        @Override
        public void onFinish()
        {   
        	Log.d("Timer","3mins finish");
        	
        		
        		Intent register=new Intent(Home.this,Register.class);
    			startActivity(register);
    		        	
        	}
        };

}