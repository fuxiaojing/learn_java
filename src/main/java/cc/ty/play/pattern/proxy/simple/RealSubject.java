package cc.ty.play.pattern.proxy.simple;

/**
 * date: 2016/7/12 13:50.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class RealSubject implements Subject {

    @Override
    public void request() {
        System.out.println("real worker handle the request");
    }
}
