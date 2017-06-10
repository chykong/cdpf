package com.critc.plat.sys.model;

import javax.persistence.Column;
import java.util.Date;

/**
 * 用户登录信息
 *
 * @author chykong
 */
public class SysUserLogin {
    private int id;//编号
    @Column(name = "user_id")
    private int userId;//id
    @Column(name = "login_date")
    private Date loginDate;//登录时间
    @Column(name = "login_ip")
    private String loginIp;//登录IP

    private String terminal;//终端
    private String explorerType;//浏览器类型
    private String explorerVersion;//浏览器版本

    @Override
    public String toString() {
        return "SysUserLogin{" +
                "id=" + id +
                ", userId=" + userId +
                ", loginDate=" + loginDate +
                ", loginIp='" + loginIp + '\'' +
                ", terminal='" + terminal + '\'' +
                ", explorerType='" + explorerType + '\'' +
                ", explorerVersion='" + explorerVersion + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getExplorerType() {
        return explorerType;
    }

    public void setExplorerType(String explorerType) {
        this.explorerType = explorerType;
    }

    public String getExplorerVersion() {
        return explorerVersion;
    }

    public void setExplorerVersion(String explorerVersion) {
        this.explorerVersion = explorerVersion;
    }

}
