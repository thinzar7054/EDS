package Model;

public class EmployeeDetailsModel {
	private String emp_id;
	private String empName;
	private String email;
	private String phone;
	private String hiringDate;
	private boolean isActive;
	private boolean isAgreement;
	private boolean isManager;
	private String department;
	private String jobTitle;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(String hiringDate) {
		this.hiringDate = hiringDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public boolean isAgreement() {
		return isAgreement;
	}

	public void setAgreement(boolean agreement) {
		isAgreement = agreement;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean manager) {
		isManager = manager;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
