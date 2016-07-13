package cc.ty.play.pattern.proxy.general;

/**
 * date: 2016/7/12 14:02.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class GamePlayer implements IGamePlayer {

    private String name = "";

    public GamePlayer(String name) {
        this.name = name;
    }

    public GamePlayer (IGamePlayer player, String name) {
        if(player == null) {
            throw new NullPointerException("player is not exist");
        } else {
            this.name = name;
        }
    }

    @Override
    public void login(String name, String password) {
        System.out.println(this.name + " 登录名：" + name + " 上线啦 ！");
    }

    @Override
    public void kill() {
        System.out.println(this.name + " 在打怪");
    }

    @Override
    public void update() {
        System.out.println(this.name + " 升级啦");
    }
}
