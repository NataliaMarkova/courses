package ua.epamcourses.natalia_markova.homework.problem10;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Created by natalia_markova on 16.06.2016.
 */

class Book {

    private String name;
    private boolean readingRoom;

    public Book(@NotNull String name) {
        this.name = name;
    }

    public Book(@NotNull String name, boolean readingRoom) {
        this.name = name;
        this.readingRoom = readingRoom;
    }

    public String getName() {
        return name;
    }

    public boolean readingRoom() {
        return readingRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return name.equals(book.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", readingRoom=" + readingRoom +
                '}';
    }
}

class Reader extends Thread {
    private String name;
    private Queue<Book> books;
    private Library library;

    public Reader(@NotNull String name, Library library) {
        this.name = name;
        this.library = library;
        books = new LinkedList<>();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                int probability = getRandomNumber(1, 100);
                if (books.size() == 0 || probability <= 50) {
                    int number = getRandomNumber(1, 150);
                    Book book = library.getBook("Book" + (number));
                    books.add(book);
                    System.out.println("Reader " + name + " took the book " + book.getName() + (book.readingRoom() ? " to read in the reading-room" : ""));
                } else {
                    Book book = books.remove();
                    library.returnBook(book);
                    System.out.println("Reader " + name + " returned the book " + book.getName() + (book.readingRoom() ? " from the reading-room" : ""));
                }
                Thread.sleep(50);

            } catch (InterruptedException e) {
                for (Book book: books) {
                    if (book.readingRoom()) {
                        library.returnBook(book);
                    }
                }
                return;
            } catch (NoSuchBookException e) {
                System.out.println("There is no such book in the library: " + e.getMessage());
            }
        }
    }

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}

class NoSuchBookException extends Exception {
    public NoSuchBookException() {
    }

    public NoSuchBookException(String message) {
        super(message);
    }
}

public class Library {

    private Set<Book> books;
    private Map<String, Book> availableBooks;
    private Set<Reader> readers = new HashSet<>();

    public Library(Set<Book> books) {
        this.books = books;
        availableBooks = new HashMap<>();
        for (Book book: books) {
            availableBooks.put(book.getName(), book);
        }
    }

    public void acceptReader(String name) {
        Reader reader = new Reader(name, this);
        if (!readers.contains(reader)) {
            readers.add(reader);
            reader.start();
        }
    }

    public Book getBook(String name) throws InterruptedException, NoSuchBookException {
        Book book = null;
        if (!books.contains(new Book(name))) {
            throw new NoSuchBookException(name);
        }
        synchronized (availableBooks) {
            while (availableBooks.get(name) == null) {
                availableBooks.wait();
            }
            book = availableBooks.get(name);

        }
        return book;
    }

    public void returnBook(Book book) {
        synchronized (availableBooks) {
            availableBooks.put(book.getName(), book);
            availableBooks.notifyAll();
        }
    }

    public void close() {
        for (Reader reader: readers) {
            reader.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Set<Book> books = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            books.add(new Book("Book" + i, i%3 == 0));
        }
        Library library = new Library(books);
        for (int i = 0; i < 10; i++) {
            library.acceptReader("Reader" + i);
        }
        Thread.sleep(1000);
        library.close();

    }
}
