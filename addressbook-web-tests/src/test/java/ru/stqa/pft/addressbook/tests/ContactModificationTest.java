package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class ContactModificationTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Tom").withMiddlename("Marvolo").withLastname("Riddle")
                    .withNickname("Lord Voldemort").withTitle("LORD").withCompany("Death eaters").withAddress("The whole world")
                    .withHome("666").withMobile("666999").withEmail("VolodyaKiller@mail.ru").withBday("13").withBmonth("April")
                    .withByear("1938"));
        }
    }

    @Test
    public void testContactModification() {

        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Tomash").withMiddlename("Marvolo").withLastname("Riddle")
                .withNickname("Lord Voldemort").withTitle("LORD").withCompany("Death eaters").withAddress("The whole world")
                .withHome("666").withMobile("666999").withEmail("VolodyaKiller@mail.ru").withBday("13").withBmonth("April")
                .withByear("1938");
        app.contact().modify(contact);
        Contacts after = app.contact().all();
        assertEquals(after.size(), before.size());//check for compliance with quantity
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));

    }

}
