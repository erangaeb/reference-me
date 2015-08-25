package appwolf.referenceme.activities;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import appwolf.referenceme.pojos.Project;

/**
 * @author eranga
 * dapater class for project list
 */
public class MyProjectsListAdapter extends BaseAdapter {

	//project list
	ArrayList<Project> projectList;
	
	//context
	Context context;
	
	/**
	 * constructor
	 */
	public MyProjectsListAdapter(Context context,ArrayList<Project> list){
		
		/*
		 * set
		 */
		this.projectList = list;
		this.context = context;
		
	}
	
	/**
	 * call when data set changed
	 * @param data
	 */
	public void changeData(ArrayList<Project> list) {
		
		//set
		this.projectList = list;
		
		//notify change
        notifyDataSetChanged();
        
    }
	
	@Override
	public int getCount() {
		
		return projectList.size();
		
	}

	@Override
	public Object getItem(int position) {
		
		return projectList.get(position);
		
	}

	@Override
	public long getItemId(int position) {

		return position;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// A ViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
		final ViewHolder holder;
		
		//get project object
		Project project = (Project) getItem(position);
		
		//view empty
		if (convertView == null) {
			
			//inflate print_list_row layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.my_projects_list_row_layout, parent, false);
			
			//new holder to store reference to child views
			holder=new ViewHolder();
		
			//set icon
			holder.imageView = (ImageView) convertView.findViewById(R.id.my_projects_list_row_layout_icon);
	
			//set project name
			holder.projectNameTextView = (TextView) convertView.findViewById(R.id.my_projects_list_row_layout_project_name);
			
			//set view holder
			convertView.setTag(holder);
			
		}
	
		//already have a view
		else{
			
			//get view holder back
			holder=(ViewHolder) convertView.getTag();
			
		}
		
		/*
		 * bind text with view holder text view to efficient use
		 */
		holder.imageView.setImageResource(R.drawable.project);
		holder.projectNameTextView.setText(project.getName());
		
		//return
		return convertView;
		
	}

	/**
	 * inner class
	 * keep reference to children view to avoid unnecessary calls 
	 */
	static class ViewHolder {
		
		//image view
		ImageView imageView;
		
		//project name text view
        TextView projectNameTextView;
        
        //attribute value text view
        TextView attributeValueTextView;
               
    }
	
}
