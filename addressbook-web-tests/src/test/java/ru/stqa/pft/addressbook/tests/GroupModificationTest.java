package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.*;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().list().size() == 0) {
            app.group().create(new GroupData("test1", null, "test3"));
        }
    }

    @Test
    public void testGroupModifiction() {

        List<GroupData> before = app.group().list();
        int index = before.size() - 1;// here you can choose which group you want to modificate
        GroupData group = new GroupData(before.get(index).getId(), "123", "testmod2", "testmod3");
        app.group().modify(index, group);
        List<GroupData> after = app.group().list();
        Assert.assertEquals(before.size(), after.size());//check for compliance with quantity

        before.remove(index);//delete an old group from the collection
        before.add(group);//add a new group to the collection==> update the collection with modificated group
        //sorting lists for further comparison
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }


}
