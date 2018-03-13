package com.bc.pmpheep.back.controller.material;


import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.vo.MaterialListVO;
import com.bc.pmpheep.back.vo.MaterialVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.controller.ImageController;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * @author MrYang
 * @CreateDate 2017年11月14日 下午2:05:32
 * 
 **/
@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping(value = "/material")
public class MaterialController {
	
	Logger      logger = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private MaterialService materialService;

	private final String Business_Type = "教材";

	/**
	 * 新建遴选公告
	 * 
	 * @param sessionId
	 * @param material
	 *            教材对象
	 * @param materialContacts
	 *            多个教材联系人
	 * @param materialExtensions
	 *            多个教材扩展项
	 * @param MaterialProjectEditors
	 *            多个教材扩项目编辑
	 * @param materialExtra
	 *            教材通知备
	 * @param materialNoticeAttachments
	 *            多个教材通知
	 * @param noticeFiles
	 *            通知文件
	 * @param materialNoteAttachments
	 *            多个教材备注
	 * @param noteFiles
	 *            备注文件
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "新建遴选公告")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseBean add(HttpServletRequest request, MaterialVO materialVO,
			// MultipartFile[] noticeFiles,
			// MultipartFile[] noteFiles,
			String deadline, String actualDeadline, String ageDeadline) {

		try {
			materialVO.getMaterial().setDeadline(DateUtil.str3Date(deadline));
			materialVO.getMaterial().setActualDeadline(DateUtil.str3Date(actualDeadline));
			materialVO.getMaterial().setAgeDeadline(DateUtil.str3Date(ageDeadline));
			return new ResponseBean(materialService.addOrUpdateMaterial(request, materialVO,
					// noticeFiles,
					// noteFiles,
					false));
		} catch (IOException e) {
			ResponseBean responseBean = new ResponseBean(e);
			responseBean.setData("上传文件失败");
			return responseBean;
		} catch (Exception e) {
			ResponseBean responseBean = new ResponseBean(e);
			responseBean.setData(e.getMessage());
			return responseBean;
		}
	}

	/**
	 * 更新遴选公告
	 * 
	 * @param sessionId
	 * @param material
	 *            教材对象
	 * @param materialContacts
	 *            多个教材联系人
	 * @param materialExtensions
	 *            多个教材扩展项
	 * @param MaterialProjectEditors
	 *            多个教材扩项目编辑
	 * @param materialExtra
	 *            教材通知备
	 * @param materialNoticeAttachments
	 *            多个教材通知
	 * @param noticeFiles
	 *            通知文件
	 * @param materialNoteAttachments
	 *            多个教材备注
	 * @param noteFiles
	 *            备注文件
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "修改遴选公告")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseBean update(MaterialVO materialVO, HttpServletRequest request,
			// MultipartFile[] noticeFiles,
			// MultipartFile[] noteFiles,
			String deadline, String actualDeadline, String ageDeadline) {
		try {
			materialVO.getMaterial().setDeadline(DateUtil.str3Date(deadline));
			materialVO.getMaterial().setActualDeadline(DateUtil.str3Date(actualDeadline));
			materialVO.getMaterial().setAgeDeadline(DateUtil.str3Date(ageDeadline));

			return new ResponseBean(materialService.addOrUpdateMaterial(request, materialVO,
					// noticeFiles,
					// noteFiles,
					true));
		} catch (IOException e) {
			ResponseBean responseBean = new ResponseBean(e);
			responseBean.setData("上传文件失败");
			return responseBean;
		} catch (Exception e) {
			ResponseBean responseBean = new ResponseBean(e);
			responseBean.setData(e.getMessage());
			return responseBean;
		}
	}

	/**
	 * 
	 * 
	 * 功能描述：初始化/条件查询教材列表
	 * 
	 * @param request
	 * @param pageSize
	 *            当前页条数
	 * @param pageNumber
	 *            当前页数
	 * @param isMy
	 *            是否我的
	 * @param state
	 *            当前状态
	 * @param materialName
	 *            教材名称
	 * @param contactUserName
	 *            联系人名称
	 * @return
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "查询教材公告列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(HttpServletRequest request, Integer pageSize, Integer pageNumber, Boolean isMy,
			String state, String materialName, String contactUserName) {
		String sessionId = CookiesUtil.getSessionId(request);
		PageParameter<MaterialListVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		MaterialListVO materialListVO = new MaterialListVO();
		materialListVO.setIsMy(isMy);
		materialListVO.setState(state);
		materialListVO.setContactUserName(contactUserName);
		materialListVO.setMaterialName(materialName);
		pageParameter.setParameter(materialListVO);
		return new ResponseBean(materialService.listMaterials(pageParameter, sessionId));
	}

	/**
	 * 获取教材名称 通过主键id
	 * 
	 * @author Mryang
	 * @createDate 2017年11月29日 上午11:22:16
	 * @param id
	 *            教材id
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "根据id获取教材名称")
	@RequestMapping(value = "/materialName", method = RequestMethod.GET)
	public ResponseBean materialName(@RequestParam(value = "id", required = true) Long id) {
		return new ResponseBean(materialService.getMaterialNameById(id));
	}

	/**
	 * 获取教材主要信息，如教材名称，当前用户的权限菜单,以及教材的其他信息
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月14日 上午9:52:42
	 * @param materialId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "获取教材主要信息，如教材名称，当前用户的权限菜单,以及教材的其他信息")
	@RequestMapping(value = "/getMaterialMainInfoById", method = RequestMethod.GET)
	public ResponseBean getMaterialMainInfoById(@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(materialService.getMaterialMainInfoById(id, sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：逻辑删除教材
	 * 
	 * @param request
	 * @param id
	 *            教材id
	 * @return
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "逻辑删除教材")
	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	public ResponseBean delete(HttpServletRequest request, Long id) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(materialService.updateMaterial(id, sessionId));
	}

	/**
	 * 根据教材id获取更新教材视图数据
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年11月23日 下午5:54:04
	 * @param id
	 *            教材id
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "获取教材视图数据")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseBean get(Long id) {
		return new ResponseBean(materialService.getMaterialVO(id));
	}

	/**
	 * 教材上传临时附件
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月6日 上午11:38:32
	 * @param request
	 * @param files
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "上传临时附件")
	@RequestMapping(value = "/upTempFile", method = RequestMethod.POST)
	public ResponseBean upTempFile(HttpServletRequest request, MultipartFile[] files) {
		try {
			return new ResponseBean(materialService.upTempFile(request, files));
		} catch (CheckedServiceException e) {
			return new ResponseBean(e);
		} catch (IOException e) {
			return new ResponseBean(e);
		} catch (Exception e) {
			return new ResponseBean(e);
		}
	}
	
	
	/**
     * 获取临时文件
     * 
     * @param tempId  
     * @param response 服务响应
     */
	@LogDetail(businessType = Business_Type, logRemark = "获取临时文件")
    @RequestMapping(value = "getTempFile", method = RequestMethod.GET)
    public void getTempFile(String tempFileId, HttpServletRequest request,HttpServletResponse response) {
    	byte[] fileByte = (byte[]) request.getSession(false).getAttribute(tempFileId);
		response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            out.write(fileByte, 0, fileByte.length);
            out.flush();
            out.close();
        } catch (IOException ex) {
            logger.error("文件下载时出现IO异常：{}", ex.getMessage());
        } catch (Exception ex) {
            logger.warn("图片查看时出现异常：{}", ex.getMessage());
        }
    }

	/**
	 * 
	 * 
	 * 功能描述：书籍页面获取所有已经结束的教材
	 * 
	 * @param materialName
	 *            教材名称
	 * @return
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "书籍页面获取所有已经结束的教材")
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ResponseBean book(String materialName) {
		return new ResponseBean(materialService.listBook(materialName));
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：获取已经发布教材信息
	 * 使用示范：
	 *
	 * &#64;return Material对象集合
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "获取已经发布教材信息")
	@RequestMapping(value = "/published", method = RequestMethod.GET)
	public ResponseBean published() {
		return new ResponseBean(materialService.listPublishedMaterial());
	}
}
