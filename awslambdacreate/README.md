# AWS Lambda Function Create Java example

This folder contains a Java application example that handles Lambda functions on AWS (Amazon Web Services).

Create an AWS Lambda function.

## Requirements

* You must have an [Amazon Web Services (AWS)](http://aws.amazon.com/) account.

* The code was written for:
 
  *  Java 8
  *  Apache Maven 3
  *  AWS SDK for Java (SDK V2)

## Using the code

* Configure your AWS access keys.

  **Important:** For security, it is strongly recommend that you use IAM users instead of the root account for AWS access.

  When you initialize a new service client without supplying any arguments, the AWS SDK for Java attempts to find AWS credentials by using the default credential provider chain.

  Setting your credentials for use by the AWS SDK for Java can be done in a number of ways, but here are the recommended approaches:

  * The default credential profiles file
  
    Set credentials in the AWS credentials profile file on your local system, located at:

    `~/.aws/credentials` on Linux, macOS, or Unix

    `C:\Users\USERNAME\.aws\credentials` on Windows

    This file should contain lines in the following format:

    ```bash
    [default]
    aws_access_key_id = <your_access_key_id>
    aws_secret_access_key = <your_secret_access_key>
    ```
    Substitute your own AWS credentials values for the values `<your_access_key_id>` and `<your_secret_access_key>`.

  * Environment variables `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`
  
    Set the `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` environment variables.

    To set these variables on Linux, macOS, or Unix, use `export`:

    ```bash
    export AWS_ACCESS_KEY_ID=<your_access_key_id>
    export AWS_SECRET_ACCESS_KEY=<your_secret_access_key>
    ```

    To set these variables on Windows, use `set`:

    ```bash
    set AWS_ACCESS_KEY_ID=<your_access_key_id>
    set AWS_SECRET_ACCESS_KEY=<your_secret_access_key>
    ```

* You need a `JAR` or `ZIP` file where the code of the Lambda function is located.

  You can use the code obtained from the AWS Lambda Function Hello World JSON Java example: [awslambdahellojson](/awslambdahellojson).

* You can select the AWS region of the Lambda function changing the value of `REGION` variable in the code.

* You have to create an AWS role that has Lambda permissions.

* Run the code.

  You must provide 4 parameter:
  
  * `<FUNCTION_NAME>`      = Lambda function name
  * `<FUNCTION_FILE>`      = The path to the JAR or ZIP file where the code of the Lambda function is located
  * `<FUNCTION_ROLE>`      = The role ARN that has Lambda permissions
  * `<FUNCTION_HANDLER>`   = The fully qualifed method name (Ex: example.Handler::handleRequest)

  Run application:

  ```bash
  java -jar awslambdacreate.jar lambda-name lambda-file lambda-role lambda-handler
  ```

  You must use as a function role the ARN.

  Ex.: `arn:aws:iam::123456789012:role/service-role/lambda-basic-execution`

* Test the application.

  The Lambda function is created and you should see the messages:
  
  * `Creating Lambda function ...`
  * `Created`
