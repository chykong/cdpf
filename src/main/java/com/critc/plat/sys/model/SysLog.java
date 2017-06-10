package com.critc.plat.sys.model;

import javax.persistence.Column;
import java.util.Date;

/**
 * 操作日志
 *
 * @author chykong
 */
public class SysLog {
    private long id;
    @Column(name = "user_id")
    private int userId;//用户id
    @Column(name = "opera_date")
    private Date operaDate;//操作日期
    @Column(name = "opera_ip")
    private String operaIp;//ip地址
    @Column(name = "module_name")
    private String moduleName;//模块id
    @Column(name = "opera_name")
    private String operaName;//操作名称
    @Column(name = "opera_url")
    private String operaUrl;//操作url
    @Column(name = "opera_params")
    private String operaParams;//参数
    private String realname;//用户姓名

    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", operaDate=" + operaDate +
                ", operaIp='" + operaIp + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", operaName='" + operaName + '\'' +
                ", operaUrl='" + operaUrl + '\'' +
                ", operaParams='" + operaParams + '\'' +
                ", realname='" + realname + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(Date operaDate) {
        this.operaDate = operaDate;
    }

    public String getOperaIp() {
        return operaIp;
    }

    public void setOperaIp(String operaIp) {
        this.operaIp = operaIp;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOperaName() {
        return operaName;
    }

    public void setOperaName(String operaName) {
        this.operaName = operaName;
    }

    public String getOperaUrl() {
        return operaUrl;
    }

    public void setOperaUrl(String operaUrl) {
        this.operaUrl = operaUrl;
    }

    public String getOperaParams() {
        return operaParams;
    }

    public void setOperaParams(String operaParams) {
        this.operaParams = operaParams;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
