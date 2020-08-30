/**
 * AWS Lambda Function Hello World Java example.
 * Handle an AWS simple Lambda function that show the content (text) of the call
 * to the lambda function and returns a message including this content.
 */

package example;

import com.amazonaws.services.lambda.runtime.Context;

public class Hello {
    public String myHandler(String name, Context context) {
        context.getLogger().log("Name: " + name + "\n");
        return String.format("Hello %s.", name);
    }
}
