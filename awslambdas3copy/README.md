# AWS Lambda Function S3 Copy Java example

This folder contains an AWS Lambda Function example in Java on AWS (Amazon Web Services).

It handles an AWS Lambda function that copies an object when it appears in a S3 bucket to another S3 bucket.

## Requirements

* You must have an [Amazon Web Services (AWS)](http://aws.amazon.com/) account.

* The code was written for:
 
  *  Java 8
  *  Apache Maven 3
  *  AWS Java Lambda Support Libraries:
     *  AWS Lambda Java Core Library
     *  AWS Lambda Java Events Library
  *  AWS SDK for Java (SDK V2)

## Using the code

* You can select the destination bucket name using an AWS Lambda environment variable: `TARGET_BUCKET`

* Access the AWS console.

* Create a S3 bucket for the source and another S3 bucket for the target.

*  Create an IAM Policy: ex. `Policy-my-buckets`

   Changing: 
  
   * `sourcebucket` to the name of your source bucket.
   * `targetbucket` to the name of your target bucket.

   Content of the IAM policy:

  ```bash
  {
      "Version": "2012-10-17",
      "Statement": [
          {
              "Effect": "Allow",
              "Action": [
                  "s3:GetObject"
              ],
              "Resource": [
                  "arn:aws:s3:::sourcebucket/*"
              ]
          },
          {
              "Effect": "Allow",
              "Action": [
                  "s3:PutObject"
              ],
              "Resource": [
                  "arn:aws:s3:::targetbucket/*"
              ]
          },
          {
              "Effect": "Allow",
              "Action": [
                  "cloudwatch:*"
              ],
              "Resource": [
                  "*"
              ]
          },
          {
              "Effect": "Allow",
              "Action": [
                  "logs:*"
              ],
              "Resource": [
                  "*"
              ]
          }
      ]
  }
  ```

* Create a role: `Role-my-buckets`.

  This role uses the policy `Policy-my-buckets`

* Create an AWS Lambda function:
  * Name: `<LAMBDA_NAME>`
  * Runtime: `Java 8`
  * Handler: `example.S3CopyHandler::handleRequest`
  * Role: `Role-my-buckets`
  * Runtime Settings for the lambda function:
    * Memory (MB): `1024`
    * Timeout: `10 sec`
  * The resources that the function's role has access to:
    * `Amazon CloudWatch`
    * `Amazon CloudWatch Logs`
    * `Amazon S3`
      * Lambda obtained information from the policy statements: `Managed policy Policy-my-buckets`:
        * `s3:GetObject` --> `Allow: arn:aws:s3:::sourcebucket/*`
        * `s3:PutObject` --> `Allow: arn:aws:s3:::targetbucket/*`
  * The triggers:
    * `S3`
      * Bucket: `<SOURCE_BUCKET_NAME>`
      * Event type: `ObjectCreated`
      * Enable trigger: `Yes`

* Create the AWS Lambda environment variable `TARGET_BUCKET` and set its value to the name of your target bucket.

* Upload the Java JAR file.

  Artifact:

  ```bash
  awslambdas3copy.jar
  ```

* Save the Lambda function.

  It deploys the Lambda function.

* Test the function:

  Copy a file in the source S3 bucket.

  The object from the source S3 bucket should be copied to the target S3 bucket.

  You should see the next messages in the log:

    ```bash
    "S3Event: <LAMBDA_INPUT>"
    "Source Bucket: <SOURCE_BUCKET_NAME>"
    "Source Object: <SOURCE_FILE_NAME>"
    "Target Bucket: <TARGET_BUCKET_NAME>"
    "Target Object: <TARGET_FILE_NAME>"
    "Copying object ..."
    "Copied"
    ```