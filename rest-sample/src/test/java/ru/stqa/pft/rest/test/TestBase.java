package ru.stqa.pft.rest.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.rest.appmanager.ApplicationManager;

import java.io.IOException;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager();

    @BeforeSuite
    public void setUp() throws IOException {
        app.init();
    }

    public String getIssueStatus(int issueId) {
        String json = RestAssured.get(app.getProperty("bugify.url") + "issues/" + issueId + ".json").asString();
        JsonElement issues = JsonParser.parseString(json).getAsJsonObject().get("issues");
        String stateName = issues.getAsJsonArray().iterator().next()
                .getAsJsonObject().get("state_name").getAsString();
        return stateName;

    }

    public boolean isIssueOpen(int issueId) {
        String issueStatus = getIssueStatus(issueId);
        if (issueStatus.equals("Closed") || issueStatus.equals("Resolved")) {
            return false;
        } else {
            return true;
        }
    }

    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}