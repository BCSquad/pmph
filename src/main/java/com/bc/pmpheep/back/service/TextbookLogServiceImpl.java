package com.bc.pmpheep.back.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.bc.pmpheep.back.dao.TextbookLogDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.po.TextbookLog;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.TextbookLogVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * TextbookLogService 实现
 * 
 * @introduction
 * 
 * @author Mryang
 * 
 * @createDate 2017年11月27日 下午5:19:35
 * 
 */
@Service
public class TextbookLogServiceImpl implements TextbookLogService {

    @Autowired
    private TextbookLogDao     textbookLogDao;

    @Autowired
    private DecPositionService decPositionService;

    @Autowired
    private DeclarationService declarationService;

    @Override
    public PageResult<TextbookLogVO> listTextbookLogByTextBookId(Long textbookId, Integer pageSize,
    Integer pageNumber, String updaterName) throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "书籍为空！");
        }
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("textbookId", textbookId);
        updaterName = StringUtil.toAllCheck(updaterName);
        if (null != updaterName) {
            map.put("updaterName", updaterName);
        }
        // 包装参数实体
        PageParameter<Map<String, Object>> pageParameter =
        new PageParameter<Map<String, Object>>(pageNumber, pageSize, map);
        // 返回实体
        PageResult<TextbookLogVO> pageResult = new PageResult<TextbookLogVO>();
        // 参数拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 获取总数
        Integer total = textbookLogDao.listTotalTextbookLogByTextBookId(pageParameter);
        if (null != total && total > 0) {
            List<TextbookLog> rows = textbookLogDao.listTextbookLogByTextBookId(pageParameter);
            List<TextbookLogVO> newRows = new ArrayList<TextbookLogVO>(rows.size());
            for (TextbookLog textbookLog : rows) {
                Long id = textbookLog.getId();
                String detail = textbookLog.getDetail();
                Timestamp gmtCreate = textbookLog.getGmtCreate();
                detail = detail.replace("{gmt_create}", DateUtil.format(gmtCreate));
                newRows.add(new TextbookLogVO(id, detail));
            }
            pageResult.setRows(newRows);
        }
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public TextbookLog addTextbookLogWhenPub(Long textbookId, Long updaterId, int userType)
    throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "书籍为空！");
        }
        if (null == updaterId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "修改者为空！");
        }
        TextbookLog textbookLog = new TextbookLog();
        textbookLog.setDetail("发布了该教材最终结果");
        textbookLog.setIsPmphUpdater(userType == 1);
        textbookLog.setTextbookId(textbookId);
        textbookLog.setUpdaterId(updaterId);
        textbookLogDao.addTextbookLog(textbookLog);
        return textbookLog;
    }

    @Override
    public TextbookLog addTextbookLogWhenPass(Long textbookId, Long updaterId, int userType)
    throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "书籍为空！");
        }
        if (null == updaterId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "修改者为空！");
        }
        TextbookLog textbookLog = new TextbookLog();
        textbookLog.setDetail("确认了该教材名单");
        textbookLog.setIsPmphUpdater(userType == 1);
        textbookLog.setTextbookId(textbookId);
        textbookLog.setUpdaterId(updaterId);
        textbookLogDao.addTextbookLog(textbookLog);
        return textbookLog;
    }

    @Override
    public TextbookLog addTextbookLog(TextbookLog textbookLog) throws CheckedServiceException {
        if (null == textbookLog) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空！");
        }
        if (null == textbookLog.getTextbookId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "书籍为空！");
        }
        if (null == textbookLog.getUpdaterId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "修改者为空！");
        }
        if (StringUtils.isEmpty(textbookLog.getDetail())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "详情为空！");
        }
        if (textbookLog.getDetail().length() > 100) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "详情太长！");
        }
        textbookLogDao.addTextbookLog(textbookLog);
        return textbookLog;
    }

    @Override
    public void addTextbookLog(List<DecPosition> oldlist, Long textbookId, Long updaterId,
    int userType) throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "书籍为空！");
        }
        if (null == updaterId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG,
                                              CheckedExceptionResult.NULL_PARAM, "修改者为空！");
        }
        List<DecPosition> newlist = decPositionService.listChosenDecPositionsByTextbookId(textbookId);
        int addSumZhuBian = 0;
        StringBuilder addZhuBian = new StringBuilder("");
        int redSumZhuBian = 0;
        StringBuilder redZhuBian = new StringBuilder("");
        int addSumFuZhuBian = 0;
        StringBuilder addFuZhuBian = new StringBuilder("");
        int redSumFuZhuBian = 0;
        StringBuilder redFuZhuBian = new StringBuilder("");
        int addSumBianWei = 0;
        StringBuilder addBianWei = new StringBuilder("");
        int redSumBianWei = 0;
        StringBuilder redBianWei = new StringBuilder("");
        int addSumShuZiBianWei = 0;
        StringBuilder addShuZiBianWei = new StringBuilder("");
        int redSumShuZiBianWei = 0;
        StringBuilder redShuZiBianWei = new StringBuilder("");
        //新旧申报的id
        List<Long> ids = new ArrayList<>(oldlist.size()+ newlist.size()); 
        for (DecPosition oldDecPosition : oldlist) {
        	ids.add(oldDecPosition.getDeclarationId());
        }
        for (DecPosition newDecPosition : newlist) {
        	ids.add(newDecPosition.getDeclarationId());
        }
        List<Declaration> declarations = declarationService.getDeclarationByIds(ids);
        // 增加的
        for (DecPosition newDecPosition : newlist) {
            // 申报者
            Long declarationId = newDecPosition.getDeclarationId();
            // 新的申报表
            Declaration declaration = new Declaration();
            for(Declaration tempDeclaration: declarations){
            	if(tempDeclaration.getId().intValue() == declarationId.intValue()){
            		declaration = tempDeclaration;
            		break ;
            	}
            }
            // 新选的职位
            Integer newChosenPosition = newDecPosition.getChosenPosition();
            // 如果现在是主编
            if (null != newChosenPosition && (
            		newChosenPosition == 4  || 
            		newChosenPosition == 5  || 
            		newChosenPosition == 6  || 
            		newChosenPosition == 7  ||
            		newChosenPosition == 12 ||
            		newChosenPosition == 13 ||
            		newChosenPosition == 14 ||
            		newChosenPosition == 15 
            		)) {
                StringBuilder temp = new StringBuilder(",");
                // 遍历出所有的主编信息
                for (DecPosition oldDecPosition : oldlist) {
                    temp.append(oldDecPosition.getDeclarationId() + "_"
                                + oldDecPosition.getChosenPosition() + ",");
                }
                if (!temp.toString().contains("," + newDecPosition.getDeclarationId() + "_1" + ",")) {
                    addSumZhuBian++;
                    addZhuBian.append("," + declaration.getRealname());
                }
            }
            // 副主编
            if (null != newChosenPosition && (
            		newChosenPosition == 2  || 
            		newChosenPosition == 3  || 
            		newChosenPosition == 6  || 
            		newChosenPosition == 7  || 
            		newChosenPosition == 10 || 
            		newChosenPosition == 11 || 
            		newChosenPosition == 14 || 
            		newChosenPosition == 15 
            		)) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition oldDecPosition : oldlist) {
                    temp.append(oldDecPosition.getDeclarationId() + "_"
                                + oldDecPosition.getChosenPosition() + ",");
                }
                if (!temp.toString().contains("," + newDecPosition.getDeclarationId() + "_2" + ",")) {
                    addSumFuZhuBian++;
                    addFuZhuBian.append("," + declaration.getRealname());
                }
           }
           // 编委
           if (null != newChosenPosition && (
        		   newChosenPosition == 1  ||
        		   newChosenPosition == 3  ||
        		   newChosenPosition == 5  ||
        		   newChosenPosition == 7  ||
        		   newChosenPosition == 9  ||
        		   newChosenPosition == 11  ||
        		   newChosenPosition == 13  ||
        		   newChosenPosition == 15  
        		   )) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition oldDecPosition : oldlist) {
                    temp.append(oldDecPosition.getDeclarationId() + "_"
                                + oldDecPosition.getChosenPosition() + ",");
                }
                if (!temp.toString().contains("," + newDecPosition.getDeclarationId() + "_3" + ",")) {
                    addSumBianWei++;
                    addBianWei.append("," + declaration.getRealname());
                }
            }
            // 数字编辑
            if (null != newChosenPosition && (
            		newChosenPosition == 8   || 
            		newChosenPosition == 9   || 
            		newChosenPosition == 10  || 
            		newChosenPosition == 11  ||
            		newChosenPosition == 12  ||
            		newChosenPosition == 13  ||
            		newChosenPosition == 14  ||
            		newChosenPosition == 15 
            		)) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition oldDecPosition : oldlist) {
                	Integer oldChosenPosition =  oldDecPosition.getChosenPosition() ;
                	if (null != oldChosenPosition && (
                			oldChosenPosition == 8   || 
        					oldChosenPosition == 9   || 
							oldChosenPosition == 10  || 
							oldChosenPosition == 11  ||
							oldChosenPosition == 12  ||
							oldChosenPosition == 13  ||
							oldChosenPosition == 14  ||
							oldChosenPosition == 15 
                    		)) { 
                        temp.append(oldDecPosition.getDeclarationId() + ",");
                    }

                }
                if (!temp.toString().contains("," + newDecPosition.getDeclarationId() + ",")) {
                    addSumShuZiBianWei++;
                    addShuZiBianWei.append("," + declaration.getRealname());
                }
            }
        }
        // 减少的
        for (DecPosition oldDecPosition : oldlist) {
            // 申报者
            Long declarationId = oldDecPosition.getDeclarationId();
            // 老的申报表
            Declaration declaration = new Declaration();
            for(Declaration tempDeclaration: declarations){
            	if(tempDeclaration.getId().intValue() == declarationId.intValue()){
            		declaration = tempDeclaration;
            		break ;
            	}
            }
            // 老的的职位
            Integer oldChosenPosition = oldDecPosition.getChosenPosition();
            // 如果以前是主编
            if (null != oldChosenPosition && (
            		oldChosenPosition == 4  || 
    				oldChosenPosition == 5  || 
					oldChosenPosition == 6  || 
					oldChosenPosition == 7  ||
					oldChosenPosition == 12 ||
					oldChosenPosition == 13 ||
					oldChosenPosition == 14 ||
					oldChosenPosition == 15 
            		)) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition newDecPosition : newlist) {
                    temp.append(newDecPosition.getDeclarationId() + "_"
                                + newDecPosition.getChosenPosition() + ",");
                }
                if (!temp.toString().contains("," + oldDecPosition.getDeclarationId() + "_1" + ",")) {
                    redSumZhuBian++;
                    redZhuBian.append("," + declaration.getRealname());
                }
               
            }
            // 副主编
            if (null != oldChosenPosition && (
            		oldChosenPosition == 2  || 
            		oldChosenPosition == 3  || 
            		oldChosenPosition == 6  || 
            		oldChosenPosition == 7  || 
            		oldChosenPosition == 10 || 
            		oldChosenPosition == 11 || 
            		oldChosenPosition == 14 || 
            		oldChosenPosition == 15 
            		)) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition newDecPosition : newlist) {
                    temp.append(newDecPosition.getDeclarationId() + "_"
                                + newDecPosition.getChosenPosition() + ",");
                }
                if (!temp.toString().contains("," + oldDecPosition.getDeclarationId() + "_2" + ",")) {
                    redSumFuZhuBian++;
                    redFuZhuBian.append("," + declaration.getRealname());
                }
                
            }
           // 编委
            if (null != oldChosenPosition && (
            		oldChosenPosition == 1  ||
            		oldChosenPosition == 3  ||
            		oldChosenPosition == 5  ||
            		oldChosenPosition == 7  ||
            		oldChosenPosition == 9  ||
            		oldChosenPosition == 11  ||
            		oldChosenPosition == 13  ||
            		oldChosenPosition == 15  
         		   )) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition newDecPosition : newlist) {
                    temp.append(newDecPosition.getDeclarationId() + "_"
                                + newDecPosition.getChosenPosition() + ",");
                }
                if (!temp.toString().contains("," + oldDecPosition.getDeclarationId() + "_3" + ",")) {
                    redSumBianWei++;
                    redBianWei.append("," + declaration.getRealname());
                }
            }
            // 数字编辑
            if (null != oldChosenPosition && (
            		oldChosenPosition == 8   || 
            		oldChosenPosition == 9   || 
            		oldChosenPosition == 10  || 
            		oldChosenPosition == 11  ||
            		oldChosenPosition == 12  ||
            		oldChosenPosition == 13  ||
            		oldChosenPosition == 14  ||
            		oldChosenPosition == 15 
            		)) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition newDecPosition : newlist) {
                	Integer newChosenPosition =  newDecPosition.getChosenPosition() ;
                    if (null != newChosenPosition && (
                    		newChosenPosition == 8   || 
                    		newChosenPosition == 9   || 
                    		newChosenPosition == 10  || 
                    		newChosenPosition == 11  ||
                    		newChosenPosition == 12  ||
                    		newChosenPosition == 13  ||
                    		newChosenPosition == 14  ||
                    		newChosenPosition == 15 
                    		)) {
                        temp.append(newDecPosition.getDeclarationId() + ",");
                    }
                }
                if (!temp.toString().contains("," + oldDecPosition.getDeclarationId() + ",")) {
                    redSumShuZiBianWei++;
                    redShuZiBianWei.append("," + declaration.getRealname());
                }
            }
        }
        if (addSumZhuBian > 0 || redSumZhuBian > 0 || addSumFuZhuBian > 0 || redSumFuZhuBian > 0
            || addSumBianWei > 0 || redSumBianWei > 0 || addSumShuZiBianWei > 0
            || redSumShuZiBianWei > 0) {
            StringBuilder detail = new StringBuilder("");
            if (redSumZhuBian > 0) {
                detail.append("移除了" + redSumZhuBian + "位主编:[" + redZhuBian.toString().substring(1)
                              + "]");
            }
            if (redSumFuZhuBian > 0) {
                detail.append("移除了" + redSumFuZhuBian + "位副主编:["
                              + redFuZhuBian.toString().substring(1) + "]");
            }
            if (redSumBianWei > 0) {
                detail.append("移除了" + redSumBianWei + "位编委:[" + redBianWei.toString().substring(1)
                              + "]");
            }
            if (redSumShuZiBianWei > 0) {
                detail.append("移除了" + redSumShuZiBianWei + "位数字编辑:["
                              + redShuZiBianWei.toString().substring(1) + "]");
            }
            if (addSumZhuBian > 0) {
                detail.append("增加了" + addSumZhuBian + "位主编:[" + addZhuBian.toString().substring(1)
                              + "]");
            }
            if (addSumFuZhuBian > 0) {
                detail.append("增加了" + addSumFuZhuBian + "位副主编:["
                              + addFuZhuBian.toString().substring(1) + "]");
            }
            if (addSumBianWei > 0) {
                detail.append("增加了" + addSumBianWei + "位编委:[" + addBianWei.toString().substring(1)
                              + "]");
            }
            if (addSumShuZiBianWei > 0) {
                detail.append("增加了" + addSumShuZiBianWei + "位数字编辑:["
                              + addShuZiBianWei.toString().substring(1) + "]");
            }
            String detail2 = detail.toString();
            TextbookLog textbookLog = new TextbookLog();
            textbookLog.setDetail(detail2.length() >= 100 ? detail2.substring(0, 95) + "..." : detail2);
            textbookLog.setIsPmphUpdater(userType == 1);
            textbookLog.setTextbookId(textbookId);
            textbookLog.setUpdaterId(updaterId);
            textbookLogDao.addTextbookLog(textbookLog);
        }
    }
}
