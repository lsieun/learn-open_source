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
