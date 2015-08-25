package appwolf.referenceme.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author eranga
 * activity class of edit/new reference tip
 */
public class TipEditReferenceActivity extends Activity implements OnClickListener{

	//previous button
	Button previousButton;
	
	//next button
	Button nextButton;
	
	//close button
	Button closeButton;
	
	/**
	 * call when create activity
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.tip_fifth_layout);
    	
    	//initialize buttons
    	previousButton = (Button) findViewById(R.id.tip_fifth_layout_previous_button);
    	nextButton = (Button) findViewById(R.id.tip_fifth_layout_next_button);
    	closeButton = (Button) findViewById(R.id.tip_fifth_layout_close_button);
    	
    	/*
    	 * disable buttons
    	 */
    	//visible false button
    	previousButton.setVisibility(View.INVISIBLE);
    	nextButton.setVisibility(View.INVISIBLE);
		
		//disable button
    	previousButton.setEnabled(false);
    	nextButton.setEnabled(false);
    	
    	//set listener
    	closeButton.setOnClickListener(TipEditReferenceActivity.this);
    	
	}
	
	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
		
		//close button
		if(v==closeButton){
			
			//finish activity
			finish();
			
		}
		
	}
	
	/**
	 * call when press back
	 */
	@Override
	public void onBackPressed() {
		
		//finish activity
		finish();
		
	}
}
