package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupFileDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupService 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class PmphGroupFileServiceImpl extends BaseService implements PmphGroupFileService {
    @Autowired
    private PmphGroupFileDao        pmphGroupFileDao;
    @Autowired
    private PmphGroupMemberService  pmphGroupMemberService;
    @Autowired
    private FileService             fileService;
    @Autowired
    private PmphGroupMessageService pmphGroupMessageService;

    /**
     * 
     * @param pmphGroupFile 实体对象
     * @return 带主键的 PmphGroupFile
     * @throws CheckedServiceException
     * @update Mryang 2017.10.13 15:30
     */
    @Override
    public String addPmphGroupFile(List<Long> ids, List<MultipartFile> files, String sessionId)
    throws CheckedServiceException, IOException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (null == pmphUser || null == pmphUser.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (null == ids || ids.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
        if (null == files || files.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "文件不能为空");
        }
        Long userId = pmphUser.getId();
        for (Long id : ids) {
            if (null == id) {
                throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                                  CheckedExceptionResult.NULL_PARAM, "小组id不能为空");
            }
            PmphGroupMemberVO pmphGroupMemberVO =
            pmphGroupMemberService.getPmphGroupMemberByMemberId(id, userId, false);
            for (MultipartFile file : files) {
                String fileId = fileService.save(file, FileType.GROUP_FILE, 0);
                PmphGroupFile pmphGroupFile =
                new PmphGroupFile(id, pmphGroupMemberVO.getId(), fileId,
                                  file.getOriginalFilename(), 0, null);
                pmphGroupFileDao.addPmphGroupFile(pmphGroupFile);
                pmphGroupMessageService.addGroupMessage(pmphGroupMemberVO.getDisplayName()
                                                        + "上传了文件" + file.getOriginalFilename(),
                                                        id,
                                                        sessionId,
                                                        Const.SENDER_TYPE_0);
            }
        }
        return "success";
    }

    /**
     * 
     * @param id 主键id
     * @return PmphGroupFile
     * @throws CheckedServiceException
     */
    @Override
    public PmphGroupFile getPmphGroupFileById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return pmphGroupFileDao.getPmphGroupFileById(id);
    }

    /**
     * 
     * @param id 主键id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public String deletePmphGroupFileById(Long groupId, List<Long> ids, String sessionId)
    throws CheckedServiceException {
        String result = "FAIL";
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (null == pmphUser || null == pmphUser.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        Long id = pmphUser.getId();
        PmphGroupMemberVO currentUser =
        pmphGroupMemberService.getPmphGroupMemberByMemberId(groupId, id, false);
        if (null == ids || ids.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        } else {
            for (Long fileId : ids) {
                Long uploaderId = pmphGroupFileDao.getPmphGroupFileById(fileId).getMemberId();
                if (uploaderId == currentUser.getGroupId() || currentUser.getIsFounder()
                    || currentUser.getIsAdmin()) {
                    pmphGroupFileDao.deletePmphGroupFileById(fileId);
                } else {
                    throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                                      CheckedExceptionResult.ILLEGAL_PARAM,
                                                      "该用户没有此操作权限");
                }
            }
            result = "SUCCESS";
        }
        return result;
    }

    /**
     * @param PmphGroupFileVO
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updatePmphGroupFile(PmphGroupFile pmphGroupFile) throws CheckedServiceException {
        if (null == pmphGroupFile.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return pmphGroupFileDao.updatePmphGroupFile(pmphGroupFile);
    }

    @Override
    public PageResult<PmphGroupFileVO> listGroupFile(PageParameter<PmphGroupFileVO> pageParameter) {
        if (null == pageParameter.getParameter().getGroupId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "小组id不能为空");
        }
        String fileName = pageParameter.getParameter().getFileName();
        if (StringUtil.notEmpty(fileName)) {
            pageParameter.getParameter().setFileName(fileName);
        }
        PageResult<PmphGroupFileVO> pageResult = new PageResult<PmphGroupFileVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        int total = pmphGroupFileDao.getGroupFileTotal(pageParameter);
        if (total > 0) {
            List<PmphGroupFileVO> list = pmphGroupFileDao.listGroupFile(pageParameter);
            pageResult.setRows(list);
        }
        pageResult.setTotal(total);
        return pageResult;
    }

}
