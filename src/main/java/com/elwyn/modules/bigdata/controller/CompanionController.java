package com.elwyn.modules.bigdata.controller;

import com.rainsoft.core.utils.Utils;
import com.rainsoft.model.User;
import com.rainsoft.model.dataFeedBack.DataQualityStats;
import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import com.rainsoft.model.machineInfo.MachineInfo;
import com.rainsoft.modules.bigdata.entity.IdentityContrastEntity;
import com.rainsoft.remoting.RpcFindCompanionService;
import com.rainsoft.service.dataFeedback.DataQualityService;
import com.rainsoft.spring.security.UserDetailsImpl;
import com.rainsoft.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller("同伴分析")
@RequestMapping("/companion")

public class CompanionController {
    
    //@Autowired
    //FindCompanionService findCompanionService;
    
    
    @Autowired
    RpcFindCompanionService rpcFindCompanionService;
    

    @Qualifier("dataerrorService")
    @Autowired
    DataQualityService dataQualityService;

    /**
     * 查找同伴
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping("/find/list")
    @ResponseBody
    public DataTablesResponse findList(@RequestBody DataTablesRequest<DataQualityStats> dataTablesRequest) {
        int type = Utils.parseInt(dataTablesRequest.getFormData().get("id"));

        if (type == 0) {
            DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
            dtres.setData(null);
            dtres.setRecordsTotal(0);
            dtres.setRecordsFiltered(0);
            return dtres;
        }
        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return rpcFindCompanionService.getCompanionInfo(user,dataTablesRequest);
    }

    @RequestMapping("/contrast/list")
    @ResponseBody
    public DataTablesResponse contrastList(@RequestBody DataTablesRequest<IdentityContrastEntity> dataTablesRequest) {

        int type = Integer.valueOf((String) dataTablesRequest.getFormData().get("id"));
        if (type == 0) {
            DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
            dtres.setData(null);
            dtres.setRecordsTotal(0);
            dtres.setRecordsFiltered(0);
            return dtres;
        }
        dataTablesRequest.setModeClass(IdentityContrastEntity.class);
		/*Collection<IdentityContrastEntity> data = identityContrastService.findListTraceAp(dataTablesRequest);
		long count = identityContrastService.countFindListTraceAp(dataTablesRequest);*/
        DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
        dtres.setData(null);
        dtres.setRecordsTotal(0);
        dtres.setRecordsFiltered(0);
        return null;
    }

    @RequestMapping(value = "/getEquipmentAll", method = RequestMethod.POST)
    @ResponseBody
    public List<MachineInfo> getEquipmentAll(@RequestBody Map<String, Object> map) {
        // 获取当前用户和城区
        User user = CurrentUser.getUser().getUser();
        String region = user.getRegion();
        Map<String, Object> data = new HashMap<>();
        if (map != null && !map.entrySet().isEmpty()) {
            data.putAll(map);
        }
        data.put("region", region);
        List<MachineInfo> machineInfoList = rpcFindCompanionService.getMachineInfoByServiceCode(data);
        return machineInfoList;
    }


}
	


