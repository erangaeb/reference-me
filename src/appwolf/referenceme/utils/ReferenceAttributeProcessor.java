package appwolf.referenceme.utils;

import java.util.ArrayList;

import appwolf.referenceme.pojos.Author;

/**
 * @author eranga
 * class that define methods to parse reference attributes
 */
public class ReferenceAttributeProcessor {

	/**
	 * get authors list
	 * @param name
	 * @return author list
	 */
	public ArrayList<Author> getAuthorList(String name){
		
		//author list
		ArrayList<Author> authorList = new ArrayList<Author>();
		
		/*
		 * split and get author names
		 */
		//to hold splited values
		String[] authorNames;
		
		//split by ','
		authorNames = name.split(",");
		
		//iterate over authorNames array
		for(int i=0;i<authorNames.length;i++){
			
			//get author ane
			String authorName = authorNames[i].trim();
			
			/*
			 * split and get names in single author name 
			 */
			//to hold splitted values
			String[] names;
			
			//split by ' ' (space)
			names = authorName.split(" ");
			
			//no author name
			if(names.length==0){
				
				//empty author object
				Author author = new Author("", "", "");
				
			}
			
			//one name
			if(names.length==1){
				
				//new author with empty initials
				Author author = new Author(names[0].trim(), names[0].trim(), "");
				
				//add author to list
				authorList.add(author);
				
			}

			//more than one name
			else{
				
				//first name
				String firstName = names[0].trim(); 
				
				//surname
				String surname = names[names.length-1].trim();
				
				//initials of user
				String initials = "";
				
				//iterate over names
				for(int j=0;j<names.length-1;j++){
					
					//set initials
					initials = initials + Character.toString(names[j].trim().charAt(0));
					
				}
				
				//create new author
				Author author = new Author(firstName, surname, initials);
				
				//add author to list
				authorList.add(author);
				
			}
			
		}
		
		//return
		return authorList;
		
	}
	
	/**
	 * get initials of harvard book bibliography
	 * @param initials
	 * @return initial text
	 */
	public String getHarvardBookBibliographyInitials(String initial){
		
		//initials
		String initialText = "";
		
		//iterate over initials
		for(int i=0;i<initial.length();i++){
			
			//first
			if(i==0){
				
				//set
				initialText = initial.charAt(i) +". ";
				
			}
			
			//other initials
			else{
				
				//set
				initialText = initialText + initial.charAt(i) +". "; 
				
			}
			
		}
	
		//return
		return initialText;
		
	}
	
	/**
	 * check emptyness of author
	 * @param author
	 * @return author
	 */
	public String checkAuthor(String author){
		
		//empty
		if(author.equals("")){
			
			//set 
			author = "#enter author name#";
			
		}
		
		//return
		return author;
		
	}
	
	/**
	 * check emptyness of article title
	 * @param title
	 * @return title
	 */
	public String checkArticleTitle(String title){
		
		//empty
		if(title.equals("")){
			
			//set 
			title = "#enter article title#";
			
		}
		
		//return
		return title;
		
	}
	
	/**
	 * check emptyness of journal title
	 * @param title
	 * @return title
	 */
	public String checkJournalTitle(String title){
		
		//empty
		if(title.equals("")){
			
			//set 
			title = "#enter journal title#";
			
		}
		
		//return
		return title;
		
	}
	
	/**
	 * check emptyness of publication year
	 * @param year
	 * @return year
	 */
	public String checkPublicationYear(String year){
		
		//empty
		if(year.equals("")){
			
			//set 
			year = "#enter publication year#";
			
		}
		
		//return
		return year;
		
	}
	
	/**
	 * check emptyness of publication place
	 * @param place
	 * @return place
	 */
	public String checkPublicationPlace(String place){
		
		//empty
		if(place.equals("")){
			
			//set 
			place = "#enter publication place#";
			
		}
		
		//return
		return place;
		
	}
	
	/**
	 * check emptyness of publisher
	 * @param place
	 * @return place
	 */
	public String checkPublisher(String publisher){
		
		//empty
		if(publisher.equals("")){
			
			//set 
			publisher = "#enter publisher name#";
			
		}
		
		//return
		return publisher;
		
	}
	
