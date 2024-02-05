package net.therap.scholarsphere.service;

import net.therap.scholarsphere.model.Paper;
import net.therap.scholarsphere.model.Rating;
import net.therap.scholarsphere.model.User;
import net.therap.scholarsphere.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.therap.scholarsphere.enums.Action.RATE;
import static net.therap.scholarsphere.enums.Action.UPDATE;

/**
 * @author mehzabinaothoi
 * @since 2/4/24
 */
@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private PaperService paperService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void save(int rating, User user, long paperId) {
        Paper paper = paperService.findById(paperId);

        paper.setTotalRated(paper.getTotalRated() + 1);
        paper.setTotalRating(paper.getTotalRating() + rating);

        paperService.saveOrUpdate(paper, UPDATE);
        ratingRepository.save(new Rating(rating, paper, user));

        notificationService.notifyForPaper(paper, RATE);
    }

    public Rating findRating(User user, Paper paper) {
        return ratingRepository.findByUserAndPaper(user, paper);
    }

    @Transactional
    public void delete(User user, long paperId) {
        Paper paper = paperService.findById(paperId);
        Rating rating = findRating(user, paper);

        paper.setTotalRated(paper.getTotalRated() - 1);
        paper.setTotalRating(paper.getTotalRating() - rating.getRating());

        paperService.saveOrUpdate(paper, UPDATE);
        ratingRepository.delete(rating);
    }

    @Transactional
    public void update(int rate, User user, long paperId) {
        Paper paper = paperService.findById(paperId);
        Rating rating = findRating(user, paper);

        paper.setTotalRating(paper.getTotalRating() - rating.getRating());
        paper.setTotalRating(paper.getTotalRating() + rate);

        rating.setRating(rate);

        paperService.saveOrUpdate(paper, UPDATE);
        ratingRepository.save(rating);
    }
}
