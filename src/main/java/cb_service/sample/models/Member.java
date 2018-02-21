package cb_service.sample.models;

public class Member {

	private long memberId;
	private String name;
	private String phoneNumber;
	private String email;
	private Status status;
	
	public Member(long memberId, String name, String phoneNumber, String email) {
		this.memberId = memberId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
