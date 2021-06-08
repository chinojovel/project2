import lombok.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    private String subjectName;
    public static void createSubject() {
        Scanner subject = new Scanner(System.in);
        System.out.println("Input the subject name");
        String sub = subject.nextLine();
        Subject s = new Subject(sub);
        Main.subjects.add(s);
        System.out.println("Subject created!");
        Main.menu();
    }
    public static void loadSubject() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter de name of the txt file");
            String txt = s.nextLine();
            File myObj = new File(txt+".txt");
            Scanner myReader = new Scanner(myObj);
            if (myObj.exists()) {
                System.out.println("File name: " + myObj.getName());

            } else {
                System.out.println("The file does not exist.");
            }
            String sub = myObj.getName();
            Subject subjectt = new Subject(sub);
            Main.subjects.add(subjectt);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStr = data.split(" ");
                Student student =new Student(arrOfStr[0]);
                Enroll en=new Enroll(subjectt,student);
                Grade grade= new Grade(en,Double.parseDouble(arrOfStr[1]));
                Main.enroll.add(en);
                Main.grades.add(grade);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Main.menu();
    }
}
