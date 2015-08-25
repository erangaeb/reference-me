package appwolf.referenceme.application;

import com.facebook.android.Facebook;

import android.app.Application;
import appwolf.referenceme.model.ReferenceMeData;
import appwolf.referenceme.pojos.Reference;
import appwolf.referenceme.pojos.Project;

/**
 * @author eranga
 * application class
 */
public class ReferenceMeApplication extends Application{

	//database instance
	ReferenceMeData referenceMeData;
	
	//current project
	Project currentProject;
	
	//current reference
	Reference currentReference;
	
	//style type
	String styleType;
	
	//book or journal
	String referenceType;
	
	//reference medium citation or bibliography
	String referenceMedium;
	
	//single reference text
	String singleReferenceText;
	
	//all reference text
	String allReferenceText;
	
	//shareing text
	String shareText;
	
	//current activity
	String currentActivity;
	
	//api call state - prism
	int prismAPICallState;	
	
	//api call sate - google
	int googleAPICallState;

	//app id
	private static final String APP_ID = "343287469054819";
	
	//facebook
	Facebook facebook;
	
	/**
	 * call when create
	 */
	@Override
	public void onCreate() {

		super.onCreate();
	
		/*
		 * initialize
		 */
		referenceMeData = new ReferenceMeData(this);
		currentProject = new Project(1, "Quick Referenc", "harvard");
		currentReference = null;
		styleType = "harvard";
		referenceType = "book";
		referenceMedium = "citation";
		singleReferenceText = "";
		allReferenceText = "";
		shareText = "";
		currentActivity = "";
		prismAPICallState = 0;
		googleAPICallState = 0;
		
		//initialize facebook
		facebook = new Facebook(APP_ID);
		
	}
	
	/**
	 * call when terminate application
	 */
	@Override
	public void onTerminate() {
		
		super.onTerminate();
		
		//clear data
		referenceMeData.close();
		
	}
	
	/**
	 * get database class instance/singleton
	 * @return referenceMeData 
	 */
	public ReferenceMeData getReferenceMeData(){
		
		//return
		return referenceMeData;
		
	}

	public Project getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
	}

	public Reference getCurrentReference() {
		return currentReference;
	}

	public void setCurrentReference(Reference reference) {
		this.currentReference = reference;
	}

	public String getStyleType() {
		return styleType;
	}

	public void setStyleType(String styleType) {
		this.styleType = styleType;
	}

	public void setReferenceMeData(ReferenceMeData referenceMeData) {
		this.referenceMeData = referenceMeData;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getAllReferenceText() {
		return allReferenceText;
	}

	public void setAllReferenceText(String allReferenceText) {
		this.allReferenceText = allReferenceText;
	}

	public int getPrismAPICallState() {
		return prismAPICallState;
	}

	public void setPrismAPICallState(int prismAPICallState) {
		this.prismAPICallState = prismAPICallState;
	}

	public int getGoogleAPICallState() {
		return googleAPICallState;
	}

	public void setGoogleAPICallState(int googleAPICallState) {
		this.googleAPICallState = googleAPICallState;
	}

	public String getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(String currentActivity) {
		this.currentActivity = currentActivity;
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	public String getReferenceMedium() {
		return referenceMedium;
	}

	public void setReferenceMedium(String referenceMedium) {
		this.referenceMedium = referenceMedium;
	}

	public String getSingleRefenceText() {
		return singleReferenceText;
	}

	public void setSingleRefenceText(String singleRefenceText) {
		this.singleReferenceText = singleRefenceText;
	}

	public String getShareText() {
		return shareText;
	}

	public void setShareText(String shareText) {
		this.shareText = shareText;
	}

}
