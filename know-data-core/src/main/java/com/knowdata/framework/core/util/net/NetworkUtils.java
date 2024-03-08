package com.knowdata.framework.core.util.net;

import com.knowdata.framework.core.util.lang.ObjectUtils;
import com.knowdata.framework.core.util.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * @author johnnyzen
 * @project know-data-parent
 * @create-time 2023/5/6  03:01:30
 * @description
 *
 * @reference-doc
 *  [1] Java 判断IP地址为内网IP还是公网IP （针对IPv地址） - CSDN - https://blog.csdn.net/weixin_42707543/article/details/126051328
 */

public class NetworkUtils {
    private final static Logger logger = LoggerFactory.getLogger(NetworkUtils.class);

    /**
     * 是否能够成功 PING 通网络
     * @description 前提远程服务器未开启对端口(7 ECHO)的防火墙，且启用了 ICMP 协议
     *  远程主机的端口 7 ECHO : 影响 isReachable
     * @reference-doc
     *  [1] java ping ip java ping ip三种方法 - 51cto - https://blog.51cto.com/u_16099267/7019367
     * @param hostname
     * @param timeout 超时时长，单位：毫秒 | default = 3000
     * @return
     * @throws Exception
     */
    public static boolean pingTest(String hostname, Integer timeout) throws Exception {
        long startTime = System.currentTimeMillis();
        timeout = ObjectUtils.isEmpty(timeout) ? 3000 : timeout;
        InetAddress inetAddress = InetAddress.getByName(hostname);
        boolean status = inetAddress.isReachable(timeout); //若返回 true ，说明 host 可用；反之， host 不可用
        long endTime = System.currentTimeMillis();
        return status;
    }

    /**
     * 是否能够成功 PING 通网络 (通过本地执行 Command(CMD/SHELL) 命令)
     * @description 前提远程服务器未开启对端口(7 ECHO)的防火墙，且启用了 ICMP 协议
     *  远程主机的端口 7 ECHO : 影响 isReachable
     * @reference-doc
     *  [1] java ping ip java ping ip三种方法 - 51cto - https://blog.51cto.com/u_16099267/7019367
     * @param hostname
     * @return
     * @throws Exception
     */
    public static String pingByCommand(String hostname){
        StringBuilder response = new StringBuilder();
        String line = null;
        try {
            Process process = Runtime.getRuntime().exec("ping " + hostname);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                  process.getInputStream() , "GBK"
            ) );
            logger.debug("The response content of Ping the hostname({}) by cmd : ", hostname);
            while ( (line = bufferedReader.readLine()) != null ) {
                response.append(line);
                logger.debug(line);
            }
        } catch (Exception exception) {
            logger.error("Fail to ping by cmd! hostname: {}, exception : {}", hostname, exception);
        }
        return response.toString();
    }

    /**
     * 检测API是否能连通
     * 只能通过状态码是否为404判断，如果网络不通则会连接失败到 catch 中返回false
     * @reference-doc
     *  [1] Java检测网络是否连通检查ip、URL和API接口 - CSDN - https://blog.csdn.net/m0_46085118/article/details/130840882
     * @param url
     * @param httpMethod 例如 : "GET" / "POST" / ...
     * @return
     */
    public static boolean urlTest(String url, String httpMethod) {
        try {
            URL urlObject = new java.net.URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0");
            connection.setRequestMethod(httpMethod.toUpperCase());//"GET" / "POST" / ...
            // 设置单次请求是否支持重定向，默认为 setFollowRedirects 方法设置的值
            connection.setInstanceFollowRedirects(false);//是否支持重定向
            connection.setConnectTimeout(1000);
            connection.connect();
            logger.debug("url : {} , response code : {}", url, connection.getResponseCode());
            if (connection.getResponseCode() != 404) {//判断状态码
                return true;
            } else {
                return false;
            }
        } catch (IOException exception) {
            logger.warn("Fail to check the url cause that the `IOException`! url : {}, exception : {}", url, exception);
            return false;
        }
    }

    /**
     * 测试ip及端口连通性
     * @param host
     * @param port [1, 65535]
     * @param timeout ms
     * @return boolean
     */
    public static boolean hostAndPortTest(String host, int port, int timeout) {
        Socket socket = new Socket();
        boolean status = false;
        try {
            socket.connect(new InetSocketAddress(host, port), timeout);
            if(socket.isConnected()){
                logger.debug("Success to test and access the network resource! <host:port> = {}:{}, isConnected : {}", host, port, socket.isConnected());//ip及端口访问正常
                status = true;
            } else {
                logger.warn("Fail to test and access the network resource!<host:port> = {}:{}, isConnected : {}", host, port, socket.isConnected());//无法访问
            }
        } catch (IOException exception) {
            logger.warn("Fail to test and access the network resource!<host:port> = {}:{}, io exception : {}", host, port, exception);//无法访问
        } finally {
            try {
                socket.close();
            } catch (IOException exception) {
                logger.warn("Fail to close the socket when test and access the network resource!<host:port> = {}:{}, io exception : {}", host, port, exception);//关闭socket异常
            }
        }
        return status;
    }

    /**
     * 是否为内网IP
     * @description
     * 在tcp/ip协议中，专门保留了三个IP地址区域作为私有地址，其地址范围如下：
     *   10.0.0.0/8：10.0.0.0～10.255.255.255
     *   172.16.0.0/12：172.16.0.0～172.31.255.255
     *   192.168.0.0/16：192.168.0.0～192.168.255.255
     * @param ip
     * @return
     */
    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    private static boolean internalIp(byte[] addr) {
        if (StringUtils.isEmpty(addr) || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0)
        {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L)){
                        return null;
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L)) {
                        return null;
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return null;
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }
}