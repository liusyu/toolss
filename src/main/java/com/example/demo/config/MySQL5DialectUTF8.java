package com.example.demo.config;

import org.hibernate.dialect.MySQL5Dialect;



    /**
     * 设置数据库建表方言
     */
    public class MySQL5DialectUTF8 extends MySQL5Dialect {

        @Override
        public String getTableTypeString() {
            return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
        }
    }

