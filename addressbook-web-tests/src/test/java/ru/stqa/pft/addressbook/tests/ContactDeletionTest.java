package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().goToContactPage();
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Tom", "Marvolo", "Riddle", "Lord Voldemort", "LORD", "Death eaters", "The whole world", "666", "666999", "VolodyaKiller@mail.ru", "13", "April", "1938"));
        }
    }

    @Test
    public void testContactDeletion() {

        List<ContactData> before = app.getContactHelper().getContactList();
        int element = before.size() - 1;// here you can choose which contact you want to delete
        app.getContactHelper().selectContact(element);
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().allertAccept();
        app.getNavigationHelper().goToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);//check for compliance with quantity

        before.remove(element);//delete an extra element
        Assert.assertEquals(before, after);//check that we have deleted the contact that we wanted

    }
}
