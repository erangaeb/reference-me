package appwolf.referenceme.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.pojos.Project;

/**
 * @author eranga
 * activity class correspond to new project
 */
public class NewProjectActivity extends Activity implements OnClickListener{

	//application
	ReferenceMeApplication application;
	
	//back button
	Button backButton;
	
	//done button
	Button doneButton;
	
	//cancel button
	Button cancelButton;
	
	//harvard radio button
	RadioButton harvardRadioButton;
	
	//vancouver radio button
	RadioButton vancouverRadioButton;
	
	//oxford radio button
	RadioButton oxfordRadioButton;
	
	//chicago radio button
	RadioButton chicagoRadioButton;
	
	//mla radio button
	RadioButton mlaRadioButton;
	
	//mrha radio button
	RadioButton mrhaRadioButton;
	
	//project name edit text view
	EditText projectNameEditText;
	
	//default style spinner
	Spinner defaultStyleSpinner;
	
	/**
	 * call when create activity
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.new_project_layout);
    	
    	//initialize application
    	application = (ReferenceMeApplication) NewProjectActivity.this.getApplication();
    							
    	/*
    	 * initialize
    	 */
    	backButton = (Button) findViewById(R.id.new_project_layout_back_button);
    	doneButton = (Button) findViewById(R.id.new_project_layout_done_button);
    	cancelButton = (Button) findViewById(R.id.new_project_layout_cancel_button);
    	/*harvardRadioButton = (RadioButton) findViewById(R.id.new_project_layout_style_harvard);
    	vancouverRadioButton = (RadioButton) findViewById(R.id.new_project_layout_style_vancouver);
    	oxfordRadioButton = (RadioButton) findViewById(R.id.new_project_layout_style_oxford);
    	chicagoRadioButton = (RadioButton) findViewById(R.id.new_project_layout_style_chicago);
    	mlaRadioButton = (RadioButton) findViewById(R.id.new_project_layout_style_mla);
    	mrhaRadioButton = (RadioButton) findViewById(R.id.new_project_layout_style_mrha);*/
    	projectNameEditText = (EditText) findViewById(R.id.new_project_layout_project_name_text);
    	defaultStyleSpinner = (Spinner) findViewById(R.id.new_project_layout_default_style_spinner);
    	
    	/*
    	 * set up spinner
    	 */
    	//new array adapter
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    														   				 R.array.default_style_array, 
    														   				 android.R.layout.simple_spinner_item);
    	
    	//drop down resource
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	//set spinner adapter
        defaultStyleSpinner.setAdapter(adapter);
    	
        /*
         * set default style according to last project style
         */
        //project list
    	ArrayList<Project> projectList = application.getReferenceMeData().getAllProjects();
        
    	//last project
    	Project lastProject = projectList.get(projectList.size()-1);
    	
        //last project style
        String lastProjectStyle = lastProject.getDefaultStyle();
        
        //get position in adapter
        int position = adapter.getPosition(lastProjectStyle);
        
        //set last project style in spinner
        defaultStyleSpinner.setSelection(position);
        
        //set item selecting listener
        defaultStyleSpinner.setOnItemSelectedListener(new DefaultStyleItemSelectedListener());
        
    	/*																				
    	 * set listeners																						
    	 */							
    	backButton.setOnClickListener(NewProjectActivity.this);
    	doneButton.setOnClickListener(NewProjectActivity.this);
    	cancelButton.setOnClickListener(NewProjectActivity.this);
    	
	}

	/**
	 * call when click on view
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		
		//back button
		if(v==backButton){
			
			//start my project list activity
			startActivity(new Intent(NewProjectActivity.this,MyProjectsListActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//done button
		else if(v==doneButton){
			
			//add project to database
			addProject();
			
		}
		
		//cancel button
		else if(v==cancelButton){
			
			//start my project list activity
			startActivity(new Intent(NewProjectActivity.this,MyProjectsListActivity.class));
			
			//finish activity
			finish();
			
		}
		
	}
	
	/**
	 * add new project to database
	 */
	public void addProject(){
		
		//get project name
		String projectName = projectNameEditText.getText().toString().trim();
		
		/*//get default style
		String defaultStyle = "harvard";
		
		//harvard check
		if(harvardRadioButton.isChecked()){
			
			//set
			defaultStyle = "harvard";
			
		}
		
		//vancouver check
		else if(vancouverRadioButton.isChecked()){
			
			//set
			defaultStyle = "vancouver";
			
		}
		
		//oxford check
		else if(oxfordRadioButton.isChecked()){
			
			//set
			defaultStyle = "oxford";
			
		}
		
		//chicago
		else if(chicagoRadioButton.isChecked()){
			
			//set
			defaultStyle = "chicago";
			
		}
		
		//mla check
		else if(mlaRadioButton.isChecked()){
			
			//set
			defaultStyle = "mla";
			
		}
		
		///mrha
		else if(mrhaRadioButton.isChecked()){
			
			//set
			defaultStyle = "mrha";
			
		} */
	
		//default style - get from spinner
		String defaultStyle = defaultStyleSpinner.getSelectedItem().toString().trim();
		
		//empty fields
		if(projectName.equals("")){
			
			//display toast
			Toast.makeText(NewProjectActivity.this, "Please enter project title", Toast.LENGTH_SHORT ).show();
			
		}
		
		//have all fields
		else{
			
			//new project object
			Project project = new Project(1, projectName, defaultStyle);
			
			//insert project
			int state = application.getReferenceMeData().addProject(project);
			
			//successful insert
			if(state == 1){
				
				//display toast
				Toast.makeText(NewProjectActivity.this, "Project created successfully", Toast.LENGTH_SHORT ).show();
				
				//start my project list activity
				startActivity(new Intent(NewProjectActivity.this,MyProjectsListActivity.class));
				
				//finish current activity
				finish();
				
			}
			
			//insert fail;
			else{
				
				//display toast
				Toast.makeText(NewProjectActivity.this, "Could not create project", Toast.LENGTH_SHORT ).show();
				
			}
			
		}
		
	}
	
	/**
	 * call when press back
	 */
	@Override
	public void onBackPressed() {
		
		//start my project list activity
		startActivity(new Intent(NewProjectActivity.this,MyProjectsListActivity.class));
		
		//finish activity
		finish();
		
	}
	
	/**
	 * @author eranga
	 * spinner item selecting listener class - inner class
	 */
	public class DefaultStyleItemSelectedListener implements OnItemSelectedListener {

		/**
		 * call when click
		 */
	    public void onItemSelected(AdapterView<?> parent,View view, int pos, long id) {
	      
	    	//do nothing
	    	
	    }

	    /**
	     * nothing selected
	     */
	    public void onNothingSelected(AdapterView parent) {
	    	
	    	//do nothing.
	    	
	    }
	    
	}
	
}