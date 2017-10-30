package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.service.MaterialTypeService;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

@Controller
@RequestMapping(value = "/books")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BookController {

	@Autowired
	BookService bookService;
	@Autowired
	MaterialTypeService materialTypeService;

	@ResponseBody
	@RequestMapping(value = "/list/book", method = RequestMethod.GET)
	public ResponseBean listBookVO(Integer pageSize, Integer pageNumber, String name, Boolean isOnSale, Boolean isNew,
			Boolean isPromote, String path) {
		PageParameter<BookVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		BookVO bookVO = new BookVO();
		bookVO.setName(name);
		bookVO.setIsNew(isNew);
		bookVO.setPath(path);
		bookVO.setIsOnSale(isOnSale);
		bookVO.setIsPromote(isPromote);
		pageParameter.setParameter(bookVO);
		return new ResponseBean(bookService.listBookVO(pageParameter));
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
	@RequestMapping(value = "/update/book", method = RequestMethod.PUT)
	public ResponseBean updateBookById(Long[] ids, Long type, Boolean isOnSale, Boolean isNew, Boolean isPromote) {
		return new ResponseBean(bookService.updateBookById(ids, type, isOnSale, isNew, isPromote));
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
	@RequestMapping(value = "/list/materialtype", method = RequestMethod.GET)
	public ResponseBean listMaterialType(Long parentId) {
		return new ResponseBean(materialTypeService.listMaterialType(parentId));
	}
}
