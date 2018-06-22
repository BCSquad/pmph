package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import com.bc.pmpheep.back.po.Textbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialTypeDao;
import com.bc.pmpheep.back.po.MaterialType;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.MaterialTypeVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialTypeService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class MaterialTypeServiceImpl extends BaseService implements MaterialTypeService {

	@Autowired
	private MaterialTypeDao materialTypeDao;

	/**
	 * 
	 * @param materialType
	 *            实体对象
	 * @return 带主键的 MaterialType
	 * @throws CheckedServiceException
	 */
	@Override
	public MaterialType addMaterialType(MaterialType materialType) throws CheckedServiceException {
		if (null == materialType.getParentId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"上级类型为空");
		}
		if (StringUtil.isEmpty(materialType.getPath())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"节点路径为空");
		}
		if (StringUtil.isEmpty(materialType.getTypeName())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"类型名称为空");
		}
		if (ObjectUtil.isNull(materialType.getSort())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"显示顺序为空");
		}
		if (StringUtil.isEmpty(materialType.getNote())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"备注为空");
		}
		materialTypeDao.addMaterialType(materialType);
		return materialType;
	}

	/**
	 * 
	 * @param id
	 * @return MaterialType
	 * @throws CheckedServiceException
	 */
	@Override
	public MaterialType getMaterialTypeById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return materialTypeDao.getMaterialTypeById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteMaterialTypeById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
        List<MaterialTypeVO> childMaterialType = materialTypeDao.listMaterialType(id);
        if (childMaterialType.size() > 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,
                    CheckedExceptionResult.ILLEGAL_PARAM, "分类下还有子分类，不能删除分类");
        }

        int countMaterial = materialTypeDao.queryMaterialByMaterialTypeId(id);
        if (countMaterial > 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,
                    CheckedExceptionResult.ILLEGAL_PARAM, "分类下有教材，不能删除分类");
        }
        int countBook = materialTypeDao.queryBookByMaterialTypeId(id);
        if (countBook > 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,
                    CheckedExceptionResult.ILLEGAL_PARAM, "分类下有已出版的图书，不能删除分类");
        }

		return materialTypeDao.deleteMaterialTypeById(id);
	}

	/**
	 * 根据主键id更新materialType 不为null和不为‘’的字段
	 * 
	 * @param materialType
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateMaterialType(MaterialType materialType) throws CheckedServiceException {
		if (ObjectUtil.isNull(materialType.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return materialTypeDao.updateMaterialType(materialType);
	}


	/**
	 * 非懒加载 利用迭代全部查询分类 并封装成树状结构
	 * @param parentId
	 * @return
	 * @throws CheckedServiceException
	 */
	@Override
	public MaterialTypeVO listMaterialType(Long parentId) throws CheckedServiceException {
		if (null == parentId) {
			parentId = Const.MATERIAL_TYPE_ID;
		}
		Long id = parentId;
		MaterialTypeVO materialTypeVO = new MaterialTypeVO();
		materialTypeVO.setId(id);
		recursionMaterialTypeVO(materialTypeVO, new ArrayList<Long>(16));
		return materialTypeVO;
	}

	/**
	 * 用于tree懒加载
	 * @param parentId
	 * @return
	 */
	@Override
	public MaterialTypeVO lazyListMaterialType(Long parentId) {
		MaterialTypeVO materialTypeVO = new MaterialTypeVO();
		if(parentId<=0){ // 初始化，数据库并没有id为0 父级为-1的层级 此处生成此对象
			materialTypeVO.setId(0l);
			materialTypeVO.setParentId(0l);
			materialTypeVO.setSort(1);
			materialTypeVO.setIsLeaf(false);
			materialTypeVO.setTypeName("教材分类");
			materialTypeVO.setNote("根节点");
			materialTypeVO.setPath("-");
			List<MaterialTypeVO> pm = materialTypeDao.lazyQueryListMaterialType(0l);
			materialTypeVO.setChildrenMaterialTypeVO(pm);
		}else{ //若有传入 则parentId为当前点击节点的id
			materialTypeVO = materialTypeDao.getMaterialTypeVoById(parentId);
			List<MaterialTypeVO> cm = materialTypeDao.lazyQueryListMaterialType(parentId);
			materialTypeVO.setChildrenMaterialTypeVO(cm);
		}

		return materialTypeVO;
	}

	/**
	 * 
	 * 功能描述：使用递归的方法将书籍类别转化为树状图 使用示范：
	 *
	 * @param materialTypeVO
	 *            ids (为后面删除做准备) 父级部门
	 */
	private void recursionMaterialTypeVO(MaterialTypeVO materialTypeVO, List<Long> ids) {
		List<MaterialTypeVO> list = materialTypeDao.listMaterialType(materialTypeVO.getId());
		if (null != list && list.size() > 0) {
			materialTypeVO.setChildrenMaterialTypeVO(list);
			materialTypeVO.setIsLeaf(false);
			for (MaterialTypeVO typeVO : list) {
				ids.add(typeVO.getId());
				recursionMaterialTypeVO(typeVO, ids);
			}
		}
	}

}
