package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GroupModificationTest extends TestBase {
    @Test
    public void testGroupModifiction() {
        app.getNavigationHelper().goToGroupPage();
        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", null, "test3"));
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        int element = before.size() - 1;// here you can choose which group you want to modificate
        app.getGroupHelper().selectGroup(element);
        app.getGroupHelper().initGroupModification();
        GroupData group = new GroupData(before.get(element).getId(),"123", "testmod2", "testmod3");
        app.getGroupHelper().fillGroupForm(group);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(before.size(), after.size());//check for compliance with quantity

        before.remove(element);//delete an old group from the collection
        before.add(group);//add a new group to the collection==> update the collection with modificated group
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));//convert two lists into sets and compare without regard to order
    }
}
