package com.someget.admin.model.vo;

import lombok.Data;

/**
 * layUi的table专用返回response
 * @author zyf
 * @date 2022-03-26 01:03
 */
@Data
public class TableResponse<T> {

    /**
     * 数据的数量
     */
    private Long count;

    /**
     * vo数据
     */
    private T data;

    /**
     * 错误代码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String msg;

    /**
     * 成功
     */
    public TableResponse(T data, Long count) {
        this.count=count;
        msg = "操作成功";
        code = 0;
        this.data = data;
    }

    public TableResponse() {
        msg = "操作成功";
        code = 0;
    }

    /**
     * 失败
     */
    public TableResponse(String msg) {
        this.count = 0L;
        this.msg = msg;
        this.code = -1;
        this.data = null;
    }
}
