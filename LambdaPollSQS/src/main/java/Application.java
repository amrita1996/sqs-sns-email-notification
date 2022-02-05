import org.example.MessagePoll;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

public class Application {
    private static final String SERVERLESS_TOPIC = "arn:aws:sns:us-east-1:029729759146:serverless-topic";


    public static void main(String[] args) {
        subscribeToTopicEmail("am242705@dal.ca", SERVERLESS_TOPIC);
    }

    private static void subscribeToTopicEmail(String email, String topic) {
        SnsClient snsClient = SnsClient.builder()
                .region(Region.US_EAST_1)
                .build();
        SubscribeRequest request = SubscribeRequest.builder()
                .protocol("email")
                .endpoint(email)
                .returnSubscriptionArn(true)
                .topicArn(topic)
                .build();

        SubscribeResponse result = snsClient.subscribe(request);
        System.out.println("Subscription ARN: " + result.subscriptionArn());
    }
}
