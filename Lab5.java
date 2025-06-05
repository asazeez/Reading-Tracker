/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author atiya
 */
public class Lab5 {

    public static String url = "jdbc:sqlite:test.db";

    public static void query1(String name){
        String query = "SELECT COUNT(DISTINCT ra.bookID) AS bookCount \n"+
                       "FROM readingActivity ra \n"+
                       "JOIN reader r ON r.accountID = ra.readerID \n" +
                       "WHERE r.readerName = '" + name + "' AND strftime('%Y', ra.startDate) = '2023'";
                
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String count = rs.getString("bookCount");
                System.out.println("Number of books read: " + count);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    public static void query2(String name){
        String query = "SELECT COUNT(DISTINCT rev.bookID) AS bookCount\n"+
                       "FROM review rev\n"+
                       "JOIN reader r ON r.accountID = rev.readerID \n" +
                       "WHERE r.readerName = '" + name + "'AND rev.starRating = 5";

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String count = rs.getString("bookCount");

                System.out.println("Number of books rated 5 stars: " + count);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    
    public static void query3(String name){
        String query = "SELECT g.genre AS genreName, COUNT(bg.bookID) AS genre_count\n"+
                       "FROM bookGenres bg\n"+
                       "JOIN genre g ON bg.genreID = g.genreID\n"+
                       "JOIN readingActivity ra ON bg.bookID = ra.bookID\n"+
                       "JOIN reader r ON r.accountID = ra.readerID\n"+
                       "WHERE r.readerName = '" + name + "'\n" +
                       "GROUP BY g.genre\n"+
                       "ORDER BY genre_count DESC\n"+
                       "LIMIT 3";

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String genre = rs.getString("genreName");
                System.out.println("Genre: " + genre);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    
    public static void query4(String name){
        String query = "SELECT DISTINCT b.bookID AS ID, b.ISBN, b.title AS bookTitle, b.bookAuthour\n"+
                       "FROM book b \n"+
                       "JOIN readingActivity ra ON b.bookID = ra.bookID \n" +
                       "JOIN reader r ON r.accountID = ra.readerID\n" +
                       "WHERE r.readerName = '" + name + "' AND strftime('%Y', startDate) = '2023'";
                
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String bookId = rs.getString("ID");
                String isbn = rs.getString("ISBN");
                String title = rs.getString("bookTitle");
                String authour = rs.getString("bookAuthour");
                System.out.println("Book ID: " + bookId + " ,ISBN: " + isbn + " ,Title: " + title + " ,Authour: " + authour);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    
    public static void query5(String name){
        String query1 = "SELECT b.title AS bookTitle, MAX(r.starRating) AS higestRating\n"+
                       "FROM review r \n"+
                       "JOIN readingActivity ra ON r.readerID = ra.readerID \n"+
                       "JOIN book b ON r.bookID = b.bookID \n"+
                       "JOIN reader user ON user.accountID = ra.readerID\n" +
                       "WHERE user.readerName = '" + name + "' AND strftime('%Y', startDate) = '2023'";
        
        String query2 = "SELECT b.title AS bookTitle, MIN(r.starRating) AS lowestRating\n"+
                       "FROM review r \n"+
                       "JOIN readingActivity ra ON r.readerID = ra.readerID \n"+
                       "JOIN book b ON r.bookID = b.bookID \n"+
                       "JOIN reader user ON user.accountID = ra.readerID\n" +
                       "WHERE user.readerName = '" + name + "' AND strftime('%Y', startDate) = '2023'";
        
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query1);
            while (rs.next()){
                String title = rs.getString("bookTitle");
                String rating = rs.getString("higestRating");
                System.out.println("Highest Rated Book - " + title + " : " + rating + " stars");
            }
            stmt.close();
            
            stmt = c.createStatement();
            rs = stmt.executeQuery(query2);
            while (rs.next()){
                String title = rs.getString("bookTitle");
                String rating = rs.getString("lowestRating");
                System.out.println("Lowest Rated Book - " + title + " : " + rating + " stars");
            }
            stmt.close();
            
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    
    public static void query6(String name){
        String query = "SELECT ra.topAuthour AS authour\n"+
                       "FROM readingAnalytics ra\n"+
                       "JOIN reader r ON ra.analyticsID=r.analyticsID\n"+
                       "WHERE r.readerName = '" + name + "'";
        
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String authour = rs.getString("authour");
                System.out.println("Authour Name: " + authour);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    public static void query7(String name, String title){
        String query = "SELECT ra.currentBookName, b.bookLength - ra.currentPage AS pages_left_to_read\n"+
                       "FROM readingActivity ra\n"+
                       "JOIN book b ON ra.bookID = b.bookID\n"+
                       "JOIN reader r ON r.accountID = ra.readerID\n" +
                       "WHERE r.readerName = '" + name + "' AND ra.currentBookName = '" + title + "'";
        
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String bookTitle = rs.getString("currentBookName");
                String pagesLeft = rs.getString("pages_left_to_read");
                System.out.println("Number of pages left for the " + bookTitle + ": " + pagesLeft);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    public static void query8(String name){
        String query = "SELECT b.bookID AS ID, b.ISBN, b.title AS bookTitle, b.bookAuthour\n"+
                       "FROM readingActivity ra\n"+
                       "JOIN book b ON ra.bookID = b.bookID\n"+
                       "JOIN reader r ON r.accountID = ra.readerID\n"+
                       "WHERE ra.currentStatus = 'In Progress' and r.readerName = '" + name + "'";
        
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String bookId = rs.getString("ID");
                String isbn = rs.getString("ISBN");
                String title = rs.getString("bookTitle");
                String authour = rs.getString("bookAuthour");
                System.out.println("Currently Reading - Book ID: " + bookId + " ,ISBN: " + isbn + " ,Title: " + title + " ,Authour: " + authour);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    public static void query9(String name){
        String query = "SELECT b.bookID AS ID, b.ISBN, b.title AS bookTitle, b.bookAuthour\n"+
                       "FROM readingList l\n"+
                       "JOIN bookAddedToReadinglist rl ON rl.readingListID = l.listID\n"+
                       "JOIN book b ON b.bookID = rl.bookID\n"+
                       "JOIN reader r ON r.accountID = l.readerID\n" + 
                       "WHERE l.liststatus = 'Want to read' and r.readerName = '" + name + "'";
        
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String bookId = rs.getString("ID");
                String isbn = rs.getString("ISBN");
                String title = rs.getString("bookTitle");
                String authour = rs.getString("bookAuthour");
                System.out.println("Book ID: " + bookId + " ,ISBN: " + isbn + " ,Title: " + title + " ,Authour: " + authour);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    public static void query10(String name, String title){
        String query = "SELECT currentStatus\n"+
                       "FROM readingActivity AS ra\n"+
                       "JOIN reader r ON r.accountID = ra.readerID\n" +
                       "WHERE ra.currentBookName = '" + title +"' AND r.readerName = '" + name + "'";
                       
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String status = rs.getString("currentStatus");
                System.out.println("Reading status for " + title + " is " + status);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }               
    }
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        Scanner sc_title = new Scanner(System.in);
        boolean run = true;
        
        System.out.println("Welcome! Enter your name:");
        String name = sc.nextLine();
        
        do{
            System.out.println("1.Number of books you read this year.");
            System.out.println("2.Number of books rated five star this year.");
            System.out.println("3.Top three genres.");
            System.out.println("4.Books read this year.");
            System.out.println("5.Highest and Lowest rated books.");
            System.out.println("6.Most read authours.");
            System.out.println("7.Number of page left for a book");
            System.out.println("8.Books being currently read");
            System.out.println("9.Books on my want to read list.");
            System.out.println("10.Reading status for a book"); 
            System.out.println("0. Exit"); 
        
            System.out.print("Enter query option: ");
            int choice = sc.nextInt();
            
            if (choice == 0){
                System.out.println("Exiting...");
                run = false;
            }
            else if (choice == 1){
                query1(name);
            }
            else if (choice == 2){
                query2(name);
            }
            else if (choice == 3){
                query3(name);
            }
            else if (choice == 4){
                query4(name);
            }
            else if (choice == 5){
                query5(name);
            }
            else if (choice == 6){
                query6(name);
            }
            else if (choice == 7){
                System.out.println("Enter a book name:");
                String bookName = sc_title.nextLine();
                query7(name, bookName);
            }
            else if (choice == 8){
                query8(name);
            }
            else if (choice == 9){
                query9(name);
            }
            else if (choice == 10){
                System.out.println("Enter a book name:");
                String bookName = sc_title.nextLine();
                query10(name, bookName);
            }
        }while(run);
    }
    
}
