package appwolf.referenceme.pojos;

public class Author {

	String firstName;
	String surname;
	String initials;
	
	public Author(String firstName, String surname, String initials) {
		super();
		this.firstName = firstName;
		this.surname = surname;
		this.initials = initials;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	
}
