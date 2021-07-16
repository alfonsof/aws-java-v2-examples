# AWS Lambda Function Http Request Java example

This folder contains an AWS Lambda Function example in Java on AWS (Amazon Web Services).

It handles an AWS Lambda function that it is invoked by an http request. It shows the parameters of the request and responds a message including the parameters.

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

* Create an AWS lambda function:
  * Name: `<LAMBDA_NAME>`
  * Runtime: `Java 8`
  * Handler: `example.HttpRequestHandler::handleRequest`
  * Role: `lambda-basic-execution`
  * Runtime Settings for the lambda function:
    * Memory (MB): `512`
    * Timeout: `15 sec`
  * The resources that the function's role has access to:
    * `Amazon CloudWatch Logs`
  * The triggers:
    * `API Gateway`
      * Details below.

* Upload the Java JAR file.

  Artifact:

  ```bash
  awslambdahttprequest.jar
  ```

* Save the Lambda function.

  It deploys the Lambda function.

* Create an `API Gateway` trigger.

  This allows to call the lambda function using an HTTP API.

  * Name: `<LAMBDA_NAME>-API`
  * API: `Create an API`
  * API type: `HTTP API`
  * Security: `Open`

  You will get an API endpoint, which can be copied and run in your browser's address bar.
  
  It looks like the following URL:
  
  ```bash
  https://<API_ID>.execute-api.<REGION>.amazonaws.com/<STAGE_NAME>/<LAMBDA_NAME>
  ```

  For example:

  ```bash
  https://abcdefg5jk.execute-api.eu-west-1.amazonaws.com/default/HttpRequestJava`
  ```

* Run the code.

  To run the code, you need to use 2 parameters:

  * `firstname`
  * `lastname`

  You call the API endpoint with this format: 
  
  ```bash
  https://<API_ID>.execute-api.<REGION>.amazonaws.com/<STAGE_NAME>/<LAMBDA_NAME>?firstname=<FIRST_NAME>&lastname=<LAST_NAME>
  ```

  For example:

  ```bash
  https://abcdefg5jk.execute-api.eu-west-1.amazonaws.com/default/HttpRequestPython?firstname=Peter&lastname=Parker
  ```

* Test the AWS Lambda function.

  Go to the URL of API endpoint that you have got: `https://<API_ID>.execute-api.<REGION>.amazonaws.com/<STAGE_NAME>/<LAMBDA_NAME>?firstname=Peter&lastname=Parker` using a browser.

  You should see the next response if you have added the right paramenters:

  ```bash
  "Hello Peter Parker!"
  ```

  You should see the next response if you have not added any paramenter:

  ```bash
  "Who are you?"
  ```
  