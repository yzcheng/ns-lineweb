package idv.tw.nslineweb.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MF_PACKAGE")
public class MfPackage {
	@Id
	@Column(name = "PACK_ID", length = 32)
	private String packId;

	@Column(name = "ORDER_ID", length = 32)
	private String orderId;

	@Column(name = "DATE_CODE", length = 32)
	private String dateCode;

	@Column(name = "LOT_NUM", length = 32)
	private String lotNum;

	@Column(name = "PASS_AMOUNT")
	private long passAmount;

	@Column(name = "STATUS", length = 16)
	private String status;

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

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDateCode() {
		return dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
	}

	public long getPassAmount() {
		return passAmount;
	}

	public void setPassAmount(long passAmount) {
		this.passAmount = passAmount;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
