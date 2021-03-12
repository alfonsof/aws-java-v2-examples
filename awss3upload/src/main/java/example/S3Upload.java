/**
 * S3Upload is an example that handles S3 buckets on AWS.
 * Upload a local file to a S3 bucket.
 * You must provide 3 parameters:
 * BUCKET_NAME     = Bucket name
 * OBJECT_NAME     = Object name in the bucket
 * LOCAL_FILE_NAME = Local file name
 */

package example;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class S3Upload {
    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) throws IOException {
        String bucketName;              // Bucket name
        String keyName;                 // Key name, it is the object name
        String localFileName;           // Upload local file name

        if (args.length < 3) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar s3upload.jar <BUCKET_NAME> <OBJECT_NAME> <LOCAL_FILE_NAME>");
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
            System.out.println("Uploading an object to S3 from a file ...");

            // Get local file
            File file = new File(localFileName);
            if (file.exists()) {
                // Upload object
                Map<String, String> metadata = new HashMap<>();
                metadata.put("myVal", "myFile");

                PutObjectRequest putOb = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(keyName)
                        .metadata(metadata)
                        .build();

                PutObjectResponse response = s3client.putObject(putOb,
                        RequestBody.fromBytes(getObjectFile(localFileName)));
                System.out.println("Uploaded");
                System.out.println("Tag information: " + response.eTag());
            } else {
                System.out.printf("Error: Local file \"%s\" does NOT exist.", localFileName);
            }
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                System.out.println("Error: Bucket does NOT exists!!");
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

    // Return a byte array
    private static byte[] getObjectFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }
}
