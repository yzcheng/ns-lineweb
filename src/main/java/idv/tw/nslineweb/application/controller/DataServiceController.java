package idv.tw.nslineweb.application.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import idv.tw.nslineweb.db.LineEmpMapService;
import idv.tw.nslineweb.db.MfOrder;
import idv.tw.nslineweb.db.MfOrderService;
import idv.tw.nslineweb.db.MfPackageService;
import idv.tw.nslineweb.db.MfTrans;
import idv.tw.nslineweb.db.MfTransProc;
import idv.tw.nslineweb.db.MfTransProcService;
import idv.tw.nslineweb.db.MfTransService;

@CrossOrigin
@RestController
public class DataServiceController {

    @Value("${line.bot.id}")
    private String botId;
	
	@Autowired
	LineEmpMapService lineEmpMapService;
	
	@Autowired
	MfOrderService mfOrderService;

	@Autowired
	MfTransService mfTransService;

	@Autowired
	MfTransProcService mfTransProcService;

	@Autowired
	MfPackageService mfPackageService;
	
	@RequestMapping("service/insertOrder")
	public MfOrder insertOrder(HttpSession httpSession, @RequestBody MfOrder order) throws Exception {
		order.setStatus("A");
		//order.setCreateUser("");
		mfOrderService.save(order);
		System.out.println("insert order...successful => " + order.getOrderId());

		return order;
	}
	
	@RequestMapping("service/deleteOrder")
	public MfOrder deleteOrder(HttpSession httpSession, @RequestBody MfOrder order)  throws Exception{
		mfOrderService.delete(order);
		System.out.println("delete order...successful => " + order.getOrderId());

		return order;
	}
	
	@RequestMapping("service/findOrdersBylastN")
	public List<MfOrder> findOrdersBylastN(HttpSession httpSession, @RequestParam(name="lastN") int lastN, @RequestParam(name="status") String status) throws Exception{
		return mfOrderService.findAll();
	}
	
	private enum MainTransStatus {
		
		INIT("Init"), FORMED("Formed"), SINTERED("Sintered"), GROUND("Ground");
		
		private String dbVal;

		private MainTransStatus(String dbVal) {
			this.dbVal = dbVal;
		}
		
	}
	@RequestMapping("service/insertTrans")
	public MfTrans insertTrans(HttpSession httpSession, @RequestBody MfTrans mainTrans) throws Exception {
		int seq = findTransByOrderId(httpSession, mainTrans.getOrderId()).size();
		
		//mainTrans.setCreateUser("");
		mainTrans.setTransId(String.format("%s-%s", mainTrans.getOrderId(), seq));
		mainTrans.setStatus(MainTransStatus.INIT.dbVal);
		mfTransService.save(mainTrans);
		System.out.println(String.format("insert main trans...successful => %s(from order %s)", mainTrans.getTransId(), mainTrans.getOrderId()));

		//TODO the transProcId will assign by user to line paper
		MfTransProc forming = new MfTransProc(mainTrans.getTransId() + "-01", mainTrans.getTransId() , 1, new Date(), mainTrans.getCreateUser());
		MfTransProc sintering = new MfTransProc(mainTrans.getTransId() + "-01", mainTrans.getTransId(), 2, new Date(), mainTrans.getCreateUser());
		MfTransProc grinding = new MfTransProc(mainTrans.getTransId() + "-01", mainTrans.getTransId(), 3, new Date(), mainTrans.getCreateUser());
		
		mfTransProcService.saveAll(Arrays.asList(forming, sintering, grinding));
		System.out.println(String.format("insert sub trans...successful => %s(from order %s)", forming.getProcId(), mainTrans.getTransId()));
		System.out.println(String.format("insert sub trans...successful => %s(from order %s)", sintering.getProcId(), mainTrans.getTransId()));
		System.out.println(String.format("insert sub trans...successful => %s(from order %s)", grinding.getProcId(), mainTrans.getTransId()));
		
		//TODO the returns will include transId & subProcId list.
		return mainTrans;
	}
	
	@RequestMapping("service/deleteTrans")
	public MfTrans deleteTrans(HttpSession httpSession, @RequestBody MfTrans mainTrans)  throws Exception{
		mfTransService.delete(mainTrans);
		System.out.println(String.format("delete main trans...successful => %s(from order %s)", mainTrans.getTransId(), mainTrans.getOrderId()));
		return mainTrans;
	}
	
	@RequestMapping("service/findTransByOrderId")
	public List<MfTrans> findTransByOrderId(HttpSession httpSession, @RequestParam(name="orderId") String orderId) throws Exception{
		Example<MfTrans> example = Example.of(MfTrans.from(orderId));
		return mfTransService.findAll(example);
	}

	private class XXXX{
		String formType;
		String id;
		List<String> subIds;
	}
	@RequestMapping("service/createBotEntryUrl")
	public String createBotEntryUrl(HttpSession httpSession, @RequestBody XXXX xxx) throws Exception{
		return String.format("https://line.me/R/oaMessage/@%s/?[%s]%s&%s<--請勿刪除[]內的訊息", botId, xxx.formType, xxx.id, xxx.subIds);
	}
	
}
