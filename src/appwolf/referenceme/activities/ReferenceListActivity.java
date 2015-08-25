package appwolf.referenceme.activities;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IInterface;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.View;																
import android.view.ViewStub;							
import android.view.Window;							
import android.view.View.OnClickListener;				
import android.view.ViewGroup.LayoutParams;			
import android.widget.AdapterView;					
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.pojos.Reference;
import appwolf.referenceme.pojos.Project;
import appwolf.referenceme.threads.PrismCaller;
import appwolf.referenceme.utils.BarcodeProcessor;
import appwolf.referenceme.utils.ReferenceGenerator;

/**
 * @author eranga
 * activity class correspond to citation list
 */
public class ReferenceListActivity extends Activity implements OnClickListener{

	//application
	ReferenceMeApplication application;
	
	//referenceme bar
	RelativeLayout referencemeBar;
	
	//back button
	Button backButton;
	
	//new/edit button
	Button newButton;
	
	//info button
	Button infoButton;
	
	//share button
	Button shareButton;
	
	//citation button
	Button citationButton;
	
	//bibliography button
	Button bibliographyButton;
	
	//harvard button
	Button harvardButton;
	
	//vancouver button
	Button vancouverButton;
	
	//oxford button
	Button oxfordButton;
	
	//chicago button
	Button chicagoButton;
	
	//chicago science 
	Button chicagoScienceButton;
	
	//mla button
	Button mlaButton;
	
	//mrha button
	Button mrhaButton;
	
	//reference list
	ArrayList<Reference> referenceList;
	
	//list view
	ListView referenceListView;
	
	//empty view
	ViewStub emptyView;
	
	//adapter
	ReferenceListAdapter adapter;
	
	//progress dialog
	public ProgressDialog progressDialog;
	
	//progress dialog facebook posting dialog
	public ProgressDialog facebookPostingDialog;
	
	/**
	 * call when create activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
       
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reference_list_layout);
		
		//initialize application
		application = (ReferenceMeApplication) ReferenceListActivity.this.getApplication();
		
		/*
		 * initialize
		 */
		referencemeBar = (RelativeLayout) findViewById(R.id.reference_list_layout_referenceme_bar);
		backButton = (Button) findViewById(R.id.reference_list_layout_back_button);
		infoButton = (Button) findViewById(R.id.reference_list_layout_info_button);
		newButton = (Button) findViewById(R.id.reference_list_layout_new_button);
		shareButton = (Button) findViewById(R.id.reference_list_layout_share_button);
		citationButton = (Button) findViewById(R.id.reference_list_layout_citation_button);
		bibliographyButton = (Button) findViewById(R.id.reference_list_layout_bibliography_button);
		harvardButton = (Button) findViewById(R.id.reference_list_layout_harvard_style_button);
		vancouverButton = (Button) findViewById(R.id.reference_list_layout_vancouver_style_button);
		oxfordButton = (Button) findViewById(R.id.reference_list_layout_oxford_style_button);
		chicagoButton = (Button) findViewById(R.id.reference_list_layout_chicago_style_button);
		chicagoScienceButton = (Button) findViewById(R.id.reference_list_layout_chicago_science_style_button); 
		mlaButton = (Button) findViewById(R.id.reference_list_layout_mla_style_button);
		mrhaButton = (Button) findViewById(R.id.reference_list_layout_mrha_style_button);
		
		/*
		 * set listeners
		 */
		referencemeBar.setOnClickListener(ReferenceListActivity.this);
		backButton.setOnClickListener(ReferenceListActivity.this);
		infoButton.setOnClickListener(ReferenceListActivity.this);
		newButton.setOnClickListener(ReferenceListActivity.this);
		shareButton.setOnClickListener(ReferenceListActivity.this);
		citationButton.setOnClickListener(ReferenceListActivity.this);
		bibliographyButton.setOnClickListener(ReferenceListActivity.this);
		harvardButton.setOnClickListener(ReferenceListActivity.this);
		vancouverButton.setOnClickListener(ReferenceListActivity.this);
		oxfordButton.setOnClickListener(ReferenceListActivity.this);
		chicagoButton.setOnClickListener(ReferenceListActivity.this);
		chicagoScienceButton.setOnClickListener(ReferenceListActivity.this);
		mlaButton.setOnClickListener(ReferenceListActivity.this);
		mrhaButton.setOnClickListener(ReferenceListActivity.this);
		
