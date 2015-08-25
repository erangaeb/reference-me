package appwolf.referenceme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.pojos.Reference;
import appwolf.referenceme.pojos.Project;

/**
 * @author eranga
 * activity correspond to new citation
 */
public class NewReferenceActivity extends Activity implements OnClickListener{		

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
	
	//current actiivty
	String currentActivity;
	
	/**
	 * call when create activity
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.new_reference_layout);
    
    	//initialize application
    	application = (ReferenceMeApplication) NewReferenceActivity.this.getApplication();
    	
    	//initialize 
    	currentActivity = application.getCurrentActivity();
    	
    	/*
    	 * initialize
    	 */
    	referencemeBar = (RelativeLayout) findViewById(R.id.new_reference_layout_referenceme_bar);
    	authorNameEditText = (EditText) findViewById(R.id.new_reference_layout_author_name_text);
    	bookTitleEditText = (EditText) findViewById(R.id.new_reference_layout_book_title_text);
    	journalTitleEditText = (EditText) findViewById(R.id.new_reference_layout_journal_title_edit_text);
    	publicationPlaceEditText = (EditText) findViewById(R.id.new_reference_layout_place_of_publication_text);
    	publicationYearEditText = (EditText) findViewById(R.id.new_reference_layout_year_of_publication_text);
    	publisherEditText = (EditText) findViewById(R.id.new_reference_layout_publisher_text);
    	editionEditText = (EditText) findViewById(R.id.new_reference_layout_book_edition_text);
    	pageNoEditText = (EditText) findViewById(R.id.new_reference_layout_page_no_text);
    	contributorEditText = (EditText) findViewById(R.id.new_reference_layout_contributor_text);
    	journalNoEditText = (EditText) findViewById(R.id.new_reference_layout_journal_no_text);
    	volumeNoEdtText = (EditText) findViewById(R.id.new_reference_layout_volume_no_text);
    	issueNoEditText = (EditText) findViewById(R.id.new_reference_layout_issue_no_text);
    	backButton = (Button) findViewById(R.id.new_reference_layout_back_button);
    	infoButton = (Button) findViewById(R.id.new_reference_layout_info_button);
    	doneButton = (Button) findViewById(R.id.new_reference_layout_done_button);
    	cancelButton = (Button) findViewById(R.id.new_reference_layout_cancel_button);
    	bookRadioButton = (RadioButton) findViewById(R.id.new_reference_layout_reference_type_book);
    	journalRadioButton = (RadioButton) findViewById(R.id.new_reference_layout_reference_type_journal);
    	titleTextView = (TextView) findViewById(R.id.new_reference_layout_title_text_view);
    	
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
    	
    	//manual entry
    	else {
    		
			//journal title edit text
			journalTitleEditText.setEnabled(false);
			journalTitleEditText.setClickable(false);
			journalTitleEditText.setFocusable(false);
			
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
    	
    	/*
    	 * set listeners
    	 */
    	referencemeBar.setOnClickListener(NewReferenceActivity.this);
    	backButton.setOnClickListener(NewReferenceActivity.this);
    	infoButton.setOnClickListener(NewReferenceActivity.this);
    	doneButton.setOnClickListener(NewReferenceActivity.this);
    	cancelButton.setOnClickListener(NewReferenceActivity.this);
    	bookRadioButton.setOnClickListener(NewReferenceActivity.this);
    	journalRadioButton.setOnClickListener(NewReferenceActivity.this);
    	
	}

