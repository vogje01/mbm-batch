package com.momentum.batch.server.database.domain;

import java.util.UUID;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.1
 */
public class UserGroupBuilder {

    private final UserGroup userGroup = new UserGroup();

    public UserGroupBuilder withId(String id) {
        userGroup.setId(id);
        return this;
    }

    public UserGroupBuilder withRandomId() {
        userGroup.setId(UUID.randomUUID().toString());
        return this;
    }

    public UserGroupBuilder withName(String nodeName) {
        userGroup.setName(nodeName);
        return this;
    }

    public UserGroupBuilder withDescription(String description) {
        userGroup.setDescription(description);
        return this;
    }

    public UserGroup build() {
        return userGroup;
    }
}
