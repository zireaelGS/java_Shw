package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditions(){
        app.getNavigationHelper().goToContactPage();
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Tom", "Marvolo", "Riddle", "Lord Voldemort", "LORD", "Death eaters", "The whole world", "666", "666999", "VolodyaKiller@mail.ru", "13", "April", "1938"));
        }
    }
    @Test
    public void testContactModification() {

        List<ContactData> before = app.getContactHelper().getContactList();
        int index = before.size() - 1;// here you can choose which contact you want to modificate
        ContactData contact = new ContactData(before.get(index).getId(),"Tomp", "Marvolo", "Riddle", "Lord Voldemort", "LORD", "Death eaters", "The whole world", "666", "666999", "VolodyaKiller@mail.ru", "13", "April", "1938");
        app.getContactHelper().modifyContact(index, contact);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());//check for compliance with quantity

        before.remove(index);//delete an old contact from the collection
        before.add(contact);//add a new contact to the collection==> update the collection with modificated contact
        //sorting lists for further comparison
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        app.getNavigationHelper().goToContactPage();
        Assert.assertEquals(before, after);
    }

}
