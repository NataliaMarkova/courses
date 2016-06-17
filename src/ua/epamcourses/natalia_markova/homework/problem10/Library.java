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
    private Set<Book> books;
    private Library library;

    public Reader(@NotNull String name, Library library) {
        this.name = name;
        this.library = library;
        books = new HashSet<>();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Book book = library.getBook("");
                books.add(book);
                Thread.sleep(50);

            } catch (InterruptedException e) {
                for (Book book: books) {
                    library.returnBook(book);
                }
                return;
            } catch (NoSuchBookException e) {

            }
        }
    }

}

class NoSuchBookException extends Exception {
    public NoSuchBookException() {
    }
}

public class Library {

    private Set<Book> books;
    private Map<String, Book> availableBooks;
    private Set<Reader> readers;

    public Library(Set<Book> books) {
        this.books = books;
        availableBooks = new HashMap<>();
        for (Book book: books) {
            availableBooks.put(book.getName(), book);
        }
    }

    public Book getBook(String name) throws InterruptedException, NoSuchBookException {
        Book book = null;
        if (!books.contains(new Book(name))) {
            throw new NoSuchBookException();
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
}
