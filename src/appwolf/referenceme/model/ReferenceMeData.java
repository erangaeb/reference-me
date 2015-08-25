package appwolf.referenceme.model;

import java.util.ArrayList;
							
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import appwolf.referenceme.pojos.Reference;
import appwolf.referenceme.pojos.Project;

/**
 * @author eranga
 * database class of reference me 
 */
public class ReferenceMeData {

	//context
	Context context;
	
	//DBHelper object,DBHelper using only in this class
	DBHelper dbHelper;
	
	/**
	 * constructor
	 * @param context
	 */
	public ReferenceMeData(Context context) {
	
		//set
		this.context=context;
		
		//create db helper
		dbHelper=new DBHelper();
		
	}
	
	/**
	 * clean all (destructor)
	 */
	public void close(){
		
		//close DB helper
		dbHelper.close();
		
	}
	
	/**
	 * get isInstalled attribute from application_data table
	 * @return loginState - login state with server
	 */
	public String getInstalledState(){
		
		//state
		String isInstalledState="0";
		
		//get database
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		// create cursor for get app data   ( table name		     columns   		select		 	 	select args  		  groupby   having  orderby )	
		Cursor appDataCursor = db.query(DBHelper.TABLE_NAME_APPDATA , null  , "attribute_name=?", new String[]{"isInstalled"},  null   , null ,  null   );
		
		//has elements in cursor
		if(appDataCursor.moveToNext()){
			
			//last record
			appDataCursor.moveToLast();
			
			//get status
			isInstalledState=appDataCursor.getString(2);
			
		}
		
		//close cursor
		appDataCursor.close();
		
		//close database
		db.close();
		
		//return
		return isInstalledState;
		
	}
	
	/**
	 * update isInstalled attribute in applicationData
	 * @param isInstalled - determine (1 or 0)
	 */
	public void setIsInstalledState(String isInstalled){
		
		//open database
        SQLiteDatabase db=dbHelper.getWritableDatabase();	
        
        //create content values
        ContentValues appDataValues =new ContentValues();
    	
    	//application data - isInstalled = 0						
    	appDataValues.put("attribute_value", isInstalled);														
    								
		//update application data									
		db.update(DBHelper.TABLE_NAME_APPDATA, appDataValues, "attribute_name=?",new String[]{"isInstalled"});
		
		//close database
		db.close();
		
	}
	
	/**
	 * get all project in database
	 * @return project list
	 */
	public ArrayList<Project> getAllProjects(){
		
		//list to hold project data
		ArrayList<Project> projecttList=new ArrayList<Project>();
		
		//open database
        SQLiteDatabase db=dbHelper.getWritableDatabase();

		// create a cursor to get data
		Cursor projectCursor = db.query(DBHelper.TABLE_NAME_PROJECT, null, null, null,null, null, null);
		
		//until has elements
		while(projectCursor.moveToNext()){
			
			/*
			 * get project data
			 */
			int id = projectCursor.getInt(0);
			String name = projectCursor.getString(1);
			String defaultStyle = projectCursor.getString(2);
			
			//create project object
			Project project = new Project(id, name, defaultStyle);
			
			//add project to list
			projecttList.add(project);
			
		}
		
		//close cursor
		projectCursor.close();
		
		//close db
		db.close();
		
		//return project list
		return projecttList;
		
	}
	
