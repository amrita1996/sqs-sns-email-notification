package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

public class MessagePoll implements RequestHandler<SQSEvent, String> {
    private final SnsClient snsClient;
    private static final String SERVERLESS_TOPIC = "arn:aws:sns:us-east-1:029729759146:serverless-topic";

    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        sqsEvent.getRecords().forEach(message -> publishToTopic(message.getBody(), SERVERLESS_TOPIC));
        return "Success";
    }

    public MessagePoll() {
        this.snsClient = SnsClient.builder().region(Region.US_EAST_1).build();
    }

    public void publishToTopic(String message, String topic) {
        PublishRequest request = PublishRequest.builder().message(message).topicArn(topic).build();
        PublishResponse result = snsClient.publish(request);
        System.out.println(result);
    }
}
