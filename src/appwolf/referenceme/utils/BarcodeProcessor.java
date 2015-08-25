package appwolf.referenceme.utils;

/**
 * @author eranga
 * functions to process barcode and generate isbn no of issn no
 */
public class BarcodeProcessor {

	/**
	 * get isbn from barcode
	 * @param barcode
	 * @return
	 */
	public String getISBN(String barcode){
		
		//isbn no
		String isbn="";
		
		//handle number format exceptions
		try{
		
			//use to generate isbn
			String temp = barcode.substring(3, 12);
			
			//sum
			int sum = 0;
			
			//keep track with multiplier 
			int j = 10;
			
			System.out.println(temp);
			
			//iterate over temp
			for(int i=0;i<temp.length();i++){
				
				//current character
				Character currentCharacter = temp.charAt(i); 
				
				//int value of current character
				int currentValue = Integer.parseInt(currentCharacter.toString());
				
				System.out.println(currentValue + ", " + j);
				
				//set sum
				sum = sum + (currentValue*j);
				
				//decrease multiplier
				j--;
				
			}
			
			//modeules of sum
			int modeulas = sum % 11;
			
			//last digit of isbn
			int lastDigit = 11 - modeulas;
			
			System.out.println("sum " + sum);
			
			System.out.println("last digit " +lastDigit);
			
			//last digit is 10
			if(lastDigit==10){
				
				//set isbn
				isbn = temp + 'x';
				
			}
			
			//last digit 11
			else if(lastDigit==11){
				
				//set isbn
				isbn = temp + '0';
				
			}
			
			//last digit less than 10
			else{
				
				//set isbn
				isbn = temp + lastDigit;
				
			}
		
		}
		
		//error
		catch(Exception e){
			
			System.out.println(e);
			
		}
		
		//return
		return isbn;
		
	}
	
	/**
	 * get issn from barcode
	 * @param barcode
	 * @return
	 */
	public String getISSN(String barcode){
		
		//isbn no
		String issn="";
		
		//handle number format exceptions
		try{
		
			//use to generate isbn
			String temp = barcode.substring(3, 10);
			
			//sum
			int sum = 0;
			
			//keep track with multiplier 
			int j = 8;
			
			System.out.println(temp);
			
			//iterate over temp
			for(int i=0;i<temp.length();i++){
				
				//current character
				Character currentCharacter = temp.charAt(i); 
				
				//int value of current character
				int currentValue = Integer.parseInt(currentCharacter.toString());
				
				System.out.println(currentValue + ", " + j);
				
				//set sum
				sum = sum + (currentValue*j);
				
				//decrease multiplier
				j--;
				
			}
			
			//modeules of sum
			int modeulas = sum % 11;
			
			//last digit of isbn
			int lastDigit = 11 - modeulas;
			
			//last digit is 10
			if(lastDigit==10){
				
				//set isbn
				issn = temp + 'x';
				
			}
			
			//last digit less than 10
			else{
				
				//set isbn
				issn = temp + lastDigit;
				
			}
		
		}
		
		//error
		catch(Exception e){
			
			System.out.println(e);
			
		}
		
		//return
		return issn;
		
	}
	
}
