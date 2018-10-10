package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsManualDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.CmsManual;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.CmsManualVO;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mongodb.gridfs.GridFSDBFile;

@Service
public class CmsManualServiceImpl implements CmsManualService {
    @Autowired
    CmsManualDao cmsManualDao;
    @Autowired
    FileService  fileService;

    @Override
    public CmsManual addCmsManual(CmsManual cmsManual) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsManual)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "CmsManual对象参数为空");
        }
        if (ObjectUtil.isNull(cmsManual.getAttachment())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "附件id参数为空");
        }
        /*if (StringUtil.isEmpty(cmsManual.getNote())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "备注参数为空");
        }*/
        if (ObjectUtil.isNull(cmsManual.getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "上传者id参数为空");
        }
        if (cmsManual.getManualName().length() > 50) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.ILLEGAL_PARAM,
                                              "操作手册名称不能超过50个字！");
        }
        String fileName = "操作手册.doc";
        GridFSDBFile file = fileService.get(cmsManual.getAttachment());
        if (ObjectUtil.notNull(file)) {
            fileName = file.getFilename();
        }
        cmsManual.setManualName(fileName);
        cmsManualDao.addCmsManual(cmsManual);
        return cmsManual;
    }

    @Override
    public Integer deleteCmsManualById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "主键ID为空");
        }
        return cmsManualDao.deleteCmsManualById(id);
    }

    @Override
    public CmsManual addCmsManual(CmsManual cmsManual, String sessionId)
    throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        cmsManual.setUserId(pmphUser.getId());
        this.addCmsManual(cmsManual);
        return cmsManual;
    }

    @Override
    public PageResult<CmsManualVO> listCmsManual(PageParameter<CmsManualVO> pageParameter,
    String sessionId) throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        PageResult<CmsManualVO> pageResult = new PageResult<CmsManualVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<CmsManualVO> cmsManualVOs = cmsManualDao.listCmsManual(pageParameter);
        if (CollectionUtil.isNotEmpty(cmsManualVOs)) {
            Integer count = cmsManualVOs.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(cmsManualVOs);
        }
        return pageResult;
    }

    @Override
    public List<CmsManual> listCmsManualByManualName(String manualName)
    throws CheckedServiceException {
        if (StringUtil.isEmpty(manualName)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return cmsManualDao.listCmsManualByManualName(manualName);
    }

}
