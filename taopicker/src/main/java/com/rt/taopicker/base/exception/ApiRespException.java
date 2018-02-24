package com.rt.taopicker.base.exception;

import com.rt.taopicker.data.api.entity.RespEntity;

/**
 * Created by yaoguangyao on 2017/9/17.
 */

public class ApiRespException extends BaseException {

    public ApiRespException(RespEntity respEntity) {
        this(respEntity.getErrorCode(), respEntity.getErrorDesc(), respEntity);
    }

    public ApiRespException(Integer code, String msg, Object data) {
        super(code, msg, data);
    }
}
