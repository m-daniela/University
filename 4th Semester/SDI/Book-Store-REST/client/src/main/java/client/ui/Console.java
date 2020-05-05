package client.ui;

import core.model.validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import web.dto.book.BookDto;
import web.dto.book.BooksDto;
import web.dto.client.ClientDto;
import web.dto.client.ClientsDto;
import web.dto.purchase.PurchaseDto;
import web.dto.purchase.PurchasesDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Console {

    @Autowired
    RestTemplate restTemplate;
    private static final String URL = "http://localhost:8080";

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
            ClientsDto clients = restTemplate.getForObject(URL + "/api/clients/filter/{name}", ClientsDto.class, name);
            System.out.println(clients);
        } catch (IOException ex) {
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
            restTemplate.delete(URL + "/api/clients/{id}", id);
        } catch (IOException | EmptyResultDataAccessException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
    }

    private void printAllClients() {
        ClientsDto clients = restTemplate.getForObject(URL + "/api/clients", ClientsDto.class);
        System.out.println(clients);
    }

    private void addClient() {
        Optional<ClientDto> client = readClient();
        client.ifPresent(c->{
            try {
                restTemplate.postForObject(URL + "/api/clients/", c, ClientDto.class);
            } catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }});
    }

    private Optional<ClientDto> readClient() {
        System.out.println("Please enter a new client: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Serial Number: ");
            String serialNumber = bufferRead.readLine();
            System.out.println("Name: ");
            String name = bufferRead.readLine();

            ClientDto client = new ClientDto(serialNumber, name);

            return Optional.of(client);
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
        return Optional.empty();
    }

    private Optional<ClientDto> readClientUpdate() {
        System.out.println("Please enter a new client: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            Optional<ClientDto> client = readClient();
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
        Optional<ClientDto> client = readClientUpdate();
        client.ifPresent(c->{
            try {
                restTemplate.put(URL + "/api/clients/", c);
            } catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }});
    }

    private void sortClients() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            System.out.println("Please enter your filters: ");
            String filters = bufferRead.readLine();
            System.out.println("Please enter the order: ");
            String direction = bufferRead.readLine();
            ClientsDto clientsDto;
            if (direction.equals("ASC") || direction.equals("DESC")){
                clientsDto = restTemplate.getForObject(URL + "/api/clients/{filters}/{direction}", ClientsDto.class, filters, direction);
            }
            else{
                clientsDto = restTemplate.getForObject(URL + "/api/clients/{filters}/{direction}", ClientsDto.class, filters, "ASC");
            }
            System.out.println(clientsDto);
        } catch (IOException e) {
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
            BooksDto clients = restTemplate.getForObject(URL + "/api/books/filter/{filter}", BooksDto.class, filter);
            System.out.println(clients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printAllBooks() {
        BooksDto books = restTemplate.getForObject(URL + "/api/books", BooksDto.class);
        System.out.println(books);
    }

    private void addBook() {
        Optional<BookDto> book = readBook();

        book.ifPresent(b->{
            try{
                restTemplate.postForObject(URL + "/api/books", b, BookDto.class);
            }
            catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void updateBook(){
        Optional<BookDto> book = readBookUpdate();
        book.ifPresent(b->{
            try{
                restTemplate.put(URL + "/api/books", b);
            }
            catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void deleteBook(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ID: ");

        try {
            Long id = Long.parseLong(bufferRead.readLine());
            restTemplate.delete(URL + "/api/books/{id}", id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private Optional<BookDto> readBook() {
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

            BookDto book = new BookDto(serialNumber, name, author, year, price, stock);

            return Optional.of(book);
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
        return Optional.empty();
    }

    private Optional<BookDto> readBookUpdate(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ID: ");
        try {
            Long id = Long.parseLong(bufferRead.readLine());
            Optional<BookDto> book = readBook();
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
            String direction = bufferRead.readLine();
            BooksDto books;
            if (direction.equals("ASC") || direction.equals("DESC")){
                books = restTemplate.getForObject(URL + "/api/books/{filters}/{direction}", BooksDto.class, filters, direction);
            }
            else{
                books = restTemplate.getForObject(URL + "/api/books/{filters}/{direction}", BooksDto.class, filters, "ASC");
            }
            System.out.println(books);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //******************************************************************************************************************
    //PURCHASES
    //******************************************************************************************************************

    private void addPurchase(){

        Optional<PurchaseDto> purchase = readPurchase();
        purchase.ifPresent(p->{
            restTemplate.postForObject(URL + "api/purchases/", p, PurchaseDto.class);

        });
    }

    private void displayPurchases(){
        PurchasesDto purchases = restTemplate.getForObject(URL + "api/purchases", PurchasesDto.class);
        System.out.println(purchases);
    }

    private void updatePurchase(){

        Optional<PurchaseDto> purchase = readPurchaseUpdate();
        purchase.ifPresent(p->{
            restTemplate.put(URL + "/api/purchases", p);
        });
    }

    private void deletePurchase(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            restTemplate.delete(URL + "/api/purchases/{id}", id);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sortPurchases() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            System.out.println("Please enter your filters: ");
            String filters = bufferRead.readLine();
            System.out.println("Please enter the order: ");
            String direction = bufferRead.readLine();
            PurchasesDto purchasesDto;
            if (direction.equals("ASC") || direction.equals("DESC")){
                purchasesDto = restTemplate.getForObject(URL + "/api/purchases/{filters}/{direction}", PurchasesDto.class, filters, direction);
            }
            else{
                purchasesDto = restTemplate.getForObject(URL + "/api/purchases/{filters}/{direction}", PurchasesDto.class, filters, "ASC");
            }
            System.out.println(purchasesDto);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Optional<PurchaseDto> readPurchase() {
        System.out.println("Please enter a new purchase: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("ID client: ");
            Long idClient = Long.parseLong(bufferRead.readLine());

            System.out.println("ID book: ");
            Long idBook = Long.parseLong(bufferRead.readLine());

            System.out.println("Number of books: ");
            int nrBooks = Integer.parseInt(bufferRead.readLine());

            PurchaseDto purchase = new PurchaseDto(idClient, idBook, nrBooks);
            return Optional.of(purchase);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<PurchaseDto> readPurchaseUpdate(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            Optional<PurchaseDto> purchase = readPurchase();
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
            PurchasesDto clients = restTemplate.getForObject(URL + "/api/purchases/filter/{name}", PurchasesDto.class, filter);
            System.out.println(clients);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    //******************************************************************************************************************

    private void getReport(){
//        try {
//            //getting how many books are in stock
//            long nrBooksInStock = bookService.getAll().stream()
//                    .map(Book::getInStock).count();
//            System.out.println("Total books in storage : " + nrBooksInStock);
//
//            //getting how many books were sold
//            long soldBooks = purchaseService.getAllPurchases().stream().
//                    map(Purchase::getNrBooks).count();
//            System.out.println("Number of books sold : " + soldBooks);
//
//            //the client that bought from us more often
//            //mapping clientID to how many books he/she bought
//            Map<Long, Integer> clientIDtoBooksBought = purchaseService.getAllPurchases().stream()
//                    .collect(Collectors.groupingBy(Purchase::getClientID, Collectors.summingInt(Purchase::getNrBooks)));
//
////            System.out.println();
//            //getting the maximum bought books
////            clientIDtoBooksBought.entrySet().stream()
////                    .max(Comparator.comparing(Map.Entry::getValue))
////                    .ifPresent(e -> { try{
////                        System.out.println("The " + clientService.findOneClient(e.getKey()).get() + " bought the most books: " + e.getValue());
////                    }catch(SQLException se){
////                        System.out.println(se);
////                    }
////                    });
//        }catch(SQLException e){
//            System.out.println(e);
//        }
    }



}
