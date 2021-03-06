package com.bc.pmpheep.back.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.service.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
    public void syncDatas(HttpServletRequest request, @RequestBody String json, HttpServletResponse response) throws IOException {
        List<SsoReturnData> retrunDatas = new ArrayList<>(); //返回信息集合
        ReturnData returnData = new ReturnData();


        try {
            System.out.println("同步社内用户");
            System.out.println(json);
            //解析应用id
            String appSerialNumber = request.getParameter("appSerialNumber");
            //获取传输信息
            String utsNodeString = request.getParameter("utsNodeInfo");
            //解析信息
            JSONObject jsonObject = JSONObject.parseObject(utsNodeString);
            //解析信息集合
            JSONArray datas = jsonObject.getJSONArray("datas");
            for (int j = 0; j < datas.size(); j++) {
                //返回信息
                SsoReturnData ssoReturnData = new SsoReturnData();
                try {
                    //解析信息
                    Datas data = datas.getObject(j, Datas.class);
                    //设置返回信息的id
                    ssoReturnData.setId(data.getId());
                    //解析信息为json对象
                    JSONObject jsonObject1 = datas.getJSONObject(j);
                    //解析对象中的utsNode 集合信息
                    JSONObject utsJSON = jsonObject1.getJSONObject("utsNode");
                    //转换为java对象
                    UtsNode utsNode = (UtsNode) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(utsJSON), UtsNode.class);
                    //同步人员
                    if ("person".equals(data.getType())) {
                        //添加用户前先检查用户所在部门是否否存在
                        String newParentPath = data.getNewParentPath();
                        String[] split = newParentPath.split("/");
                        List<String> strList = new ArrayList<>();
                        //如果没有上级
                        if (newParentPath.equals("/")) {
                            //添加默认用户信息中的部门
                            strList.add(data.getNewName());
                        }
                        for (String s : split) {
                            if (!StringUtils.isNullOrEmpty(s)) {
                                strList.add(s);
                            }
                        }
                        if (strList.size() <= 1) {  //没有上级部门
                            PmphDepartment dp = pmphDepartmentService.getPmphDepartmentByName(strList.get(0));
                            //如果系统不存在此部门
                            if (ObjectUtil.isNull(dp)) {
                                PmphDepartment pmphDepartment = new PmphDepartment();
                                pmphDepartment.setDpName(strList.get(0));
                                pmphDepartment.setParentId(1L);
                                pmphDepartment.setSort(999);
                                pmphDepartment.setPath("0-1");
                                pmphDepartmentService.add(pmphDepartment);
                            } else {
                                //如果用户信息没有部门信息
                                if (StringUtils.isNullOrEmpty(utsNode.getErpdeptname())) {
                                    utsNode.setErpdeptname(dp.getDpName());
                                }
                            }
                            //如果有上级部门
                        } else {
                            //设置父id
                            Long parentId = 1L;
                            //设置路径
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
                            PmphDepartment lastDpName = pmphDepartmentService.getPmphDepartmentByName(strList.get(strList.size() - 1));
                            //如果用户信息中没有部门,把节点信息最后一个部门当做用户部门
                            if (StringUtils.isNullOrEmpty(utsNode.getErpdeptname())) {
                                utsNode.setErpdeptname(lastDpName.getDpName());
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
                            PmphUser oldPmphUser = pmphUserService.getPmphUser(data.getOldName());
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
                    } else if ("organizationalUnit".equals(data.getType())) {
                        if ("INSERT".equals(data.getOperation())) {
                            PmphDepartment pmphDepartmentByName = pmphDepartmentService.getPmphDepartmentByName(utsNode.getOu());
                            if (ObjectUtil.isNull(pmphDepartmentByName)) {
                                List<String> strList = new ArrayList<>();
                                String newParentPath = data.getNewParentPath();
                                if (newParentPath.equals("/")) {
                                    strList.add(data.getNewName());
                                }
                                String[] split = newParentPath.split("/");
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
                                List<String> strList = new ArrayList<>();
                                String newParentPath = data.getNewParentPath();
                                if (newParentPath.equals("/")) {
                                    strList.add(data.getNewName());
                                }
                                String[] split = newParentPath.split("/");

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
        PrintWriter writer = response.getWriter();
        writer.write("{\"status\":\"0x0000\",\"message\":{\"datas\":" + retrunDatas.toString().replace("=", ":") + "}}");
        writer.close();
    }

    public void addPmphUser(UtsNode utsNode) {
        PmphUser newPmphUser = new PmphUser();
        newPmphUser.setUsername(utsNode.getCn());
        newPmphUser.setRealname(utsNode.getSn());
        String pwd = Decrypt.decryptPasswd(utsNode.getUserpasswordcipher());
        newPmphUser.setPassword(pwd);
        newPmphUser.setPassword(new DesRun("", newPmphUser.getPassword()).enpsw);
        newPmphUser.setGmtUpdate(utsNode.getLastmodifytime());
        PmphDepartment pmphDepartmentByName;
        if (StringUtils.isNullOrEmpty(utsNode.getErpdeptname())) {
            pmphDepartmentByName = null;
        } else {
            pmphDepartmentByName = pmphDepartmentService.getPmphDepartmentByName(utsNode.getErpdeptname());
        }
        if (ObjectUtil.notNull(pmphDepartmentByName)) {
            newPmphUser.setDepartmentId(pmphDepartmentByName.getId());
        } else {
            newPmphUser.setDepartmentId(0L);
        }
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
        PmphDepartment pmphDepartmentName;
        if (StringUtils.isNullOrEmpty(utsNode.getErpdeptname())) {
            pmphDepartmentName = null;
        } else {
            pmphDepartmentName = pmphDepartmentService.getPmphDepartmentByName(utsNode.getErpdeptname());
        }

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
