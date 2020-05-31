package idv.tw.nslineweb.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MfPackageService  extends JpaRepository<MfPackage, String>, JpaSpecificationExecutor<MfPackage> {

}
