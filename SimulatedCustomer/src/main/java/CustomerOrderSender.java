import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerOrderSender {
    private static final List<String> messages = Arrays.asList("Send a dozen lillies to Ruben","Send half a dozen roses to Jacob", "Send 2 dozen Orchids to Steffi");
    private SqsClient sqsClient;
    public CustomerOrderSender() {
        this.sqsClient = SqsClient.builder().region(Region.US_EAST_1).build();
    }

    public void sendOrders(String queue) {
        final int i = ThreadLocalRandom.current().nextInt(0, 3);
        String message = messages.get(i);
        String queueUrl = queueUrl(queue);
        sqsClient.sendMessage(SendMessageRequest.builder().queueUrl(queueUrl).messageBody(message).delaySeconds(1).build());
    }

    private String queueUrl(String queue) {
        GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queue).build());
        String queueUrl = getQueueUrlResponse.queueUrl();
        return queueUrl;
    }

    public void createQueue(String queue) {
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(queue)
                .build();
        sqsClient.createQueue(createQueueRequest);
    }
}
