package dev.example.dao.audit;

import dev.conf.TestConfig;
//import dev.example.entities.envers.CustomRevisionEntity;
import dev.example.entities.envers.CustomRevisionEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {TestConfig.class},
        loader = AnnotationConfigContextLoader.class
)
class AuditUserDaoImplTest {

    @Autowired
    AuditUserDaoImpl auditUserDao;

    private static final Long UID = 5138L;

    @Test
    void findRevisionsByNumbers() {
//        final HashSet<Number> numbers = new HashSet<>();
//        numbers.add(5020);
//        numbers.add(5021);

        final HashSet<Number> numbers = new HashSet<>(auditUserDao.getUserRevisions(5138L));
        final Map<Number, CustomRevisionEntity> revisions = auditUserDao.findRevisionsByNumbers(numbers);

        revisions.entrySet().forEach(System.out::println);
        assertThat(revisions).isNotEmpty();
    }

    @Test
    void getUserRevisions(){
        final List<Number> userRevisions = auditUserDao.getUserRevisions(UID);

        userRevisions.forEach(System.out::println);
        assertThat(userRevisions).isNotEmpty();
    }

    @Test
    @Disabled(value = "TODO: try tracking")
    void getRev() {
        auditUserDao.getRev();
    }
}