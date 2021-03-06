package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if(app.db().groups().size()==0){
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupModifiction() {
        if(Boolean.getBoolean("verifyUI")) {
            Groups before = app.db().groups();
            GroupData modifiedGroup = before.iterator().next();
            GroupData group = new GroupData().withId(modifiedGroup.getId())
                    .withName("test3").withHeader("header3").withFooter("footer3");
            app.goTo().groupPage();
            app.group().modify(group);
            assertThat(app.group().count(), equalTo(before.size()));
            Groups after = app.db().groups();
            assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
            verifyGroupListInUI();
        }
    }




}
