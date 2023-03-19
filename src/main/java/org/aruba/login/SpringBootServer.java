package org.aruba.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.slack.api.Slack;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.aruba.login"})
public class SpringBootServer
{

    public static void main(final String[] args)
    {
        SpringApplication.run(SpringBootServer.class, args);
    }

    // per i file
    @Bean
    public MultipartResolver multipartResolver()
    {
        return new StandardServletMultipartResolver();
    }

    // per slack
    @Bean
    public Slack slack()
    {
        return Slack.getInstance();
    }

    @Bean
    @ApplicationScope
    public OKMWebservices openkmClient(@Value("${openkm.host}") final String host,
        @Value("${openkm.user.name}") final String user, @Value("${openkm.user.password}") final String password)
    {
        return OKMWebservicesFactory.newInstance(host, user, password);
    }

}
