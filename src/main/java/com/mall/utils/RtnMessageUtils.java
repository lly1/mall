package com.mall.utils;


import com.mall.common.RtnMessage;
import com.mall.constant.ErrorType;
import org.apache.commons.lang3.StringUtils;

/**
 * 构造RtnMessage
 */
public class RtnMessageUtils {
    /**
     * 构造成功的结果
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RtnMessage<T> buildSuccess(T data) {
        RtnMessage<T> t = new RtnMessage<T>(ErrorType.SUCCESS, data);
        return t;
    }

    /**
     * 构造失败的结果
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RtnMessage<T> buildFailed(T data) {
        RtnMessage<T> t = new RtnMessage<T>(ErrorType.FAILED, data);
        return t;
    }

    /**
     * @Author yuerfeng 14090408
     * @Description 复制message
     * @Date 2019-07-18 20:03
     * @param
     * @return
     **/
    public static <T> RtnMessage<T> buildFailedFromRtn(RtnMessage rtnMessage) {
        if(rtnMessage == null){
            return buildFailed(null);
        }
        RtnMessage<T> t = new RtnMessage<T>(ErrorType.FAILED, null);
        t.setMessage(rtnMessage.getMessage());
        t.setCode(rtnMessage.getCode());
        return t;
    }

    /**
     * 构造失败的结果，包含信息的错误提示
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> RtnMessage<T> buildError(ErrorType type) {
        RtnMessage<T> t = new RtnMessage<T>(type, null);
        return t;
    }

    @Deprecated
    public static <T> RtnMessage<T> buildResult(String code, String message, T data) {
        RtnMessage<T> rtnMessage = new RtnMessage();
        rtnMessage.setCode(code);
        rtnMessage.setMessage(message);
        rtnMessage.setData(data);
        return rtnMessage;
    }

    public static <T> RtnMessage<T> buildResult(String code, String message) {
        RtnMessage<T> rtnMessage = new RtnMessage();
        rtnMessage.setCode(code);
        rtnMessage.setMessage(message);
        rtnMessage.setData(null);
        return rtnMessage;
    }



    public static <T> RtnMessage<T> paramValidateFailed(String defaultMessage) {
        RtnMessage<T> result = buildError(ErrorType.PARA_ERROR);
        if (StringUtils.isNotBlank(defaultMessage)) {
            result.setMessage(defaultMessage);
        }
        return result;
    }

    /**
     * 业务异常返回
     * @param exceptionMessage
     * @param <T>
     * @return
     */
    public static <T>RtnMessage<T> serviceExceptionResult(String exceptionMessage){
        RtnMessage<T> result = buildError(ErrorType.SERVICE_EXCEPTION);
        if (StringUtils.isNotBlank(exceptionMessage)){
            result.setMessage(exceptionMessage);
        }
        return result;
    }

}
