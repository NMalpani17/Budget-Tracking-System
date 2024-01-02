package edu.neu.csye6200.controller;

import edu.neu.csye6200.annotation.AuthorizeUser;
import edu.neu.csye6200.exception.CustomException;
import edu.neu.csye6200.model.Group;
import edu.neu.csye6200.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Group> getAllGroups(){
        return groupService.getAllGroups();
    }

    @GetMapping("/{groupId}")
    public Group getGroupById(@PathVariable int groupId) {
        return groupService.getGroupById(groupId);
    }

    @PostMapping
    @AuthorizeUser
    public ResponseEntity<String> createGroup(@RequestBody Group group) {
        try {
            Group createdGroup = groupService.createGroup(group);
            return ResponseEntity.ok("Group created successfully with id : " + createdGroup.getId());
        } catch (CustomException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access : User is not valid");
        }
    }

    @PutMapping("/{groupId}")
    @AuthorizeUser
    public Group updateGroup(@PathVariable int groupId, @RequestBody Group group) {
        return groupService.updateGroup(groupId, group);
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable int groupId) {
        groupService.deleteGroup(groupId);
    }
}
