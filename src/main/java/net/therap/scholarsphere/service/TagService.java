package net.therap.scholarsphere.service;

import jakarta.persistence.EntityNotFoundException;
import net.therap.scholarsphere.model.Tag;
import net.therap.scholarsphere.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mehzabinaothoi
 * @since 1/12/24
 */
@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findById(long id) {
        return tagRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No tag found with id: " + id));
    }

    public Tag findByName(String name) {
       return tagRepository.findByName(name);
    }
}
