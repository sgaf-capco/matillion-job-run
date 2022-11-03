package org.sanjida.matillion.job;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.sanjida.matillion.params.RunJobParams;
import org.sanjida.matillion.response.MatillionResponse;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.Map;

public class MatillionJobRunner implements RequestHandler<Map<String,String>, String> {

    private final String matillionUsername = "sanjida_user";
    private final String matillionPassword = "M4t1ll10n";


    private final String matillionProjectEndpoint = "http://linux-vm-matillion-poc-b3df2fbe50.uksouth.cloudapp.azure.com/rest/v1/group/name/xx_sanjida/project/name/test/version/name/default";
    private final String dwhJobRunningPath = "/job/name/DWH%20Orchestration/run";


    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void runOrchestration(LambdaLogger logger, String demoTableNameSuffix, String demoTableName) throws URISyntaxException, IOException, InterruptedException {

        HttpClient client = getHttpClientWithPassAuth();
        String body = "{ \"scalarVariables\": { \"demo_table_name_suffix\": \"" + demoTableNameSuffix + "\", \"demo_table_name\": \"" + demoTableName + "\"}}";

        HttpRequest jobRunRequest = HttpRequest.newBuilder()
                .uri(new URI(matillionProjectEndpoint + dwhJobRunningPath))
                .header("Content-Type", "application/json")
                .header("Authorization", getBasicAuthHeader())
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse response = client.send(jobRunRequest, BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        MatillionResponse jobRunResponse = objectMapper.readValue(response.body().toString(), MatillionResponse.class);
        logger.log(jobRunResponse.toString());
    }

    private HttpClient getHttpClientWithPassAuth(){
        return HttpClient.newBuilder().authenticator(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(matillionUsername, matillionPassword.toCharArray());
            }
        }).build();
    }

    private String getBasicAuthHeader(){
        String plainCreds = matillionUsername + ":" + matillionPassword;
        byte[] base64Bytes = Base64.getEncoder().encode(plainCreds.getBytes());

        return "Basic " + new String(base64Bytes);
    }


    @Override
    public String handleRequest(Map<String, String> event, Context context)  {
        LambdaLogger logger = context.getLogger();
        String response = "200 OK";
        String params = gson.toJson(event);
        logger.log("EVENT: " + params);

        RunJobParams jobParams = gson.fromJson(params, RunJobParams.class);

        try {
            runOrchestration(logger, jobParams.getDemoTableNameSuffix(), jobParams.getDemoTableName());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }
}
