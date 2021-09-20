package by.training.сonfectionery.model.dao.impl;

import by.training.сonfectionery.domain.Feedback;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.ColumnName;
import by.training.сonfectionery.model.dao.FeedbackDao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FeedbackDaoImpl extends FeedbackDao {

    private static final String SQL_FIND_ALL_FEEDBACKS = """
             SELECT id, user_id, text, rating, date
             FROM feedbacks
            """;
    private static final String SQL_FIND_FEEDBACK_BY_ID = """
             SELECT id, user_id, text, rating, date
             FROM feedbacks
             WHERE id = ?;
            """;
    private static final String SQL_DELETE_FEEDBACK_BY_ID = """
            DELETE FROM feedbacks WHERE id = ?;
            """;
    private static final String SQL_CREATE_FEEDBACK = """
            INSERT INTO feedbacks(id, user_id, text, rating, date)
            VALUES (?, ?, ?, ?, ?);
            """;
    private static final String SQL_UPDATE_FEEDBACK = """
            UPDATE feedbacks
            SET  user_id = ?, text = ?, rating = ?, date = ?
            WHERE id = ?;
            """;

    @Override
    public List<Feedback> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_FEEDBACKS)) {
            List<Feedback> feedbacks = new LinkedList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Feedback feedback = buildFeedback(resultSet);
                    feedbacks.add(feedback);
                }
                return feedbacks;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all feedbacks", e);
        }
    }

    @Override
    public Optional<Feedback> findById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_FEEDBACK_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Feedback feedback = buildFeedback(resultSet);
                    return Optional.of(feedback);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to find feedback by id", e);
        }
    }

    @Override
    public boolean deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_FEEDBACK_BY_ID)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete feedback by id", e);
        }
    }

    @Override
    public boolean create(Feedback feedback) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_FEEDBACK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, feedback.getUserId());
            statement.setString(2, feedback.getText());
            statement.setInt(3, feedback.getRating());
            statement.setDate(4, Date.valueOf(feedback.getDate()));
            boolean result = statement.executeUpdate() == 1;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    feedback.setId(resultSet.getInt(1));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to create feedback", e);
        }
    }

    @Override
    public Feedback update(Feedback feedback) throws DaoException {
        Feedback oldFeedback = findById(feedback.getId()).
                orElseThrow(() -> new DaoException("Failed to update feedback, feedback Id was not found"));
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_FEEDBACK)) {
            statement.setInt(1, feedback.getUserId());
            statement.setString(2, feedback.getText());
            statement.setInt(3, feedback.getRating());
            statement.setDate(4, Date.valueOf(feedback.getDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update feedback", e);
        }
        return oldFeedback;
    }


    private Feedback buildFeedback(ResultSet resultSet) throws SQLException {

        return new Feedback.FeedbackBuilder()
                .setId(resultSet.getInt(ColumnName.ID))
                .setUserId(resultSet.getInt(ColumnName.USER_ID))
                .setText(resultSet.getString(ColumnName.TEXT))
                .setRating(resultSet.getInt(ColumnName.RATING))
                .setDate(resultSet.getDate(ColumnName.DATE).toLocalDate())
                .createFeedback();
    }
}
