package idv.tw.nslineweb.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MfOrderService extends JpaRepository<MfOrder, String>, JpaSpecificationExecutor<MfOrder> {

}
