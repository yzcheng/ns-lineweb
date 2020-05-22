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
	@Column(name = "LINE_UID", length=64, nullable=false)
	private String lineUid;

	@Column(name = "DISPLAY_NAME", length=64)
	private String displayName;

	@Column(name = "EMP_ID", length=32)
	private String empId;

	@Column(name = "STATUS", length=1)
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
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
