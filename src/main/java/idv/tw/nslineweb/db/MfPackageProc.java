package idv.tw.nslineweb.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MF_PACKAGE_PROC")
public class MfPackageProc {

	@Id
	@Column(name = "PROC_ID", length = 32)
	private String procId;

	@Column(name = "PACK_ID", length = 32)
	private String packId;

	@Column(name = "SEQ_NO")
	private int seqNo;

	@Column(name = "CREATE_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDt;

	@Column(name = "CREATE_USER", length = 32)
	private String createUser;

	@Column(name = "UPDATE_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDt;

	@Column(name = "UPDATE_USER", length = 32)
	private String updateUser;

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}
