/**
 * LambdaUpdate is an example that handles Lambda functions on AWS.
 * Update a Lambda function.
 * You must provide 1 parameter:
 * FUNCTION_NAME      = Lambda function name
 * FUNCTION_FILE      = The path to the JAR or ZIP file where the code of the Lambda function is located
 * FUNCTION_ROLE      = The role ARN that has Lambda permissions
 * FUNCTION_HANDLER   = The fully qualified method name (Ex: example.Handler::handleRequest)
 */

package example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.lambda.model.LambdaException;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.UpdateFunctionConfigurationRequest;
import software.amazon.awssdk.services.lambda.model.UpdateFunctionConfigurationResponse;
import software.amazon.awssdk.services.lambda.model.UpdateFunctionCodeRequest;
import software.amazon.awssdk.services.lambda.model.UpdateFunctionCodeResponse;


public class LambdaUpdate {
    private static final Region REGION = Region.of("eu-west-1");      // Region name

    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println("Not enough parameters.\n" +
                    "Proper Usage is: java -jar lambdaupdate.jar " +
                    "<FUNCTION_NAME> <FUNCTION_FILE> <FUNCTION_ROLE> <FUNCTION_HANDLER>");
            System.exit(1);
        }

        String functionName = args[0];
        String functionFile = args[1];
        String functionRole = args[2];
        String functionHandler = args[3];

        System.out.println("Lambda function name:    " + functionName);
        System.out.println("Lambda function file:    " + functionFile);
        System.out.println("Lambda function role:    " + functionRole);
        System.out.println("Lambda function handler: " + functionHandler);

        LambdaClient awsLambda = LambdaClient.builder().region(REGION).build();

        try {
            InputStream is = new FileInputStream(functionFile);
            SdkBytes fileToUpload = SdkBytes.fromInputStream(is);

            System.out.println("Updating Lambda function ...");

            // Update Configuration of Lambda function
            UpdateFunctionConfigurationRequest functionConfigurationRequest =
                    UpdateFunctionConfigurationRequest.builder()
                    .functionName(functionName)
                    .runtime("java8")
                    .role(functionRole)
                    .handler(functionHandler)
                    .description("Updated by the Lambda Java API")
                    .timeout(15)
                    .memorySize(128)
                    .build();
            UpdateFunctionConfigurationResponse functionConfigurationResponse =
                    awsLambda.updateFunctionConfiguration(functionConfigurationRequest);

            // Update Code of Lambda function
            UpdateFunctionCodeRequest functionCodeRequest = UpdateFunctionCodeRequest.builder()
                    .functionName(functionName)
                    .zipFile(fileToUpload)
                    .publish(true)
                    .build();
            UpdateFunctionCodeResponse functionCodeResponse = awsLambda.updateFunctionCode(functionCodeRequest);

            System.out.println("Updated");
            System.out.println("The function ARN (Conf.) is " + functionConfigurationResponse.functionArn());
            System.out.println("The function ARN (Code) is  " + functionCodeResponse.functionArn());

        } catch (LambdaException | FileNotFoundException e) {
            System.err.println(e.getMessage());
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
