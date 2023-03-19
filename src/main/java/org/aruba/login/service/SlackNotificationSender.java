package org.aruba.login.service;

import org.aruba.login.notification.Notifica;
import org.aruba.login.notification.NotificationSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

@Service
public class SlackNotificationSender implements NotificationSender {

    @Value("${slack.token}")
    private String slackToken;

    @Override
    public void sendNotification(final Notifica notifica) throws Exception {
        Slack slack = Slack.getInstance();
        MethodsClient methods = slack.methods(slackToken);
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(notifica.getDestinatario())
                .text(notifica.getTesto())
                .build();
        ChatPostMessageResponse response = methods.chatPostMessage(request);
        if (!response.isOk()) {
            throw new Exception(response.getError());
        }
    }
}
