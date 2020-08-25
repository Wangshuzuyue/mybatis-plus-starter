package com.yunxin.mp.util;

import java.util.Calendar;

public abstract class AbstractGenerator {

    public static final long EPOCH;

    protected static final long SEQUENCE_BITS = 17L;

    protected static final long WORKER_ID_BITS = 5L;

    protected static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    protected static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    protected static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    protected static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

    /**
     * 取32的倍数, 一万以内最大的数是9984
     */
    protected static final int RANDOM_BOUND = 9984;

    private long workerId;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.JANUARY, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }

    public void setWorkerId(final long workerId) {
        if (!(workerId >= 0L && workerId < WORKER_ID_MAX_VALUE)){
            throw new RuntimeException(String.format("workId设置异常,需在0 - %s取值.[%s]",WORKER_ID_MAX_VALUE, workerId));
        }
        this.workerId = workerId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long generateKey() {
        return 0;
    }

    public long generateBusinessKey(long coreId) {
        return 0;
    }

    /**
     * 等待到下一个毫秒
     *
     * @param lastTime
     * @return
     */
    protected long waitUntilNextTime(final long lastTime) {
        long time = System.currentTimeMillis();
        while (time <= lastTime) {
            time = System.currentTimeMillis();
        }
        return time;
    }

}
