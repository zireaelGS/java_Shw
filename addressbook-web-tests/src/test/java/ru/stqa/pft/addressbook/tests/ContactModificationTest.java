package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Tom").withMiddlename("Marvolo").withLastname("Riddle")
                    .withNickname("Lord Voldemort").withTitle("LORD").withCompany("Death eaters").withAddress("The whole world")
                    .withHome("666").withMobile("666999").withEmail("VolodyaKiller@mail.ru").withBday("13").withBmonth("April")
                    .withByear("1938"));
        }
    }

    @Test
    public void testContactModification() {

        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;// here you can choose which contact you want to modificate
        ContactData contact = new ContactData().withId(before.get(index).getId()).withFirstname("Tomas").withMiddlename("Marvolo").withLastname("Riddle")
                .withNickname("Lord Voldemort").withTitle("LORD").withCompany("Death eaters").withAddress("The whole world")
                .withHome("666").withMobile("666999").withEmail("VolodyaKiller@mail.ru").withBday("13").withBmonth("April")
                .withByear("1938");
        app.contact().modify(index, contact);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());//check for compliance with quantity

        before.remove(index);//delete an old contact from the collection
        before.add(contact);//add a new contact to the collection==> update the collection with modificated contact
        //sorting lists for further comparison
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        app.goTo().contactPage();
        Assert.assertEquals(before, after);
    }

}
