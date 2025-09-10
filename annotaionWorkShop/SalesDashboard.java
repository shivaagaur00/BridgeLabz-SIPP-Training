package annotaionWorkShop;
import java.lang.annotation.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface ImportantCustomer {
    String note() default "VIP Customer";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface DiscountApplicable {
    int minOrders();
    double minValue();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface CategoryInfo {
    String name();
    String description();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Trackable {
}

@ImportantCustomer
@DiscountApplicable(minOrders = 5, minValue = 2000)
class Customer {
    private String name;
    private int totalOrders;
    private double totalValue;

    public Customer(String name, int totalOrders, double totalValue) {
        this.name = name;
        this.totalOrders = totalOrders;
        this.totalValue = totalValue;
    }

    public String getName() { return name; }
    public int getTotalOrders() { return totalOrders; }
    public double getTotalValue() { return totalValue; }
}


















@Trackable
@CategoryInfo(name = "Electronics", description = "Devices, gadgets, and appliances")
class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}

@Trackable
class Order {
    private Customer customer;
    private Item item;

    public Order(Customer customer, Item item) {
        this.customer = customer;
        this.item = item;
    }

    public Customer getCustomer() { return customer; }
    public Item getItem() { return item; }
}

class ReflectionUtil {
    public static void scanClasses(List<Class<?>> classes) {
        System.out.println("Scanning classes for @Trackable, @CategoryInfo, and @DiscountApplicable...\n");

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Trackable.class)) {
                System.out.println("Trackable class: " + clazz.getSimpleName());
            }

            if (clazz.isAnnotationPresent(CategoryInfo.class)) {
                CategoryInfo info = clazz.getAnnotation(CategoryInfo.class);
                System.out.println("Category: " + info.name() + " - " + info.description());
            }

            if (clazz.isAnnotationPresent(DiscountApplicable.class)) {
                DiscountApplicable disc = clazz.getAnnotation(DiscountApplicable.class);
                System.out.println("Discount Rule -> Min Orders: " + disc.minOrders()
                        + ", Min Value: " + disc.minValue());
            }
        }
    }

    public static boolean qualifiesForDiscount(Customer c) {
        if (c.getClass().isAnnotationPresent(DiscountApplicable.class)) {
            DiscountApplicable disc = c.getClass().getAnnotation(DiscountApplicable.class);
            return c.getTotalOrders() >= disc.minOrders() && c.getTotalValue() >= disc.minValue();
        }
        return false;
    }

    public static boolean isVIP(Customer c) {
        return c.getClass().isAnnotationPresent(ImportantCustomer.class);
    }
}

public class SalesDashboard {
    public static void main(String[] args) {
        Customer c1 = new Customer("Alice", 6, 3000);
        Customer c2 = new Customer("Bob", 2, 500);
        Item i1 = new Item("Laptop", 1000);
        Item i2 = new Item("Smartphone", 700);

        List<Order> orders = List.of(
                new Order(c1, i1),
                new Order(c1, i2),
                new Order(c2, i2)
        );

        ReflectionUtil.scanClasses(List.of(Customer.class, Item.class, Order.class));

        System.out.println("\n===== Top Affordable Picks =====\n");

        orders.stream()
                .sorted(Comparator.comparingDouble(o -> o.getItem().getPrice()))
                .forEach(order -> {
                    Customer cust = order.getCustomer();
                    Item item = order.getItem();

                    String vipTag = ReflectionUtil.isVIP(cust) ? "[VIP]" : "";

                    boolean discount = ReflectionUtil.qualifiesForDiscount(cust);
                    double finalPrice = discount ? item.getPrice() * 0.9 : item.getPrice();

                    System.out.println(cust.getName() + " " + vipTag +
                            " -> " + item.getName() +
                            " | Price: " + item.getPrice() +
                            (discount ? " | Discounted: " + finalPrice : ""));
                });
    }
}
