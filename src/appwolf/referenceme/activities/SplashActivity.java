package appwolf.referenceme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import appwolf.referenceme.application.ReferenceMeApplication;

/**
 * @author eranga
 * splash screen of reference me
 */
public class SplashActivity extends Activity{
	
	//application
	ReferenceMeApplication application;
	
	/**
	 * Called when the activity is first created. 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.splash_layout);
    
    	//initialize application
    	application = (ReferenceMeApplication) SplashActivity.this.getApplication();
    	
    	//reference activity
    	final Intent referenceIntent=new Intent(this,ReferenceMeActivity.class);
    	
    	//message activity
    	final Intent messageIntent=new Intent(this,MessageActivity.class);
    	
    	//new thread
    	Thread timer = new Thread(){
    		
    		//run method of thread
    		public void run(){
    			
    			//sleep for while
    			try{
    				
    				sleep(2000);
    				
    			}catch(Exception e){
    				
    				System.out.println(e.toString());
    				
    			}finally{
    				
    				//first time application launch
    				if(application.getReferenceMeData().getInstalledState().equals("0")){
    					
    					//start message activity
    					startActivity(messageIntent);
    					
    					//set is installed state
    					application.getReferenceMeData().setIsInstalledState("1");
    					
    				}
    				
    				//already installed
    				else{
    				
    					//start reference me activity
    					startActivity(referenceIntent);
    					
    				}

    				//finish activity
    				finish();
    				
    			}
    			
    		}
    		
    	};
    	
		//start 
		timer.start();
						
    }

}