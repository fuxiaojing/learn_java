/**
 * Copyright (c) 2015, TP-Link Co.,Ltd.
 * Author:  chenbiren <chenbiren@tp-link.net>
 * Created: 2015-11-12
 */
package cc.ty.play.common.zk;

import cc.ty.play.common.util.NetUtils;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class URL {

    private final java.net.URL url;

    private InetSocketAddress socketAddress;

    private final Map<String, String> parameters;

    public URL(String spec) throws MalformedURLException {
        this(new java.net.URL(null, spec, new UrlHandler()));
    }

    public URL(String spec, Map<String, String> parameters) throws MalformedURLException {
        this(new java.net.URL(null, spec, new UrlHandler()), parameters);
    }

    public URL(String protocol, String host, int port, String file) throws MalformedURLException {
        this(new java.net.URL(protocol, host, port, file, new UrlHandler()));
    }

    private URL(java.net.URL url) {
        this(url, null);
    }

    private URL(java.net.URL url, Map<String, String> parameters) {
        if (url == null) {
            throw new NullPointerException("uri is null");
        }
        this.url = url;
        if (parameters == null) {
            parameters = new HashMap<>();
        } else {
            parameters = new HashMap<>(parameters);
        }
        parameters.putAll(readParameters(url));
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    private static Map<String, String> readParameters(java.net.URL url) {
        String query = url.getQuery();
        if (query == null || query.equals("")) {
            return new HashMap<>();
        }

        Map<String, String> parameters = new HashMap<>();
        for (String kv : query.split("&")) {
            String[] parts = kv.split("=");
            if (parts.length != 2) {
                continue;
            }
            parameters.put(parts[0], parts[1]);
        }
        return parameters;
    }

    public java.net.URL getUrl() {
        return url;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getParameter(String key, T defaultValue) {
        if (defaultValue == null) {
            throw new IllegalArgumentException("value is null");
        }
        Class<T> clazz = (Class<T>) defaultValue.getClass();
        return getParameter(key, defaultValue, clazz);
    }

    public <T> T getParameter(String key, T defaultValue, Class<T> type) {
        if (defaultValue == null) {
            throw new IllegalArgumentException("value is null");
        }
        String value = parameters.get(key);
        if (value == null) {
            return defaultValue;
        }

        Class<?> clazz = defaultValue.getClass();
        if (clazz == String.class) {
            return type.cast(value);
        }
        try {
            Method method = clazz.getMethod("valueOf", String.class);
            return type.cast(method.invoke(null, value));
        } catch (Exception e) {
            throw new RuntimeException("Failed to cast string to" + clazz, e);
        }
    }

    public InetSocketAddress toInetSocketAddress() {
        if (socketAddress == null) {
            socketAddress = new InetSocketAddress(url.getHost(), url.getPort());
        }
        return socketAddress;
    }

    public boolean isInvalidHost() {
        return NetUtils.isInvalidLocalHost(url.getHost());
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(url.getProtocol()).append("://");
        builder.append(url.getHost()).append(":");
        builder.append(url.getPort());
        String path = url.getPath();
        if (path != null && !"".equals(path)) {
            builder.append(url.getPath());
        }
        if (parameters.size() > 0) {
            builder.append("?");
            for (String key : parameters.keySet()) {
                builder.append(key).append("=").append(parameters.get(key)).append("&");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private static class UrlHandler extends URLStreamHandler {

        @Override
        protected URLConnection openConnection(java.net.URL u) {
            throw new UnsupportedOperationException();
        }

    }
}
