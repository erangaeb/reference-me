package appwolf.referenceme.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.pojos.Project;
import appwolf.referenceme.threads.PrismCaller;
import appwolf.referenceme.utils.BarcodeProcessor;

/**
 * @author eranga
 * reference me activity
 */
public class ReferenceMeActivity extends Activity implements OnClickListener{
	
	//application
	ReferenceMeApplication application;
	
	//my projects button
	Button myProjectsButton;
	
	//quick reference button
	Button quickReferenceButton;
	
	//tutorial button
	Button tutorialButton;
	
	//progress dialog
	public ProgressDialog progressDialog;
	
    /** 
     * Called when the activity is first created. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.referenceme_layout);
        
        //initialize application
        application = (ReferenceMeApplication) ReferenceMeActivity.this.getApplication();
        
        /*
         * initialize
         */
        myProjectsButton = (Button) findViewById(R.id.referenceme_layout_my_project_button);
        quickReferenceButton = (Button) findViewById(R.id.referenceme_layout_quick_reference_button);
        tutorialButton = (Button) findViewById(R.id.referenceme_layout_tutorial_button);
        
        /*
         * set listeners
         */
        myProjectsButton.setOnClickListener(ReferenceMeActivity.this);
        quickReferenceButton.setOnClickListener(ReferenceMeActivity.this);
        tutorialButton.setOnClickListener(ReferenceMeActivity.this);
        
    }

	/**
     * call when click on view
     * @param v
     */
	@Override
	public void onClick(View v) {

		//my project button
		if(v==myProjectsButton){
			
			//set style in application
			application.setStyleType("harvard");
			
			//start my project list activity
			startActivity(new Intent(ReferenceMeActivity.this,MyProjectsListActivity.class));
			
			//finish activity
			finish();
		
		}
	
		//quick reference button
		else if(v==quickReferenceButton){
			
			//set style in application
			application.setStyleType("harvard");
			
			//set current project
			application.setCurrentProject(new Project(1, "Quick Referenc", "harvard"));
			
			//set current reference to null
			application.setCurrentReference(null);
			
			//zxing scan activity
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "ONE_D_MODE");
			
			//start activity
			startActivityForResult(intent, 0);
			
		}																					
		
		//tutorial button
		else if(v==tutorialButton){														
			
			//start tip activity
			startActivity(new Intent(ReferenceMeActivity.this,TipFirstActivity.class));
			
			//finish activity
			finish();
			
		}
		
	}
	
	/**
	 * call when result came
	 * @param requestCode
	 * @param resultCode
	 * @param intent
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		//successful
		if (requestCode == 0) {
			
			//Handle successful scan
			if (resultCode == RESULT_OK) {
				
				//get scan result - use for api call
				String barcode = intent.getStringExtra("SCAN_RESULT");
				
				//get format (not using)
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	   
				//book
				if(barcode.substring(0, 3).equals("978")){
					
					//set reference type to book in application
					application.setReferenceType("book");
					
					//get isbn
					String isbn = new BarcodeProcessor().getISBN(barcode);
					
					//display dialog
					progressDialog = ProgressDialog.show(ReferenceMeActivity.this, "", "Retrieving book details ... ");
					
					//start thread for prism api call
					new PrismCaller(ReferenceMeActivity.this,null,null,barcode,isbn).execute();
					
				}
				
				//journal
				else if(barcode.substring(0, 3).equals("977")){
					
					//set reference type to journal in application
					application.setReferenceType("journal");
					
					//get issn
					String issn = new BarcodeProcessor().getISSN(barcode);
					
					//display dialog
					progressDialog = ProgressDialog.show(ReferenceMeActivity.this, "", "Retrieving journal details ... ");
					
					//start thread for prism api call
					new PrismCaller(ReferenceMeActivity.this,null,null,barcode,issn).execute();
					
				}
				
				//not a journal or book
				else{
					
					//display toast
					Toast.makeText(ReferenceMeActivity.this, "Incorrect ISSN or ISBN", Toast.LENGTH_SHORT ).show();
					
				}
				
			}

			//handle cancel
			else if (resultCode == RESULT_CANCELED) {
	    	  
				//do nothing
	    	  
			}
			
		}
	
	}
    
}