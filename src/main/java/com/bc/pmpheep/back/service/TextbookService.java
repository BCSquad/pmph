package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.bo.DecPositionBO;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.vo.BookListVO;
import com.bc.pmpheep.back.vo.BookPositionVO;
import com.bc.pmpheep.back.vo.ExcelDecAndTextbookVO;
import com.bc.pmpheep.back.vo.TextbookDecVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * TextbookService 接口
 * 
 * @author 曾庆峰
 * 
 */
public interface TextbookService {

    /**
     * 新增一个Textbook
     * 
     * @param Textbook 实体对象
     * @return 带主键的 Textbook thorws CheckedServiceException
     */
    Textbook addTextbook(Textbook textbook) throws CheckedServiceException;

    /**
     * 查询一个 Textbook 通过主键id
     * 
     * @param id
     * @return Textbook
     * @throws CheckedServiceException
     */
    Textbook getTextbookById(Long id) throws CheckedServiceException;

    /**
     * 判断是否为策划编辑
     * 
     * @param id
     * @return Textbook
     * @throws CheckedServiceException
     */
    List<Textbook> getTextbookByMaterialIdAndUserId(Long materialId, Long userId)
    throws CheckedServiceException;

    /**
     * 删除Textbook 通过主键id
     * 
     * @param Textbook
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteTextbookById(Long id) throws CheckedServiceException;

    /**
     * 更新一个 Textbook通过主键id
     * 
     * @param Textbook
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateTextbook(Textbook textbook) throws CheckedServiceException;

    /**
     * 
     * Description:根据教材id获取教材书籍列表
     * 
     * @author:lyc
     * @date:2017年11月23日上午9:56:09
     * @param
     * @return List<BookListVO>
     */
    List<BookListVO> getBookListVOs(Long materialId) throws CheckedServiceException;

    /**
     * 
     * Description:添加或更新教材书籍
     * 
     * @author:lyc
     * @date:2017年11月23日下午1:56:50
     * @param
     * @return List<Textbook>
     */
    List<Textbook> addOrUpdateTextBookList(Long materialId, Boolean isPublic, String textbooks,
    String sessionId) throws CheckedServiceException;

    /**
     * 
     * Description:Excel导入教材书籍
     * 
     * @author:lyc
     * @date:2017年11月26日下午8:36:21
     * @param
     * @return List<Textbook>
     */
    List<Textbook> importExcel(MultipartFile file, Long materialId) throws CheckedServiceException,
    IOException;

    /**
     * 功能描述：批量结果公布（最终结果公布）
     * 
     * @param ids
     * @return
     * @throws CheckedServiceException
     */
    Integer updateTextbookAndMaterial(Long[] ids, String sessionId) throws CheckedServiceException;

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
    List<Textbook> getTextbookByMaterialId(Long materialId) throws CheckedServiceException;

    /**
     * 初始化书籍职位列表
     * 
     * @author Mryang
     * @createDate 2017年11月24日 下午3:52:23
     * @param pageNumber
     * @param pageSize
     * @param state 0全部 1名单没有确认 2名单已确认 3 结果已经公布 4强制结束
     * @param textBookIds [1,2,3,4,5]
     * @param materialId
     * @param sessionId
     * @return
     */
    PageResult<BookPositionVO> listBookPosition(Integer pageNumber, Integer pageSize,
    Integer state, String textBookIds, String bookName, Long materialId, String sessionId);

    /**
     * 功能描述：批量通过（确认名单）
     * 
     * @param textBookIds
     * @param isLocked
     * @return
     * @throws CheckedServiceException
     */
    Integer updateTextbooks(Long[] ids, String sessionId) throws CheckedServiceException;

    /**
     * 
     * Description:获取设置选题号书籍列表
     * 
     * @author:lyc
     * @date:2017年11月27日上午9:51:36
     * @param
     * @return List<Textbook>
     */
    List<Textbook> listTopicNumber(Long materialId) throws CheckedServiceException;

    /**
     * 
     * Description:设置教材书籍选题号
     * 
     * @author:lyc
     * @date:2017年11月27日上午9:52:25
     * @param
     * @return List<Textbook>
     */
    List<Textbook> addTopicNumber(String topicTextbooks) throws CheckedServiceException;

    /**
     * 
     * Description:设置选题号页面导入Excel文件
     * 
     * @author:lyc
     * @date:2018年1月22日下午5:16:16
     * @param
     * @return List<Textbook>
     */
    List<Textbook> importTopicExcel(MultipartFile file) throws CheckedServiceException, IOException;

    /**
     * 通过书籍id查询对应已通过的主编和编委
     * 
     * @param textbookId
     * @return
     */
    PageResult<TextbookDecVO> listEditorSelection(PageParameter<TextbookDecVO> pageParameter)
    throws CheckedServiceException;

    /**
     * 通过书籍id查询对应的主编和编委
     * 
     * @param textbookIds
     * @return
     * @throws CheckedServiceException
     */
    List<ExcelDecAndTextbookVO> getExcelDecAndTextbooks(Long[] textbookIds)
    throws CheckedServiceException;

    /**
     * 通过教材id获取所有书籍name
     * 
     * @param materialId
     * @return
     */
    List<Textbook> getTextbooknameList(Long materialId);

    /**
     * 通过教材id查询该教材的主编/副主编
     * 
     * @param textbookIds
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
    Integer updatRevisionTimesByTextBookId(Integer number, Long textBookId)
    throws CheckedServiceException;

}
