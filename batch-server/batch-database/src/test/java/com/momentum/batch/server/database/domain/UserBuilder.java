package com.momentum.batch.server.database.domain;

import java.util.UUID;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.1
 */
public class UserBuilder {

    private User user = new User();

    public UserBuilder withId(String id) {
        user.setId(id);
        return this;
    }

    public UserBuilder withRandomId() {
        user.setId(UUID.randomUUID().toString());
        return this;
    }

    public UserBuilder withUserId(String userId) {
        user.setUserId(userId);
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        user.setFirstName(firstName);
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        user.setLastName(lastName);
        return this;
    }

    public UserBuilder withEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder withPhone(String phone) {
        user.setPhone(phone);
        return this;
    }

    public User build() {
        return user;
    }
}
