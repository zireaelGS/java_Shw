package ru.stqa.pft.rest;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests {

    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssueses();
        Issue newIssue = new Issue();
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssueses();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, equals(oldIssues));

    }

    private Set<Issue> getIssueses() throws IOException {
        String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json"))
                .returnContent().asString();
        return null;
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("28accbe43ea112d9feb328d2c00b3eed", "");
    }

    private int createIssue(Issue newIssue) {
        return 0;
    }
}
