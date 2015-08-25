package appwolf.referenceme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;															
import android.view.View;									
import android.view.View.OnClickListener;							
import android.widget.Button;

/**
 * @author eranga
 * activity class relates to sixth tip 
 */
public class TipSixthActivity extends Activity implements OnClickListener{

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
    	setContentView(R.layout.tip_sixth_layout);
    	
    	//initialize buttons
    	previousButton = (Button) findViewById(R.id.tip_sixth_layout_previous_button);
    	closeButton = (Button) findViewById(R.id.tip_sixth_layout_close_button);
    	
    	//set listener
    	previousButton.setOnClickListener(TipSixthActivity.this);
    	closeButton.setOnClickListener(TipSixthActivity.this);
    	
	}
	
	/**
	 * call when click on view
	 */
	@Override
	public void onClick(View v) {

		//previous button
		if(v== previousButton){
		
			//back to previous tip
			startActivity(new Intent(TipSixthActivity.this,TipFifthActivity.class));
			
			//finish activity
			finish();
			
		}
		
		//close button
		else if(v==closeButton){
			
			//go to refereneme
			startActivity(new Intent(TipSixthActivity.this,ReferenceMeActivity.class));
			
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
		startActivity(new Intent(TipSixthActivity.this,TipFifthActivity.class));
		
		//finish activity
		finish();
		
	}
	
}