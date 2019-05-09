/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.vo.DecPositionDisplayVO;
import com.bc.pmpheep.back.vo.DecPositionEditorSelectionVO;
import com.bc.pmpheep.back.vo.DeclarationResultBookVO;
import com.bc.pmpheep.back.vo.DeclarationResultSchoolVO;
import com.bc.pmpheep.back.vo.DeclarationSituationBookResultVO;
import com.bc.pmpheep.back.vo.DeclarationSituationSchoolResultVO;
import com.bc.pmpheep.back.vo.TextBookDecPositionVO;
import com.bc.pmpheep.back.vo.TextbookDecVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:作家申报职位表实体类
 * <p>
 * <p>
 * Description:作家申报职位表 数据访问层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年10月9日 下午4:42:32
 */

@Repository
public interface DecPositionDao {
    /**
     * 
     * Description:新增一个作家申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:45:24
     * @Param:
     * @Return:Integer
     */
    Integer addDecPosition(DecPosition decPosition);

    /**
     * 
     * Description:根据id删除一个作家申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:48:01
     * @Param:
     * @Return:Integer
     */
    Integer deleteDecPosition(Long id);

    /**
     * 
     * Description:更新作家申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:48:46
     * @Param:
     * @Return:Integer
     */
    Integer updateDecPosition(DecPosition decPosition);

    /**
     * 
     * Description:根据id查询一个作家申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:52:40
     * @Param:
     * @Return:DecPosition
     */
    DecPosition getDecPositionById(Long id);

    /**
     * 根据orgid和bookid获取该机构某些已公布的书的申报职位
     * 
     * @author Mryang
     * @createDate 2017年11月19日 上午10:13:40
     * @param map
     * @return
     */
    List<DecPosition> listDecPositionsByTextbookIdAndOrgid(Map<String, Object> map);

    /**
     * 
     * <pre>
     * 功能描述：教材申报-遴选主编/遴选编委 (根据书籍Id查询所有申报id)
     * 使用示范：
     *
     * @param textbookId  书籍Id
     * @return 所有申报id
     * </pre>
     */
    List<Long> getDecPositionIdByBookId(@Param("textbookId") Long textbookId,
    @Param("editorOrSubeditorType") Integer editorOrSubeditorType);

    /**
     * 
     * <pre>
     * 功能描述：教材申报-遴选主编/遴选编委 (更新前先初始化)
     * 使用示范：
     *
     * @param ids 主键ID集合
     * @param editorOrSubeditorType 主编，副主编/编委(1:主编，副主编，2：编委)
     * @return 影响行数 
     * </pre>
     */
    Integer updateDecPositionSetDefault(@Param("ids") List<Long> ids,
    @Param("editorOrSubeditorType") Integer editorOrSubeditorType);

    /**
     * 
     * Description:根据申报表id查询申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:54:30
     * @Param:
     * @Return:List<DecPosition>
     */
    List<DecPosition> listDecPositions(Long declarationId);

    /**
     * 
     * 
     * 功能描述：获取申报书籍和职位
     * 
     * @param declarationId 申报表id
     * @return
     * 
     */

    Map<String, String> getTextbookNameAndPresetPosition(Long declarationId);

    /**
     * 根据书籍id获取申报职位
     * 
     * @author Mryang
     * @createDate 2017年11月16日 下午2:37:19
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPosition> listDecPositionsByTextbookId(Long textbookId);

    /**
     * 根据书籍ids获取申报职位
     * 
     * @author Mryang
     * @createDate 2017年11月16日 下午2:37:19
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPosition> listDecPositionsByTextBookIds(@Param("textbookIds") List<Long> textbookIds);

    /**
     * 根据书籍id获取入选的职位职位(主编、副主编、编委、数字编辑)
     * 
     * @author Mryang
     * @createDate 2017年11月16日 下午2:37:19
     * @param textbookId
     * @return
     */
    List<DecPosition> listChosenDecPositionsByTextbookId(Long textbookId);

    /**
     * 
     * <pre>
	 * 功能描述：根据书籍id获取申报表id
	 * 使用示范：
	 *
	 * &#64;param textbookIds textbookIds 书籍id数组
	 * &#64;return 申报表id结果集
	 * </pre>
     */
    List<Long> listDecPositionsByTextbookIds(String[] textbookIds);

