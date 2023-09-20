package com.woowahanbank.backend.global.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationUtil {
    private static String domainPrefix;

    @Autowired
    public NotificationUtil(@Value("${util.domain.prefix}") String domainPrefix) {
        this.domainPrefix = domainPrefix;
    }

    public static String clickUrl(String url) {
        return domainPrefix + url;
    }
}
