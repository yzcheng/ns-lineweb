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

	@Column(name = "SEQ_NO")
	private int seqNo;

	@Column(name = "EQP_TYPE", length = 8)
	private String eqpType;

	@Column(name = "TRANS_UNIT")
	private long transUnit;

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

	@Column(name = "QC_RESULT", length = 8)
	private String qcResult;

	@Column(name = "QC_COMMENT", length = 255)
	private String qcComment;

	@Column(name = "QC_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date qcDt;

	@Column(name = "QC_USER", length = 32)
	private String qcUser;

	public MfTransProc(String procId, String transId, int seqNo, Date createDt, String createUser) {
		super();
		this.procId = procId;
		this.transId = transId;
		this.seqNo = seqNo;
		this.createDt = createDt;
		this.createUser = createUser;
	}

	public MfTransProc() {
		super();
	}

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

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
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

	public String getQcResult() {
		return qcResult;
	}

	public void setQcResult(String qcResult) {
		this.qcResult = qcResult;
	}

	public String getQcComment() {
		return qcComment;
	}

	public void setQcComment(String qcComment) {
		this.qcComment = qcComment;
	}

	public Date getQcDt() {
		return qcDt;
	}

	public void setQcDt(Date qcDt) {
		this.qcDt = qcDt;
	}

	public String getQcUser() {
		return qcUser;
	}

	public void setQcUser(String qcUser) {
		this.qcUser = qcUser;
	}

}
