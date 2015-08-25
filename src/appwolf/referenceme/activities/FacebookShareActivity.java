package appwolf.referenceme.activities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import appwolf.referenceme.application.ReferenceMeApplication;

/**
 * @author eranga
 * share facebook layout
 */
public class FacebookShareActivity extends Activity implements OnClickListener{

	//back button
	Button backBUtton;
	
	//login button
	Button loginButton;
	
	//share button
	Button shareButton;
	
	//cancel button
	Button cancelButton;
	
	//reference text
	EditText referenceText;
	
	//application
	ReferenceMeApplication application;
	
	//facebook
	private Facebook facebook;
	
	//app id
	private static final String APP_ID = "343287469054819";
	
	//facebook permission
	private static final String[] PERMISSIONS = new String[] {"publish_stream"};
	
	//facebook thread class
	private AsyncFacebookRunner asyncFacebookRunner;
	
	//file name
	String FILENAME = "AndroidSSO_data";
	
	//sharead preference
	private SharedPreferences shareadPreference;
	
	//login state 1 - logged, 0 - logged out
	int loginState = 1; 
	
	//facebook posting dialog
	public ProgressDialog facebookPostingDialog;
	
	/**
	 * call when create activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
       
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_facebook_layout);
		
		//initialize application
		application = (ReferenceMeApplication) FacebookShareActivity.this.getApplication();
		
		/*
		 * initialize
		 */
		backBUtton = (Button) findViewById(R.id.share_facebook_layout_back_button);
		loginButton = (Button) findViewById(R.id.share_facebook_layout_login_button);
		shareButton = (Button) findViewById(R.id.share_facebook_layout_share_button);
		cancelButton = (Button) findViewById(R.id.share_facebook_layout_cancel_button);
		referenceText = (EditText) findViewById(R.id.share_facebook_layout_reference_text);
		
		//set sharing text in reference text
		referenceText.setText(application.getShareText().
							  replace("<i>","").
							  replace("</i>", "").
							  replace("<br></br><br></br>", " +++ "));
		
		/*
		 * set listener
		 */
		backBUtton.setOnClickListener(FacebookShareActivity.this);
		loginButton.setOnClickListener(FacebookShareActivity.this);
		shareButton.setOnClickListener(FacebookShareActivity.this);
		cancelButton.setOnClickListener(FacebookShareActivity.this);
		
		//get facebook
		facebook = application.getFacebook();
		
		//initialize
		asyncFacebookRunner = new AsyncFacebookRunner(facebook);
		
		//validate facebook session - valid session
		if(validateFacebookSession()){
			
			//set background for login button to logout
	        loginButton.setBackgroundResource(R.drawable.facebook_logout_button_selector);
	        
	        //enable share button
			shareButton.setEnabled(true);
			//shareButton.setFocusableInTouchMode(true);
			shareButton.setClickable(true);
			shareButton.setFocusable(true);
			
			//enable reference text
			referenceText.setEnabled(true);
			//shareButton.setFocusableInTouchMode(true);
			shareButton.setClickable(true);
			shareButton.setFocusable(true);
	        
			//set login state
			loginState = 1;
			
		}
		
