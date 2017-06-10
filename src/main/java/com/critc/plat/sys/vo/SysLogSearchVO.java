package com.critc.plat.sys.vo;


import com.critc.plat.util.page.PageSearchVO;

/**
 * 日志管理查询条件
 *
 * @author chykong
 */
public class SysLogSearchVO extends PageSearchVO {
    private Integer userId;//用户id
    private String startDte;//起始日期
    private String endDate;//终止日期

    @Override
    public String toString() {
        return "SysLogSearchVO{" +
                "userId=" + userId +
                ", startDte='" + startDte + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStartDte() {
        return startDte;
    }

    public void setStartDte(String startDte) {
        this.startDte = startDte;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
