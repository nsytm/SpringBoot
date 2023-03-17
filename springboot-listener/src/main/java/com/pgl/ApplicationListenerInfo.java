package com.pgl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 * @author pgl
 * @ClassName ApplicationListenerInfo
 * @Description 使用spring提供的事件监听, 应用启动时打印访问路径
 * @date:2023/3/17
 */
@Slf4j
@Component
public class ApplicationListenerInfo implements ApplicationListener<ApplicationReadyEvent> {
    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String port = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path");

        log.info(
                "\n--------------------------------------------------------\n" +
                        "\tApplication is running! Access address:\n" +
                        "\tLocal:\t\thttp://localhost:{}{}" +
                        "\n--------------------------------------------------------\n",
                port, contextPath
        );
    }
}
