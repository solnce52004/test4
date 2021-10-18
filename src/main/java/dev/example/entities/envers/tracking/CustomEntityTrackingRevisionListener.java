//package dev.example.entities.envers.tracking;
//
//import dev.example.entities.envers.CurrentUser;
//import org.hibernate.envers.EntityTrackingRevisionListener;
//import org.hibernate.envers.RevisionType;
//
//import java.io.Serializable;
//
//public class CustomEntityTrackingRevisionListener implements EntityTrackingRevisionListener {
//
//    @Override
//    public void entityChanged(
//            Class entityClass,
//            String entityName,
//            Serializable entityId,
//            RevisionType revisionType,
//            Object revisionEntity
//    ) {
//        String type = entityClass.getName();
//        final CustomTrackingRevisionEntity entity = (CustomTrackingRevisionEntity) revisionEntity;
//        entity.addModifiedEntityType(type);
//        entity.setUsername(CurrentUser.INSTANCE.get());
//    }
//
//    @Override
//    public void newRevision(Object revisionEntity) {
//    }
//}
