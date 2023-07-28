package com.skshieldus.esecurity.common.utils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CollectionUtils {

    private CollectionUtils() {
        throw new IllegalStateException("can access only static utility methods");
    }

    /**
     * list partition by size
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 3. 3.
     */
    public static <T> Collection<List<T>> partitionBySize(List<T> inputList, int size) {
        final AtomicInteger counter = new AtomicInteger(0);
        return inputList.stream().collect(Collectors.groupingBy(s -> counter.getAndIncrement() / size)).values();
    }

}
