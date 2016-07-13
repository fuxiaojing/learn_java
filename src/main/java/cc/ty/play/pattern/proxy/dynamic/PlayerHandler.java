package cc.ty.play.pattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * date: 2016/7/12 14:21.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class PlayerHandler implements InvocationHandler {

    Class clazz = null;

    Object obj = null;

    public PlayerHandler (Object _obj) {
        this.obj = _obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(this.obj, args);
        return result;
    }
}
