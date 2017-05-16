package com.elwyn.modules.bigdata.controller.hbase;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.model.bigdata.ComparisonPlaceRequest;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.model.functiontree.Function;
import com.rainsoft.modules.bigdata.entity.IdentityContrastEntity;
import com.rainsoft.remoting.RpcComparisonHbaseService;
import com.rainsoft.remoting.RpcIdentityContrasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Name com.rainsoft.modules.bigdata.controller.hbase.ContrastController
 * @Description 比对分析
 * 
 * @Author Sugar
 * @Version 2017年1月3日 上午11:29:14
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller("HbaseContrast")
@RequestMapping("/bigdata/hbase/contrast")
public class ContrastController extends BaseController {
	
//	@Autowired
//	private IdentityContrasService identityContrastService;
	
	@Autowired
	private RpcIdentityContrasService rpcIdentityContrastService;
	
	
	
//	@Autowired
//	private ComparisonHbaseService comparisonService;
	
	@Autowired
	private RpcComparisonHbaseService rpcComparisonService;

	@RequestMapping("/person")
	public String person() {
		return "bigdata/hbase/contrast/person";
	}

	@RequestMapping("/place")
	public String place() {
		return "bigdata/hbase/contrast/place";
	}
	
	@RequestMapping("/area")
	public String area() {
		return "bigdata/hbase/contrast/area";
	}

	/**
	 * 身份比对
	 * 
	 * @param dataTablesRequest
	 * @return
	 */
	@RequestMapping("/person/list")
	@ResponseBody
	public DataTablesResponse contrastPerson(@RequestBody DataTablesRequest<IdentityContrastEntity> dataTablesRequest) {
		int id = Integer.valueOf((String) dataTablesRequest.getFormData().get("id"));
		if (id == 0) {
			DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
			dtres.setData(null);
			dtres.setRecordsTotal(0);
			dtres.setRecordsFiltered(0);
			return dtres;
		}
		dataTablesRequest.setModeClass(IdentityContrastEntity.class);
		return rpcIdentityContrastService.findListTraceAp(dataTablesRequest);
	}
	
	@ResponseBody
	@RequestMapping(value="/place/list", method= RequestMethod.POST)
	@Function("场所比对列表查询")
	public DataTablesResponse contrastPlace(@RequestBody DataTablesRequest<ComparisonPlaceRequest>  dataTablesRequest){
		return rpcComparisonService.place(dataTablesRequest);
	}
}
