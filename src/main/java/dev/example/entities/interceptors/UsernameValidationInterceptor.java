package dev.example.entities.interceptors;

import dev.example.entities.User;
import lombok.extern.log4j.Log4j2;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

@Log4j2
public class UsernameValidationInterceptor extends EmptyInterceptor {
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            User user = (User) entity;
            final String username = user.getUsername();
            if (username.length() >= 8) {
                log.warn(
                        "Trying to set username={} with length={} to User id={}",
                        username,
                        username.length(),
                        user.getId()
                );

                // но отсюда исключения будут падать неявно!
                // надо каждый раз в коде смотреть не навешен ли этот интерцептор...
//                throw new IllegalArgumentException("Invalid username length");
            }
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
