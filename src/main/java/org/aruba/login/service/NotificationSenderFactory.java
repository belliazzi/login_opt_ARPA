package org.aruba.login.service;

import org.aruba.login.notification.NotificationSender;
import org.springframework.stereotype.Component;

@Component
public interface NotificationSenderFactory
{
    NotificationSender createNotificationSender(String type);
}
