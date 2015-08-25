package appwolf.referenceme.utils;

import java.util.Comparator;

import appwolf.referenceme.pojos.Reference;

/**
 * @author eranga
 * compare class of reference
 */
public class ReferenceComparator implements Comparator<Reference>{

	/**
	 * comparing method
	 */
	@Override
	public int compare(Reference object1, Reference object2) {
		
		//compare
		return object1.getAuthorName().compareToIgnoreCase(object2.getAuthorName());
		
	}

}
