package com.praful.projects.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Prafulla Rathore
 */
public class UniqueIdentityGenerator {

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public synchronized static String generateUniqueIdentity() {
        return getDatePrefix() + getNextSequence();
    }

    private static String getNextSequence() {
        if (atomicInteger.get() == 999) {
            atomicInteger.set(0);
        }
        return String.format("%03d", atomicInteger.incrementAndGet());
    }


    private static String getDatePrefix() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmm");
        return dateFormat.format(new Date());
    }
}
