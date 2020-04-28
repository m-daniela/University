package jpa.ui;

import jpa.domain.Book;
import jpa.domain.Client;
import jpa.domain.Purchase;
import jpa.domain.validators.ValidatorException;
import jpa.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class Console {
    @Autowired
    private jpa.service.ClientService clientService;
    @Autowired
    private jpa.service.BookService bookService;
    @Autowired
    private jpa.service.PurchaseService purchaseService;

    public void runConsole() {
        boolean finished=false;
        while(!finished){
            printChoices();
            try {
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Input your choice: ");
                int choice = keyboard.nextInt();
                switch (choice) {
                    case 0:
                        finished = true;
                        break;
                    case 1:
                        addClient();
                        break;
                    case 2:
                        addBook();
                        break;
                    case 3:
                        printAllClients();
                        break;
                    case 4:
                        printAllBooks();
                        break;
                    case 5:
                        filterClients();
                        break;
                    case 6:
                        filterBooks();
                        break;
                    case 7:
                        deleteClient();
                        break;
                    case 8:
                        deleteBook();
                        break;
                    case 9:
                        updateClient();
                        break;
                    case 10:
                        updateBook();
                        break;
                    case 11:
                        addPurchase();
                        break;
                    case 12:
                        displayPurchases();
                        break;
                    case 13:
                        updatePurchase();
                        break;
                    case 14:
                        deletePurchase();
                        break;
                    case 15:
                        filterPurchases();
                        break;
                    case 16:
                        getReport();
                        break;
                    case 17:
                        sortClients();
                        break;
                    case 18:
                        sortBooks();
                        break;
                    case 19:
                        sortPurchases();
                        break;
                    default:
                        throw new ValidatorException("Please input a valid choice.");
                    }
            }catch(InputMismatchException e ){
                System.out.println("Please input a number.");
            }catch(ValidatorException ve){
                System.out.println(ve.getMessage());
            }
        }

    }

    private void printChoices(){
        System.out.println("\nChoose one from below:");
        System.out.println("0.Exit");
        System.out.println("1.Add new client.");
        System.out.println("2.Add new book.");
        System.out.println("3.Show all clients.");
        System.out.println("4.Show all books.");
        System.out.println("5.Filter clients.");
        System.out.println("6.Filter books.");
        System.out.println("7.Delete client.");
        System.out.println("8.Delete book.");
        System.out.println("9.Update client.");
        System.out.println("10.Update book.");
        System.out.println("11.Buy a book.");
        System.out.println("12.Show all purchases.");
        System.out.println("13.Update purchase.");
        System.out.println("14.Delete purchase.");
        System.out.println("15.Filter purchases.");
        System.out.println("16.Report.");
        System.out.println("17.Sort clients.");
        System.out.println("18.Sort books.");
        System.out.println("19.Sort purchases.");
    }

    //******************************************************************************************************************
    //Client functions:

    private void filterClients() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Name: ");
            String name = bufferRead.readLine();
            List<jpa.domain.Client> students = clientService.filterClientsByName(name);
            students.forEach(System.out::println);
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }

    }

    private void deleteClient(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            clientService.removeClient(id);
        } catch (IOException | SQLException | EmptyResultDataAccessException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
    }

    private void printAllClients() {
        try {
            List<Client> students = clientService.getAllClients();
            students.forEach(System.out::println);
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    private void addClient() {
        Optional<Client> client = readClient();
        client.ifPresent(c->{
            try {
                Client result = clientService.addClient(c);
            } catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }});
    }

    private Optional<Client> readClient() {
        System.out.println("Please enter a new client: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
//            System.out.println("ID: ");
//            Long id = Long.parseLong(bufferRead.readLine());
            System.out.println("Serial Number: ");
            String serialNumber = bufferRead.readLine();
            System.out.println("Name: ");
            String name = bufferRead.readLine();

            jpa.domain.Client client = new jpa.domain.Client(serialNumber, name);

            return Optional.of(client);
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
        return Optional.empty();
    }

    private Optional<Client> readClientUpdate() {
        System.out.println("Please enter a new client: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            Optional<Client> client = readClient();
            client.ifPresent(client1 -> {client1.setId(id);});
            return client;
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
        return Optional.empty();
    }

    private void updateClient(){
        Optional<Client> client = readClientUpdate();
        client.ifPresent(c->{
            try {
                clientService.updateClient(c);
            } catch (ValidatorException | SQLException e) {
                System.out.println(e.getMessage());
            }});
    }

    private void sortClients() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            System.out.println("Please enter your filters: ");
            String filters = bufferRead.readLine();
            System.out.println("Please enter the order: ");
            if (bufferRead.readLine().equals("DESC")){
                List<Client> clients = clientService.getAllClients(Sort.by(Sort.Direction.DESC, filters.split(" ")));
                clients.forEach(System.out::println);
            }
            else{
                List<Client> clients = clientService.getAllClients(Sort.by(Sort.Direction.ASC, filters.split(" ")));
                clients.forEach(System.out::println);
            }


        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // ----------------
    // Books
    // ----------------

    private void filterBooks() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Title: ");
        try {
            String filter = bufferRead.readLine();
            List<Book> filteredBooks = bookService.filter(filter);
            filteredBooks.forEach(System.out::println);
        } catch (IOException |SQLException e) {
            e.printStackTrace();
        }
    }

    private void printAllBooks() {
        try {
            List<Book> books = bookService.getAll();
            books.forEach(System.out::println);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void addBook() {
        Optional<Book> book = readBook();

        book.ifPresent(b->{
            try{
                bookService.add(b);
            }
            catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void updateBook(){
        Optional<Book> book = readBookUpdate();
        book.ifPresent(b->{
            try{
                bookService.update(b);
            }
            catch (ValidatorException | SQLException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void deleteBook(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ID: ");

        try {
            Long id = Long.parseLong(bufferRead.readLine());
            bookService.delete(id);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Optional<Book> findOneBook(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ID: ");
        try {
            Long id = Long.parseLong(bufferRead.readLine());
            return bookService.findOneBook(id);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    private Optional<jpa.domain.Book> readBook() {
        System.out.println("Please enter a new book: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Serial Number: ");
            String serialNumber = bufferRead.readLine();
            System.out.println("Title: ");
            String name = bufferRead.readLine();
            System.out.println("Author: ");
            String author = bufferRead.readLine();
            System.out.println("Year: ");
            int year = Integer.parseInt(bufferRead.readLine());
            System.out.println("Price: ");
            double price = Double.parseDouble(bufferRead.readLine());
            System.out.println("In stock: ");
            int stock = Integer.parseInt(bufferRead.readLine());

            jpa.domain.Book book = new jpa.domain.Book(serialNumber, name, author, year, price, stock);

            return Optional.of(book);
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
        return Optional.empty();
    }

    private Optional<Book> readBookUpdate(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ID: ");
        try {
            Long id = Long.parseLong(bufferRead.readLine());
            Optional<Book> book = readBook();
            book.ifPresent(book1 -> {book1.setId(id);});
            return book;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void sortBooks() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            System.out.println("Please enter your filters: ");
            String filters = bufferRead.readLine();
            System.out.println("Please enter the order: ");
            if (bufferRead.readLine().equals("DESC")){
                List<Book> books = bookService.getAll(Sort.by(Sort.Direction.DESC, filters.split(" ")));
                books.forEach(System.out::println);
            }
            else{
                List<Book> books = bookService.getAll(Sort.by(Sort.Direction.ASC, filters.split(" ")));
                books.forEach(System.out::println);
            }


        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    //******************************************************************************************************************
    //PURCHASES
    //******************************************************************************************************************

    private void addPurchase(){

        Optional<Purchase> purchase = readPurchase();
        purchase.ifPresent(p->{
            try {
                purchaseService.addPurchase(p);

            } catch (ParserConfigurationException | TransformerException | SAXException | IOException |SQLException  e) {
                e.printStackTrace();
            }

        });
    }

    private void displayPurchases(){
        try {
            List<jpa.domain.Purchase> purchases = purchaseService.getAllPurchases();
            purchases.forEach(System.out::println);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void updatePurchase(){

        Optional<Purchase> purchase = readPurchaseUpdate();
        purchase.ifPresent(p->{
            try {
                purchaseService.updatePurchase(p);
            }catch(SQLException e){
                e.printStackTrace();
            }
        });
    }

    private void deletePurchase(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            purchaseService.removePurchase(id);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void sortPurchases() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            System.out.println("Please enter your filters: ");
            String filters = bufferRead.readLine();
            System.out.println("Please enter the order: ");
            if (bufferRead.readLine().equals("DESC")){
                List<Purchase> purchases = purchaseService.getAllPurchases(Sort.by(Sort.Direction.DESC, filters.split(" ")));
                purchases.forEach(System.out::println);
            }
            else{
                List<Purchase> purchases = purchaseService.getAllPurchases(Sort.by(Sort.Direction.ASC, filters.split(" ")));
                purchases.forEach(System.out::println);
            }


        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    private Optional<Purchase> readPurchase() {
        System.out.println("Please enter a new purchase: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("ID client: ");
            Long idClient = Long.parseLong(bufferRead.readLine());

            System.out.println("ID book: ");
            Long idBook = Long.parseLong(bufferRead.readLine());

            System.out.println("Number of books: ");
            int nrBooks = Integer.parseInt(bufferRead.readLine());

            Purchase purchase = new Purchase(idClient, idBook, nrBooks);
            return Optional.of(purchase);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<Purchase> readPurchaseUpdate(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            Optional<Purchase> purchase = readPurchase();
            purchase.ifPresent(purchase1 -> {purchase1.setId(id);});
            return purchase;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    private void filterPurchases() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Filter: ");
        try {
            Long filter = Long.parseLong(bufferRead.readLine());
            List<Purchase> filteredPurchase = purchaseService.filterPurchasesByClientID(filter);
            filteredPurchase.forEach(System.out::println);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }




    //******************************************************************************************************************

    private void getReport(){
        try {
            //getting how many books are in stock
            long nrBooksInStock = bookService.getAll().stream()
                    .map(Book::getInStock).count();
            System.out.println("Total books in storage : " + nrBooksInStock);

            //getting how many books were sold
            long soldBooks = purchaseService.getAllPurchases().stream().
                    map(Purchase::getNrBooks).count();
            System.out.println("Number of books sold : " + soldBooks);

            //the client that bought from us more often
            //mapping clientID to how many books he/she bought
            Map<Long, Integer> clientIDtoBooksBought = purchaseService.getAllPurchases().stream()
                    .collect(Collectors.groupingBy(Purchase::getClientID, Collectors.summingInt(Purchase::getNrBooks)));

//            System.out.println();
            //getting the maximum bought books
//            clientIDtoBooksBought.entrySet().stream()
//                    .max(Comparator.comparing(Map.Entry::getValue))
//                    .ifPresent(e -> { try{
//                        System.out.println("The " + clientService.findOneClient(e.getKey()).get() + " bought the most books: " + e.getValue());
//                    }catch(SQLException se){
//                        System.out.println(se);
//                    }
//                    });
        }catch(SQLException e){
            System.out.println(e);
        }
    }



}
