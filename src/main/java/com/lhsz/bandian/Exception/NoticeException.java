package com.lhsz.bandian.Exception;

/**
 * @author zhusenlin
 * Date on 2020/8/17  16:55
 */
public class NoticeException extends RuntimeException {
    public NoticeException() {
        super();
    }

    public NoticeException(String message) {
        super(message);
    }

    public NoticeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoticeException(Throwable cause) {
        super(cause);
    }
}
