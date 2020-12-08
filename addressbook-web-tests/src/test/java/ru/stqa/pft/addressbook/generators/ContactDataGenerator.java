package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;//кол-во контактов,которые надо создать

    @Parameter(names = "-f", description = "Target file")
    public String file;//путь к файлу

    @Parameter(names = "-d", description = "Data format")
    public String format;//задаем формат

    public static void main(String[] args) throws IOException {
        //действуем согласно документации JCommander
        ContactDataGenerator generator = new ContactDataGenerator();//создаем объект текущего класса
        JCommander jCommander = new JCommander(generator);//создаем объект типа JCommander и помешаем в локальную переменную
        //конструкция,которая показывает,как именно правильно надо запустить программу,если были введены неверные аргументы
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();//запускаем ран


      /*  так мы создавали файл до того,как узнали о библиотеке jCommander
      т.к. необходимо,чтобы пользователь понимал,что именно он сделал неправильно,
      передавая параметры для генерации данных, произведена замена этой реализации реализацию через jCommander

        int count = Integer.parseInt(args[0]);//кол-во контактов,которые надо создать
        File file = new File(args[1]);//путь к файлу

        List<ContactData> contacts = generateContact(count);//генерируем данные
        save( contacts,file);//сохраняем эти данные в файл
        */
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContact(count);//генерируем данные
        if (format.equals("csv")) {
            saveAsCsv(contacts, new File(file));//сохранение данных в файл
        } else if ((format.equals("xml"))) {
            saveAsXml(contacts, new File(file));
        } else if ((format.equals("json"))) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("Unrecognized format " + format);
        }
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson =new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contacts);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }

    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n"
                    , contact.getFirstname(), contact.getMiddlename(), contact.getLastname(), contact.getNickname()
                    , contact.getTitle(), contact.getCompany(), contact.getAddress(), contact.getHome()
                    , contact.getMobile(), contact.getEmail(), contact.getEmail2(), contact.getEmail3()
                    , contact.getBday(), contact.getBmonth(), contact.getByear(), contact.getPhoto()));
        }
        writer.close();
    }

    private List<ContactData> generateContact(int count) {
        File photo = new File("src/test/resources/tom.jpg");
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().withFirstname(String.format("TomG %s", i)).withMiddlename(String.format("Marvolo %s", i))
                    .withLastname(String.format("Riddle %s", i)).withNickname(String.format("Lord Voldemort %s", i))
                    .withTitle(String.format("LORD %s", i)).withCompany(String.format("Death eaters %s", i))
                    .withAddress(String.format("The whole world %s", i)).withHome(String.format("666 %s", i))
                    .withMobile(String.format("666999 %s", i)).withEmail(String.format("VolodyaKiller@mail.ru %s", i))
                    .withEmail2(String.format("Vovan@mail.ru %s", i)).withEmail3(String.format("Vovchik@mail.ru %s", i))
                    .withBday("12").withBmonth("April").withByear("1932").withPhoto(photo));
        }
        return contacts;
    }
}
