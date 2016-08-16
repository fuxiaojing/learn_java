package cc.ty.play.common.jetty.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * date: 2016/7/17 17:03.
 *
 * @author taoyang (ty.ice@me.com)
 */
@Path("/")
public class HttpCheck {
    @GET
    @Path("heartBeat")
    public String shakeHands() {
        return "hello world!";
    }
}
