package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.bo.DecPositionBO;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.vo.BookListVO;
import com.bc.pmpheep.back.vo.BookPositionVO;
import com.bc.pmpheep.back.vo.ExcelDecAndTextbookVO;
import com.bc.pmpheep.back.vo.TextbookDecVO;

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
     * @param textbook 实体对象
     * @return 影响行数
     */
    Integer addTextbook(Textbook textbook);

    /**
     * 删除Textbook 通过主键id
     *
     * @return 影响行数
     */
    Integer deleteTextbookById(Long id);

    /**
     * 更新一个 Textbook通过主键id
     * 
     * @param textbook
     * @return 影响行数
     */
    Integer updateTextbook(Textbook textbook);

    /**
     * 查询一个 Textbook 通过主键id
     * 
     *  Textbook 必须包含主键ID
     * @return Textbook
     */
    Textbook getTextbookById(Long id);

    /**
     * 通过用户id与教材id查询书籍集合
     * 
     *  Textbook 必须包含主键ID
     * @return Textbook
     */
    List<Textbook> getTextbookByMaterialIdAndUserId(@Param("materialId") Long materialId,
    @Param("userId") Long userId);

    /**
     * 
     * <pre>
	 * 功能描述：查询表单的数据总条数
	 * 使用示范：
	 *
	 * &#64;return 表单的数据总条数
	 * </pre>
     */
    Long getTextbookCount();

    /**
     * 
     * <pre>
	 * 功能描述：根据教材Id查询对应的书籍集合
	 * 使用示范：
	 *
	 * &#64;param materialId 教材Id
	 * &#64;return
	 * </pre>
     */
    List<Textbook> getTextbookByMaterialId(@Param("materialId") Long materialId);

    /**
     *
     * <pre>
     * 功能描述：根据教材Id查询对应的书籍集合
     * 使用示范：
     *
     * &#64;param materialId 教材Id
     * &#64;return
     * </pre>
     */
    List<Textbook> getTextbookByMaterialIdAndSurveyId(@Param("materialId") Long materialId,@Param("materialSurveyId") Long materialSurveyId);

    /**
     * 职位遴选界面书籍总条数
     * 
     * @author Mryang
     * @createDate 2017年11月21日 下午5:25:33
     * @param pageParameter
     * @return 总条数
     */
    Integer listBookPositionTotal(PageParameter<Map<String, Object>> pageParameter);

    /**
     * listBookPosition
     * 
     * @author Mryang
     * @createDate 2017年11月21日 下午5:25:58
     * @param pageParameter
     * @return 分页书籍列表数据
     */
    List<BookPositionVO> listBookPosition(PageParameter<Map<String, Object>> pageParameter);

    /**
     * 
     * @param textBookIds
     * @return 获取书籍列表
     */
    List<Textbook> getTextbooks(Long[] textBookIds);

    /**
     * 批量通过（名单确认）
     *
     *  isLocked
     * @return
     */
    Integer updateTextbooks(List<Textbook> textBook);

    /**
     * 
     * Description:添加或更新教材书籍
     * 
     * @author:lyc
     * @date:2017年11月23日上午11:45:18
     * @param
     * @return Integer
     */
    Integer addOrUpdateTextBookList(BookListVO bookListVO);

    /**
     * 书籍结果公布
     * 
     * @param textBooks
     * @return
     */
    Integer updateBookPublished(List<Textbook> textBooks);

    /**
     * 
     * Description:设置选题号页面获取书籍列表
     * 
     * @author:Administrator
     * @date:2017年11月27日上午9:48:57
     * @param
     * @return List<Textbook>
     */
    List<Textbook> listTopicNumber(Long materialId);

    /**
     * 通过书籍id 查询该书籍对应关系
     *
     * @return
     */
    List<TextbookDecVO> getTextbookDecVOList(PageParameter<TextbookDecVO> pageParameter);

    /**
     * 通过书籍id 查询该书籍对应主编和编委的总条数
     * 
     * @param pageParameter
     * @return
     */
    Integer getTextbookDecTotal(PageParameter<TextbookDecVO> pageParameter);

    /**
     * 通过书籍id查询该书籍下面的主编、编委
     * 
     * @param textbookIds
     * @return
     */
    List<ExcelDecAndTextbookVO> getExcelDecAndTextbooks(Long[] textbookIds);

    /**
     * 通过教材id查询该教材的主编/副主编
     *
     * @return
     */
    List<DecPositionBO> getExcelDecByMaterialId(Long[] textbookIds);

    /**
     * 
     * <pre>
     * 功能描述：遴选编委后修改 再次修改次数
     * 使用示范：
     *
     * @param number 修改次数（+1/-1）
     * @param textBookId 书籍ID
     * @return
     * </pre>
     */
    Integer updatRevisionTimesByTextBookId(@Param("number") Integer number,
    @Param("textBookId") Long textBookId);

    Integer listBookPositionTotal_up1(PageParameter<Map<String, Object>> pageParameter);

    List<BookPositionVO> listBookPosition_up1(PageParameter<Map<String, Object>> pageParameter);
    List<BookPositionVO> listBookPosition_up12(PageParameter<Map<String, Object>> pageParameter);

    List<Map<String,Object>> listBookPosition_up1_ids(PageParameter<Map<String, Object>> pageParameter);
}
