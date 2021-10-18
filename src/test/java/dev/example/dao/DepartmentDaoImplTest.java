package dev.example.dao;

import dev.conf.TestConfig;
import dev.example.entities.Department;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {TestConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@Log4j2
class DepartmentDaoImplTest {
    @Autowired
    DepartmentDaoImpl departmentDaoImpl;

    @Test
    void findByIdWithGraph() {
        final Department graph = departmentDaoImpl.findByIdWithGraph(1L);
        log.info("----- {}", graph);
    }
}