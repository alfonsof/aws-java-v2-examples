/**
 * S3List is an example that handles S3 buckets on AWS.
 * List information about the objects in a S3 bucket.
 * You must provide 1 parameter:
 * BUCKET_NAME     = Bucket name
 */

package example;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

public class S3List {
    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) throws IOException {

        String bucketName;  // Bucket name

        if (args.length < 1) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar s3list.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // The name for the bucket
        bucketName = args[0];

        System.out.println("Bucket name: " + bucketName);

        // Instantiates a client
        S3Client s3client = S3Client.builder()
                .region(REGION)
                .build();

        try {
            System.out.println("Listing objects ...");
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse res = s3client.listObjects(listObjects);
            List<S3Object> objects = res.contents();

            for (ListIterator iterVals = objects.listIterator(); iterVals.hasNext(); ) {
                S3Object myValue = (S3Object) iterVals.next();
                System.out.println(" - " + myValue.key() +
                        " (size = " + myValue.size() + " bytes)" +
                        " (owner = " + myValue.owner());
            }
            System.out.println("Listed");
            s3client.close();

        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                System.out.println("Error: Bucket does not exist!!");
            } else {
                System.out.println("S3Exception: " + e);
                System.out.println("HTTP Status Code:  " + e.statusCode());
            }
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, " +
                    "which means your request made it " +
                    "to Amazon S3, but was rejected with an error response " +
                    "for some reason.");
            System.out.println("Error Message:     " + ase.getMessage());
            System.out.println("HTTP Status Code:  " + ase.statusCode());
            System.out.println("AWS Service Name:  " + ase.awsErrorDetails().serviceName());
            System.out.println("AWS Error Code:    " + ase.awsErrorDetails().errorCode());
            System.out.println("AWS Error Message: " + ase.awsErrorDetails().errorMessage());
            System.out.println("Request ID:        " + ase.requestId());
        } catch (SdkException ace) {
            System.out.println("Caught an AmazonClientException, " +
                    "which means the client encountered " +
                    "an internal error while trying to communicate" +
                    " with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}
