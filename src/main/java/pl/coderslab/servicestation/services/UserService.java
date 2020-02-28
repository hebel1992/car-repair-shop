package pl.coderslab.servicestation.services;

import pl.coderslab.servicestation.models.User;

public interface UserService {

    User findByUserName(String username);

    User findByEmployeeId(Long employeeId);

    void saveUser(User user);
}
