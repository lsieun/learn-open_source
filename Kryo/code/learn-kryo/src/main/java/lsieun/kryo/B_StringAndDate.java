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