    /**
     * 
     * <pre>
	 * 功能描述：教材申报-遴选主编/遴选编委(列表)
	 * 使用示范：
	 *
	 * &#64;param textbookId 书籍ID
	 * &#64;param realName 申报人姓名
	 * &#64;param presetPosition 申报职位
	 * &#64;return
	 * </pre>
     */
    List<DecPositionEditorSelectionVO> listEditorSelection(@Param("textbookId") Long textbookId,
    @Param("realName") String realName, @Param("orgName") String orgName);

    /**
     * 
     * <pre>
	 * 功能描述：教材申报-遴选主编/遴选编委(更新)
	 * 使用示范：
	 *
	 * &#64;param decPositions DecPosition 对象集合
	 * &#64;return
	 * </pre>
     */
    Integer updateDecPositionEditorSelection(List<DecPosition> decPositions);

    /**
     * 
     * Description:根据申报表id查询申报职位信息并显示图书名字
     * 
     * @author:tyc
     * @date:2017年11月29日上午8:40:37
     * @Param:
     * @Return:List<DecPosition>
     */
    List<DecPositionDisplayVO> listDecPositionsOrBook(Long declarationId);

    /**
     * 
     * Description:根据教材id（和学校名称）查询学校申报情况
     * 
     * @author:lyc
     * @date:2017年12月1日下午2:30:41
     * @param
     * @return List<DeclarationSituationSchoolResultVO>
     */
    List<DeclarationSituationSchoolResultVO> getSchoolResult(
    PageParameter<DeclarationSituationSchoolResultVO> pageParameter);

    /**
     * 
     * Description:根据教材id（和学校名）查询统计结果
     * 
     * @author:lyc
     * @date:2017年12月1日下午5:56:59
     * @param
     * @return List<DeclarationResultSchoolVO>
     */
    List<DeclarationResultSchoolVO> getSchoolList(
    PageParameter<DeclarationResultSchoolVO> pageParameter);

    /**
     * 
     * Description:根据教材id获取院校申报总数
     * 
     * @author:lyc
     * @date:2017年11月30日下午6:08:56
     * @param
     * @return Integer
     */
    Integer getSchoolDeclarationCount(Long materialId);

    /**
     * 
     * Description:根据教材id获取院校当选总数
     * 
     * @author:lyc
     * @date:2018年1月2日下午4:12:15
     * @param
     * @return Integer
     */
    Integer getSchoolDeclarationChosenCount(Long materialId);

    /**
     * 
     * Description:根据教材id获取院校总数
     * 
     * @author:lyc
     * @date:2017年11月30日下午6:09:49
     * @param
     * @return Integer
     */
    Integer getSchoolCount(Long materialId);

    /**
     * 
     * Description:根据教材id获取主编申报总数
     * 
     * @author:lyc
     * @date:2017年11月30日下午6:10:40
     * @param
     * @return Integer
     */
    Integer getEditorCount(Long materialId);
    Integer getEditorCount2(Long materialId);

    /**
     * 
     * Description:根据教材id获取副主编申报总数
     * 
     * @author:lyc
     * @date:2017年11月30日下午6:11:35
     * @param
     * @return Integer
     */
    Integer getSubEditorCount(Long materialId);

    /**
     * 
     * Description:根据教材id获取编委申报总数
     * 
     * @author:lyc
     * @date:2017年11月30日下午6:12:13
     * @param
     * @return Integer
     */
    Integer getEditorialCount(Long materialId);

    /**
     * 
     * Description:根据教材id获取数字编委申报总数
     * 
     * @author:lyc
     * @date:2017年12月18日下午3:13:09
     * @param
     * @return Integer
     */
    Integer getDigitalCount(Long materialId);

    /**
     * 
     * Description:根据教材id获取主编当选总数
     * 
     * @author:lyc
     * @date:2018年1月2日下午3:38:02
     * @param
     * @return Integer
     */
    Integer getChosenEditorCount(Long materialId);

