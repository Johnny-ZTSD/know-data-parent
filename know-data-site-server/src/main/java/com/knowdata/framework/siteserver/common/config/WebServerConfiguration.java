package com.knowdata.framework.siteserver.common.config;

import com.knowdata.framework.core.application.config.tomcat.TomcatEmbedServerProperties;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/4/3 17:44
 * @description 内嵌tomcat
 *  [1] 在 spring-configuration-metadata.json 文件中相关tomcat配置
 *      server.tomcat.accept-count:                               等待队列长度，默认100
 *      server.tomcat.max-connections:                            最大可连接数，默认10000
 *      server.tomcat.max-threads:                                最大工作线程数，默认200
 *      server.tomcat.min-spare-threads:                          最小工作线程数，默认10
 *      server.tomcat.accesslog.enabled=true　　                   开启access日志
 *      server.tomcat.accesslog.directory=/var/www//tomcat　　     日志存放的路径
 *      server.tomcat.accesslog.pattern=%h %l %u %t "%r" %s %b %D  日志格式为：请求的host主机地址，时间，方式，路径，协议，状态，返回字节数，处理时间
 *
 *      注意：默认配置下，连接数超过 10000 后出现拒接连接情况；触发的请求超过 200+100 后拒绝
 *   [2] 定制内嵌 tomcat 开发
 *      相关参数：
 *          keepAliveTimeOut :多少毫秒后不响应的断开keepalive
 *          maxKeepAliveRequests :多少次请求后keepalive断开失效
 *      keepAlive 简介：参考这里
 *          优点：
 *              Keep-Alive 功能使客户端到服务器端的连接持续有效，当出现对服务器的后继请求时，Keep-Alive功能避免了建立或者重新建立连接。
 *              长连接能够保证服务器和客户端的socket能够高效利用，减少握手等额外的开销。
 *          缺点：
 *              但是对于负担较重的网站来说，这里存在另外一个问题：
 *                  虽然为客户保留打开的连接有一定的好处，但它同样影响了性能，因为在处理暂停期间，本来可以释放的资源仍旧被占用。
 *                  当Web服务器和应用服务器在同一台机器上运行时，Keep-Alive功能对资源利用的影响尤其突出。
 * @reference-doc
 *  [1] springboot内嵌tomcat优化 - 博客园 - https://www.cnblogs.com/hjwucc/p/11425306.html
 *  [2] 我可以为 Spring Boot 的嵌入式 tomcat 启用 tomcat 管理器应用程序吗? - IT1352 - https://www.it1352.com/2405633.html
 *  [3] Tomcat卷三:Jasper引擎 - CSDN - https://blog.csdn.net/m0_53157173/article/details/123131713
 *  [4] Spring Boot 最佳实践（二）集成Jsp与生产环境部署 - 51CTO - https://blog.51cto.com/vipstone/5408719
 *  [5] 嵌入式 Tomcat (Embedded Tomcat) - 博客园 - https://www.cnblogs.com/develon/p/11602969.html
 *
 *  [6] 【Java基础】-- isAssignableFrom的用法详细解析 - 腾讯云 - https://cloud.tencent.com/developer/article/1754376
 *  [7] The HTTP Connector - Apache Tomcat(9) - https://tomcat.apache.org/tomcat-9.0-doc/config/http.html
 *  [8] HTTP/1.1与HTTP/1.0的区别 - CSDN - https://blog.csdn.net/qq_25827845/article/details/80127198
 */

//当 Spring容器中没有 TomcatEmbeddedServletContainerFactory 这个 bean 时，会把此bean加载进容器
@Configuration
public class WebServerConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(WebServerConfiguration.class);

    // 在某配置类中添加如下内容
    // 监听的http请求的端口,需要在application配置中添加http.port=端口号  如 80
//    @Value("${http.port}")
//    Integer httpPort;

    //正常启用的https端口 如 443
//    @Value("${server.port}")
//    Integer httpsPort;

    @Autowired
    Environment environment;

    @Autowired
    ServerProperties serverProperties;

    @Autowired
    TomcatEmbedServerProperties tomcatServerProperties;

