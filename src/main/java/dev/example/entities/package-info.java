@NamedQueries({
        @NamedQuery(
                name = UserNamedQueries.FIND_USERS_ORDER_BY_NAME,
                query = "from User as u order by u.username asc",
                timeout = 1,
                fetchSize = 1,
                comment = "find all users"
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = UserNamedQueries.FIND_USERS_BY_ROLE_NATIVE,
                query = "select " +
                        "u.id as id, " +
                        "u.username as username, " +
                        "u.address_id as addressId, " +
                        "r.title as roleTitle " +
                        "from users u " +
                        "join user_role ur on u.id = ur.user_id " +
                        "join roles r on r.id = ur.role_id " +
                        "where r.title = :role_title"//,
//                resultSetMapping = "UserDTO"
//                resultClass = dev.example.dto.UserFullDTO.class
        )
})
//@javax.persistence.SqlResultSetMapping(
//        name = "UserDTO",
//        classes = @javax.persistence.ConstructorResult(
//                targetClass = dev.example.dto.UserFullDTO.class,
//                columns = {
//                        @javax.persistence.ColumnResult(name = "id"),
//                        @javax.persistence.ColumnResult(name = "username"),
//                        @javax.persistence.ColumnResult(name = "addressTd"),
//                        @javax.persistence.ColumnResult(name = "roleTitle")
//                }
//        )
//)
@GenericGenerator(
        name = "GENERATOR_ENHANCED_SEQUENCE",
        strategy = "enhanced-sequence",
        parameters = {
                @Parameter(
                        name = "sequence_name",
                        value = "TEST4_SEQUENCE"
                ),
                @Parameter(
                        name = "initial_value",
                        value = "5000"
                )
        }
)
//@Filter не очень хорошо работает с кэшированием!!!!
//Кэш второго уровня хранит только полные нефильтрованные коллекции!!!!
@FilterDefs(
        @FilterDef(
                name = DynamicUserFilter.LIMIT_BY_IS_ACTUAL,

                //это не подзапрос (как @Subselect), а условия для полей ТАБЛИЦЫ на sql
                defaultCondition = "CAST(created_at as DATE) = " +
                        "CAST(:" + DynamicUserFilter.LIMIT_BY_IS_ACTUAL_PARAM_CREATED_AT + " as DATE) "+
                        "and is_actual != :" + DynamicUserFilter.LIMIT_BY_IS_ACTUAL_PARAM_IS_ACTUAL,

                parameters = {
                        @ParamDef(name = DynamicUserFilter.LIMIT_BY_IS_ACTUAL_PARAM_CREATED_AT, type = "java.lang.String"),//low!!! date?
                        @ParamDef(name = DynamicUserFilter.LIMIT_BY_IS_ACTUAL_PARAM_IS_ACTUAL, type = "int")
                }
        )
)
package dev.example.entities;

import dev.example.entities.filters.DynamicUserFilter;
import dev.example.entities.named_queries.UserNamedQueries;
import org.hibernate.annotations.*;