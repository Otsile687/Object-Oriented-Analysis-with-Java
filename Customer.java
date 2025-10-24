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

    // Setters
    public void setCustomerID(int customerID) { this.customerID = customerID; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    // Common behavior
    public String getCustomerInfo() {
        return "ID: " + customerID +
                ", Name: " + name +
                ", Age: " + age +
                ", Gender: " + gender +
                ", Occupation: " + occupation +
                ", Address: " + address +
                ", Phone: " + phoneNumber;
    }

    @Override
    public String toString() {
        return getCustomerInfo();
    }
}
