package edu.neu.csye6200.service;

import edu.neu.csye6200.dto.UserGroupDto;
import edu.neu.csye6200.model.UserGroup;

import java.util.List;

public interface UserGroupService {
    UserGroup addUserToGroup(String userEmail, int groupId);

    void removeUserFromGroup(int userId, int groupId);

    List<UserGroupDto> getMembersByGroup(Integer groupId);
}