//    @Bean
//    public TomcatWebServerFactoryCustomizer tomcatWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
//        return new TomcatWebServerFactoryCustomizer(environment, serverProperties);
//    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer() {
            @Override
            public void customize(WebServerFactory factory) {
                //使用工厂类定制 tomcat connector
                TomcatServletWebServerFactory webServerFactory =  ((TomcatServletWebServerFactory)factory);
                //webServerFactory.addContextCustomizers((context -> {
                //    context.addWelcomeFile("/index.html");
                //    // 使用 Tomcat 的 LegacyCookieProcessor 处理器
                //    context.setCookieProcessor(new LegacyCookieProcessor())
                //}));
                //TomcatServletWebServerFactoryCustomizer tomcatServletWebServerFactoryCustomizer = new TomcatServletWebServerFactoryCustomizer(serverProperties);
                //tomcatServletWebServerFactoryCustomizer.customize(webServerFactory);
                //int order = tomcatServletWebServerFactoryCustomizer.getOrder();

                TomcatConnectorCustomizer tomcatConnectorCustomizer = new TomcatConnectorCustomizer() {
                    @Override
                    public void customize(Connector connector) {
                        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
                        PropertyMapper propertyMapper = PropertyMapper.get();

                        //protocol.setPort(tomcatServerProperties.getPort());
                        propertyMapper.from(tomcatServerProperties::getPort).whenNonNull().to(protocol::setPort);

                        /** Tomcat 9.0.46 默认配置 **/
                        /**
                         * @param 最小备用线程数
                         * @defaultValue 10
                         * tomcat启动时的初始化的线程数
                         */
                        //protocol.setMinSpareThreads(10);
                        propertyMapper.from(tomcatServerProperties::getMinSpareThreads).whenNonNull().to(protocol::setMinSpareThreads);
                        /**
                         * @param 最大线程数
                         * @defaultValue 200
                         * Tomcat可创建的最大的线程数，每一个线程处理一个请求
                         * 超过这个请求数后，客户端请求只能排队，等有线程释放才能处理
                         * 可以在服务器 CPU 核心数的 200~250 倍之间
                         */
                        //protocol.setMaxThreads(200);
                        propertyMapper.from(tomcatServerProperties::getMaxThreads).whenNonNull().to(protocol::setMaxThreads);
                        /**
                         * @param 请求等待队列的容量大小
                         * @defaultValue 100
                         * 当tomcat请求处理线程池中的所有线程都处于忙碌状态时，此时新建的链接将会被放入到pending队列
                         * acceptCount即是此队列的容量，如果队列已满，此后所有的建立链接的请求(accept)，都将被拒绝
                         * 在高并发/短链接较多的环境中，可以适当增大此值；当长链接较多的场景中，可以将此值设置为0
                         */
                        //protocol.setAcceptCount(100);
                        propertyMapper.from(tomcatServerProperties::getAcceptCount).whenNonNull().to(protocol::setAcceptCount);
                        /**
                         * @param 在同一时间，tomcat能够接受的最大连接数
                         * @defaultValue 8192
                         * 当达到`max-connections `临界值时,系统可能会基于accept-count继续接受连接
                         * tomcat允许接收和处理的最大链接数，对于BIO而言此值默认与maxThreads参数一样，对于NIO而言此值默认为10000
                         * 此值还受限于系统的 ulimit、CPU、内存等配置。
                         */
                        // protocol.setMaxConnections(8192);
                        propertyMapper.from(tomcatServerProperties::getMaxConnections).whenNonNull().to(protocol::setMaxConnections);
                        /**
                         * @param 处于keepAlive状态的请求的个数
                         * @defaultValue 100
                         * -1 表示不限制，1表示关闭 keepAlive 机制
                         * 建议: 此值为 maxThreads * 0.5; 不得大于 maxThreads
                         */
                        //protocol.setMaxKeepAliveRequests(100);
                        propertyMapper.from(tomcatServerProperties::getMaxKeepAliveRequests).whenNonNull().to(protocol::setMaxKeepAliveRequests);
                        /**
                         * @param Tomcat 在关闭连接(Connection)之前，等待另一个请求的时间 （HTTP 1.1 KeepAlive 持久连接）
                         * @defaultValue 60000
                         * 此值控制/影响: HTTP响应报文中的 2个Header
                         *     Connection: Keep-Alive
                         *     keep-alive: timeout={KeepAliveTimeout/1000}s
                         */
                        //protocol.setKeepAliveTimeout(60000);
                        propertyMapper.from(tomcatServerProperties::getKeepAliveTimeout).whenNonNull().to(protocol::setKeepAliveTimeout);
                        /**
                         * @param 与客户端建立连接后, Tomcat 等待客户端请求的时间
                         * @defaultValue 60000
                         * 如果客户端没有请求进来，等待一段时间后断开连接，释放线程
                         */
                        //protocol.setConnectionTimeout(60000);
                        propertyMapper.from(tomcatServerProperties::getConnectionTimeout).whenNonNull().to(protocol::setConnectionTimeout);
                    }
                };

                webServerFactory.addConnectorCustomizers(tomcatConnectorCustomizer);
                webServerFactory.addContextCustomizers();
            }
        };
    }

    // springboot2 写法
//    @Bean
//    public TomcatServletWebServerFactory tomcatServletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        tomcat.addConnectorCustomizers();
//        return tomcat;
//    }

    // springboot 1.x

    /**
     * @reference-doc
     *  [1] Spring Boot如何控制Tomcat缓存？ - codenong - https://www.codenong.com/39146476/
     *      EmbeddedServletContainerCustomizer / TomcatEmbeddedServletContainerFactory / ConfigurableEmbeddedServletContainer
     * @return
     */
/*    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                tomcat.addUser("admin", "secret");
                tomcat.addRole("admin", "manager-gui");

                try {
                    tomcat.addWebapp("/manager", "/path/to/manager/app");
                }
                catch (ServletException ex) {
                    throw new IllegalStateException("Failed to add manager app", ex);
                }
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }
        };
    }*/



//    public Connector httpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        //Connector监听的http的端口号
//        connector.setPort(httpPort);
//        connector.setSecure(false);
//        //监听到http的端口号后转向到的https的端口号
//        connector.setRedirectPort(httpsPort);
//        return connector;
//    }

//    @Configuration(
//            proxyBeanMethods = false
//    )
//    @ConditionalOnClass({Tomcat.class, UpgradeProtocol.class})
//    public static class TomcatWebServerFactoryCustomizerConfiguration {
//        public TomcatWebServerFactoryCustomizerConfiguration() {
//        }
//
//        @Bean
//        public TomcatWebServerFactoryCustomizer tomcatWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
//            return new TomcatWebServerFactoryCustomizer(environment, serverProperties);
//        }
//    }
}


