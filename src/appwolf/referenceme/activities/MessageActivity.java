package appwolf.referenceme.activities;			
							
import android.app.Activity;						
import android.content.Intent;							
import android.content.SharedPreferences;		
import android.os.Bundle;				
import android.view.View;				
import android.view.View.OnClickListener;			
import android.widget.Button;				
import android.widget.Toast;
import appwolf.referenceme.application.ReferenceMeApplication;

import com.facebook.android.*;
import com.facebook.android.Facebook.*;

/**
 * @author eranga
 * initial message activity
 */
public class MessageActivity extends Activity implements OnClickListener{																																					

	//application
	ReferenceMeApplication application;
	
	//skip button
	Button skipButton;

	//fb signin button
	Button fbSigninButton;
	
	//sharead preference
	private SharedPreferences shareadPreference;
	
	//facebook
	Facebook facebook;
	
	/** 
	 * Called when the activity is first created.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.message_layout);
    
    	//initialize application
    	application = (ReferenceMeApplication) getApplication();
    	
    	//get facebook
    	facebook = application.getFacebook();
    	
    	//initialize button
    	skipButton = (Button) findViewById(R.id.message_layout_skip_button);
    	fbSigninButton = (Button) findViewById(R.id.message_layout_fb_signin_button);
    	
    	//set on click listener
    	skipButton.setOnClickListener(MessageActivity.this);
    	fbSigninButton.setOnClickListener(MessageActivity.this);
    	
    }
	
	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
		
		//close button
		if(v==skipButton){
			
			//start reference me activity
			startActivity(new Intent(MessageActivity.this,ReferenceMeActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//sign in to facebook
		else if(v == fbSigninButton){
			
			//login to facebook
			loginToFacebook();
			
		}
		
	}
	
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
	 * login to facebook
	 */
	public void loginToFacebook(){
		
		//validate session - invalid session
		if (!validateFacebookSession()) {
			
			//authenticate in facebook
			facebook.authorize(this,
							   new String[] { "email", "publish_stream" },
							   new LoginDialogListener());
			
		}
		
		//have valid session
		else{
			
			//start reference me activity
			startActivity(new Intent(MessageActivity.this,ReferenceMeActivity.class));
			
			//finish activity
			finish();
			
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
			
			//display toast
			Toast.makeText(MessageActivity.this, "You logged into FB successfully", Toast.LENGTH_SHORT ).show();
			
			//start reference me activity
			startActivity(new Intent(MessageActivity.this,ReferenceMeActivity.class));
			
			//finish activity
			finish();
			
	    }
	    
	    /**
	     * facebook error
	     */
	    public void onFacebookError(FacebookError error) {
	    	
	    	//error
	    	Toast.makeText(MessageActivity.this, "FB login error", Toast.LENGTH_SHORT ).show();
	    	
	    }
	    
	    /**
	     * authantication error
	     */
	    public void onError(DialogError error) {
	    
	    	//error
	    	Toast.makeText(MessageActivity.this, "FB login error", Toast.LENGTH_SHORT ).show();
	    	
	    }
	    
	    /**
	     * cancel dialog
	     */
	    public void onCancel() {
	    	
	    	
	    }
	    
	}
	
}