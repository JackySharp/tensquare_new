package com.tensquare.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean status;//返回成功或者失败
    private Integer returnCode;//返回码
    private String message;//返回提示信息
    private Object data;//返回的数据

    public Result(boolean status, Integer returnCode, String message) {
        this.status = status;
        this.returnCode = returnCode;
        this.message = message;
    }
}
