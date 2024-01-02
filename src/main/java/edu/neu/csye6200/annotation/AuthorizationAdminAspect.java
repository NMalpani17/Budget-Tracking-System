package edu.neu.csye6200.annotation;

import edu.neu.csye6200.enums.Role;
import edu.neu.csye6200.exception.CustomException;
import edu.neu.csye6200.model.User;
import edu.neu.csye6200.model.UserRole;
import edu.neu.csye6200.repository.UserRoleRepository;
import edu.neu.csye6200.service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
//@Order(1)
public class AuthorizationAdminAspect {


    private UserService userService;
    private UserRoleRepository userRoleRepository;
    private String adminUserName;
    private String adminPassword;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Value("${admin-username}")
    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    @Value("${admin-password}")
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Before("@annotation(edu.neu.csye6200.annotation.AuthorizeAdmin) || @within(edu.neu.csye6200.annotation.AuthorizeAdmin)")
    public void checkAuthorization() throws CustomException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !isValidAuthorization(authorizationHeader)) {
            System.out.println("this is not working fine");
            throw new CustomException("Unauthorized access : User is not valid");
        }
    }

    private boolean isValidAuthorization(String authorizationHeader) {
        String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedBytes);

        // Split the credentials into username and password
        String[] splitCredentials = credentials.split(":", 2);
        String username = splitCredentials[0];
        String password = splitCredentials[1];

        // check if the user is an Admin, if yes return true (Admin will have access to everything)
        if (username.equals(adminUserName) && password.equals(adminPassword)) {
            return true;
        }

        User user = userService.findUserByName(username);
        if (!Objects.equals(user.getPassword(), password)) {
            return false;
        }
        Optional<UserRole> userRole = userRoleRepository.findById(user.getId());
        if (ObjectUtils.isEmpty(user) || userRole.isEmpty()) {
            return false;
        } else if (userRole.get().getRole().equals(Role.ROLE_ADMIN)) {
            return true;
        }
        System.out.println("this is working fine");
        return false;
    }
}
