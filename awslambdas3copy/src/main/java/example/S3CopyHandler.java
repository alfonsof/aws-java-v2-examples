/**
 * AWS Lambda Function S3 Copy Java example.
 * It handles an AWS Lambda function that copies an object
 * when it appears in a S3 bucket to another S3 bucket.
 */

package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;

public class S3CopyHandler implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        String sourceBucketName;              // Source bucket name
        String sourceKeyName;                 // Source key name
        String destinationBucketName;         // Destination bucket name
        String destinationKeyName;            // Destination key name

        LambdaLogger logger = context.getLogger();

        // Get Event Record
        S3EventNotificationRecord record = s3Event.getRecords().get(0);

        // Source Bucket Name
        sourceBucketName = record.getS3().getBucket().getName();

        // Source Object Name
        sourceKeyName = record.getS3().getObject().getKey(); // Name doesn't contain any special characters

        // Destination Bucket Name
        destinationBucketName = System.getenv("TARGET_BUCKET");

        if (destinationBucketName == null || destinationBucketName.isEmpty()) {
            logger.log("Error: TARGET_BUCKET Lambda environment variable does not exist!!");
            System.exit(1);
        }

        // Destination File Name
        destinationKeyName = sourceKeyName;

        logger.log("S3Event: " + s3Event);
        logger.log("Source Bucket: " + sourceBucketName + "\n");
        logger.log("Source Object: " + sourceKeyName + "\n");
        logger.log("Target Bucket: " + destinationBucketName + "\n");
        logger.log("Target Object: " + destinationKeyName + "\n");

        // Instantiates a client
        S3Client s3client = S3Client.builder()
                .build();

        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(sourceBucketName + "/" + sourceKeyName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            logger.log("URL could not be encoded: " + e.getMessage());
        }

        try {
            logger.log("Copying object ...");

            // Copy object
            CopyObjectRequest copyReq = CopyObjectRequest.builder()
                    .copySource(encodedUrl)
                    .destinationBucket(destinationBucketName)
                    .destinationKey(destinationKeyName)
                    .build();

            CopyObjectResponse copyRes = s3client.copyObject(copyReq);

            logger.log("Copied");

        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                logger.log("Error: Bucket/Object \"" + sourceBucketName + "/" +
                        sourceKeyName + "\" or Bucket \"" + destinationBucketName + "\" do not exist!!");
                logger.log("S3Exception: " + e);
            } else {
                logger.log("S3Exception: " + e);
                logger.log("HTTP Status Code:  " + e.statusCode());
            }
        } catch (AwsServiceException ase) {
            logger.log("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            logger.log("Error Message:     " + ase.getMessage());
            logger.log("HTTP Status Code:  " + ase.statusCode());
            logger.log("AWS Service Name:  " + ase.awsErrorDetails().serviceName());
            logger.log("AWS Error Code:    " + ase.awsErrorDetails().errorCode());
            logger.log("AWS Error Message: " + ase.awsErrorDetails().errorMessage());
            logger.log("Request ID:        " + ase.requestId());
        } catch (SdkException ace) {
            logger.log("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            logger.log("Error Message: " + ace.getMessage());
        }
        s3client.close();
        return destinationBucketName + "/" + destinationKeyName;
    }
}