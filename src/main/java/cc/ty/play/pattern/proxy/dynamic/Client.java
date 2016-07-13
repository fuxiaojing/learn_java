package cc.ty.play.pattern.proxy.dynamic;

import cc.ty.play.pattern.proxy.general.GamePlayer;
import cc.ty.play.pattern.proxy.general.IGamePlayer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * date: 2016/7/12 13:56.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class Client {
    public static void main(String[] args) {
        IGamePlayer player = new GamePlayer("xiaoming");
        InvocationHandler handler = new PlayerHandler(player);
        ClassLoader cl = player.getClass().getClassLoader();
        IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(cl, new Class[]{IGamePlayer.class}, handler);
        proxy.login("zhangsan", "123");
        proxy.kill();
        proxy.update();
    }
}
