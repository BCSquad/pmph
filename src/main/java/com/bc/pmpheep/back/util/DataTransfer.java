package com.bc.pmpheep.back.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.po.PmphUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 数据迁移服务
 *
 * @author 刘亚川
 */
@Service
public class DataTransfer {

    @Resource
    private AreaService areaService;
    @Resource
    private OrgService orgService;
    @Resource
    private OrgUserService orgUserService;
    @Resource
    private OrgTypeService orgTypeService;
    @Resource
    private PmphDepartmentService pmphDepartmentService;
    @Resource
    private PmphUserService pmphUserService;

    private final Logger logger = LoggerFactory.getLogger(DataTransfer.class);
    private String url = "jdbc:mysql://localhost:3306/pmph_imesp?useSSL=true";
    private String username = "gugia";
    private String password = "123123";

    public void setConnectionParams(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*
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

    /*
	 * 机构表迁移
     */
//    public void org() {
//        String sql = "SELECT parentid,orgname,orgtype,orgprovince,orgcity,orgcounty,linker,linktel,remark,sortno FROM ba_organize WHERE orgcode NOT LIKE '15%' ORDER BY LENGTH(orgcode),orgcode";
//        ConnectionManager cm = new ConnectionManager(url, username, password);
//        ResultSet rs = cm.getResultSet(sql);
//        try {
//            while (rs.next()) {
//                HashMap map = new HashMap<>();
//                map.put("parentid", rs.getString(1));
//                map.put("orgname", rs.getString(2));
//                map.put("orgtype", rs.getString(3));
//                String area = "";
//                String[] areas = new String[]{rs.getString(6),
//                    rs.getString(5), rs.getString(4)};
//                for (int i = 0; i < areas.length; i++) {
//                    if (null == areas[i] || "".equals(areas[i])) {
//                        continue;
//                    }
//                    area = areas[i];
//                }
//                map.put("areaid", area);
//                map.put("linker", rs.getString(7));
//                map.put("linktel", rs.getString(8));
//                map.put("note", rs.getString(9));
//                map.put("sort", rs.getString(10));
//                List<HashMap> list = new ArrayList<>();
//                list.add(map);
//                Iterator<HashMap> it = list.iterator();
//                while (it.hasNext()) {
//                    Org org = new Org();
//                    HashMap key = it.next();
//                    org.setParentId((Long) key.get("parentid"));
//                    org.setOrgName((String) key.get("orgname"));
//                    org.setOrgTypeId((Long) key.get("orgtype"));
//                    org.setAreaId((Long) key.get("areaid"));
//                    org.setCountactPerson((String) key.get("linker"));
//                    org.setCountactPhone((String) key.get("linktel"));
//                    org.setNote((String) key.get("note"));
//                    org.setSort((Integer) key.get("sort"));
//                    orgService.addOrg(org);
//                }
//            }
//            cm.close();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    /*
	 * 机构用户表迁移
     */
//    public void orgUser() {
//        String sql = "SELECT a.usercode,a.`password`,a.isvalid,d.orgname,a.username,b.sex,b.duties,b.positional,b.fax,b.handset,b.phone,b.idcard,b.email,b.address,b.postcode,a.memo,a.sortno FROM sys_user a LEFT JOIN sys_userext b ON a.userid = b.userid LEFT JOIN sys_userorganize c ON b.userid = c.userid LEFT JOIN ba_organize d ON c.orgid = d.orgid WHERE a.sysflag=1 AND b.usertype=2";
//        ConnectionManager cm = new ConnectionManager(url, username, password);
//        ResultSet rs = cm.getResultSet(sql);
//        try {
//            while (rs.next()) {
//                HashMap map = new HashMap<>();
//                map.put("username", rs.getString(1));
//                map.put("password", rs.getString(2));
//                map.put("isDisabled", rs.getString(3));
//                map.put("realname", rs.getString(5));
//                map.put("sex", rs.getString(6));
//                map.put("position", rs.getString(7));
//                map.put("title", rs.getString(8));
//                map.put("fax", rs.getString(9));
//                map.put("handphone", rs.getString(10));
//                map.put("telephone", rs.getString(11));
//                map.put("idcard", rs.getString(12));
//                map.put("email", rs.getString(13));
//                map.put("address", rs.getString(14));
//                map.put("postcode", rs.getString(15));
//                map.put("note", rs.getString(16));
//                map.put("sort", rs.getString(17));
//                List<HashMap> list = new ArrayList<>();
//                list.add(map);
//                Iterator<HashMap> it = list.iterator();
//                while (it.hasNext()) {
//                    OrgUser orgUser = new OrgUser();
//                    HashMap key = it.next();
//                    orgUser.setUsername((String) key.get("username"));
//                    orgUser.setPassword((String) key.get("password"));
//                    orgUser.setIsDisabled((boolean) key.get("isDisabled"));
//                    orgUser.setRealname((String) key.get("realname"));
//                    orgUser.setSex((Integer) key.get("sex"));
//                    orgUser.setPosition((String) key.get("position"));
//                    orgUser.setTitle((String) key.get("title"));
//                    orgUser.setFax((String) key.get("fax"));
//                    orgUser.setHandphone((String) key.get("handphone"));
//                    orgUser.setTelephone((String) key.get("telephone"));
//                    orgUser.setIdcard((String) key.get("idcard"));
//                    orgUser.setEmail((String) key.get("email"));
//                    orgUser.setAddress((String) key.get("address"));
//                    orgUser.setPostcode((String) key.get("postcode"));
//                    orgUser.setNote((String) key.get("note"));
//                    orgUser.setSort((Integer) key.get("sort"));
//                    orgUserService.addOrgUser(orgUser);
//                }
//            }
//            cm.close();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    /*
	 * 机构类型表迁移
     */
//    public void orgType() {
//        String sql = "SELECT a.orgtype,a.orgname,a.sortno FROM ba_organize a WHERE a.parentid = 0 AND a.orgcode NOT LIKE '15%'";
//        ConnectionManager cm = new ConnectionManager(url, username, password);
//        ResultSet rs = cm.getResultSet(sql);
//        try {
//            while (rs.next()) {
//                HashMap map = new HashMap<>();
//                map.put("id", rs.getString(1));
//                map.put("typename", rs.getString(2));
//                map.put("sort", rs.getString(3));
//                List<HashMap> list = new ArrayList<>();
//                list.add(map);
//                Iterator<HashMap> it = list.iterator();
//                while (it.hasNext()) {
//                    OrgType orgType = new OrgType();
//                    HashMap key = it.next();
//                    orgType.setId((Long) key.get("id"));
//                    orgType.setTypeName((String) key.get("typename"));
//                    orgType.setSort((Integer) key.get("sort"));
//                    try {
//                        orgTypeService.addOrgType(orgType);
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//            cm.close();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    /*
	 * 社内用户部门表迁移
     */
//    public void pmphDepartment() {
//        String sql = "SELECT a.parentid,a.orgcode,a.orgname,a.sortno,a.remark,a.isdelete,a.orgtype FROM ba_organize a WHERE a.orgcode LIKE '15%'";
//        ConnectionManager cm = new ConnectionManager(url, username, password);
//        ResultSet rs = cm.getResultSet(sql);
//        try {
//            while (rs.next()) {
//                HashMap map = new HashMap<>();
//                map.put("dpname", rs.getString(3));
//                map.put("sort", rs.getString(4));
//                map.put("note", rs.getString(5));
//                List<HashMap> list = new ArrayList<>();
//                list.add(map);
//                Iterator<HashMap> it = list.iterator();
//                while (it.hasNext()) {
//                    HashMap key = it.next();
//                    PmphDepartment pmphDepartment = new PmphDepartment();
//                    pmphDepartment.setDpName((String) key.get("dpname"));
//                    pmphDepartment.setSort((Integer) key.get("sort"));
//                    pmphDepartment.setNote((String) key.get("note"));
//                    pmphDepartmentService.addPmphDepartment(pmphDepartment);
//                }
//            }
//            cm.close();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    /*
	 * 社内用户表迁移
     */
//    public void pmphUser() {
//        String sql = "SELECT a.usercode,a.`password`,a.username,c.orgid,b.handset,b.email,a.memo,a.sortno FROM sys_user a LEFT JOIN sys_userext b ON a.userid = b.userid LEFT JOIN sys_userorganize c ON b.userid = c.userid WHERE a.sysflag = 0";
//        ConnectionManager cm = new ConnectionManager(url, username, password);
//        ResultSet rs = cm.getResultSet(sql);
//        try {
//            while (rs.next()) {
//                HashMap map = new HashMap<>();
//                map.put("username", rs.getString(1));
//                map.put("password", rs.getString(2));
//                map.put("realname", rs.getString(3));
//                map.put("handphone", rs.getString(5));
//                map.put("email", rs.getString(6));
//                map.put("note", rs.getString(7));
//                map.put("sort", rs.getString(8));
//                List<HashMap> list = new ArrayList<>();
//                list.add(map);
//                Iterator<HashMap> it = list.iterator();
//                while (it.hasNext()) {
//                    HashMap key = it.next();
//                    PmphUser pmphUser = new PmphUser();
//                    pmphUser.setUsername((String) key.get("username"));
//                    pmphUser.setPassword((String) key.get("password"));
//                    pmphUser.setRealname((String) key.get("realname"));
//                    pmphUser.setHandphone((String) key.get("handphone"));
//                    pmphUser.setEmail((String) key.get("email"));
//                    pmphUser.setNote((String) key.get("note"));
//                    try {
//                        pmphUser.setSort(Integer.parseInt((String) key
//                                .get("sort")));
//                    } catch (NumberFormatException ex) {                  //如果为空拆箱报错，设为默认值
//                        pmphUser.setSort(999);
//                    }
//                    pmphUserService.add(pmphUser);
//                }
//            }
//            cm.close();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    class ConnectionManager {

        private final String driver = "com.mysql.jdbc.Driver";
        private Connection conn = null;
        private PreparedStatement ps = null;

        public ConnectionManager(String url, String username, String password) throws ClassNotFoundException, SQLException {
            Class.forName(driver);
            if (null == conn || conn.isClosed()) {
                conn = DriverManager.getConnection(url, username, password);
            }
        }

        /*
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