	/**
	 * call when click on view
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		
		//referenceme bar
		if(v==referencemeBar){
			
			//display tip screen
			//startActivity(new Intent(NewReferenceActivity.this,TipEditReferenceActivity.class));
			
		}
		
		//info button
		else if(v==infoButton){
			
			//display tip screen
			startActivity(new Intent(NewReferenceActivity.this,TipEditReferenceActivity.class));
			
		}
		
		//back button
		else if(v==backButton){
			
			//reference me activity
			if(currentActivity.equals("reference_me")){
				
				//start reference me activity
				startActivity(new Intent(NewReferenceActivity.this,ReferenceMeActivity.class));
			}
			
			//reference list
			else if(currentActivity.equals("reference_list")){
				
				//start reference list activity
				startActivity(new Intent(NewReferenceActivity.this,ReferenceListActivity.class));
				
			}
			
			//new issn isbn
			else{
				
				//start new issn activity
				startActivity(new Intent(NewReferenceActivity.this,NewISSNISBNActivity.class));
				
			}
			
			//finish activity
			finish();
			
		}
		
		//done button
		else if(v==doneButton){
			
			//add reference
			addReference();
			
		}
		
		//cancel button
		else if(v==cancelButton){
			
			//reference me activity
			if(currentActivity.equals("reference_me")){
				
				//start reference me activity
				startActivity(new Intent(NewReferenceActivity.this,ReferenceMeActivity.class));
			}
			
			//reference list
			else if(currentActivity.equals("reference_list")){
				
				//start reference list activity
				startActivity(new Intent(NewReferenceActivity.this,ReferenceListActivity.class));
				
			}
			
			//new issn isbn
			else{
				
				//start new issn activity
				startActivity(new Intent(NewReferenceActivity.this,NewISSNISBNActivity.class));
				
			}
			
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
	 * add new reference to database
	 */
	public void addReference(){
		
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
		String author = authorNameEditText.getText().toString().trim();
		String bookTitle = bookTitleEditText.getText().toString().trim();
		String journalTitle = journalTitleEditText.getText().toString().trim();
		String publicationYear = publicationYearEditText.getText().toString().trim();
		String publicationPlace = publicationPlaceEditText.getText().toString().trim();
		String publisher = publisherEditText.getText().toString().trim();
		String edition = editionEditText.getText().toString().trim();
		String pageNo = pageNoEditText.getText().toString().trim();
		String contributon = contributorEditText.getText().toString().trim();
		String journalNo = journalNoEditText.getText().toString().trim();
		String volumeNo = volumeNoEdtText.getText().toString().trim();
		String issueNo = issueNoEditText.getText().toString().trim();
		
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
			Toast.makeText(NewReferenceActivity.this, "Please enter " + type + " title", Toast.LENGTH_SHORT ).show();
			
		}
		
		//have required fields
		else{
        	
			//valid page no
			if(validatePageNo(pageNo)){
				
				//get current project
				Project currentProject = application.getCurrentProject();
				
				//create new reference object
				Reference reference = new Reference(0,
												 	currentProject.getId(),
												 	type, 
												 	currentProject.getDefaultStyle(), 
												 	author.replace(".", ""), 
												 	bookTitle.replace(".", ""), 
												 	journalTitle.replace(".", ""), 
												 	publicationYear.replace(".", ""), 
												 	publicationPlace.replace(".", ""), 
												 	publisher.replace(".", ""), 
												 	edition.replace(".", ""),
												 	contributon.replace(".", ""),
												 	journalNo.replace(".", ""),
												 	volumeNo.replace(".", ""),
												 	issueNo.replace(".", ""),
												 	pageNo.replace(".", ""));
				
				//set reference in application
				application.setCurrentReference(reference);
				
				//insert reference to database
				int state = application.getReferenceMeData().addCitation(reference);
				
				//successful insert
				if(state == 1){
					
					//display toast
					Toast.makeText(NewReferenceActivity.this, "Reference created successfully", Toast.LENGTH_SHORT ).show();
					
					//start new activity
					startActivity(new Intent(NewReferenceActivity.this,ReferenceListActivity.class));
					
					//finish current activity
					finish();
					
				}
				
				//insert fail;
				else{
					
					//display toast
					Toast.makeText(NewReferenceActivity.this, "Could not create reference", Toast.LENGTH_SHORT ).show();
					
				}
				
			}
			
			//invalid page no
			else{
				
				//display toast
				Toast.makeText(NewReferenceActivity.this, "Please enter page number or range as 1 or 1-2", Toast.LENGTH_SHORT ).show();
				
			}
			
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
		
		//reference me activity
		if(currentActivity.equals("reference_me")){
			
			//start reference me activity
			startActivity(new Intent(NewReferenceActivity.this,ReferenceMeActivity.class));
		}
		
		//reference list
		else if(currentActivity.equals("reference_list")){
			
			//start reference list activity
			startActivity(new Intent(NewReferenceActivity.this,ReferenceListActivity.class));
			
		}
		
		//new issn isbn
		else{
			
			//start new issn activity
			startActivity(new Intent(NewReferenceActivity.this,NewISSNISBNActivity.class));
			
		}
		
		//finish activity
		finish();
		
	}
	
}