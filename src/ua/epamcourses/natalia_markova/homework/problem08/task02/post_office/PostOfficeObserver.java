package ua.epamcourses.natalia_markova.homework.problem08.task02.post_office;

import java.util.*;

/**
 * Created by natalia_markova on 12.06.2016.
 */

class PrintedProduct {
    private String name;

    public PrintedProduct(String name) {
        this.name = name;
    }

    public PrintedProduct(String name, int quantity) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrintedProduct that = (PrintedProduct) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}

class Publisher {

    public Publisher() {
    }

    public Map<PrintedProduct, Integer> supply(Map<String,Integer> productNames) {
        Map<PrintedProduct, Integer> products = new HashMap<>();
        for (String productName: productNames.keySet()) {
            products.put(produce(productName), productNames.get(productName));
        }
        return products;
    }

    private PrintedProduct produce(String name) {
        return new PrintedProduct(name);
    }
}

class Subscriber {
    private String name;
    private Set<PrintedProduct> products;
    private PostOffice postOffice;

    public Subscriber(String name, Set<PrintedProduct> products, PostOffice postOffice) {
        this.name = name;
        this.products = products;
        this.postOffice = postOffice;
        postOffice.addSubscriber(this);
    }

    public void receiveProducts() {
        for (PrintedProduct product: products) {
            if (postOffice.hasPrintedProduct(product)) {
                postOffice.sendPrintedProduct(product);
                System.out.println("Subscriber " + this + " received " + product);
            }
        }
    }

    public Set<PrintedProduct> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return name;
    }
}

class PostOffice {
    private Map<PrintedProduct, Integer> availableProducts = new HashMap<>();
    private Publisher publisher = new Publisher();
    private List<Subscriber> subscribers = new ArrayList<>();

    public PostOffice() {
    }

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void getAndSendPrintedProducts() {
        Map<String, Integer> order = formOrder();
        Map<PrintedProduct, Integer> receivedProducts = publisher.supply(order);
        for (PrintedProduct product : receivedProducts.keySet()) {
            int quantity = receivedProducts.get(product);
            if (availableProducts.containsKey(product)) {
                quantity += availableProducts.get(product);
            }
            availableProducts.put(product, quantity);
        }
        sendProductsToSubscribers();
    }

    public void sendProductsToSubscribers() {
        for (Subscriber subscriber: subscribers) {
            subscriber.receiveProducts();
        }
    }

    public boolean hasPrintedProduct(PrintedProduct product) {
        if (!availableProducts.containsKey(product)) {
            return false;
        }
        Integer quantity = availableProducts.get(product);
        return quantity > 0;
    }

    public void sendPrintedProduct(PrintedProduct product) {
        Integer quantity = availableProducts.get(product);
        if (quantity != null && quantity > 0) {
            availableProducts.put(product, --quantity);
        }
    }

    private Map<String, Integer> formOrder() {
        Map<String, Integer> order = new HashMap<>();
        for (Subscriber subscriber : subscribers) {
            for (PrintedProduct product: subscriber.getProducts()) {
                int quantity = 1;
                if (order.containsKey(product.getName())) {
                    quantity += order.get(product.getName());
                }
                order.put(product.getName(), quantity);
            }
        }
        for (PrintedProduct product: availableProducts.keySet()) {
            Integer quantity = order.get(product.getName());
            if (quantity == null) {
                continue;
            }
            quantity = Math.max(quantity - availableProducts.get(product), 0);
            if (quantity == 0) {
                order.remove(product.getName());
            } else {
                order.put(product.getName(), quantity);
            }
        }
        return order;
    }

}


public class PostOfficeObserver {

    public static void main(String[] args) {
        PostOffice postOffice = new PostOffice();

        Set<PrintedProduct> products1 = new HashSet<>();
        products1.add(new PrintedProduct("National Geographic"));
        products1.add(new PrintedProduct("Vogue"));
        Subscriber subscriber1 = new Subscriber("Subscriber1", products1, postOffice);

        Set<PrintedProduct> products2 = new HashSet<>();
        products2.add(new PrintedProduct("Vogue"));
        products2.add(new PrintedProduct("UT"));
        Subscriber subscriber2 = new Subscriber("Subscriber2", products2, postOffice);

        Set<PrintedProduct> products3 = new HashSet<>();
        products3.add(new PrintedProduct("National Geographic"));
        products3.add(new PrintedProduct("UT"));
        Subscriber subscriber3 = new Subscriber("Subscriber3", products3, postOffice);

        postOffice.getAndSendPrintedProducts();
        System.out.println();

        Set<PrintedProduct> products4 = new HashSet<>();
        products4.add(new PrintedProduct("Economist"));
        Subscriber subscriber4 = new Subscriber("Subscriber4", products4, postOffice);

        postOffice.getAndSendPrintedProducts();

    }

}
