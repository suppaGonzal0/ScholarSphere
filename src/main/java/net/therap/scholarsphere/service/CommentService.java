package net.therap.scholarsphere.service;

import jakarta.persistence.EntityNotFoundException;
import net.therap.scholarsphere.model.Comment;
import net.therap.scholarsphere.model.User;
import net.therap.scholarsphere.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.therap.scholarsphere.enums.Action.COMMENT;

/**
 * @author mehzabinaothoi
 * @since 1/12/24
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void save(Comment comment, User user) {
        comment.setUser(user);

        commentRepository.save(comment);

        notificationService.notifyForPaper(comment.getPaper(), COMMENT);
    }

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

        notificationService.notifyForLike(user, comment);
    }

    @Transactional
    public void unlike(long userId, long commentId) {
        User user = userService.findById(userId);
        Comment comment = findById(commentId);

        comment.getLikedByUsers().remove(user);

        commentRepository.save(comment);
    }
}
