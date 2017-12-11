/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.bc.pmpheep.back.util.StringUtil;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JDBC工具类
 *
 * @author L.X <gugia@qq.com>
 */
public class JdbcHelper {

    private static DriverManagerDataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcHelper.class);

    private static final String ADD_NEW_PK_COLUMN = "ALTER TABLE ? ADD new_pk BIGINT(20) NOT NULL COMMENT '新表主键'";
    private static final String UPDATE_NEW_PK = "UPDATE # SET new_pk = ? WHERE $ = ?";
    private static final String QUERY = "SELECT * FROM ?";
    private static final String GET_PARENT_PK = "SELECT new_pk FROM # WHERE $ = ?";
    private static final String GET_PARENTID = "SELECT % FROM # WHERE $ = ?";

    public static JdbcTemplate getJdbcTemplate() {
        if (null == dataSource) {
            dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(SQLParameters.DRIVER);
            dataSource.setUrl(SQLParameters.DB_URL);
            dataSource.setUsername(SQLParameters.DB_USERNAME);
            dataSource.setPassword(SQLParameters.DB_PASSWORD);
        }
        return new JdbcTemplate(dataSource);
    }

    /**
     * 为旧数据库指定表新增new_pk列
     *
     * @param tableName 旧数据库表名，例如"bbs_group"
     */
    public static void addColumn(String tableName) {
        String sql = ADD_NEW_PK_COLUMN.replace("?", tableName);
        try {
            getJdbcTemplate().execute(sql);
        } catch (DataAccessException ex) {
            LOG.info(ex.getMessage());
            LOG.warn("执行SQL时发生异常，可能表<{}>已存在'new_pk'字段", tableName);
        }
    }

    /**
     * 更新旧数据库指定表符合查询条件的new_pk字段
     *
     * @param tableName 旧数据库表名，例如"bbs_group"
     * @param pk 数据在新表中的主键
     * @param column 旧数据表中的主键列名
     * @param columnValue 旧数据表中的主键值
     */
    public static void updateNewPrimaryKey(String tableName, long pk, String column, Object columnValue) throws DataAccessException {
        String sql = UPDATE_NEW_PK.replace("#", tableName);
        sql = sql.replace("$", column);
        getJdbcTemplate().update(sql, pk, columnValue);
    }

    /**
     * 根据旧表字段查询父节点对应的new_pk字段值
     *
     * @param tableName 旧数据库表名
     * @param column 旧数据表中的主键列名
     * @param columnValue 对应字段值
     * @return 查询结果
     */
    public static Long getPrimaryKey(String tableName, String column, Object columnValue) {
        String sql = GET_PARENT_PK.replace("#", tableName);
        sql = sql.replace("$", column);
        try {
            return getJdbcTemplate().queryForObject(sql, Long.class, columnValue);
        } catch (DataAccessException ex) {
            LOG.warn("getPrimaryKey方法未获取到唯一结果，异常信息：{}", ex.getMessage());
        }
        return null;
    }

    /**
     * 查询旧数据库指定表的全部数据
     *
     * @param tableName 旧数据库表名，例如"bbs_group"
     * @return 查询到的结果集
     */
    public static List<Map<String, Object>> queryForList(String tableName) throws DataAccessException {
        String sql = QUERY.replace("?", tableName);
        return getJdbcTemplate().queryForList(sql);
    }

    /**
     *
     * Description:根据旧表父节点字段查询父节点对应的new_pk字段值，如果不为0，则递归调用自己， 知道找到为旧表父节点为0的最高节点，拼接成path
     *
     * @author:lyc
     * @date:2017年10月26日下午5:23:43
     * @param tableName 旧数据库表名
     * @param column 旧数据表中的主键列名
     * @param parentColumn 旧数据表中的父节点列名
     * @param parentColumnValue 对应字段值
     * @return 拼接的path
     */
    public static String getPath(String tableName, String column, String parentColumn, Object parentColumnValue) throws DataAccessException {
        String sqlNewParentId = GET_PARENT_PK.replace("#", tableName);
        String sqlParentId = GET_PARENTID.replace("#", tableName);
        sqlNewParentId = sqlNewParentId.replace("$", column);
        sqlParentId = sqlParentId.replace("$", column);
        sqlParentId = sqlParentId.replace("%", parentColumn);
        String path;
        //如果旧表中父节点为0，则直接返回路径0，若不为0，再继续往父类节点查最后拼接
        if (!"0".equals(parentColumnValue.toString())) {
            //不为0，根据旧表父节点字段查询父节点对应的new_pk和父节点id字段值，依次类推，查最后拼接
            String parentId = getJdbcTemplate().queryForObject(sqlParentId, String.class, parentColumnValue);
            Long parentNewId = getJdbcTemplate().queryForObject(sqlNewParentId, Long.class, parentColumnValue);
            path = getPath(tableName, column, parentColumn, parentId) + "-" + parentNewId;
        } else {
            path = "0";
        }
        return path;
    }

    /**
     *
     * Description:判断表中唯一值方法
     *
     * @author:若表中除主键外有需要判断重复值的列，调用此方法，若重名，返回true，否则返回false
     * @date:2017年11月3日上午2:41:47
     * @param list 装有需要判断字段值的集合
     * @param name 需要判断的具体值
     * @return boolean
     */
    public static boolean nameDuplicate(List<String> list, String name) {
        boolean flag = false;
        if (list.contains(name)) {
            flag = true;
        }
        return flag;
    }

    /**
     *
     * Description:判断教龄数据是否符合规范
     *
     * @author:lyc
     * @date:2017年11月9日下午3:35:10
     * @param experience 教龄
     * @return boolean
     */
    public static boolean judgeExperience(String experience) {
        boolean flag = true;
        if (StringUtil.notEmpty(experience) && StringUtil.isNumeric(experience)
                && StringUtil.strLength(experience) < 3) {
            flag = false;
        }
        return flag;
    }

    /**
     *
     * Description:将不符合规范的教龄修改为合乎规范的数据
     *
     * @author:lyc
     * @date:2017年11月9日下午3:40:11
     * @param experience 教龄
     * @return 合乎规范的教龄数据
     */
    public static String correctExperience(String experience) {
        if (StringUtil.isEmpty(experience) || "无".equals(experience) || "、".equals(experience)) {
            experience = "0";
        }
        if (experience.contains("岁")) {
            experience = experience.substring(0, experience.lastIndexOf("岁"));
            Integer age = Integer.parseInt(experience);
            experience = String.valueOf(age);
        } else {
            experience = experience.replace("年", "").replace("五", "5").replace("s", "").replace(" ", "")
                    .replace("内", "");
        }
        return experience;
    }

    /**
     *
     * Description:计算每个模块（图）迁移所耗时间
     *
     * @param begin 每个模块迁移程序启动时的系统时间
     * @return
     * @author:lyc
     * @date:2017年11月15日下午5:51:26
     */
    public static String getPastTime(Date begin) {
        StringBuilder sb = new StringBuilder();
        Date end = new Date();
        long milliseconds = end.getTime() - begin.getTime();
        if (milliseconds < 1000) {
            sb.append(milliseconds);
            sb.append("毫秒");
            return sb.toString();
        }
        long second = milliseconds / 1000;
        long hour = 0, minute = 0;
        if (second > 3600) {
            hour = second / 3600;
            second = second % 3600;
            minute = second / 60;
            second = second % 60;
        }
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (hour > 0) {
            sb.append(hour);
            sb.append("小时");
        }
        if (hour > 0 || minute > 0) {
            sb.append(minute);
            sb.append("分");
        }
        sb.append(second);
        sb.append("秒");
        return sb.toString();
    }
    
    /**
     * 抓取HTML中src地址
     *
     * @param html html字符串
     * @return 符合图片特征的集合
     */
    public static List<String> getImgSrc(String html) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<>(16);
        //String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址  
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(html);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            // Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src  
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }
}
