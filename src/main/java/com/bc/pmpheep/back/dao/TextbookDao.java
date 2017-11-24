package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.vo.BookPositionVO;

/**
 * TextbookDao实体类数据访问层接口
 * 
 * @author 曾庆峰
 * 
 */
@Repository
public interface TextbookDao {

    /**
     * 新增一个Textbook
     * 
     * @param Textbook 实体对象
     * @return 影响行数
     */
    Integer addTextbook(Textbook textbook);

    /**
     * 删除Textbook 通过主键id
     * 
     * @param Textbook
     * @return 影响行数
     */
    Integer deleteTextbookById(Long id);

    /**
     * 更新一个 Textbook通过主键id
     * 
     * @param Textbook
     * @return 影响行数
     */
    Integer updateTextbook(Textbook textbook);

    /**
     * 查询一个 Textbook 通过主键id
     * 
     * @param Textbook 必须包含主键ID
     * @return Textbook
     */
    Textbook getTextbookById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的数据总条数
     * </pre>
     */
    Long getTextbookCount();

    /**
     * 
     * <pre>
     * 功能描述：根据教材Id查询对应的书籍集合
     * 使用示范：
     *
     * @param materialId 教材Id
     * @return
     * </pre>
     */
    List<Textbook> getTextbookByMaterialId(@Param("materialId") Long materialId);
    
    /**
     * 职位遴选界面书籍总条数
     * @author Mryang
     * @createDate 2017年11月21日 下午5:25:33
     * @param pageParameter
     * @return 总条数
     */
    Integer listBookPositionTotal(PageParameter<Map<String, Object>> pageParameter);
    
    /**
     * listBookPosition
     * @author Mryang
     * @createDate 2017年11月21日 下午5:25:58
     * @param pageParameter
     * @return 分页书籍列表数据
     */
    List<BookPositionVO> listBookPosition (PageParameter<Map<String, Object>> pageParameter);
    
    /**
     * 
     * @param textBookIds
     * @return 获取书籍列表
     */
    List<Textbook> getTextbooks(Long[] textBookIds);
    
    /**	
     * 批量通过（名单确认）
     * @param textbooks
     * @param isLocked
     * @return 
     */
	Integer updateTextbooks(List<Textbook> textBook);

}
