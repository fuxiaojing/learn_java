package cc.ty.play.common.jetty;

import javax.ws.rs.core.UriBuilder;

import java.net.URI;

import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

/**
 * date: 2016/7/17 17:05.
 *
 * @author taoyang (ty.ice@me.com)
 */
public class Server {

    public static void main(String[] args) {
        URI baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(9099).build();
        org.eclipse.jetty.server.Server httpServer = JettyHttpContainerFactory.createServer(baseUri, new Application());
        try {
            httpServer.start();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
