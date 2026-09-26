import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

// ======================================================
// ENUMS
// ======================================================

enum ServiceType {
WASH(50),
DRY_CLEAN(120),
IRON(30),
WASH_AND_IRON(70);

private final double price;

ServiceType(double price) {
        this.price = price;
}

public double getPrice() {
        return price;
}
}

enum OrderStatus {
RECEIVED,
WASHING,
READY,
DELIVERED
}

// ======================================================
// CUSTOMER CLASS
// ======================================================

class Customer {
private int customerId;
private String name;
private String phone;
private String address;

public Customer(int customerId, String name,
                String phone, String address) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
}

public int getCustomerId() {
        return customerId;
}

public String getName() {
        return name;
}

public String getPhone() {
        return phone;
}

public String getAddress() {
        return address;
}

public void setName(String name) {
        this.name = name;
}

public void setPhone(String phone) {
        this.phone = phone;
}

public void setAddress(String address) {
        this.address = address;
}

@Override
public String toString() {
        return "ID: " + customerId +
                ", Name: " + name +
                ", Phone: " + phone +
                ", Address: " + address;
}
}

// ======================================================
// GARMENT CLASS
// ======================================================

class Garment {
private String type;
private int quantity;
private ServiceType service;

public Garment(String type, int quantity,
                ServiceType service) {
        if (quantity <= 0) {
        throw new IllegalArgumentException(
                "Quantity must be greater than 0."
        );
        }

        this.type = type;
        this.quantity = quantity;
        this.service = service;
}

public String getType() {
        return type;
}

public int getQuantity() {
        return quantity;
}

public ServiceType getService() {
        return service;
}
public double calculateCharge() {
        return quantity * service.getPrice();
}

@Override
public String toString() {
        return type + " x " + quantity +
                " [" + service + "] = Rs." +
                calculateCharge();
}
}

// ======================================================
// BILL CLASS
// ======================================================

class Bill {
private double subtotal;
private double gst;
private double total;

public Bill(double subtotal) {
        this.subtotal = subtotal;
        this.gst = subtotal * 0.05;
        this.total = subtotal + gst;
}

public double getSubtotal() {
        return subtotal;
}

public double getGst() {
        return gst;
}

public double getTotal() {
        return total;
}
}

// ======================================================
// LAUNDRY ORDER CLASS
// ======================================================

class LaundryOrder {
private int orderId;
private Customer customer;
private ArrayList<Garment> garments;

private LocalDate orderDate;
private LocalDate dueDate;

private OrderStatus status;

    // LinkedList for status history
private LinkedList<String> statusHistory;

public LaundryOrder(int orderId,
                        Customer customer,
                        ArrayList<Garment> garments,
                        LocalDate dueDate) {

        if (dueDate.isBefore(LocalDate.now())) {
        throw new IllegalArgumentException(
                "Due date cannot be in the past."
                );
        }

        this.orderId = orderId;
        this.customer = customer;
        this.garments = garments;

        this.orderDate = LocalDate.now();
        this.dueDate = dueDate;

        this.status = OrderStatus.RECEIVED;

        statusHistory = new LinkedList<>();

        statusHistory.add(
                LocalDate.now() + " - RECEIVED"
        );
}

public int getOrderId() {
        return orderId;
}

public Customer getCustomer() {
        return customer;
}

public ArrayList<Garment> getGarments() {
        return garments;
}

 public LocalDate getOrderDate() {
        return orderDate;
}

    public LocalDate getDueDate() {
        return dueDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LinkedList<String> getStatusHistory() {
        return statusHistory;
    }

    public void updateStatus(OrderStatus newStatus) {
        status = newStatus;

        statusHistory.add(
                LocalDate.now() +
                        " - Status changed to " +
                        newStatus
        );
    }

    public double calculateSubtotal() {
        double total = 0;

        for (Garment garment : garments) {
            total += garment.calculateCharge();
        }

        return total;
    }

    public Bill generateBill() {
        return new Bill(calculateSubtotal());
    }
}

// ======================================================
// MAIN CLASS
// ======================================================

public class LaundryServiceManagementSystem {

