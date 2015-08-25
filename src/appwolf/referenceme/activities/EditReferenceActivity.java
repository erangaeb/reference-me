package appwolf.referenceme.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.pojos.Project;
import appwolf.referenceme.pojos.Reference;

/**
 * @author eranga
 * activity class relates to edit reference
 */
public class EditReferenceActivity extends Activity implements OnClickListener{

	//application
	ReferenceMeApplication application;			
	
	//referenceme bar
	RelativeLayout referencemeBar;
	
	//author name text	
	EditText authorNameEditText;		
		
	//title text	
	EditText bookTitleEditText;
	
	//journal title text	
	EditText journalTitleEditText;
		
	//publication year text	
	EditText publicationYearEditText;	
	
	//publication place text	
	EditText publicationPlaceEditText;
	
	//publisher text
	EditText publisherEditText;
	
	//edition text
	EditText editionEditText;
	
	//page numbers text
	EditText pageNoEditText;
	
	//contributor text
	EditText contributorEditText;
	
	//journal no text
	EditText journalNoEditText;
	
	//volume no
	EditText volumeNoEdtText;
	
	//issue no
	EditText issueNoEditText;
	
	//title text view
	TextView titleTextView;
	
	//back button
	Button backButton;
	
	//info button
	Button infoButton;
	
	//share button
	Button shareButton;
	
	//done button
	Button doneButton;
	
	//cancel button
	Button cancelButton;
	
	//book radio button
	RadioButton bookRadioButton;
	
	//journal radio button
	RadioButton journalRadioButton;
	
	//application citation object
	Reference currentReference;
	
	/**
	 * call when create activity
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);			
    	setContentView(R.layout.edit_reference_layout);			
    
    	//initialize application
    	application = (ReferenceMeApplication) EditReferenceActivity.this.getApplication();
    	
    	/*
    	 * initialize
    	 */
    	referencemeBar = (RelativeLayout) findViewById(R.id.edit_reference_layout_referenceme_bar);
    	authorNameEditText = (EditText) findViewById(R.id.edit_reference_layout_author_name_text);
    	bookTitleEditText = (EditText) findViewById(R.id.edit_reference_layout_book_title_text);
    	journalTitleEditText = (EditText) findViewById(R.id.edit_reference_layout_journal_title_edit_text);
    	publicationPlaceEditText = (EditText) findViewById(R.id.edit_reference_layout_place_of_publication_text);
    	publicationYearEditText = (EditText) findViewById(R.id.edit_reference_layout_year_of_publication_text);
    	publisherEditText = (EditText) findViewById(R.id.edit_reference_layout_publisher_text);
    	editionEditText = (EditText) findViewById(R.id.edit_reference_layout_book_edition_text);
    	pageNoEditText = (EditText) findViewById(R.id.edit_reference_layout_page_no_text);
    	contributorEditText = (EditText) findViewById(R.id.edit_reference_layout_contributor_text);
    	journalNoEditText = (EditText) findViewById(R.id.edit_reference_layout_journal_no_text);
    	volumeNoEdtText = (EditText) findViewById(R.id.edit_reference_layout_volume_no_text);
    	issueNoEditText = (EditText) findViewById(R.id.edit_reference_layout_issue_no_text);
    	backButton = (Button) findViewById(R.id.edit_reference_layout_back_button);
    	infoButton = (Button) findViewById(R.id.edit_reference_layout_info_button);
    	shareButton = (Button) findViewById(R.id.edit_reference_layout_share_button);
    	doneButton = (Button) findViewById(R.id.edit_reference_layout_done_button);
    	cancelButton = (Button) findViewById(R.id.edit_reference_layout_cancel_button);
    	bookRadioButton = (RadioButton) findViewById(R.id.edit_reference_layout_reference_type_book);
    	journalRadioButton = (RadioButton) findViewById(R.id.edit_reference_layout_reference_type_journal);
    	titleTextView = (TextView) findViewById(R.id.edit_reference_layout_title_text_view);
    	
    	//get reference
    	currentReference = application.getCurrentReference();
    	
