package net.therap.scholarsphere.service;

/*
 * @author mehzabinaothoi
 * @since 2/4/24
 */

import net.therap.scholarsphere.enums.Action;
import net.therap.scholarsphere.model.Comment;
import net.therap.scholarsphere.model.Notification;
import net.therap.scholarsphere.model.Paper;
import net.therap.scholarsphere.model.User;
import net.therap.scholarsphere.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author mehzabinaothoi
 * @since 1/16/24
 */
@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Transactional
	public void notifyFollowers(Set<User> authors) {

		for (User author : authors) {
			for (User follower : author.getFollowers()) {
				notificationRepository.save(new Notification(follower, author.getFullName() + " uploaded a paper."));
			}
		}
	}

	@Transactional
	public void notifyForPaper(Paper paper, Action action) {
		String message = switch (action) {
			case COMMENT -> "Someone commented on your paper " + paper.getTitle();
			case RATE -> "Someone rated your paper " + paper.getTitle();
			case APPROVE -> "Your paper \"" + paper.getTitle() + "\" has been approved";
			case UNAPPROVE -> "Your paper \"" + paper.getTitle() + "\" was not approved";
			default -> "";
		};

		assert !message.isEmpty();

		for (User author : paper.getAuthors()) {
			notificationRepository.save(new Notification(author, message));
		}
	}

	@Transactional
	public void notifyForLike(User receiver, Comment comment) {
		notificationRepository.save(new Notification(receiver, "Someone liked your comment \"" + comment.getText() + "\""));
	}

	@Transactional
	public void notifyForFollow(User receiver, String action) {
		notificationRepository.save(new Notification(receiver, "Someone " + action + "ed you"));
	}

	@Transactional
	public void markAsRead(User receiver) {
		notificationRepository.deleteAll(findAll(receiver));
	}

	public List<Notification> findAll(User receiver) {
		return notificationRepository.findAllByReceiver(receiver);
	}
}

