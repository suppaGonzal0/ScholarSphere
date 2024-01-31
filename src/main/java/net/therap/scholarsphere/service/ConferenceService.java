package net.therap.scholarsphere.service;

import jakarta.persistence.EntityNotFoundException;
import net.therap.scholarsphere.model.Conference;
import net.therap.scholarsphere.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mehzabinaothoi
 * @since 1/9/24
 */
@Service
public class ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

    public List<Conference> searchByTitle(String title) {
        return conferenceRepository.findByTitleContainingIgnoreCase(title);
    }

    public Conference findById(long id) {
        return conferenceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No conference found with id: " + id));
    }

    public Conference findByTitle(String title) {
        return conferenceRepository.findByTitle(title);
    }

    public List<Conference> findAll() {
        return conferenceRepository.findAllByOrderByCreatedOnDesc();
    }

    @Transactional
    public void save(Conference conference) {
        conferenceRepository.save(conference);
    }
}
