package appwolf.referenceme.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import appwolf.referenceme.activities.NewISSNISBNActivity;
import appwolf.referenceme.activities.NewReferenceActivity;
import appwolf.referenceme.activities.ReferenceListActivity;
import appwolf.referenceme.activities.ReferenceMeActivity;
import appwolf.referenceme.application.ReferenceMeApplication;
import appwolf.referenceme.pojos.Reference;
import appwolf.referenceme.pojos.Project;

/**
 * @author eranga
 * facilitate functions of google api call
 */
public class GoogleCaller extends AsyncTask<String, String, String>{

	//application
	ReferenceMeApplication application;
	
	//refence me activity
	ReferenceMeActivity referenceMeActivity;
	
	//reference list list activity
	ReferenceListActivity referenceListActivity;
	
	//new issn isbn activity
	NewISSNISBNActivity newISSNISBNActivity;
	
	//bar code
	String barCode;
	
	//isbn
	String isbn;
	
	//api parameter - isbn or issn 
	String apiParameter;
	
	//keep track with api call state
	int apiCallState = 0;
	
	/**
	 * constructor
	 * @param activity
	 */
	public GoogleCaller(ReferenceMeActivity refenceMeActivity,
						ReferenceListActivity referenceListActivity,
						NewISSNISBNActivity newISSNISBNActivity,
						String barCode,
						String isbn){
		
		/*
		 * set 
		 */
		this.referenceMeActivity = refenceMeActivity;
		this.referenceListActivity = referenceListActivity;
		this.newISSNISBNActivity = newISSNISBNActivity;
		this.barCode = barCode;
		this.isbn = isbn;
		this.apiParameter = isbn;
		
		//reference me activity
		if(referenceMeActivity!=null){
		
			//initialize application
			application = (ReferenceMeApplication)referenceMeActivity.getApplication();
		
		}
		
		//reference list activity
		else if(referenceListActivity!=null){
			
			//initialize application
			application = (ReferenceMeApplication)referenceListActivity.getApplication();
			
		}
		
		//new issn isbn activity
		else{
			
			//initialize application
			application = (ReferenceMeApplication)newISSNISBNActivity.getApplication();
			
		}
		
	}
	
	/**
	 * background functionality
	 */
	@Override
	protected String doInBackground(String... params) {
		
		//sleep for while
		try{
			Thread.sleep(2000);	
		}
		catch(Exception e){
		}
		
		//call google api
		callGoogleApi(apiParameter);
		
		//return
		return Integer.toString(apiCallState);
		
	}

	/**
	 * call google api
	 * @param apiParameter - issn,isbn or barcode
	 */
	public void callGoogleApi(String apiParameter){
		
		//server response
		String serverResponse="";
		
		//connect to server and get input stream
		InputStream inputStream=getStreamConntion(apiParameter);
		
		//handle network errors
		try{
			
			//no error
			if(inputStream!=null){
				
				//get server response
				serverResponse=getServerResponse(inputStream);
				
				//System.out.println(serverResponse);
				
				//process server response
				apiCallState = processServerResponse(serverResponse);
				
			}
			
			//network error
			else{
				
				apiCallState=-2;
				
			}
		
		}
		
		//network error
		catch(Exception e){
			
			apiCallState=-2;
								
			System.out.println("DataSyncer/sync " + e);
			
		}
		
	}
	
