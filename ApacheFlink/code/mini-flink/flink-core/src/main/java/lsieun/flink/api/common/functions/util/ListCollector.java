package lsieun.flink.api.common.functions.util;


import java.util.List;

import lsieun.flink.annotation.Internal;
import lsieun.flink.util.Collector;

/**
 * A {@link Collector} that puts the collected elements into a given list.
 *
 * @param <T> The type of the collected elements.
 */
@Internal
public class ListCollector<T> implements Collector<T> {

    private final List<T> list;

    public ListCollector(List<T> list) {
        this.list = list;
    }

    @Override
    public void collect(T record) {
        list.add(record);
    }

    @Override
    public void close() {}
}