	/**
	 * check emptyness of edition
	 * @param edition
	 * @return edition
	 */
	public String checkEdition(String edition){
		
		//handle 
		try{
		
			//empty
			if(edition.equals("")){
				
				//set
				edition = "#enter edition no#";
				
			}
			
			//have edition no
			else{
				
				//edition = 1 or 0
				if(edition.equals("1") || edition.equals("0")){
					
					//do nothing
					
				}
				
				//edition no greater than 1
				else{
					
					//handle parsing errors
					try{
						
						//edition no
						int editionNo = Integer.parseInt(edition);
						
						//set edition
						edition = editionNo + getEditionSuffix(editionNo);
						
					}
					
					//parsing error
					catch(Exception e){
						
						//set
						edition = "#enter edition no#";
						
					}
					
				}
				
				/*//length 1
				else if(edition.length()==1){
					
					//1 st
					if(edition.equals("1")){
						
						//set 
						edition = edition + "st";
						
					}
					
					//2nd edition
					else if(edition.equals("2")){
						
						//set
						edition = edition + "nd" ;
						
					}
					
					//3rd
					else if(edition.equals("3")){
						
						//set edition
						edition = edition + "rd";
						
					}
					
					//4th , 5th, 6th, 7th, 8th, 9th, 0th
					else {
						
						//set edition
						edition = edition + "th";
						
					}
					
				}
				
				//length 2
				else if(edition.length()==2){
					
					//edition greater than 19 
					if(Integer.parseInt(edition)>19){
						
						//last character
						char character = edition.charAt(edition.length()-1);
						
						//get last character of edition
						String lastCharacer = Character.toString(character);
						
						//1 st
						if(lastCharacer.equals("1")){
							
							//set 
							edition = edition + "st";
							
						}
						
						//2nd edition
						else if(lastCharacer.equals("2")){
							
							//set
							edition = edition + "nd" ;
							
						}
						
						//3rd
						else if(edition.equals("3")){
							
							//set edition
							edition = edition + "rd";
							
						}
						
						//4th , 5th, 6th, 7th, 8th, 9th, 10th
						else {
							
							//set edition
							edition = edition + "th";
							
						}
						
					}
					
					//edition 10 - 19
					else{
						
						//set edition
						edition = edition + "th";
						
					}
					
				}
				
				//length greater than 3
				else{
					
					//get last two digits
					String lastTwoDigits = edition.substring(edition.length()-2, edition.length());
					
					//last two digit number
					int lastTwodigitNumber = Integer.parseInt(lastTwoDigits);
					
					//0 - 9
					if(lastTwodigitNumber<10){
						
						//get last character of edition
						String lastCharacer = Integer.toString(lastTwodigitNumber);
						
						//1 st
						if(lastCharacer.equals("1")){
							
							//set 
							edition = edition + "st";
							
						}
						
						//2nd edition
						else if(lastCharacer.equals("2")){
							
							//set
							edition = edition + "nd" ;
							
						}
						
						//3rd
						else if(edition.equals("3")){
							
							//set edition
							edition = edition + "rd";
							
						}
						
						//4th , 5th, 6th, 7th, 8th, 9th, 0th
						else {
							
							//set edition
							edition = edition + "th";
							
						}
						
					}
					
					//greater than 19
					else if(lastTwodigitNumber>19){
						
						//last character
						char character = edition.charAt(edition.length()-1);
						
						//get last character of edition
						String lastCharacer = Character.toString(character);
						
						//1 st
						if(lastCharacer.equals("1")){
							
							//set
							edition = edition + "st";
							
						}
						
						//2nd edition
						else if(lastCharacer.equals("2")){
							
							//set
							edition = edition + "nd" ;
							
						}
						
						//3rd
						else if(edition.equals("3")){
							
							//set edition
							edition = edition + "rd";
							
						}
						
						//4th , 5th, 6th, 7th, 8th, 9th, 10th
						else {
							
							//set edition
							edition = edition + "th";
							
						}
						
					}
					
					//10 - 19
					else{
						
						//set edition
						edition = edition + "th";
						
					}
					
				}
				
				//greater than 1
				else{
					
					//last character
					char character = edition.charAt(edition.length()-1);
					
					//get last character of edition
					String lastCharacer = Character.toString(character);
					
					//1 st
					if(lastCharacer.equals("1")){
						
						//set 
						edition = edition + "st";
						
					}
					
					//2nd edition
					else if(lastCharacer.equals("2")){
						
						//set
						edition = edition + "nd" ;
						
					}
					
					//3rd
					else if(edition.equals("3")){
						
						//set edition
						edition = edition + "rd";
						
					}
					
					//4th , 5th, 6th, 7th, 8th, 9th, 10th
					else {
						
						//set edition
						edition = edition + "th";
						
					}
					
				}*/
				
			}
			
		}
		
		catch(Exception e){
			
			//set
			edition = "#enter edition no#";
			
		}
		
		//return
		return edition;
		
	}
	
