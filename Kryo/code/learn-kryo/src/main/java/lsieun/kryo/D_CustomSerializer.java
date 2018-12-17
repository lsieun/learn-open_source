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
import lsieun.kryo.entity.PersonSerializer;

public class D_CustomSerializer {
    public static void main(String[] args) throws Exception {
        String workingDir = System.getProperty("user.dir");
        String filepath = workingDir + File.separator + "target/file-CustomSerializer-Person.bin";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = format.parse("1988-01-01");

        Person person = new Person();
        person.setName("Jerry");
        person.setAge(0);
        person.setBirthDate(birthDate);
        System.out.println(person);

        Kryo kryo = new Kryo();
        kryo.register(Person.class, new PersonSerializer());
        Output output = new Output(new FileOutputStream(filepath));
        Input input = new Input(new FileInputStream(filepath));

        kryo.writeObject(output, person);
        output.close();

        Person readPerson = kryo.readObject(input, Person.class);
        input.close();

        System.out.println(readPerson);
    }
}
