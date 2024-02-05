package net.therap.scholarsphere.repository;

import net.therap.scholarsphere.model.Paper;
import net.therap.scholarsphere.model.Rating;
import net.therap.scholarsphere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author aothoi
 * @since 05/02/2024
 */

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Rating findByUserAndPaper(User user, Paper paper);
}
