package com.boilingstocks.BizQuiz;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.BizQuiz.R;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
	
	EditText name_user;
	EditText pass_user;
	String uname;
	String pass;
	
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);
       
       Button login=(Button) findViewById(R.id.bt_login);
       name_user=(EditText) findViewById(R.id.ed_login);
       pass_user=(EditText) findViewById(R.id.ed_pass);
       
       login.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			uname=name_user.getText().toString();
			pass=pass_user.getText().toString();
			
			new Loginvalidation(Login.this).execute(uname,pass);
		}
	});
       
       
	}

	
	public class Loginvalidation extends AsyncTask<String, Void, Boolean> {

		Context context;
		ProgressDialog pDialog;
		JSONObject jsonObject = new JSONObject();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    String url="http://practice.site11.com/loginvalidation.php";
	    int status;
	    String uphone;
        String uemail;
        String ucity;
        String uage;
        int score1,score2,score3,score4,score5,score6,score7,score8;
	    
	    
	    public Loginvalidation(Context ctx) {
	        
	        this.context = ctx;
	         pDialog= new ProgressDialog(context);
	    }

	    
		@Override
		protected void onPreExecute(){
			pDialog.setTitle("Please Wait...");
			pDialog.setMessage("Logging In...");
			pDialog.setIndeterminate(true);
			pDialog.show();
		}
		
		@Override
		public Boolean doInBackground(String...str){

			String usname=str[0];
			String psswd=str[1];
			Log.d("String",usname+psswd);
			
			params.add(new BasicNameValuePair("username",usname));
			params.add(new BasicNameValuePair("password",psswd));
			
			jsonObject = jsonParser.makeHttpRequest(url, "GET", params);
			
			
			try {
				
				status = jsonObject.getInt("status");
				uphone=jsonObject.getString("Contact");
				uemail=jsonObject.getString("Email");
				ucity=jsonObject.getString("City");
				uage=jsonObject.getString("Age");
				score1 = jsonObject.getInt("Category1");
				score2 = jsonObject.getInt("Category2");
				score3 = jsonObject.getInt("Category3");
				score4 = jsonObject.getInt("Category4");
				score5 = jsonObject.getInt("Category5");
				score6 = jsonObject.getInt("Category6");
				score7 = jsonObject.getInt("Category7");
				score8 = jsonObject.getInt("Category8");
				
				Log.d("values of score",Integer.toString(score1)+Integer.toString(score2)+Integer.toString(score3)+
						                Integer.toString(score4)+Integer.toString(score5)+
				                        Integer.toString(score6)+Integer.toString(score7)+Integer.toString(score8));
                 				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
			
}

		protected void onPostExecute(Boolean b){
			
			pDialog.dismiss();
			
		   if(status==1)
		   {
			   
			   sp = context.getSharedPreferences("First_run", MODE_PRIVATE);
			    SharedPreferences.Editor editor = sp.edit();
			    editor.putBoolean("Firstrun", false);
			    editor.putString("Username",uname);
			    editor.putString("Password", pass);
			    editor.putString("Contact",uphone);
			    editor.putString("Email",uemail);
			    editor.putString("Age",uage);
			    editor.putString("City",ucity);
			    editor.putInt("Category1",score1);
			    editor.putInt("Category2",score2);
			    editor.putInt("Category3",score3);
			    editor.putInt("Category4",score4);
			    editor.putInt("Category5",score5);
			    editor.putInt("Category6",score6);
			    editor.putInt("Category7",score7);
			    editor.putInt("Category8",score8);
			    editor.commit();	    

			    Toast.makeText(context, "Login Correct.", Toast.LENGTH_LONG).show();
			   Intent intent=new Intent(context,Categories.class);
			   context.startActivity(intent);
		   }
		   else{
			   Toast.makeText(context, "Login Incorrect.Please try again", Toast.LENGTH_LONG).show();
			   context.startActivity(new Intent(context,Login.class));
		   }
					
		}
}
	
	@Override
    public void onBackPressed() {
       Log.d("CDA", "onBackPressed Called");
       Intent back = new Intent(this,Register.class);
//       setIntent.addCategory(Intent.CATEGORY_HOME);
//       setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(back);
    }

	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                                                        INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}