package appwolf.referenceme.utils;

import java.util.ArrayList;
								
import appwolf.referenceme.pojos.Author;											
import appwolf.referenceme.pojos.Reference;																	

/**
 * @author eranga
 * generate references according to reference styles
 */
public class ReferenceGenerator {

	/**
	 * get citation for harvard style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return citation text
	 */
	public String getHarwardCitation(Reference reference,String referenceType){

		//citation								
		String citationText = "";				
		
		//book
		if(referenceType.equals("book")){
		
			//surname text
			String surnameText = "";
			
			//empty author name
			if(reference.getAuthorName().equals("")){
			
				//set
				surnameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					surnameText = author.getSurname()+",";
					
				}
				
				//have 4 or more others
				else if(authorList.size()>=4){
					
					//get first author
					Author author = authorList.get(0);
					
					//set surname
					surnameText = author.getSurname() + " et al." ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
							
							//get and set author
							surnameText = author.getSurname() + ", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//get and set author
							surnameText = surnameText.substring(0, surnameText.length() - 2) + 
										  " and " + 
										  author.getSurname()+",";
							
						}
						
						//middle authors
						else{
						
							//middle authors
							surnameText = surnameText + author.getSurname() + ", ";
							
						}
						
					}
					
				}
			
			}
			
			//publication year text
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
	
			//create citation text
			citationText = "(" +
							surnameText.trim() + " " +
							publicationYear	+ ": " + 
							pageNo +
							")"; 
			
		}
		
		//journal
		else{								
			
			//journal title				
			String journaltitle = new ReferenceAttributeProcessor().
			   						  checkArticleTitle(reference.getJournalTitle());
			
			//set up journal title with <i> </i>
			journaltitle = "<i>" + journaltitle + "</i>";
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
			 							 checkPublicationYear(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//non empty page no
			if(!pageNo.equals("#enter page no#")){
				
				//set
				//pageNo = "p." + pageNo;
				pageNo = pageNo;
				
			}
			
			//create citation
			citationText = "(" + 
							journaltitle + ", " + 
							publicationYear + ": " + 
							pageNo + 
							")";
			
		}
		
		//return
		return citationText;
		
	}
	
	/**
	 * get citation for harvard style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return citation text
	 */
	public String getHarvardBibliography(Reference reference,String referenceType){
		
		//bibliography text
		String bibliographyText = "";
					
		if(referenceType.equals("book")){			
						
			//surname text			
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#.";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//get initial text
					String initialText = new ReferenceAttributeProcessor().
											 getHarvardBookBibliographyInitials(author.getInitials());
					
					//set author name text
					authorNameText = author.getSurname()+", "+initialText;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
							
							//get initial text
							String initialText = new ReferenceAttributeProcessor().
													 getHarvardBookBibliographyInitials(author.getInitials());
							
							//set author name text
							authorNameText = author.getSurname()+", "+initialText;
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
													
							//get initial text
							String initialText = new ReferenceAttributeProcessor().
													 getHarvardBookBibliographyInitials(author.getInitials());
						
							//set
							authorNameText =  authorNameText + " and " + author.getSurname()+", "+initialText;
							
						}
						
						//middle authors
						else{
							
							//get initial text
							String initialText = new ReferenceAttributeProcessor().
													 getHarvardBookBibliographyInitials(author.getInitials());
						
							//set 
							authorNameText =  authorNameText + " " + author.getSurname()+", "+initialText;
							
						}
						
					}
					
				}
				
			}
			
			//publication year
			String publicationYear = "(" + new ReferenceAttributeProcessor().
											   checkPublicationYear(reference.getPublicationYear()) + ")";
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//format title with <i> </i>
			title = "<i>" + title + "</i>";
			
			//edition
			String edition = new ReferenceAttributeProcessor().checkEdition(reference.getEdition());
			
			//non empty edition
			if(!edition.equals("#enter edition no#")){
				
				// edition
				if(!edition.equals("1")){
					
					//set
					edition = edition + " ed. ";
					
				}
				
				//edition = 1
				else{
					
					//set
					edition = "";
					
				}
				
			}
			
			//empty
			else{
				
				//set
				edition = edition + ". ";
				
			}
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
			
			//create bibliography text
			bibliographyText = authorNameText.trim() + " " + 
							   publicationYear + ". " +
							   title + ". " +
							   edition + 
							   publicationPlace + ": " +
							   publisher + "." ;
			
		}
		
		//journal
		else{
			
			//surname text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//get initial text
					String initialText = new ReferenceAttributeProcessor().
											 getHarvardBookBibliographyInitials(author.getInitials());
					
					//set author name text
					authorNameText = author.getSurname()+", "+initialText.replace(" ", "");
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
							
							//get initial text
							String initialText = new ReferenceAttributeProcessor().
													 getHarvardBookBibliographyInitials(author.getInitials());
							
							//set author name text
							authorNameText = author.getSurname()+", "+initialText;
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//get initial text
							String initialText = new ReferenceAttributeProcessor().
													 getHarvardBookBibliographyInitials(author.getInitials());
						
							//set 
							authorNameText =  authorNameText + " & " + author.getSurname()+", "+initialText;
							
						}
						
						//middle authors
						else{
							
							//get initial text
							String initialText = new ReferenceAttributeProcessor().
													 getHarvardBookBibliographyInitials(author.getInitials());
						
							//set 
							authorNameText =  authorNameText + " " + author.getSurname()+", "+initialText;
							
						}
						
					}
					
				}
				
			}
			
			//article title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
	   		  						  checkJournalTitle(reference.getJournalTitle());
			
			//publication year
			String publicationYear = "(" + new ReferenceAttributeProcessor().
											   checkPublicationYear(reference.getPublicationYear()) + ")";
			
			//format journal title with <i> </i>
			journalTitle = "<i>" + journalTitle + "</i>";
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
				
			//issue no
			String issueNo = new ReferenceAttributeProcessor().
								 checkIssueNo(reference.getIssueNo());	
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//non empty page no
			if(!pageNo.equals("#enter page no#")){
				
				//set
				pageNo = "pp. " + pageNo;
				
			}
			
			//create bibliography text
			bibliographyText = authorNameText.trim() + " " + 
			   				   publicationYear + " " + 
			   				   articleTitle + ". " +
			   				   journalTitle + " " +
			   				   volumeNo + "(" + issueNo + ")" + ", " +
			   				   pageNo + ".";
			
		}
		
		//return
		return bibliographyText;
		
	}

	/**
	 * get bibliography and citation for vancouver style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getVancouverBookReference(Reference reference,String referenceType){
		
		//reference
		String referenceText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#.";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + " " + author.getInitials() + "." ;
					
				}
				
				//more than 6 authors
				else if(authorList.size()>6){
					
					//get 1st author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + " " + author.getInitials() + ", " ;
					
					//get 2nd author
					author = authorList.get(1);

					//set
					authorNameText = authorNameText + author.getSurname() + " " + author.getInitials() + ", "; 
					
					//get 2rd author
					author = authorList.get(2);
					
					//set
					authorNameText = authorNameText + author.getSurname() + " " + author.getInitials() + " et al.";
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + " " +
											 author.getInitials()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + " " + 
											 author.getInitials() + "."; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + " " +
											 author.getInitials()+", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//edition
			String edition = new ReferenceAttributeProcessor().checkEdition(reference.getEdition());
			
			//non empty edition
			if(!edition.equals("#enter edition no#")){
				
				//set up edition
				if(!edition.equals("1")){
					
					//set
					edition = edition + " ed. ";
					
				}
				
				//edition = 1
				else{
					
					//set
					edition = "";
					
				}
				
			}
			
			//empty
			else{
				
				//set
				edition = edition + ". ";
				
			}
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
			
			//year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//non empty page no
			if(!pageNo.equals("#enter page no#")){
				
				//set
				pageNo = "p" + pageNo;
				
			}
			
			//create reference
			referenceText = authorNameText.trim() + " " +
							title + ". " +
							edition +
							publicationPlace + ": " +
							publisher + "; " +
							publicationYear +  ". " +
							pageNo + ".";
			
		}
		
		//journal
		else{
			
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#.";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + " " + author.getInitials() + "." ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + " " +
											 author.getInitials()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + " " + 
											 author.getInitials() + ". "; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + " " +
											 author.getInitials()+", ";
							
						}
						
					}
					
				}
				
			}
			
			//article title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
	   		  						  checkJournalTitle(reference.getJournalTitle());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									  	 checkPublicationYear(reference.getPublicationYear());
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//create reference
			referenceText = authorNameText.trim() + " " +
							articleTitle + ". " +
							journalTitle + ". " +
							publicationYear + "; " +
							volumeNo + ": " +
							pageNo + ".";
			
		}
		
		//return
		return referenceText;
		
	}
	
	/**
	 * get citation for oxford style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getOxfordCitation(Reference reference,String referenceType){
		
		//citation text
		String citationText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getInitials() + " " + author.getSurname() + ", " ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getInitials() + " " +
											 author.getSurname() + ", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText.substring(0, authorNameText.length() - 2) +
											" and " +
											 author.getInitials() + " " + 
											 author.getSurname() + ", "; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText +								
											 author.getInitials() + " " + 
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().					
							   checkArticleTitle(reference.getArticleTitle());
			
			//format title with <i> </i>
			title = "<i>" + title + "</i>";
			
			//edition
			String edition = new ReferenceAttributeProcessor().checkEdition(reference.getEdition());
			
			//non empty edition
			if(!edition.equals("#enter edition no#")){
				
				//set up edition
				if(!edition.equals("1")){
					
					//set
					edition = edition + " edn, ";
					
				}
				
				//edition = 1
				else{
					
					//set
					edition = "";
					
				}
				
			}
			
			//empty
			else{
				
				//set
				edition = edition + ", ";
				
			}
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									 checkPublicationYear(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//non empty page no
			if(!pageNo.equals("#enter page no#")){
				
				//have multiple plages
				if(pageNo.contains("-")){
					
					//set page no with pp
					pageNo = "pp." + pageNo;
					
				}
				
				//single page
				else{
					
					//set page no with p
					pageNo = "p." + pageNo;
				
				}
				
			}
			
			//set citation
			citationText = authorNameText.trim() + " " +
						   title + ", " +
						   edition + " " +
						   publisher + ", " +
						   publicationPlace + ", " +
						   publicationYear + ", " +
						   pageNo +".";
			
		}
		
		//journal
		else{
			
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getInitials() + " " + author.getSurname() + ", " ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getInitials() + " " +
											 author.getSurname() + ", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 "and " +
											 author.getInitials() + " " + 
											 author.getSurname() + ", ";
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText +
											 author.getInitials() + " " + 
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
									  checkJournalTitle(reference.getJournalTitle());
			
			//format with <i> and </i>
			journalTitle = "<i>" + journalTitle + "</i>";
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
			
			//journal no
			String journalNo = new ReferenceAttributeProcessor().
			  					   checkJournalNo(reference.getJournalNo());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//non empty page no
			if(!pageNo.equals("#enter page no#")){
				
				//have multiple plages
				if(pageNo.contains("-")){
					
					//set page no with pp
					pageNo = "pp. " + pageNo;
					
				}
				
				//single page
				else{
					
					//set page no with p
					pageNo = "p. " + pageNo;
				
				}
				
			}
			
			//create citation
			citationText = authorNameText.trim() + " " +						
						   "'" + articleTitle + "'," +					 				
						   journalTitle + ", " +					
						   "vol. " + volumeNo + ", " +				 
						   "no. " + journalNo + ", " +				
						   publicationYear + ", " +			
						   pageNo + ".";				
			
		}
		
		//return
		return citationText;
		
	}
	
	/**
	 * get bibliography for oxford style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getOxfordBibliography(Reference reference,String referenceType){
		
		//citation text
		String bibliographyText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text																
					authorNameText = author.getSurname() + ", " + author.getInitials() + "." ;			
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + ", " + 
											 author.getInitials()+"., ";
									
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText.substring(0, authorNameText.length() - 2) +   
											 " and " +
											 author.getInitials() + " " + 
											 author.getSurname() + ", "; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getInitials() + " " + 
											 author.getSurname() + ", " ;
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//format with <i> and </i>
			title = "<i>" + title +"</i>";
			
			//edition
			String edition = new ReferenceAttributeProcessor().checkEdition(reference.getEdition());
			
			//non empty edition
			if(!edition.equals("#enter edition no#")){
				
				//set up edition
				if(!edition.equals("1")){
					
					//set
					edition = edition + " edn, ";
					
				}
				
				//edition = 1
				else{
					
					//set
					edition = "";
					
				}
				
			}
			
			//empty
			else{
				
				//set
				edition = edition + ", ";
				
			}
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublisher(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//non empty page no
			if(!pageNo.equals("#enter page no#")){
				
				//set
				pageNo = "pp. " + pageNo;
				
			}
			
			//set citation
			bibliographyText = authorNameText.trim() + " " +
							   title + ", " +
							   edition + 
							   publisher + ", " +
							   publicationPlace + ", " +
							   publicationYear + ", " +
							   pageNo +".";
			
		}
		
		//journal
		else{
			
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getInitials() + ".," ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + ", " + 
											 author.getInitials()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 "and " +
											 author.getInitials() + " " + 
											 author.getSurname() + ",";  
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getInitials() + " " + 
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
									  checkJournalTitle(reference.getJournalTitle());
			
			//format with <i> and </i>
			journalTitle = "<i>" + journalTitle + "</i>";
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
			
			//journal no
			String journalNo = new ReferenceAttributeProcessor().
			   					   checkJournalNo(reference.getJournalNo());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//non empty page no
			if(!pageNo.equals("#enter page no#")){
				
				//set
				pageNo = "pp. " + pageNo;
				
			}
			
			//create bibliography
			bibliographyText = authorNameText.trim() + " " +
						   	   "'" + articleTitle + "'," + 
						   	   journalTitle + ", " +
						   	   "vol. " + volumeNo + ", " + 
						   	   "no. " + journalNo + ", " +
						   	   publicationYear + ", " +
						   	   pageNo + ".";
			
		}
		
		//return
		return bibliographyText;
		
	}
	
	
	/**
	 * get citation for mrha style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getMRHACitation(Reference reference,String referenceType){
	
		//citation text
		String citationText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getFirstName() + " " + author.getSurname() + "," ;
					
				}
				
				//4 or more authors
				else if(authorList.size()>=4){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getFirstName() + " " + author.getSurname() + " and others,";
					
				}
				
				//multiple authors 1 to 3
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getFirstName() + " " +
											 author.getSurname()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText.substring(0, authorNameText.length() - 2) + 
											 " and " +
											 author.getFirstName() + " " +
											 author.getSurname()+",";
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getFirstName() + " " +
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//format with <i> and </i>
			title = "<i>" + title + "</i>";
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
				
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//non empty page no
			if(!pageNo.equals("#enter page no#")){
				
				//set
				pageNo = "p. " + pageNo;
				
			}
			
			//create citation
			citationText = authorNameText.trim() + " " +
						   title + " " +
						   "(" + publicationPlace + ": " + publisher + ", " + publicationYear + "), " + 
						   pageNo + "."; 
			
		}
		
		//journal
		else{
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getFirstName() + " " + author.getSurname() + "," ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getFirstName() + " " +
											 author.getSurname()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText.substring(0, authorNameText.length() - 2) + 
											 " and " +
											 author.getFirstName() + " " +
											 author.getSurname()+","; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getFirstName() + " " +
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//article title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
	   		  						  checkJournalTitle(reference.getJournalTitle());
			
			//format with <i> and </i>
			journalTitle = "<i>" + journalTitle + "</i>";
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									  	 checkPublicationYear(reference.getPublicationYear());
				
			//page numbers
			String pageNumbers = new ReferenceAttributeProcessor().
								 	 checkPageNo(reference.getPageNo());
			
			//page no
			String pageNo = "p. #enter page no#";
			
			//create citation
			citationText = authorNameText.trim() + " " + 
						   "'" + articleTitle + "'," +
						   journalTitle + ", " +
						   volumeNo + " " +
						   "(" + publicationYear + ")" + ", " +
						   pageNumbers + " " +
						   "(" + pageNo + ").";
			
		}
		
		//return
		return citationText;
		
	}
	
	/**
	 * get bibliography for mrha style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getMRHABibliography(Reference reference,String referenceType){
	
		//bibliography text
		String bibliographyText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() + "," ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + ", " +
											 author.getFirstName()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText.substring(0, authorNameText.length() - 2) +
											 " and " +		
											 author.getSurname() + " " +
											 author.getFirstName()+","; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText +
											 author.getSurname() + " " +
											 author.getFirstName() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//format with <i> and </i>
			title = "<i>" + title + "</i>";
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());

			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
								
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//create citation
			bibliographyText = authorNameText.trim() + " " +
						   	   title + " " +
						   	   "(" + publicationPlace + ": " + publisher + ", " + publicationYear + ")"; 
			
		}
		
		//journal
		else{
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() + ".," ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + " " +
											 author.getFirstName()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText.substring(0, authorNameText.length() - 2) + 
											 " and " +
											 author.getSurname() + " " +
											 author.getFirstName()+","; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText +
											 author.getSurname() + " " +
											 author.getFirstName() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//article title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
	   		  						  checkJournalTitle(reference.getJournalTitle());
			
			//format with <i> and </i>
			journalTitle = "<i>" + journalTitle + "</i>";
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									  	 checkPublicationYear(reference.getPublicationYear());
			
			//page numbers
			String pageNumbers = new ReferenceAttributeProcessor().
								 	 checkPageNo(reference.getPageNo());
			
			//page no
			String pageNo = "p. #enter page no#";
			
			//create bibliography
			bibliographyText = authorNameText.trim() + " " + 
						   	   "'" + articleTitle + "'," +
						   	   journalTitle + ", " +
						   	   volumeNo + " " +
						   	   "(" + publicationYear + ")" + ", " +
						   	   pageNumbers + " " + 
						   	   pageNo + ".";
			
		}
		
		//return
		return bibliographyText;
		
	}
		
	/**
	 * get citation for mla style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getMLACitation(Reference reference,String referenceType){
	
		//citation text
		String citationText = "";							
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() + "." ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + " " +
											 author.getFirstName()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 "and " + 
											 author.getSurname() + " " +
											 author.getFirstName() + "."; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getFirstName() + " " +
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//format with <i> and </i>
			title = "<i>" + title + "</i>";
			
			//edition
			String edition = new ReferenceAttributeProcessor().checkEdition(reference.getEdition());
			
			//non empty edition
			if(!edition.equals("#enter edition no#")){
				
				//set up edition
				if(!edition.equals("1")){
					
					//set
					edition = edition + " ed. ";
					
				}
				
				//edition = 1
				else{
					
					//set
					edition = "";
					
				}
				
			}
			
			//empty
			else{
				
				//set
				edition = edition + ". ";
				
			}
			
			//contributor
			String contributor = reference.getContributor();
					
			//have contributor
			if(!contributor.equals("")){
				
				//set contributor
				contributor = "Ed. " + contributor + ".";
				
			}
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
				
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());

			//medium
			String medium = "Print";
			
			//create citation
			citationText = authorNameText.trim() + " " +
						   title + ". " +
						   contributor + " " +
						   edition +
						   publicationPlace + ": " + 
						   publisher + ", " + 
						   publicationYear + ", " + 
						   medium + "."; 
			
		}
		
		//journal
		else{
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#.";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() + "." ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + " " +
											 author.getFirstName()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 "and " + 
											 author.getSurname() + " " +
											 author.getFirstName()+ "."; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + " " +
											 author.getFirstName() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//article title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
	   		  						  checkJournalTitle(reference.getJournalTitle());
			
			//format with <i> and </i>
			journalTitle = "<i>" + journalTitle + "</i>";
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									  	 checkPublicationYear(reference.getPublicationYear());
				
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//medium
			String medium = "Print";
			
			//create citation
			citationText = authorNameText.trim() + " " +
						   "\"" + articleTitle + ".\"" +
						   journalTitle + " " +
						   "(" + publicationYear + ")" + ": " +
						   pageNo + ". " +
						   medium + ".";
			
		}
		
		//return
		return citationText;
		
	}
	
	/**
	 * get bibliography for mla style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getMLABibliography(Reference reference,String referenceType){
	
		//bibliography text
		String bibliographyText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() + ".";
					
				}
				
				//more than 3 others
				else if(authorList.size()>3){
					
					//get first author
					Author author = authorList.get(0);
					
					//set surname
					authorNameText = author.getSurname() + ", " + author.getFirstName() + " et al." ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
										
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
									
							//set surname text
							authorNameText = author.getSurname() + " " +
											 author.getFirstName()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText +
											 "and " + 
											 author.getSurname() + " " +
											 author.getFirstName() + "."; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getFirstName() + " " +
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//format with <i> and </i>
			title = "<i>" + title + "</i>";
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
				
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//create citation
			bibliographyText = authorNameText.trim() + " " +
						   	   title + ". " +
						   	   publicationPlace + ": " + publisher + ", " + publicationYear + "."; 
			
		}
		
		//journal
		else{
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#.";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() +"." ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
										
							//set surname text
							authorNameText = author.getSurname() + ", " +
											 author.getFirstName()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 "and " + 
											 author.getSurname() + " " +
											 author.getFirstName() + "." ;
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getFirstName() + " " +
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//article title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
	   		  						  checkJournalTitle(reference.getJournalTitle());
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									  	 checkPublicationYear(reference.getPublicationYear());
			
			//page numbers
			String pageNumbers = new ReferenceAttributeProcessor().
								 	 checkPageNo(reference.getPageNo());
			
			//medium
			String medium = "print";
			
			//create bibliography
			bibliographyText = authorNameText.trim() + " " + 
						   	   "\"" + articleTitle + ".\"" +
						   	   journalTitle + ". " +
						   	   volumeNo + " " +
						   	   "(" + publicationYear + ")" + ": " +
						   	   pageNumbers + ". " + 
						   	   medium + ".";
			
		}
		
		//return
		return bibliographyText;
		
	}
	
	/**
	 * get citation for chicago arts style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getChicagoArtsCitation(Reference reference,String referenceType){
	
		//citation text
		String citationText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
											
					//get author
					Author author = authorList.get(0);

					//set surname text
					authorNameText = author.getFirstName() + " " + author.getSurname() + "," ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getFirstName() + " " +
											 author.getSurname()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText.substring(0, authorNameText.length() - 2) + 
											 " and " +
											 author.getFirstName() + " " +
											 author.getSurname()+"."; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getFirstName() + " " +
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//set up with <i> and </i>
			title = "<i>" + title + "</i>";
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
				
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//create citation
			citationText = authorNameText.trim() + " " +
						   title + " " +
						   "(" + publicationPlace + ": " + publisher + ", " + publicationYear + "), " + 
						   pageNo + "."; 
			
		}
		
		//journal
		else{
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#,";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getFirstName() + " " + author.getSurname() + "," ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
												
							//set surname text
							authorNameText = author.getFirstName() + " " +
											 author.getSurname()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText.substring(0, authorNameText.length() - 2) +
											 " and " +
											 author.getFirstName() + " " +
											 author.getSurname()+",";
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText +
											 author.getFirstName() + " " +
											 author.getSurname() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//article title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
	   		  						  checkJournalTitle(reference.getJournalTitle());
			
			//set up with <i> and </i>
			journalTitle = "<i>" + journalTitle + "</i>";
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
								
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									  	 checkPublicationYear(reference.getPublicationYear());
				
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//create citation
			citationText = authorNameText.trim() + " " +
						   "\"" + articleTitle + ",\"" +
						   journalTitle + " " +
						   volumeNo + " " +
						   "(" + publicationYear + ")" + ": " +
						   pageNo + ". ";
			
		}
		
		//return
		return citationText;
		
	}
	
	/**
	 * get bibliography chicago art style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getChicagoArtBibliography(Reference reference,String referenceType){
	
		//bibliography text
		String bibliographyText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#.";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() + "." ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + " " +
											 author.getFirstName()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 "and " + 
											 author.getSurname() + " " +
											 author.getFirstName() + "."; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + " " +
											 author.getFirstName() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//set up with <i> and </i>
			title = "<i>" + title + "</i>";
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
				
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//create citation
			bibliographyText = authorNameText.trim() + " " +
						   	   title + ". " +
						   	   publicationPlace + ": " + publisher + ", " + publicationYear + ". "; 
			
		}
		
		//journal
		else{
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#.";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() + ".," ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + ". " +
											 author.getFirstName()+"., ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 "and " +
											 author.getSurname() + " " +
											 author.getFirstName()+"."; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + " " +
											 author.getFirstName() + ", ";
							
						}
						
					}
					
				}
				
			}
			
			//article title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
	   		  						  checkJournalTitle(reference.getJournalTitle());
			
			//set up with <i> and </i>
			journalTitle = "<i>" + journalTitle + "</i>";
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									  	 checkPublicationYear(reference.getPublicationYear());
			
			//page numbers
			String pageNumbers = new ReferenceAttributeProcessor().
								 	 checkPageNo(reference.getPageNo());
			
			//create bibliography
			bibliographyText = authorNameText.trim() + " " + 
						   	   "\"" + articleTitle + ".\" " +
						   	   journalTitle + " " +
						   	   volumeNo + " " +
						   	   "(" + publicationYear + ")" + ": " +
						   	   pageNumbers + ".";
			
		}
		
		//return
		return bibliographyText;
		
	}
	
	/**
	 * get citation for chicago science style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getChicagoScienceCitation(Reference reference,String referenceType){
	
		//citation text
		String citationText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname();
					
				}
				
				//more than 4 authors
				else if(authorList.size()>4){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + " et al.";
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname()+", ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText.substring(0, authorNameText.length() - 2) + 
											 " and " +
											 author.getSurname(); 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + ", ";
							
						}
						
					}
						
				}
				
			}
				
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//create citation
			citationText = "(" + authorNameText.trim() + " " +
						   publicationYear + ", " +
						   pageNo + ")";  
			
		}
		
		//journal
		else{
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname();
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
												
							//set surname text
							authorNameText = author.getSurname() + " " ;
											 
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + "and " +
											 author.getSurname() ;
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText +
											 author.getSurname() + " ";
							
						}
						
					}
					
				}
				
			}
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									  	 checkPublicationYear(reference.getPublicationYear());
				
			//page no
			String pageNo = new ReferenceAttributeProcessor().
								checkPageNo(reference.getPageNo());
			
			//create citation
			citationText = "(" + authorNameText.trim() + " " +
						   publicationYear + ", " +
						   pageNo + ")";
			
		}
		
		//return
		return citationText;
		
	}
	
	/**
	 * get bibliography chicago science style
	 * @param citation
	 * @param referenceType - book or journal
	 * @return bibliography text
	 */
	public String getChicagoScienceBibliography(Reference reference,String referenceType){
	
		//bibliography text
		String bibliographyText = "";
		
		//book
		if(referenceType.equals("book")){
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#.";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() +"." ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + " " +
											 author.getFirstName()+"., ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + 
											 "and " + 
											 author.getSurname() + " " +
											 author.getFirstName()+"."; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + " " +
											 author.getFirstName() + "., ";
							
						}
						
					}
					
				}
				
			}
			
			//title
			String title = new ReferenceAttributeProcessor().
							   checkArticleTitle(reference.getArticleTitle());
			
			//set up with <i> and </i>
			title = "<i>" + title + "</i>"; 
			
			//publication place
			String publicationPlace = new ReferenceAttributeProcessor().
										  checkPublicationPlace(reference.getPublicationPlace());
			
			//publisher
			String publisher = new ReferenceAttributeProcessor().
								   checkPublisher(reference.getPublisher());
				
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
										 checkPublicationYear(reference.getPublicationYear());
			
			//create citation
			bibliographyText = authorNameText.trim() + " " +
							   publicationYear + ". " +
						   	   title + ". " +
						   	   publicationPlace + ": " + publisher +"."; 
			
		}
		
		//journal
		else{
		
			//author text
			String authorNameText = "";
			
			//empty author name
			if(reference.getAuthorName().trim().equals("")){
			
				//set
				authorNameText = "#enter author name#.";
			
			}
			
			//have author name
			else{
				
				//get author list
				ArrayList<Author> authorList = new ReferenceAttributeProcessor().getAuthorList(reference.getAuthorName());
				
				//single author
				if(authorList.size()==1){
					
					//get author
					Author author = authorList.get(0);
					
					//set surname text
					authorNameText = author.getSurname() + ", " + author.getFirstName() +"." ;
					
				}
				
				//multiple authors
				else{
					
					//iterate over list
					for(int i=0;i<authorList.size();i++){
						
						//get author
						Author author = authorList.get(i);
						
						//first author
						if(i==0){
					
							//set surname text
							authorNameText = author.getSurname() + ", " +
											 author.getFirstName()+". ";
							
						}
						
						//last author
						else if(i==(authorList.size()-1)){
							
							//set
							authorNameText = authorNameText + " and " +
											 author.getSurname() + " " +
											 author.getFirstName()+"."; 
							
						}
						
						//middle authors
						else{
							
							//set
							authorNameText = authorNameText + 
											 author.getSurname() + ", " +
											 author.getFirstName() + "., ";
							
						}
						
					}
					
				}
				
			}
			
			//article title
			String articleTitle = new ReferenceAttributeProcessor().
							   		  checkArticleTitle(reference.getArticleTitle());
			
			//journal title
			String journalTitle = new ReferenceAttributeProcessor().
	   		  						  checkJournalTitle(reference.getJournalTitle());
			
			//set up with <i> and </i>
			journalTitle = "<i>" + journalTitle + "</i>";
			
			//volume no
			String volumeNo = new ReferenceAttributeProcessor().
								  checkVolumeNo(reference.getVolumeNo());
			
			//publication year
			String publicationYear = new ReferenceAttributeProcessor().
									  	 checkPublicationYear(reference.getPublicationYear());
			
			//page numbers
			String pageNumbers = new ReferenceAttributeProcessor().
								 	 checkPageNo(reference.getPageNo());
			
			//create bibliography
			bibliographyText = authorNameText.trim() + " " + 
							   publicationYear + ". " + 	
						   	   "\"" + articleTitle + ".\" " +
						   	   journalTitle + " " +
						   	   volumeNo + ":" +
						   	   pageNumbers + ".";
			
		}
		
		//return
		return bibliographyText;
		
	}
	
	/**
	 * get reference txet according to refence type and reference style
	 * @param reference - reference object
	 * @param referenceType - citation or bibliography 
	 * @param styleType - hrvard, vancouver ...
	 * @return
	 */
	public String getReferenceText(Reference reference,String referenceType,String styleType){
		
		//reference text
		String referenceText = "";
		
		//citation
		if(referenceType.equals("citation")){
			
			//harvard style
			if(styleType.equals("harvard")){
				
				//set
				referenceText = new ReferenceGenerator().getHarwardCitation(reference, reference.getType());
				
			}
			
			//oxford
			else if(styleType.equals("oxford")){
						
				//set
				referenceText = new ReferenceGenerator().getOxfordCitation(reference, reference.getType());
				
			}
			
			//vancouver
			else if(styleType.equals("vancouver")){
				
				//set
				referenceText = new ReferenceGenerator().getVancouverBookReference(reference, reference.getType());
				
			}
			
			//chicago
			else if(styleType.equals("chicago")){
				
				//set
				referenceText = new ReferenceGenerator().getChicagoArtsCitation(reference, reference.getType());
				
			}
			
			//chicago_science
			else if(styleType.equals("chicago_science")){
				
				//set
				referenceText = new ReferenceGenerator().getChicagoScienceCitation(reference, reference.getType());
				
			}
			
			//mla
			else if(styleType.equals("mla")){
				
				//set
				referenceText = new ReferenceGenerator().getMLACitation(reference, reference.getType());
				
			}
			
			//mrha
			else if(styleType.equals("mrha")){
				
				//set
				referenceText = new ReferenceGenerator().getMRHACitation(reference, reference.getType());
				
			}
			
		}
		
		//bibliography
		else{
			
			//harvard style
			if(styleType.equals("harvard")){
				
				//set
				referenceText = new ReferenceGenerator().getHarvardBibliography(reference, reference.getType());
				
			}
			
			//oxford
			else if(styleType.equals("oxford")){
								
				//set
				referenceText = new ReferenceGenerator().getOxfordBibliography(reference, reference.getType());
				
			}
			
			//vancouver
			else if(styleType.equals("vancouver")){
				
				//set
				referenceText = new ReferenceGenerator().getVancouverBookReference(reference, reference.getType());
				
			}
			
			//chicago
			else if(styleType.equals("chicago")){
				
				//set
				referenceText = new ReferenceGenerator().getChicagoArtBibliography(reference, reference.getType());
				
			}
			
			//chicago_science
			else if(styleType.equals("chicago_science")){
				
				//set
				referenceText = new ReferenceGenerator().getChicagoScienceBibliography(reference, reference.getType());
				
			}
			
			//mla
			else if(styleType.equals("mla")){
				
				//set
				referenceText = new ReferenceGenerator().getMLABibliography(reference, reference.getType());
				
			}
			
			//mrha
			else if(styleType.equals("mrha")){
				
				//set
				referenceText = new ReferenceGenerator().getMRHABibliography(reference, reference.getType());
				
			}
			
		}
		
		//return 
		return referenceText;
		
	}
	
}