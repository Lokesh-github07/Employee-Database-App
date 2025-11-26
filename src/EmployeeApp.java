import java.sql.*;
import java.util.Scanner;

public class EmployeeApp {

    // DB Connection details
    static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    static final String USER = "root"; // your MySQL username
    static final String PASS = "Root12345"; // your MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Employee Database App =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee(sc);
                    break;
                case 4:
                    deleteEmployee(sc);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // Add Employee
    private static void addEmployee(Scanner sc) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();
            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            String query = "INSERT INTO employees(name, salary, department) VALUES(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setDouble(2, salary);
            ps.setString(3, dept);

            ps.executeUpdate();
            System.out.println("Employee Added Successfully!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // View Employees
    private static void viewEmployees() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String query = "SELECT * FROM employees";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getDouble("salary") + " | " +
                        rs.getString("department")
                );
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Update Employee
    private static void updateEmployee(Scanner sc) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.print("Enter Employee ID to Update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();
            System.out.print("Enter New Salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();
            System.out.print("Enter New Department: ");
            String dept = sc.nextLine();

            String query = "UPDATE employees SET name=?, salary=?, department=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setDouble(2, salary);
            ps.setString(3, dept);
            ps.setInt(4, id);

            ps.executeUpdate();
            System.out.println("Employee Updated Successfully!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Delete Employee
    private static void deleteEmployee(Scanner sc) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.print("Enter Employee ID to Delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM employees WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Employee Deleted Successfully!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
