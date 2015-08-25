package appwolf.referenceme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author eranga
 * activity class relates to fourth tip
 */
public class TipFourthActivity extends Activity implements OnClickListener{

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
    	
    	//set listener
    	nextButton.setOnClickListener(TipFourthActivity.this);
    	previousButton.setOnClickListener(TipFourthActivity.this);
    	closeButton.setOnClickListener(TipFourthActivity.this);
    	
	}
	
	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {
		
		//next button
		if(v== nextButton){
			
			//go to fifth tip
			startActivity(new Intent(TipFourthActivity.this,TipFifthActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//previous button
		else if(v== previousButton){
		
			//back to previous tip
			startActivity(new Intent(TipFourthActivity.this,TipThirdActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//close button
		else if(v==closeButton){
			
			//go to refereneme
			startActivity(new Intent(TipFourthActivity.this,ReferenceMeActivity.class));
			
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
		startActivity(new Intent(TipFourthActivity.this,TipThirdActivity.class));
		
		//finish activity
		finish();
		
	}
	
}