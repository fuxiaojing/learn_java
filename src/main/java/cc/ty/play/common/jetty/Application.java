package cc.ty.play.common.jetty;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * date: 2016/7/17 16:56.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class Application extends ResourceConfig {
    public Application() {
        packages("cc.ty.play.common.jetty.resources");
        register(JacksonJsonProvider.class);
        register(JacksonFeature.class);
    }
}
