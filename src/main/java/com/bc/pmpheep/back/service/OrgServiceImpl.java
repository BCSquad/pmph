package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgDao;
import com.bc.pmpheep.back.dao.OrgUserDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.OrgExclVO;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgService 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class OrgServiceImpl extends BaseService implements OrgService {

    @Autowired
    private OrgDao orgDao;

    @Autowired
    OrgUserDao     orgUserDao;

    @Override
    public List<Org> listBeElectedOrgByBookIds(List<Long> bookIds) throws CheckedServiceException {
        if (null == bookIds || bookIds.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        for (Long bookId : bookIds) {
            if (null == bookId) {
                throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                                  CheckedExceptionResult.NULL_PARAM, "书籍参数为空");
            }
        }
        return orgDao.listBeElectedOrgByBookIds(bookIds);
    }

    @Override
    public List<Org> listBeElectedOrgByBookIds_up1(List<Long> bookIds) throws CheckedServiceException {
        if (null == bookIds || bookIds.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        for (Long bookId : bookIds) {
            if (null == bookId) {
                throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                        CheckedExceptionResult.NULL_PARAM, "书籍参数为空");
            }
        }
        return orgDao.listBeElectedOrgByBookIds_up1(bookIds);
    }

    /**
     * 
     * @param org 实体对象
     * @return Org 带主键
     * @throws CheckedServiceException
     */
    @Override
    public Org addOrg(Org org) throws CheckedServiceException {
        if (ObjectUtil.isNull(org)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        // if (null == org.getParentId()) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
        // CheckedExceptionResult.NULL_PARAM, "上级机构id不能为空");
        // }
        if (StringUtil.isEmpty(org.getOrgName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构名称为空");
        }
        if (StringUtil.strLength(org.getOrgName()) > 20) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构名称过长");
        }
        if (orgDao.getOrgByOrgName(org.getOrgName()).size() > 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构名称重复了");
        }
        if (ObjectUtil.isNull(org.getOrgTypeId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构类型不能为空");
        }
        if (ObjectUtil.isNull(org.getAreaId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构区域不能为空");
        }
        // if (null == org.getContactPerson()) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
        // CheckedExceptionResult.NULL_PARAM,
        // "机构联系人不能为空");
        // }
        // if (null == org.getContactPhone()) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
        // CheckedExceptionResult.NULL_PARAM,
        // "机构联系电话不能为空");
        // }
        // if (null == org.getSort()) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
        // CheckedExceptionResult.NULL_PARAM,
        // "机构显示顺序为空");
        // }
        // if (null == org.getNote()) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
        // CheckedExceptionResult.NULL_PARAM, "备注为空");
        // }
        Long id = org.getId();
        orgDao.addOrg(org);
        if (null != id) {
            org.setId(id);
        }
        return org;
    }

    /**
     * 
     * @param id
     * @return Org
     * @throws CheckedServiceException
     */
    @Override
    public Org getOrgById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return orgDao.getOrgById(id);
    }

    @Override
    public Org getOrgByNameAndUserName(String name, String username) throws CheckedServiceException {
        if (StringUtil.isEmpty(name)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构名称为空");
        }
        if (StringUtil.isEmpty(username)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构代码为空");
        }
        return orgDao.getOrgByNameAndUserName(name.trim(), username.trim());
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteOrgById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        if (!CollectionUtil.isEmpty(orgUserDao.listOrgUserByOrgId(id))) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM,
                                              "该机构内还有用户哦，请先将用户删除。");
        }
        return orgDao.deleteOrgById(id);
    }

    /**
     * @param org
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateOrg(Org org) throws CheckedServiceException {
        if (null == org) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (null == org.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        // if (null == org.getParentId()) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
        // CheckedExceptionResult.NULL_PARAM,
        // "上级机构id不能为空");
        // }
        if (null == org.getOrgName()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构名称为空");
        }
        if (null == org.getOrgTypeId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构类型不能为空");
        }
        if (null == org.getAreaId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构区域不能为空");
        }
        if (null == org.getContactPerson()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构联系人不能为空");
        }
        if (null == org.getContactPhone()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构联系电话不能为空");
        }
        if (null == org.getSort()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "机构显示顺序为空");
        }
        if (null == org.getNote()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
                                              CheckedExceptionResult.NULL_PARAM, "备注为空");
        }
        return orgDao.updateOrg(org);
    }

    @Override
    public PageResult<OrgVO> listOrg(PageParameter<OrgVO> pageParameter)
    throws CheckedServiceException {
        PageResult<OrgVO> pageResult = new PageResult<OrgVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        List<OrgVO> orgVOList = orgDao.listOrg(pageParameter);
        if (null != orgVOList && orgVOList.size() > 0) {
            Integer count = orgVOList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(orgVOList);
        }
        return pageResult;
    }

    @Override
    public PageResult<OrgVO> getSchoolAdminCheckList(PageParameter<OrgVO> pageParameter)
    throws CheckedServiceException {
        PageResult<OrgVO> pageResult = new PageResult<OrgVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        List<OrgVO> orgVOList = orgDao.getSchoolAdminCheckList(pageParameter);
        if (CollectionUtil.isNotEmpty(orgVOList)) {
            Integer count = orgVOList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(orgVOList);
        }
        return pageResult;
    }
    @Override
    public List<OrgVO> exportOrgUser(OrgVO orgVO)
            throws CheckedServiceException {

        List<OrgVO> orgVOList = orgDao.exportOrgUser(orgVO);

        for(OrgVO rgVO :orgVOList){
            switch(rgVO.getProgress()){
                case 0 : rgVO.setProgressName("待审核"); break;
                case 1 : rgVO.setProgressName("已通过"); break;
                case 2 : rgVO.setProgressName("已退回"); break;
            }
        }

        return orgVOList;
    }


    @Override
    public List<OrgVO> listOrgByOrgName(String orgName) throws CheckedServiceException {
        List<OrgVO> list = new ArrayList<>();
        if (null != orgName) {
            orgName = orgName.trim();
            if (!orgName.equals("")) {
                orgName = "%" + orgName + "%";
                list = orgDao.listOrgByOrgName(orgName);
            }
        }
        return list;
    }

    @Override
    public List<OrgVO> listSendToSchoolAdminOrAllUser(String orgName, Long materialId)
    throws CheckedServiceException {
        return orgDao.listSendToSchoolAdminOrAllUser(orgName, materialId);
    }

    @Override
    public List<OrgVO> listSendClinicalToSchoolAdminOrAllUser(String orgName, Long productId)
            throws CheckedServiceException {
        return orgDao.listSendClinicalToSchoolAdminOrAllUser(orgName, productId);
    }

    @Override
    public List<OrgExclVO> listAllOrgToExcel(String listAllOrgToExcel) throws CheckedServiceException {
        return orgDao.listAllOrgToExcel(StringUtil.isEmpty(listAllOrgToExcel)?null:listAllOrgToExcel.split(","));
    }

}
