package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {
    @Test
    public void testContactModification() {
        app.getNavigationHelper().goToContactPage();
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Tom", "Marvolo", "Riddle", "Lord Voldemort", "LORD", "Death eaters", "The whole world", "666", "666999", "VolodyaKiller@mail.ru", "13", "April", "1938"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        int element = before.size() - 1;// here you can choose which contact you want to modificate
        app.getContactHelper().selectContact(element);
        app.getContactHelper().initContactModification(element);
        ContactData contact = new ContactData(before.get(element).getId(),"Tomp", "Marvolo", "Riddle", "Lord Voldemort", "LORD", "Death eaters", "The whole world", "666", "666999", "VolodyaKiller@mail.ru", "13", "April", "1938");
        app.getContactHelper().fillContactForm(contact);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());//check for compliance with quantity

        before.remove(element);//delete an old contact from the collection
        before.add(contact);//add a new contact to the collection==> update the collection with modificated contact
        //sorting lists for further comparison
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        app.getNavigationHelper().goToContactPage();
        Assert.assertEquals(before, after);
    }
}
