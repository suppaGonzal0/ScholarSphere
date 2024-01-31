package net.therap.scholarsphere.service;

import jakarta.persistence.EntityNotFoundException;
import net.therap.scholarsphere.model.Comment;
import net.therap.scholarsphere.model.User;
import net.therap.scholarsphere.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mehzabinaothoi
 * @since 1/12/24
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

//    @Autowired
//    private PaperService paperService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private NotificationService notificationService;

//    @Transactional
//    public void save(Comment comment, long userId) {
//        Paper paper = paperService.findById(comment.getPaper().getId());
//        comment.setPaper(paper);
//
//        User user = userService.findById(userId);
//        comment.setUser(user);
//
//        entityManager.persist(comment);
//
//        notificationService.notifyForPaper(comment.getPaper().getId(), Action.COMMENT);
//    }

    @Transactional
    public List<Comment> findAll(long id, String sort) {
        return commentRepository.findAllComments(id, sort);
    }

    public Comment findById(long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No comment found with id: " + id));
    }

    @Transactional
    public void addLike(long userId, long commentId) {
        User user = userService.findById(userId);
        Comment comment = findById(commentId);

        comment.getLikedByUsers().add(user);

        commentRepository.save(comment);

//        notificationService.notifyForLike(userId, commentId);
    }

    @Transactional
    public void unlike(long userId, long commentId) {
        User user = userService.findById(userId);
        Comment comment = findById(commentId);

        comment.getLikedByUsers().remove(user);

        commentRepository.save(comment);
    }
}