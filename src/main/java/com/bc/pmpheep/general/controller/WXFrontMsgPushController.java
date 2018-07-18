package com.bc.pmpheep.general.controller;

import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.back.dao.BookDao;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.service.*;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.MaterialProjectEditorVO;
import com.bc.pmpheep.back.vo.MaterialVO;
import com.bc.pmpheep.wx.service.WXQYUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("frontWxMsg")
public class WXFrontMsgPushController {

    @Autowired
    private DeclarationService declarationService;
    @Autowired
    private MaterialProjectEditorService materialProjectEditorService;
    @Autowired
    private PmphUserService pmphUserService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private WXQYUserService wxqyUserService;
    @Autowired
    private TextbookService textbookService;
    @Autowired
    private WriterUserService writerUserService;
    @Autowired
    private BookEditorService bookEditorService;
    @Autowired
    WxSendMessageService wxSendMessageService;

    @Autowired
    private BookDao bookDao;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public void helloBack(HttpServletRequest request) {
        String msg = request.getParameter("msg");
        System.out.printf("hello" + msg);
    }


    /**
     * 前台作家直接提交申报表给任出版社后，或学校审核完申报表后，向负责该套教材的项目编辑 推送微信消息
     * @param request
     * @param decId
     * @param response
     * @return
     */
    @RequestMapping(value = "/projectEditorPleaseAdit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean projectEditorPleaseAdit(HttpServletRequest request, @PathVariable(value = "id", required = true) Long decId, HttpServletResponse response) {
        //企业微信推送对象的微信id集合
        Set<String> touserOpenidSet = new HashSet<String>();
        Declaration dec = declarationService.getDeclarationById(decId);
        dec.getRealname();
        Material material = materialService.getMaterialById(dec.getMaterialId());
        List<MaterialProjectEditorVO> materialProjectEditorVOList = materialProjectEditorService.listMaterialProjectEditors(dec.getMaterialId());
        List<Long> useridList = new ArrayList<Long>();
        for (MaterialProjectEditorVO materialProjectEditorVO : materialProjectEditorVOList) {
            PmphUser projectEditorUser = pmphUserService.get(materialProjectEditorVO.getEditorId());
            //项目编辑加入企业微信推送对象集合
            touserOpenidSet.add(projectEditorUser.getOpenid());
            useridList.add(projectEditorUser.getId());
        }
        touserOpenidSet.remove(null);
        String touser = touserOpenidSet.toString();
        //***（作者人名）已提交《****》（教材名）的申报表，请审核
        String msg = dec.getRealname() + "已提交《" + material.getMaterialName() + "》的申报表，";//“请审核” 已被超链接补齐，此处不需显示
        //String url = "/materialrouter/materialnav/" + decId + "/presscheck";
        //&UserId&materialId=&declarationId=
        String paramUrlFormat = "&UserId=%s&materialId=%s&declarationId=%s";
        //String paramUrlFormat = "/#/material/%s/expert?declarationId=%s";
        String hrefType = "2";
        String hrefContentType = "2";
        for (String t: touserOpenidSet) {
            String paramUrl=String.format(paramUrlFormat,t,dec.getMaterialId(),dec.getId());
            Map resultMap = wxqyUserService.sendTextMessage(hrefType, hrefContentType, t, "", "", "text", msg, (short) 0,paramUrl);
            //return ((int) resultMap.get("errcode")) == 0;
        }
        String paramUrlFormat1 = "/#/material/%s/expert?declarationId=%s";
        String paramUrl=String.format(paramUrlFormat1,dec.getMaterialId(),dec.getId());
        wxSendMessageService.batchInsertWxMessage(msg,"0".equals(hrefType)?0:1,useridList,"6",hrefContentType,paramUrl);


        return false;
    }

    /**
     * 第一主编遴选编委之后，向主编、策划编辑、项目编辑 推送微信消息
     * @param textBookId 所遴选书籍id
     * @param uid 第一主编id
     * @return
     */
    @RequestMapping(value = "/firstEditorChooseSubmit/{id}/{uid}", method = RequestMethod.GET)
    @ResponseBody
    public boolean firstEditorChooseSubmit(@PathVariable(value = "id")Long textBookId
                                            ,@PathVariable(value = "uid")Long uid){

        WriterUser firstEditor = writerUserService.get(uid);
        Textbook textbook = textbookService.getTextbookById(textBookId);
        MaterialVO materialVo = materialService.getMaterialVO(textbook.getMaterialId());
        String msg = "";
        String touser = "";
        Set<String> touserIdSet = new HashSet<String>();
        Set<String> touserOpenidSet = new HashSet<String>();
        //策划编辑
        if(textbook.getPlanningEditor()!=null){
            touserIdSet.add(String.valueOf(textbook.getPlanningEditor()));
        }
        //主任
        touserIdSet.add(String.valueOf(materialVo.getDirector()));
        //项目编辑集合 [{"id":319,"realname":"ADiTest","materialId":75,"editorId":610}]
        List<Map> projectEditorsJA = JSON.parseArray(materialVo.getMaterialProjectEditors(), Map.class);
        for (Map m : projectEditorsJA) {
            touserIdSet.add(m.get("editorId").toString());
        }
        touserIdSet.remove(null);


        List<Long> useridList = new ArrayList<Long>();

        for (String id : touserIdSet) { //通过主任、项目编辑、策划编辑的id集合（set无重复id）查询其企业微信号id集合
            PmphUser pu = pmphUserService.get(Long.parseLong(id));
            useridList.add(pu.getId());
            if (StringUtil.notEmpty(pu.getOpenid())) {
                touserOpenidSet.add(pu.getOpenid());
            }
        }
        touserOpenidSet.remove(null);
        touser = touserOpenidSet.toString();
        // 推送内容： 《***》（书名）的***（主编）已提交编委预选名单
        msg = "“"+ materialVo.getMaterial().getMaterialName() + "”-《" + textbook.getTextbookName() + "》的第一主编 "+firstEditor.getRealname()+" 已提交编委预选名单";

        String hrefType = "0";
        String hrefContentType = "0";
        for (String t: touserOpenidSet) {
            Map resultMap = wxqyUserService.sendTextMessage(hrefType, hrefContentType, t, "", "", "text", msg, (short) 0,"");
            return ((int) resultMap.get("errcode")) == 0;
        }

        wxSendMessageService.batchInsertWxMessage(msg,"0".equals(hrefType)?0:1,useridList,hrefType,hrefContentType,"");

        return false;
    }

