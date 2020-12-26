package ru.stqa.pft.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import ru.stqa.pft.rest.model.Issue;

import java.util.Set;

public class RestAssuredHelper {

    private ApplicationManager app;

    public RestAssuredHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Issue> getIssues() {
        String json = RestAssured.get(app.getProperty("bugify.url") + "issues.json").asString();
        JsonElement parsedJson = new JsonParser().parse(json);
        JsonElement issues = parsedJson.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }

    public int createIssue(Issue issue) {
        String json = RestAssured.given()
                .parameter("subject", issue.getSubject())
                .parameter("description", issue.getDescription())
                .post(app.getProperty("bugify.url") + "issues.json").asString();
        JsonElement parsedJson = new JsonParser().parse(json);
        return parsedJson.getAsJsonObject().get("issue_id").getAsInt();
    }

}