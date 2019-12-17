package ru.ospos.npf.commons.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Getter
public class DataResult <T> extends OperationResult {

    DataResult() {}

    private long count;

    private Collection<T> data = new ArrayList<>();

    public static <T> DataResult<T> data(long count, Collection<T> data) {

        DataResult<T> dataResult = new DataResult<>();
        dataResult.count = count;
        dataResult.data = data;

        return dataResult;
    }

    public static <T> DataResult<T> data(Collection<T> data) {

        if (data != null) {
            return data(data.size(), data);
        }
        return data(0, new ArrayList<>());
    }

    public static <T> DataResult<T> data(T data) {

        if (data != null) {
            return data(1, Collections.singletonList(data));
        }
        return data(0, new ArrayList<>());
    }
}
