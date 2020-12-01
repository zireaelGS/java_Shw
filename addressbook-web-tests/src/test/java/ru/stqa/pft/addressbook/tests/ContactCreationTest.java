package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.goTo().goToContactPage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstname("Tom").withMiddlename("Marvolo").withLastname("Riddle")
                .withNickname("Lord Voldemort").withTitle("LORD").withCompany("Death eaters").withAddress("The whole world")
                .withHome("666").withMobile("666999").withEmail("VolodyaKiller@mail.ru").withBday("13").withBmonth("April")
                .withByear("1938");
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }


}

