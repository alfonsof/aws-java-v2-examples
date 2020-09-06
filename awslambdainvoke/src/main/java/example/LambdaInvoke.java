/**
 * LambdaInvoke is an example that handles Lambda functions on AWS.
 * Invoke a Lambda function.
 * You must provide 1 parameter:
 * FUNCTION_NAME      = Lambda function name
 */

package example;

import java.io.IOException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.ServiceException;

public class LambdaInvoke {

    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar lambdainvoke.jar <FUNCTION_NAME>");
            System.exit(1);
        }

        String functionName = args[0];

        System.out.println("Lambda function name: " + functionName);

        InvokeResponse res = null ;
        try {
            LambdaClient awsLambda = LambdaClient.builder().region(REGION).build();

            //Need a SdkBytes instance for the payload
            SdkBytes payload = SdkBytes.fromUtf8String("{\n" +
                    " \"firstName\": \"Peter\",\n" +
                    " \"lastName\": \"Parker\"\n" +
                    "}" ) ;

            //Setup an InvokeRequest
            InvokeRequest request = InvokeRequest.builder()
                    .functionName(functionName)
                    .payload(payload)
                    .build();

            //Invoke the Lambda function
            res = awsLambda.invoke(request);

            //Get the response
            String value = res.payload().asUtf8String() ;

            //write out the response
            System.out.println("Lambda return value: " + value);
            System.out.println("Lambda status code: " + res.statusCode());

        } catch (ServiceException e) {
            System.out.println("ServiceException: " + e);
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AwsServiceException, " +
                    "which means your request made it " +
                    "to AWS Lambda, but was rejected with an error " +
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
                    " communicate with AWS Lambda, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + se.getMessage());
        }
    }
}