	/**
	 * create stream connection with server
	 * @param api call parameter
	 */
	public InputStream getStreamConntion(String apiParameter){
		
		//input stream
		InputStream inputStream=null;

		//get reference type
		String referenceType = application.getReferenceType();
		
		//server url
		String urlString = "";
			
		//reference type book
		if(referenceType.equals("book")){
			
			//set up url
			urlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+apiParameter;
			
		}
		
		//reference type journal
		else{
			
			//set up url
			urlString = "https://www.googleapis.com/books/v1/volumes?q=issn:"+apiParameter;
			
		}
		
		System.out.println(urlString);
		
		//handle network errors
        try {
        	
        	//create url object
        	URL url= new URL(urlString);											
        	
        	//open connection
        	URLConnection urlConnection=url.openConnection();
        	
        	//cast
        	HttpURLConnection httpUrlConnection=(HttpURLConnection)urlConnection;
        	
        	//set requesting method
        	//httpUrlConnection.setRequestMethod("POST");
        	httpUrlConnection.setRequestMethod("GET");
        					
        	//set attributes
        	httpUrlConnection.setDoInput(true);			
        	//httpUrlConnection.setDoOutput(true);
        		
        	//connect
        	httpUrlConnection.connect();
        	
        	//get response code
            int responseCode = httpUrlConnection.getResponseCode();
            
            //connection is OK
            if (responseCode == HttpURLConnection.HTTP_OK) {
           
            	//get input stream from connection
            	inputStream = httpUrlConnection.getInputStream();
            	
            }
            
            //network error
            else{
            	
            	//error in network connection
            	apiCallState=-2;
            				
            }
            		
            //get input stream
            inputStream = httpUrlConnection.getInputStream();
            
        //error in connection    
        }catch(Exception e){
        
            //error in network connection
        	apiCallState=-2;
        				
            System.out.println("GoogleCaller/getStreamConnection " + e);
            		
        }			
        
        //return
        return inputStream;
		
	}
	
	/**
	 * get server response
	 * @param inputStream
	 * @return server response
	 */
	private String getServerResponse(InputStream inputStream) {
		
		//for read data from stream
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		//to store reading data
		StringBuilder sb = new StringBuilder();

		//reading line
		String readingLine = null;
		
		//handle error
		try {
			
			//read each and every line
			while ((readingLine = bufferedReader.readLine()) != null) {
			
				//append to string builder
				sb.append(readingLine + "\n");
				
			}
			
		}
		
		//error 
		catch (IOException e) {
			
			System.out.println("PrismCaller/getServerResponse" + e);
			
		}
		
		//at the end close streams
		finally {
			
			try {
			
				//close stream
				inputStream.close();
				
			} catch (IOException e) {
				
				System.out.println("DataSyncer/getServerResponse" + e);
				
			}
			
		}
		
		//return
		return sb.toString();
		
	}
	
	/**
	 * process server json response and generate new citation object
	 * @param serverResponse - server response
	 * @return state
	 */
	public int processServerResponse(String serverResponse){
		
		//state
		int state = 1;
		
		//get current project from application
		Project project = application.getCurrentProject();
		
		//get reference
		Reference reference = application.getCurrentReference();
		
		//handle json parsing exception
		try{
			
			//json object
			JSONObject jsonResponse = new JSONObject(serverResponse);
			
			//json item array
			JSONArray jsonItemArray = jsonResponse.getJSONArray("items");
			
			//have elemets in item array
			if(jsonItemArray.length()>0){
				
				//get initial item object
				JSONObject jsonItemObject = jsonItemArray.getJSONObject(0);
				
				//get volume info from json item object
				JSONObject jsonVolumeInfoObject = jsonItemObject.getJSONObject("volumeInfo");
				
				//title
				String title = jsonVolumeInfoObject.get("title").toString();
			
				//article title
				String articleTitle = reference.getArticleTitle();
				
				//journal title
				String journalTitle = reference.getJournalTitle();
				
				System.out.println("artcle title " + articleTitle);
				
				System.out.println("journal title " + journalTitle);
				
				//book
				if(application.getReferenceType().equals("book")){
					
					//currently no article title
					if(articleTitle.equals("")){
					
						//set 
						articleTitle = title; 
					
					}	
						
					//journal title empty
					journalTitle = "";
						
				}
				
				//journal
				else{
					
					System.out.println("inside journal");
					
					//currently no journal title
					if(journalTitle.equals("")){
					
						//set
						journalTitle = title;
					
					}
					
					//empty article title
					articleTitle = "";
					
				}
				
				System.out.println("journal " +journalTitle);
				
				//publisher
				String publisher = "";
				
				//handle json exception
				try{
				
					//get publisher
					publisher = jsonVolumeInfoObject.get("publisher").toString();
				
				}
				
				//json parsing exception
				catch(Exception e){
					
					System.out.println("no publisher " + e);
					
				}
				
				//published date
				String publishedDate = "";
				
				//handle json exception
				try{
					
					//get published date
					publishedDate = jsonVolumeInfoObject.get("publishedDate").toString();
					
				}
				
				//json parsing exception
				catch(Exception e){
					
					System.out.println("no publish date " + e);
					
				}
				
				/*
				 * split date
				 */
				//not empty
				if(!publishedDate.equals("")){
					
					/*
					 * split
					 */
					//to hold splited values
					String[] temp;
					
					// delimiter
					String delimiter = "-";
					
					//split
					temp = publishedDate.split(delimiter);
					
					//have values in temp
					if(temp.length>0){
						
						//set place of publication
						publishedDate = temp[0];
						
					}
					
				}
				
				//author
				String author = "";
				
				//no of authors
				int authorCount = 0;
				
				//handle json exception
				try{
				
					//get author array
					JSONArray jsonAuthorArray = jsonVolumeInfoObject.getJSONArray("authors");
					
					//set author count
					authorCount = jsonAuthorArray.length();
					
					//iterate over author array
					for(int i=0;i<jsonAuthorArray.length();i++){
						
						//initial
						if(i==0){
							
							//get and set author
							author = jsonAuthorArray.get(i).toString();
							
						}
						
						else{
							
							//get and set author
							author = author + "," + jsonAuthorArray.get(i).toString();
							
						}
						
					}
					
				}
				
				//json parsing exception
				catch(Exception e){
					
					System.out.println("no author " + e);
					
				}
				
				/*
				 * set details in application citation object
				 */
				//article title
				application.getCurrentReference().setArticleTitle(articleTitle);
				
				//journal title
				application.getCurrentReference().setJournalTitle(journalTitle);
				
				//publisher
				if(reference.getPublisher().equals("")){
					
					//set publisher
					application.getCurrentReference().setPublisher(publisher);
					
				}
				
				//published date
				if(reference.getPublicationYear().equals("")){
					
					//set date
					application.getCurrentReference().setPublicationYear(publishedDate);
					
				}
				
				//author ,have multiple authors
				if(authorCount>0){
					
					//set author
					application.getCurrentReference().setAuthorName(author);
					
				}
				
			}
			
		}
		
		//json parsing error
		catch(Exception e){
			
			//set state
			state = 0;
			
			//print error
			System.out.println("GoogleCaller/processServerResponce " +e);
			
		}
		
		//return
		return state;
		
	}
	
