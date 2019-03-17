package com.example.demo.common.log.log;

import com.example.demo.config.PropertyConfigurer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logs {

    private static String logPath = PropertyConfigurer.getProperty("log.path");//"D:/test/log/";
    private static Log log = LogFactory.getLog(Logs.class);

    private static String filePath = System.getProperty("catalina.base") + "/logs/";


    private static String getLogPath() {
        if (!validateBlank(logPath)) {
            return logPath;
        }
        return filePath;
    }

    //        public Logs(String logName){
//            this.logName = logName;
//        }
    private static boolean validateBlank(String msg) {
        if (msg == null || msg.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 根据日志的类型创建文件
     *
     * @param logNameEmun
     * @return
     * @throws IOException
     */
    private static File createFile(LogNameEmun logNameEmun) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());
        String logName = logNameEmun.getLogName(logNameEmun);
        File file = new File(getLogPath() + nowDate + "_" + logName + ".txt");
        if (!file.exists()) {
            File file1 = new File(file.getParent());
            if (!file1.exists()) {
                file1.mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }


    public static void write(String message, LogNameEmun logNameEmun, Exception ex) {
        if (validateBlank(message) || logNameEmun == null) return;
        try {
            writeData(createFile(logNameEmun), message, ex);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("日志文件创建失败");
        }
    }

    public static void write(String message, LogNameEmun logNameEmun) {
        write(message, logNameEmun, null);
    }

    /**
     * 把打印的消息输出到文件
     *
     * @param file
     * @param msg
     * @throws IOException
     */
    private static void writeData(File file, String msg) {
        writeData(file, msg);
    }

    /**
     * 把打印的消息输出到文件
     *
     * @param file
     * @param msg
     * @throws IOException
     */
    private static void writeData(File file, String msg, Exception ex) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNow = sdf.format(new Date());
            StringBuffer sb = new StringBuffer(String.format("[%s]", dateNow));
            sb.append(getClassAndMethodName());
            sb.append(String.format("----->  [logInfo:%s]", msg));
            bw.write(sb.toString());// 往已有的文件上添加字符串

            if (ex != null) ex.printStackTrace(new PrintWriter(fw));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) bw.close();
                if (fw != null) fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //InheritableThreadLocal<Integer> number = new InheritableThreadLocal<>();
    private static String getClassAndMethodName() {
        JavaLangAccess access = SharedSecrets.getJavaLangAccess();
        Throwable throwable = new Throwable();
        int depth = access.getStackTraceDepth(throwable);

        boolean lookingForLogger = true;
        int i = 0;
        for (int ix = 0; ix < depth; ix++) {
            // Calling getStackTraceElement directly prevents the VM
            // from paying the cost of building the entire stack frame.
            StackTraceElement frame = access.getStackTraceElement(throwable, ix);
            String cname = frame.getClassName();

            if (!cname.endsWith("Logs")) {
                return String.format("{className[%s]-method[%s]}", cname, frame.getMethodName());
            }

        }
        return null;

    }

    }