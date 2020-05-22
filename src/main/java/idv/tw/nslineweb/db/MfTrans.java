package idv.tw.nslineweb.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MF_TRANS")
public class MfTrans {

	@Id
	@Column(name = "TRANS_ID", length = 32)
	private String transId;

	@Column(name = "ORDER_ID", length = 32)
	private String orderId;

	@Column(name = "PACK_ID", length = 32)
	private String packId;

	@Column(name = "MF_AMOUNT")
	private long mfAmount;

	@Column(name = "MF_MATERIAL", length = 16)
	private String mfMaterial;

	@Column(name = "STATUS", length = 1)
	private String status;

	@Column(name = "CREATE_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDt;

	@Column(name = "CREATE_USER", length = 32)
	private String createUser;

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public long getMfAmount() {
		return mfAmount;
	}

	public void setMfAmount(long mfAmount) {
		this.mfAmount = mfAmount;
	}

	public String getMfMaterial() {
		return mfMaterial;
	}

	public void setMfMaterial(String mfMaterial) {
		this.mfMaterial = mfMaterial;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}
