/**
 * LambdaDelete is an example that handles Lambda functions on AWS.
 * Delete a Lambda function.
 * You must provide 1 parameter:
 * FUNCTION_NAME      = Lambda function name
 */

package example;

import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.model.DeleteFunctionRequest;
import software.amazon.awssdk.services.lambda.model.ServiceException;

public class LambdaDelete {
    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Not enough parameters.\n" +
                    "Proper Usage is: java -jar lambdadelete.jar <FUNCTION_NAME>");
            System.exit(1);
        }

        String functionName = args[0];

        System.out.println("Lambda function name: " + functionName);

        LambdaClient awsLambda = LambdaClient.builder().region(REGION).build();

        try {
            System.out.println("Deleting Lambda function ...");

            // Setup an DeleteFunctionRequest
            DeleteFunctionRequest request = DeleteFunctionRequest.builder()
                    .functionName(functionName)
                    .build();

            // Invoke the Lambda deleteFunction method
            awsLambda.deleteFunction(request);
            
            System.out.println("Deleted");

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
        awsLambda.close();
    }
}
