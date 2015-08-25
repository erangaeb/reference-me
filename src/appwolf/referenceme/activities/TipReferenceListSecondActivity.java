package appwolf.referenceme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author eranga
 * activity class of reference list second tip
 */
public class TipReferenceListSecondActivity extends Activity implements OnClickListener{

	//next button
	Button nextButton;
	
	//previous button
	Button previousButton;
	
	//close button
	Button closeButton;
	
	/**
	 * call when create activity
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.tip_fourth_layout);
    	
    	//initialize buttons
    	nextButton = (Button) findViewById(R.id.tip_fourth_layout_next_button);
    	previousButton = (Button) findViewById(R.id.tip_fourth_layout_previous_button);
    	closeButton = (Button) findViewById(R.id.tip_fourth_layout_close_button);
    	
    	/*
    	 * disable next button
    	 */
    	//visible false button
    	nextButton.setVisibility(View.INVISIBLE);
		
		//disable button
    	nextButton.setEnabled(false);
    	
    	//set listener
    	previousButton.setOnClickListener(TipReferenceListSecondActivity.this);
    	closeButton.setOnClickListener(TipReferenceListSecondActivity.this);
    	
	}
	
	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
		
		//previous button
		if(v== previousButton){
		
			//back to previous tip
			startActivity(new Intent(TipReferenceListSecondActivity.this,TipReferenceListFirstActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//close button
		else if(v==closeButton){
					
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