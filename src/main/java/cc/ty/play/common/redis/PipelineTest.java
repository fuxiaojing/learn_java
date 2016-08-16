package cc.ty.play.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.io.IOException;

/**
 * date: 2016/7/20 15:52.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class PipelineTest {
    public static void main(String[] args) throws IOException {
        JedisPool jedisPool = new JedisUtil().get();
        Jedis jedis = jedisPool.getResource();
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("foo", "bar");
        System.out.println(pipeline.syncAndReturnAll());
        pipeline.close();
    }
}
