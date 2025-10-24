public class Individual extends Customer {
    public Individual(int customerID, String name, String address, String phoneNumber,
                      int age, String gender, String occupation) {
        super(customerID, name, address, phoneNumber, age, gender, occupation);
    }

    // Individual-specific behavior
    public boolean isEligibleForDiscount() {
        return getAge() < 18 || getAge() > 65;
    }

    @Override
    public String toString() {
        return "Individual Customer - " + super.getCustomerInfo();
    }
}