	/**
	 * get all references correspond to project from database
	 * @return
	 */
	public ArrayList<Reference> getProjectreferences(Project project){
		
		//reference list
		ArrayList<Reference> referenceList = new ArrayList<Reference>();
		
		//open database
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        System.out.println("id " +project.getId());
        
		//create a cursor to get data
		Cursor referenceCursor = db.query(DBHelper.TABLE_NAME_CITATION, null, "project_id=?", new String[]{Integer.toString(project.getId())},null, null, null);
		
		//until has elements
		while(referenceCursor.moveToNext()){
			
			/*
			 * get citation data
			 */
			int id = referenceCursor.getInt(0);
			int projectId = referenceCursor.getInt(1);
			String type = referenceCursor.getString(2);
			String defaultStyle = referenceCursor.getString(3);
			String authorName = referenceCursor.getString(4);
			String articleTitle = referenceCursor.getString(5);
			String journalTitle = referenceCursor.getString(6);
			String publicationYear = referenceCursor.getString(7);
			String publicationPlace = referenceCursor.getString(8);
			String publisher = referenceCursor.getString(9);
			String edition = referenceCursor.getString(10);
			String contributor = referenceCursor.getString(11);
			String journalNo = referenceCursor.getString(12); 
			String volumeNo = referenceCursor.getString(13);
			String issueNo = referenceCursor.getString(14);
			String pageNo = referenceCursor.getString(15);
			
			//create new reference object
			Reference reference = new Reference(id, 
											 projectId, 
											 type, 
											 defaultStyle, 
											 authorName, 
											 articleTitle, 
											 journalTitle, 
											 publicationYear, 
											 publicationPlace, 
											 publisher, 
											 edition, 
											 contributor,
											 journalNo,
											 volumeNo, 
											 issueNo, 
											 pageNo);
			
			//add to list
			referenceList.add(reference);
			
		}
		
		//close cursor
		referenceCursor.close();
		
		//close db
		db.close();
		
		//return
		return referenceList;
		
	}
	
	/**
	 * insert new project to database
	 * @return insert state
	 */
	public int addProject(Project project){
		
		//inserting state
		int insertState = 1;
		
		//open database
        SQLiteDatabase db=dbHelper.getWritableDatabase();
		
		//create content values for project
        ContentValues projectContentValues =new ContentValues();
        
        //fill content value
        projectContentValues.put("name", project.getName());
        projectContentValues.put("default_style", project.getDefaultStyle());
		
        //insert project data
    	try {
    	
    		//insert fail throw exception
    		db.insertOrThrow(DBHelper.TABLE_NAME_PROJECT, null, projectContentValues);
            
        }
    	catch (Exception e) {
        	
    		//insert fail
    		insertState = 0;
    		
            //catch code
        	System.out.println("Error " + e);
        	
        }
    	
    	//close db
    	db.close();
    	
    	//return insert state
    	return insertState;
        
	}
	
	/**
	 * insert reference into database
	 * @param reference
	 * @return insert state
	 */
	public int addCitation(Reference reference){
		
		//insert state
		int insertState=1;
		
		//open database
        SQLiteDatabase db=dbHelper.getWritableDatabase();

		//create content values
        ContentValues citationContentValues =new ContentValues();
        
        /*
         * fill content values
         */
        citationContentValues.put("project_id", reference.getProjectId());
        citationContentValues.put("type", reference.getType());
        citationContentValues.put("default_style", reference.getDefaultStyle());
        citationContentValues.put("author_name", reference.getAuthorName());
        citationContentValues.put("article_title", reference.getArticleTitle());
        citationContentValues.put("journal_title", reference.getJournalTitle());
        citationContentValues.put("publication_year", reference.getPublicationYear());
        citationContentValues.put("publication_place", reference.getPublicationPlace());
        citationContentValues.put("publisher", reference.getPublisher());
        citationContentValues.put("edition", reference.getEdition());
        citationContentValues.put("contributor", reference.getContributor());
        citationContentValues.put("journal_no", reference.getJournalNo());
        citationContentValues.put("volume_no", reference.getVolumeNo());
        citationContentValues.put("issue_no", reference.getIssueNo());
        citationContentValues.put("page_no", reference.getPageNo());
        
        //insert project data
    	try {
    	
    		//insert fail throw exception
    		db.insertOrThrow(DBHelper.TABLE_NAME_CITATION, null, citationContentValues);
            
        }
    	catch (Exception e) {
        	
    		//insert fail
    		insertState = 0;
    		
            //catch code
        	System.out.println("Error " + e);
        	
        }
        
    	//close db
    	db.close();
    	
		//return
		return insertState;
		
	}
	
