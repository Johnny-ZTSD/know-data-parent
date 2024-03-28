package com.knowdata.framework.study.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.Test;

import java.util.Properties;

/**
 * nacos 配置中心测试
 * @description NACOS Server 端 : 2.0.3 / NACOS 客户端 : 1.4.3
 * @reference-doc
 *  [1] NACOS 用户指南(JAVA SDK) - nacos - https://nacos.io/zh-cn/docs/sdk.html
 *  [2] https://mvnrepository.com/artifact/com.alibaba.nacos/nacos-client
 */
public class NacosConfigTest {
    /** NACOS 服务器地址 **/
    private final static String SERVER_ADDR = "http://xxxx.com.cn:18848/nacos"; // 形如 : 127.0.0.1:8848 或 http://127.0.0.1:8848

    private final static String USERNAME = "xxxx";//nacos
    private final static String PASSWORD = "xxxx";//nacos
    private final static String NAMESPACE = "public";//public
    private final static String GROUP = "test";//DEFAULT_GROUP
    private final static String DATA_ID = "nacos.cfg.dataId";//demoy.yml

    /**
     * NACOS 服务器端启用强制客户端输入密码，但客户端不提供密码
     * @sample [shell] curl -X GET 'http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.example&group=com.alibaba.nacos'
     * @descrption
     * [1] nacos server 2.0.3 : 执行成功
     * [2] nacos server 2.3.1 : 执行失败，ErrCode:403, ErrMsg:<html><body><h1>Whitelabel Error Page</h1><p>This application has no explicit mapping for /error, so you are seeing this as a fallback
     */
    @Test
    public void nacosServerEnablePasswordButClientNotUsePasswordTest(){
        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, SERVER_ADDR);
            properties.put(PropertyKeyConst.NAMESPACE, NAMESPACE);
            //properties.put(PropertyKeyConst.USERNAME, USERNAME);
            //properties.put(PropertyKeyConst.PASSWORD, PASSWORD);
            ConfigService configService = NacosFactory.createConfigService(properties);
            String content = configService.getConfig(DATA_ID, GROUP, 5000);
            System.out.println("content :" + content);
        } catch (NacosException e) {//读取配置超时或网络异常，抛出 NacosException 异常
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * NACOS 服务器端不启用强制客户端输入密码，且客户端也提供密码
     * @description
     * [1] nacos server 2.0.3 : 密码正确时，执行成功 ; 密码错误时， 报 403
     * [1] nacos server 2.3.1 : 密码正确时，执行成功 ; 密码错误时， 报 403
     */
    @Test
    public void nacosServerDisablePasswordAndClientUsePasswordTest(){
        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, SERVER_ADDR);
            properties.put(PropertyKeyConst.NAMESPACE, NAMESPACE);
            properties.put(PropertyKeyConst.USERNAME, USERNAME);
            properties.put(PropertyKeyConst.PASSWORD, PASSWORD);
            ConfigService configService = NacosFactory.createConfigService(properties);
            String content = configService.getConfig(DATA_ID, GROUP, 5000);
            System.out.println(content);
        } catch (NacosException e) {//读取配置超时或网络异常，抛出 NacosException 异常
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * NACOS 服务器端不启用强制客户端输入密码，但客户端不提供密码
     * @sample [shell] curl -X GET 'http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.example&group=com.alibaba.nacos'
     * @description
     * [1] nacos server 2.0.3 : 请求成功
     * [2] nacos server 2.3.1 : 请求成功
     */
    @Test
    public void nacosServerDisablePasswordButClientNotUsePasswordTest(){
        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, SERVER_ADDR);
            properties.put(PropertyKeyConst.NAMESPACE, NAMESPACE);
            //properties.put(PropertyKeyConst.USERNAME, USERNAME);
            //properties.put(PropertyKeyConst.PASSWORD, PASSWORD);
            ConfigService configService = NacosFactory.createConfigService(properties);
            String content = configService.getConfig(DATA_ID, GROUP, 5000);
            System.out.println(content);
        } catch (NacosException e) {//读取配置超时或网络异常，抛出 NacosException 异常
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * NACOS 服务器端启用强制客户端输入密码，且客户端也提供密码
     * @description 执行成功
     */
    @Test
    public void nacosServerEnablePasswordAndClientUsePasswordTest(){
        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, SERVER_ADDR);
            properties.put(PropertyKeyConst.NAMESPACE, NAMESPACE);

            properties.put(PropertyKeyConst.USERNAME, USERNAME);
            properties.put(PropertyKeyConst.PASSWORD, PASSWORD);
            //properties.put(PropertyKeyConst.ACCESS_KEY, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYWNvcyIsImV4cCI6MTcxMTYzNzE3NX0.ul940V4eB3aQwsQLeGQqgbnkrL0n45bkeSYBfGvYM2w");
            //properties.put(PropertyKeyConst.SECRET_KEY, "SecretKey012345678901234567890123456789012345678901234567890123456789");

            ConfigService configService = NacosFactory.createConfigService(properties);

            System.out.println("nacos-server-status: " + configService.getServerStatus() );//UP

            String content = configService.getConfig(DATA_ID, GROUP, 5000);
            if (content == null) {
                // 处理null情况，可能是配置不存在、网络问题、权限问题等
                System.out.println("获取到的配置 : null，可能原因：配置不存在、网络问题、权限问题等");
            } else {
                System.out.println("获取到的配置 : " + content);
            }

            Boolean removeConfigResult = configService.removeConfig(DATA_ID, GROUP);
            System.out.println("removeConfigResult : " + removeConfigResult);//true / false

        } catch (NacosException e) {//读取配置超时或网络异常，抛出 NacosException 异常
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}