package com.limengxiang.breeze.manager;

import lombok.Data;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public interface ICoordinator {

    @Data
    class DutyInfo {
        private boolean on;
        private long tickFrom;
        private long tickTo;

        public DutyInfo() {
            on = false;
            tickFrom = 0L;
            tickTo = 0L;
        }

        public boolean on() {
            return on;
        }

        public long tickFrom() {
            return tickFrom;
        }

        public long tickTo() {
            return tickTo;
        }

        public boolean negative() {
            return tickFrom > tickTo;
        }
    }

    DutyInfo acquireDuty();

}
