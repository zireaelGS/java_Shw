package ru.stqa.pft.rest.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.util.Set;

import static com.jayway.restassured.RestAssured.authentication;
import static com.jayway.restassured.RestAssured.basic;
import static org.testng.Assert.assertEquals;

public class RestAssuredTests extends TestBase {

    @BeforeClass
    public void init() {
        authentication = basic(app.getProperty("bugify.login"), app.getProperty("bugify.password"));
    }

    @Test
    public void testCreateIssue() {
        skipIfNotFixed(100);
        Set<Issue> oldIssues = app.restAssured().getIssues();
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
        int issueId = app.restAssured().createIssue(newIssue);
        Set<Issue> newIssues = app.restAssured().getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

}
