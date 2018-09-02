package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.WxMessageDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.WxMessageVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxMessageServiceImpl implements WxMessageService{

    @Autowired
    WxMessageDao wxMessageDao;

    @Override
    public Map<String,Object> listMessage(PageParameter<WxMessageVO> pageParameter, String sessionId) {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (null == pmphUser || null == pmphUser.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }
        pageParameter.getParameter().setUserid(pmphUser.getId());

        Map<String,Object> resultMap = new HashMap<String,Object>();

        List<WxMessageVO> resultList = new ArrayList<WxMessageVO>();

        int count = wxMessageDao.queryMessageListCount(pageParameter);
        resultMap.put("total",count);
        if (count>0){
            resultList = wxMessageDao.queryMessageList(pageParameter);
        }
        resultMap.put("data",resultList);

        return resultMap;
    }

    @Override
    public int haveRead(Long id) {
        int count = wxMessageDao.haveRead(id);
        return count;
    }
}
