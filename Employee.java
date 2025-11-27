public class Employee {
    private int employeeID;
    private String name;
    private String position;

    public Employee(int employeeID, String name, String position) {
        this.employeeID = employeeID;
        this.name = name;
        this.position = position;
    }

    public int getEmployeeID() { return employeeID; }
    public String getName() { return name; }
    public String getPosition() { return position; }
}