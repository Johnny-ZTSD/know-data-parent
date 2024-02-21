package com.knowdata.framework.core.model.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.StringTokenizer;

/**
 * @author johnny-zen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2023/3/21 19:10
 * @description ...
 * @reference-doc
 *  [1] java计算cpu使用率 - 天兴工作室 - http://www.easyaq.com/post/19606.html
 *  [2] Java如何获取系统cpu、内存、硬盘信息 - 博客园 - https://www.cnblogs.com/minkaihui/p/4075621.html
 */
public class ApplicationCpuStatus {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationCpuStatus.class);

    private static final int CPUTIME = 30;
    private static final int PERCENT = 100;
    private static final int FAULTLENGTH = 10;

    private static final File versionFile = new File("/proc/version");

    private static String linuxVersion = null;

    private ThreadGroup rootThreadGroup;

    private Integer activeThreads;

    public static ApplicationCpuStatus collect(){
        ThreadGroup rootThreadGroup;
        for(rootThreadGroup = Thread.currentThread().getThreadGroup(); rootThreadGroup.getParent() != null;) {
            rootThreadGroup = rootThreadGroup.getParent();
        }
        int activeThreads = rootThreadGroup.activeCount();

        double cpuRatio = 0;

//         if (OSTypeEnum.isWindows(OSTypeEnum.getOSName())) {
//            cpuRatio = this.getCpuRatioForWindows();
//         } else {
//            // cpuRatio = this.getCpuRateForLinux();
//         }
        //TODO 待继续
        return null;
    }

    private static double getCpuRateForLinux() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader brStat = null;
        StringTokenizer tokenStat = null;
        try {

            logger.debug("Get usage rate of CPU , linux version: " + linuxVersion);

            Process process = Runtime.getRuntime().exec("top -b -n 1");
            is = process.getInputStream();
            isr = new InputStreamReader(is);
            brStat = new BufferedReader(isr);

            if (linuxVersion.equals("2.4")) {
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                tokenStat = new StringTokenizer(brStat.readLine());
                tokenStat.nextToken();
                tokenStat.nextToken();

                String user = tokenStat.nextToken();

                tokenStat.nextToken();
                String system = tokenStat.nextToken();
                tokenStat.nextToken();
                String nice = tokenStat.nextToken();
                System.out.println(user + " , " + system + " , " + nice);
                user = user.substring(0, user.indexOf("%"));
                system = system.substring(0, system.indexOf("%"));
                nice = nice.substring(0, nice.indexOf("%"));
                float userUsage = new Float(user).floatValue();
                float systemUsage = new Float(system).floatValue();
                float niceUsage = new Float(nice).floatValue();

                return (userUsage + systemUsage + niceUsage) / 100;

            } else {
                brStat.readLine();
                brStat.readLine();
                tokenStat = new StringTokenizer(brStat.readLine());
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                String cpuUsage = tokenStat.nextToken();
                logger.debug("CPU idle : " + cpuUsage);
                Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));
                return (1 - usage.floatValue() / 100);
            }
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
            freeResource(is, isr, brStat);
            return 1;
        } finally {
            freeResource(is, isr, brStat);
        }
    }

    private double getCpuRatioForWindows() {
        Double cpuUsageRate = -1.0;
        try {

            String procCmd = System.getenv("windir")
                    + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";

            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPUTIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));

            long idletime = -1;
            long busytime = -1;
            if (c0 != null && c1 != null) {
                idletime = c1[0] - c0[0];
                busytime = c1[1] - c0[1];
                cpuUsageRate = Double.valueOf(PERCENT * (busytime) / (busytime + idletime)).doubleValue();
            } else {
                cpuUsageRate = 0.0;
            }
            logger.debug("CPU Usage Rate: " + Double.valueOf(PERCENT * (busytime)*1.0 / (busytime + idletime)).intValue() + "%");
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
        return cpuUsageRate;
    }

    private long[] readCpu(final Process proc) {

        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();

            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }

            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;

            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }

                String caption = line.substring( capidx, cmdidx - 1 ) .trim();
                String cmd = line.substring( cmdidx, kmtidx - 1 ).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }

                // log.info("line="+line);
                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    idletime += Long.valueOf( line.substring( kmtidx, rocidx - 1 ).trim() ).longValue();
                    idletime += Long.valueOf(line.substring( umtidx, wocidx - 1 ).trim()).longValue();
                    continue;
                }

                kneltime += Long.valueOf( line.substring( kmtidx, rocidx - 1 ).trim() ).longValue();
                usertime += Long.valueOf( line.substring( umtidx, wocidx - 1 ).trim() ).longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br) {
        try {
            if(is != null){
                is.close();
            }
            if(isr != null){
                isr.close();
            }
            if(br != null){
                br.close();
            }
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }
    }

}
