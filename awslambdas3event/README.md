# AWS Lambda Function S3 Event Java example

This folder contains an AWS Lambda Function example in Java on AWS (Amazon Web Services).

It handles an AWS Lambda function that sends information to the log about an object when it appears in a S3 bucket.

## Requirements

* You must have an [Amazon Web Services (AWS)](http://aws.amazon.com/) account.

* The code was written for:
 
  *  Java 8
  *  Apache Maven 3
  *  AWS Java Lambda Support Libraries:
     *  AWS Lambda Java Core Library
     *  AWS Lambda Java Events Library

## Using the code

* Access the AWS console.

* Create a S3 bucket.

* Create an AWS lambda function:
  * Name: `<LAMBDA_NAME>`
  * Runtime: `Java 8`
  * Handler: `example.S3EventHandler::handleRequest`
  * Role: `lambda-basic-execution`
  * Runtime Settings for the lambda function:
    * Memory (MB): `512`
    * Timeout: `15 sec`
  * The resources that the function's role has access to:
    * `Amazon CloudWatch Logs`
  * The triggers:
    * `S3`
      * Bucket: `<BUCKET_NAME>`
      * Event type: `ObjectCreated`
      * Enable trigger: `Yes`

* Upload the Java JAR file.

  Artifact:

  ```bash
  awslambdas3event.jar
  ```

* Save the Lambda function.

  It deploys the Lambda function.

* Test the function:

  Copy a file in the source S3 bucket.

  You should see the next message in the log:

  ```bash
  "S3Event: <LAMBDA_INPUT>"
  "Bucket: <BUCKET_NAME>"
  "Object: <FILE_NAME>"
  ```