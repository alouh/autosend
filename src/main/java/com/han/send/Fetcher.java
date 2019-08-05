package com.han.send;

import com.han.entity.Customer;
import com.han.send.impl.FetcherException;

import java.util.List;

/**
 * @Author: Hanjiafeng
 * @Date: 2019/8/4
 */
public interface Fetcher {
    /**
     * 登录
     *
     * @throws FetcherException 登录异常
     */
    void login() throws FetcherException;

    /**
     * 查询指定参数的使用情况
     *
     * @param pageIndex 页码
     * @param pageSize  每页数量
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param category  分类
     * @return .
     * @throws FetcherException 查询异常
     */
    List<Customer> selectCustomers(int pageIndex, int pageSize, long startDate, long endDate, String category) throws FetcherException;


}
