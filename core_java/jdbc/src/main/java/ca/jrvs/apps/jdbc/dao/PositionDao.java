package ca.jrvs.apps.jdbc.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDao implements CrudDao<Position, String> {
    private Connection c;

    public PositionDao(Connection connection) {
        this.c = connection;
    }

    @Override
    public Position save(Position entity) throws IllegalArgumentException {
        String sql = "INSERT INTO position (symbol, number_of_shares, value_paid) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, entity.getTicker());
            stmt.setInt(2, entity.getNumOfShares());
            stmt.setDouble(3, entity.getValuePaid());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Inserting position failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Error executing SQL statement for insert", e);
        }
        return entity;
    }

    @Override
    public Optional<Position> findById(String s) throws IllegalArgumentException {
        String sql = "SELECT * FROM position WHERE symbol = ?";
        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error executing SQL statement for findById", e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Position> findAll() {
        List<Position> positions = new ArrayList<>();
        String sql = "SELECT * FROM position";
        try (Statement stmt = c.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                positions.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error executing SQL statement for findAll", e);
        }
        return positions;
    }

    @Override
    public void deleteById(String s) throws IllegalArgumentException {
        String sql = "DELETE FROM position WHERE symbol = ?";
        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, s);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Deleting position failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error executing SQL statement for deleteById", e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM position";
        try (Statement stmt = c.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error executing SQL statement for deleteAll", e);
        }
    }

    private Position mapResultSet(ResultSet rs) throws SQLException {
        Position position = new Position();
        position.setTicker(rs.getString("symbol"));
        position.setNumOfShares(rs.getInt("number_of_shares"));
        position.setValuePaid(rs.getDouble("value_paid"));
        return position;
    }
}
