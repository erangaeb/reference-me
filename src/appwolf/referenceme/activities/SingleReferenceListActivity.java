package appwolf.referenceme.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.pojos.Reference;

/**
 * @author eranga
 * activity class relates to single reference
 */
public class SingleReferenceListActivity extends Activity implements OnClickListener{

	//application
	ReferenceMeApplication application;
	
	//back button
	Button backButton;
	
	//new/edit button
	Button newButton;
	
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
	SingleReferenceListAdapter adapter;
	
	/**
	 * call when create activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
       
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_reference_list_layout);
		
		//initialize application
		application = (ReferenceMeApplication) SingleReferenceListActivity.this.getApplication();
		
		/*
		 * initialize
		 */
		backButton = (Button) findViewById(R.id.single_reference_list_layout_back_button);
		newButton = (Button) findViewById(R.id.single_reference_list_layout_new_button);
		citationButton = (Button) findViewById(R.id.single_reference_list_layout_citation_button);
		bibliographyButton = (Button) findViewById(R.id.single_reference_list_layout_bibliography_button);
		harvardButton = (Button) findViewById(R.id.single_reference_list_layout_harvard_style_button);
		vancouverButton = (Button) findViewById(R.id.single_reference_list_layout_vancouver_style_button);
		oxfordButton = (Button) findViewById(R.id.single_reference_list_layout_oxford_style_button);
		chicagoButton = (Button) findViewById(R.id.single_reference_list_layout_chicago_style_button);
		mlaButton = (Button) findViewById(R.id.single_reference_list_layout_mla_style_button);
		mrhaButton = (Button) findViewById(R.id.single_reference_list_layout_mrha_style_button);
		
		/*
		 * set listeners
		 */
		backButton.setOnClickListener(SingleReferenceListActivity.this);
		newButton.setOnClickListener(SingleReferenceListActivity.this);
		citationButton.setOnClickListener(SingleReferenceListActivity.this);
		bibliographyButton.setOnClickListener(SingleReferenceListActivity.this);
		harvardButton.setOnClickListener(SingleReferenceListActivity.this);
		vancouverButton.setOnClickListener(SingleReferenceListActivity.this);
		oxfordButton.setOnClickListener(SingleReferenceListActivity.this);
		chicagoButton.setOnClickListener(SingleReferenceListActivity.this);
		mlaButton.setOnClickListener(SingleReferenceListActivity.this);
		mrhaButton.setOnClickListener(SingleReferenceListActivity.this);
		
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
			mlaButton.setSelected(false);
			
		}
		
		//set selected true to citation
		citationButton.setSelected(true);
		
		//set selected false to bibliography
		bibliographyButton.setSelected(false);
		
    	//initialize list view
    	referenceListView = (ListView) findViewById(R.id.single_reference_list_layout_list);
    	
    	//initialize reference list
    	referenceList = new ArrayList<Reference>();
    	
    	//get current citation
    	Reference citation = application.getCurrentReference();
    	
    	//add citation to list
    	referenceList.add(citation);
    	
    	//new adapter
    	adapter = new SingleReferenceListAdapter(SingleReferenceListActivity.this,SingleReferenceListActivity.this, referenceList, "citation");
    	
    	//set list adapter
    	referenceListView.setAdapter(adapter);
    	
	}

	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
		
		//back button
		if(v == backButton){
			
			//finish activity
			finish();
			
		}
		
		//new button
		else if(v==newButton){
			
			//start edit activity
			
		}
		
		
		//citation button
		else if(v==citationButton){
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new SingleReferenceListAdapter(SingleReferenceListActivity.this,SingleReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//bibliography button
		else if(v == bibliographyButton){
			
			//set selected true to bibliography
			bibliographyButton.setSelected(true);
			
			//set selected false to citation
			citationButton.setSelected(false);
			
			//create new adapter
			adapter = new SingleReferenceListAdapter(SingleReferenceListActivity.this,SingleReferenceListActivity.this, referenceList, "bibliography");
			
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
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new SingleReferenceListAdapter(SingleReferenceListActivity.this,SingleReferenceListActivity.this, referenceList, "citation");
			
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
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new SingleReferenceListAdapter(SingleReferenceListActivity.this,SingleReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
		//oxford button
		else if(v==oxfordButton){
			
			//set background selector of citation to footnotes
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
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new SingleReferenceListAdapter(SingleReferenceListActivity.this,SingleReferenceListActivity.this, referenceList, "citation");
			
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
			mlaButton.setSelected(false);
			mrhaButton.setSelected(false);
			
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
			mrhaButton.setSelected(false);
			
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
			mlaButton.setSelected(false);
			
			//set selected true to citation
			citationButton.setSelected(true);
			
			//set selected false to bibliography
			bibliographyButton.setSelected(false);
			
			//create new adapter
			adapter = new SingleReferenceListAdapter(SingleReferenceListActivity.this,SingleReferenceListActivity.this, referenceList, "citation");
			
			//set list adapter
	    	referenceListView.setAdapter(adapter);
			
		}
		
	}
	
}