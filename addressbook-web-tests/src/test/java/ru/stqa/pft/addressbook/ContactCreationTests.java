package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        initContactCreation();
        fillContactForm(new ContactData("Tom", "Marvolo", "Riddle", "Lord Voldemort", "LORD", "Death eaters", "The whole world", "666", "666999", "VolodyaKiller@mail.ru", "13", "April", "1938"));
        submitContactCreation();
        returnContactPage();
        getLogout();
    }


}
