package com.touceng.common.response;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 业务错误码定位
 * @createTime 2018年7月2日 下午4:25:33
 * @copyright: 上海投嶒网络技术有限公司
 */
public enum EResultEnum {

    SUCCESS(200, "操作成功"),
    BAD_REQUEST(40000, "参数错误"),
    UNAUTHORIZED(40001, "Unauthorized"),
    FORBIDDEN(40003, "Forbidden"),
    NOT_FOUND(40004, "Not Found"),
    FILE_NOT_EMPTY(40005, "上传文件为空"),
    FILE_FORMAT_ERROR(40006, "文件上传格式错误"),
    SAVE_FILE_ERROE(40007, "文件上传错误"),
    SMS_TIME_ERROE(40008, "获取验证码频率过快"),
    SMS_VERIFY_ERROE(40009, "验证码验证失败"),
    SMS_SEND_ERROE(40010, "验证码发送失败"),
    SMS_MOBILE_ERROE(40011, "电话号码格式不正确"),
    PAY_SIGN_ERROE(40012, "请求支付加密签文不正确"),
    PAY_ORDER_ERROE(40013, "请求支付不正确"),
    PAY_NOTIFY_ERROE(40014, "支付结果不正确"),
    PAY_AGAIN_ERROE(40015, "订单已完成，请重新下单"),
    GET_PIAOFUTONG_ERROE(40016, "获取票付通接口异常"),
    PAY_DECRYPT_ERROE(40017, "支付回调解密异常"),
    TOKEN_CREATE_ERROR(40018, "生成token异常"),
    ERROR(50000, "服务打了个盹，请稍后重试"),
    QUERY_ERROR(50001, "查询异常"),
    INSERT_ERROR(50002, "插入异常"),
    UPDATE_ERROR(50003, "修改异常"),
    DELETE_ERROR(50004, "删除异常"),
    LOGIN_ERROR(50005, "登录异常"),
    VALIDATE_ERROR(50006, "数据验证错误"),
    STATUS_ERROR(50007, "状态不正确"),
    REQUEST_TIMEOUT(52001, "请求超时，请重试。"),
    REQUEST_REMOTE_ERROR(52002, "服务异常。");

    private Integer code;

    private String msg;

    private EResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}