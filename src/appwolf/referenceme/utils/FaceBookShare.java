package appwolf.referenceme.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import appwolf.referenceme.activities.ReferenceListActivity;
import appwolf.referenceme.activities.FacebookShareActivity;
import appwolf.referenceme.application.ReferenceMeApplication;

import com.facebook.android.*;
import com.facebook.android.Facebook.*;

/**
 * @author eranga
 * correspond to operation with facebook
 */
public class FaceBookShare {

	//app id
	private static final String APP_ID = "343287469054819";
	
	//facebook permission
	private static final String[] PERMISSIONS = new String[] {"publish_stream"};

	//access token
	private static final String TOKEN = "access_token";
	
	//edxpire
    private static final String EXPIRES = "expires_in";
    
    //key
    private static final String KEY = "facebook-credentials";
    
    //facebook share activity
    FacebookShareActivity facebookShareActivity;
    
    //facebook
    private Facebook facebook;
    
    //response
    String response;
    
    /**
     * constructor
     * @param activity
     */
    public FaceBookShare(FacebookShareActivity activity){
    	
    	//set
    	this.facebookShareActivity = activity;
    	
    	//initialize
    	response = "reference posted to your facebook wall!";
    	
    }
    
    public boolean saveCredentials(Facebook facebook) {
    	
    	//get editor
    	Editor editor = facebookShareActivity.getApplicationContext().
    										  getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
    	
    	//set editor parameters
    	editor.putString(TOKEN, facebook.getAccessToken());
    	editor.putLong(EXPIRES, facebook.getAccessExpires());
    	
    	//commit
    	return editor.commit();
    	
	}

    /**
     * restore facebook credentials
     * @param facebook
     * @return validity
     */
	public boolean restoreCredentials(Facebook facebook) {
		
		//get shared preference
    	SharedPreferences sharedPreferences = facebookShareActivity.getApplicationContext().
    																getSharedPreferences(KEY, Context.MODE_PRIVATE);
    	
    	//set in facebook
    	facebook.setAccessToken(sharedPreferences.getString(TOKEN, null));
    	facebook.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));
    	
    	//validity
    	return facebook.isSessionValid();
    	
	}
    
	/**
	 * initial configuration of facebook
	 */
	public void configureFacebook(){
		
		//creat facebook
		facebook = new Facebook(APP_ID);
		
		//login to facebook
		loginToFacebook();

	}
	
	/**
	 * share on facebook
	 * @param message
	 * @return response
	 */
	public String share(String message){
		
		//restore credential
		restoreCredentials(facebook);
		
		System.out.println(facebook.isSessionValid());
		
		//invalid session
		if (! facebook.isSessionValid()) {

			//set response
			response = "not logged to facebook";
			
		}
		
		//valid session
		else {
			
			//post to wall
			postToWall(message);
			
		}
		
		//return 
		return response;
		
	}

	/**
	 * login
	 */
	public void loginToFacebook(){
		
		//authorize facebook
		facebook.authorize(facebookShareActivity, 
						   PERMISSIONS, 
						   Facebook.FORCE_DIALOG_AUTH, 
						   new LoginDialogListener());
		
	}

	/**
	 * post to wall
	 * @param message
	 */
	public void postToWall(String message){
		
		//parameter
		Bundle parameters = new Bundle();
		
		//set up parameters
        parameters.putString("message", message);
        parameters.putString("description", "topic share");
                
        //handle errors
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
				
				//set response
				response = "Failed to post to wall!";
				
			}
			
			//successfully post
			else {
				
				//set response
				response = "reference posted to your facebook wall!";
				
			}
			
		//error	
		} catch (Exception e) {
			
			//set response
			response = "Failed to post to wall!";
			
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
	    	
	    	//save credntials
	    	saveCredentials(facebook);
	    	
	    	//set response
	    	response = "authanticate success";
	    	
	    }
	    
	    /**
	     * facebook error
	     */
	    public void onFacebookError(FacebookError error) {
	    	
	    	//set response
			response = "Authentication with Facebook failed!";
	    	
	    }
	    
	    /**
	     * authantication error
	     */
	    public void onError(DialogError error) {
	    
	    	//set response
			response = "Authentication with Facebook failed!";
	    	
	    }
	    
	    /**
	     * cancel dialog
	     */
	    public void onCancel() {
	    	
	    	//set response
			response = "Authentication with Facebook cancelled!";
	    	
	    }
	    
	}
	
}