    	//have citation
    	if(currentReference!=null){
    	
    		//get citation type
    		String referenceType = currentReference.getType();
    		
    		//book
    		if(referenceType.equals("book")){
    			
    			//check book
    			bookRadioButton.setChecked(true);
    			
    			//disable journal button
    			journalRadioButton.setEnabled(false);
    			
    			//set clickble false to journal button
    			journalRadioButton.setClickable(false);
    			
    			//disable journal title edit text
    			journalTitleEditText.setEnabled(false);
    			journalTitleEditText.setClickable(false);
    			journalTitleEditText.setFocusable(false);
    			
    			//enable contributor
    			contributorEditText.setEnabled(true);
    			contributorEditText.setFocusableInTouchMode(true);
    			contributorEditText.setClickable(true);
    			contributorEditText.setFocusable(true);
    			
    			//disable journal no text
    			journalNoEditText.setEnabled(false);
    			journalNoEditText.setClickable(false);
    			journalNoEditText.setFocusable(false);
    			
    			//disable volume no edit text
    			volumeNoEdtText.setEnabled(false);
    			volumeNoEdtText.setClickable(false);
    			volumeNoEdtText.setFocusable(false);
    			
    			//disable issue no edit text
    			issueNoEditText.setEnabled(false);
    			issueNoEditText.setClickable(false);
    			issueNoEditText.setFocusable(false);
    			
    			//change text of article title to book title
    			titleTextView.setText("Book Title");
    			
    		}
    		
    		//journal
    		else{
    			
    			//check journal
    			journalRadioButton.setChecked(true);
    			
    			//disable book button
    			bookRadioButton.setEnabled(false);
    			
    			//set clickble false to book button
    			bookRadioButton.setClickable(false);
    			
    			//enable journal title
    			journalTitleEditText.setEnabled(true);											
    			journalTitleEditText.setFocusableInTouchMode(true);						
    			journalTitleEditText.setClickable(true);								
    			journalTitleEditText.setFocusable(true);								
    			
    			//disable contributor text
    			contributorEditText.setEnabled(false);
    			contributorEditText.setClickable(false);
    			contributorEditText.setFocusable(false);
    			
    			//enable journal no
    			journalNoEditText.setEnabled(true);
    			journalNoEditText.setFocusableInTouchMode(true);
    			journalNoEditText.setClickable(true);
    			journalNoEditText.setFocusable(true);
    			
    			//enable volume no
    			volumeNoEdtText.setEnabled(true);
    			volumeNoEdtText.setFocusableInTouchMode(true);
    			volumeNoEdtText.setClickable(true);
    			volumeNoEdtText.setFocusable(true);
    			
    			//enable issue no
    			issueNoEditText.setEnabled(true);
    			issueNoEditText.setFocusableInTouchMode(true);
    			issueNoEditText.setClickable(true);
    			issueNoEditText.setFocusable(true);
    			
    			//change text of book title to article title
    			titleTextView.setText("Article Title");
    			
    		}
    		
	    	/*				
	    	 * set text to edit text fields
	    	 */
    		authorNameEditText.setText(currentReference.getAuthorName());
    		bookTitleEditText.setText(currentReference.getArticleTitle());
    		journalTitleEditText.setText(currentReference.getJournalTitle());
    		publicationPlaceEditText.setText(currentReference.getPublicationPlace());
    		publicationYearEditText.setText(currentReference.getPublicationYear());
    		publisherEditText.setText(currentReference.getPublisher());
    		editionEditText.setText(currentReference.getEdition());
    		pageNoEditText.setText(currentReference.getPageNo());
    		volumeNoEdtText.setText(currentReference.getVolumeNo());
    		issueNoEditText.setText(currentReference.getIssueNo());
    		
    	}
    	
