package dev.example.entities.interceptors;

import dev.example.entities.AuditLogUser;
import dev.example.entities.User;
import dev.example.entities.interfaces.Auditable;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO: ПОТОКОБЕЗОПАСНЫМ НЕ ЯВЛЯЕТСЯ!!!
 */
public class AuditLogUserInterceptor extends EmptyInterceptor {

    private Session currentSession;
    private Long currentAuthorId;
    private final Set<Auditable> inserts = new HashSet<>();
    private final Set<Auditable> updates = new HashSet<>();

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public void setCurrentAuthorId(Long currentAuthorId) {
        this.currentAuthorId = currentAuthorId;
    }

    /**
     * on update
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            updates.add((Auditable) entity);
        }

        return false;
    }

    /**
     * on save
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            inserts.add((Auditable) entity);
        }

        return false;
    }

    /**
     * after save
     */
    @Override
    public void postFlush(Iterator entities) {

        if (currentSession != null) {

            try (Session tmpSession = currentSession
                    .sessionWithOptions()//return session builder
                    .transactionContext()
                    .connection()//будем использовать соединение и контекст текущей сессии
                    .openSession()) {
                // аудит обновления записей в таблице
                for (Auditable entity : updates) {
                    tmpSession.persist(
                            new AuditLogUser(
                                    "update",
                                    entity.getId(),
                                    entity.getClass().getName(),
                                    currentAuthorId
                            )
                    );
                }

                // аудит создания записей в таблице
                for (Auditable entity : inserts) {
                    tmpSession.save(
                            new AuditLogUser(
                                    "insert",
                                    entity.getId(),
                                    entity.getClass().getName(),
                                    currentAuthorId
                            )
                    );
                }

//            tmpSession.flush();//закрываем временный сеанс

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                updates.clear();
                inserts.clear();
            }
        }
    }
}
