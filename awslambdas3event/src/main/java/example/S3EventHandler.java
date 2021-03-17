/**
 * AWS Lambda Function S3 Event Java example.
 * It handles an AWS simple Lambda function that sends information to the log
 * about an object when it appears in a S3 bucket.
 */

package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;

public class S3EventHandler implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        // Get Event Record
        S3EventNotificationRecord record = s3Event.getRecords().get(0);

        // Source Bucket Name
        String srcBucketName = record.getS3().getBucket().getName();

        // Source File Name
        String srcObjectName = record.getS3().getObject().getUrlDecodedKey();

        context.getLogger().log("S3Event: " + s3Event + "\n");
        context.getLogger().log("Bucket: " + srcBucketName + "\n");
        context.getLogger().log("Object: " + srcObjectName + "\n");

        return srcBucketName + "/" + srcObjectName;
    }
}
