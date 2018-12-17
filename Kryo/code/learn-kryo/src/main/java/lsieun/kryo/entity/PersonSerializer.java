package lsieun.kryo.entity;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class PersonSerializer extends Serializer<Person> {
    public void write(Kryo kryo, Output output, Person object) {
        output.writeString(object.getName());
        output.writeLong(object.getBirthDate().getTime());
    }

    public Person read(Kryo kryo, Input input, Class<Person> type) {
        Person person = new Person();
        person.setName(input.readString());
        long birthDate = input.readLong();
        person.setBirthDate(new Date(birthDate));
        person.setAge(calculateAge(birthDate));
        return person;
    }

    private int calculateAge(long birthTime) {
        Date birthDate = new Date();
        birthDate.setTime(birthTime);

        Date currentDate = new Date();

        return calculateAge(birthDate, currentDate);
    }

    private int calculateAge(Date birthDate, Date currentDate) {
        LocalDate from = toLocal(birthDate);
        LocalDate to = toLocal(currentDate);
        int age = Period.between(from, to).getYears();
        return age;
    }

    private LocalDate toLocal(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
}
