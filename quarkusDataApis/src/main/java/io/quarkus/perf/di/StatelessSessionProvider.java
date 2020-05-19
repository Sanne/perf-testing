package io.quarkus.perf.di;

import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

public class StatelessSessionProvider {

    @Inject
    EntityManagerFactory emf;

    @Produces
    StatelessSession getStatelessSession(){
        return emf.unwrap(SessionFactory.class).openStatelessSession();
    }
}
