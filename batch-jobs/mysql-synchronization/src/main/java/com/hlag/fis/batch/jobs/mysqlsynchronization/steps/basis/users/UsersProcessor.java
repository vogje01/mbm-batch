package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.users;

import com.hlag.fis.db.db2.model.UsersOld;
import com.hlag.fis.db.mysql.model.Users;
import com.hlag.fis.db.mysql.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersProcessor implements ItemProcessor<UsersOld, Users> {

    private static final Logger logger = LoggerFactory.getLogger(UsersProcessor.class);

    @Value("${users.fullSync}")
    private boolean fullSync;

    private UsersRepository usersRepository;

    @Autowired
    public UsersProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Item processor for the user model.
     * <p>
     * This will create a new MySQL user model.
     *
     * @param usersOld old DB2 user .
     * @return full developed MySQL user model.
     */
    @Override
    public Users process(UsersOld usersOld) {
        logger.debug("Processing old user  - " + usersOld.toString());
        Users newUsers;
        Optional<Users> oldUsersOptional = usersRepository.findByUserId(usersOld.getId().getUserId());
        if (oldUsersOptional.isPresent()) {
            if (!fullSync && oldUsersOptional.get().getLastChange().equals(usersOld.getLastChange())) {
                return null;
            }
            newUsers = oldUsersOptional.get();
        } else {
            newUsers = new Users();
        }
        newUsers.update(usersOld);
        return newUsers;
    }
}