package com.example.demo;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.demo.common.log.LogFactory;
import com.example.demo.common.log.LogUtil;
import com.example.demo.common.log.log.LogNameEmun;
import com.example.demo.common.log.log.Logs;
import org.junit.Test;
 
public class JDKLogTest {


    @Test
    public void testWrite(){
       // Logs logs = new Logs();
        try{
            System.out.println(1/0);
        }catch (Exception e) {
            Logs.write("aa", LogNameEmun.Request_Info,e);
        }
    }
    @Test
    public void test01() {
        // 级别从上往下依次降低
       // Logs.info("aaaa");

    }

    @Test
    public void test03() {
//        sysLog.info("test03 info!");
    }

    @Test
    public void test04() {
//        sysLog.info("test04 info!");
    }
}