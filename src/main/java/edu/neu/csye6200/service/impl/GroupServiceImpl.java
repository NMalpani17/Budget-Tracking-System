package edu.neu.csye6200.service.impl;

import edu.neu.csye6200.model.Group;
import edu.neu.csye6200.repository.DebtRepository;
import edu.neu.csye6200.repository.ExpenseRepository;
import edu.neu.csye6200.repository.GroupRepository;
import edu.neu.csye6200.repository.SimplifyRepository;
import edu.neu.csye6200.repository.UserGroupRepository;
import edu.neu.csye6200.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SimplifyRepository simplifyRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Override
    public Group getGroupById(int groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    @Override
    public Group createGroup(Group group) {
        // Implement validation or additional logic as needed
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(int groupId, Group group) {
        // Implement validation or additional logic as needed
        group.setId(groupId);
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(int groupId) {
        // clear debt by groupId
        // clear expense by groupId
        // clear simplify by groupId
        // clear from user group by groupId
        debtRepository.deleteByGroupId(groupId);
        expenseRepository.deleteByGroupId(groupId);
        simplifyRepository.deleteByGroupId(groupId);
        userGroupRepository.deleteByGroupId(groupId);

        // now proceed to delete the group
        groupRepository.deleteById(groupId);
    }

    @Override
    public boolean doesGroupExists(int groupId) {
        return groupRepository.existsById(groupId);
    }

    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        groups.sort(null);
        return groups;
    }
}
