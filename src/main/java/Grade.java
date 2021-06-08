import lombok.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor
public class Grade {
    private Enroll enroll;
    private double grade;
    public static void assignGrade(Enroll e) {
        Scanner input = new Scanner(System.in);
        System.out.println("Assign the grade to the student  " + e.getStudent().getStudentName());
        String inputGrade = input.nextLine();
        Validation.validateGrade(inputGrade, e);
    }

    public static void subjectsStudents(int m_option) {
        String   subject = Main.subjects.get(m_option-2).getSubjectName();
        int contador = 1;
        System.out.println("Choose one student to asign  the grade");
        for(Enroll en: Main.enroll){
            if (en.getSubject().getSubjectName().equals(subject)){
                System.out.println(contador +" "+ en.getStudent().getStudentName());
                contador=contador+1;
            }
        }
        Scanner enrollment = new Scanner(System.in);
        System.out.println("Input the number");
        String number = enrollment.nextLine();
        Enroll chosen= Main.enroll.get(Integer.parseInt(number));
        Grade.assignGrade(chosen);
    }
    public static void loaddata(int m_option)  {
        try {
            File myObj = new File("data.txt");
            Scanner myReader = new Scanner(myObj);
            if (myObj.exists()) {
                System.out.println("File name: " + myObj.getName());
                System.out.println("Absolute path: " + myObj.getAbsolutePath());
                System.out.println("Writeable: " + myObj.canWrite());
            } else {
                System.out.println("The file does not exist.");
            }
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStr = data.split(" ");
                Student student =new Student(arrOfStr[0]);
                Subject subject = new Subject(Main.subjects.get(m_option-2).getSubjectName());
                Enroll en=new Enroll(subject,student);
                Grade grade= new Grade(en,Double.parseDouble(arrOfStr[1]));
                Main.enroll.add(en);
                Main.grades.add(grade);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
