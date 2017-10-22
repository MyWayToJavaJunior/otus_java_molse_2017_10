package ru.otus.hw;

import com.carrotsearch.sizeof.RamUsageEstimator;

public class EstimateStand<T> {

    private T t;

    public void printSize(T t) {
        long length = RamUsageEstimator.sizeOf(t);
        System.out.println(RamUsageEstimator.humanReadableUnits(length));
    }
}
