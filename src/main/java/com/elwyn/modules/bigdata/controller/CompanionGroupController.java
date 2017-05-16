package com.elwyn.modules.bigdata.controller;

import com.rainsoft.model.datatables.DataTablesRequest;
import com.rainsoft.model.datatables.DataTablesResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Name com.rainsoft.modules.bigdata.controller
 * @Description
 * @Author Elwyn
 * @Version 2016/12/27 9:48
 * @Copyright 上海云辰信息科技有限公司
 */
@Controller("同伴归类")
@RequestMapping("/companionGroupNew")
public class CompanionGroupController {

    @RequestMapping("/list")
    @ResponseBody
    public DataTablesResponse getCompanionGroupList(@RequestBody DataTablesRequest dataTablesRequest){
        DataTablesResponse dtres = new DataTablesResponse(dataTablesRequest);
        dtres.setData(null);
        dtres.setRecordsTotal(0);
        dtres.setRecordsFiltered(0);
        return dtres;
    }
}
