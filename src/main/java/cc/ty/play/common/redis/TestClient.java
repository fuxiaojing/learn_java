package cc.ty.play.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * date: 2016/7/15 15:03.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class TestClient {

    private static final int TEST_NUM = 250000;


    public static void main(String[] args) {
        JedisPool jedisPool = new JedisUtil().get();

        Jedis jedis = jedisPool.getResource();
        Pipeline pipeline = jedis.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < TEST_NUM; i++) {
            //pipeline.set("device:" + i + ":connector_ip", "172.31.1.1");
            if(i< 23) {
                pipeline.set("device:" + i + ":connector_ip", "172.31.1.1");
            } else if (i < 100){
                pipeline.set("device:" + i + ":connector_ip", "172.31.1.2");
            } else {
                pipeline.set("device:" + i + ":connector_ip", "172.31.1.3");
            }
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined SET: " + ((end - start)/1000.0) + " seconds");

//        start = System.currentTimeMillis();
//        for (int i = 0; i < TEST_NUM; i++) {
//            pipeline.set("test.key.num:" + i, "test" + i);
//        }
//        List<Object> results2 = pipeline.syncAndReturnAll();
//        end = System.currentTimeMillis();
//        System.out.println("Pipelined del: " + ((end - start)/1000.0) + " seconds");

//
//        long start2 = System.currentTimeMillis();
//
//        for (int i = 0; i < TEST_NUM; i++) {
//            Jedis jedis2 = jedisPool.getResource();
//            jedis2.set("device:" + i + ":connector_ip", "172.31.1.1");
//            jedis2.close();
//        }
//        long end2 = System.currentTimeMillis();
//        System.out.println("Jedis del: " + ((end2 - start2)/1000.0) + " seconds");
    }
}
