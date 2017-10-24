package com.bc.pmpheep.back.controller.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * @author MrYang
 * @CreateDate 2017年9月25日 下午2:56:37
 *
 **/
@Controller
@RequestMapping(value = "/area")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AreaController {

	@Autowired
	private AreaService areaService;

	/**
	 * 根据父级id获取下一级子节点
	 * 
	 * @author Mryang
	 * @createDate 2017年9月30日 下午3:00:28
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/areachirldren", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean getAreaChirldren(Long parentId) {
		return new ResponseBean(areaService.getAreaChirldren(parentId));
	}

	/**
	 * 通过parentId获取树
	 * 
	 * @author Mryang
	 * @createDate 2017年9月25日 下午3:00:28
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/areatree", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean getAreaTree(Long parentId) {
		return new ResponseBean(areaService.getAreaTreeVO(parentId));
	}

	/**
	 * 
	 * 添加area 对应前台的添加节点（子节点和根节点）
	 * 
	 * @author Mryang
	 * @createDate 2017年9月25日 下午3:01:54
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/add/area", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBean addArea(Area area) {
		return new ResponseBean(areaService.addArea(area));
	}

	/**
	 * 
	 * 根据id删除当前area和他下面的area (前台的删除)
	 * 
	 * @author Mryang
	 * @createDate 2017年9月25日 下午3:03:23
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/areabatch", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseBean deleteAreaBatch(Long id) {
		return new ResponseBean(areaService.deleteAreaBatch(id));
	}

	/**
	 * 更新一个 Area通过主键id
	 * 
	 * @author Mryang
	 * @createDate 2017年9月25日 下午3:04:37
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/update/area", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseBean updateArea(Area area) {
		return new ResponseBean(areaService.updateArea(area));
	}
}
