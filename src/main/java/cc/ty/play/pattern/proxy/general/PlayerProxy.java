package cc.ty.play.pattern.proxy.general;

/**
 * date: 2016/7/12 14:09.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class PlayerProxy implements IGamePlayer {

    private IGamePlayer player;

    public PlayerProxy (String _name) {
        try {
            this.player = new GamePlayer(this, _name);
        } catch (Exception e) {

        }
    }

    @Override
    public void login(String name, String passeord) {
        player.login(name, passeord);
    }

    @Override
    public void kill() {
        player.kill();
    }

    @Override
    public void update() {
        player.update();
    }
}
