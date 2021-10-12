package dev.example.dto;

import dev.example.entities.Address;
import dev.example.entities.Role;
import lombok.*;

import java.util.List;

@NoArgsConstructor //!!!
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDTO {
    private Long id;
    private String username;
    private List<Role> roles;
    private Address addresses;
}