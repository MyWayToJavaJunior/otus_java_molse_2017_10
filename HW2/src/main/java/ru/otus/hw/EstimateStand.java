package ru.otus.hw;

import com.carrotsearch.sizeof.RamUsageEstimator;

public class EstimateStand<T> {

    public void printSize(T t) {
        long length = RamUsageEstimator.sizeOf(t);
        System.out.println("Размер: " + RamUsageEstimator.humanReadableUnits(length));
    }
}
