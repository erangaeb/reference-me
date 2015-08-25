package appwolf.referenceme.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.threads.PrismCaller;
import appwolf.referenceme.utils.BarcodeProcessor;

/**
 * @author eranga
 * class correspond to handle new isbn and issn 
 */
public class NewISSNISBNActivity extends Activity implements OnClickListener{

	//application
	ReferenceMeApplication application;	
	
	//isbn issn text
	EditText isbnText;
	
	//back button
	Button backButton;
	
	//done button
	Button doneButton;
	
	//cancel button
	Button cancelButton;
	
	//book radio button
	RadioButton bookRadioButton;
	
	//journal radio button
	RadioButton journalRadioButton;
	
	//progress dialog
	public ProgressDialog progressDialog;
	
	/** 
     * Called when the activity is first created. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.new_isbn_issn_layout);
    
        //initialize application
    	application = (ReferenceMeApplication) NewISSNISBNActivity.this.getApplication();
    	
    	/*
    	 * initialize
    	 */
    	isbnText = (EditText) findViewById(R.id.new_isbn_issn_layout_isbn_text);
    	backButton = (Button) findViewById(R.id.new_isbn_issn_layout_back_button);
    	doneButton = (Button) findViewById(R.id.new_isbn_issn_layout_done_button);
    	cancelButton = (Button) findViewById(R.id.new_isbn_issn_layout_cancel_button);
    	bookRadioButton = (RadioButton) findViewById(R.id.new_isbn_issn_layout_reference_type_book);
    	journalRadioButton = (RadioButton) findViewById(R.id.new_isbn_issn_layout_reference_type_journal);
    	
    	/*
    	 * set listener
    	 */
    	backButton.setOnClickListener(NewISSNISBNActivity.this);
    	doneButton.setOnClickListener(NewISSNISBNActivity.this);
    	cancelButton.setOnClickListener(NewISSNISBNActivity.this);
    	
    }

    /**
     * call when click on view
     * @param v
     */
	@Override
	public void onClick(View v) {
		
		//back button
		if(v==backButton){
			
			//back to reference list activity
			startActivity(new Intent(NewISSNISBNActivity.this,ReferenceListActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//done button
		else if(v==doneButton){
			
			//process and do a api call
			processISBNISSN();
			
		}
		
		//cancel button
		else if(v==cancelButton){
			
			//back to reference list activity
			startActivity(new Intent(NewISSNISBNActivity.this,ReferenceListActivity.class));
			
			//finish activity
			finish();
			
		}
		
	}
	
	/**
	 * process isbn or issn entry 
	 */
	public void processISBNISSN(){
		
		/*
		 * get text field contents
		 */
		//type
		String type = "book";
		
		//checked journal button
		if(journalRadioButton.isChecked()){
			
			//journal type
			type = "journal";
			
		}
		
		//set type in application
		application.setReferenceType(type);
		
		//issn or isbn
		String apiParameter = isbnText.getText().toString().trim(); 
		
		//empty
		if(apiParameter.equals("")){
			
			//display toast
			Toast.makeText(NewISSNISBNActivity.this, "Please enter ISSN or ISBN first", Toast.LENGTH_SHORT ).show();
			
		}
		
		//not empty
		else{
			
			//book ,
			if(type.equals("book")){
				
				//isbn need to be 10 digit
				if(apiParameter.length()==10 || apiParameter.length()==13){
					
					//lenght 10
					if(apiParameter.length()==10){
						
						//display dialog
						progressDialog = ProgressDialog.show(NewISSNISBNActivity.this, "", "Retrieving book details ... ");
						
						//start thread to prism api call
						new PrismCaller(null,null,NewISSNISBNActivity.this,"test",apiParameter).execute();
						
					}
					
					//lenght 13 means barcode
					else{
						
						//barcode correspond to book
						if(apiParameter.substring(0, 3).equals("978")){
							
							//get isbn from barcode
							apiParameter = new BarcodeProcessor().getISBN(apiParameter);
							
							//display dialog
							progressDialog = ProgressDialog.show(NewISSNISBNActivity.this, "", "Retrieving book details ... ");
							
							//start thread to prism api call
							new PrismCaller(null,null,NewISSNISBNActivity.this,"test",apiParameter).execute();
							
						}
						
						//invalid barcode
						else{
					
							System.out.println(apiParameter.substring(0, 3));
							
							System.out.println("978");
							
							//display toast
							Toast.makeText(NewISSNISBNActivity.this, "Incorrect ISBN", Toast.LENGTH_SHORT ).show();
							
						}
						
					}
					
				}
				
				//invalid isbn length
				else{
					
					System.out.println("not 10 or 13");
					
					//display toast
					Toast.makeText(NewISSNISBNActivity.this, "Incorrect ISBN", Toast.LENGTH_SHORT ).show();
					
				}
				
			}
			
			//journal
			else{
				
				//issn need to be 8 digit or 13 digit
				if(apiParameter.length()==8 || apiParameter.length()==13){
					
					//lenght 8
					if(apiParameter.length()==8){
					
						//display dialog
						progressDialog = ProgressDialog.show(NewISSNISBNActivity.this, "", "Retriving journal details ... ");
						
						//start thread to prism api call
						new PrismCaller(null,null,NewISSNISBNActivity.this,"test",apiParameter).execute();
						
					}
					
					//length 13 means barcode
					else{
						
						//barcode correspond to journal
						if(apiParameter.substring(0, 3).equals("977")){
							
							//get issn from barcode
							apiParameter = new BarcodeProcessor().getISSN(apiParameter);
							
							//display dialog
							progressDialog = ProgressDialog.show(NewISSNISBNActivity.this, "", "Retriving journal details ... ");
							
							//start thread to prism api call
							new PrismCaller(null,null,NewISSNISBNActivity.this,"test",apiParameter).execute();
							
						}
						
						//invalid barcode
						else{
							
							//display toast
							Toast.makeText(NewISSNISBNActivity.this, "Incorrect ISSN", Toast.LENGTH_SHORT ).show();
							
						}
						
					}
					
				}
				
				//invalid issn lenght
				else{
					
					//display toast
					Toast.makeText(NewISSNISBNActivity.this, "Incorrect ISSN", Toast.LENGTH_SHORT ).show();
					
				}
				
			}
			
		}
		
	}
	
	/**
	 * call when press back
	 */
	@Override
	public void onBackPressed() {
		
		//back to reference list activity
		startActivity(new Intent(NewISSNISBNActivity.this,ReferenceListActivity.class));
		
		//finish activity
		finish();
		
	}
    
}