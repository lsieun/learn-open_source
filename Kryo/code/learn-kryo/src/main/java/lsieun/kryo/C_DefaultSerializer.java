package lsieun.kryo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import lsieun.kryo.entity.Person;

public class C_DefaultSerializer {
    public static void main(String[] args) throws Exception {
        String workingDir = System.getProperty("user.dir");
        String filepath = workingDir + File.separator + "target/file-DefaultSerializer-Person.bin";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = format.parse("2000-10-10");

        Person person = new Person();
        person.setName("Tom");
        person.setAge(10);
        person.setBirthDate(birthDate);
        System.out.println(person);

        Kryo kryo = new Kryo();
        Output output = new Output(new FileOutputStream(filepath));
        kryo.writeObject(output, Person.class);
        output.close();

        Input input = new Input(new FileInputStream(filepath));
        Person readPerson = kryo.readObject(input, Person.class);
        input.close();

        System.out.println(readPerson);
    }
}