    /**
     * 
     * Description:根据教材id获取副主编当选总数
     * 
     * @author:lyc
     * @date:2018年1月2日下午3:38:44
     * @param
     * @return Integer
     */
    Integer getChosenSubeditorCount(Long materialId);

    /**
     * 
     * Description:根据教材id获取编委当选总数
     * 
     * @author:lyc
     * @date:2018年1月2日下午3:39:09
     * @param
     * @return Integer
     */
    Integer getChosenEditorialCount(Long materialId);

    /**
     * 
     * Description:根据教材id获取数字编委当选总数
     * 
     * @author:lyc
     * @date:2018年1月2日下午3:39:36
     * @param
     * @return Integer
     */
    Integer getChosenDigitalCount(Long materialId);

    /**
     * 
     * Description:根据教材id（和学校名称）查询学校当选职务情况（按当选结果排序,不分页）
     * 
     * @author:lyc
     * @date:2017年12月1日下午2:30:41
     * @param
     * @return List<DeclarationSituationSchoolResultVO>
     */
    List<DeclarationSituationSchoolResultVO> getSchoolResultChosen(
    PageParameter<DeclarationSituationSchoolResultVO> pageParameter);

    /**
     * Description:根据教材id（和学校名称）查询学校当选职务情况（按当选结果排序,分页）
     * @param pageParameter
     * @author:lyc
     * @date:2018年3月21日下午4:17:32
     * @return List<DeclarationSituationSchoolResultVO>
     */
    List<DeclarationSituationSchoolResultVO> getSchoolResultChosenPage(
    PageParameter<DeclarationSituationSchoolResultVO> pageParameter);
    
    /**
     * 
     * Description:根据教材id（和学校名称）查询学校申报职务情况（按申报结果排序）
     * 
     * @author:lyc
     * @date:2017年12月5日上午10:15:13
     * @param
     * @return List<DeclarationSituationSchoolResultVO>
     */
    List<DeclarationSituationSchoolResultVO> getSchoolResultPreset(
    PageParameter<DeclarationSituationSchoolResultVO> pageParameter);
    
    /**
     * Description:根据教材id（和学校名称）查询学校申报职务情况（按连接申报职位表按当选结果排序）
     * 
     * @author:lyc
     * @date:2018年3月21日上午11:30:23
     * @param pageParameter
     * @return List<DeclarationSituationSchoolResultVO>
     */
    List<DeclarationSituationSchoolResultVO> getSchoolResultPresetChosen(
    PageParameter<DeclarationSituationSchoolResultVO> pageParameter);

    /**
     * 总数 : 根据教材id（和学校名称）查询学校申报职务情况（按连接申报职位表按当选结果排序）
     * @param pageParameter
     * @return
     */
    int getSchoolResultPresetChosenCount(PageParameter<DeclarationSituationSchoolResultVO> pageParameter);

    /**
     * 
     * Description:获取书本总数
     * 
     * @author:lyc
     * @date:2017年12月1日下午5:22:39
     * @param
     * @return Integer
     */
    Integer getBooks(Long materialId);
    
    /**
     * 
     * Description:获取书本列表
     * 
     * @author:lyc
     * @date:2017年12月1日下午5:22:39
     * @param
     * @return List<DeclarationSituationBookResultVO>
     */
    List<DeclarationSituationBookResultVO> getBookListOne(
    		PageParameter<DeclarationSituationBookResultVO> pageParameter);
    /**
     * getBookListOne 的总数
     */

    int getBookListOneCount(PageParameter<DeclarationSituationBookResultVO> pageParameter);

    /**
     * 
     * Description:根据教材id（和书本名称）查询学校申报职务情况
     * 
     * @author:lyc
     * @date:2017年12月1日下午5:20:08
     * @param
     * @return List<DeclarationSituationBookResultVO>
     */
    List<DeclarationSituationBookResultVO> getBookResultPreset(
    PageParameter<DeclarationSituationBookResultVO> pageParameter);
    
    /**
     * 
     * Description:根据教材id（和书本名称）查询学校当选职务情况
     * 
     * @author:lyc
     * @date:2017年12月1日下午5:20:08
     * @param
     * @return List<DeclarationSituationBookResultVO>
     */
    List<DeclarationSituationBookResultVO> getBookResultChosen(
    PageParameter<DeclarationSituationBookResultVO> pageParameter);

