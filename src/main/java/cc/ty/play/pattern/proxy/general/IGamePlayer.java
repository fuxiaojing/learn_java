package cc.ty.play.pattern.proxy.general;

/**
 * date: 2016/7/12 14:00.
 *
 * @author taoyang (ty.ice@me.com)
 */
public interface IGamePlayer {
    void login(String name, String passeord);
    void kill();
    void update();
}
