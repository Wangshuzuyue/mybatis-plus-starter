package com.yunxin.mp.util;//package com.yunxin.mp.util;
import java.util.Random;

/**
 * 分布式ID生成器，基于SnowFlake规则修改。
 * 主要修改有：
 * <pre>
 * 1：工作进程ID减少为5位，序列号长度由12位改为17位；
 * 2：标准情况下，不同毫秒内生成ID时，序列号采用随机数，解决低并发时产生ID全是偶数的问题；
 *
 * 3：生成业务ID时，须传入一个coreId，新生成的ID末四位将与coreId一致；
 * 4：同一毫秒内生成业务ID时，序列号在前序基础上累加10000，以使万位数字递增；
 * (同时带来一个影响，同一毫秒时间内一台主机只能产生14个业务ID，即1秒14000个，一般并发情况下已经足够)
 * </pre>
 *
 * <p>
 * Use snowflake algorithm. Length is 64 bit.
 * </p>
 *
 * <pre>
 * 1bit   sign bit.
 * 41bits timestamp offset from 2019.01.01 to now.
 * 5bits  worker process name. (范围0-31)
 * 17bits auto increment offset in one mills
 * </pre>
 *
 * <p>
 * Call @{@code setWorkerId} to set.
 * </p>
 *
 * @author jianglongtao/ huangzuwang
 */

public final class SnowKey extends AbstractGenerator {

    private byte sequenceOffset;

    //序列号
    private long sequence;

    private long lastTime;

    private long businessSequence;

    private long businessLastTime;

    private static final Random RANDOM = new Random();


    public SnowKey(Long workId) {
        setWorkerId(workId);
    }

    @Override
    public synchronized long generateKey() {
        long currentMillis = System.currentTimeMillis();
        if (lastTime > currentMillis){
            throw new RuntimeException(String.format("Clock is moving backwards, last time is %s milliseconds, current time is %s milliseconds", lastTime, currentMillis));
        }
        if (lastTime == currentMillis) {
            if (0L == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            // 末位抖动，使奇偶比随机更加均匀
//            vibrateSequenceOffset();
            sequence = (long)RANDOM.nextInt(RANDOM_BOUND);
        }
        lastTime = currentMillis;
        return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (getWorkerId() << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    private void vibrateSequenceOffset() {
        sequenceOffset = (byte) (~sequenceOffset & 1);
    }

    @Override
    public synchronized long generateBusinessKey(long coreId) {
        long currentMillis = System.currentTimeMillis();
        if (businessLastTime > currentMillis){
            throw new RuntimeException(String.format("Clock is moving backwards, last time is %s milliseconds, current time is %s milliseconds", businessLastTime, currentMillis));
        }
        if (businessLastTime == currentMillis) {
            // 同一毫秒时间内
            // 加10000，增加万位数值
            long currentLong = businessSequence + 10000L;
            if (currentLong >= SEQUENCE_MASK) {
                currentMillis = waitUntilNextTime(currentMillis);
                // 切毫秒，序列归零
                businessSequence = 0;
            } else {
                businessSequence = currentLong;
            }
        } else {
            businessSequence = 0;
        }
        businessLastTime = currentMillis;

        long result = ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (getWorkerId() << WORKER_ID_LEFT_SHIFT_BITS) | businessSequence;

        long prefix = result / 10000 * 10000;
        long suffix = coreId % 10000;

        return prefix + suffix;
    }

}
