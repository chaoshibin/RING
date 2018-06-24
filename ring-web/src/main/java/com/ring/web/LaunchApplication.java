package com.ring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author chaoshibin
 */
@ImportResource({"classpath:base.xml"})
@ComponentScan(basePackages = {"com.ring.web","com.ring.core","com.ring.service"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LaunchApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaunchApplication.class, args);
        System.out.println("" +
                "/***\n" +
                " *                    .::::.\n" +
                " *                  .::::::::.\n" +
                " *                 :::::::::::\n" +
                " *             ..:::::::::::'\n" +
                " *           '::::::::::::'\n" +
                " *             .::::::::::\n" +
                " *        '::::::::::::::..\n" +
                " *             ..::::::::::::.\n" +
                " *           ``::::::::::::::::\n" +
                " *            ::::``:::::::::'        .:::.\n" +
                " *           ::::'   ':::::'       .::::::::.\n" +
                " *         .::::'      ::::     .:::::::'::::.\n" +
                " *        .:::'       :::::  .:::::::::' ':::::.\n" +
                " *       .::'        :::::.:::::::::'      ':::::.\n" +
                " *      .::'         ::::::::::::::'         ``::::.\n" +
                " *  ...:::           ::::::::::::'              ``::.\n" +
                " * ```` ':.          ':::::::::'                  ::::..\n" +
                " *                    '.:::::'                    ':'````..\n" +
                " */" +
                "");
    }
}
