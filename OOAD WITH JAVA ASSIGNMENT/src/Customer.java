public class Customer {
    private int customerID;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    public Customer(int customerID, String name, String address, String phoneNumber, String email) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getCustomerID() { return customerID; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
}
