package appwolf.referenceme.pojos;

/**
 * @author eranga
 * pojo class to hold reference details
 */
public class Reference {

	int id;
	int projectId;
	String type;
	String defaultStyle;
	String authorName;
	String articleTitle;
	String journalTitle;
	String publicationYear;
	String publicationPlace;
	String publisher;
	String edition;
	String contributor;
	String journalNo;
	String volumeNo;
	String issueNo;
	String pageNo;
	
	public Reference(int id, int projectId, String type, String defaultStyle,
			String authorName, String articleTitle, String journalTitle,
			String publicationYear, String publicationPlace, String publisher,
			String edition, String contributor, String journalNo,
			String volumeNo, String issueNo, String pageNo) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.type = type;
		this.defaultStyle = defaultStyle;
		this.authorName = authorName;
		this.articleTitle = articleTitle;
		this.journalTitle = journalTitle;
		this.publicationYear = publicationYear;
		this.publicationPlace = publicationPlace;
		this.publisher = publisher;
		this.edition = edition;
		this.contributor = contributor;
		this.journalNo = journalNo;
		this.volumeNo = volumeNo;
		this.issueNo = issueNo;
		this.pageNo = pageNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDefaultStyle() {
		return defaultStyle;
	}
	public void setDefaultStyle(String defaultStyle) {
		this.defaultStyle = defaultStyle;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getJournalTitle() {
		return journalTitle;
	}
	public void setJournalTitle(String journalTitle) {
		this.journalTitle = journalTitle;
	}
	public String getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}
	public String getPublicationPlace() {
		return publicationPlace;
	}
	public void setPublicationPlace(String publicationPlace) {
		this.publicationPlace = publicationPlace;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getContributor() {
		return contributor;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	public String getJournalNo() {
		return journalNo;
	}
	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}
	public String getVolumeNo() {
		return volumeNo;
	}
	public void setVolumeNo(String volumeNo) {
		this.volumeNo = volumeNo;
	}
	public String getIssueNo() {
		return issueNo;
	}
	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
		
}
