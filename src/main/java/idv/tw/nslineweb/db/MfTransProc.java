package idv.tw.nslineweb.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MF_TRANS_PROC")
public class MfTransProc {

	@Id
	@Column(name = "PROC_ID", length = 32)
	private String procId;

	@Column(name = "TRANS_ID", length = 32)
	private String transId;

	@Column(name = "SRC_PROC_ID", length = 32)
	private String srcProcId;

	@Column(name = "EQP_TYPE", length = 8)
	private String eqpType;

	@Column(name = "TRANS_UNIT")
	private long transUnit;

	@Column(name = "CREATE_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDt;

	@Column(name = "CREATE_USER", length = 32)
	private String createUser;

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getSrcProcId() {
		return srcProcId;
	}

	public void setSrcProcId(String srcProcId) {
		this.srcProcId = srcProcId;
	}

	public String getEqpType() {
		return eqpType;
	}

	public void setEqpType(String eqpType) {
		this.eqpType = eqpType;
	}

	public long getTransUnit() {
		return transUnit;
	}

	public void setTransUnit(long transUnit) {
		this.transUnit = transUnit;
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
}