    /**
     * 
     * Description:根据教材id（和学校名）查询统计结果（按当选人数排名,不分页）
     * 
     * @author:lyc
     * @date:2017年12月1日下午5:56:59
     * @param
     * @return List<DeclarationResultSchoolVO>
     */
    List<DeclarationResultSchoolVO> getSchoolListChosen(
    PageParameter<DeclarationResultSchoolVO> pageParameter);

    /**
     * Description:根据教材id（和学校名）查询统计结果（按当选人数排名,分页）
     * 
     * @author:lyc
     * @date:2018年3月21日下午4:20:49
     * @param pageParameter
     * @return List<DeclarationResultSchoolVO>
     */
    List<DeclarationResultSchoolVO> getSchoolListChosenPage(
    PageParameter<DeclarationResultSchoolVO> pageParameter);
    
    /**
     * 
     * Description:根据教材id（和学校名）查询统计结果（按申报人数排名）
     * 
     * @author:lyc
     * @date:2017年12月5日上午11:15:38
     * @param
     * @return List<DeclarationResultSchoolVO>
     */
    List<DeclarationResultSchoolVO> getSchoolListPreset(
    PageParameter<DeclarationResultSchoolVO> pageParameter);

    /**
     * Description:根据教材id（和学校名）查询统计结果（连接申报职位表按当选人数排名）
     * @author:lyc
     * @date:2018年3月21日上午11:32:25
     * @param pageParameter
     * @return List<DeclarationResultSchoolVO>
     */
    List<DeclarationResultSchoolVO> getSchoolListPresetChosen(
    PageParameter<DeclarationResultSchoolVO> pageParameter);

    /**
     * getSchoolListPresetChosen 的总数
     * @param pageParameter
     * @return
     */
    int getSchoolListPresetChosenCount(PageParameter<DeclarationResultSchoolVO> pageParameter);

    /**
     * 获取书本列表数量
     * @param pageParameter
     * @return
     */
    int getBookListTwoCount(PageParameter<DeclarationResultBookVO> pageParameter);
    
    /**
     * 
     * Description:获取书本列表
     * 
     * @author:lyc
     * @date:2017年12月1日下午5:22:39
     * @param
     * @return List<DeclarationSituationBookResultVO>
     */
    List<DeclarationResultBookVO> getBookListTwo(PageParameter<DeclarationResultBookVO> pageParameter);
    
    /**
     * 
     * Description:根据教材id（和书名）查询统计结果
     * 
     * @author:lyc
     * @date:2017年12月1日下午6:35:54
     * @param
     * @return List<DeclarationResultBookVO>
     */
    List<DeclarationResultBookVO> getBookChosenList(PageParameter<DeclarationResultBookVO> pageParameter);

    /**
     * 根据书籍id查询该书的主编、副编委、编委
     * 
     * @param textbookId
     * @return
     */
    List<TextbookDecVO> getTextbookEditorList(Long textbookId);

    /**
     * 根据书籍id查询编委
     * 
     * @param textbookId
     * @return
     */
    List<DecPosition> getDecPositionByTextbookId( Long textbookId);

    /**
     * 根据书籍id查询主编副主编
     * @param textbookId
     * @return
     */
    List<DecPosition> getMainDecPositionByTextbookId( Long textbookId);

    /**
     * 
     * <pre>
     * 功能描述：根据书籍ID查询对应书籍申报人员信息
     * 使用示范：
     *
     * @param pageParameter 
     * @return List<TextBookDecPositionVO> 集合
     * </pre>
     */
    List<TextBookDecPositionVO> listDeclarationByTextbookIds(
    PageParameter<TextBookDecPositionVO> pageParameter);

    /**
     * 
     * <pre>
     * 功能描述：根据书籍ID查询书籍选中的主编，副主编
     * 使用示范：
     *
     * @param textbookId 书籍ID
     * @return DecPosition集合
     * </pre>
     */
    List<DecPosition> getEditorByTextbookId(Long textbookId);

    /*
    获取当前教材下的主编
     */
    List<Map<String,Object>> getIsZhuBian(@Param("materialId") Long materialId);

