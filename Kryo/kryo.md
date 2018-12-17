# Kryo

Github:

- [ ] https://github.com/EsotericSoftware/kryo

Tutorial:

- [ ] https://www.baeldung.com/kryo

<!-- TOC -->

- [1. Overview](#1-overview)
- [2. Maven Dependency](#2-maven-dependency)
- [3. Kryo Basics](#3-kryo-basics)
    - [3.1. Introduction](#31-introduction)
    - [3.2. Serializing Objects](#32-serializing-objects)
- [4. Serializers](#4-serializers)
    - [4.1. Default Serializers](#41-default-serializers)
    - [4.2. Custom Serializers](#42-custom-serializers)
    - [4.3. Java Serializer](#43-java-serializer)

<!-- /TOC -->

## 1. Overview

Kryo is a Java serialization framework with a focus on speed, efficiency, and a user-friendly API.

Kryo in general is **not stable across different JVMs** unless you are very careful about how you register types.

## 2. Maven Dependency

The first thing we need to do is to add the kryo dependency to our `pom.xml`:

```xml
<dependency>
   <groupId>com.esotericsoftware</groupId>
   <artifactId>kryo</artifactId>
   <version>5.0.0-RC1</version>
</dependency>
```

The latest version of this artifact can be found on [Maven Central](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22com.esotericsoftware%22%20AND%20a%3A%22kryo%22) or [Github](https://github.com/EsotericSoftware/kryo#with-maven).


## 3. Kryo Basics

### 3.1. Introduction

The framework provides the `Kryo` class as the main entry point for all its functionality.


> Kryo是主要的类

This class orchestrates(使合谐地结合起来) the serialization process and maps **classes** to `Serializer` instances which handle the details of converting **an object’s graph** to **a byte representation**.

> Kryo将内存中的object转换成byte array。

Once the bytes are ready, they’re written to a stream using an `Output` object. This way they can be stored in a file, a database or transmitted over the network.

> 使用Output将byte array进行输出，可以输出到文件、数据库或通过网络传输

Later, when the object is needed, an `Input` instance is used to read those bytes and decode them into Java objects.

> 使用Input将byte array进行输出

### 3.2. Serializing Objects

Now, we can look a how easy is to write and read an object using Kryo:

```java
package lsieun.kryo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class A_String {
    public static void main(String[] args) throws Exception {
        String workingDir = System.getProperty("user.dir");
        String filepath = workingDir + File.separator + "target/file-String.bin";
        Object someObject = "Hello World";

        Kryo kryo = new Kryo();
        Output output = new Output(new FileOutputStream(filepath));
        Input input = new Input(new FileInputStream(filepath));

        kryo.writeClassAndObject(output, someObject);
        output.close();

        Object theObject = kryo.readClassAndObject(input);
        input.close();

        System.out.println(theObject);
    }
}

```

Notice the call to the `close()` method. This is needed since `Output` and `Input` classes inherit from `OutputStream` and `InputStream` respectively.

Serializing multiple objects is similarly straightforward:

```java
package lsieun.kryo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class B_StringAndDate {
    public static void main(String[] args) throws Exception {
        String workingDir = System.getProperty("user.dir");
        String filepath = workingDir + File.separator + "target/file-StringAndDate.bin";

        String someString = "HelloWorld";
        Date someDate = new Date(915170400000L);
        System.out.println(someString);
        System.out.println(someDate);

        Kryo kryo = new Kryo();
        Output output = new Output(new FileOutputStream(filepath));
        Input input = new Input(new FileInputStream(filepath));

        kryo.writeObject(output, someString);
        kryo.writeObject(output, someDate);
        output.close();

        String readString = kryo.readObject(input, String.class);
        Date readDate = kryo.readObject(input, Date.class);
        input.close();

        System.out.println(readString);
        System.out.println(readDate);
    }

}

```

Notice that we’re passing **the appropriate class** to the `readObject()` method, this makes our code cast-free.

## 4. Serializers

### 4.1. Default Serializers

When Kryo serializes an object, it creates an instance of a previously registered `Serializer` class to do the conversion to bytes. These are called **default serializers** and can be used without any setup(设置；设定) on our part.

The library already provides several such serializers that process primitives, lists, maps, enums, etc. If no serializer is found for a given class, then a `FieldSerializer` is used, which can handle almost any type of object.

Let’s see how this looks like. First, lets’ create a `Person` class:

```java
package lsieun.kryo.entity;

import java.util.Date;

public class Person {
    private String name;
    private int age;
    private Date birthDate;

    public Person() {
    }

    public Person(String name, int age, Date birthDate) {
        this.name = name;
        this.age = age;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthDate=" + birthDate +
                '}';
    }
}

```

Now, let’s write an object from this class and then read it back:

> 注意：我的代码执行的时候出错了，网上查了一下，或许是由于Kryo对于JVM有依赖性。

```java
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

```

Notice that we didn’t have to specify anything to serialize a `Person` object since a `FieldSerializer` is created automatically for us.

### 4.2. Custom Serializers

If we need more control over the serialization process, we have **two options**; we can write our own `Serializer` class and register it with Kryo or let the class handle the serialization by itself.

To demonstrate the first option, let’s create a class that extends `Serializer`:

```java
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

```

Now, let’s put it to test:

```java
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

```

We can also use the `@DefaultSerializer` annotation to let Kryo know we want to use the `PersonSerializer` each time it needs to handle a `Person` object. This helps avoid the call to the `register()` method:

```java
@DefaultSerializer(PersonSerializer.class)
public class Person implements KryoSerializable {
    // ...
}
```

For the second option, let’s modify our `Person` class to extend the `KryoSerializable` interface:

```java
public class Person implements KryoSerializable {
    // ...
 
    public void write(Kryo kryo, Output output) {
        output.writeString(name);
        // ...
    }
 
    public void read(Kryo kryo, Input input) {
        name = input.readString();
        // ...
    }
}
```

### 4.3. Java Serializer

In sporadic(偶尔发生的) cases, Kryo won’t be able to serialize a class. If this happens, and writing a custom serializer isn’t an option, we can use **the standard Java serialization mechanism** using a `JavaSerializer`. This requires that the class implements the `Serializable` interface as usual.

Here’s an example that uses the aforementioned serializer:

```java
public class ComplexObject implements Serializable {
    private String name = "Bael";
     
    // standard getters and setters
}
```

```java
ComplexClass complexObject = new ComplexClass();
kryo.register(ComplexClass.class, new JavaSerializer());

kryo.writeObject(output, complexObject);
output.close();

ComplexClass readComplexObject = kryo.readObject(input, ComplexClass.class);
input.close();
```


