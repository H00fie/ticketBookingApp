package bm.app.model;

public class Customer {

    private int id;
    private String name;
    private String email;
    private String ticketType;
    private int seatNumber;

    public Customer() {
    }


    public Customer(int id, String name, String email, String ticketType, int seatNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.ticketType = ticketType;
        this.seatNumber = seatNumber;
    }


    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getSeatnumber() {
        return seatNumber;
    }

    public void setSeatnumber(int seatnumber) {
        this.seatNumber = seatnumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", seatNumber=" + seatNumber +
                '}';
    }

}
