package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.back.vo.MyMessageVO;
import com.bc.pmpheep.back.vo.UserMessageVO;
import com.bc.pmpheep.back.vo.WxMessageVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 微信消息列表
 **/
public interface WxMessageService {

	/**
	 * 分页查询保存于人卫工程的数据库中的消息，用于微信微平台显示
	 * @param pageParameter
	 * @param sessionId
	 * @return
	 */
	Map<String,Object> listMessage(PageParameter<WxMessageVO> pageParameter, String sessionId);

}
