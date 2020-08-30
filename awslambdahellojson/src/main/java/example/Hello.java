/**
 * AWS Lambda Function Hello World JSON Java example.
 * Handle an AWS simple Lambda function that show the content (JSON) of the call
 * to the lambda function and returns a message including this content
 * using classes for Request and Response.
 */

package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Hello implements RequestHandler<RequestClass, ResponseClass>{

    public ResponseClass handleRequest(RequestClass request, Context context){
        context.getLogger().log("First Name: " + request.firstName + "\n");
        context.getLogger().log("Last Name: " + request.lastName + "\n");
        String greetingString = String.format("Hello %s %s.", request.firstName, request.lastName);
        return new ResponseClass(greetingString);
    }
}