	/**
	 * edit reference
	 * @param reference
	 * @return 
	 */
	public int editCitation(Reference reference){
		
		//edit state
		int state = 1;
		
		//open database
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        
        //create content values
        ContentValues citationContentValues =new ContentValues();
        
        /*
         * fill content values
         */
        citationContentValues.put("project_id", reference.getProjectId());
        citationContentValues.put("type", reference.getType());
        citationContentValues.put("default_style", reference.getDefaultStyle());
        citationContentValues.put("author_name", reference.getAuthorName());
        citationContentValues.put("article_title", reference.getArticleTitle());
        citationContentValues.put("journal_title", reference.getJournalTitle());
        citationContentValues.put("publication_year", reference.getPublicationYear());
        citationContentValues.put("publication_place", reference.getPublicationPlace());
        citationContentValues.put("publisher", reference.getPublisher());
        citationContentValues.put("edition", reference.getEdition());
        citationContentValues.put("contributor", reference.getContributor());
        citationContentValues.put("journal_no", reference.getJournalNo());
        citationContentValues.put("volume_no", reference.getVolumeNo());
        citationContentValues.put("issue_no", reference.getIssueNo());
        citationContentValues.put("page_no", reference.getPageNo());
		
        try{
        
        	//update record						
        	db.update(DBHelper.TABLE_NAME_CITATION, 
        			  citationContentValues, 
        			  "id=?",
        			  new String[]{Integer.toString(reference.getId())});
        	
        }catch(Exception e){
        	
        	//set status
        	state = 0;
        	
        }
		
		//close database
		db.close();
		
		//return
		return state;
		
	}
	
	/**
	 * delete project from database
	 * @param project
	 * @return delete state
	 */
	public int deleteProject(Project project){
		
		//deletion state
		int deleteState = 1;
		
		//open database
        SQLiteDatabase db=dbHelper.getWritableDatabase();
		
		//handle errors
		try{
			
			//delete relevant project record
			db.delete(DBHelper.TABLE_NAME_PROJECT, "id=?", new String[]{Integer.toString(project.getId())});
			
		}
		
		//error
		catch(Exception e){
		
			System.out.println(e);
			
		}
		
		//close db
		db.close();
		
		//return delete state
		return deleteState;
		
	}
	
	/**
	 * delete citation from database
	 * @param reference
	 * @return deleteState
	 */
	public int deleteReference(Reference reference){
		
		//deletion state
		int deleteState = 1;
		
		//open database
        SQLiteDatabase db=dbHelper.getWritableDatabase();
		
		//handle errors
		try{
			
			//delete relevant reference record
			db.delete(DBHelper.TABLE_NAME_CITATION, "id=?", new String[]{Integer.toString(reference.getId())});
			
		}
		
		//error
		catch(Exception e){
		
			System.out.println(e);
			
		}
		
		//close db
		db.close();
		
		//return delete state
		return deleteState;
		
	}
	
	/**
	 * inner class class for SQLite database operations (helper class for create,open and upgrade database) 
	 */
	private class DBHelper extends SQLiteOpenHelper {
		
		// constants
		public static final String DB_NAME = "referencemedb";
		public static final String TABLE_NAME_PROJECT = "project";
		//public static final String TABLE_NAME_CITATION_BOOK = "citation_book";
		//public static final String TABLE_NAME_CITATION_JOURNAL = "citation_journal";
		public static final String TABLE_NAME_CITATION = "citation";
		public static final String TABLE_NAME_STYLE = "style";
		public static final String TABLE_NAME_APPDATA = "application_data";
		
		//DB version
		public static final int DB_VERSION = 27;
		
		/**
		 * constructor
		 * @param context - context
		 */		
		public DBHelper() {
							
			super(context, DB_NAME, null, DB_VERSION);

		}
		
		/**
		 * call once n only once
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			
			//create table project
			String sqlProject = "create table project (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			  										  "name TEXT, " +
			  										  "default_style TEXT)";
			
			/*//create table for citation book
			String sqlCitationBook = "create table citation_book (id 				INTEGER PRIMARY KEY AUTOINCREMENT, " +
													 		 	 "project_id 		TEXT, " +
													 		 	 "default_style 	TEXT, " +
													 		 	 "author_name 		TEXT, " +
													 		 	 "title  			TEXT, " +
													 		 	 "publication_year  TEXT, " +
													 		 	 "publication_place TEXT, " +
													 		 	 "publisher  		TEXT, " +
													 		 	 "edition    		TEXT, " +
													 		 	 "page_no  			TEXT)";
			
