package com.knowdata.framework.core.model.server;

/**
 * @author johnny-zen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2023/3/21 18:30
 * @description 应用程序的内存状态
 */
public class ApplicationMemoryStatus implements HardwareEnvironmentStatus {

    /** unit : byte **/
    private Long totalSize;
    /** unit : byte **/
    private Long freeSize;
    /** unit : byte **/
    private Long maxSize;

    /** 使用率; unit : % **/
    private Double usageRate;

    /**
     * 采集此时此刻的内存状态信息
     */
    public static ApplicationMemoryStatus collect(){
        ApplicationMemoryStatus memoryStatus = new ApplicationMemoryStatus();
        Runtime runtime = Runtime.getRuntime();

        memoryStatus.setFreeSize(runtime.freeMemory());
        memoryStatus.setTotalSize(runtime.totalMemory());
        memoryStatus.setMaxSize(runtime.maxMemory());

        Double usageRate = Double.valueOf( ((memoryStatus.totalSize - memoryStatus.freeSize ) / memoryStatus.totalSize)*100 );

        memoryStatus.setUsageRate(  usageRate );
        return memoryStatus;
    }

    public static void main(String[] args) {
        ApplicationMemoryStatus memoryEnvironmentStatus = ApplicationMemoryStatus.collect();
        System.out.println(memoryEnvironmentStatus.toStringAsMB());
    }

    @Override
    public String toString() {
        return "ApplicationMemoryStatus{" +
                "totalSize=" + totalSize + "byte" +
                ", freeSize=" + freeSize + "byte" +
                ", maxSize=" + maxSize + "byte" +
                ", usageRate=" + usageRate + "%" +
                '}';
    }

    public String toStringAsMB() {
        return "ApplicationMemoryStatus{" +
                "totalSize=" + (totalSize/1024/1024) + "mb" +
                ", freeSize=" + (freeSize/1024/1024) + "mb" +
                ", maxSize=" + (maxSize/1024/1024) + "mb" +
                ", usageRate=" + usageRate + "%" +
                '}';
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Long getFreeSize() {
        return freeSize;
    }

    public void setFreeSize(Long freeSize) {
        this.freeSize = freeSize;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }

    public Double getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(Double usageRate) {
        this.usageRate = usageRate;
    }
}
