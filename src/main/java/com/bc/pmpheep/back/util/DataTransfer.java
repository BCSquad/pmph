package com.bc.pmpheep.back.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphUserService;

/**
 * 数据迁移服务
 * 
 * @author 刘亚川
 */
@Service
public class DataTransfer {

    @Resource
    private AreaService           areaService;
    @Resource
    private OrgTypeService        orgTypeService;
    @Resource
    private PmphDepartmentService pmphDepartmentService;
    @Resource
    private PmphUserService       pmphUserService;

    private final Logger          logger   = LoggerFactory.getLogger(DataTransfer.class);
    private String                url      =
                                           "jdbc:mysql://localhost:3306/pmph_imesp_9.18?useSSL=false";
    private String                username = "root";
    private String                password = "cc148604";

    public void setConnectionParams(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * authoer:lyc 区域表迁移
     */
    public int area() {
        String sql = "SELECT AreaID,ParentCode,AreaName FROM ba_areacode";
        ConnectionManager cm;
        ResultSet rs;
        try {
            cm = new ConnectionManager(url, username, password);
            rs = cm.getResultSet(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("JDBC初始化发生错误", ex);
            return 0;
        }
        areaService.deleteAllArea();
        int count = 0;
        try {
            while (rs.next()) {
                Area area = new Area();
                area.setId(new Long(rs.getInt(1)));
                area.setParentId(new Long(rs.getInt(2)));
                area.setAreaName(rs.getString(3));
                areaService.addArea(area);
                count++;
                if (count % 100 == 0) {
                    logger.info("已迁移 {} 条数据", count);
                }
            }
            logger.info("总共迁移 {} 条数据", count);
            cm.close();
        } catch (SQLException ex) {
            logger.error("JDBC SQL语句运行时发生错误", ex);
            return count;
        }
        return count;
    }

    /**
     * 机构类型表迁移
     */
    public int orgType() {
        String sql =
        "SELECT a.orgtype,a.orgname,a.sortno FROM ba_organize a WHERE a.parentid = 0 AND a.orgcode NOT LIKE '15%'";
        ConnectionManager cm;
        ResultSet rs;
        try {
            cm = new ConnectionManager(url, username, password);
            rs = cm.getResultSet(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("JDBC初始化错误", ex);
            return 0;
        }
        int count = 0;
        try {
            while (rs.next()) {
                OrgType orgType = new OrgType();
                orgType.setTypeName(rs.getString(2));
                orgType.setSort(rs.getString(3) == null ? 999 : Integer.parseInt(rs.getString(3)));
                orgTypeService.addOrgType(orgType);
                count++;
                logger.info("已经转移{}条数据", count);
            }
            logger.info("一共转移了{}条数据", count);
            cm.close();
        } catch (SQLException ex) {
            logger.error("SQL语句运行出错", ex);
            return count;
        }
        return count;
    }

    /**
     * 社内部门表迁移
     */
    public int pmphDepartment() {
        String sql =
        "SELECT a.orgid,a.parentid,a.orgcode,a.orgname,a.sortno,a.remark,a.isdelete,a.orgtype FROM ba_organize a WHERE a.orgcode LIKE '15%' ORDER BY LENGTH(orgcode),orgcode";
        ConnectionManager cm;
        ResultSet rs;
        try {
            cm = new ConnectionManager(url, username, password);
            rs = cm.getResultSet(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("JDBC初始化错误", ex);
            return 0;
        }
        int count = 0;
        try {
            while (rs.next()) {
                PmphDepartment pmphDepartment = new PmphDepartment();
                pmphDepartment.setId(Long.valueOf(rs.getString(3).replace("-", "")));
                String[] tree = rs.getString(3).split("-");
                if (tree.length == 1) {
                    pmphDepartment.setParentId(0L);
                } else if (tree.length == 2) {
                    pmphDepartment.setParentId(Long.valueOf(rs.getString(3)
                                                              .substring(0,
                                                                         rs.getString(3)
                                                                           .indexOf("-"))));
                } else {
                    pmphDepartment.setParentId(Long.valueOf(rs.getString(3)
                                                              .substring(0,
                                                                         rs.getString(3).length() - 4)
                                                              .replace("-", "")));
                }
                pmphDepartment.setPath(rs.getString(3));
                pmphDepartment.setDpName(rs.getString(4));
                pmphDepartment.setNote(rs.getString(3));
                count++;
                pmphDepartmentService.addPmphDepartment(pmphDepartment);
                logger.info("已经迁移{}条数据", count);
            }
            logger.info("一共迁移了{}条数据", count);
            cm.close();
        } catch (SQLException ex) {
            logger.error("SQL语句执行出错", ex);
            return count;
        }
        return count;

    }

    /**
     * 社内用户表迁移
     */
    public int pmphUser() {
        String sql =
        "SELECT a.usercode,a.`password`,a.username,c.orgid,b.handset,b.email,a.memo,a.sortno FROM sys_user a LEFT JOIN sys_userext b ON a.userid = b.userid LEFT JOIN sys_userorganize c ON b.userid = c.userid WHERE a.sysflag = 0";
        ConnectionManager cm;
        ResultSet rs;
        try {
            cm = new ConnectionManager(url, username, password);
            rs = cm.getResultSet(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("JDBC初始化发生错误", ex);
            return 0;
        }
        int count = 0;
        try {
            while (rs.next()) {
                PmphUser pmphUser = new PmphUser();
                pmphUser.setUsername(rs.getString(1));
                pmphUser.setPassword(rs.getString(2));
                pmphUser.setRealname(rs.getString(3));
                pmphUser.setHandphone(rs.getString(5));
                pmphUser.setEmail(rs.getString(6));
                pmphUser.setNote(rs.getString(7));
                pmphUser.setSort(rs.getInt(8));
                pmphUserService.add(pmphUser);
                count++;
                if (count % 100 == 0) {
                    logger.info("已经迁移{}条数据", count);
                }
            }
            logger.info("一共迁移{}条数据", count);
            cm.close();
        } catch (SQLException ex) {
            logger.error("SQL语句运行发生错误", ex);
            return count;
        }
        return count;

    }

    class ConnectionManager {

        private final String      driver = "com.mysql.jdbc.Driver";
        private Connection        conn   = null;
        private PreparedStatement ps     = null;

        public ConnectionManager(String url, String username, String password)
        throws ClassNotFoundException, SQLException {
            Class.forName(driver);
            if (null == conn || conn.isClosed()) {
                conn = DriverManager.getConnection(url, username, password);
            }
        }

        /**
         * 获取查询结果集
         */
        public ResultSet getResultSet(String sql) throws SQLException {
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();

        }

        public void close() throws SQLException {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }
}
