package com.han.send.impl;

/**
 * @Author: Hanjiafeng
 * @Date: 2019/8/4
 */
public class FetcherException extends Exception {
    public FetcherException() {
        super();
    }

    public FetcherException(String msg) {
        super(msg);
    }

    public FetcherException(String msg, Throwable throwable) {
        super(msg);
        initCause(throwable);
    }
}