    	/*
    	 * set listeners
    	 */
    	referencemeBar.setOnClickListener(EditReferenceActivity.this);
    	backButton.setOnClickListener(EditReferenceActivity.this);
    	infoButton.setOnClickListener(EditReferenceActivity.this);
    	shareButton.setOnClickListener(EditReferenceActivity.this);
    	doneButton.setOnClickListener(EditReferenceActivity.this);
    	cancelButton.setOnClickListener(EditReferenceActivity.this);
    	bookRadioButton.setOnClickListener(EditReferenceActivity.this);
    	journalRadioButton.setOnClickListener(EditReferenceActivity.this);
    	
	}
	
	/**
	 * edit reference  database
	 */
	public void editReference(){
		
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
		
		/*
		 * get texts
		 */
		String author = authorNameEditText.getText().toString();
		String bookTitle = bookTitleEditText.getText().toString();
		String journalTitle = journalTitleEditText.getText().toString();
		String publicationYear = publicationYearEditText.getText().toString();
		String publicationPlace = publicationPlaceEditText.getText().toString();
		String publisher = publisherEditText.getText().toString();
		String edition = editionEditText.getText().toString();
		String contributon = contributorEditText.getText().toString();
		String journalNo = journalNoEditText.getText().toString();
		String pageNo = pageNoEditText.getText().toString();
		String volumeNo = volumeNoEdtText.getText().toString();
		String issueNo = issueNoEditText.getText().toString();
		
		//keep track with emptyness of required fields
		boolean isEmpty = false;
		
		//book type
		if(type.equals("book")){
			
			//empty title
			if(bookTitle.equals("")){
				
				//empty
				isEmpty = true;
				
			}
			
		}
		
		//journal
		else{
			
			//empty title
			if(journalTitle.equals("")){
				
				//empty
				isEmpty = true;
				
			}
			
		}
		
		//empty required fields
		if(isEmpty == true){
		
			//display toast
			Toast.makeText(EditReferenceActivity.this, "Please enter " + type + " title", Toast.LENGTH_SHORT ).show();
			
		}
		
		//have required fields
		else{
			
			//valid page no
			if(validatePageNo(pageNo)){
			
				//get current project
				Project currentProject = application.getCurrentProject();
				
				//create new citation object
				Reference reference = new Reference(currentReference.getId(),
												 	currentReference.getProjectId(),
												 	type, 
												 	currentReference.getDefaultStyle(), 
												 	author, 
												 	bookTitle, 
												 	journalTitle, 
												 	publicationYear, 
												 	publicationPlace, 
												 	publisher, 
												 	edition,
												 	contributon,
												 	journalNo,
												 	volumeNo,
												 	issueNo,
												 	pageNo);
				
				//set reference in application
				application.setCurrentReference(reference);
				
				//edit reference in database
				int state = application.getReferenceMeData().editCitation(reference);
				
				//edit success
				if(state == 1){
					
					//display toast
					Toast.makeText(EditReferenceActivity.this, "Reference edited successfully", Toast.LENGTH_SHORT ).show();
					
					//start new activity
					startActivity(new Intent(EditReferenceActivity.this,ReferenceListActivity.class));
					
					//finish current activity
					finish();
					
				}
				
				//insert fail;
				else{
					
					//display toast
					Toast.makeText(EditReferenceActivity.this, "Could not edit reference", Toast.LENGTH_SHORT ).show();
					
				}
			
			}
			
			//invalid page no
			else{
				
				//display toast
				Toast.makeText(EditReferenceActivity.this, "Please enter page number or range as 1 or 1-2", Toast.LENGTH_SHORT ).show();
				
			}
				
		}
		
	}

	/**
	 * display share option dialog
	 */
	public void displayShareOptionDialog(){
		
		//initiate dialog
        final Dialog dialog = new Dialog(EditReferenceActivity.this);
        
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
				startActivity(new Intent(EditReferenceActivity.this,FacebookShareActivity.class));
		
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
	 * share references with email
	 */
	public void shareByEmail(){
		
		//new gmail intent
		Intent intent = new Intent(Intent.ACTION_SEND);
		
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
	    String weburl =  "<a href='http://www.awin1.com/cread.php?s=275049&v=3787&q=130624&r=134456'>";
	    	
	    //image url
	    String imageUrl = "<img src='http://www.awin1.com/cshow.php?s=275049&v=3787&q=130624&r=134456' border='0'></a>"; 	
	    
	    System.out.println(weburl+imageUrl); 
	    
	    //set up html content
	    htmlContent.append("<p>").
	    			append(weburl).
	    			append(imageUrl).
	    			append("Hooray, I just used the ReferenceME app and cited the following:").
	    			append("<br></br><br></br>").
	    			append(emailText).
	    			append("<br></br><br></br>").
	    			append("</p>");
	    
	    //mail content
	    intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(htmlContent.toString()));
	    
	    //mail text type
	    intent.setType("text/html");
	
	    //start activity
	    startActivity(intent);
		
	}
	
	/**
	 * call whenc click on view
	 */
	@Override
	public void onClick(View v) {

		//referenceme bar
		if(v==referencemeBar){
			
			//display tip screen
			//startActivity(new Intent(EditReferenceActivity.this,TipEditReferenceActivity.class));
			
		}
		
		//back button
		else if(v==backButton){
			
			//start new activity
			startActivity(new Intent(EditReferenceActivity.this,ReferenceListActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//info button
		else if(v==infoButton){
			
			//display tip screen
			startActivity(new Intent(EditReferenceActivity.this,TipEditReferenceActivity.class));
			
		}
		
		//share button
		else if(v==shareButton){
			
			//display hsare option dialog
			displayShareOptionDialog();
			
		}
		
		//done button
		else if(v==doneButton){
			
			//edit reference
			editReference();
			
		}
		
		//cancel button
		else if(v==cancelButton){
			
			//start new activity
			startActivity(new Intent(EditReferenceActivity.this,ReferenceListActivity.class));
			
			//finish activity
			finish();
	
		}
		
		//book radio button
		else if(v==bookRadioButton){
			
			//disable journal title edit text
			journalTitleEditText.setEnabled(false);
			journalTitleEditText.setClickable(false);
			journalTitleEditText.setFocusable(false);
			
			//enable contributor
			contributorEditText.setEnabled(true);
			contributorEditText.setFocusableInTouchMode(true);
			contributorEditText.setClickable(true);
			contributorEditText.setFocusable(true);
			
			//disable journal no text
			journalNoEditText.setEnabled(false);
			journalNoEditText.setClickable(false);
			journalNoEditText.setFocusable(false);
			
			//disable volume no edit text
			volumeNoEdtText.setEnabled(false);
			volumeNoEdtText.setClickable(false);
			volumeNoEdtText.setFocusable(false);
			
			//disable issue no edit text
			issueNoEditText.setEnabled(false);
			issueNoEditText.setClickable(false);
			issueNoEditText.setFocusable(false);
			
			//change text of article title to book title
			titleTextView.setText("Book Title");
			
		}
		
		//journal radio button
		else if(v==journalRadioButton){
			
			//enable journal title
			journalTitleEditText.setEnabled(true);
			journalTitleEditText.setFocusableInTouchMode(true);
			journalTitleEditText.setClickable(true);
			journalTitleEditText.setFocusable(true);
			
			//disable contributor text
			contributorEditText.setEnabled(false);
			contributorEditText.setClickable(false);
			contributorEditText.setFocusable(false);
			
			//enable journal no
			journalNoEditText.setEnabled(true);
			journalNoEditText.setFocusableInTouchMode(true);
			journalNoEditText.setClickable(true);
			journalNoEditText.setFocusable(true);
			
			//enable volume no
			volumeNoEdtText.setEnabled(true);
			volumeNoEdtText.setFocusableInTouchMode(true);
			volumeNoEdtText.setClickable(true);
			volumeNoEdtText.setFocusable(true);
			
			//enable issue no
			issueNoEditText.setEnabled(true);
			issueNoEditText.setFocusableInTouchMode(true);
			issueNoEditText.setClickable(true);
			issueNoEditText.setFocusable(true);
			
			//change text of book title to article title
			titleTextView.setText("Article Title");
			
		}
		
	}
	
	/**
	 * validate page no
	 * @param pageNo 
	 * @return validity
	 */
	public boolean validatePageNo(String pageNo){
		
		//validate
		boolean valid = true; 
		
		//empty page no
		if(pageNo.equals("")){
			
			//valid
			valid = true;
			
		}
		
		//non empty page no
		else{
		
			//contains '-'
			if(pageNo.contains("-")){
			
				/*
				 * split and get author names
				 */
				//to hold splited values
				String[] pageNoValues;
				
				//split by '-'
				pageNoValues = pageNo.trim().split("-");
				
				//length should be 2
				if(pageNoValues.length == 2){			
								
					//handle parsing errors
					try{												
									
						//parse									
						Integer.parseInt(pageNoValues[0].trim());
						Integer.parseInt(pageNoValues[1].trim());			
									
					}
					
					//parsing error
					catch(Exception e){
						
						//invalid
						valid = false;
						
					}
					
				}
				
				//invalid 
				else{
					
					//set valid
					valid = false;
					
				}
				
			}
			
			//no '-'
			else{
				
				//parse
				try{
					
					//parse page no
					Integer.parseInt(pageNo);
					
				}
				
				//parsing error
				catch(Exception e){
					
					//invalid
					valid = false;
					
				}
				
			}
			
		}
		
		//return
		return valid;
		
	}

	/**
	 * call when press back
	 */
	@Override
	public void onBackPressed() {
		
		//start new activity
		startActivity(new Intent(EditReferenceActivity.this,ReferenceListActivity.class));
		
		//finish activity
		finish();
		
	}
	
}