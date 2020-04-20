package bm.app.service;

import bm.app.config.Connections;
import bm.app.model.Customer;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static bm.app.config.Constans.*;

@Service
public class CustomerService {



    private Connection getConnection(){
        Connection connection = Connections.createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);

        if (connection == null){
            return null;
        }

        return connection;
    }

    public boolean insert(Customer customer){
        String sql ="insert into tickets values(?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = getConnection().prepareStatement(sql)){
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getTicketType());
            preparedStatement.setInt(4, customer.getSeatnumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Customer> selectAll(){
        String sql ="select id, name, email, tickettype from tickets";
        List<Customer> customers = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = getConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setTicketType(resultSet.getString("tickettype"));
                customers.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(resultSet);
            close(preparedStatement);
        }
        return customers;
    }

    private static void close(Connection connection){
        try {
            if (connection != null){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void close(ResultSet resultSet){
        try {
            if (resultSet != null){
                resultSet.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void close(PreparedStatement preparedStatement){
        try {
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
