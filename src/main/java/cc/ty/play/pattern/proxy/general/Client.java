package cc.ty.play.pattern.proxy.general;

/**
 * date: 2016/7/12 13:56.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class Client {
    public static void main(String[] args) {
        IGamePlayer player = new PlayerProxy("xiaoming");
        player.login("xiaoming", "123");
        player.kill();
        player.update();
    }
}
