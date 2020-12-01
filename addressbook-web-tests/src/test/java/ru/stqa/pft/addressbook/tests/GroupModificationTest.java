package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupModifiction() {

        Set<GroupData> before = app.group().all();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId())
                .withName("test3").withHeader("header3").withFooter("footer3");
        app.group().modify(group);
        Set<GroupData> after = app.group().all();
        Assert.assertEquals(before.size(), after.size());//check for compliance with quantity

        before.remove(modifiedGroup);//delete an old group from the collection
        before.add(group);//add a new group to the collection==> update the collection with modificated group
        Assert.assertEquals(before, after);
    }


}
