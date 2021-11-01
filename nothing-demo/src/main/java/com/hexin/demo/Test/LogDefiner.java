package com.hexin.demo.Test;

import ch.qos.logback.core.PropertyDefinerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * @Author hex1n
 * @Date 2021/11/1 13:25
 * @Description
 */
public class LogDefiner extends PropertyDefinerBase {

    private static final Logger LOG = LoggerFactory.getLogger(LogDefiner.class);

    private String getUniqName() {
        String localIp = null;
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG.error("fail to get ip...", e);
        }
        String uniqName = UUID.randomUUID().toString().replace("-", "");
        if (localIp != null) {
            uniqName = localIp + "-" + uniqName;
        }
        return uniqName;
    }

    @Override
    public String getPropertyValue() {
        return getUniqName();
    }
}