			//create table for citation journal
			String sqlCitationJournal = "create table citation_journal (id 				INTEGER PRIMARY KEY AUTOINCREMENT, " +
													 		 		"project_id 		TEXT, " +
													 		 		"type 				TEXT, " + // book or journal
													 		 		"default_style 		TEXT, " +
													 		 		"author_name 		TEXT, " +
													 		 		"article_title  	TEXT, " +
													 		 		"journal_title  	TEXT, " +
													 		 		"publication_year  	TEXT, " +
													 		 		"publication_place 	TEXT, " +
													 		 		"publisher  		TEXT, " +
													 		 		"edition    		TEXT, " +
													 		 		"volume_no    		TEXT, " +
													 		 		"issue_no    		TEXT, " +
													 		 		"page_no  			TEXT)";*/
			
			//create table for citation
			String sqlCitation = "create table citation (id 				INTEGER PRIMARY KEY AUTOINCREMENT, " +
													 	"project_id 		int, " +
												 		"type 				TEXT, " + // book or journal
													 	"default_style 		TEXT, " +
													 	"author_name 		TEXT, " +
													 	"article_title  	TEXT, " +
													 	"journal_title  	TEXT, " +
													 	"publication_year  	TEXT, " +
													 	"publication_place 	TEXT, " +
													 	"publisher  		TEXT, " +
													 	"edition    		TEXT, " +
													 	"contributor		TEXT, " +
													 	"journal_no			TEXT, " +
													 	"volume_no    		TEXT, " +
													 	"issue_no    		TEXT, " +
													 	"page_no  			TEXT)";
			
			//create table sql for style - reference/bibliography styles
			String sqlStyle = "create table style (id 	INTEGER PRIMARY KEY AUTOINCREMENT, " +
			  									  "name 	TEXT)";
			
			//create table for store application attributes
			String sqlApplicationData="create table application_data (id 				INTEGER PRIMARY KEY AUTOINCREMENT, " +
																	 "attribute_name 	TEXT, " +
																	 "attribute_value 	TEXT )";
			
			/*
			 * execute sql
			 */
			db.execSQL(sqlProject);
			db.execSQL(sqlCitation);
			db.execSQL(sqlStyle);
			db.execSQL(sqlApplicationData);
			
			/*
			 * insert initial data
			 */
			//create content values for project
	        ContentValues projectContentValues =new ContentValues();
	        
	        //fill content value
	        projectContentValues.put("name", "Quick Reference");
	        projectContentValues.put("default_style", "harvard");
			
	        //create content value for application data - isInstalled
	        ContentValues isInstalledContentValues = new ContentValues();
	        
	        //fill content value
	        isInstalledContentValues.put("attribute_name", "isInstalled");
	        isInstalledContentValues.put("attribute_value", "0");
	        
	        //insert initial project data
	    	try {
	    	
	    		//insert fail throw exception
	    		db.insertOrThrow(DBHelper.TABLE_NAME_PROJECT, null, projectContentValues);
	    		db.insertOrThrow(DBHelper.TABLE_NAME_APPDATA, null, isInstalledContentValues);
	            
	        }
	    	catch (Exception e) {
	        	
	            //catch code
	        	System.out.println("Error " + e);
	        	
	        }
	        
		}

		/**
		 * call when upgrade the database, normally changing the DB_VERSION
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			//drop tables
			db.execSQL("drop table if exists "+ TABLE_NAME_PROJECT);
			db.execSQL("drop table if exists "+ TABLE_NAME_CITATION);
			db.execSQL("drop table if exists "+ TABLE_NAME_STYLE);
			db.execSQL("drop table if exists "+ TABLE_NAME_APPDATA);
			
			//create databases again
			this.onCreate(db);
			
		}
		
	}
	
}