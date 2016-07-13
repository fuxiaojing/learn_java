package cc.ty.play.pattern.proxy.simple;

/**
 * date: 2016/7/12 13:56.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class Client {
    public static void main(String[] args) {
        Subject proxy = new Proxy(new RealSubject());
        proxy.request();
    }
}
