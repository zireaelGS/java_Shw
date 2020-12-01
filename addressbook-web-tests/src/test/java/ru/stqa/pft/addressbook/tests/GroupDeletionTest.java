package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().list().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupDeletion() throws Exception {

        List<GroupData> before = app.group().list();
        int index = before.size() - 1;// here you can choose which group you want to delete
        app.group().delete(index);
        List<GroupData> after = app.group().list();
        Assert.assertEquals(after.size(), before.size() - 1);//check for compliance with quantity

        before.remove(index);//delete an extra index
        Assert.assertEquals(before, after);//check that we have deleted the group that we wanted

    }


}