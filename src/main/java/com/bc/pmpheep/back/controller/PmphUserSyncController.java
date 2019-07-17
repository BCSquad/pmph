package com.bc.pmpheep.back.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.service.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.itrus.utils.Decrypt;
import com.mysql.jdbc.StringUtils;
import com.sun.deploy.net.HttpUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.security.Key;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/pmphWeb")
@SuppressWarnings({"rawtypes", "unchecked"})
public class PmphUserSyncController {

    @Autowired
    PmphUserService pmphUserService;

    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    PmphDepartmentService pmphDepartmentService;
    @Autowired
    PmphRoleService pmphRoleService;

    private static final String BUSSINESS_TYPE = "同步社内用户";
    private static final String PERSON = "person";    //同步类型为用户
    private static final String ORGANIZATIONAL_UNIT = "organizationalUnit";  //同步类型为部门
    private static final String SUCCESS = "success";    //新增
    private static final String DELETE = "DELETE";     //删除


    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "同步社内用户")
    @RequestMapping(value = "/syncDatas", method = RequestMethod.POST)
    public ResponseBean<List> syncDatas(HttpServletRequest request, @RequestBody String json) {
        List<SsoReturnData> retrunDatas = new ArrayList<>();
        try {
            Integer code = 1;
            System.out.println("同步社内用户");
            System.out.println(json);
            String appSerialNumber = request.getParameter("appSerialNumber");
            String utsNodeString = request.getParameter("utsNodeInfo");
            JSONObject jsonObject = JSONObject.parseObject(utsNodeString);
            JSONArray datas = jsonObject.getJSONArray("datas");

            for (int j = 0; j < datas.size(); j++) {

                SsoReturnData ssoReturnData = new SsoReturnData();
                try {
                    Datas data = datas.getObject(j, Datas.class);
                    ssoReturnData.setId(data.getId());

                    JSONObject jsonObject1 = datas.getJSONObject(j);
                    JSONObject utsJSON = jsonObject1.getJSONObject("utsNode");
                    UtsNode utsNode = (UtsNode) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(utsJSON), UtsNode.class);
                    //同步人员
                    if (PERSON.equals(data.getType())) {
                        PmphDepartment dpname = pmphDepartmentService.getPmphDepartmentByName(utsNode.getErpdeptname());
                        if (ObjectUtil.isNull(dpname)) {
                            String newParentPath = data.getNewParentPath();
                            String[] split = newParentPath.split("/");
                            List<String> strList = new ArrayList<>();
                            for (String s : split) {
                                if (!StringUtils.isNullOrEmpty(s)) {
                                    strList.add(s);
                                }
                            }
                            if (strList.size() <= 1) {
                                PmphDepartment pmphDepartment = new PmphDepartment();
                                pmphDepartment.setDpName(utsNode.getOu());
                                pmphDepartment.setParentId(1L);
                                pmphDepartment.setSort(999);
                                pmphDepartment.setPath("0-1");
                                pmphDepartmentService.add(pmphDepartment);
                            } else {
                                Long parentId = 1L;
                                String path = "0-1";
                                for (String s : strList) {
                                    PmphDepartment dp = pmphDepartmentService.getPmphDepartmentByName(s);
                                    if (ObjectUtil.notNull(dp)) {
                                        path = dp.getPath();
                                    } else {
                                        PmphDepartment pmphDepartment = new PmphDepartment();
                                        pmphDepartment.setDpName(s);
                                        pmphDepartment.setParentId(parentId);
                                        pmphDepartment.setSort(999);
                                        pmphDepartment.setPath(path);
                                        PmphDepartment add = pmphDepartmentService.add(pmphDepartment);
                                        parentId = add.getId();
                                        path = add.getPath() + "-" + add.getId();
                                    }
                                }
                            }
                        }

                        if ("INSERT".equals(data.getOperation())) {
                            PmphUser oldPmphUser = pmphUserService.getPmphUser(utsNode.getCn());
                            if (ObjectUtil.notNull(oldPmphUser)) {
                                updatePmphUser(oldPmphUser, utsNode);
                                ssoReturnData.setCode("0");
                                ssoReturnData.setMessage(SUCCESS);
                            } else {
                                addPmphUser(utsNode);
                                ssoReturnData.setCode("0");
                                ssoReturnData.setMessage(SUCCESS);
                            }
                        }
                        if ("DELETE".equals(data.getOperation())) {
                            PmphUser pmphUser1 = pmphUserService.getPmphUser(utsNode.getCn());
                            if (ObjectUtil.notNull(pmphUser1)) {
                                pmphUser1.setIsDeleted(true);
                                pmphUserService.updateUser(pmphUser1);
                                ssoReturnData.setCode("0");
                                ssoReturnData.setMessage(SUCCESS);
                            }

                        }
                        if ("MODIFY".equals(data.getOperation())) {
                            PmphUser oldPmphUser = pmphUserService.getPmphUser(utsNode.getCn());
                            if (ObjectUtil.notNull(oldPmphUser)) {
                                updatePmphUser(oldPmphUser, utsNode);
                                ssoReturnData.setCode("0");
                                ssoReturnData.setMessage(SUCCESS);
                            } else {
                                addPmphUser(utsNode);
                                ssoReturnData.setCode("0");
                                ssoReturnData.setMessage(SUCCESS);
                            }

                        }
                    }
                    //同步部门
                    if (ORGANIZATIONAL_UNIT.equals(data.getType())) {
                        if ("INSERT".equals(data.getOperation())) {
                            PmphDepartment pmphDepartmentByName = pmphDepartmentService.getPmphDepartmentByName(utsNode.getOu());
                            if (ObjectUtil.isNull(pmphDepartmentByName)) {
                                String newParentPath = data.getNewParentPath();
                                String[] split = newParentPath.split("/");
                                List<String> strList = new ArrayList<>();
                                for (String s : split) {
                                    if (!StringUtils.isNullOrEmpty(s)) {
                                        strList.add(s);
                                    }
                                }
                                if (strList.size() <= 1) {
                                    PmphDepartment pmphDepartment = new PmphDepartment();
                                    pmphDepartment.setDpName(utsNode.getOu());
                                    pmphDepartment.setParentId(1L);
                                    pmphDepartment.setSort(999);
                                    pmphDepartment.setPath("0-1");
                                    pmphDepartmentService.add(pmphDepartment);
                                } else {
                                    Long parentId = 1L;
                                    String path = "0-1";
                                    for (String s : strList) {
                                        PmphDepartment pmphDepartment = new PmphDepartment();
                                        pmphDepartment.setDpName(s);
                                        pmphDepartment.setParentId(parentId);
                                        pmphDepartment.setSort(999);
                                        pmphDepartment.setPath(path);
                                        PmphDepartment add = pmphDepartmentService.add(pmphDepartment);
                                        parentId = add.getId();
                                        path = add.getPath() + "-" + add.getId();
                                    }

                                }
                                ssoReturnData.setCode("0");
                                ssoReturnData.setMessage(SUCCESS);

                            }

                        }
                        if ("DELETE".equals(data.getOperation())) {
                            PmphDepartment pmphDepartmentByName = pmphDepartmentService.getPmphDepartmentByName(utsNode.getOu());
                            if (ObjectUtil.notNull(pmphDepartmentByName)) {
                                Integer integer = pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartmentByName.getId());
                                if (integer > 0) {
                                    ssoReturnData.setCode("0");
                                    ssoReturnData.setMessage(SUCCESS);
                                } else {
                                    ssoReturnData.setCode("1");
                                    ssoReturnData.setMessage("删除部门失败");
                                }

                            }
                        }
                        if ("MODIFY".equals(data.getOperation())) {
                            PmphDepartment pmphDepartmentByName = pmphDepartmentService.getPmphDepartmentByName(utsNode.getOu());
                            if (ObjectUtil.isNull(pmphDepartmentByName)) {
                                String newParentPath = data.getNewParentPath();
                                String[] split = newParentPath.split("/");
                                List<String> strList = new ArrayList<>();
                                for (String s : split) {
                                    if (!StringUtils.isNullOrEmpty(s)) {
                                        strList.add(s);
                                    }
                                }
                                if (strList.size() <= 1) {
                                    PmphDepartment pmphDepartment = new PmphDepartment();
                                    pmphDepartment.setDpName(utsNode.getOu());
                                    pmphDepartment.setParentId(1L);
                                    pmphDepartment.setSort(999);
                                    pmphDepartment.setPath("0-1");
                                    pmphDepartmentService.add(pmphDepartment);
                                } else {
                                    Long parentId = 1L;
                                    String path = "0-1";
                                    for (String s : strList) {
                                        PmphDepartment pmphDepartment = new PmphDepartment();
                                        pmphDepartment.setDpName(s);
                                        pmphDepartment.setParentId(parentId);
                                        pmphDepartment.setSort(999);
                                        pmphDepartment.setPath(path);
                                        PmphDepartment add = pmphDepartmentService.add(pmphDepartment);
                                        parentId = add.getId();
                                        path = add.getPath() + "-" + add.getId();
                                    }

                                }

                            }
                            ssoReturnData.setCode("0");
                            ssoReturnData.setMessage(SUCCESS);

                        }
                    }
                    System.out.println(data);
                    System.out.println(utsNode);

                } catch (Exception e) {
                    ssoReturnData.setCode("1");
                    ssoReturnData.setMessage(e.getMessage());

                } finally {
                    retrunDatas.add(ssoReturnData);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseBean<List>(retrunDatas);
    }

    public void addPmphUser(UtsNode utsNode) {
        PmphUser newPmphUser = new PmphUser();
        newPmphUser.setUsername(utsNode.getCn());
        newPmphUser.setRealname(utsNode.getSn());
        String pwd = Decrypt.decryptPasswd(utsNode.getUserpasswordcipher());
        newPmphUser.setPassword(pwd);
        newPmphUser.setPassword(new DesRun("", newPmphUser.getPassword()).enpsw);
        newPmphUser.setGmtUpdate(utsNode.getLastmodifytime());
        PmphDepartment pmphDepartmentByName = pmphDepartmentService.getPmphDepartmentByName(utsNode.getErpdeptname());
        if (ObjectUtil.notNull(pmphDepartmentByName))
            newPmphUser.setDepartmentId(pmphDepartmentByName.getId());
        newPmphUser.setHandphone(utsNode.getEmployeemobile());
        newPmphUser.setEmail(utsNode.getEmployeemail());
        newPmphUser.setSort(999);
        PmphUser add = pmphUserService.add(newPmphUser);
        pmphRoleService.addUserRole(add.getId(), 2L);//
    }

    public void updatePmphUser(PmphUser oldPmphUser, UtsNode utsNode) {
        oldPmphUser.setRealname(utsNode.getSn());
        String pwd = Decrypt.decryptPasswd(utsNode.getUserpasswordcipher());
        oldPmphUser.setPassword(pwd);
        oldPmphUser.setPassword(new DesRun("", oldPmphUser.getPassword()).enpsw);
        oldPmphUser.setGmtCreate(utsNode.getEmployeebirthday());
        oldPmphUser.setGmtUpdate(utsNode.getLastmodifytime());
        PmphDepartment pmphDepartmentName = pmphDepartmentService.getPmphDepartmentByName(utsNode.getErpdeptname());
        if (ObjectUtil.notNull(pmphDepartmentName)) {
            oldPmphUser.setDepartmentId(pmphDepartmentName.getId());
        } else {
            oldPmphUser.setDepartmentId(0L);
        }
        oldPmphUser.setHandphone(utsNode.getEmployeemobile());
        oldPmphUser.setEmail(utsNode.getEmployeemail());
        pmphUserService.updateUser(oldPmphUser);

    }

}
