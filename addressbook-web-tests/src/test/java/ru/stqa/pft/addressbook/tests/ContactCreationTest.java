package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.goTo().contactPage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstname("Tom").withMiddlename("Marvolo").withLastname("Riddle")
                .withNickname("Lord Voldemort").withTitle("LORD").withCompany("Death eaters").withAddress("The whole world")
                .withHome("666").withMobile("666999").withEmail("VolodyaKiller@mail.ru").withBday("13").withBmonth("April")
                .withByear("1938");
        app.contact().create(contact);
        Contacts after = app.contact().all();
        assertEquals(after.size(), before.size() + 1);//check for compliance with quantity

        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

    }


}

