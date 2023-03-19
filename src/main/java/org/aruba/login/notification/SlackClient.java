package org.aruba.login.notification;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlackClient
{

    @Value("${slack.token}")
    private String slackToken;

    public void sendMessage(final String channel, final String message) throws Exception
    {
        Slack slack = Slack.getInstance();
        MethodsClient methods = slack.methods(slackToken);
        ChatPostMessageRequest request = ChatPostMessageRequest.builder().channel(channel).text(message).build();
        ChatPostMessageResponse response = methods.chatPostMessage(request);
        if (!response.isOk()) {
            throw new Exception(response.getError());
        }
    }
}
