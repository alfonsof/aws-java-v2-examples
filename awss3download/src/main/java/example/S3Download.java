/**
 * S3Download is an example that handles S3 buckets on AWS.
 * Download an object from a S3 bucket to a local file.
 * You must provide 3 parameters:
 * BUCKET_NAME     = Bucket name
 * OBJECT_NAME     = Object file name in the bucket
 * LOCAL_FILE_NAME = Local file name
 */

package example;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;


public class S3Download {
    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) {
        String bucketName;            // Source bucket name
        String keyName;               // Key name, it is the object name
        String localFileName;         // Local file name

        if (args.length < 3) {
            System.out.println("Not enough parameters.\n" +
                    "Proper Usage is: java -jar s3download.jar " +
                    "<BUCKET_NAME> <OBJECT_NAME> <LOCAL_FILE_NAME>");
            System.exit(1);
        }

        bucketName    = args[0];
        keyName       = args[1];
        localFileName = args[2];

        System.out.println("Bucket:     " + bucketName);
        System.out.println("Object/Key: " + keyName);
        System.out.println("Local file: " + localFileName);

        // Instantiates a client
        S3Client s3client = S3Client.builder()
                .region(REGION)
                .build();

        try {
            System.out.println("Downloading an object from a S3 to a local file ...");

            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();

            // Get content object
            ResponseBytes<GetObjectResponse> objectBytes = s3client.getObjectAsBytes(objectRequest);
            byte[] data = objectBytes.asByteArray();

            // Write the data to a local file
            File myFile = new File(localFileName);
            OutputStream os = new FileOutputStream(myFile);
            os.write(data);
            os.close();

            System.out.println("Downloaded");

        } catch (IOException ee) {
            System.err.println(ee.getMessage());
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                System.out.println("Error: Bucket/Object \"" + bucketName + "/" + keyName + "\" does not exist!!");
                System.out.println("S3Exception: " + e);
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
