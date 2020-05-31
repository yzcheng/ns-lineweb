package idv.tw.nslineweb.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LINE_LOG")
public class LineLog {
	
	@Id
	@Column(name = "LINE_UID", length = 64)
	private String lineUid; 
	
	@Column(name = "ID", length = 32)
	private String id;

	@Column(name = "STATUS", length = 16)
	private String status;

	public String getLineUid() {
		return lineUid;
	}

	public void setLineUid(String lineUid) {
		this.lineUid = lineUid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
