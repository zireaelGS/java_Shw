package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTest extends TestBase {


    @Test
    public void testGroupDeletion() throws Exception {
        app.getNavigationHelper().goToGroupPage();
        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", null, "test3"));
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        int element = before.size() - 1;// here you can choose which group you want to delete
        app.getGroupHelper().selectGroup(element);
        app.getGroupHelper().deleteSelectedGroups();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() - 1);//check for compliance with quantity

        before.remove(element);//delete an extra element
        Assert.assertEquals(before, after);//check that we have deleted the group that we wanted

    }

}