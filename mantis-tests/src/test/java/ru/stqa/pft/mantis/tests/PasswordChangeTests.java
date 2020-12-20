package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.Users;
import ru.stqa.pft.mantis.model.UsersData;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class PasswordChangeTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void passwordChangeTest() throws IOException {
        String newPassword = "Vovchik669";
        app.users().loginAs("administrator", "root");
        app.users().openUserPage();
        Users users = app.users().all();
        Set<UsersData> us = new HashSet<UsersData>();
        for (UsersData u : users) {
            if (!u.getUsername().equals("administrator")) {
                us.add(u);
            }
        }
        //here we are choosing some user and because of your checking higher we never will choose administrator
        UsersData selectedUser = us.iterator().next();
        String username = selectedUser.getUsername();
        String email = selectedUser.getEmail();
        app.users().resetUserPassword(username);
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.users().passwordResetConfirmation(confirmationLink, username, newPassword);
        //checking that user can login with new password on http level
        HttpSession session = app.newSession();
        assertTrue(session.login(username, newPassword));
        assertTrue(session.isLoggedInAs(username));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findAny().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

}