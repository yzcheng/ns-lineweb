package idv.tw.nslineweb.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MfOrderService extends JpaRepository<MfOrder, String>, JpaSpecificationExecutor<MfOrder> {
	// 自定義 sql 語句
	@Query(value = "select * from mf_order where ship_date = ?1 and status = ?2", nativeQuery = true)
	MfOrder selectByUid(String Uid);

	/*
	 * 我們在這裡直接繼承 JpaRepository 這裡面已經有很多現成的方法 這也是JPA的一大優點
	 *
	 * （1） CrudRepository： 繼承 Repository，實現了一組 CRUD 相關的方法 （2）
	 * PagingAndSortingRepository： 繼承 CrudRepository，實現了一組分頁排序相關的方法
	 * （3）JpaRepository： 繼承 PagingAndSortingRepository，實現一組 JPA 規範相關的方法
	 * （4）JpaSpecificationExecutor： 實現條件查詢
	 */
}
