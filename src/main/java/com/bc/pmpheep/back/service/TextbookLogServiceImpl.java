package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.bc.pmpheep.back.dao.TextbookLogDao;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.po.TextbookLog;
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
	private TextbookLogDao textbookLogDao;
	
	@Autowired
	private DecPositionService  decPositionService;
	
	@Autowired
	private  DeclarationService declarationService;
	
	@Override
	public TextbookLog addTextbookLogWhenPub(Long textbookId,Long updaterId,int userType) throws CheckedServiceException {
		if(null == textbookId){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG, CheckedExceptionResult.NULL_PARAM,
					"书籍为空！");
		}
		if(null == updaterId){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG, CheckedExceptionResult.NULL_PARAM,
					"修改者为空！");
		}
		TextbookLog textbookLog = new TextbookLog();
		textbookLog.setDetail("发布了该教材最终结果");
		textbookLog.setIsPmphUpdater(userType==1);
		textbookLog.setTextbookId(textbookId);
		textbookLog.setUpdaterId(updaterId);
		textbookLogDao.addTextbookLog(textbookLog);
		return textbookLog;
	}
	
	@Override
	public TextbookLog addTextbookLogWhenPass(Long textbookId,Long updaterId,int userType) throws CheckedServiceException {
		if(null == textbookId){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG, CheckedExceptionResult.NULL_PARAM,
					"书籍为空！");
		}
		if(null == updaterId){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG, CheckedExceptionResult.NULL_PARAM,
					"修改者为空！");
		}
		TextbookLog textbookLog = new TextbookLog();
		textbookLog.setDetail("确认了该教材名单");
		textbookLog.setIsPmphUpdater(userType==1);
		textbookLog.setTextbookId(textbookId);
		textbookLog.setUpdaterId(updaterId);
		textbookLogDao.addTextbookLog(textbookLog);
		return textbookLog;
	}



    // 是否是数字编委
    private static Integer     IsDigitalEditor = 1; // 0:否,1：是

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
        List<DecPosition> newlist =
        decPositionService.listChosenDecPositionsByTextbookId(textbookId);
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
        // 增加的
        for (DecPosition newDecPosition : newlist) {
            // 申报者
            Long declarationId = newDecPosition.getDeclarationId();
            // 新的申报表
            Declaration declaration = declarationService.getDeclarationById(declarationId);
            // 新选的职位
            Integer newChosenPosition = newDecPosition.getChosenPosition();
            // 如果现在是主编
            if (null != newChosenPosition && newChosenPosition == 1) {
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
                // 副主编
            } else if (null != newChosenPosition && newChosenPosition == 2) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition oldDecPosition : oldlist) {
                    temp.append(oldDecPosition.getDeclarationId() + "_"
                                + oldDecPosition.getChosenPosition() + ",");
                }
                if (!temp.toString().contains("," + newDecPosition.getDeclarationId() + "_2" + ",")) {
                    addSumFuZhuBian++;
                    addFuZhuBian.append("," + declaration.getRealname());
                }
                // 编委
            } else if (null != newChosenPosition && newChosenPosition == 3) {
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
            if (newDecPosition.getIsDigitalEditor().intValue() == IsDigitalEditor.intValue()) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition oldDecPosition : oldlist) {
                    if (oldDecPosition.getIsDigitalEditor().intValue() == IsDigitalEditor.intValue()) {
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
            Declaration declaration = declarationService.getDeclarationById(declarationId);
            // 老的的职位
            Integer oldChosenPosition = oldDecPosition.getChosenPosition();
            // 如果以前是主编
            if (null != oldChosenPosition && oldChosenPosition == 1) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition newDecPosition : newlist) {
                    temp.append(newDecPosition.getDeclarationId() + "_"
                                + newDecPosition.getChosenPosition() + ",");
                }
                if (!temp.toString().contains("," + oldDecPosition.getDeclarationId() + "_1" + ",")) {
                    redSumZhuBian++;
                    redZhuBian.append("," + declaration.getRealname());
                }
                // 副主编
            } else if (null != oldChosenPosition && oldChosenPosition == 2) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition newDecPosition : newlist) {
                    temp.append(newDecPosition.getDeclarationId() + "_"
                                + newDecPosition.getChosenPosition() + ",");
                }
                if (!temp.toString().contains("," + oldDecPosition.getDeclarationId() + "_2" + ",")) {
                    redSumFuZhuBian++;
                    redFuZhuBian.append("," + declaration.getRealname());
                }
                // 编委
            } else if (null != oldChosenPosition && oldChosenPosition == 3) {
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
            if (oldDecPosition.getIsDigitalEditor().intValue() == IsDigitalEditor.intValue()) {
                StringBuilder temp = new StringBuilder(",");
                for (DecPosition newDecPosition : newlist) {
                    if (newDecPosition.getIsDigitalEditor().intValue() == IsDigitalEditor.intValue()) {
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
            if (redSumZhuBian > 0) {
                detail.append("移除了" + redSumFuZhuBian + "位副主编:["
                              + redFuZhuBian.toString().substring(1) + "]");
            }
            if (redSumZhuBian > 0) {
                detail.append("移除了" + redSumBianWei + "位编委:[" + redBianWei.toString().substring(1)
                              + "]");
            }
            if (redSumZhuBian > 0) {
                detail.append("移除了" + redSumShuZiBianWei + "位数字编辑:["
                              + redShuZiBianWei.toString().substring(1) + "]");
            }
            if (addSumZhuBian > 0) {
                detail.append("增加了" + addSumZhuBian + "位主编:[" + addZhuBian.toString().substring(1)
                              + "]");
            }
            if (addSumZhuBian > 0) {
                detail.append("增加了" + addSumFuZhuBian + "位副主编:["
                              + addFuZhuBian.toString().substring(1) + "]");
            }
            if (addSumZhuBian > 0) {
                detail.append("增加了" + addSumBianWei + "位编委:[" + addBianWei.toString().substring(1)
                              + "]");
            }
            if (addSumZhuBian > 0) {
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
