package navarro.cantero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Alumn implements Serializable {
    private static final long serialVersionUID = -8677161065045907537L;
    static int max = 0;
    private int id;
    private String name;
    private int lessons;

    public Alumn(int id, String name, int lessons) {
        super();
        this.id = id;
        this.name = name;
        this.lessons = lessons;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLessons(int lessons) {
        this.lessons = lessons;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLessons() {
        return lessons;
    }

    static void setMax() {
        ArrayList<Alumn> alumns = Alumn.fromFileToAlumns(Alumn.getPath());
        int aux = 0;
        for (Alumn alumn : alumns) {
            aux = alumn.getId();
            if (Alumn.max < aux) {
                Alumn.max = aux;
            }
        }

    }

    public static void alumnsToFile(ArrayList<Alumn> alumn, String filePath) {

        try {
            ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(filePath));
            fos.writeObject(alumn);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Alumn.setMax();
    }

    public static void addAlumnToFile(ArrayList<Alumn> alumns, String filePath, Alumn AlumnToAdd) {
        System.out.println(AlumnToAdd);
        alumns.add(AlumnToAdd);
        Alumn.alumnsToFile(alumns, Alumn.getPath());

    }

    public static void removeAlumnToFile(ArrayList<Alumn> alumns, String filePath, Alumn alumnToRemove) {

        Iterator<Alumn> it = alumns.iterator();

        while (it.hasNext()) {
            if (it.next().equals(alumnToRemove)) {
                it.remove();
                break;
            }
        }

        Alumn.alumnsToFile(alumns, Alumn.getPath());
    }

    public static void updateAlumnToFile(ArrayList<Alumn> alumns, String filePath, Alumn alumnToErase, Alumn alumnToUpdate) {
        Alumn.removeAlumnToFile(alumns, Alumn.getPath(), alumnToErase);
        Alumn.addAlumnToFile(alumns, Alumn.getPath(), alumnToUpdate);

    }

    public static ArrayList<Alumn> fromFileToAlumns(String filePath) {
        ArrayList<Alumn> alumns = new ArrayList<>();
        File file = new File(Alumn.getPath());
        if (file.exists() && !file.isDirectory()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
                try {

                    alumns = (ArrayList<Alumn>) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
//
                e.printStackTrace();
            }
        } else {
            alumns.add(new Alumn(1, "Marta", 2));
            alumns.add(new Alumn(2, "Jose", 2));
            alumns.add(new Alumn(3, "Pedro", 4));
            alumns.add(new Alumn(4, "Jaime", 1));
            Alumn.alumnsToFile(alumns, Alumn.getPath());
        }
        int aux = 0;
        for (Alumn alumn : alumns) {
            aux = alumn.getId();
            if (Alumn.max < aux) {
                Alumn.max = aux;
            }
        }
        return alumns;
    }

    static public String getPath() {
        Path currentPath = Paths.get("");
        String path = currentPath.toAbsolutePath().toString();
        return path + "/uf.dat";
    }

    @Override
    public String toString() {
        return "  id=" + id +
                ", name='" + name + '\'' +
                ", lessons=" + lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alumn)) return false;
        Alumn alumn = (Alumn) o;
        return getId() == alumn.getId() &&
                Objects.equals(getName(), alumn.getName()) &&
                Objects.equals(getLessons(), alumn.getLessons());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lessons);
    }
}
