# Java examples on AWS (Amazon Web Services) - SDK V2

This repo contains Java code examples on AWS (Amazon Web Services).

These examples show how to use Java 8 and AWS SDK for Java (SDK V2) in order to manage Amazon services on AWS.

AWS SDK for Java allows Java developers to write software that makes use of Amazon services like EC2, S3 and Lambda functions.

*If you have to use AWS SDK for Java (SDK V1) you have the Java code examples on AWS following this link: [https://github.com/alfonsof/aws-java-examples/](https://github.com/alfonsof/aws-java-examples)*

## Quick start

You must have an [AWS (Amazon Web Services)](http://aws.amazon.com/) account.

The code for the samples is contained in individual folders on this repository.

For instructions on running the code, please consult the README in each folder.

This is the list of examples:

**Compute - Amazon EC2:**

* [awsec2instances](/awsec2instances) - AWS EC2 instances: Example of how to handle AWS EC2 instances.
  
**Compute - AWS Lambda:**

* [awslambdahello](/awslambdahello) - AWS Lambda Function Hello World: Example of how to handle an AWS simple Lambda function and a text input.
* [awslambdahellojson](/awslambdahellojson) - AWS Lambda Function Hello World JSON: Example of how to handle an AWS simple Lambda  function and a JSON input, using classes for Request and Response.
* [awslambdainvoke](/awslambdainvoke) - AWS Lambda Function Invoke: Example of how to handle an AWS Lambda function and invoke it.
* [awslambdalist](/awslambdalist) - AWS Lambda Function List: Example of how to handle an AWS Lambda function and list its information.
* [awslambdalistall](/awslambdalistall) - AWS Lambda Function List All: Example of how to handle AWS Lambda functions and list all Lambda functions and their information.
* [awslambdadelete](/awslambdadelete) - AWS Lambda Function Delete: Example of how to handle an AWS Lambda function and delete it.

**Storage - Amazon S3:**

* [awss3create](/awss3create) - AWS S3 Create: Example of how to handle S3 buckets and create a new S3 bucket.
* [awss3delete](/awss3delete) - AWS S3 Delete: Example of how to handle S3 buckets and delete a S3 bucket.
* [awss3list](/awss3list) - AWS S3 List: Example of how to handle S3 buckets and list information about the objects in a S3 bucket.
* [awss3listall](/awss3listall) - AWS S3 List All: Example of how to handle S3 buckets and list information about all S3 buckets and the objects that they contain.
* [awss3upload](/awss3upload) - AWS S3 Upload: Example of how to handle S3 buckets and upload a local file to a S3 bucket.
* [awss3download](/awss3download) - AWS S3 Download: Example of how to handle S3 buckets and download an object from a S3 bucket to a local file.

## License

This code is released under the MIT License. See LICENSE file.