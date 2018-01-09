package com.zyf.dc.base;

/**
 * JSR 303 验证组
 */
public interface Group {

    /**
     * Controller 验证组
     */
    interface C {
        interface Insert {
        }

        interface Update {
        }

        interface UpdateStatus {
        }

        interface Delete {
        }

        interface Query {
        }
    }

    /**
     * Service 验证组
     */
    interface S {
        interface Insert {
        }

        interface Update {
        }

        interface UpdateStatus {
        }

        interface Delete {
        }

        interface Query {
        }
    }

}
