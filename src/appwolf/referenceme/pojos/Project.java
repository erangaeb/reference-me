package appwolf.referenceme.pojos;

/**
 * @author eranga
 * POJO class to store project details
 */
public class Project {

	int id;
	String name;
	String defaultStyle;
	
	public Project(int id, String name, String defaultStyle) {
		super();
		this.id = id;
		this.name = name;
		this.defaultStyle = defaultStyle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultStyle() {
		return defaultStyle;
	}

	public void setDefaultStyle(String defaultStyle) {
		this.defaultStyle = defaultStyle;
	}
	
}
