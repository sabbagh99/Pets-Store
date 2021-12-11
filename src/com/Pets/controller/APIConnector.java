package com.Pets.controller;

import com.Pets.Global;
import com.Pets.Pet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class APIConnector {

    private final String urlString = "https://petstore.swagger.io/v2/pet";
    private int responseResult;

    public int getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(int responseResult) {
        this.responseResult = responseResult;
    }

    public APIConnector() {
    }

    public JSONArray getJSONArray(String query) { // get array of objects
        try {
            URL url = new URL(urlString + "/findByStatus?status=" + query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            setResponseResult(responseCode);
            if (responseCode != Global.SUCCESSFULLY_RESPONSE_RESULT) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parse = new JSONParser();

                return (JSONArray) parse.parse(String.valueOf(informationString));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getJSONObject(String id, String requestMethod) { // get one Object
        try {
            URL url = new URL(urlString + "/" + id);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();
            setResponseResult(responseCode);

            if (responseCode != Global.SUCCESSFULLY_RESPONSE_RESULT) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parse = new JSONParser();

                return (JSONObject) parse.parse(String.valueOf(informationString));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public JSONObject setJSONObject(String requestMethod, Pet pet) { // for PUT / POST request

        try {

            URL url = new URL(urlString);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod(requestMethod);
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            OutputStream os = httpCon.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            JSONObject category = new JSONObject();
            JSONArray photoUrls = new JSONArray();
            JSONArray tags = new JSONArray();
            JSONObject parent = new JSONObject();
            JSONObject objectTags = new JSONObject();

            parent.put("id", pet.getId());
            category.put("id", pet.getId());
            category.put("name", pet.getName());
            parent.put("category", category);
            parent.put("name", pet.getName());
            photoUrls.add(pet.getPhotoUrls());
            parent.put("photoUrls", photoUrls);
            objectTags.put("id", pet.getId());
            objectTags.put("name", pet.getName());
            tags.add(0, objectTags);
            parent.put("tags", tags);
            parent.put("status", pet.getStatus());
            osw.write(parent.toString());
            osw.flush();
            osw.close();
            os.close();  //don't forget to close the OutputStream
            httpCon.connect();
            int responseCode = httpCon.getResponseCode();
            setResponseResult(responseCode);

//
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpCon.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public JSONObject deleteJSONObject(String id) throws IOException {
        URL url = new URL(urlString + "/" + id);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Accept", "application/json");
        setResponseResult(conn.getResponseCode());

        conn.connect();
        return null;
    }
}