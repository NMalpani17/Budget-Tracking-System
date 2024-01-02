package edu.neu.csye6200.service;

import edu.neu.csye6200.model.Group;

import java.util.List;

public interface GroupService {
    Group getGroupById(int groupId);

    Group createGroup(Group group);

    Group updateGroup(int groupId, Group group);

    void deleteGroup(int groupId);

    boolean doesGroupExists(int groupId);

    List<Group> getAllGroups();
}
