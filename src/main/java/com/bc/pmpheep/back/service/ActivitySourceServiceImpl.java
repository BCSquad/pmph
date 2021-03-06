package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ActivitySourceDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivitySourceServiceImpl implements ActivitySourceService {
    @Autowired
    ActivitySourceDao activitySourceDao;

    @Autowired
    private FileService fileService;

    @Override
    public ActivitySource addSource(Long activityId,String[] files, ActivitySource activitySource, String sessionId, HttpServletRequest request) throws IOException {

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        activitySource.setGmtUpload(DateUtil.getCurrentTime());
        activitySource.setUploaderId(pmphUser.getId());

        /*     activitySourceDao.addSource(activitySource);// 获取新增后的主键ID*/
        Long id = this.addSource(activitySource).getId();
        ActivitySource source = null;
        if (ArrayUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.length; i++) {
                byte[] fileByte = (byte[]) request.getSession(false).getAttribute(files[i]);
                String fileName =
                        (String) request.getSession(false).getAttribute("fileName_" + files[i]);
                InputStream sbs = new ByteArrayInputStream(fileByte);
                String gridFSFileId =
                        fileService.save(sbs, fileName, FileType.CMS_ATTACHMENT, id);
                if (StringUtil.isEmpty(gridFSFileId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                            CheckedExceptionResult.FILE_UPLOAD_FAILED,
                            "文件上传失败!");
                }
                activitySource.setSort(activitySourceDao.getMaxSort() + 1);
                activitySource.setFileId(gridFSFileId);
                Integer sort=activitySourceDao.getChainSortMax(activityId).intValue() + 1;
                activitySourceDao.addActivitySourceChain(new ActivitySourceChain(activityId,activitySource.getId(),sort.longValue()));




                // 保存对应数据
                activitySourceDao.updateSource(activitySource);
                if (ObjectUtil.isNull(activitySource.getId())) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                            CheckedExceptionResult.PO_ADD_FAILED,
                            "CmsExtra对象新增失败");
                }
            }
        }
        return activitySource;
    }
    @Override
    public ActivitySource addSourceList(Long activityId,String[] files,String sessionId, HttpServletRequest request) throws IOException {
        if (ArrayUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.length; i++) {
                    ActivitySource activitySource = new ActivitySource();
                    activitySource.setSourceName("default");
                    PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
                    activitySource.setGmtUpload(DateUtil.getCurrentTime());
                    activitySource.setUploaderId(pmphUser.getId());

                    /*     activitySourceDao.addSource(activitySource);// 获取新增后的主键ID*/
                    Long id = this.addSource(activitySource).getId();


                byte[] fileByte = (byte[]) request.getSession(false).getAttribute(files[i]);
                String fileName =
                        (String) request.getSession(false).getAttribute("fileName_" + files[i]);
                InputStream sbs = new ByteArrayInputStream(fileByte);
                String gridFSFileId =
                        fileService.save(sbs, fileName, FileType.CMS_ATTACHMENT, id);
                if (StringUtil.isEmpty(gridFSFileId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                            CheckedExceptionResult.FILE_UPLOAD_FAILED,
                            "文件上传失败!");
                }
                activitySource.setSourceName(fileName);
                activitySource.setSort(activitySourceDao.getMaxSort()+1);
                activitySource.setFileId(gridFSFileId);
                Integer sort=activitySourceDao.getChainSortMax(activityId).intValue() + 1;

                activitySourceDao.addActivitySourceChain(new ActivitySourceChain(activityId,activitySource.getId(),sort.longValue()));

                // 保存对应数据
                activitySourceDao.updateSource(activitySource);
                if (ObjectUtil.isNull(activitySource.getId())) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                            CheckedExceptionResult.PO_ADD_FAILED,
                            "CmsExtra对象新增失败");
                }
            }
        }
        return null;
    }

    @Override
    public PageResult<ActivitySourceVO> listSource(PageParameter<ActivitySourceVO> pageParameter, String sessionId) {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        PageResult<ActivitySourceVO> pageResult = new PageResult<ActivitySourceVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // if(cmsContentDao.getCmsContentByAuthorId(pageParameter.getParameter().getAuthorId()).size()>0){
        // 包含数据总条数的数据集
        List<ActivitySourceVO> sourcesList = activitySourceDao.listActivitySource(pageParameter);
        if (CollectionUtil.isNotEmpty(sourcesList)) {
            Integer count = sourcesList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(sourcesList);
        }
        // }
        return pageResult;
    }

    @Override
    public void addActivitySourceChain(String activityId, String[] sourceIds) {
            activitySourceDao.delsourceChin(Long.parseLong(activityId));
            Long i=0L;
            for (String sourceId : sourceIds) {
                ActivitySourceChain activitySourceChain = new ActivitySourceChain();
                activitySourceChain.setActivityId(Long.parseLong(activityId));
                activitySourceChain.setActivitySourceId(Long.parseLong(sourceId));
                activitySourceChain.setSort(i);
                addActivitySourcechin(activitySourceChain);
                i++;
            }

    }

    @Override
    public Integer deleteSourceByIds(Long id) {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return activitySourceDao.deleteSourceById(id);
    }

    @Override
    public Integer updateSort(Integer id, PageParameter<ActivitySourceVO> pageParameter, String type) {
        ActivitySource sortById = activitySourceDao.getSortById(id);
        if ("up".equals(type)) {
            ActivitySource upSortById = activitySourceDao.getUpSortById(sortById.getSort());
            Integer down = upSortById.getSort();
            Integer up = sortById.getSort();
            sortById.setSort(null);
            upSortById.setSort(null);
            activitySourceDao.updateSourceSort(sortById);
            activitySourceDao.updateSourceSort(upSortById);
            sortById.setSort(down);
            upSortById.setSort(up);
            activitySourceDao.updateSourceSort(sortById);
            Integer integer = activitySourceDao.updateSourceSort(upSortById);
            return integer;
        }
        if ("down".equals(type)) {
            ActivitySource downSortById = activitySourceDao.getDownSortById(sortById.getSort());
            Integer up = downSortById.getSort();
            Integer down = sortById.getSort();
            sortById.setSort(null);
            downSortById.setSort(null);
            activitySourceDao.updateSourceSort(sortById);
            activitySourceDao.updateSourceSort(downSortById);
            sortById.setSort(up);
            downSortById.setSort(down);
            activitySourceDao.updateSourceSort(sortById);
            Integer integer = activitySourceDao.updateSourceSort(downSortById);
            return integer;
        }
        return 0;
    }

    @Override
    public List<ActivitySourceChain> getSourceChain(Long id) {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "id为空");
        }
        return activitySourceDao.getSourceChain(id);
    }

    @Override
    public Integer checkedName(String sourceName) {
        if (ObjectUtil.isNull(sourceName)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "sourceName为空");
        }
        return activitySourceDao.checkedName(sourceName);
    }

    public void addActivitySourcechin(ActivitySourceChain activitySourceChain) {
        activitySourceDao.addActivitySourceChain(activitySourceChain);
    }

    public ActivitySource addSource(ActivitySource activitySource) {
        activitySourceDao.addSource(activitySource);
        return activitySource;
    }

    @Override
    public PageResult<ActivitySourceVO> getChainList(PageParameter<ActivitySourceVO> pageParameter, String sessionId) {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        PageResult<ActivitySourceVO> pageResult = new PageResult<ActivitySourceVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // if(cmsContentDao.getCmsContentByAuthorId(pageParameter.getParameter().getAuthorId()).size()>0){
        // 包含数据总条数的数据集
        List<ActivitySourceVO> sourcesList = activitySourceDao.getChainList(pageParameter);
        if (CollectionUtil.isNotEmpty(sourcesList)) {
            Integer count = sourcesList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(sourcesList);
        }
        // }
        return pageResult;
    }

    @Override
    public Integer delChainSourceById(Long activityId, Long activitySourceId) {
        if (ObjectUtil.isNull(activityId) || ObjectUtil.isNull(activitySourceId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
        }

        Map<String,Long > paramsMap=new HashMap<String,Long>();
        paramsMap.put("activityId",activityId);
        paramsMap.put("activitySourceId",activitySourceId);
        return activitySourceDao.delChainBySourceId(paramsMap);
    }

    public Integer updateChainSort(ActivitySourceChain activitySourceChain){
        return  activitySourceDao.updateChainSort(activitySourceChain);
    }
    public  ActivitySourceChain getUpChainById(Map<String,Long> map){
        return  activitySourceDao.getUpChianById(map);
    }
    public  ActivitySourceChain getDownChainnById(Map<String,Long> map){
        return  activitySourceDao.getDownChainById(map);
    }

}