    /**
     * 撤销某(人)申报表的所有遴选，dec_positiond的choosePosition设为0，dec_pisition_published相应删除
     * @param declarationId
     * @return
     */
    int dismissPositionByDeclarationId(Long declarationId);

    //计算当选人数
    Integer getChoseCount(@Param("bookId") Long id, @Param("materialId") Long materialId,@Param("bookName") String bookName);


        /**
         * 根据orgid和bookid获取该机构某些已公布的书的申报职位
         *
         * @author Mryang
         * @createDate 2017年11月19日 上午10:13:40
         * @param map
         * @return
         */
        List<DecPosition> listDecPositionsByTextbookIdAndOrgid2(Map<String, Object> map);

        /**
         *
         * <pre>
         * 功能描述：教材申报-遴选主编/遴选编委 (根据书籍Id查询所有申报id)
         * 使用示范：
         *
         * @param textbookId  书籍Id
         * @return 所有申报id
         * </pre>
         */
        List<Long> getDecPositionIdByBookId2(@Param("textbookId") Long textbookId,
                                            @Param("editorOrSubeditorType") Integer editorOrSubeditorType);





        /**
         *
         *
         * 功能描述：获取申报书籍和职位
         *
         * @param declarationId 申报表id
         * @return
         *
         */

        Map<String, String> getTextbookNameAndPresetPosition2(Long declarationId);







        /**
         *
         * <pre>
         * 功能描述：教材申报-遴选主编/遴选编委(列表)
         * 使用示范：
         *
         * &#64;param textbookId 书籍ID
         * &#64;param realName 申报人姓名
         * &#64;param presetPosition 申报职位
         * &#64;return
         * </pre>
         */
        List<DecPositionEditorSelectionVO> listEditorSelection2(@Param("textbookId") Long textbookId,
                                                               @Param("realName") String realName, @Param("orgName") String orgName);








        /**
         *
         * Description:根据教材id获取院校当选总数
         *
         * @author:lyc
         * @date:2018年1月2日下午4:12:15
         * @param
         * @return Integer
         */
        Integer getSchoolDeclarationChosenCount2(Long materialId);





        /**
         *
         * Description:根据教材id获取副主编申报总数
         *
         * @author:lyc
         * @date:2017年11月30日下午6:11:35
         * @param
         * @return Integer
         */
        Integer getSubEditorCount2(Long materialId);

        /**
         *
         * Description:根据教材id获取编委申报总数
         *
         * @author:lyc
         * @date:2017年11月30日下午6:12:13
         * @param
         * @return Integer
         */
        Integer getEditorialCount2(Long materialId);


        /**
         *
         * Description:根据教材id获取主编当选总数
         *
         * @author:lyc
         * @date:2018年1月2日下午3:38:02
         * @param
         * @return Integer
         */
        Integer getChosenEditorCount2(Long materialId);

        /**
         *
         * Description:根据教材id获取副主编当选总数
         *
         * @author:lyc
         * @date:2018年1月2日下午3:38:44
         * @param
         * @return Integer
         */
        Integer getChosenSubeditorCount2(Long materialId);

        /**
         *
         * Description:根据教材id获取编委当选总数
         *
         * @author:lyc
         * @date:2018年1月2日下午3:39:09
         * @param
         * @return Integer
         */
        Integer getChosenEditorialCount2(Long materialId);



        /**
         *
         * Description:根据教材id（和学校名称）查询学校当选职务情况（按当选结果排序,不分页）
         *
         * @author:lyc
         * @date:2017年12月1日下午2:30:41
         * @param
         * @return List<DeclarationSituationSchoolResultVO>
         */
        List<DeclarationSituationSchoolResultVO> getSchoolResultChosen2(
                PageParameter<DeclarationSituationSchoolResultVO> pageParameter);

        /**
         * Description:根据教材id（和学校名称）查询学校当选职务情况（按当选结果排序,分页）
         * @param pageParameter
         * @author:lyc
         * @date:2018年3月21日下午4:17:32
         * @return List<DeclarationSituationSchoolResultVO>
         */
        List<DeclarationSituationSchoolResultVO> getSchoolResultChosenPage2(
                PageParameter<DeclarationSituationSchoolResultVO> pageParameter);

