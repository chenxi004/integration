package com.practice.ch5.bq;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class QueueItem<T> implements Delayed {
    private long activeTime;//到期时间 单位是纳秒
    private T data;

    public T getData() {
        return data;
    }
    public QueueItem(long period, T data) {
        this.activeTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(period,TimeUnit.MILLISECONDS) ;
        this.data = data;
    }


    @Override
    public long getDelay(TimeUnit timeUnit) {
        return   TimeUnit.MICROSECONDS.convert(this.activeTime-System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        long d = this.getDelay(TimeUnit.MILLISECONDS)-delayed.getDelay(TimeUnit.MILLISECONDS);
        return d==0?0: (d>0?1:-1);
    }
}
