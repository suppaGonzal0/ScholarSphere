package net.therap.scholarsphere.repository;

import net.therap.scholarsphere.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    List<Conference> findByTitleContainingIgnoreCase(String title);

    Conference findByTitle(String title);

    List<Conference> findAllByOrderByCreatedOnDesc();
}
