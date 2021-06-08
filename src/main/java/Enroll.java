import lombok.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor
public class Enroll {
    private Subject subject;
    private Student student;

    public static String enrollstudent(int m_option) {
        Scanner enrollment = new Scanner(System.in);
        System.out.println("Input the student name");
        String name = enrollment.nextLine();
        String v_name = Validation.validateEnrollment(name, m_option);
        Student s= new Student(v_name);
        System.out.println(m_option);
        Enroll e= new Enroll(Main.subjects.get(m_option-2),s);
        Main.enroll.add(e);
        Scanner yon = new Scanner(System.in);
        System.out.println("Do you want to assign a grade to this student (y/n)?");
        String decision = yon.nextLine();
        Validation.validateDecision(decision,e);
        return name;
    }


}
