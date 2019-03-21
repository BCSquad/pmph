package com.bc.pmpheep.back.controller;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DataDictionaryItem;
import com.bc.pmpheep.back.po.DataDictionaryType;
import com.bc.pmpheep.back.service.DataDictionaryService;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("dataDictionary")
public class DataDictionaryController {

    @Autowired
    DataDictionaryService dicService;

    /**
     * 获取数据字典类型列表
     * @param request
     * @param dataDictionaryType
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/typeList")
    @ResponseBody
    public ResponseBean typeList(HttpServletRequest request,DataDictionaryType dataDictionaryType,
                                 @RequestParam(value = "pageNumber", required = false)    Integer pageNumber,
                                 @RequestParam(value = "pageSize",   required = false)	Integer pageSize){
        PageResult<DataDictionaryType> pageResult = dicService.getTypeList(dataDictionaryType,pageNumber,pageSize);
        return new ResponseBean(pageResult);
    }

    /**
     * 新增或修改数据字典类型
     * @param request
     * @param dataDictionaryType
     * @return
     * @throws CheckedServiceException
     */
    @RequestMapping("/type/add")
    @ResponseBody
    public ResponseBean typeAdd(HttpServletRequest request,DataDictionaryType dataDictionaryType) throws CheckedServiceException{
        int result = dicService.typeAdd(dataDictionaryType);
        return new ResponseBean(dataDictionaryType);
    }


    /**
     * 获取数据字典项列表
     * @param request
     * @param dataDictionaryItem
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/itemList")
    @ResponseBody
    public ResponseBean itemList(HttpServletRequest request,DataDictionaryItem dataDictionaryItem,
                                 @RequestParam(value = "pageNumber", required = false)    Integer pageNumber,
                                 @RequestParam(value = "pageSize",   required = false)	Integer pageSize){
        PageResult<DataDictionaryItem> pageResult = dicService.getItemList(dataDictionaryItem,pageNumber,pageSize);
        return new ResponseBean(pageResult);
    }

    /**
     * 新增或修改数据字典项
     * @param request
     * @param dataDictionaryItem
     * @return
     * @throws CheckedServiceException
     */
    @RequestMapping("/item/add")
    @ResponseBody
    public ResponseBean itemAdd(HttpServletRequest request,DataDictionaryItem dataDictionaryItem) throws CheckedServiceException{
        int result = dicService.itemAdd(dataDictionaryItem);
        return new ResponseBean(dataDictionaryItem);
    }
}
