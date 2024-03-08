package com.knowdata.framework.idgenerator.service;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/9/5 18:43
 * @description
 *  1. There is no problem using it in a standalone environment, but if used in a distributed environment, different workIds need to be assigned. If the workIds are the same, it may result in the generated IDs being the same.
 *   在单机情况下使用雪花算法没有问题，但如果在分布系统下使用，就需要分配不同的workId,如果workId相同，可能会导致生成的id相同。
 * @refrence-doc
 *  1. [越过时针回拨的坑, 解决重启导致的雪花算法id重复问题 - CSDN](https://blog.csdn.net/qq_33709508/article/details/118732375)
 * @gpt-promt
 */
public class WorkerNodeIdGenerator {
    /**
     * initial timestamp ( 开始时间截 )
     * @note
     *   1. usage time duration(使用时长) = (2^41)/(1000×60×60×24×365) = 69 years
     *   2.Once it is determined that it cannot be changed, otherwise the time will be recalled or changed, which may cause duplicate or conflicting IDs
     *     一旦确定不可更改，否则时间被回调，或改变，可能会造成id重复或冲突
     *   3. The initial Time Value = 1693914188095L = 2023-09-05 19:43:08 (UTC+8)
     */
    private final static long initialTime = 1693914188095L;

    /**
     * The number of digits occupied by the machine ID
     * 机器ID所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * Number of digits occupied by data center ID
     * 数据中心ID所占的位数
     */
    private final long dataCenterIdBits = 5L;

    /**
     * Maximum machine ID supported
     * 支持的最大机器ID
     * @note 结果是 31 (此移位算法可很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * The maximum supported data center id
     * 支持的最大数据中心ID
     * @note resulting in 31 / 结果是31
     */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /**
     * Number of digits occupied by the serial number
     * 序列号所占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * Machine ID moved {sequenceBits=12} bits to the left
     * 机器ID向左移(sequenceBits=12)位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * Move the data center ID to the left by {sequenceBits + workerIdBits = 12 + 5} bits
     * 数据中心id向左移(sequenceBits + workerIdBits=12+5)位
     */
    private final long dataCenterIdShift = sequenceBits + workerIdBits;

    /**
     * Move the time cutoff to the left by {sequenceBits + workerIdBits + dataCenterIdBits=5+5+12=22} bits
     * 时间截向左移 {sequenceBits + workerIdBits + dataCenterIdBits=5+5+12=22} 位
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * Mask for generating serial numbers
     * 生成序列号的掩码
     * @note This is 4095. / 这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * Work machine ID
     * 工作机器ID
     * @note this is [0, 2^5] = [0, 31]
     */
    private long workerId;

    /**
     * Data Center ID
     * 数据中心ID
     * @note this is [0, 2^5] = [0, 31]
     */
    private long dataCenterId;

    /**
     * Millisecond sequence
     * 毫秒内序列
     * @note 0~4095
     */
    private long sequence = 0L;

    /**
     * Last ID generation deadline
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * Default Work Machine ID
     * 默认的工作机器ID
     */
    private final static long defaultWorkId = 0L;

    /**
     * Default data center ID
     * 默认的数据中心ID
     */
    private final static long defaultDataCenterId = 0L;

    /** Constructors **/

    /**
     * constructor function
     * 构造函数
     */
    public WorkerNodeIdGenerator() {
        this.workerId = defaultWorkId;
        this.dataCenterId = defaultDataCenterId;
    }

    /**
     * constructor function
     * 构造函数
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public WorkerNodeIdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDataCenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = datacenterId;
    }

    /** Methods **/

    /**
     * Obtain the next ID
     * 获得下一个ID
     * @note this method is thread safe.(该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = generateTimestamp();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，此时应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - initialTime) << timestampLeftShift) //
                | (dataCenterId << dataCenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }


    /**
     * Block until the next millisecond until a new timestamp is obtained
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp Last ID generation deadline(上次生成ID的时间截)
     * @return Current timestamp(当前时间戳)
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = generateTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = generateTimestamp();
        }
        return timestamp;
    }

    /**
     * Returns the current time in milliseconds
     * 返回以毫秒为单位的当前时间
     * @return Current timestamp, milliseconds.(当前时间戳, 毫秒)
     */
    protected long generateTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取雪花ID / get snowflake id
     * @param workerId
     * @param dataCenterId
     * @return
     */
    public static Long getIdAsLong(Long workerId, Long dataCenterId) {
        WorkerNodeIdGenerator workerNodeId = new WorkerNodeIdGenerator(workerId, dataCenterId);
        return workerNodeId.nextId();
    }

    public static Long getIdAsLong() {
        return getIdAsLong(defaultWorkId, defaultDataCenterId);
    }

    /**
     * Random ID generation using snowflake algorithm
     * 随机id生成，使用雪花算法
     * @return
     */
    public static String getId(Long workerId, Long dataCenterId) {
        return String.valueOf( getIdAsLong(workerId, dataCenterId) );
    }

    public static String getId() {
        String id = String.valueOf(getIdAsLong());
        return id;
    }
}
