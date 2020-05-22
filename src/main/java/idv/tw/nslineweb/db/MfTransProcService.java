package idv.tw.nslineweb.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MfTransProcService extends JpaRepository<MfTransProc, String>, JpaSpecificationExecutor<MfTransProc> {

}