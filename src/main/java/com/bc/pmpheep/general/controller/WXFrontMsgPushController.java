package com.bc.pmpheep.general.controller;

import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.service.MaterialProjectEditorService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.vo.MaterialProjectEditorVO;
import com.bc.pmpheep.wx.service.WXQYUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public void helloBack(HttpServletRequest request){
        String msg = request.getParameter("msg");
        System.out.printf("hello"+msg);
    }

    @RequestMapping(value = "/projectEditorPleaseAdit/{id}",method = RequestMethod.GET)
    public boolean projectEditorPleaseAdit(HttpServletRequest request, @PathVariable(value = "id",required = true)Long decId, HttpServletResponse response){
        //企业微信推送对象的微信id集合
        Set<String> touserOpenidSet = new HashSet<String>();
        Declaration dec = declarationService.getDeclarationById(decId);
        dec.getRealname();
        Material material = materialService.getMaterialById(dec.getMaterialId());
        List<MaterialProjectEditorVO> materialProjectEditorVOList = materialProjectEditorService.listMaterialProjectEditors(dec.getMaterialId());

        for (MaterialProjectEditorVO materialProjectEditorVO:materialProjectEditorVOList) {
            PmphUser projectEditorUser = pmphUserService.get(materialProjectEditorVO.getEditorId());
            //项目编辑加入企业微信推送对象集合
            touserOpenidSet.add(projectEditorUser.getOpenid());
        }
        touserOpenidSet.remove(null);
        String touser = touserOpenidSet.toString();
        //***（作者人名）已提交《****》（教材名）的申报表，请审核
        String msg = dec.getRealname()+"已提交《"+material.getMaterialName()+"》的申报表，";//“请审核” 已被超链接补齐，此处不需显示
        String url = "/materialrouter/materialnav/"+decId+"/presscheck";
        if (touserOpenidSet.size() > 0) {
            Map resultMap = wxqyUserService.sendTextMessage("2", "2", touser, "", "", "text", msg, (short) 0);
            return ((int)resultMap.get("errcode"))==0;
        }

        return false;
    }


//    @RequestMapping(value = "/projectEditorPleaseAdit/{id}",method = RequestMethod.GET)
//    public boolean wxBookErrorShenHe(String submitName,String bookId,HttpServletRequest request){
//
//    }
}


