package bm.app.service;

import bm.app.config.Connections;
import bm.app.model.Complaint;
import bm.app.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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


    private Connection getConnection() {
        Connection connection = Connections.createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);

        if (connection == null) {
            return null;
        }

        return connection;
    }

    public boolean insert(Customer customer) {
        String sql = "insert into tickets (name, email, tickettype, seatnumber) values(?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getTicketType());
            preparedStatement.setInt(4, customer.getSeatnumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public List<Customer> selectAll() {
        String sql = "select name, email, tickettype, seatnumber from tickets";
        List<Customer> customers = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = getConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setTicketType(resultSet.getString("tickettype"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return customers;
    }

    public Customer getCustomerByEmail(String email) {
        Customer customer = new Customer();
        String sql = "select name, tickettype, seatnumber, id from tickets where email = ?;";
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setName(resultSet.getString("name"));
                customer.setTicketType(resultSet.getString("tickettype"));
                customer.setSeatnumber(resultSet.getInt("seatnumber"));
                customer.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customer;


    }

    public Customer getCustomerByName(String name) {
        Customer customer = new Customer();
        String sql = "select email, tickettype, seatnumber, id from tickets where name = ?;";
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setEmail(resultSet.getString("email"));
                customer.setTicketType(resultSet.getString("tickettype"));
                customer.setSeatnumber(resultSet.getInt("seatnumber"));
                customer.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customer;
    }

    public int getId(String name) {
        String sql = "select id from tickets where name = ?;";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int extractedId = 0;

        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                extractedId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return extractedId;
    }

    public Customer getCustomerById(int id) {
        Customer customer = new Customer();
        String sql = "select name, email, tickettype, seatnumber from tickets where id = ?;";
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setName(resultSet.getString("name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setTicketType(resultSet.getString("tickettype"));
                customer.setSeatnumber(resultSet.getInt("seatnumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customer;
    }

    public String getEmailById(int id){
        String sql = "select email from tickets where id = ?;";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String email = null;

        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                email = resultSet.getString("email");
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return email;
    }

    public boolean deleteById(int id){
        String sql = "delete from tickets where id = ?";
        try (final PreparedStatement preparedStatement = getConnection().prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateEmail(int id, String email){
        Customer customer = getCustomerById(id);
        String sql = "update tickets set email = ? where id = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Below are hibernate-bound methods.
     * @return
     */

    public List<Complaint> selectAllComplaints(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Complaint> customersComplaints = session.createQuery("from Complaint ", Complaint.class).list();
        transaction.commit();
        return  customersComplaints;
    }

    public boolean insertLostTicketComplaint(int id, String content){
        Complaint complaint = new Complaint();
        complaint.setId(id);
        complaint.setType("Lost ticket.");
        complaint.setContent(content);

        Transaction transaction = null;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(complaint);
            transaction.commit();

        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            return false;
        }
        return true;
    }

    public boolean insertDidntReceiveDetailsComplaint(int id, String content){
        Complaint complaint = new Complaint();
        complaint.setId(id);
        complaint.setType("Details not received.");
        complaint.setContent(content);

        Transaction transaction = null;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(complaint);
            transaction.commit();

        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            return false;
        }
        return true;
    }

    public boolean insertSearchEngineComplaint(int id, String content){
        Complaint complaint = new Complaint();
        complaint.setId(id);
        complaint.setType("Search engine malfunctional.");
        complaint.setContent(content);

        Transaction transaction = null;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(complaint);
            transaction.commit();

        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            return false;
        }
        return true;
    }


}
