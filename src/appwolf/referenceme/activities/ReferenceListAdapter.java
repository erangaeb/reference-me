package appwolf.referenceme.activities;

import java.util.ArrayList;					
import java.util.Collections;					
		
import android.content.Context;													
import android.view.LayoutInflater;								
import android.view.View;										
import android.view.ViewGroup;									
import android.widget.BaseAdapter;								
import android.widget.ImageView;								
import android.widget.TextView;				
import appwolf.referenceme.application.ReferenceMeApplication;	
import appwolf.referenceme.pojos.Reference;						
import appwolf.referenceme.utils.ReferenceComparator;						
import appwolf.referenceme.utils.ReferenceGenerator;			
						
/**					
 * @author eranga					
 * adapter class of citation list			
 */								
public class ReferenceListAdapter extends BaseAdapter{											

	//application
	ReferenceMeApplication application;
	
	//context
	Context context;
	
	//citation list
	ArrayList<Reference> referenceList;
	
	//reference type citation or bibliography
	String referenceType;
	
	/**
	 * constructor
	 * @param activity
	 * @param list
	 */
	public ReferenceListAdapter(ReferenceListActivity activity,ArrayList<Reference> list,String referenceType){
		
		//set
		this.referenceList = list;
		this.referenceType = referenceType;
		
		//set context
		context = (Context) activity;
		
		//initialize application
		application = (ReferenceMeApplication) activity.getApplication(); 						
		
		//bibliography list
		if(!referenceType.equals("citation")){
		
			//sort citation list
			Collections.sort(referenceList, new ReferenceComparator());
		
		}
		
		//initialize all reference text in application
		application.setAllReferenceText("");
		
	}
	
	@Override
	public int getCount() {						

		return referenceList.size();
		
	}

	@Override
	public Object getItem(int position) {
	
		return referenceList.get(position);
		
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
		Reference reference = (Reference) getItem(position);
		
		//view empty
		if (convertView == null) {
			
			//inflate print_list_row layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.reference_list_row_layout, parent, false);
			
			//new holder to store reference to child views
			holder=new ViewHolder();
			
			//set reference
			holder.referenceTextView = (TextView) convertView.findViewById(R.id.reference_list_row_layout_reference);
			
			//set view holder
			convertView.setTag(holder);
			
		}
		
		//already have a view
		else{
			
			//get view holder back
			holder=(ViewHolder) convertView.getTag();
			
		}
		
		//get style type
		String styleType = application.getStyleType();
		
		//reference text
		String referenceText = new ReferenceGenerator().getReferenceText(reference, referenceType, styleType);
		
		//bind text with view holder text view to efficient use
		holder.referenceTextView.setText(referenceText.replace("<i>","").replace("</i>", ""));
		
		//last position
		if(position==referenceList.size()-1){
			
			//set all reference text in application
			application.setAllReferenceText(application.getAllReferenceText() + referenceText);
			
		}
		
		//not last position
		else{
			
			//set all reference text in application
			application.setAllReferenceText(application.getAllReferenceText() + referenceText + "<br></br><br></br>");
			
		}
		
		//return
		return convertView;
		
	}

	/**
	 * inner class
	 * keep reference to children view to avoid unnecessary calls 
	 */
	static class ViewHolder {
		
		//image view, NOT USING
		ImageView imageView;
		
		//reference text view
        TextView referenceTextView;
        
        //reference type, NOT USING
        TextView referenceTypeTextView;
        
    }
	
}