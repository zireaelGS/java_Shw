package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemoveFromGroupTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().goToContactPage();
            app.contact().create(new ContactData().withFirstname("Tom").withMiddlename("Marvolo").withLastname("Riddle")
                    .withNickname("Lord Voldemort").withTitle("LORD").withCompany("Death eaters").withAddress("The whole world")
                    .withHome("666").withMobile("666999").withEmail("VolodyaKiller@mail.ru").withBday("13").withBmonth("April")
                    .withByear("1938"));
        }

        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testContactRemoveFromGroup() {
        Contacts contacts = app.db().contacts();
        ContactData contact = contacts.iterator().next();
        Groups groups = app.db().groups();
        GroupData group = groups.iterator().next();
        app.goTo().goToContactPage();

        if (contact.getGroups().isEmpty() || !contact.getGroups().contains(group)) {
            app.contact().selectDisplayGroup("[all]");
            app.contact().addContactToTheGroup(contact, group);
            assertThat(contact.getGroups().withAdded(group),
                    equalTo(app.db().contacts().stream().filter((c) -> c.getId() == contact
                            .getId()).collect(Collectors.toList()).get(0).getGroups()));
            app.goTo().goToContactPage();
        }

        app.contact().removeContactFromGroup(contact, group);
        app.goTo().goToContactPage();
        app.contact().selectDisplayGroup("[all]");
        assertThat(contact.getGroups().without(group), equalTo(app.db().contacts().stream().
                filter((c) -> c.getId() == contact.getId()).collect(Collectors.toList())
                .get(0).getGroups()));
    }
}
