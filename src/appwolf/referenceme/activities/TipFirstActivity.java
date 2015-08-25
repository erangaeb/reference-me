package appwolf.referenceme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * activity class relates to first tip
 * @author eranga
 */
public class TipFirstActivity extends Activity implements OnClickListener{

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
    	setContentView(R.layout.tip_first_layout);
    	
    	//initialize next button
    	nextButton = (Button) findViewById(R.id.tip_first_layout_next_button);
    	closeButton = (Button) findViewById(R.id.tip_first_layout_close_button);
    	
    	//set listener
    	nextButton.setOnClickListener(TipFirstActivity.this);
    	closeButton.setOnClickListener(TipFirstActivity.this);
    	
	}

	@Override
	public void onClick(View v) {
		
		//next button
		if(v == nextButton){
			
			//go to second tip
			startActivity(new Intent(TipFirstActivity.this,TipSecondActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//close button
		else if(v==closeButton){
			
			//go to refereneme 
			startActivity(new Intent(TipFirstActivity.this,ReferenceMeActivity.class));
			
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
		startActivity(new Intent(TipFirstActivity.this,ReferenceMeActivity.class));
		
		//finish activity
		finish();
		
	}
	
}