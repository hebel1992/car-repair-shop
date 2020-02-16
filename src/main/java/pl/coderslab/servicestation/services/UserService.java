package pl.coderslab.servicestation.services;

import pl.coderslab.servicestation.models.User;

public interface UserService {

    User findByUserName(String username);

    void saveUser(User user);
}
