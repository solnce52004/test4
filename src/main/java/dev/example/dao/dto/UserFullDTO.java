package dev.example.dao.dto;

import lombok.*;

@NoArgsConstructor //!!!
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserFullDTO {
    private Long id;
    private String username;
    private Long addressId;
    private String roleTitle;
}
