package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    /**
     * Saves the specified user in the database.
     *
     * @param applicationUser the user which will be persisted
     * @return the user which was persisted in the database
     */
    ApplicationUser save(ApplicationUser applicationUser);

    /**
     * Find user with the specified email.
     *
     * @param email the users email address
     * @return the user with the specified email
     */
    ApplicationUser findUserByEmail(String email);

    /**
     * Update user
     *
     * @param applicationUser the values which will be updated
     * @return the updated user
     */
    //ApplicationUser updateByEmail(String email,ApplicationUser applicationUser);
}
