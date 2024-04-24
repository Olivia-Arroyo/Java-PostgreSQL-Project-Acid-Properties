package addProduct;

import java.sql.*;
import java.io.IOException;


public class AddProduct {
    public static void main(String args[]) throws SQLException, IOException, ClassNotFoundException {
        // Load the PostgreSQL driver
        // Connect to the default database with credentials
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");

        // To ensure atomicity
        conn.setAutoCommit(false);

        // To ensure isolation
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            //Create statement object to send the SQL statement to the DBMS
            stmt1 = conn.createStatement();

            // ExecuteUpdate is used here since the query modifies the tables
            stmt1.executeUpdate("INSERT INTO Product VALUES ('p100', 'cd', 5)");
            stmt1.executeUpdate("INSERT INTO Stock VALUES ('p100', 'd2', 50)");

            // ExecuteQuery is used here since the query selects values from the tables
            rs = stmt1.executeQuery("SELECT * FROM Product P, Stock S WHERE P.ProdId = S.ProdId");

            while (rs.next()) {
                // Product Table
                System.out.println(rs.getString("prodid"));
                System.out.println(rs.getString("pname"));
                System.out.println(rs.getInt("price"));
                System.out.println(rs.getString("depid"));
                System.out.println(rs.getInt("quantity"));
            }




        } catch (SQLException e) {
            System.out.println("An exception was thrown");
            e.printStackTrace();

            //To ensure atomicity
            conn.rollback();
            stmt1.close();
            rs.close();
            conn.close();
            return;
        }

        conn.commit();
        stmt1.close();
        rs.close();
        conn.close();
    }

}
