package net.therap.scholarsphere.service;

import jakarta.persistence.EntityNotFoundException;
import net.therap.scholarsphere.enums.Action;
import net.therap.scholarsphere.model.User;
import net.therap.scholarsphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.therap.scholarsphere.enums.Action.CREATE;
import static net.therap.scholarsphere.util.Util.hashPassword;

/**
 * @author mehzabinaothoi
 * @since 1/4/24
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

	@Transactional
	public void saveOrUpdate(User user, Action action) {
		if (CREATE.equals(action)) {
			hashPassword(user);
		}

		userRepository.save(user);
	}

	@Transactional
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> findAllUsers() {
		return userRepository.findAllRegularUsers();
	}

	public User findById(long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("No user found with id: " + id));
	}

	@Transactional
	public void followOrUnfollow(Long followerId, Long followingId, String action) {
		Action actionType = Action.valueOf(action.toUpperCase());

		User follower = userRepository.getReferenceById(followerId);
		User following = userRepository.getReferenceById(followingId);

		switch (actionType) {
			case FOLLOW -> {
				following.getFollowers().add(follower);
				follower.getFollowings().add(following);
			}
			case UNFOLLOW -> {
				following.getFollowers().remove(follower);
				follower.getFollowings().remove(following);
			}
		}

		userRepository.save(following);
		userRepository.save(follower);

        notificationService.notifyForFollow(following, action);
	}

	@Transactional
	public boolean isFollowed(Long followerId, Long followingId) {
		User follower = userRepository.getReferenceById(followerId);
		User following = userRepository.getReferenceById(followingId);

		return following.getFollowers().contains(follower);
	}

	@Transactional
	public List<User> getFollowers(long id) {
		return userRepository.findByFollowings_Id(id);
	}

	@Transactional
	public List<User> getFollowings(long id) {
		return userRepository.findByFollowers_Id(id);
	}

//    @Transactional
//    public List<Paper> getPapers(long id) {
//        return userRepository.findByPapersIsApprovedTrueAndId(id);
//    }
}
