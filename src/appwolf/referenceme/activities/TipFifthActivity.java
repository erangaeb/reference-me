package appwolf.referenceme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author eranga
 * activity class realtes to fifth tip
 */
public class TipFifthActivity extends Activity implements OnClickListener{

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
    	
    	//set listener
    	previousButton.setOnClickListener(TipFifthActivity.this);
    	nextButton.setOnClickListener(TipFifthActivity.this);
    	closeButton.setOnClickListener(TipFifthActivity.this);
    	
	}
	
	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
		
		//previous button
		if(v== previousButton){
		
			//back to previous tip
			startActivity(new Intent(TipFifthActivity.this,TipFourthActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//next button
		else if(v==nextButton){
			
			//start next tip
			startActivity(new Intent(TipFifthActivity.this,TipSixthActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//close button
		else if(v==closeButton){
			
			//go to refereneme
			startActivity(new Intent(TipFifthActivity.this,ReferenceMeActivity.class));
			
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
		startActivity(new Intent(TipFifthActivity.this,TipFourthActivity.class));
		
		//finish activity
		finish();
		
	}
	
}