		//invalid session
		else{
			
			//disable share button
			shareButton.setEnabled(false);
			shareButton.setClickable(false);
			shareButton.setFocusable(false);
			
			//disable reference text
			referenceText.setEnabled(false);
			referenceText.setClickable(false);
			referenceText.setFocusable(false);
			
			//set login state
			loginState = 0;
			
		}
		
	}

	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
		
		//back button
		if(v==backBUtton){
			
			//finish
			finish();
			
		}
		
		//login button
		else if(v == loginButton){
			
			//currently logged
			if(loginState == 1){
			
				//display posting dialog
				facebookPostingDialog = ProgressDialog.show(FacebookShareActivity.this, "", "login out... ");
				
				//new thread to logout
				Thread thread = new Thread(new Runnable() {
					
					/*
					 * (non-Javadoc)
					 * @see java.lang.Runnable#run()
					 */
					@Override
					public void run() {
						
						//log out from facebook
						logoutFromFacebook();
						
					}
					
				});
				
				//start thread
				thread.start();
			
			}
			
			//currently logged out
			else{
				
				//login to facebook
				loginToFacebook();
				
			}
			
		}
		
		//share button
		else if(v == shareButton){
			
			//empty mail
			if(application.getShareText().equals("")){
				
				//display toast
				Toast.makeText(FacebookShareActivity.this, "No references available to share", Toast.LENGTH_SHORT ).show();
				
			}
			
			//have reference
			else{
				
				//display posting dialog
				//facebookPostingDialog = ProgressDialog.show(FacebookShareActivity.this, "", "posting on wall... ");
				
				//new thread to post status to wall
				/*Thread thread = new Thread(new Runnable() {
					
					
					 * (non-Javadoc)
					 * @see java.lang.Runnable#run()
					 
					@Override
					public void run() {
						
						//post to wall
						postToWall(application.getRefenceText().replace("<i>","").replace("</i>", ""));
						
					}
					
				});
				
				//start thread
				thread.start();*/
				
				//post to wall
				postToWall(application.getShareText().
						   replace("<i>","").
						   replace("</i>", "").
						   replace("<br></br><br></br>", " +++ "));
				
			}
			
		}
		
		//cancel
		else if(v==cancelButton){
			
			//finish
			finish();
			
		}
		
	}
	
	/**
	 * login to facebook
	 */
	public void loginToFacebook(){
		
		//validate session - invalid session
		if (!validateFacebookSession()) {
			
			System.out.println("invalid session");
			
			//authenticate in facebook
			facebook.authorize(this,
							   new String[] { "email", "publish_stream" },
							   new LoginDialogListener());
			
		}
		
	}
	
	/** 
	 * @author eranga
	 * login dialog listener
	 */
	class LoginDialogListener implements DialogListener {
		
		/**
		 * when complete
		 */
	    public void onComplete(Bundle values) {
	    	
	    	//Edit Preferences and update facebook acess_token
			SharedPreferences.Editor editor = shareadPreference.edit();
			
			//set values
			editor.putString("access_token",facebook.getAccessToken());
			editor.putLong("access_expires",facebook.getAccessExpires());
			
			//commit
			editor.commit();
	    	
			//set background for login button to logout
	        loginButton.setBackgroundResource(R.drawable.facebook_logout_button_selector);
			
			//enable share button
			shareButton.setEnabled(true);
			//shareButton.setFocusableInTouchMode(true);
			shareButton.setClickable(true);
			shareButton.setFocusable(true);
			
			//enable reference text
			referenceText.setEnabled(true);
			//referenceText.setFocusableInTouchMode(true);
			referenceText.setClickable(true);
			referenceText.setFocusable(true);
			
			//set login state
			loginState = 1;
			
			//display toast
			Toast.makeText(FacebookShareActivity.this, "You logged into FB successfully", Toast.LENGTH_SHORT ).show();
			
	    }
	    
	    /**
	     * facebook error
	     */
	    public void onFacebookError(FacebookError error) {
	    	
	    	//display toast
			Toast.makeText(FacebookShareActivity.this, "FB login error", Toast.LENGTH_SHORT ).show();
	    	
	    }
	    
	    /**
	     * authantication error
	     */
	    public void onError(DialogError error) {
	    
	    	//display toast
			Toast.makeText(FacebookShareActivity.this, "FB login error", Toast.LENGTH_SHORT ).show();
	    	
	    }
	    
	    /**
	     * cancel dialog
	     */
	    public void onCancel() {
	    	
	    	
	    }
	    
	}
	
	/** 
	 * @author eranga
	 * posting dialog listener
	 */
	class PostingDialogListener implements DialogListener {
		
		/**
		 * when complete
		 */
	    public void onComplete(Bundle values) {
	    	
	    	//display toast
			Toast.makeText(FacebookShareActivity.this, "References shared successfully", Toast.LENGTH_SHORT ).show();
			
			//finish activity
			finish();
			
	    }
	    
	    /**
	     * facebook error
	     */
	    public void onFacebookError(FacebookError error) {
	    	
	    	
	    }
	    
	    /**
	     * authantication error
	     */
	    public void onError(DialogError error) {
	    
	    	
	    }
	    
	    /**
	     * cancel dialog
	     */
	    public void onCancel() {
	    	
	    	
	    }
	    
	}
	
	/**
	 * log out from facebook
	 */
	public void logoutFromFacebook() {
		
		//login out
		//asyncFacebookRunner.logout(this, new LogoutListener());
		
		//state
		int state = 1;
		
		//handle network and other exceptions
		try {
			
			//logout from facebook
			facebook.logout(getApplicationContext());
			
			//set state
			state = 1;
			
		}
		
		//error
		catch (Exception e) {
			
			//set state
			state = -1;
			
			System.out.println("error logout");
			
		}

		//new message
		Message message = new Message();
		
		//set arg
		message.arg1 = state;
		
		//handle logout
		handlerLogout.sendMessage(message);
		
	}
	
	/**
	 * facebook logout handler
	 */
	private Handler handlerLogout = new Handler() {

		/**
		 * handle message
		 */
		@Override
		public void handleMessage(Message message) {
			
			//dismiss dialog
			facebookPostingDialog.dismiss();
			
			//successful post
			if (message.arg1 == 1) {
				
				//display toast
				Toast.makeText(FacebookShareActivity.this, "You logged out from FB ", Toast.LENGTH_SHORT ).show();
				
				//set background for logout button to login
		        loginButton.setBackgroundResource(R.drawable.facebook_login_button_selector);
				
				//disable share button
				shareButton.setEnabled(false);
				shareButton.setClickable(false);
				shareButton.setFocusable(false);
				
				//disable reference text
				referenceText.setEnabled(false);
				referenceText.setClickable(false);
				referenceText.setFocusable(false);
				
				facebook.setAccessToken(null);
				facebook.setAccessExpires(0);
				
				//Edit Preferences and update facebook acess_token
				SharedPreferences.Editor editor = shareadPreference.edit();
				
				//set values
				editor.putString("access_token",null);
				editor.putLong("access_expires",0);
				
				//commit
				editor.commit();
				
				//set login state
				loginState = 0;
				
				//finish activity
				//finish();
				
			}
			
			//logout fail
			if (message.arg1 == -1) {
				
				//display toast
				//Toast.makeText(FacebookShareActivity.this, "fail to post on wall", Toast.LENGTH_SHORT ).show();
				
			}
			
		}

	};
	
	/**
	 * @author eranga
	 * logout listener
	 */
	class LogoutListener implements RequestListener{

		@Override
		public void onComplete(String response, Object state) {

			//successfully logged out
			if (Boolean.parseBoolean(response) == true) {

				//set background for logout button to login
		        //loginButton.setBackgroundResource(R.drawable.facebook_login_button_selector);
				
				//disable share button
				//shareButton.setEnabled(false);
				//shareButton.setClickable(false);
				//shareButton.setFocusable(false);
				
				//disable reference text
				//referenceText.setEnabled(false);
				//referenceText.setClickable(false);
				//referenceText.setFocusable(false);
				
				//set login state
				loginState = 0;
				
				System.out.println("loged out");
				
			}
			
			else{
				
				System.out.println("not logged out");
				
			}
			
		}

		@Override
		public void onIOException(IOException e, Object state) {
			
		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			
		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			
		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			
		}
		
	}
	
	/**
	 * post status 
	 * @param message 
	 */
	public void postToWall(String postingMessage){
		
		//posting state
		int state = 1;
		
		//parameter
		Bundle parameters = new Bundle();
		
		//set up parameters
		parameters.putString("name", "Hooray, I just used the ReferenceME app and cited the following:");
		parameters.putString("caption", "It does all your Citations & Bibliography in a click");
        parameters.putString("description", postingMessage);
        parameters.putString("link", "http://m.facebook.com/apps/referenceme/");
        parameters.putString("action", "http://bit.ly/wQipcO");
                
        /*//handle errors
        try {
        	
        	//request
        	facebook.request("me");
        	
        	//get response
			String response = facebook.request("me/feed", parameters, "POST");
			
			//log
			Log.d("Tests", "got response: " + response);
			
			//response error
			if (response == null ||
				response.equals("") ||
		        response.equals("false")) {
				
				//set state
				state = -1;
				
			}
			
			//successfully post
			else {
				
				//set state
				state = 1;
				
			}
			
		//error	
		} catch (Exception e) {
			
			//set state
			state = -1;
			
		}
		
		//new message
		Message message = new Message();
		
		//set arg
		message.arg1 = state;
		
		//handle post on wall
		handlerPostOnWall.sendMessage(message);*/
		
        //post to wall dislog
        facebook.dialog(this, "feed", parameters, new PostingDialogListener());
        
	}
	
	/**
	 * facebook post handler
	 */
	private Handler handlerPostOnWall = new Handler() {

		/**
		 * handle message
		 */
		@Override
		public void handleMessage(Message message) {
			
			//dismiss facebook posting dialog
			facebookPostingDialog.dismiss();
			
			//successful post
			if (message.arg1 == 1) {
				
				//display toast
				Toast.makeText(FacebookShareActivity.this, "References shared successfully", Toast.LENGTH_SHORT ).show();
				
				//finish activity
				finish();
				
			}
			
			//post fail
			if (message.arg1 == -1) {
				
				//display toast
				Toast.makeText(FacebookShareActivity.this, "Could not share references", Toast.LENGTH_SHORT ).show();
				
			}
			
		}

	};
	
	/**
	 * validate current facebook session
	 * @return
	 */
	public boolean validateFacebookSession(){
		
		//get sharead preference
		shareadPreference = getPreferences(MODE_PRIVATE);
		
		//access token
		String access_token = shareadPreference.getString("access_token", null);
		
		//expire
		long expires = shareadPreference.getLong("access_expires", 0);

		//empty access token
		if (access_token != null) {
			
			//set in facebook
			facebook.setAccessToken(access_token);
			
		}
		
		//have expire
		if (expires != 0) {
			
			//set in facebook
			facebook.setAccessExpires(expires);
			
		}
		
		//return
		return facebook.isSessionValid();
		
	}
	
	/**
	 * call when press back
	 */
	@Override
	public void onBackPressed() {
		
		//finish activity
		finish();
		
	}
	
}