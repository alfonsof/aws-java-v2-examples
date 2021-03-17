# AWS Lambda Function Hello World JSON Java example

This folder contains an AWS Lambda Function example in Java on AWS (Amazon Web Services).

It handles a simple AWS Lambda function that shows the content (JSON) of the call to the lambda function and returns a message including this content, using classes for Request and Response.

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
  * Handler: `example.Hello::handleRequest`
  * Role: `lambda-basic-execution`
  * The triggers:
    * `Nothing`
  * The resources that the function's role has access to:
    * `Amazon CloudWatch Logs`
  * Basic Settings for the lambda function:
    * Memory (MB): `512`
    * Timeout: `15 sec`

* Upload the Java JAR file.

  Artifact:

  ```bash
  awslambdahellojson.jar
  ```

* Save the Lambda function.

  It deploys the Lambda function.

* Create and configure a Test event.

  Input JSON file content:

  ```bash
  {
    "firstName": "Peter",
    "lastName": "Parker"
  }
  ```

* Run the code.

  Run the code in an AWS lambda function using the test button.

* Test the AWS Lambda function.

  You should see the next message in the return:

  ```bash
  {
    "greetings": "Hello Peter Parker."
  }
  ```

  You should see the next message in the log:

  ```bash
  "First Name: Peter"
  "Last Name: Parker"
  ```

