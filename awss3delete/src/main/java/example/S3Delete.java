/**
 * S3Delete is an example that handles S3 buckets on AWS.
 * Delete a S3 bucket.
 * You must provide 1 parameter:
 * BUCKET_NAME = Name of the bucket
 */

package example;

import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;


public class S3Delete {
    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Not enough parameters.\n" +
                    "Proper Usage is: java -jar s3delete.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // The name for the bucket
        String bucketName = args[0];

        System.out.println("Bucket name: " + bucketName);

        // Instantiates a client
        S3Client s3client = S3Client.builder()
                .region(REGION)
                .build();

        try {
            System.out.println("Deleting bucket ...");

            // Delete bucket
            DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
            s3client.deleteBucket(deleteBucketRequest);
            
            System.out.println("Deleted");

        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                System.out.println("Error: Bucket does not exist!!");
            }else if (e.statusCode() == 409) {
                System.out.println("Error: The bucket you tried to delete is not empty!!");
            } else {
                System.out.println("S3Exception: " + e);
                System.out.println("HTTP Status Code:  " + e.statusCode());
            }
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AwsServiceException, " +
                    "which means your request made it " +
                    "to AWS S3, but was rejected with an error " +
                    "response for some reason.");
            System.out.println("Error Message:     " + ase.getMessage());
            System.out.println("HTTP Status Code:  " + ase.statusCode());
            System.out.println("AWS Service Name:  " + ase.awsErrorDetails().serviceName());
            System.out.println("AWS Error Code:    " + ase.awsErrorDetails().errorCode());
            System.out.println("AWS Error Message: " + ase.awsErrorDetails().errorMessage());
            System.out.println("Request ID:        " + ase.requestId());
        } catch (SdkException se) {
            System.out.println("Caught an SdkException, " +
                    "which means the client encountered " +
                    "an internal error while trying to " +
                    " communicate with AWS S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + se.getMessage());
        }
        s3client.close();
    }
}
