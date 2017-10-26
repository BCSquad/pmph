package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphGroupMessage;
import com.bc.pmpheep.back.vo.PmphGroupMessageVO;

/**
 * PmphGroupMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphGroupMessageDao {
    /**
     * 
     * @param pmphGroupMessage 实体对象
     * @return 影响行数
     */
    Integer addPmphGroupMessage(PmphGroupMessage pmphGroupMessage);

    /**
     * 
     * @param PmphGroupMessage 必须包含主键ID
     * @return PmphGroupMessage
     */
    PmphGroupMessage getPmphGroupMessageById(Long id);

    /**
     * 
     * @param id
     * @return 影响行数
     */
    Integer deletePmphGroupMessageById(Long id);

    /**
     * @param pmphGroupMessage
     * @return 影响行数
     */
    Integer updatePmphGroupMessage(PmphGroupMessage pmphGroupMessage);

    /**
     * 
     * <pre>
	 * 功能描述：查询表单的数据总条数
	 * 使用示范：
	 *
	 * &#64;return 表单的总条数
	 * </pre>
     */
    Long getPmphGroupMessageCount();

    /**
     * 
     * 
     * 功能描述： 根据小组id删除小组消息
     * 
     * @param groupId 小组id
     * @return 影响的行数
     * 
     */
    Integer deletePmphGroupMessageByGroupId(Long groupId);

    /**
     * 
     * 
     * 功能描述：获取全部历史消息数量
     * 
     * @param pageParameter
     * @return 历史消息总数
     * 
     */
    Integer getPmphGroupMessageTotal(PageParameter<PmphGroupMessageVO> pageParameter);

    /**
     * 
     * 
     * 功能描述：进入小组是加载历史消息
     * 
     * @param pageParameter 分页参数 以及 小组id和进入小组时间
     * @return 分页消息结果集
     * 
     */
    List<PmphGroupMessageVO> listPmphGroupMessage(PageParameter<PmphGroupMessageVO> pageParameter);
}
