/**
 * S3Copy is an example that handles S3 buckets on AWS.
 * Copy an onject from a S3 bucket to another S3 bucket.
 * You must provide 3 parameters:
 * SOURCE_BUCKET      = Source bucket name
 * SOURCE_OBJECT      = Source object name
 * DESTINATION_BUCKET = Destination bucket name
 */

package example;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;

public class S3Copy {
    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) throws IOException {
        String sourceBucketName;       // Source bucket name
        String sourceKey;              // Source key
        String destinationBucketName;  // Destination bucket name
        String destinationKey;         // Destination key

        if (args.length < 3) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar s3copy.jar <SOURCE_BUCKET> <SOURCE_OBJECT> <DESTINATION_BUCKET>");
            System.exit(1);
        }

        sourceBucketName      = args[0];
        sourceKey             = args[1];
        destinationBucketName = args[2];
        destinationKey        = sourceKey;

        System.out.println("From - bucket: " + sourceBucketName);
        System.out.println("From - object: " + sourceKey);
        System.out.println("To   - bucket: " + destinationBucketName);
        System.out.println("To   - object: " + destinationKey);

        // Instantiates a client
        S3Client s3client = S3Client.builder()
                .region(REGION)
                .build();

        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(sourceBucketName + "/" + sourceKey, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("URL could not be encoded: " + e.getMessage());
        }

        try {
            System.out.println("Copying object ...");

            // Copy object
            CopyObjectRequest copyReq = CopyObjectRequest.builder()
                    .copySource(encodedUrl)
                    .destinationBucket(destinationBucketName)
                    .destinationKey(destinationKey)
                    .build();

            CopyObjectResponse copyRes = s3client.copyObject(copyReq);
            System.out.println("Copied");
            System.out.println("Tag information: " + copyRes.copyObjectResult().eTag());
            System.out.println("Last Modified: " + copyRes.copyObjectResult().lastModified());

        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                System.out.println("Error: Bucket/Object \"" + sourceBucketName + "/" +
                        sourceKey + "\" or Bucket \"" + destinationBucketName + "\" do not exist!!");
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