    /**
     * 前台用户提交选题申报后，向有管理员身份的社内用户推送微信消息
     */
    @RequestMapping(value = "topicSubmit/{tid}/{uid}",method = RequestMethod.GET)
    @ResponseBody
    public boolean topicSubmit(@PathVariable(value = "tid")Long tid,@PathVariable(value = "uid")long uid){

        Set<String> touserOpenidSet = new HashSet<String>();
        WriterUser submiter = writerUserService.get(uid);
        List<PmphUser> admins = pmphUserService.getListByRole(1l);
        List<Long> useridList = new ArrayList<Long>();
        for (PmphUser admin: admins) {
            touserOpenidSet.add(admin.getOpenid());
            useridList.add(admin.getId());
        }
        touserOpenidSet.remove(null);
        String touser = touserOpenidSet.toString();
        String msg = submiter.getRealname()+"已经提交了选题申报表，";
        String paramUrlFormat = "&UserId=%s";
        String hrefType = "2";
        String hrefContentType = "3";
        for (String t: touserOpenidSet) {
            String paramUrl=String.format(paramUrlFormat,t);
            Map resultMap = wxqyUserService.sendTextMessage(hrefType, hrefContentType, t, "", "", "text", msg, (short) 0,paramUrl);
            //return ((int) resultMap.get("errcode")) == 0;
        }
        String paramUrl = "/#/topic/list";
        wxSendMessageService.batchInsertWxMessage(msg,"0".equals(hrefType)?0:1,useridList,"6",hrefContentType,paramUrl);



        return false;
    }


    @GetMapping("/bookError/{bookId}/{submitId}/{correctId}")
    @ResponseBody
    public Map bookEoor(@PathVariable(value = "submitId") Long submitId,
                        @PathVariable(value = "bookId") Long bookId,
                        @PathVariable(value = "correctId")Long correctId ,HttpServletRequest request){
    //这本图书的图书的策划编辑
        Book book = bookDao.getBookById(bookId);



        Set<String> touserOpenidSet = new HashSet<String>();
        WriterUser submiter = writerUserService.get(submitId);
        List<PmphUser> admins = pmphUserService.getListByRole(1l);
        List<Long> useridList = new ArrayList<Long>();
        for (PmphUser admin: admins) {
            touserOpenidSet.add(admin.getOpenid());
            useridList.add(admin.getId());
        }
        touserOpenidSet.remove(null);
        String touser = touserOpenidSet.toString();
        String msg = submiter.getRealname()+"已经提交了《"+ (ObjectUtil.isNull(book)?"":book.getBookname())+"》的图书纠错信息，";
        Map resultMap = null;
        String paramUrlFormat = "&UserId=%s&bookName=%s&type=%s&id=%s";
        String hrefType = "5";
        String hrefContentType = "2";
        for (String t: touserOpenidSet) {
            String paramUrl=String.format(paramUrlFormat,t,book.getBookname(),"check",correctId);
            resultMap = wxqyUserService.sendTextMessage(hrefType, hrefContentType, t, "", "", "text", msg, (short) 0,paramUrl);
        }
        String paramUrlFormat1="/#/checkbook?bookName=%s&type=%s&id=%s";
        String paramUrl = String.format(paramUrlFormat1,"",book.getBookname(),"check",correctId);
        wxSendMessageService.batchInsertWxMessage(msg,"0".equals(hrefType)?0:1,useridList,"6",hrefContentType,paramUrl);


        return resultMap;
    }

    /*String[] openids = touser.split("\\|");
    List<Long> useridList = new ArrayList<Long>();
        for (String opend:openids) {
        PmphUser user = pmphUserService.getPmphUserByOpenid(opend);
        useridList.add(user.getId());
    }

        wxSendMessageService.batchInsertWxMessage(text,"0".equals(hrefType)?0:1,useridList,hrefType,hrefContentType,paramUrl);*/



}