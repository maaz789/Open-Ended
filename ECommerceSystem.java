
class Inventory {
    private int stock;

    public Inventory(int stock) {
        this.stock = stock;
    }

    // Synchronized method to process orders
    public synchronized boolean processOrder(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            System.out.println(Thread.currentThread().getName() + " processed order of " + quantity + " items. Remaining stock: " + stock);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " failed to process order of " + quantity + " items. Insufficient stock!");
            return false;
        }
    }
}

class Customer extends Thread {
    private final Inventory inventory;
    private final int quantity;

    public Customer(Inventory inventory, String name, int quantity) {
        super(name);
        this.inventory = inventory;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        boolean success = inventory.processOrder(quantity);
        if (success) {
            System.out.println(getName() + " successfully placed an order for " + quantity + " items.");
        } else {
            System.out.println(getName() + " could not place an order for " + quantity + " items.");
        }
        try {
            Thread.sleep(300); // Simulate delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ECommerceSystem {
    public static void main(String[] args) {
        Inventory inventory = new Inventory(50); // Initial stock of 50 items

        Customer customer1 = new Customer(inventory, "Customer-1", 10);
        Customer customer2 = new Customer(inventory, "Customer-2", 20);
        Customer customer3 = new Customer(inventory, "Customer-3", 30);

        customer1.start();
        customer2.start();
        customer3.start();
    }
}
