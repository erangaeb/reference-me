package appwolf.referenceme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author eranga
 * activity class of reference list first tip
 */
public class TipReferenceListFirstActivity extends Activity implements OnClickListener{

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
    	setContentView(R.layout.tip_third_layout);
    	
    	//initialize buttons
    	nextButton = (Button) findViewById(R.id.tip_third_layout_next_button);
    	previousButton = (Button) findViewById(R.id.tip_third_layout_previous_button);
    	closeButton = (Button) findViewById(R.id.tip_third_layout_close_button);
    	
    	/*
    	 * disable previous button
    	 */
    	//visible false button
    	previousButton.setVisibility(View.INVISIBLE);
		
		//disable button
    	previousButton.setEnabled(false);
    	
    	//set listener
    	nextButton.setOnClickListener(TipReferenceListFirstActivity.this);
    	closeButton.setOnClickListener(TipReferenceListFirstActivity.this);
    	
	}
	
	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
									
		//next button
		if(v== nextButton){
								
			//go to fourth tip
			startActivity(new Intent(TipReferenceListFirstActivity.this,TipReferenceListSecondActivity.class));
			
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