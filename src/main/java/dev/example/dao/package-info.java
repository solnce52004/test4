
@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(
                name = "User_findUsersOrderByName",
                query = "from User as u order by u.username asc",
                timeout = 1,
                fetchSize = 1,
                comment = "find all users"
        )
})
@org.hibernate.annotations.NamedNativeQueries({
        @org.hibernate.annotations.NamedNativeQuery(
                name = "User_findUsersByRole_Native",
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
//                resultClass = dev.example.dao.dto.UserFullDTO.class
        )
})
//@javax.persistence.SqlResultSetMapping(
//        name = "UserDTO",
//        classes = @javax.persistence.ConstructorResult(
//                targetClass = dev.example.dao.dto.UserFullDTO.class,
//                columns = {
//                        @javax.persistence.ColumnResult(name = "id"),
//                        @javax.persistence.ColumnResult(name = "username"),
//                        @javax.persistence.ColumnResult(name = "addressTd"),
//                        @javax.persistence.ColumnResult(name = "roleTitle")
//                }
//        )
//)

package dev.example.dao;