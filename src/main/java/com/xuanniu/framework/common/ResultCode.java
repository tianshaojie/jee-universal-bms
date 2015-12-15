package com.xuanniu.framework.common;

import java.io.Serializable;

public class ResultCode implements Serializable {
    //成功
    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MSG = "SUCCESS";

    /**系统错误码：5xx，记录系统级别错误，如系统超时等**/
    //失败
    public static final int ERROR_CODE = 500;
    public static final String ERROR_MSG = "服务器内部错误，请重试或联系客服";

    /**通用错误码：6xx,记录业务级别通用错误，比如：数据库错误**/
    public static final int MSGSYSTEM_SEND_VERIFY_ERROR_CODE = 601;
    public static final String MSGSYSTEM_SEND_VERIFY_ERROR_MSG = "消息发送失败";

    public static final int DB_ERROR_CODE = 602;
    public static final String DB_ERROR_MSG = "目前理财人数过多，请稍后再试";

    //Token参数为空
    public static final int TOKEN_PARAM_NULL_ERROR_CODE = 609;
    public static final String TOKEN_PARAM_NULL_ERROR_MSG = "您的身份信息有误，请尝试重新登录";

    //Token有误
    public static final int TOKEN_ERROR_CODE = 603;
    public static final String TOKEN_ERROR_MSG = "您的身份信息无效，请重新登录";

    public static final int VERIFY_FAIL_CODE = 604;
    public static final String VERIFY_FAIL_MSG = "请输入正确的验证码";

    //安全问题不正确
    public static final int ANSWER_INCORRECT_CODE = 605;
    public static final String ANSWER_INCORRECT_MSG = "ANSWER_INCORRECT";

    //登陆密码不正确
    public static final int PASSWORD_INCORRECT_CODE = 606;
    public static final String PASSWORD_INCORRECT_MSG = "请输入正确的密码";

    //安全密码不正确
    public static final int SECURITY_PASSWORD_INCORRECT_CODE = 607;
    public static final String SECURITY_PASSWORD_INCORRECT_MSG = "请输入正确的密码";

    //用户不存在
    public static final int USER_NOT_EXIST_CODE = 608;
    public static final String USER_NOT_EXIST_MSG = "抱歉，此用户不存在";

    //验证码为空
    public static final int VERIFYCODE_PARAM_NULL_ERROR_CODE = 610;
    public static final String VERIFYCODE_PARAM_NULL_ERROR_MSG = "请输入验证码";

    public static final int REDIS_ERROR_CODE = 611;
    public static final String REDIS_ERROR_MSG = "目前理财人数过多，服务器正在努力奔跑";

    public static final int MOBILE_FORMAT_ERROR_CODE = 612;
    public static final String MOBILE_FORMAT_ERROR_MSG = "请输入正确的手机号码";

    public static final int EMAIL_NOT_EXIST_ERROR_CODE = 613;
    public static final String EMAIL_NOT_EXIST_ERROR_MSG = "请设置安全邮箱";

    public static final int PASSWORD_OLD_NEW_SAME_ERROR_CODE = 614;
    public static final String PASSWORD_OLD_NEW_SAME_ERROR_MSG = "新密码不能与旧密码相同";
    public static final int PASSWORD_SECURITY_LOGIN_SAME_ERROR_CODE = 615;
    public static final String PASSWORD_SECURITY_LOGIN_SAME_ERROR_MSG = "安全密码不能与登录密码相同";
    public static final int PASSWORD_LENGTH_ERROR_CODE = 616;
    public static final String PASSWORD_LENGTH_ERROR_MSG = "请输入长度为6-18位密码";
    public static final int VERIFY_TOO_MANY_ERROR_CODE = 617;
    public static final String VERIFY_TOO_MANY_ERROR_MSG = "您当前请求验证码次数过多，请稍后再试";
    public static final int EMAIL_URL_SIGN_ERROR_CODE = 618;
    public static final String EMAIL_URL_SIGN_ERROR_MSG = "邮件链接无效";
    public static final int SECURITY_PASSWORD_LOCK_ERROR_CODE = 619;
    public static final String SECURITY_PASSWORD_LOCK_ERROR_MSG = "您的安全密码被冻结，请联系客服";
    public static final int VERIFY_TIMEOUT_ERROR_CODE = 620;
    public static final String VERIFY_TIMEOUT_ERROR_MSG = "请求验证过期，返回重新操作";
    public static final int USER_ALREADY_EXIST_ERROR_CODE = 621;
    public static final String USER_ALREADY_EXIST_ERROR_MSG = "此用户已存在";
    public static final int USER_PROTECT_NOT_EXIST_ERROR_CODE = 622;
    public static final String USER_PROTECT_NOT_EXIST_ERROR_MSG = "请完善安全信息";
    public static final int PASSWORD_LOCK_ERROR_CODE = 623;
    public static final String PASSWORD_LOCK_ERROR_MSG = "您的登录密码被冻结，请联系客服";
    public static final int USER_LOCK_ERROR_CODE = 624;
    public static final String USER_LOCK_ERROR_MSG = "您当前用户名被锁定，请联系客服";
    public static final int PASSWORD_NOTIFY_ERROR_CODE = 625;
    public static final String PASSWORD_NOTIFY_ERROR_MSG = "您还有#次尝试机会";

    public static final int LOGIN_IP_CHANGE_ERROR_CODE = 626;
    public static final String LOGIN_IP_CHANGE_ERROR_MSG = "异地登录，请输入短信验证码";
    public static final int EMAIL_INVALID_ERROR_CODE = 627;
    public static final String EMAIL_INVALID_ERROR_MSG = "请输入正确的邮箱";
    public static final int EMAIL_ALREADY_EXIST_ERROR_CODE = 628;
    public static final String EMAIL_ALREADY_EXIST_ERROR_MSG = "此邮箱已被占用";
    public static final int PASSWORD_STR_ERROR_CODE = 629;
    public static final String PASSWORD_STR_ERROR_MSG = "密码为6-18位数字、字母或符号";

    /**业务相关错误码：7xx,记录业务级别具体错误，比如登陆密码错误**/
    public static final int ERROR_CODE_701 = 701;
    public static final int ERROR_CODE_702 = 702;
    public static final int ERROR_CODE_703 = 703;
    public static final int ERROR_CODE_704 = 704;
    public static final int ERROR_CODE_705 = 705;
    public static final int ERROR_CODE_706 = 706;
    public static final int ERROR_CODE_707 = 707;
    public static final int ERROR_CODE_708 = 708;
    public static final int ERROR_CODE_709 = 709;
    public static final int ERROR_CODE_710 = 710;
    public static final int ERROR_CODE_711 = 711;
    public static final int ERROR_CODE_712 = 712;
    public static final int ERROR_CODE_713 = 713;
    public static final int ERROR_CODE_714 = 714;
    public static final int ERROR_CODE_715 = 715;
    public static final int ERROR_CODE_716 = 716;

}
