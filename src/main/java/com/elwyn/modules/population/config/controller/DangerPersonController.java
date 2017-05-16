package com.elwyn.modules.population.config.controller;

import com.rainsoft.core.controller.BaseController;
import com.rainsoft.core.paging.Page;
import com.rainsoft.modules.population.config.entity.DangerPerson;
import com.rainsoft.modules.population.config.entity.DangerPersonGroup;
import com.rainsoft.modules.population.config.entity.DangerPersonResultEntity;
import com.rainsoft.modules.population.config.service.IDangerPersonGroupService;
import com.rainsoft.modules.population.config.service.IDangerPersonService;
import com.rainsoft.utils.CurrentUser;
import com.rainsoft.utils.HandleExcel;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高危人群库管理Controller
 *
 * @author Sugar
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "/population/config/danger/person")
public class DangerPersonController extends BaseController {

    @Autowired
    private IDangerPersonService dangerPersonService;
    @Autowired
    private IDangerPersonGroupService dangerPersonGroupService;

    @ModelAttribute
    public DangerPerson get(@RequestParam(required = false) String id) {
        DangerPerson entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = dangerPersonService.getById(id);
        }
        if (entity == null) {
            entity = new DangerPerson();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(DangerPerson dangerPerson, HttpServletRequest request, Model model) {
        Page<DangerPerson> page = dangerPersonService.findPage(dangerPerson, getInt(request, "pageNo", 1), getInt(request, "pageSize", 10));
        model.addAttribute("dangerPerson", dangerPerson);
        model.addAttribute("page", page);
        model.addAttribute("groupList", dangerPersonGroupService.findList(new DangerPersonGroup()));
        return "population/config/dangerPersonList";
    }

    @RequestMapping(value = "form")
    public String form(DangerPerson dangerPerson, Model model) {
        model.addAttribute("dangerPerson", dangerPerson);
        model.addAttribute("groupList", dangerPersonGroupService.findList(new DangerPersonGroup()));
        return "population/config/dangerPersonForm";
    }

    @RequestMapping(value = "validateImsi")
    @ResponseBody
    public boolean validateImsi(String imsi, String id) {
        DangerPerson dangerPerson = new DangerPerson();
        dangerPerson.setId(id);
        dangerPerson.setImsi(imsi);
        return dangerPersonService.exists(dangerPerson);
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public String save(DangerPerson dangerPerson, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, dangerPerson)) {
            String msg = (String) model.asMap().get("message");
            if (msg == null) {
                return "数据输入有误";
            }
            return msg;
        }
        dangerPerson.setCreateBy(CurrentUser.get().getId());
        try {
            if (dangerPersonService.save(dangerPerson)) {
                return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "保存失败";
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(String id) {
        try {
            int row = dangerPersonService.deleteById(id);
            if (row > 0) {
                return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "删除失败";
    }

    @RequestMapping(value = "batchDelete")
    @ResponseBody
    public String batchDelete(String ids) {
        try {
            int count = 0;
            String[] id = ids.substring(0, (ids.length() - 1)).split(",");
            if (id != null) {
                for (int i = 0; i < id.length; i++) {
                    int row = dangerPersonService.deleteById(id[i]);
                    if (row > 0) {
                        count++;
                    }
                }
            }
            if (count > 0) {
                return "0";
            } else {
                return "删除失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "删除失败";
    }

    /**
     * 读取上传的文件
     *
     * @param genePicPath
     * @param originalFilename
     * @return
     */
    public String importData(String genePicPath, String originalFilename) throws IOException {
        List<DangerPerson> dangerPersons = new ArrayList<>();
        String file = genePicPath + File.separator + originalFilename;
        InputStream is = new FileInputStream(file);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        int count = 0;
        DangerPersonResultEntity dangerPersonResult = new DangerPersonResultEntity();
        List<Map<String, String>> errorInfos = null;
        try {
            dangerPersonResult = HandleExcel.readXls(genePicPath, originalFilename);
            dangerPersons = dangerPersonResult.getDangerPersons();
            errorInfos = dangerPersonResult.getErrorInfo();
            if (dangerPersons.size() == 0) {
                return "上传文件数据异常";
            }
            for (int i = 0; i < dangerPersons.size(); i++) {
                handleDangerPersonGroup(dangerPersons.get(i));
                dangerPersons.get(i).setCreateBy(CurrentUser.get().getId());
                try {
                    boolean dangerPerson = dangerPersonService.exists(dangerPersons.get(i));
                    if (!dangerPerson) {
                        if (dangerPersonService.save(dangerPersons.get(i))) {
                            count++;
                        }
                    }else{
                        Map<String,String> exists = new HashMap<>();
                        exists.put("errorRow",dangerPersons.get(i).getRownum()+"");
                        exists.put("imsi","当前imsi已存在");
                        errorInfos.add(exists);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        hssfWorkbook.close();
        is.close();
        // 封装返回信息
        String result = "有效数据" + dangerPersonResult.getTotalRow() + "条(成功" + count + "条,失败" + (dangerPersonResult.getTotalRow() - count) + "条)。<br/>";
        int size = errorInfos.size();
        if (size > 0) {
            result = result + "错误说明 :<br/>";
            for (int i = 0; i < size; i++) {
                Map<String, String> errorinfo = errorInfos.get(i);
                if (errorinfo.get("errorRow") != null) {
                    result = result + "第" + errorinfo.get("errorRow") + "行:";
                    if (errorinfo.get("imsi") != null) {
                        result = result + errorinfo.get("imsi");
                    }
                    if (errorinfo.get("phoneNo") != null) {
                        result = result + errorinfo.get("phoneNo");
                    }
                    if (errorinfo.get("idNo") != null) {
                        result = result + errorinfo.get("idNo");
                    }
                    result = result +";<br/>";
                }
            }
        }
        return result;
    }

    /**
     * 处理危险人员的危险分组
     *
     * @param dangerPerson
     * @return
     */
    private DangerPerson handleDangerPersonGroup(DangerPerson dangerPerson) {
        DangerPersonGroup dangerPersonGroup = null;
        if (!StringUtils.isEmpty(dangerPerson.getGroupName())) {
            String[] groupNames = StringUtils.split(dangerPerson.getGroupName(), ",");
            String groupId = "";
            for (String groupName : groupNames) {
                String gn = groupName.trim();
                dangerPersonGroup = dangerPersonGroupService.getByName(gn);
                if (dangerPersonGroup == null) {

                    DangerPersonGroup dpgSave = new DangerPersonGroup();
                    dpgSave.setName(gn);
                    dangerPersonGroupService.save(dpgSave);

                    DangerPersonGroup dgp = dangerPersonGroupService.getByName(gn);
                    groupId += dgp.getId() + ",";
                } else {
                    groupId += dangerPersonGroup.getId() + ",";
                }
                dangerPerson.setGroupId(groupId.substring(0, groupId.length() - 1));
            }
        }
        return dangerPerson;
    }

    /**
     * 上传文件
     *
     * @param myfile
     * @param request
     * @return
     */
    @RequestMapping(value = "uploadFile", method = RequestMethod.POST, produces = "text/html;charset=UTF-8;")
    @ResponseBody
    public String importExcelFile(@RequestParam MultipartFile myfile, HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (myfile.isEmpty()) {
            map.put("result", "error");
            map.put("msg", "上传文件为空");
        } else {
            String originalFilename = myfile.getOriginalFilename();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
            if (!fileType.isEmpty()) {
                if (!fileType.equals("xls") && !fileType.equals("xlsx")) {
                    map.put("result", "error");
                    map.put("msg", "上传文件格式不匹配");
                } else {
                    String fileBaseName = FilenameUtils.getBaseName(originalFilename);
                    String floderName = fileBaseName + "_" + System.currentTimeMillis();
                    try {
                        String genePicPath = request.getSession().getServletContext().getRealPath("/upload/" + floderName);
                        //把上传的文件放到服务器的文件夹下
                        FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(genePicPath, originalFilename));
                        String result = importData(genePicPath, originalFilename);
                        map.put("result", "0");
                        map.put("msg", result);
                    } catch (Exception e) {
                        map.put("result", "error");
                        map.put("msg", e.getMessage());
                    }
                }
            }
        }
        String result = String.valueOf(JSONObject.fromObject(map));
        return result;
    }

    /**
     * 下载文件模板
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/download")
    public void download(HttpServletResponse response, HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/model/dangerPersonModel.xls");

        File file = new File(filePath);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] b = new byte[1024];
        int len = 0;
        try {
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();

            response.setContentType("application/force-download");
            String filename = "dangerPersonModel.xls";
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.setContentLength((int) file.length());

            while ((len = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream = null;
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream = null;
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}