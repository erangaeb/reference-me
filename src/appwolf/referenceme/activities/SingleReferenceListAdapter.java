package appwolf.referenceme.activities;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import appwolf.referenceme.activities.ReferenceListAdapter.ViewHolder;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.pojos.Reference;
import appwolf.referenceme.utils.ReferenceGenerator;

/**
 * adapter class of single reference list
 * @author eranga
 */
public class SingleReferenceListAdapter extends BaseAdapter{

	//application
	ReferenceMeApplication application;
	
	//context
	Context context;
	
	//citation list
	ArrayList<Reference> citationList;
	
	//reference type citation or bibliography
	String referenceType;
	
	/**
	 * constructor
	 * @param conext
	 * @param list
	 */
	public SingleReferenceListAdapter(SingleReferenceListActivity activity,Context context,ArrayList<Reference> list,String referenceType){
		
		/*
		 * set
		 */
		this.context = context;
		this.citationList = list;
		this.referenceType = referenceType;
		
		//initialize application
		application = (ReferenceMeApplication) activity.getApplication();
		
	}
	
	@Override
	public int getCount() {

		return citationList.size();
		
	}

	@Override
	public Object getItem(int position) {
	
		return citationList.get(position);
		
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
		
		//get citation object
		Reference citation = (Reference) getItem(position);
		
		//view empty
		if (convertView == null) {
			
			//inflate print_list_row layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.reference_list_row_layout, parent, false);
			
			//new holder to store reference to child views
			holder=new ViewHolder();
			
			//set citation name
			holder.citationNameTextView = (TextView) convertView.findViewById(R.id.reference_list_row_layout_reference);
			
			//set view holder
			convertView.setTag(holder);
			
		}
		
		//already have a view
		else{
			
			//get view holder back
			holder=(ViewHolder) convertView.getTag();
			
		}
		
		//get reference text
		String referenceText = "";
		
		//get style type
		String styleType = application.getStyleType();
		
		//citation
		if(referenceType.equals("citation")){
			
			//harvard style
			if(styleType.equals("harvard")){
			
				//citation text
				referenceText = new ReferenceGenerator().getHarwardCitation(citation, "book");
			
			}
			
			//oxford
			else if(styleType.equals("oxford")){
				
				//citation text
				referenceText = new ReferenceGenerator().getOxfordCitation(citation, "book");
				
			}
			
			//vancouver
			else if(styleType.equals("vancouver")){
				
				//citation text
				referenceText = new ReferenceGenerator().getVancouverBookReference(citation, "book");
				
			}
			
			//mrha
			else if(styleType.equals("mrha")){
				
				//citation text
				referenceText = new ReferenceGenerator().getMRHACitation(citation, "book");
				
			}
			
		}
		
		//bibliography
		else{
			
			///harvard style
			if(styleType.equals("harvard")){
			
				//bibliography text
				referenceText = new ReferenceGenerator().getHarvardBibliography(citation, "book");
			
			}
			
			//oxford
			else if(styleType.equals("oxford")){
				
				//get
				referenceText = new ReferenceGenerator().getOxfordBibliography(citation, "book");
				
			}
			
			//vancouver
			else if(styleType.equals("vancouver")){
				
				//get
				referenceText = new ReferenceGenerator().getVancouverBookReference(citation, "book");
				
			}
			
			//mrha
			else if(styleType.equals("mrha")){
				
				//citation text
				referenceText = new ReferenceGenerator().getMRHABibliography(citation, "book");
				
			}
			
		}
		
		//bind text with view holder text view to efficient use
		holder.citationNameTextView.setText(referenceText);
		
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
		
		//citation name text view
        TextView citationNameTextView;
        
    }
	
}
