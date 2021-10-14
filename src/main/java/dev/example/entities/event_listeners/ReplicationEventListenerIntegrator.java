package dev.example.entities.event_listeners;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

@Component
public class ReplicationEventListenerIntegrator implements Integrator {

    // singleton !!!
    public static final ReplicationEventListenerIntegrator INSTANCE = new ReplicationEventListenerIntegrator();

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

        // берем регистратор списка ивентов и слушателей
        final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

        // регистрируем кастомного слушателя
        eventListenerRegistry.appendListeners(
                EventType.POST_INSERT,
                ReplicationInsertEventListener.INSTANCE
        );
    }

    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        //
    }
}