	/**
	 * get suffix of edition no
	 * @param value
	 * @return suffix
	 */
	public String getEditionSuffix(int editionNo) {
		
		//hundread 
        int hunRem = editionNo % 100;
        
        //ten 
        int tenRem = editionNo % 10;
        
        //equals to 10
        if (hunRem - tenRem == 10) {
        	
        	//return
            return "th";
            
        }
        
        /*
         * calculate suffix
         */
        switch (tenRem) {
        
	        case 1:
	        	
	        	//return
                return "st";
	                
	        case 2:
	        	
	        	//return
                return "nd";
	                
	        case 3:
	        	
	        	//return
	        	return "rd";
	        
	        default:
	        	
	        	//return
	        	return "th";
	                
    	}
        
	}
	
	/**
	 * check emptyness of page no
	 * @param pageno
	 * @return pageno
	 */
	public String checkPageNo(String pageNo){
		
		//empty
		if(pageNo.equals("")){
			
			//set
			pageNo = "#enter page no#";
			
		}
		
		//return
		return pageNo;
		
	}
	
	/**
	 * check emptyness of volume no
	 * @param volumeno
	 * @return volumeno
	 */
	public String checkVolumeNo(String volumeNo){
		
		//empty
		if(volumeNo.equals("")){
			
			//set
			volumeNo = "#enter volume no#";
			
		}
		
		//return
		return volumeNo;
		
	}
	
	/**
	 * check emptyness of issue no
	 * @param issueno
	 * @return issueno
	 */
	public String checkIssueNo(String issueNo){
		
		//empty
		if(issueNo.equals("")){
			
			//set
			issueNo = "#enter issue no#";
			
		}
		
		//return
		return issueNo;
		
	}
	
	/**
	 * check emptyness of journal no
	 * @param journalNo
	 * @return journalNo
	 */
	public String checkJournalNo(String journalNo){
		
		//empty
		if(journalNo.equals("")){
			
			//set
			journalNo = "#enter journal no#";
			
		}
		
		//return
		return journalNo;
		
	}
	
	/**
	 * get authors surname list
	 * @param name
	 * @param name list
	 *//*
	public ArrayList<String> getAuthorsSurname(String name){
		
		//author name list
		ArrayList<String> authorsSurNameList = new ArrayList<String>();
		
		
		 * split and get author names
		 
		//to hold splited values
		String[] authorNames;
		
		//split by ','
		authorNames = name.split(",");
		
		//iterate over authorNames array
		for(int i=0;i<authorNames.length;i++){
		
			//get author ane
			String authorName = authorNames[i].trim();
			
			
			 * split and get names in single author name 
			 
			//to hold splitted values
			String[] names;
			
			//split by ' ' (space)
			names = authorName.split(" ");
			
			//add last name to author surname list
			authorsSurNameList.add(names[names.length-1].trim());
			
		}
		
		//return
		return authorsSurNameList;
		
	}
	
	*//**
	 * get authors last name list
	 * @param name
	 * @return name list
	 *//*
	public ArrayList<String> getAuthorsFirstName(String name){
		
		//author name list
		ArrayList<String> authorsFirstNameList = new ArrayList<String>();
		
		
		 * split and get author names
		 
		//to hold splited values
		String[] authorNames;
		
		//split by ','
		authorNames = name.split(",");
		
		//iterate over authorNames array
		for(int i=0;i<authorNames.length;i++){
		
			//get author ane
			String authorName = authorNames[i].trim();
			
			
			 * split and get names in single author name
			 
			//to hold splitted values
			String[] names;
			
			//split by ' ' (space)
			names = authorName.split(" ");
			
			//have names
			if(names.length>0){
			
				//add last name to author surname list
				authorsFirstNameList.add(names[0].trim());
			
			}
			
		}
		
		//return
		return authorsFirstNameList;
		
	}
	
	*//**
	 * get author initial list
	 * @param name
	 * @return initial list
	 *//*
	public ArrayList<String> getAuthorIntialList(String name){

		//initial list
		ArrayList<String> authorsInitialList = new ArrayList<String>();
		
		
		 * split and get author names
		 
		//to hold splited values
		String[] authorNames;
		
		//split by ','
		authorNames = name.split(",");
		
		//iterate over authorNames array
		for(int i=0;i<authorNames.length;i++){
		
			//get author ane
			String authorName = authorNames[i].trim();
			
			
			 * split and get names in single author name
			 
			//to hold splitted values
			String[] names;
			
			//split by ' ' (space)
			names = authorName.split(" ");
			
			//have names
			if(names.length>1){
			
				//iterate over names
				for(int j=0;j<names.length-2;j++){
					
					//get initial
					String initial = Character.toString(names[i].trim().charAt(0));
			
					//add to list
					authorsInitialList.add(initial);
					
				}
				
			}
			
		}
		
		//return
		return authorsInitialList;
		
	}*/
	
}