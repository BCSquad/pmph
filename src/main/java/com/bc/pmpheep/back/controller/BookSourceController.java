package com.bc.pmpheep.back.controller;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.BookSourceService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookSource;
import com.bc.pmpheep.back.vo.BookSourceVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * @author MrYang
 * @CreateDate 2018年2月6日 下午5:19:00
 *
 *
 */
@Controller
@RequestMapping("/bookSource")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BookSourceController {

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "图书资源";

    @Autowired
    private BookSourceService bookSourceService;

    /**
     * 查询书籍资源文件
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午5:34:12
     * @param pageSize
     * @param pageNumber
     * @param bookName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSourceList", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询图书资源列表")
    public ResponseBean<PageResult<BookSourceVO>> getVideoList(
            Integer pageSize,
            Integer pageNumber,
            String bookName,
            String sourceName,
            Integer state,
            String upLoadTimeStart,
            String upLoadTimeEnd) {
        pageSize = (pageSize == null || pageSize <= 0) ? 5 : pageSize;
        pageNumber = (pageNumber == null || pageNumber <= 0) ? 1 : pageNumber;
        return new ResponseBean<PageResult<BookSourceVO>>(
                bookSourceService.getSourceList(pageSize, pageNumber, bookName,sourceName, state, upLoadTimeStart, upLoadTimeEnd));
    }



    /**
     * 审核视频
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午5:34:12
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/audit", method = RequestMethod.PUT)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "微视频审核")
    public ResponseBean<Integer> audit(HttpServletRequest request, Long id, Boolean isAuth) {
        if (ObjectUtil.isNull(id)) {
            return new ResponseBean(new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO,
                    CheckedExceptionResult.NULL_PARAM, "资源id不能为空"));
        }
        if (ObjectUtil.isNull(isAuth)) {
            return new ResponseBean(new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO,
                    CheckedExceptionResult.NULL_PARAM, "审核参数不能为空"));
        }
        String sessionId = CookiesUtil.getSessionId(request);
        if (StringUtil.isEmpty(sessionId)) {
            return new ResponseBean(new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO,
                    CheckedExceptionResult.USER_SESSION, "尚未登录或session已过期"));
        }
        BookSource bookSource = new BookSource();
        bookSource.setId(id);
        bookSource.setAuthDate(new Date());
        bookSource.setAuthId(SessionUtil.getPmphUserBySessionId(sessionId).getId());
        bookSource.setAuth(isAuth);
        return new ResponseBean(bookSourceService.updateBookSource(bookSource));
    }


    @ResponseBody
    @RequestMapping(value = "/deleteBookSource", method = RequestMethod.PUT)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除微视频")
    public ResponseBean<Integer> deleteBookSource(Long id) {
        return new ResponseBean(bookSourceService.deleteBookSourceByIds(Arrays.asList(new Long[]{id})));
    }

    /**
     * 保存视频
     *
     * @param request
     * @param cover
     * @throws IOException
     * @introduction
     * @author Mryang
     * @createDate 2018年2月10日 下午5:34:12
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBookSource", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存资源")
    public ResponseBean<Integer> addBookSource(HttpServletRequest request, Long bookId,
            String sourceName, @RequestParam("file") String[] file) throws IOException {
        String sessionId = CookiesUtil.getSessionId(request);
        if (StringUtil.isEmpty(sessionId)) {
            return new ResponseBean(new CheckedServiceException(CheckedExceptionBusiness.BOOK_SOURCE,
                    CheckedExceptionResult.USER_SESSION, "尚未登录或session已过期"));
        }
        BookSource bookSource = new BookSource();
        bookSource.setBookId(bookId);
        bookSource.setSourceName(sourceName);
        return new ResponseBean(bookSourceService.addBookSource(sessionId, bookSource, file,request));
    }

    @ResponseBody
    @RequestMapping(value = "/addSource", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存微视频信息")
    public ResponseBean<Integer> addVideo(Long userId, Long bookId,
            String sourceName, @RequestParam("file") MultipartFile file) throws IOException {
        BookSource bookSource = new BookSource();
        bookSource.setBookId(bookId);
        bookSource.setSourceName(sourceName);

        return new ResponseBean(bookSourceService.addBookSourceFront(userId, bookSource, file));
    }
}
