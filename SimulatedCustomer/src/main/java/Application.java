public class Application {

    private static final String CUSTOM_SERVERLESS_QUEUE = "customServerlessQueue";

    public static void main(String[] args) {
        CustomerOrderSender customerOrderSender = new CustomerOrderSender();
        customerOrderSender.createQueue(CUSTOM_SERVERLESS_QUEUE);
        customerOrderSender.sendOrders(CUSTOM_SERVERLESS_QUEUE);
    }
}
