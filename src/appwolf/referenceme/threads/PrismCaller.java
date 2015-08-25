package appwolf.referenceme.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
							
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
import appwolf.referenceme.utils.XMLfunctions;

/**
 * @author eranga
 * facilitate functions of prism api call
 */
public class PrismCaller extends AsyncTask<String, String, String>{

	//application
	ReferenceMeApplication application;
	
	//reference me activity
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
	public PrismCaller(ReferenceMeActivity referenceMeActivity,
					   ReferenceListActivity referenceListActivity,
					   NewISSNISBNActivity newISSNISBNActivity,
					   String barCode,
					   String isbn){
		
		/*
		 * set
		 */
		this.referenceMeActivity = referenceMeActivity;
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
		
		//api call
		callPrismApi(apiParameter);
		
		//return
		return Integer.toString(apiCallState);
		
	}

	/**
	 * call prism api
	 * @param apiParameter - isbn, issn or barcode
	 */
	public void callPrismApi(String apiParameter){
		
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
				
				//process server xml response 
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
	 * @param reference no
	 */
	public InputStream getStreamConntion(String apiParameter){
		
		//input stream
		InputStream inputStream=null;

		//get reference type
		String referenceType = application.getReferenceType();
		
		//server url
		String urlString= "";
		
		//reference type book
		if(referenceType.equals("book")){
			
			//set up url
			urlString = "http://prism.talis.com/brookes/items.rss?query=isbn:"+apiParameter;
			
		}
		
		//reference type journal
		else{
			
			//set up url
			urlString = "http://prism.talis.com/brookes/items.rss?query="+apiParameter;

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
        				
            System.out.println("PrismCaller/getStreamConnection " + e);
            		
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
	 * process server xml response and generate new citation object
	 * @param serverResponse - server response
	 * @return state
	 */
	public int processServerResponse(String serverResponse){
		
		//get current project
		Project project = application.getCurrentProject();
		
		//state
		int state = 0;
		
		//handle xml parsing exception
		try{
		
			//get xml document
			Document document = XMLfunctions.XMLfromString(serverResponse);
			
			//get item tag elements
			NodeList nodeList = document.getElementsByTagName("item");
			
			//have elements in node list
			if(nodeList.getLength()>0){
				
				//get first element of nodelist
				Element element = (Element)nodeList.item(0);
				
				//get title
				String title = XMLfunctions.getValue(element, "title");
				
				//article title
				String articleTitle = "";
				
				//journal title
				String journalTitle = "";
				
				//book
				if(application.getReferenceType().equals("book")){
					
					//set
					articleTitle = title.trim().replace(".", "").replace("-", "");
					
				}
				
				//journal
				else{
					
					//set
					journalTitle = title.trim().replace(".", "").replace("-", "");
					
				}
				
				//year come only year ex - 1995
				String year = "";
				
				/*
				 * get year, date can come in ns0,ns1,ns2 ..
				 */
				//iterate 5 times until find data
				for(int i=0;i<5;i++){
					
					//get year
					year = XMLfunctions.getValue(element, "ns"+ i +":date");
					
					//have year
					if(!year.equals("")){
						
						//break loop
						break;
						
					}
					
				}
				
				//author
				String author = "";
				
				/*
				 * get author/creator, creator can come in ns0,ns1,ns2 .. 
				 */
				//iterate 5 times until find an author
				for(int i=0;i<5;i++){
					
					//get author
					author = XMLfunctions.getValue(element, "ns"+ i +":creator");
					
					//have author
					if(!author.equals("")){
						
						//break loop
						break;
						
					}
					
				}
				
				/*
				 * setup author
				 */
				//have author
				if(!author.equals("")){
					
					/*
					 * split
					 */
					//to hold splited values
					String[] temp;
					
					// delimiter
					String delimiter = ",";
					
					//split
					temp = author.split(delimiter);
					
					//reset author
					author = (temp[1].trim() + " " + temp[0].trim()).replace(".", ""); 
					
				}
				
				//publisher
				String publisher = "";
				
				/*
				 * get publisher, publisher can come in ns0,ns1,ns2 .. 
				 */
				//iterate 5 times until find an author
				for(int i=0;i<5;i++){
					
					//get publisher
					publisher = XMLfunctions.getValue(element, "ns"+ i +":publisher");
				
					//have publisher
					if(!publisher.equals("")){
						
						//break loop
						break;
						
					}
					
				}
				
				//place of publication comes with publisher
				String placeOfPublication = "";
				
				/*
				 * split publisher and get place of publication
				 */
				//not empty
				if(!publisher.equals("")){
					
					/*
					 * split
					 */
					//to hold splited values
					String[] temp;
					
					// delimiter
					String delimiter = ":";
					
					//split
					temp = publisher.split(delimiter);
					
					//have values in temp
					if(temp.length==2){
						
						//set publisher
						publisher = temp[1].replace(",", "").trim();
						
						//set place of publication
						placeOfPublication = temp[0].replace(",", "").trim();
						
					}
					
				}
				
				System.out.println("ar " + articleTitle);
				System.out.println(journalTitle);
				System.out.println(author);
				System.out.println(publisher);
				System.out.println(placeOfPublication);
				System.out.println(year);
				
				//create new citation object
				Reference reference = new Reference(0,
												 project.getId(),
												 application.getReferenceType(),
												 project.getDefaultStyle(),
												 author.trim().replace(".", "").replace("-", ""),
												 articleTitle,
												 journalTitle, 
												 year.trim().replace(".", "").replace("-", ""),
												 placeOfPublication.trim().replace(".", "").replace("-", ""),
												 publisher.trim().replace(".", "").replace("-", ""),
												 "",
												 "",
												 "",
												 "",					 
												 "",					
												 "");							
				
				//save new object in application
				application.setCurrentReference(reference);
				
				//set state
				state = 1;
				
			}
			
			//no elements in node list
			else{
				
				//create empty reference object
				Reference reference = new Reference(0,
												 project.getId(),
												 application.getReferenceType(),
												 project.getDefaultStyle(), 
												 "", 
												 "", 
												 "",
												 "",
												 "", 
												 "", 
												 "", 
												 "",
												 "",
												 "", 
												 "",
												 "");
				
				//set empty reference object in application
				application.setCurrentReference(reference);
				
				//set state
				state = 0;
				
			}
		
		}
		
		//parsing error
		catch(Exception e){
			
			//create empty reference object
			Reference reference = new Reference(0, 
											 project.getId(),
											 application.getReferenceType(),
											 project.getDefaultStyle(), 
											 "",
											 "",
											 "",
											 "",
											 "", 
											 "", 
											 "", 
											 "",
											 "",
											 "", 
											 "", 
											 "");
			
			//set empty reference object in application
			application.setCurrentReference(reference);
			
			//set state
			state = -1;
			
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
		
		//set prism api call state
		application.setPrismAPICallState(Integer.parseInt(result));
		
		//reference me activity
		if(referenceMeActivity!=null){
		
			//successful api call
			if(result.equals("1")){
				
				//close progress dialog
				//referenceMeActivity.progressDialog.dismiss();
				
				//save citation object in application
				
				//start new citation activity
				//referenceMeActivity.startActivity(new Intent(referenceMeActivity,NewCitationActivity.class));
				
				//display toast
				//Toast.makeText(referenceMeActivity, "found a book", Toast.LENGTH_SHORT ).show();
				
			}
			
			//network connection error
			else if(result.equals("-2")){
				
				//close progress dialog
				//referenceMeActivity.progressDialog.dismiss();
				
				//start new citation activity
				//referenceMeActivity.startActivity(new Intent(referenceMeActivity,NewCitationActivity.class));
				
				//display toast
				//Toast.makeText(referenceMeActivity, "connection error,cannot retrive details", Toast.LENGTH_SHORT ).show();
				
			}
			
			//no data from api call or data parsing error
			else{
				
				//call google api
				//new GoogleCaller(referenceMeActivity,null,barCode,isbn).execute();
				
			}
			
			//call google api
			new GoogleCaller(referenceMeActivity,null,null,barCode,apiParameter).execute();
			
		}
		
		//reference list activity
		else if(referenceListActivity!=null){
			
			//successful api call
			if(result.equals("1")){
				
				//close progress dialog
				//referenceListActivity.progressDialog.dismiss();
				
				//save citation object in application
				
				//start new citation activity
				//referenceListActivity.startActivity(new Intent(referenceListActivity,NewCitationActivity.class));
				
				//display toast
				//Toast.makeText(referenceListActivity, "found a book", Toast.LENGTH_SHORT ).show();
				
			}
			
			//network connection error
			else if(result.equals("-2")){
				
				//close progress dialog
				//referenceListActivity.progressDialog.dismiss();
				
				//start new citation activity
				//referenceListActivity.startActivity(new Intent(referenceListActivity,NewCitationActivity.class));
				
				//display toast
				//Toast.makeText(referenceListActivity, "connection error,cannot retrive details", Toast.LENGTH_SHORT ).show();
				
			}
			
			//no data from api call or data parsing error
			else{
				
				//call google api
				//new GoogleCaller(null,referenceListActivity,barCode,isbn).execute();
				
			}
			
			//call google api
			new GoogleCaller(null,referenceListActivity,null,barCode,apiParameter).execute();
			
		}
		
		//new issn isbn activity
		else{
		
			//call google api
			new GoogleCaller(null,null,newISSNISBNActivity,barCode,apiParameter).execute();
			
		}
		
	}
	
}