        /**
         *
         * Description:根据教材id（和学校名称）查询学校申报职务情况（按申报结果排序）
         *
         * @author:lyc
         * @date:2017年12月5日上午10:15:13
         * @param
         * @return List<DeclarationSituationSchoolResultVO>
         */
        List<DeclarationSituationSchoolResultVO> getSchoolResultPreset2(
                PageParameter<DeclarationSituationSchoolResultVO> pageParameter);

        /**
         * Description:根据教材id（和学校名称）查询学校申报职务情况（按连接申报职位表按当选结果排序）
         *
         * @author:lyc
         * @date:2018年3月21日上午11:30:23
         * @param pageParameter
         * @return List<DeclarationSituationSchoolResultVO>
         */
        List<DeclarationSituationSchoolResultVO> getSchoolResultPresetChosen2(
                PageParameter<DeclarationSituationSchoolResultVO> pageParameter);




        /**
         *
         * Description:根据教材id（和书本名称）查询学校申报职务情况
         *
         * @author:lyc
         * @date:2017年12月1日下午5:20:08
         * @param
         * @return List<DeclarationSituationBookResultVO>
         */
        List<DeclarationSituationBookResultVO> getBookResultPreset2(
                PageParameter<DeclarationSituationBookResultVO> pageParameter);

        /**
         *
         * Description:根据教材id（和书本名称）查询学校当选职务情况
         *
         * @author:lyc
         * @date:2017年12月1日下午5:20:08
         * @param
         * @return List<DeclarationSituationBookResultVO>
         */
        List<DeclarationSituationBookResultVO> getBookResultChosen2(
                PageParameter<DeclarationSituationBookResultVO> pageParameter);

        /**
         *
         * Description:根据教材id（和学校名）查询统计结果（按当选人数排名,不分页）
         *
         * @author:lyc
         * @date:2017年12月1日下午5:56:59
         * @param
         * @return List<DeclarationResultSchoolVO>
         */
        List<DeclarationResultSchoolVO> getSchoolListChosen2(
                PageParameter<DeclarationResultSchoolVO> pageParameter);

        /**
         * Description:根据教材id（和学校名）查询统计结果（按当选人数排名,分页）
         *
         * @author:lyc
         * @date:2018年3月21日下午4:20:49
         * @param pageParameter
         * @return List<DeclarationResultSchoolVO>
         */
        List<DeclarationResultSchoolVO> getSchoolListChosenPage2(
                PageParameter<DeclarationResultSchoolVO> pageParameter);

        /**
         *
         * Description:根据教材id（和学校名）查询统计结果（按申报人数排名）
         *
         * @author:lyc
         * @date:2017年12月5日上午11:15:38
         * @param
         * @return List<DeclarationResultSchoolVO>
         */
        List<DeclarationResultSchoolVO> getSchoolListPreset2(
                PageParameter<DeclarationResultSchoolVO> pageParameter);






        /**
         *
         * Description:根据教材id（和书名）查询统计结果
         *
         * @author:lyc
         * @date:2017年12月1日下午6:35:54
         * @param
         * @return List<DeclarationResultBookVO>
         */
        List<DeclarationResultBookVO> getBookChosenList2(PageParameter<DeclarationResultBookVO> pageParameter);



        /**
         * 根据书籍id查询编委
         *
         * @param textbookId
         * @return
         */
        List<DecPosition> getDecPositionByTextbookId2( Long textbookId);

        /**
         * 根据书籍id查询主编副主编
         * @param textbookId
         * @return
         */
        List<DecPosition> getMainDecPositionByTextbookId2( Long textbookId);



        /**
         *
         * <pre>
         * 功能描述：根据书籍ID查询书籍选中的主编，副主编
         * 使用示范：
         *
         * @param textbookId 书籍ID
         * @return DecPosition集合
         * </pre>
         */
        List<DecPosition> getEditorByTextbookId2(Long textbookId);

        /*
        获取当前教材下的主编
         */
        List<Map<String,Object>> getIsZhuBian2(@Param("materialId") Long materialId);





}
