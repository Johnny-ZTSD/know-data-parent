package com.knowdata.framework.siteserver.app;

import com.knowdata.framework.core.util.log.debug.Print;
import com.knowdata.framework.core.util.spring.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/4/3 13:44
 * @description 应用程序启动钩子
 *  除了通常的Spring Framework事件之外，例如 ContextRefreshedEvent，SpringApplication发送一些额外的应用程序事件。
 *      某些事件实际上是在 ApplicationContext 创建之前触发的，因此您无法在这些事件上注册侦听器@Bean。（bean是在applicationContext创建之后才实例化的）
 *      您可以使用 SpringApplication.addListeners( ... ) 方法或 SpringApplicationBuilder.listeners( ... ) 方法注册它们 。
 *
 *      如果您希望自动注册这些侦听器，无论应用程序的创建方式如何，可以向项目添加文件 META-INF/spring.factories ,
 *      并使用该 org.springframework.context.ApplicationListener键配置侦听器 ，如以下示例所示：
 *          org.springframework.context.ApplicationListener = com.example.project.MyListener
 *  应用程序运行时，应按以下顺序发送应用程序事件：
 *      一个 ApplicationStartingEvent 是在一个运行的开始，但任何处理之前被发送，除了listeners 和initializers注册者。
 *      一个 ApplicationEnvironmentPreparedEvent / Environment 已知，但是在创建上下文之前。
 *      一个 ApplicationPreparedEvent 是在刷新开始前，但bean定义已经被加载之后。
 *          ContextRefreshedEvent 的调用点是 refreshContext(context) (之前)，可能被调用多次
 *      一个 ApplicationStartedEvent 上下文已被刷新后发送，但是在调用command-line runners 之前。
 *      一个 ApplicationReadyEvent / CommandLineRunner (command-line runners) 已被调用。它表示应用程序已准备好为请求提供服务。
 *          ApplicationReadyEvent 的调用点是 listeners.running(context)，仅在程序启动过程中调用一次
 *      一个 ApplicationFailedEvent 如果在启动时异常发送。
 * @refeence-doc
 *  [1] springboot启动事件监听器退出钩子  - 博客园 - https://www.cnblogs.com/gc65/p/10624035.html
 *  [2] SpringBoot中利用Environment给静态变量赋值配置文件中的常量 - CSDN - https://blog.csdn.net/lisijing8201/article/details/118364910
 *  [3] springboot启动时多次监听到ApplicationReadyEvent事件 - CSDN - https://blog.csdn.net/weixin_43378325/article/details/118277450
 *  [4] SpringApplication的启动过程 - CSDN - https://blog.csdn.net/qq_44885775/article/details/124397540
 */
@Component
public class ApplicationStartupHook implements ApplicationListener<ApplicationReadyEvent>, EnvironmentAware {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationStartupHook.class);

    private Environment environment;

    /** 在启动时显示变量值的变量名 **/
    @Value("${service-config.debug.variable-name-for-show-variable-info-when-startup:NACOS_SERVER_ADDR}")
    private String variableNameForShowVariableInfoWhenStartup;

    /**
     * 在 应用程序已准备好后进行处理
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContextUtils.setApplicationContext(event.getApplicationContext());
        logger.info("application is ready now!");

        try{
            ConfigurableListableBeanFactory beanFactory = ((AnnotationConfigServletWebServerApplicationContext) ApplicationContextUtils.getApplicationContext()).getBeanFactory();
            logger.info("spring bean definition names:");
            Print.print(beanFactory.getBeanDefinitionNames());
        }catch (Exception e) {
            logger.warn("exception : {}", e);
        }

        Environment env = ApplicationContextUtils.getBean(Environment.class);

        //读取系统环境变量
        String variableName = variableNameForShowVariableInfoWhenStartup;
        String variableValue = env.getProperty(variableName); // X
        logger.info("env | {} : {}", variableName, variableValue);
        variableValue = System.getProperty(variableName); // √
        logger.info("sys | {} : {}", variableName, variableValue);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
