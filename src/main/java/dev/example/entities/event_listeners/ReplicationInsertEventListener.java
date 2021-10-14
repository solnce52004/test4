package dev.example.entities.event_listeners;

import dev.example.entities.User;
import org.hibernate.FlushMode;
import org.hibernate.event.spi.EventSource;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;


public class ReplicationInsertEventListener implements PostInsertEventListener {

    // singleton !!!
    public static final ReplicationInsertEventListener INSTANCE = new ReplicationInsertEventListener();

    @Override
    public void onPostInsert(PostInsertEvent event) {
        final Object entity = event.getEntity();

        if (entity instanceof User) {
            User user = (User) entity;

            final EventSource session = event.getSession();
            // слушаем событие в рамках уже запущенной транзакции в текущей сессии
//            session.beginTransaction();

            session.createNativeQuery(
                    "insert into users_copy (id, username, address_id, is_actual, created_at, updated_at) values (:id, :username, :address_id, :is_actual, :created_at, :updated_at)"
            )
                    .setParameter("id", user.getId())
                    .setParameter("username", user.getUsername())
                    .setParameter("address_id", user.getAddresses().getId())
                    // тк слушаем событие ПОСЛЕ сохранения
                    // то и в поле получим значение уже обработанное конвертеорм!!!
                    // Converter на это поле вернет после сохранения boolean!
                    .setParameter("is_actual", user.getIsActual().getIsActualStatus())
                    .setParameter("created_at", user.getCreatedAt())
                    .setParameter("updated_at", user.getUpdatedAt())

                    .setFlushMode(FlushMode.MANUAL)
                    .executeUpdate();
        }
    }

    /**
     * Does this listener require that after transaction hooks be registered?
     *
     * @param persister The persister for the entity in question.
     * @return {@code true} if after transaction callbacks should be added.
     * @deprecated use {@link #requiresPostCommitHandling(EntityPersister)}
     */
    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }
}
