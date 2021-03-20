/**
 * S3DeleteObject is an example that handles S3 buckets on AWS.
 * Delete an object in a S3 bucket.
 * You must provide 2 parameters:
 * BUCKET_NAME = Name of the bucket
 * OBJECT_NAME = Name of the object in the bucket
 */

package example;

import java.util.ArrayList;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;


public class S3DeleteObject {
    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Not enough parameters.\n" +
                    "Proper Usage is: java -jar s3deleteobject.jar " +
                    "<BUCKET_NAME> <OBJECT_NAME>");
            System.exit(1);
        }

        // The name for the bucket
        String bucketName = args[0];

        // The name for the object
        String keyName = args[1];

        System.out.println("Bucket name: " + bucketName);
        System.out.println("Object name: " + keyName);

        // Instantiates a client
        S3Client s3client = S3Client.builder()
                .region(REGION)
                .build();

        try {
            System.out.println("Deleting object ...");

            ArrayList<ObjectIdentifier> toDelete = new ArrayList<ObjectIdentifier>();
            toDelete.add(ObjectIdentifier.builder().key(keyName).build());

            // Delete Object
            DeleteObjectsRequest delReq = DeleteObjectsRequest.builder()
                    .bucket(bucketName)
                    .delete(Delete.builder().objects(toDelete).build())
                    .build();
            s3client.deleteObjects(delReq);

            System.out.println("Deleted");
            
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                System.out.println("Error: Bucket does not exist!!");
                System.out.println("S3Exception: " + e);
            } else {
                System.out.println("S3Exception: " + e);
                System.out.println("HTTP Status Code:  " + e.statusCode());
            }
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:     " + ase.getMessage());
            System.out.println("HTTP Status Code:  " + ase.statusCode());
            System.out.println("AWS Service Name:  " + ase.awsErrorDetails().serviceName());
            System.out.println("AWS Error Code:    " + ase.awsErrorDetails().errorCode());
            System.out.println("AWS Error Message: " + ase.awsErrorDetails().errorMessage());
            System.out.println("Request ID:        " + ase.requestId());
        } catch (SdkException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        s3client.close();
    }
}
