package cc.ty.play.pattern.proxy.simple;

/**
 * date: 2016/7/12 13:51.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class Proxy implements Subject {

    private Subject subject = null;

    public Proxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void request() {
        this.before();
        this.subject.request();
        this.after();
    }

    private void before() {
        System.out.println("proxy before...");
    }

    private void after() {
        System.out.println("proxy after...");
    }
}
