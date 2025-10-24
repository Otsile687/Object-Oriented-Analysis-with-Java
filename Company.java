public class Company extends Customer {
    public Company(int customerID, String name, String address, String phoneNumber,
                   int age, String gender, String occupation) {
        super(customerID, name, address, phoneNumber, age, gender, occupation);
    }

    // Company-specific behavior
    public boolean isCorporateClient() {
        return true;
    }

    @Override
    public String toString() {
        return "Company Customer - " + super.getCustomerInfo();
    }
}