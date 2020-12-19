package idv.tw.nslineweb;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import idv.tw.nslineweb.db.LineEmpMap;
import idv.tw.nslineweb.db.LineEmpMapService;

@SpringBootTest
public class NsLinewebApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private LineEmpMapService lineEmpMapService;

	@Test
	public void testQuery() {
		LineEmpMap lineEmp = lineEmpMapService.findById("testid").orElse(new LineEmpMap());
		if (StringUtils.isBlank(lineEmp.getLineUid())) {
			lineEmp.setLineUid("testid");
			lineEmp.setLineName("Kenny Chen");
			lineEmp.setStatus("W");
			lineEmp.setCreateDt(new Date());
			lineEmp.setLastLoginDt(new Date());
			lineEmpMapService.saveAndFlush(lineEmp);
		}
		lineEmpMapService.delete(lineEmp);
	}
}
