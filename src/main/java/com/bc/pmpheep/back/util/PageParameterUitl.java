package com.bc.pmpheep.back.util;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;

/**
 * 
 * <pre>
 * 功能描述：Page 工具类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-19
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public final class PageParameterUitl {
    private PageParameterUitl() {
    }

    /**
     * 
     * 将 PageParameter的当前页数和页面大小拷贝到PageResult 对象里面去
     * 
     * @author Mryang
     * @createDate 2017年10月8日 下午10:23:51
     * @param PageParameter
     * @param pageResult
     */
    @SuppressWarnings("rawtypes")
    public static void CopyPageParameter(PageParameter pageParameter, PageResult pageResult) {
        pageResult.setPageNumber(pageParameter.getPageNumber());
        pageResult.setPageSize(pageParameter.getPageSize());
    }

}
