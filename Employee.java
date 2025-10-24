public class Employee {
    private int employeeID;
    private String name;
    private String companyPosition;

    public Employee(int employeeID, String name, String companyPosition) {
        this.employeeID = employeeID;
        this.name = name;
        this.companyPosition = companyPosition;
    }

    public int getEmployeeID() { return employeeID; }
    public String getName() { return name; }
    public String getCompanyPosition() { return companyPosition; }
}
