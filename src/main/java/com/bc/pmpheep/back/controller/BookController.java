package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.service.MaterialTypeService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/books")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BookController {

	@Autowired
	BookService bookService;
	@Autowired
	MaterialTypeService materialTypeService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "图书管理";

	/**
	 * 
	 * 
	 * 功能描述：初始化/条件查询书籍信息
	 *
	 * @param pageSize
	 *            当页条数
	 * @param pageNumber
	 *            当前页数
	 * @param type
	 *            书籍类别
	 * @param name
	 *            书籍名称/ISBN
	 * @param isOnSale
	 *            是否上架
	 * @param isNew
	 *            是否新书
	 * @param isPromote
	 *            是否推荐
	 * @param path
	 *            书籍类别根路径
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "初始化/条件查询书籍信息")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(Integer pageSize, Integer pageNumber, Long type, String name, Boolean isOnSale,
			Boolean isNew, Boolean isPromote, String path) {
		PageParameter<BookVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		BookVO bookVO = new BookVO();
		bookVO.setName(name);
		bookVO.setIsNew(isNew);
		bookVO.setPath(path);
		bookVO.setType(type);
		bookVO.setIsOnSale(isOnSale);
		bookVO.setIsPromote(isPromote);
		pageParameter.setParameter(bookVO);
		return new ResponseBean(bookService.listBookVO(pageParameter));
	}

	/**
	 * 功能描述: 初始化/条件查询推荐书籍信息
	 * @param recommendPageSize
	 * @param recommendPageNumber
	 * @param currentBookId
	 * @param ischeckteachbook
	 * @param ischeckxgcommend
	 * @param ischeckrwcommend
	 * @param name
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "初始化/条件查询推荐书籍信息")
	@RequestMapping(value = "/recommendlist", method = RequestMethod.GET)
	public ResponseBean recommendlist(Integer recommendPageSize, Integer recommendPageNumber, Long currentBookId,Boolean ischeckteachbook,Boolean ischeckxgcommend,Boolean ischeckrwcommend,String name) {
		PageParameter<BookVO> pageParameter = new PageParameter<>(recommendPageNumber, recommendPageSize);
		BookVO bookVO = new BookVO();
		bookVO.setName(name);
		bookVO.setId(currentBookId);
		bookVO.setIscheckteachbook(ischeckteachbook);
		bookVO.setIscheckrwcommend(ischeckrwcommend);
		bookVO.setIscheckxgcommend(ischeckxgcommend);
		pageParameter.setParameter(bookVO);
		return new ResponseBean(bookService.recommendlist(pageParameter));
	}
	/**
	 * 功能描述: 推荐书籍
	 * @param currentBookId
	 * @param ischeckteachbook
	 * @param ischeckxgcommend
	 * @param ischeckrwcommend
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "推荐书籍")
	@RequestMapping(value = "/recommendcheck", method = RequestMethod.GET)
	public ResponseBean recommendcheck(Long currentBookId,int selectType,Long recommendBookId,Boolean ischeckteachbook,Boolean ischeckxgcommend,Boolean ischeckrwcommend) {


		return new ResponseBean(bookService.recommendcheck( currentBookId, selectType, recommendBookId, ischeckteachbook, ischeckxgcommend, ischeckrwcommend));
	}

	/**
	 * 
	 * 
	 * 功能描述：修改单个/多个书籍详情
	 *
	 * @param ids
	 *            书籍id
	 * @param type
	 *            书籍类型id
	 * @param isOnSale
	 *            是否上架
	 * @param isNew
	 *            是否新书
	 * @param isPromote
	 *            是否推荐
	 * @return 是否成功
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改单个/多个书籍详情")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseBean update(Long[] ids, Long type, Boolean isOnSale, Boolean isNew, Boolean isPromote,
			Long materialId,Boolean isKey,Boolean isStick) {
		return new ResponseBean(bookService.updateBookById(ids, type, isOnSale, isNew, isPromote, materialId,isKey,isStick));
	}

	/**
	 * 
	 * 
	 * 功能描述：获取所有书籍类别
	 *
	 * @param parentId
	 *            父级id
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取所有书籍类别")
	@RequestMapping(value = "/list/materialType", method = RequestMethod.GET)
	public ResponseBean materialType(Long parentId) {
		return new ResponseBean(materialTypeService.listMaterialType(parentId));
	}

	/**
	 * 
	 * 
	 * 功能描述：根据书籍id删除书籍
	 *
	 * @param id
	 *            书籍id
	 * @return 是否成功
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除书籍")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseBean delete(Long id) {
		return new ResponseBean(bookService.deleteBookById(id));
	}

	/**
	 * 
	 * 
	 * 功能描述：商城更新图书的接口
	 *
	 * @param noteicetype
	 *            通知类型 0：修改
	 * @param key
	 *            本版号
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "商城更新图书")
	@RequestMapping(value = "/abuttingjoint", method = RequestMethod.GET)
	public ResponseBean abuttingjoint(Integer noteicetype, String[] key) {
		return new ResponseBean(bookService.AbuttingJoint(key, noteicetype));
	}

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "图书配套导入")
	@RequestMapping(value = "/bookExcel", method = RequestMethod.POST)
	public ResponseBean bookExcel(@RequestParam("file") MultipartFile file) {
		return new ResponseBean<>(bookService.bookExcel(file));
	}

	/**
	 * 
	 * 
	 * 功能描述：图书同步 1：全量同步 2：增量同步
	 *
	 * @param type
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "图书同步")
	@RequestMapping(value = "/allsynchronization", method = RequestMethod.GET)
	public synchronized ResponseBean allsynchronization(Integer type) {
		return new ResponseBean(bookService.AllSynchronization(type));
	}

	/**
	 * 
	 * 
	 * 功能描述：获取图书是否同步完成
	 *
	 * @param type
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取图书是否同步完成")
	@RequestMapping(value = "/isEnd", method = RequestMethod.GET)
	public ResponseBean isEnd() {
		return new ResponseBean(Const.AllSYNCHRONIZATION);
	}
}
