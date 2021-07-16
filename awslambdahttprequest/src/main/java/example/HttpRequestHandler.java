/**
 * AWS Lambda Function Http Request Java example.
 * Handle an AWS Lambda function that it is invoked by an http request.
 * It shows the parameters of the request and responds a message including the parameters.
 */

package example;

import java.util.Map;
import java.util.HashMap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class HttpRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        context.getLogger().log("Event: " + event);
        Map queryStringParameters = event.getQueryStringParameters();
        String responseString;
        if (queryStringParameters == null) {
            responseString = "Who are you?";
        } else {
            context.getLogger().log("firstname: " + queryStringParameters.get("firstname"));
            context.getLogger().log("lastname: " + queryStringParameters.get("lastname"));
            responseString = "Hello " +
                    queryStringParameters.get("firstname") + " " +
                    queryStringParameters.get("lastname") +"!";
        }
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setIsBase64Encoded(false);
        response.setStatusCode(200);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "text/html");
        response.setHeaders(headers);
        response.setBody("<!DOCTYPE html><html><head><title>AWS Lambda example</title></head><body>"+
                responseString + "</p>" +
                "</body></html>");

        return response;
    }
}