	/**
	 * call when doInBackground is finish
	 */
	@Override
	protected void onPostExecute(String result){
		
		super.onPostExecute(result);
		
		//set google api call state
		application.setGoogleAPICallState(Integer.parseInt(result));
		
		//prism call state
		int prismCallState = application.getPrismAPICallState();
		
		//google state
		int googleCallState = application.getGoogleAPICallState();
							
		//reference me activity					
		/*if(referenceMeActivity!=null){					
							
			//close progress dialog				
			referenceMeActivity.progressDialog.dismiss();				
			
			//successful api call
			if(result.equals("1")){
				
				//display toast
				Toast.makeText(referenceMeActivity, "found a book", Toast.LENGTH_SHORT ).show();
				
			}
			
			//network connection error
			else if(result.equals("-2")){
				
				//display toast
				Toast.makeText(referenceMeActivity, "connection error,cannot retrive details", Toast.LENGTH_SHORT ).show();
				
			}
			
			//no matching data 
			else{
				
				//display toast
				//Toast.makeText(referennceMeActivity, "no matching data", Toast.LENGTH_SHORT ).show();
				
			}
			
			//start new citation activity
			referenceMeActivity.startActivity(new Intent(referenceMeActivity,NewReferenceActivity.class));
			
		}
		
		//reference list activity
		else if(referenceListActivity!=null){
			
			//close progress dialog
			referenceListActivity.progressDialog.dismiss();
			
			//successful api call
			if(result.equals("1")){
				
				//display toast
				Toast.makeText(referenceListActivity, "found a book", Toast.LENGTH_SHORT ).show();
				
			}
			
			//network connection error
			else if(result.equals("-2")){
				
				//display toast
				Toast.makeText(referenceListActivity, "connection error,cannot retrive details", Toast.LENGTH_SHORT ).show();
				
			}
			
			//no matching data 
			else{
				
				//display toast
				//Toast.makeText(referenceListActivity, "", Toast.LENGTH_SHORT ).show();
				
			}
			
			//start new citation activity
			referenceListActivity.startActivity(new Intent(referenceListActivity,NewReferenceActivity.class));
			
		}
		
		//new issn isbn activity
		else{
			
			//close progress dialog
			newISSNISBNActivity.progressDialog.dismiss();
			
			//successful api call
			if(result.equals("1")){
				
				//display toast
				Toast.makeText(newISSNISBNActivity, "found a book", Toast.LENGTH_SHORT ).show();
				
			}
			
			//network connection error
			else if(result.equals("-2")){
				
				//display toast
				Toast.makeText(newISSNISBNActivity, "connection error,cannot retrive details", Toast.LENGTH_SHORT ).show();
				
			}
			
			//no matching data 
			else{
				
				//display toast
				//Toast.makeText(newISSNISBNActivity, "", Toast.LENGTH_SHORT ).show();
				
			}
			
			//start new citation activity
			newISSNISBNActivity.startActivity(new Intent(newISSNISBNActivity,NewReferenceActivity.class));
			
		}*/
			
		//reference me activity					
		if(referenceMeActivity!=null){					
							
			//close progress dialog				
			referenceMeActivity.progressDialog.dismiss();				
			
			//successful api call
			if(prismCallState == 1 || googleCallState == 1 ){
				
				//display toast
				Toast.makeText(referenceMeActivity, "found a " + application.getCurrentReference().getType(), Toast.LENGTH_SHORT ).show();
				
				//start new citation activity
				referenceMeActivity.startActivity(new Intent(referenceMeActivity,NewReferenceActivity.class));
				
				//set current activity in application
				application.setCurrentActivity("reference_me");
				
				//finish activity
				referenceMeActivity.finish();
				
			}
			
			//network connection error
			else if(prismCallState == -2 && googleCallState == -2){
				
				//display toast
				Toast.makeText(referenceMeActivity, "connection error,cannot retrive details", Toast.LENGTH_SHORT ).show();
				
			}
			
			//no matching data
			else if(prismCallState == 0 && googleCallState ==0){
				
				//display toast
				Toast.makeText(referenceMeActivity, "no matching data", Toast.LENGTH_SHORT ).show();
				
			}
			
		}
		
		//reference list activity
		else if(referenceListActivity!=null){
			
			//close progress dialog				
			referenceListActivity.progressDialog.dismiss();				
			
			//successful api call
			if(prismCallState == 1 || googleCallState == 1 ){
				
				//display toast
				Toast.makeText(referenceListActivity, "found a " + application.getCurrentReference().getType(), Toast.LENGTH_SHORT ).show();
				
				//start new citation activity
				referenceListActivity.startActivity(new Intent(referenceListActivity,NewReferenceActivity.class));
				
				//set current activity in application
				application.setCurrentActivity("reference_list");
				
				//finish activity
				referenceListActivity.finish();
				
			}
			
			//network connection error
			else if(prismCallState == -2 && googleCallState == -2){
				
				//display toast
				Toast.makeText(referenceListActivity, "connection error,cannot retrive details", Toast.LENGTH_SHORT ).show();
				
			}
			
			//no matching data
			else if(prismCallState == 0 && googleCallState ==0){
				
				//display toast
				Toast.makeText(referenceListActivity, "no matching data", Toast.LENGTH_SHORT ).show();
				
			}
			
		}
		
		//new issn isbn activity
		else{
			
			//close progress dialog				
			newISSNISBNActivity.progressDialog.dismiss();				
			
			//successful api call
			if(prismCallState == 1 || googleCallState == 1 ){
				
				//display toast
				Toast.makeText(newISSNISBNActivity, "found a " + application.getCurrentReference().getType(), Toast.LENGTH_SHORT ).show();
				
				//start new citation activity
				newISSNISBNActivity.startActivity(new Intent(newISSNISBNActivity,NewReferenceActivity.class));
				
				//set current activity in application
				application.setCurrentActivity("new_issn_isbn");
				
				//finish activity
				newISSNISBNActivity.finish();
				
			}
			
			//network connection error
			else if(prismCallState == -2 && googleCallState == -2){
				
				//display toast
				Toast.makeText(newISSNISBNActivity, "connection error,cannot retrive details", Toast.LENGTH_SHORT ).show();
				
			}
			
			//no matching data
			else if(prismCallState == 0 && googleCallState ==0){
				
				//display toast
				Toast.makeText(newISSNISBNActivity, "no matching data", Toast.LENGTH_SHORT ).show();
				
			}
			
		}
		
	}
	
}