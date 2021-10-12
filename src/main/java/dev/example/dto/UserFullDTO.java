package dev.example.dto;

import lombok.*;

@NoArgsConstructor //!!!
@AllArgsConstructor
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
