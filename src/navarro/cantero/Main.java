package navarro.cantero;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Main {


    public static void main(String[] args) throws InterruptedException, IOException {


        MyTable myTable = new MyTable();
        myTable.go();

    }
}
