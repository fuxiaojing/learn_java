/**
 * Copyright (c) 2015, TP-Link Co.,Ltd.
 * Author:  taoyang <taoyang@tp-link.net>
 * Created: 2015-12-2
 */
package cc.ty.play.common.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A utility class that provides various common operations
 * related with {@link JedisCluster}
 */
public class JedisClusterUtil {

    private JedisCluster jedis;

    /**
     * Constructor which sets connection with redis
     * and provides the instance of {@link JedisCluster}
     */
    public JedisClusterUtil() {
        String[] addrs = formatAddr("172.31.1.154:6380,172.31.1.154:6381,172.31.1.154:6382");
        if ( addrs.length < 2) {
            throw new IllegalArgumentException(
                    "host should not be null" + " and at least 2 host:port should be provided");
        }
        Set<HostAndPort> hps = new HashSet<HostAndPort>();
        HostAndPort hp = null;
        String[] address = null;
        for (String addr : addrs) {
            try {
                address = addr.split(":");
                hp = new HostAndPort(address[0], Integer.valueOf(address[1]));
                hps.add(hp);
            } catch (Exception e) {
                throw new IllegalArgumentException("Illegal host and port, " + addr);
            }
        }
        jedis = new JedisCluster(hps);
    }

    public JedisCluster instance() {
        return jedis;
    }

    public String set(String key, String value) {
        return jedis.set(key, value);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public boolean exists(String key) {
        return jedis.exists(key);
    }

    public boolean delete(String key) {
        boolean result = false;
        if (jedis.del(key) > 0) {
            result = true;
        }
        return result;
    }

    public String getSet(String key, String value) {
        return jedis.getSet(key, value);
    }

    public boolean hmset(final String key, final Map<String, String> map) {
        boolean result = false;
        if (jedis.hmset(key, map).equals("OK")) {
            result = true;
        }
        return result;
    }

    public Long hset(final String key, final String field, final String value) {
        return jedis.hset(key, field, value);
    }

    public List<String> hmget(final String key, final String... fields) {
        return jedis.hmget(key, fields);
    }

    public String hget(final String key, final String field) {
        return jedis.hget(key, field);
    }

    public List<String> hvals(final String key) {
        return jedis.hvals(key);
    }

    public Map<String, String> hgetAll(final String key) {
        return jedis.hgetAll(key);
    }

    public boolean hdel(final String key, final String... fields) {
        boolean result = false;
        if (jedis.hdel(key, fields) == 1) {
            result = true;
        }
        return result;
    }

    public boolean expire(final String key, final int seconds) {
        boolean result = false;
        if (jedis.expire(key, seconds) == 1) {
            result = true;
        }
        return result;
    }

    private String[] formatAddr(String addr) {
        return addr.split(",");
    }
}
