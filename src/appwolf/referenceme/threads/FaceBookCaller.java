package appwolf.referenceme.threads;

import android.os.AsyncTask;
import android.widget.Toast;
import appwolf.referenceme.activities.FacebookShareActivity;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.utils.FaceBookShare;

/**
 * @author eranga
 * async task to post a status in facebook
 */
public class FaceBookCaller extends AsyncTask<String, String, String>{

	//application
	ReferenceMeApplication application;
	
	//facebook share activity
	FacebookShareActivity facebookShareActivity;
	
	//facebook
	FaceBookShare facebookShare;
	
	/**
	 * constructor
	 * @param activity
	 * @param facebook share
	 */
	public FaceBookCaller(FacebookShareActivity activity,FaceBookShare facebookShare){
		
		//set
		this.facebookShareActivity = activity;
		this.facebookShare = facebookShare;
		
		//initialize
		application = (ReferenceMeApplication) facebookShareActivity.getApplication();
		
	}
	
	/**
	 * background process
	 */
	@Override
	protected String doInBackground(String... arg0) {

		//share
		String response = Share();
		
		//return
		return response;
		
	}
	
	/**
	 * post status on wall
	 * @return response
	 */
	public String Share(){
		
		//response
		String response = facebookShare.share(application.getAllReferenceText().replace("<br></br>", "<center></center>"));
		
		//return
		return response;
		
	}
	
	/**
	 * call when doInBackground is finish
	 */
	@Override
	protected void onPostExecute(String result){
		
		super.onPostExecute(result);

		//close dialog
		//facebookShareActivity.facebookPostingDialog.dismiss();
		
		//finish activity
		facebookShareActivity.finish();

		//display toast
		Toast.makeText(facebookShareActivity, result, Toast.LENGTH_SHORT ).show();
		
	}

}
