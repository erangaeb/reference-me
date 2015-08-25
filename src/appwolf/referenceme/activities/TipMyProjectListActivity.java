package appwolf.referenceme.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author eranga
 * activity class of my project list tip
 */
public class TipMyProjectListActivity extends Activity implements OnClickListener{

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
    	setContentView(R.layout.tip_second_layout);
    	
    	//initialize buttons
    	nextButton = (Button) findViewById(R.id.tip_second_layout_next_button);
    	previousButton = (Button) findViewById(R.id.tip_second_layout_previous_button);
    	closeButton = (Button) findViewById(R.id.tip_second_layout_close_button);
    	
    	/*
    	 * disable buttons
    	 */
    	//visible false button
    	nextButton.setVisibility(View.INVISIBLE);
    	previousButton.setVisibility(View.INVISIBLE);
		
		//disable button
    	nextButton.setEnabled(false);
    	previousButton.setEnabled(false);
    	
    	//set listener
    	closeButton.setOnClickListener(TipMyProjectListActivity.this);
    	
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
