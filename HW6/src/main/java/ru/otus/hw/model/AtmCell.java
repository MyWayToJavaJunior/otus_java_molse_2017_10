package ru.otus.hw.model;

import lombok.Data;

@Data
public class AtmCell implements Comparable{
        private int nominal;
        private int count;

        public AtmCell(int nominal, int count) {
                this.nominal = nominal;
                this.count = count;
        }

        public void increaseCount(int n){
                this.count += n;
        }

        public void descreaseCount(int n){
                this.count -= n;
        }

        @Override
        public String toString() {
                return "AtmCell{" +
                        "nominal=" + nominal +
                        ", count=" + count +
                        '}';
        }

        @Override
        public int compareTo(Object o) {
                if (this.getNominal()> ((AtmCell) o).getNominal()) return -1;
                else if (this.getNominal()< ((AtmCell) o).getNominal()) return 1;
                return 0;
        }
}
