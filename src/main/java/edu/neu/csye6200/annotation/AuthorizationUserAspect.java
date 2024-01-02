package edu.neu.csye6200.annotation;

import edu.neu.csye6200.enums.Role;
import edu.neu.csye6200.exception.CustomException;
import edu.neu.csye6200.model.User;
import edu.neu.csye6200.model.UserRole;
import edu.neu.csye6200.repository.UserRoleRepository;
import edu.neu.csye6200.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
public class AuthorizationUserAspect {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Value("${admin-username}")
    private String adminUserName;

    @Value("${admin-password}")
    private String adminPassword;

    @Before("@annotation(edu.neu.csye6200.annotation.AuthorizeUser) || @within(edu.neu.csye6200.annotation.AuthorizeUser)")
    @Pointcut("!execution(* edu.neu.csye6200.controller.UserController.deleteUser(..))")
    @Order(1)
    public void checkAuthorization() throws CustomException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !isValidAuthorization(authorizationHeader)) {
            System.out.println("Unauthorized access: User is not valid");
            throw new CustomException("Unauthorized access: User is not valid");
        }
    }


    @Pointcut("execution(* edu.neu.csye6200.controller.UserController.deleteUser(..)) && args(userId)")
    @Order(2)
    public void deleteUserPointcut(int userId) {
    }

    @Before("deleteUserPointcut(userId)")
    @Order(3)
    public void beforeDeleteUser(JoinPoint joinPoint, int userId) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !isValidAuthorization(authorizationHeader)) {
            System.out.println("Unauthorized access: User is not valid");
            throw new CustomException("Unauthorized access: User is not valid");
        }

        System.out.println("Before deleting user with ID: " + userId);
        // You can add additional logic before the method execution if needed
        HttpServletRequest request1 = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // Extracting path variables from the request attribute
        Map<String, String> pathVariables = (Map<String, String>) request1.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (pathVariables != null && pathVariables.containsKey("userId")) {
            String value = pathVariables.get("userId");
            System.out.println("PathVariable userId value: " + value);
        }
        try {
            // ... (your existing code)

            System.out.println("Exiting logPathVariable advice");
        } catch (Exception e) {
            System.err.println("Error in logPathVariable advice: " + e.getMessage());
            throw e; // Make sure to rethrow the exception if caught
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
        Optional<UserRole> userRole = userRoleRepository.findById(user.getId());
        if (ObjectUtils.isEmpty(user) || userRole.isEmpty()) {
            return false;
        } else if (userRole.get().getRole().equals(Role.ROLE_ADMIN)) {
            return true;
        }

        // check for user permission
        if (!Objects.equals(user.getPassword(), password)) {
            return false;
        }
        System.out.println("this is working fine");
        return true;
    }
}
