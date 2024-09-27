
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
public class Main {
    private static final String url="jdbc:mysql://localhost:3306/myHotelDB";
    private static final String name="root";
    private static final String pass="Admin@123";
    public static void main(String[] args)throws SQLException,ClassNotFoundException
    {

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Class Loaded Successfully");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        try(Connection conn=DriverManager.getConnection(url,name,pass))
        {
            System.out.println("Connection established successfully");
            Statement stmt=conn.createStatement();
            Scanner in=new Scanner(System.in);
            while(true)
            {
                System.out.println();
                System.out.println("...Welcome to the Hotel Reservation System...!!!");
                System.out.println(" Press 1 to book a room ");
                System.out.println(" Press 2 to fetch reservation details ");
                System.out.println(" Press 3 to fetch room number");
                System.out.println(" Press 4 to update an existing reservation");
                System.out.println(" Press 5 to delete a reservation record");
                System.out.println(" Press 6 to exit from the menu");
                System.out.println(" Enter your choice");
                int choice=in.nextInt();
                switch (choice)
                {
                    case 1:
                        bookRoom(stmt,in);
                        break;
                    case 2:
                        getDetails(stmt);
                        break;
                    case 3:
                        fetchRoomNo(stmt,in);
                        break;
                    case 4:
                        updateExisting(stmt,in);
                        break;
                    case 5:
                        deleteRecord(stmt,in);
                        break;
                    case 6: {
                        exit();
                        in.close();
                        stmt.close();
                        conn.close();
                        return;
                    }
                    default:
                        System.out.println("Wrong Choice...!!! please select an appropriate option");

                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static void bookRoom(Statement stmt, Scanner in)throws SQLException
    {
        System.out.println("Enter the Guest's Name");
        String gName=in.next();
        System.out.println("Enter the Guest's Phone Number");
        String gPhone=in.next();
        System.out.println("Enter the Guest's Room Number");
        int gRoom=in.nextInt();
        String query="insert into reservation (guestName,guestMobNo,roomNo) "+"values("+"'"+gName+"'"+","+"'"+gPhone+"'"+","+gRoom+")"+";";
        int rs=stmt.executeUpdate(query);
        if(rs>0)
        {
            System.out.println(rs+"row(s)"+"affected...");
            System.out.println("Reservation successfull...!!!");
        }
        else {
            System.out.println("booking Unsuccessfull");
        }
    }
    private static void getDetails(Statement stmt)throws SQLException
    {
        String query="Select * from reservation";
        ResultSet rs=stmt.executeQuery(query);
        System.out.println("******************************************************************************************************");
        System.out.println("|  reservation id  |    guestName    |    guestMobNo    |   roomNo   |       timeOfBooking      | ");
        while(rs.next())
        {
            int bookId=rs.getInt("reservation_id");
            String gName=rs.getString("guestName");
            String gPhone=rs.getString("guestMobNo");
            int gRoom=rs.getInt("roomNo");
            String time=rs.getTimestamp("timeOfBooking").toString();
            System.out.println();
            System.out.print("|        "+bookId+"       ");
            System.out.print("|       "+gName+"      ");
            System.out.print("|      "+gPhone+"  ");
            System.out.print("|       "+gRoom+"      ");
            System.out.print("| "+time+"  |");
            System.out.println();

        }
        System.out.println("*******************************************************************************************************");

    }
    private static void fetchRoomNo(Statement stmt,Scanner in)throws SQLException
    {
        System.out.println("Enter the reservation_id of the guest");
        String resv_id=in.next();
        String query = "select roomNo,guestName from reservation where reservation_id=" + resv_id + ";";
        ResultSet rs=stmt.executeQuery(query);
        if(rs.next()) {
            int roomNum = rs.getInt("roomNo");
            String gname = rs.getString("guestName");
            System.out.println(" with booking_id=" + resv_id + " Guest name is "+gname+" and room no is "+ roomNum);
        }
        else
            System.out.println("There is no prior reservation with reservation id = "+resv_id);
    }
    private static void updateExisting(Statement stmt,Scanner in)throws SQLException
    {
        System.out.println("Enter the booking id of the record you want to update");
        String resv_id=in.next();
        System.out.println("Enter the Guest's Name");
        String gName=in.next();
        System.out.println("Enter the Guest's Phone Number");
        String gPhone=in.next();
        System.out.println("Enter the Guest's Room Number");
        String gRoom=in.next();
        String query="Update reservation set guestName="+"'"+gName+"'"+","+"guestMobNo="+"'"+gPhone+"'"+","+"roomNo="+gRoom+" where reservation_id="+resv_id+";";
        int rs=stmt.executeUpdate(query);
        if(rs>0)
            {
                System.out.println(rs+"row(s)"+"affected...");
                System.out.println("Update successfull...!!!");
            }
        else
            {
                System.out.println("Update Unsuccessfull");
            }
    }
    private static void deleteRecord(Statement stmt,Scanner in)throws SQLException
    {
        System.out.println("Enter the booking id of the record you want to update");
        String resv_id=in.next();
        String query="Delete from reservation where reservation_id="+resv_id+";";
        int rs=stmt.executeUpdate(query);
        if(rs>0)
        {
            System.out.println(rs+"row(s)"+"affected...");
            System.out.println("Delete successfull...!!!");
        }
        else
        {
            System.out.println("Delete Unsuccessfull");
        }

    }
    public static void exit()throws InterruptedException
    {
        System.out.print("Exiting from the System!!!");
        try {
            int i = 5;
            while (i != 0) {
                System.out.print(".");
                Thread.sleep(650);
                i--;
            }
            System.out.println();
            System.out.println("ThankYou...!!! For using Hotel Management System");
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}