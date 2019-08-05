package com.han.entity;

/**
 * @Author: Hanjiafeng
 * @Date: 2019/8/5
 */
public class Customer implements Comparable<Customer> {
    private int status;
    private String code;
    private String name;
    private int totalCount;
    private int successCount;
    private int failureCount;
    private String successRate;

    public Customer(int status, String code, String name, int totalCount, int successCount, int failureCount, String successRate) {
        this.status = status;
        this.code = code;
        this.name = name;
        this.totalCount = totalCount;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.successRate = successRate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", totalCount=" + totalCount +
                ", successCount=" + successCount +
                ", failureCount=" + failureCount +
                ", successRate='" + successRate + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int compareTo(Customer o) {
        return o.getSuccessCount() - totalCount;
    }
}
