package dev.example.entities.event_listeners;

import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

import java.util.List;

/**
 * TODO: Пока не удалось использовать данную реализацию
 * подключение в пропертях не работает
 * Подключать листенеры удалось пока только через формирование бина SessionFactory
 */
public class EventListenerIntegratorProvider implements IntegratorProvider {
    @Override
    public List<Integrator> getIntegrators() {
        return List.of(
                ReplicationEventListenerIntegrator.INSTANCE
        );
    }
}
