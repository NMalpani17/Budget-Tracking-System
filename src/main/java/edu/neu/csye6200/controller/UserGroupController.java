package edu.neu.csye6200.controller;

import edu.neu.csye6200.dto.UserGroupDto;
import edu.neu.csye6200.model.UserGroup;
import edu.neu.csye6200.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usergroup")
@CrossOrigin(origins = "http://localhost:3000")
public class UserGroupController {
    @Autowired
    private UserGroupService userGroupService;

    @PostMapping("/addUserToGroup")
    public UserGroup addUserToGroup(@RequestParam String userEmail, @RequestParam int groupId) {
        return userGroupService.addUserToGroup(userEmail, groupId);
    }

    @DeleteMapping("/removeUserFromGroup")
    public void removeUserFromGroup(@RequestParam int userId, @RequestParam int groupId) {
        userGroupService.removeUserFromGroup(userId, groupId);
    }

    @GetMapping("/{groupId}/members")
    public List<UserGroupDto> getMembersByGroup(@PathVariable Integer groupId){
        return userGroupService.getMembersByGroup(groupId);
    }

}
