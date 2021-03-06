package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("title"), contactData.getTitle());
        type(By.name("company"), contactData.getCompany());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHome());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("email"), contactData.getEmail());
//        attach(By.name("photo"), contactData.getPhoto());
        wd.findElement(By.name("bday")).click();
        new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getBday());
        wd.findElement(By.name("bday")).click();
        wd.findElement(By.name("bmonth")).click();
        new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getBmonth());
        wd.findElement(By.name("bmonth")).click();
        wd.findElement(By.name("byear")).click();
        wd.findElement(By.name("byear")).clear();
        wd.findElement(By.name("byear")).sendKeys(contactData.getByear());

    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[id='" + id + "']")).click();
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void allertAccept() {
        wd.switchTo().alert().accept();
    }


    public void submitContactModification() {
        click(By.name("update"));
    }

    public void returnToContactPage() {
        click(By.linkText("home page"));
    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModificationById(contact.getId());
        fillContactForm(contact);
        submitContactModification();
        contactCache = null;
        returnToContactPage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        allertAccept();
        contactCache = null;
    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        contactCache = null;
        returnToContactPage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> rows = wd.findElements(By.xpath("//tr[contains(@name ,'entry')]"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("id"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).withAddress(address)
                    .withAllEmails(allEmails).withAllPhones(allPhones));
        }
        return contactCache;
    }

    public int count() {
       return wd.findElements(By.xpath("//tr[@name='entry']")).size();
    }

    public void initContactModificationById(int id) {
        wd.findElement(By.xpath("//a[@href='edit.php?id=" + id + "']")).click();
    }
    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname =wd.findElement(By.name("lastname")).getAttribute("value");
        String home=wd.findElement(By.name("home")).getAttribute("value");
        String work=wd.findElement(By.name("work")).getAttribute("value");
        String mobile=wd.findElement(By.name("mobile")).getAttribute("value");
        String address=wd.findElement(By.name("address")).getAttribute("value");
        String email=wd.findElement(By.name("email")).getAttribute("value");
        String email2=wd.findElement(By.name("email2")).getAttribute("value");
        String email3=wd.findElement(By.name("email3")).getAttribute("value");

        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withAddress(address)
                .withEmail(email).withEmail2(email2).withEmail3(email3);
    }

    public void selectDisplayGroup(String name) {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(name);
    }

    private void removeFromGroup(String name) {
        click(By.name("remove"));
    }
    public void addContactToTheGroup(ContactData contact, GroupData group) {
        selectContactById(contact.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
        click(By.name("add"));
    }

    public void removeContactFromGroup(ContactData contact, GroupData group) {
        selectDisplayGroup(group.getName());
        selectContactById(contact.getId());
        removeFromGroup(group.getName());
    }
}
