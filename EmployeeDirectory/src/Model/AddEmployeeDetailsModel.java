package Model;

import java.sql.Date;
import java.time.LocalDate;

public class AddEmployeeDetailsModel {
	private String emp_id;
	private String empName;
	private String phone;
	private String email;
	private Date hiringDate;
	private boolean isManager;
	private boolean isActive;
	private boolean isAgreement;
	private String dep_id;
	private String pos_id;
	private String password;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean b) {
		this.isManager = b;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean b) {
		this.isActive = b;
	}

	public boolean isAgreement() {
		return isAgreement;
	}

	public void setAgreement(boolean b) {
		this.isAgreement = b;
	}

	public String getDep_id() {
		return dep_id;
	}

	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}

	public String getPos_id() {
		return pos_id;
	}

	public void setPos_id(String pos_id) {
		this.pos_id = pos_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
