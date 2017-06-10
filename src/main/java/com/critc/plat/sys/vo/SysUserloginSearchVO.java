package com.critc.plat.sys.vo;

import com.critc.plat.util.page.PageSearchVO;

/**
 * Author  孔垂云
 * Date  2017/6/11.
 */
public class SysUserloginSearchVO extends PageSearchVO{
    private Integer userId;

    @Override
    public String toString() {
        return "SysUserloginSearchVO{" +
                "userId=" + userId +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
