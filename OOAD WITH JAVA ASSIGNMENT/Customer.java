public class Customer {
    private int customerID;
    private String name;
    private String address;
    private String phoneNumber;
    private int age;
    private String gender;
    private String occupation;

    public Customer(int customerID, String name, String address, String phoneNumber,
                    int age, String gender, String occupation) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
    }

    // Getters
    public int getCustomerID() { return customerID; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getOccupation() { return occupation; }

    public String getCustomerInfo() {
        return String.format("ID: %d, Name: %s, Age: %d, Phone: %s", 
            customerID, name, age, phoneNumber);
    }

    @Override
    public String toString() {
        return getCustomerInfo();
    }
}