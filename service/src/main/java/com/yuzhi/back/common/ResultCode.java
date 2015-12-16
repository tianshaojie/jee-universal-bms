package com.yuzhi.back.common;

import java.io.Serializable;

public class ResultCode implements Serializable {
    //成功
    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MSG = "操作成功";

    //失败
    public static final int ERROR_CODE = 500;
    public static final String ERROR_MSG = "服务器内部错误，请重试";

    /**通用错误码：6xx,记录业务级别通用错误，比如：数据库错误**/
    public static final int PARAM_ERROR_CODE = 601;
    public static final String PARAM_ERROR_MSG = "参数错误，请检查参数";

    public static final int DB_ERROR_CODE = 602;
    public static final String DB_ERROR_MSG = "数据库操作失败，请稍后再试";

    //Token有误
    public static final int TOKEN_ERROR_CODE = 603;
    public static final String TOKEN_ERROR_MSG = "Token过期，请重新登录";


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

}
