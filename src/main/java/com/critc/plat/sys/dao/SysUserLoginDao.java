package com.critc.plat.sys.dao;

import com.critc.plat.core.dao.BaseDao;
import com.critc.plat.sys.model.SysUserLogin;
import com.critc.plat.sys.vo.SysUserloginSearchVO;
import com.critc.plat.util.page.PageUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户登录DAO
 */
@Repository
public class SysUserLoginDao extends BaseDao<SysUserLogin, SysUserloginSearchVO> {

    public void add(SysUserLogin sysUserLogin) {
        String sql = "insert into t_sys_userlogin(id,user_id,login_date,login_ip,terminal,explorerType,explorerVersion)";
        sql += " values(seq_t_sys_userlogin.nextval,:userId,:loginDate,:loginIp,:terminal,:explorerType,:explorerVersion)";
        insert(sql, sysUserLogin);
    }

    /**
     * 取得最后登录信息
     *
     * @param userId
     * @return
     */
    public SysUserLogin getLastLogin(int userId) {
        String sql = "select * from t_sys_userlogin where user_id=? and rownum<2 order by login_date desc";
        return get(sql, userId);
    }

    public List<SysUserLogin> list(SysUserloginSearchVO sysUserloginSearchVO) {
        String sql = "select * from t_sys_userlogin where user_id=? order by login_date desc ";
        sql = PageUtil.createOraclePageSQL(sql, sysUserloginSearchVO.getPageIndex());
        return list(sql, sysUserloginSearchVO.getUserId());
    }

    /**
     * 查询用户登录总数
     *
     * @param userId
     * @return
     */
    public int listCount(int userId) {
        String sql = "select count(*) from t_sys_userlogin where user_id=? ";
        return listCount(sql, userId);
    }

}
