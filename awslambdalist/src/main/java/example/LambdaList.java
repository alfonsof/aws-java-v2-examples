/**
 * LambdaList is an example that handles Lambda functions on AWS.
 * List Lambda function information.
 * You must provide 1 parameter:
 * FUNCTION_NAME      = Lambda function name
 */

package example;

import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.GetFunctionConfigurationRequest;
import software.amazon.awssdk.services.lambda.model.GetFunctionConfigurationResponse;
import software.amazon.awssdk.services.lambda.model.ServiceException;


public class LambdaList {
    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Not enough parameters.\n" +
                    "Proper Usage is: java -jar lambdalist.jar <FUNCTION_NAME>");
            System.exit(1);
        }

        String functionName = args[0];

        System.out.println("Lambda function name: " + functionName);

        LambdaClient awsLambda = LambdaClient.builder().region(REGION).build();

        try {
            System.out.println("Listing Lambda function ...");

            GetFunctionConfigurationRequest configRequest = GetFunctionConfigurationRequest.builder()
                    .functionName(functionName).build();
            GetFunctionConfigurationResponse config = awsLambda.getFunctionConfiguration(configRequest);

            System.out.println("The function name is "+config.functionName());
            System.out.println("  - ARN: "+config.functionArn());
            System.out.println("  - Runtime: "+config.runtime());
            System.out.println("  - Role: "+config.role());
            System.out.println("  - Handler: "+config.handler());
            System.out.println("  - Description: "+config.description());
            System.out.println("  - Timeout: "+config.timeout());
            System.out.println("  - Memory Size: "+config.memorySize());
            System.out.println("  - Last Modified: "+config.lastModified());
            System.out.println("  - Code Size: "+config.codeSize());
            System.out.println("  - Version: "+config.version());
            System.out.println();

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
