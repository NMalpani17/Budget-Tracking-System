package edu.neu.csye6200.service.impl;

import edu.neu.csye6200.dto.UserGroupDto;
import edu.neu.csye6200.model.Group;
import edu.neu.csye6200.model.User;
import edu.neu.csye6200.model.UserGroup;
import edu.neu.csye6200.repository.GroupRepository;
import edu.neu.csye6200.repository.UserGroupRepository;
import edu.neu.csye6200.repository.UserRepository;
import edu.neu.csye6200.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public UserGroup addUserToGroup(String userEmail, int groupId) {
        User userByEmail = userRepository.findByEmail(userEmail);
        User user = userRepository.findById(userByEmail.getId()).orElse(null);
        Group group = groupRepository.findById(groupId).orElse(null);

        if (user != null && group != null) {
            UserGroup.UserGroupId userGroupId = new UserGroup.UserGroupId();
            userGroupId.setUserId(userByEmail.getId());
            userGroupId.setGroupId(groupId);
            UserGroup userGroup = new UserGroup(userGroupId);
            return userGroupRepository.save(userGroup);
        }

        return null;
    }

    @Override
    public void removeUserFromGroup(int userId, int groupId) {
        userGroupRepository.deleteByUserIdAndGroupId(userId, groupId);
    }

    @Override
    public List<UserGroupDto> getMembersByGroup(Integer groupId) {
        List<UserGroup> userGroups = userGroupRepository.getByGroupId(groupId);
        List<Integer> userIds = userGroups.stream().map(x -> x.getUserGroupId().getUserId()).collect(Collectors.toList());
        Map<Integer, String> userNameByIdMap = userRepository.findAllById(userIds).stream().collect(Collectors.toMap(User::getId, User::getName));
        Map<Integer, String> userEmailByIdMap = userRepository.findAllById(userIds).stream().collect(Collectors.toMap(User::getId, User::getEmail));
        List<UserGroupDto> userGroupDtos = new ArrayList<>();

        // convert to DTO
        userGroups.forEach(userGroup -> {
            UserGroupDto userGroupDto = new UserGroupDto();
            userGroupDto.setName(userNameByIdMap.get(userGroup.getUserGroupId().getUserId()));
            userGroupDto.setUserId(userGroup.getUserGroupId().getUserId());
            userGroupDto.setGroupId(userGroup.getUserGroupId().getGroupId());
            userGroupDto.setEmail(userEmailByIdMap.get(userGroup.getUserGroupId().getUserId()));
            userGroupDtos.add(userGroupDto);
        });

        return userGroupDtos;
    }
}
