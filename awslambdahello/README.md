# AWS Lambda Function Hello World Java example

This folder contains an AWS Lambda Function example in Java on AWS (Amazon Web Services).

It handles a simple AWS Lambda function that shows the content (text) of the call to the lambda function and returns a message including this content.

## Requirements

* You must have an [Amazon Web Services (AWS)](http://aws.amazon.com/) account.

* The code was written for:
 
  *  Java 8
  *  Apache Maven 3
  *  AWS Java Lambda Support Libraries:
     *  AWS Lambda Java Core Library

## Using the code

* Access the AWS console.

* Select AWS Lambda in the services menu.

* Create an AWS lambda function:
  * Name: `<LAMBDA_NAME>`
  * Runtime: `Java 8`
  * Handler: `example.Hello::myHandler`
  * Role: `lambda-basic-execution`
  * Runtime Settings for the lambda function:
    * Memory (MB): `512`
    * Timeout: `15 sec`
  * The resources that the function's role has access to:
    * `Amazon CloudWatch Logs`
  * The triggers:
    * `Nothing`

* Upload the Java JAR file.

  Artifact:

  ```bash
  awslambdahello.jar
  ```

* Save the Lambda function.

  It deploys the Lambda function.

* Create and configure a Test event.

  Input file content:

  ```bash
  "Peter"
  ```

* Run the code.

  Run the code in an AWS lambda function using the test button.

* Test the AWS Lambda function.

  You should see the next message in the return:

  ```bash
  "Hello Peter."
  ```

  You should see the next message in the log:

  ```bash
  "Name: Peter"
  ```
