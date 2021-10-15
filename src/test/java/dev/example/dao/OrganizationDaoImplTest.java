package dev.example.dao;

import dev.conf.TestConfig;
import dev.example.entities.Department;
import dev.example.entities.Organization;
import dev.example.entities.OrganizationPK;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {TestConfig.class},
        loader = AnnotationConfigContextLoader.class
)
class OrganizationDaoImplTest {

    @Autowired
    private OrganizationDaoImpl organizationDao;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

/*    @Test
    void createByEmbededId() {

//        final Department dep1 = new Department();
//        dep1.setTitle("dep2");

        final OrganizationPK embeddedId = new OrganizationPK();
        embeddedId.setNum(UUID.randomUUID().toString().substring(0, 31));
//        embeddedId.setDepartmentId(null); //не учитывается, если @MapsId
        embeddedId.setDepartment_id(1L); //если не @MapsId, а @JoinColumn, dep уже должен быть сохранен, достаточно указать только ид

        final Organization org1 = new Organization();
        org1.setEmbeddedId(embeddedId);
        org1.setTitle("org1");
        org1.setAddressId(1L);
//        org1.setDepartment(dep1);//обязательно при @MapsId!!!

        organizationDao.create(org1);
    }*/

    @Test
    void createByIdClass() {
        final Organization org1 = new Organization();
//        org1.setNum(UUID.randomUUID());
        org1.setDepartmentId(1L);
        org1.setTitle("org1");
        org1.setAddressId(1L);

        organizationDao.create(org1);
    }
}