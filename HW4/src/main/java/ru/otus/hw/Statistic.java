package ru.otus.hw;

import java.time.ZonedDateTime;

public class Statistic {
    private ZonedDateTime dateTime;
    private Generation generation;
    private String gcName;
    private  long duration;

    private Statistic() {
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public Generation getGeneration() {
        return generation;
    }

    public String getGcName() {
        return gcName;
    }

    public long getDuration() {
        return duration;
    }

    public static StatisticBuilder newBuilder() {
        return new Statistic().new StatisticBuilder();
    }



    public class StatisticBuilder {


        private StatisticBuilder() {
        }

        public StatisticBuilder setDateTime(ZonedDateTime dateTime) {
            Statistic.this.dateTime = dateTime;
            return this;
        }

        public StatisticBuilder setGeneration(Generation generation) {
            Statistic.this.generation = generation;
            return this;
        }

        public StatisticBuilder setGcName(String gcName) {
            Statistic.this.gcName = gcName;
            return this;
        }

        public StatisticBuilder setDuration(long duration) {
            Statistic.this.duration = duration;
            return this;
        }

        public Statistic build() {
            return Statistic.this;
        }
    }
}
