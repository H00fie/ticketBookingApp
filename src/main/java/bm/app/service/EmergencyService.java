package bm.app.service;

import bm.app.config.Connections;
import bm.app.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static bm.app.config.Constans.*;
import static bm.app.config.Constans.DEFAULT_PASSWORD;

public class EmergencyService {

    /**
     * Methods to insert new data to a database given auto_increment option is disabled.
     */

    private Connection getConnection() {
        Connection connection = Connections.createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);

        if (connection == null) {
            return null;
        }

        return connection;
    }

    public boolean insertIfIdNotAutoincremented(Customer customer) {
        String sql = "insert into tickets (name, email, tickettype, seatnumber, id) values(?, ?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getTicketType());
            preparedStatement.setInt(4, customer.getSeatnumber());
            preparedStatement.setInt(5, incrementIdIfNotAutoIncremented());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int incrementIdIfNotAutoIncremented() {
        String nextId = "select max(id) from tickets";
        ResultSet resultSet;
        int id;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(nextId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = Integer.parseInt(resultSet.getString(1)) + 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return id;
    }
}
