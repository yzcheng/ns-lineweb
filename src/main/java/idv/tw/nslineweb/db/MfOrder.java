package idv.tw.nslineweb.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MF_ORDER")
public class MfOrder {

	@Id
	@Column(name = "ORDER_ID", length = 32)
	private String orderId;

	@Column(name = "PROD_ID", length = 32)
	private String prodId;

	@Column(name = "PROD_SPEC", length = 64)
	private String prodSpec;

	@Column(name = "CUST_CODE", length = 16)
	private String custCode;

	@Column(name = "AMOUNT")
	private long amount;

	@Column(name = "SHIP_DATE")
	@Temporal(TemporalType.DATE)
	private Date shipDate;

	@Column(name = "STATUS", length = 1)
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdSpec() {
		return prodSpec;
	}

	public void setProdSpec(String prodSpec) {
		this.prodSpec = prodSpec;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MfOrder [orderId=" + orderId + ", prodId=" + prodId + ", prodSpec=" + prodSpec + ", custCode="
				+ custCode + ", amount=" + amount + ", shipDate=" + shipDate + ", status=" + status + ", createDt="
				+ createDt + ", createUser=" + createUser + ", updateDt=" + updateDt + ", updateUser=" + updateUser
				+ "]";
	}
}
