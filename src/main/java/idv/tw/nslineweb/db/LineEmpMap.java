package idv.tw.nslineweb.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LINE_EMP_MAP")
public class LineEmpMap {

	@Id
	@Column(name = "LINE_UID", length = 64, nullable = false)
	private String lineUid;

	@Column(name = "LINE_NAME", length = 64)
	private String lineName;

	@Column(name = "LINE_PIC_URL", length = 128)
	private String linePicUrl;

	@Column(name = "EMP_ID", length = 32)
	private String empId;

	@Column(name = "ROLE", length = 32)
	private String role;

	@Column(name = "STATUS", length = 1)
	private String status;

	@Column(name = "CREATE_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDt;

	@Column(name = "LAST_LOGIN_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDt;

	public String getLineUid() {
		return lineUid;
	}

	public void setLineUid(String lineUid) {
		this.lineUid = lineUid;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getLinePicUrl() {
		return linePicUrl;
	}

	public void setLinePicUrl(String linePicUrl) {
		this.linePicUrl = linePicUrl;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getLastLoginDt() {
		return lastLoginDt;
	}

	public void setLastLoginDt(Date lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

}
