package cc.ty.play.common.map;

import java.util.HashMap;
import java.util.Map;

/**
 * date: 2016/7/25 10:03.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class TestMap {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("ip", "127.0.0.1");
        map.put("time", "210008150912");
        System.out.println(map.values());
    }
}
