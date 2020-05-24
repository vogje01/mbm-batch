package com.hlag.fis.batch.jobs.db2synchronization.steps.users;

import com.hlag.fis.db.db2.model.UsersOld;
import com.hlag.fis.db.db2.repository.UsersOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersProcessor implements ItemProcessor<UsersOld, UsersOld> {

    private static final Logger logger = LoggerFactory.getLogger(UsersProcessor.class);

    @Value("${dbSync.basis.users.fullSync}")
    private boolean fullSync;

    private UsersOldRepository usersOldRepository;

    @Autowired
    public UsersProcessor(UsersOldRepository usersOldRepository) {
        this.usersOldRepository = usersOldRepository;
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
    public UsersOld process(UsersOld usersOld) {
        logger.debug("Processing old user  - " + usersOld.toString());
        Optional<UsersOld> oldUsers = usersOldRepository.findById(usersOld.getId());
        if (oldUsers.isPresent()) {
            if (!fullSync && !oldUsers.get().getLastChange().equals(usersOld.getLastChange())) {
                return usersOld;
            }
        } else {
            return usersOld;
        }
        return null;
    }
}