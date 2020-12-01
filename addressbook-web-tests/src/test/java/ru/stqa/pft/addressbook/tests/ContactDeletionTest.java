package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTest extends TestBase {
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
    public void testContactDeletion() {

        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;// here you can choose which contact you want to delete
        app.contact().delete(index);
        app.goTo().contactPage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);//check for compliance with quantity

        before.remove(index);//delete an extra index
        Assert.assertEquals(before, after);//check that we have deleted the contact that we wanted

    }


}