		//set selected true to citation
		citationButton.setSelected(true);
		
		//set selected false to bibliography
		bibliographyButton.setSelected(false);
		
		//set reference medium to citataion
		application.setReferenceMedium("citation");
		
		/*
		 * set up style button
		 */
		//get style
		String style = application.getStyleType();
		
		//harvard
		if(style.equals("harvard")){
			
			//select harvard button
			harvardButton.setSelected(true);
			
			/*
			 * un selct other buttons
			 */
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
		}
		
		//vancouver
		else if(style.equals("vancouver")){
			
			//select vancouver button
			vancouverButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
		}
		
		//oxford
		else if(style.equals("oxford")){
			
			//select oxford button
			oxfordButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
		}
		
		//chicago
		else if(style.equals("chicago")){
			
			//select chicago button
			chicagoButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
		}
		
		//chicago science
		else if(style.equals("chicago_science")){
			
			//select chicago button
			chicagoScienceButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
		}
		
		//mla
		else if(style.equals("mla")){
			
			//select mla button
			mlaButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mrhaButton.setSelected(false);
			
		}
		
		//mrha
		else if(style.equals("mrha")){
			
			//select oxford button
			mrhaButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
										
		}
		
    	//initialize list view
    	referenceListView = (ListView) findViewById(R.id.reference_list_layout_list);
		
    	//get current project from application
    	Project project = application.getCurrentProject();
    	
    	//initialize reference list
    	referenceList = new ArrayList<Reference>();
    	
    	//get matching reference list from database
    	referenceList = application.getReferenceMeData().getProjectreferences(project);
    	
    	//new adapter
    	adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
    	
    	//set list adapter
    	referenceListView.setAdapter(adapter);
    	
