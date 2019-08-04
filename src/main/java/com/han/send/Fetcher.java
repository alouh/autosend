package com.han.send;

import com.han.send.impl.FetcherException;

/**
 * @Author: Hanjiafeng
 * @Date: 2019/8/4
 */
public interface Fetcher {
    /**
     * 登录
     */
    void login() throws FetcherException;
}
