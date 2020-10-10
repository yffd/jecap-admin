package com.yffd.jecap.admin.domain.exception;

import com.yffd.jecap.common.base.exception.BaseException;

public class SysException extends BaseException {
    private static final long serialVersionUID = -689907954359500172L;

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public static SysException cast() {
        return new SysException("系统错误【代号：admin】", null);
    }

    public static SysException cast(String message) {
        return new SysException(message, null);
    }

    public static SysException cast(Throwable cause) {
        return new SysException(null, cause);
    }

    public static SysException cast(Throwable cause, String message) {
        return new SysException(message, cause);
    }

}
