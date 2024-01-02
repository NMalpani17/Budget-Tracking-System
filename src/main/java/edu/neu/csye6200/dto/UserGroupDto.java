package edu.neu.csye6200.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGroupDto {
    private Integer groupId;
    private Integer userId;
    private String name;
    private String email;
}
