package appwolf.referenceme.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.pojos.Project;

/**
 * @author eranga
 * activity class correspond to project list
 */
public class MyProjectsListActivity extends Activity implements OnClickListener{

	//application
	ReferenceMeApplication application;
	
	//project list
	ArrayList<Project> projectList;
	
	//list view
	ListView projectListView;
	
	//empty view
	ViewStub emptyView;
	
	//adapter
	MyProjectsListAdapter adapter;
	
	//referenceme bar
	RelativeLayout referencemeBar;
	
	//back button
	Button backButton;
	
	//info button
	Button infoButton;
	
	//new button
	Button newButton;
	
	/**
	 * call when create activity
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.my_projects_list_layout);
    	
    	//initialize application
    	application = (ReferenceMeApplication)MyProjectsListActivity.this.getApplication();
    	
    	/*
    	 * initialize
    	 */
    	referencemeBar = (RelativeLayout) findViewById(R.id.my_projects_list_layout_referenceme_bar); 
    	newButton = (Button) findViewById(R.id.my_projects_list_layout_new_button);
    	backButton = (Button) findViewById(R.id.my_projects_list_layout_back_button);
    	infoButton = (Button) findViewById(R.id.my_projects_list_layout_info_button);
    	
    	/*
    	 * set click listeners
    	 */
    	referencemeBar.setOnClickListener(MyProjectsListActivity.this);
    	newButton.setOnClickListener(MyProjectsListActivity.this);
    	backButton.setOnClickListener(MyProjectsListActivity.this);
    	infoButton.setOnClickListener(MyProjectsListActivity.this);
    	
    	//initialize list view
    	projectListView = (ListView) findViewById(R.id.my_projects_list_layout_list);
    	
    	//set empty view
    	emptyView = (ViewStub) findViewById(R.id.my_project_list_layout_empty_view);
    	
    	//set empty view for list view
		projectListView.setEmptyView(emptyView);
    	
		//initialize list
    	projectList = new ArrayList<Project>();
		
		//get project list from database
		projectList = application.getReferenceMeData().getAllProjects();
    	
    	//new adapter
    	adapter = new MyProjectsListAdapter(MyProjectsListActivity.this, projectList);
    	
    	//set list adapter
    	projectListView.setAdapter(adapter);
    	
    	//set click listener
    	projectListView.setOnItemClickListener(new OnItemClickListener(){
    		
    		/**
    		 * click on view
    		 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long duration){
				
				//get selected project
				Project project = (Project) adapter.getItem(position);
				
				//set selected project as current project in application
				application.setCurrentProject(project);
				
				//set style type in application (style type - current project default style)
				application.setStyleType(project.getDefaultStyle());
				
				//display reference list activity
				startActivity(new Intent(MyProjectsListActivity.this,ReferenceListActivity.class));
				
				//finish activity
				finish();
				
			}
			
		});
    	
    	//set long press listener
    	projectListView.setOnItemLongClickListener(new OnItemLongClickListener() {

    		/**
    		 * call in long press
    		 */
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v,int position, long id) {

				//get corresponding project
				Project project= (Project)adapter.getItem(position);

				//quick reference project
				if(project.getName().equals("Quick Reference")){
					
					//do nothing, cannot delete project
					
				}
				
				//other project
				else{
				
					//display information dialog
					displayInformationDialog("Are you sure you want to delete this project ? ", project);
				
				}
				
				//return
				return true;
				
			}
    		
		});
				
	}
	
	/**
	 * display information dialog
	 * @param message - message text
	 * @param project - corresponding project
	 */
	public void displayInformationDialog(String message,final Project project){
		
		//initiate dialog
        final Dialog dialog = new Dialog(MyProjectsListActivity.this);
        
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
				application.getReferenceMeData().deleteProject(project);

				//remove project from list
				projectList.remove(project);
				
				//reset adapter
				projectListView.setAdapter(adapter);
				
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
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {

		//referenceme bar
		if(v==referencemeBar){
			
			//display tip screen
			//startActivity(new Intent(MyProjectsListActivity.this,TipMyProjectListActivity.class));
			
		}
		
		//new button
		else if(v==newButton){
			
			//start new project activity
			startActivity(new Intent(MyProjectsListActivity.this,NewProjectActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//info button
		else if(v==infoButton){
			
			//display tip screen
			startActivity(new Intent(MyProjectsListActivity.this,TipMyProjectListActivity.class));
			
		}
		
		//back button
		else if(v==backButton){
			
			//start my project list activity
			startActivity(new Intent(MyProjectsListActivity.this,ReferenceMeActivity.class));
			
			//finish activity
			finish();
			
		}
		
	}
	
	/**
	 * call when press back
	 */
	@Override
	public void onBackPressed() {
		
		//start my project list activity
		startActivity(new Intent(MyProjectsListActivity.this,ReferenceMeActivity.class));
		
		//finish activity
		finish();
		
	}
	
}