    static Scanner sc = new Scanner(System.in);

    // ArrayList -> Customers
    static ArrayList<Customer> customers =
            new ArrayList<>();

    // HashMap -> Order ID -> Order
    static HashMap<Integer, LaundryOrder> orders =
            new HashMap<>();

    // TreeMap -> Orders according to due date
    static TreeMap<LocalDate, ArrayList<LaundryOrder>>
            ordersByDate = new TreeMap<>();

    // Array -> Garment types
    static String[] garmentTypes = {
            "Shirt",
            "T-Shirt",
            "Pant",
            "Jeans",
            "Dress",
            "Jacket",
            "Saree",
            "Bedsheet",
            "Curtain"
    };

    static int nextCustomerId = 1;
    static int nextOrderId = 1001;

    // ======================================================
    // MAIN
    // ======================================================

    public static void main(String[] args) {

        int choice;

        do {
            showMainMenu();

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        addCustomer();
                        break;

                    case 2:
                        viewCustomers();
                        break;

                    case 3:
                        updateCustomer();
                        break;

                    case 4:
                        deleteCustomer();
                        break;

                    case 5:
                        createOrder();
                        break;

                    case 6:
                        viewOrders();
                        break;

                    case 7:
                        searchOrder();
                        break;

                    case 8:
                        updateOrderStatus();
                        break;

                    case 9:
                        showOrderHistory();
                        break;

                    case 10:
                        generateBill();
                        break;

                    case 11:
                        sortOrders();
                        break;

                    case 12:
                        generateReport();
                        break;

                    case 0:
                        System.out.println(
                                "\nThank you for using Laundry Service Management System!"
                        );
                        break;

                    default:
                        System.out.println(
                                "\nInvalid choice."
                        );
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "\nPlease enter numbers only."
                );

                choice = -1;
            }

        } while (choice != 0);
    }

    // ======================================================
    // MAIN MENU
    // ======================================================

    static void showMainMenu() {

        System.out.println(
                "\n=========================================="
        );

        System.out.println(
                "    LAUNDRY SERVICE MANAGEMENT SYSTEM"
        );

        System.out.println(
                "=========================================="
        );

        System.out.println("1. Add Customer");
        System.out.println("2. View Customers");
        System.out.println("3. Update Customer");
        System.out.println("4. Delete Customer");

        System.out.println("5. Create Laundry Order");
        System.out.println("6. View All Orders");
        System.out.println("7. Search Order");
        System.out.println("8. Update Order Status");

        System.out.println("9. View Order History");
        System.out.println("10. Generate Bill");
        System.out.println("11. Sort Orders by Due Date");
        System.out.println("12. Generate Report");

        System.out.println("0. Exit");

        System.out.println(
                "=========================================="
        );
    }

    // ======================================================
    // ADD CUSTOMER - CREATE
    // ======================================================

    static void addCustomer() {

        System.out.println("\n--- ADD CUSTOMER ---");

        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine().trim();

        System.out.print("Enter Address: ");
        String address = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        if (!phone.matches("\\d{10}")) {
            System.out.println(
                    "Phone number must contain exactly 10 digits."
            );
            return;
        }

        if (address.isEmpty()) {
            System.out.println(
                    "Address cannot be empty."
            );
            return;
        }

        Customer customer =
                new Customer(
                        nextCustomerId,
                        name,
                        phone,
                        address
                );

        customers.add(customer);

        System.out.println(
                "\nCustomer registered successfully!"
        );

        System.out.println(
                "Customer ID: " + nextCustomerId
        );

        nextCustomerId++;
    }

    // ======================================================
    // VIEW CUSTOMERS - READ
    // ======================================================

    static void viewCustomers() {

        System.out.println("\n--- CUSTOMER LIST ---");

        if (customers.isEmpty()) {
            System.out.println(
                    "No customers registered."
            );
            return;
        }

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    // ======================================================
    // UPDATE CUSTOMER
    // ======================================================

    static void updateCustomer() {

        System.out.print(
                "\nEnter Customer ID: "
        );

        try {
            int id =
                    Integer.parseInt(sc.nextLine());

            Customer customer =
                    findCustomer(id);

            if (customer == null) {
                System.out.println(
                        "Customer not found."
                );
                return;
            }

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Phone: ");
            String phone = sc.nextLine();

            System.out.print("Enter New Address: ");
            String address = sc.nextLine();

            if (!phone.matches("\\d{10}")) {
                System.out.println(
                        "Invalid phone number."
                );
                return;
            }

            customer.setName(name);
            customer.setPhone(phone);
            customer.setAddress(address);

            System.out.println(
                    "Customer updated successfully."
            );

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid Customer ID."
            );
        }
    }

    // ======================================================
    // DELETE CUSTOMER
    // ======================================================

    static void deleteCustomer() {

        System.out.print(
                "\nEnter Customer ID to delete: "
        );

        try {
            int id =
                    Integer.parseInt(sc.nextLine());

            boolean removed =
                    customers.removeIf(
                            customer ->
                                    customer.getCustomerId()
                                            == id
                    );

            if (removed) {
                System.out.println(
                        "Customer deleted successfully."
                );
            } else {
                System.out.println(
                        "Customer not found."
                );
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid Customer ID."
            );
        }
    }

    // ======================================================
    // SEARCH CUSTOMER
    // ======================================================

    static Customer findCustomer(int id) {

        for (Customer customer : customers) {

            if (customer.getCustomerId() == id) {
                return customer;
            }
        }

        return null;
    }

    // ======================================================
    // CREATE ORDER
    // ======================================================

    static void createOrder() {

        if (customers.isEmpty()) {
            System.out.println(
                    "\nRegister a customer first."
            );
            return;
        }

        viewCustomers();

        try {

            System.out.print(
                    "\nEnter Customer ID: "
            );

            int customerId =
                    Integer.parseInt(sc.nextLine());

            Customer customer =
                    findCustomer(customerId);

            if (customer == null) {
                System.out.println(
                        "Customer not found."
                );
                return;
            }

            ArrayList<Garment> garments =
                    new ArrayList<>();

            System.out.println(
                    "\nAvailable Garments:"
            );

            for (int i = 0;
                 i < garmentTypes.length;
                 i++) {

                System.out.println(
                        (i + 1) + ". " +
                                garmentTypes[i]
                );
            }

            System.out.print(
                    "Select Garment: "
            );

            int garmentChoice =
                    Integer.parseInt(sc.nextLine());

            if (garmentChoice < 1 ||
                    garmentChoice >
                            garmentTypes.length) {

                System.out.println(
                        "Invalid garment."
                );

                return;
            }

            System.out.println(
                    "\nAvailable Services:"
            );

            ServiceType[] services =
                    ServiceType.values();

            for (int i = 0;
                 i < services.length;
                 i++) {

                System.out.println(
                        (i + 1) + ". " +
                                services[i] +
                                " - Rs." +
                                services[i].getPrice()
                );
            }

            System.out.print(
                    "Select Service: "
            );

            int serviceChoice =
                    Integer.parseInt(sc.nextLine());

            if (serviceChoice < 1 ||
                    serviceChoice >
                            services.length) {

                System.out.println(
                        "Invalid service."
                );

                return;
            }

            System.out.print(
                    "Enter Quantity: "
            );

            int quantity =
                    Integer.parseInt(sc.nextLine());

            Garment garment =
                    new Garment(
                            garmentTypes[
                                    garmentChoice - 1
                            ],
                            quantity,
                            services[
                                    serviceChoice - 1
                            ]
                    );

            garments.add(garment);

            System.out.print(
                    "Enter Due Date (YYYY-MM-DD): "
            );

            LocalDate dueDate =
                    LocalDate.parse(
                            sc.nextLine()
                    );

            LaundryOrder order =
                    new LaundryOrder(
                            nextOrderId,
                            customer,
                            garments,
                            dueDate
                    );

            // HashMap
            orders.put(
                    nextOrderId,
                    order
            );

            // TreeMap
            ordersByDate
                    .computeIfAbsent(
                            dueDate,
                            k -> new ArrayList<>()
                    )
                    .add(order);

            System.out.println(
                    "\nOrder created successfully!"
            );

            System.out.println(
                    "Order ID: " + nextOrderId
            );

            System.out.println(
                    "Customer: " +
                            customer.getName()
            );

            System.out.println(
                    "Status: " +
                            order.getStatus()
            );

            nextOrderId++;

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter valid numbers."
            );

        } catch (DateTimeParseException e) {

            System.out.println(
                    "Invalid date. Use YYYY-MM-DD."
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    e.getMessage()
            );
        }
    }

    // ======================================================
    // VIEW ORDERS
    // ======================================================

    static void viewOrders() {

        if (orders.isEmpty()) {
            System.out.println(
                    "\nNo orders available."
            );
            return;
        }

        System.out.println(
                "\n========== ALL ORDERS =========="
        );

        for (LaundryOrder order :
                orders.values()) {

            displayOrder(order);
        }
    }

    // ======================================================
    // DISPLAY ORDER
    // ======================================================

    static void displayOrder(
            LaundryOrder order
    ) {

        System.out.println(
                "\nOrder ID: " +
                        order.getOrderId()
        );

        System.out.println(
                "Customer: " +
                        order.getCustomer().getName()
        );

        System.out.println(
                "Order Date: " +
                        order.getOrderDate()
        );

        System.out.println(
                "Due Date: " +
                        order.getDueDate()
        );

        System.out.println(
                "Status: " +
                        order.getStatus()
        );

        System.out.println("Garments:");

        for (Garment garment :
                order.getGarments()) {

            System.out.println(
                    "  " + garment
            );
        }

        System.out.println(
                "--------------------------------"
        );
    }

    // ======================================================
    // SEARCH ORDER
    // ======================================================

    static void searchOrder() {

        System.out.print(
                "\nEnter Order ID: "
        );

        try {

            int id =
                    Integer.parseInt(sc.nextLine());

            // HashMap search
            LaundryOrder order =
                    orders.get(id);

            if (order == null) {
                System.out.println(
                        "Order not found."
                );
                return;
            }

            System.out.println(
                    "\nOrder Found!"
            );

            displayOrder(order);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid Order ID."
            );
        }
    }

    // ======================================================
    // UPDATE ORDER STATUS
    // ======================================================

    static void updateOrderStatus() {

        System.out.print(
                "\nEnter Order ID: "
        );

        try {

            int id =
                    Integer.parseInt(sc.nextLine());

            LaundryOrder order =
                    orders.get(id);

            if (order == null) {
                System.out.println(
                        "Order not found."
                );
                return;
            }

            System.out.println(
                    "\n1. RECEIVED"
            );

            System.out.println(
                    "2. WASHING"
            );

            System.out.println(
                    "3. READY"
            );

            System.out.println(
                    "4. DELIVERED"
            );

            System.out.print(
                    "Select New Status: "
            );

            int choice =
                    Integer.parseInt(sc.nextLine());

            OrderStatus newStatus;

            switch (choice) {

                case 1:
                    newStatus =
                            OrderStatus.RECEIVED;
                    break;

                case 2:
                    newStatus =
                            OrderStatus.WASHING;
                    break;

                case 3:
                    newStatus =
                            OrderStatus.READY;
                    break;

                case 4:
                    newStatus =
                            OrderStatus.DELIVERED;
                    break;

                default:
                    System.out.println(
                            "Invalid status."
                    );
                    return;
            }

            order.updateStatus(newStatus);

            System.out.println(
                    "Order status updated to " +
                            newStatus
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid input."
            );
        }
    }

    // ======================================================
    // ORDER HISTORY
    // ======================================================

    static void showOrderHistory() {

        System.out.print(
                "\nEnter Order ID: "
        );

        try {

            int id =
                    Integer.parseInt(sc.nextLine());

            LaundryOrder order =
                    orders.get(id);

            if (order == null) {
                System.out.println(
                        "Order not found."
                );
                return;
            }

            System.out.println(
                    "\n===== ORDER HISTORY ====="
            );

            for (String history :
                    order.getStatusHistory()) {

                System.out.println(history);
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid Order ID."
            );
        }
    }

    // ======================================================
    // BILL
    // ======================================================

    static void generateBill() {

        System.out.print(
                "\nEnter Order ID: "
        );

        try {

            int id =
                    Integer.parseInt(sc.nextLine());

            LaundryOrder order =
                    orders.get(id);

            if (order == null) {
                System.out.println(
                        "Order not found."
                );
                return;
            }

            Bill bill =
                    order.generateBill();

            System.out.println(
                    "\n================================"
            );

            System.out.println(
                    "         LAUNDRY BILL"
            );

            System.out.println(
                    "================================"
            );

            System.out.println(
                    "Order ID : " +
                            order.getOrderId()
            );

            System.out.println(
                    "Customer : " +
                            order.getCustomer().getName()
            );

            System.out.println(
                    "Phone    : " +
                            order.getCustomer().getPhone()
            );

            System.out.println(
                    "--------------------------------"
            );

            for (Garment garment :
                    order.getGarments()) {

                System.out.println(garment);
            }

            System.out.println(
                    "--------------------------------"
            );

            System.out.printf(
                    "Subtotal : Rs.%.2f%n",
                    bill.getSubtotal()
            );

            System.out.printf(
                    "GST (5%%) : Rs.%.2f%n",
                    bill.getGst()
            );

            System.out.printf(
                    "TOTAL    : Rs.%.2f%n",
                    bill.getTotal()
            );

            System.out.println(
                    "================================"
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid Order ID."
            );
        }
    }

    // ======================================================
    // SORT ORDERS
    // ======================================================

    static void sortOrders() {

        if (orders.isEmpty()) {
            System.out.println(
                    "\nNo orders available."
            );
            return;
        }

        ArrayList<LaundryOrder> sortedOrders =
                new ArrayList<>(
                        orders.values()
                );

        sortedOrders.sort(
                Comparator.comparing(
                        LaundryOrder::getDueDate
                )
        );

        System.out.println(
                "\n===== ORDERS SORTED BY DUE DATE ====="
        );

        for (LaundryOrder order :
                sortedOrders) {

            System.out.println(
                    "Order ID: " +
                            order.getOrderId() +
                            " | Customer: " +
                            order.getCustomer().getName() +
                            " | Due: " +
                            order.getDueDate() +
                            " | Status: " +
                            order.getStatus()
            );
        }
    }

    // ======================================================
    // REPORT
    // ======================================================

    static void generateReport() {

        double totalRevenue = 0;

        int received = 0;
        int washing = 0;
        int ready = 0;
        int delivered = 0;

        for (LaundryOrder order :
                orders.values()) {

            totalRevenue +=
                    order.generateBill()
                            .getTotal();

            switch (order.getStatus()) {

                case RECEIVED:
                    received++;
                    break;

                case WASHING:
                    washing++;
                    break;

                case READY:
                    ready++;
                    break;

                case DELIVERED:
                    delivered++;
                    break;
            }
        }

        System.out.println(
                "\n================================"
        );

        System.out.println(
                "       LAUNDRY REPORT"
        );

        System.out.println(
                "================================"
        );

        System.out.println(
                "Total Customers : " +
                        customers.size()
        );

        System.out.println(
                "Total Orders    : " +
                        orders.size()
        );

        System.out.println(
                "Received        : " +
                        received
        );

        System.out.println(
                "Washing         : " +
                        washing
        );

        System.out.println(
                "Ready           : " +
                        ready
        );

        System.out.println(
                "Delivered       : " +
                        delivered
        );

        System.out.printf(
                "Total Revenue   : Rs.%.2f%n",
                totalRevenue
        );

        System.out.println(
                "================================"
        );
    }
}


