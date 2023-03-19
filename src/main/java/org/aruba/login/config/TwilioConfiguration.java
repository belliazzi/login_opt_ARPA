package org.aruba.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.twilio.http.TwilioRestClient;

@Configuration
public class TwilioConfiguration {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Bean
    public TwilioRestClient twilioRestClient() {
        return new TwilioRestClient.Builder(accountSid, authToken).build();
    }
}
