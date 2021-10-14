package dev.example.entities.event_listeners;

import lombok.extern.log4j.Log4j2;

import javax.persistence.PostPersist;

@Log4j2
public class PersistEntityListener {

    @PostPersist
    public void notifyAdmin(Object entity){
        //TODO: Address успел подгрузиться, а Role нет!!! все вложенные значеия роли null!!!
        log.info("NOTIFY ADMIN: entity: {}", entity);
    }
}
