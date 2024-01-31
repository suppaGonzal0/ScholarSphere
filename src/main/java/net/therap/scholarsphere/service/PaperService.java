package net.therap.scholarsphere.service;

import jakarta.persistence.EntityNotFoundException;
import net.therap.scholarsphere.enums.Action;
import net.therap.scholarsphere.model.Paper;
import net.therap.scholarsphere.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mehzabinaothoi
 * @since 1/9/24
 */
@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

//    @Autowired
//    private NotificationService notificationService;

    public Paper findById(long id) {
        return paperRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No paper found with id: " + id));
    }

    @Transactional
    public void create(Paper paper) {
        paperRepository.save(paper);

//        notificationService.notifyFollowers(paper.getAuthors());
    }

    public List<Paper> findAll(String sort) {
        return paperRepository.findAllSorted(sort);
    }

    public List<Paper> findAllUnapproved() {
        return paperRepository.findAllByIsApprovedFalse();
    }

    public List<Paper> searchByTitle(String title) {
        return paperRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Paper> searchByConference(String conferenceTitle) {
        return paperRepository.findByConferenceTitleContainingIgnoreCase(conferenceTitle);
    }

    public List<Paper> searchByTag(String tagName) {
        return paperRepository.findByTagsNameContainingIgnoreCase(tagName);
    }

    public List<Paper> findAllByConference(long conferenceId) {
        return paperRepository.findByConferenceIdAndIsApprovedTrue(conferenceId);
    }

    public List<Paper> searchByAuthorEmail(String email) {
        return paperRepository.findByAuthorsEmailContainingIgnoreCaseAndIsApprovedTrue(email);
    }

    @Transactional
    public void incrementDownloadCount(Paper paper) {
        paper.setDownloadCount(paper.getDownloadCount() + 1);

        paperRepository.save(paper);
    }

//    @Transactional
//    public void approvePaper(String[] approvedPapers) {
//        for (String id : approvedPapers) {
//            Paper paper = entityManager.find(Paper.class, Long.parseLong(id));
//            paper.setApproved(true);
//
//            entityManager.merge(paper);
//
////            notificationService.notifyForPaper(paper.getId(), Action.APPROVE);
//        }
//    }

    @Transactional
    public void unapprovePaper(String id) {
//        Paper paper = entityManager.find(Paper.class, Long.parseLong(id));
//
////        notificationService.notifyForPaper(paper.getId(), Action.UNAPPROVE);
//
//        entityManager.remove(paper);
    }

    public boolean isSaved(Long userId, Long paperId) {
        return paperRepository.countSavedPapers(userId, paperId) > 0;
    }

    public List<Paper> findSavedPapers(long id) {
        return paperRepository.findSavedPapersByUserId(id);
    }

    @Transactional
    public void saveOrUnsavePaper(long userId, long paperId, String action) {
        Action actionType = Action.valueOf(action.toUpperCase());

        switch (actionType) {
            case SAVE:
                paperRepository.savePaper(userId, paperId);
                break;

            case UNSAVE:
                paperRepository.unsavePaper(userId, paperId);
                break;
        }
    }
}
