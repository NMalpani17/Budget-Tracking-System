package edu.neu.csye6200.controller;

import edu.neu.csye6200.model.Simplify;
import edu.neu.csye6200.service.SimplifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/simplify")
@CrossOrigin(origins = "http://localhost:3000")
public class SimplifyController {

    @Autowired
    private SimplifyService simplifyService;

    @PostMapping("/{groupId}")
    public List<Simplify> simplify(@PathVariable int groupId) {
        return simplifyService.simplify(groupId);
    }
}
