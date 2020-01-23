package com.mall.constant;

/**
 * 统一错误码定义
 */
public enum ErrorType {

    /**
     * ls推送接口的成功
     */
    LS_SUCCESS("200","success"),

    /**
     * 缺省的成功
     */
    SUCCESS("0000", "成功"),
    /**
     * 失败
     */
    FAILED("9999", "失败"),

    /**
     * 自定义errorMsg信息
     */
    CUSTOM("10000", "自定义"),
    /**
     * 错误:用户未登录
     */
    NEEDLOGIN("10001", "用户未登录"),
    /**
     * 错误:拒绝访问
     */
    UNAUTHORIZED("10002", "权限不足，拒绝访问"),
    /**
     * 错误:获取用户信息失败
     */
    GETUSERFAILED("10003", "获取用户信息失败"),
    /**
     * 错误:获取工号信息失败
     */
    USERNAMENOTFOUND("10004", "获取工号失败"),
    /**
     * 错误:登录失败
     */
    LOGINFAILED("10005", "登录失败"),
    /**
     * 错误，请求参数缺失
     */
    PARAMERROR("10006", "请求参数错误"),

    PARA_ERROR("10007", "请求参数不正确"),
    /**
     * 导出数据太多
     */
    EXPORT_TOO_LARGE("10008", "导出数据过多"),

    SERVICE_EXCEPTION("1009", "业务处理异常"),

    BAD_USER_OR_PASS("10010", "用户名或密码错误"),


    OPERATION_FAILED("10011", "操作失败"),

    USER_ALREADY_EXISTS("10012", "该用户已存在"),


    SAME_ROLE("10013", "角色英文名称已存在 "),


    HAS_USER_ROLE("10014", "该角色已被分配有用户 不能直接删除"),

    UNSEARCH("10015", "未查到相应数据"),

    SAME_ROLE_NAME("10016","角色名相同"),

    ROLR_MUST_ENGLISH("10017", "角色英文名称必须是英文或者英文+下划线组合"),
    USENAME_FAIL("10018", "用户不存在"),
    USENAME_NOT_ABLE("10019", "用户未启用"),
    ;
    private String errorCode;
    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    ErrorType(String c, String m) {
        this.errorCode = c;
        this.errorMsg = m;
    }


}
