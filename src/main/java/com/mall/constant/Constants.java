package com.mall.constant;

/**
 * 系统常量
 */
public class Constants {
	/**
	 * shiro采用加密算法
	 */
	public static final String HASH_ALGORITHM = "SHA-1";
	/**
	 * 生成Hash值的迭代次数
	 */
	public static final int HASH_INTERATIONS = 1024;
	/**
	 * 生成盐的长度
	 */
	public static final int SALT_SIZE = 8;

	/**
	 * 验证码
	 */
	public static final String VALIDATE_CODE = "validateCode";

	/**
	 * 系统用户默认密码
	 */
	public static final String DEFAULT_PASSWORD = "123456";

	/**
	 * 定时任务状态:正常
	 */
	public static final Integer QUARTZ_STATUS_NOMAL = 0;
	/**
	 * 定时任务状态:暂停
	 */
	public static final Integer QUARTZ_STATUS_PUSH = 1;
	/**
	 * userSession
	 */
	public final static String User_Session = "userSession";
	/**
	 * userPermission
	 */
	public static class Role{
        public final static String admin = "0";
        public final static String customer = "1";
        public final static String businessShop = "2";
    }
}