    	//set click listener
    	referenceListView.setOnItemClickListener(new OnItemClickListener(){
    		
    		/**
    		 * click on view
    		 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long duration){
				
				//get selected reference
				Reference reference = (Reference) adapter.getItem(position);
				
				//set selected reference in application
				application.setCurrentReference(reference);
				
				//get reference medium
				String refenenceMedium =  application.getReferenceMedium();
				
				//get reference
				String styleType = application.getStyleType();
				
				//reference text
				String singleRefenceText = new ReferenceGenerator().getReferenceText(reference, refenenceMedium, styleType);

				//set single reference text in application
				application.setSingleRefenceText(singleRefenceText);

				//set single reference text as share text in application
				application.setShareText(singleRefenceText);
				
				//display edit reference activity
				startActivity(new Intent(ReferenceListActivity.this,EditReferenceActivity.class));
				
				//finish
				finish();
				
			}
			
		});
    	
    	//set long press listener
    	referenceListView.setOnItemLongClickListener(new OnItemLongClickListener() {

    		/**
    		 * call in long press
    		 */
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v,int position, long id) {

				//get corresponding citation
				Reference reference= (Reference)adapter.getItem(position);
			
				//display information dialog
				displayInformationDialog("Are you sure you want to delete this reference ? ", reference);
					
				//return
				return true;
				
			}
    		
		});
    	
	}

	/**
	 * call when resulr activity
	 *//*
	@Override
	protected void onResume() {

		super.onResume();
		
		//get current project from application
    	Project project = application.getCurrentProject();
		
		//get matching reference list from database
    	referenceList = application.getReferenceMeData().getProjectreferences(project);
    	
    	//new adapter
    	adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
    	
    	//set list adapter
    	referenceListView.setAdapter(adapter);
		
	}*/


	/**
	 * display information dialog
	 * @param message - message text
	 * @param citation - corresponding project
	 */
	public void displayInformationDialog(String message,final Reference citation){
		
		//initiate dialog
        final Dialog dialog = new Dialog(ReferenceListActivity.this);
        
        //set layout for dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.information_message_dialog_layout);
        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
		
        //message text view
        TextView messageTextView = (TextView) dialog.findViewById(R.id.information_message_dialog_layout_message_text);
        
        //set text
        messageTextView.setText(message);
        
        //set yes button
        Button yesButton = (Button) dialog.findViewById(R.id.information_message_dialog_layout_yes_button);
        
        //on click listener
        yesButton.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
        
            	//cancel dialog
				dialog.cancel();

				//delete project from database
				application.getReferenceMeData().deleteReference(citation);

				//remove citation from list
				referenceList.remove(citation);
				
				//reset adapter
				referenceListView.setAdapter(adapter);
				
            }
            
        });
        
        //set no button
        Button noButton = (Button) dialog.findViewById(R.id.information_message_dialog_layout_no_button);
        
        //on click listener
        noButton.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
        
            	//cancel dialog
        		dialog.cancel();
                		
            }
            
        });
        
        //show dialog
        dialog.show();
		
	}
	
	/**
	 * display new reference option dialog
	 */
	public void displayNewReferenceOptionDialog(){
		
		//initiate dialog
        final Dialog dialog = new Dialog(ReferenceListActivity.this);
        
        //set layout for dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.new_reference_option_dialog_layout);
        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        
        //back button
        Button backButton = (Button)dialog.findViewById(R.id.new_reference_option_dialog_layout_back_button);
        
        //scan barcode button
        Button scanButton = (Button)dialog.findViewById(R.id.new_reference_option_dialog_layout_scan_button);
        
        //isbn button
        Button isbnButton = (Button)dialog.findViewById(R.id.new_reference_option_dialog_layout_isbn_button);
        
        //manual entry button
        Button manualEntryButton = (Button)dialog.findViewById(R.id.new_reference_option_dialog_layout_manual_entry_button);
		
        //back button on click listener
        backButton.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
        
            	//cancel dialog
        		dialog.cancel();
        	
            }
            
        });
        
        //scan button on click listener
        scanButton.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
        
            	//zxing scan activity
    			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    			intent.putExtra("SCAN_MODE", "ONE_D_MODE");
    			
    			//start activity
    			startActivityForResult(intent, 0);
        
    			//set current citation in application to null
            	application.setCurrentReference(null);
    			
    			//set style type (style type - current project default style)
    			//application.setStyleType("harvard");
            	application.setStyleType(application.getCurrentProject().getDefaultStyle());
    			
    			//set current reference to null
    			application.setCurrentReference(null);
    			
            	//cancel dialog
        		dialog.cancel();
        	
            }
            
        });
        
        //isbn button on click listener
        isbnButton.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
        
            	//set current citation in application to null
            	application.setCurrentReference(null);
            	
            	//set style type (style type - current project default style)
            	application.setStyleType(application.getCurrentProject().getDefaultStyle());
            	
            	//start new activity
            	startActivity(new Intent(ReferenceListActivity.this,NewISSNISBNActivity.class));
            	//displayNewISBNISSNDialog();
            	
            	//finish activity
            	finish();
            	
            	//cancel dialog
        		dialog.cancel();
        	
            }
            
        });
        
        //manual entry on click listener
        manualEntryButton.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
        
            	//set current citation in application to null
            	application.setCurrentReference(null);
            	
            	//set style type (style type - current project default style)
            	application.setStyleType(application.getCurrentProject().getDefaultStyle());
            	
            	//display new reference activity
            	startActivity(new Intent(ReferenceListActivity.this,NewReferenceActivity.class));
            	
            	//set current activity in application
            	application.setCurrentActivity("reference_list");
            	
            	//finish
            	finish();
            	
            	//cancel dialog
        		dialog.cancel();
        	
            }
            
        });
        
        //show dialog
        dialog.show();
        
	}
	
	public void displayNewISBNISSNDialog(){
		
		//initiate dialog
        final Dialog dialog = new Dialog(ReferenceListActivity.this);
        
        //set layout for dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.new_isbn_issn_dialog_layout);
        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        
        //show dialog
        dialog.show();
        
	}
	
	/**
	 * display share option dialog
	 */
	public void displayShareOptionDialog(){
		
		//initiate dialog
        final Dialog dialog = new Dialog(ReferenceListActivity.this);
        
        //set layout for dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.share_option_dialog_layout);
        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        
        //back button
        Button backButton = (Button)dialog.findViewById(R.id.share_option_dialog_layout_back_button);
		
        //facebook button
        Button facebookButton = (Button)dialog.findViewById(R.id.share_option_dialog_layout_facebook_button);
        
        //email button
        Button emailButton = (Button)dialog.findViewById(R.id.share_option_dialog_layout_email_button);
        
        //back button on click listener
        backButton.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
        
            	//cancel dialog
        		dialog.cancel();
        	
            }
            
        });
        
        //facebook button on click listener
        facebookButton.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
        
        		//display facebook share activity
				startActivity(new Intent(ReferenceListActivity.this,FacebookShareActivity.class));
				
				//cancel dialog
        		dialog.cancel();
				
            }
            
        });
        
        //email button on click listener
        emailButton.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
        
            	//share with email
            	shareByEmail();
            	
            	//cancel dialog
        		dialog.cancel();
        	
            }
            
        });
        
        //show dialog
        dialog.show();
        
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
				
				//get format
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	         
				System.out.println(barcode);
	   
				//book
				if(barcode.substring(0, 3).equals("978")){
					
					//set reference type to book in application
					application.setReferenceType("book");
					
					//get isbn
					String isbn = new BarcodeProcessor().getISBN(barcode);
					
					//display dialog
					progressDialog = ProgressDialog.show(ReferenceListActivity.this, "", "Retrieving book details ... ");
					
					//start thread for prism api call
					new PrismCaller(null,ReferenceListActivity.this,null,barcode,isbn).execute();
					
				}
				
				//journal
				else if(barcode.substring(0, 3).equals("977")){
					
					//set reference type to journal in application
					application.setReferenceType("journal");
					
					//get issn
					String issn = new BarcodeProcessor().getISSN(barcode);
					
					//display dialog
					progressDialog = ProgressDialog.show(ReferenceListActivity.this, "", "Retrieving journal details ... ");
					
					//api call for prism
					new PrismCaller(null,ReferenceListActivity.this,null,barcode,issn).execute();
					
				}
				
				//not a journal or book
				else{
					
					//display toast
					Toast.makeText(ReferenceListActivity.this, "Incorrect ISSN or ISBN", Toast.LENGTH_SHORT ).show();
					
				}
				
			}
			
			//handle cancel
			else if (resultCode == RESULT_CANCELED) {
	    	  
				//do nothing
	    	  
			}
			
		}
		   		
	}
	
	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
		
		//referenceme bar
		if(v==referencemeBar){
			
			//display tip screen
			//startActivity(new Intent(ReferenceListActivity.this,TipReferenceListFirstActivity.class));
			
		}
		
		//back button
		else if(v == backButton){
			
			//start project list activity
			startActivity(new Intent(ReferenceListActivity.this,MyProjectsListActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//info button
		else if(v==infoButton){
			
			//display tip screen
			startActivity(new Intent(ReferenceListActivity.this,TipReferenceListFirstActivity.class));
			
		}
		
		//new button
		else if(v==newButton){
			
			//display reference option dialog
			displayNewReferenceOptionDialog();
			
		}
		
		//share button
		else if(v==shareButton){
			
			//have references
			if(referenceList.size()>0){
			
				//set all reference text as share text in application
				application.setShareText(application.getAllReferenceText());
				
				//display share option dialog
				displayShareOptionDialog();
				
				//share with email
            	//shareByEmail();
			
			}
			
			//no references
			else{
				
				//display toast
				Toast.makeText(ReferenceListActivity.this, "No references available to share", Toast.LENGTH_SHORT ).show();
				
			}
			
		}
		
		//citation button
		else if(v==citationButton){
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//set medium to citation
			application.setReferenceMedium("citation");
			
			//create new adapter
			adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//bibliography button
		else if(v == bibliographyButton){
			
			//set selected true to bibliography
			bibliographyButton.setSelected(true);
			
			//set selected false to citation
			citationButton.setSelected(false);
			
			//set medium to reference
			application.setReferenceMedium("bibliography");
			
			//create new adapter
			adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "bibliography");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//harvard button
		else if(v==harvardButton){
			
			//set background selector of citation
			citationButton.setBackgroundResource(R.drawable.citation_button_selector);
			
			//set style in application
			application.setStyleType("harvard");
			
			//select harvard button
			harvardButton.setSelected(true);
			
			/*
			 * un selct other buttons
			 */
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//vancouver button
		else if(v==vancouverButton){
			
			//set background selector of citation
			citationButton.setBackgroundResource(R.drawable.citation_button_selector);
			
			//set style in application
			application.setStyleType("vancouver");
			
			//select vancouver button
			vancouverButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//oxford button
		else if(v==oxfordButton){
			
			//set background for citation button
	        citationButton.setBackgroundResource(R.drawable.footnote_button_selctor);
			
			//set style in application
			application.setStyleType("oxford");
			
			//select oxford button
			oxfordButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//chicago button
		else if(v==chicagoButton){
			
			//set background selector of citation
			citationButton.setBackgroundResource(R.drawable.citation_button_selector);
			
			//set style in application
			application.setStyleType("chicago");
			
			//select chicago button
			chicagoButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//chicago science button
		else if(v==chicagoScienceButton){
			
			//set background selector of citation
			citationButton.setBackgroundResource(R.drawable.citation_button_selector);
			
			//set style in application
			application.setStyleType("chicago_science");
			
			//select chicago button
			chicagoScienceButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//mla button
		else if(v==mlaButton){
			
			//set background selector of citation
			citationButton.setBackgroundResource(R.drawable.citation_button_selector);
			
			//set style in application
			application.setStyleType("mla");
			
			//select mla button
			mlaButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mrhaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//mrha button
		else if(v==mrhaButton){
			
			//set background selector of citation
			citationButton.setBackgroundResource(R.drawable.citation_button_selector);
			
			//set style in application
			application.setStyleType("mrha");
			
			//select oxford button
			mrhaButton.setSelected(true);
			
			/*
			 * un select other buttons
			 */
			harvardButton.setSelected(false);
			vancouverButton.setSelected(false);
			oxfordButton.setSelected(false);
			chicagoButton.setSelected(false);
			chicagoScienceButton.setSelected(false);
			mlaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new ReferenceListAdapter(ReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
	}
	
	/**
	 * share references with email
	 */
	public void shareByEmail(){
		
		//new mail intent
		Intent intent = new Intent(Intent.ACTION_SEND);
		
		System.out.println(application.getAllReferenceText());
		
		//mail to
	    String[] tos = {"eranga@appwolf.com"};
	    
	    //add to intent
	    //intent.putExtra(Intent.EXTRA_EMAIL, tos);
	    
	    //subject
	    intent.putExtra(Intent.EXTRA_SUBJECT, "Reference Me");
	    
	    //mailing text is share text
	    String emailText = application.getShareText();
	    
	    //string builder to hold html mail text
	    StringBuilder htmlContent = new StringBuilder();
	    
	    //header url
	    String weburl =  "<a href=\"http://www.awin1.com/cread.php?s=275049&v=3787&q=130624&r=134456\"/>";
	    	
	    //image url
	    //String imageUrl = "<img src=\"http://www.awin1.com/cshow.php?s=275049&v=3787&q=130624&r=134456\" border=\"0\"/>";	
	    
	    String imageUrl = "<img src=\"data:image/gif;base64,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%3D\"/>";
	    
	    System.out.println(weburl+imageUrl);
	    
	    //set up html content
	    htmlContent.append("<p>").
	    			//append(weburl).
	    			//append(imageUrl).
	    			append("Hooray, I just used the ReferenceME app and cited the following:").
	    			append("<br></br><br></br>").
	    			append(emailText).
	    			append("<br></br><br></br>").
	    			append("</p>");
	    		
	    
	    /*Spanned html = Html.fromHtml("<html><body>h<b>ell</b>o<img src='http://www.pp.rhul.ac.uk/twiki/pub/TWiki/GnuPlotPlugin/RosenbrockFunctionSample.png'>world</body></html>",
	    				    new ImageGetter() {
								
	    						InputStream s;
	    	
								public Drawable getDrawable(String url) {
		
									try{
										 
										s = (InputStream) (new URL(url)).getContent();
									 
									} catch (Exception e) {

									   
									}
									
									Drawable d = Drawable.createFromStream(s, null);
									d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()); 
									
									return d;

								}
								
							},null);*/

	    
	    //mail content
	    intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(htmlContent.toString()));
	    //intent.putExtra(Intent.EXTRA_TEXT, html);
	    
	    //mail text type
	    intent.setType("text/html");
	
	    //start activity
	    startActivity(intent);
		
	}

	/**
	 * call when press back
	 */
	@Override
	public void onBackPressed() {
		
		//start project list activity
		startActivity(new Intent(ReferenceListActivity.this,MyProjectsListActivity.class));
		
		//finish activity
		finish();
		
	}
	
}
