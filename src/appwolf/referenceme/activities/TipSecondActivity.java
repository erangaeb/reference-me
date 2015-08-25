package appwolf.referenceme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author eranga
 * activity class related to second tip
 */
public class TipSecondActivity extends Activity implements OnClickListener{

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
    	
    	//set listener
    	nextButton.setOnClickListener(TipSecondActivity.this);
    	previousButton.setOnClickListener(TipSecondActivity.this);
    	closeButton.setOnClickListener(TipSecondActivity.this);
    	
	}

	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
		
		//next button
		if(v== nextButton){
			
			//go to thirst tip
			startActivity(new Intent(TipSecondActivity.this,TipThirdActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//previous button
		else if(v== previousButton){
		
			//back to previous tip
			startActivity(new Intent(TipSecondActivity.this,TipFirstActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//close button
		else if(v==closeButton){
			
			//go to refereneme 
			startActivity(new Intent(TipSecondActivity.this,ReferenceMeActivity.class));
			
			//finish activity
			finish();
			
		}
		
	}
	
	/**
	 * call when press back
	 */
	@Override
	public void onBackPressed() {
		
		//go to reference me tip
		startActivity(new Intent(TipSecondActivity.this,TipFirstActivity.class));
		
		//finish activity
		finish();
		
	}
	
}
