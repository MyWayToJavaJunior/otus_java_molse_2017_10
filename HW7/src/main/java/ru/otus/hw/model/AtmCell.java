package ru.otus.hw.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AtmCell implements Serializable {
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
                return count+ "x"+ nominal;
        }
}
