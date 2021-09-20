package by.training.сonfectionery.domain;

import java.time.LocalDate;

public class Feedback extends Entity {
    private int userId;
    private String text;
    private int rating;
    private LocalDate date;

    public Feedback() {

    }

    public Feedback(int id, int userId, String text, int rating, LocalDate date) {
        super(id);
        this.setUserId(userId);
        this.setRating(rating);
        this.setText(text);
        this.setDate(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public int getUserId() {
        return userId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static class FeedbackBuilder {
        Feedback feedback;

        public FeedbackBuilder() {
            feedback = new Feedback();
        }

        public Feedback.FeedbackBuilder setId(int id) {
            feedback.setId(id);
            return this;
        }

        public Feedback.FeedbackBuilder setDate(LocalDate date) {
            feedback.setDate(date);
            return this;
        }

        public Feedback.FeedbackBuilder setUserId(int id) {
            feedback.setUserId(id);
            return this;
        }

        public Feedback.FeedbackBuilder setText(String text) {
            feedback.setText(text);
            return this;
        }

        public Feedback.FeedbackBuilder setRating(int rating) {
            feedback.setRating(rating);
            return this;
        }

        public Feedback createFeedback() {
            return feedback;
        }
    }


}
