package ec.edu.monster.ws;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        java.util.Set<Class<?>> resources =
                new java.util.HashSet<>();

        resources.add(
                ec.edu.monster.ws.CoreBancarioResource.class
        );

        return resources;
    }
}