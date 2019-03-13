package com.example.demo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {
        public static Logger log = Logger.getLogger(TestLogger.class.toString());
        //        SEVERE（最高值） WARNING INFO CONFIG FINE FINER FINEST（最低值） 往后不打印日志
//        此外，还有一个级别 OFF，可用来关闭日志记录，使用级别 ALL 启用所有消息的日志记录。
        public static void main(String[] args) {
                // all→finest→finer→fine→config→info→warning→server→off
                // 级别依次升高，后面的日志级别会屏蔽之前的级别
                log.setLevel(Level.ALL);
                log.finest("finest");
                log.finer("finer");
                log.fine("fine");
                log.config("config");
                log.info("info");
                log.warning("warning");
                log.severe("server");


                Logger log = Logger.getLogger("TestLogger");
                log.setLevel(Level.ALL);
////                Logger log1 = Logger.getLogger("lavasoft");
////                System.out.println(log==log1);     //true
//                Logger log2 = Logger.getLogger("lavasoft.blog");
//                log2.setLevel(Level.FINE);
//
                log.info("aaa");
//                log2.info("bbb");
                log.config("11111");
                log.fine("。。。。。。。。。");
        } 
}