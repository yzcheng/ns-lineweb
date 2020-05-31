package idv.tw.nslineweb.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MfPackageProcService extends JpaRepository<MfPackageProc, String>, JpaSpecificationExecutor<MfPackageProc> {
	
}
