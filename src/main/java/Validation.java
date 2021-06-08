import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validation
{
    static int validateOptionMenu(String opcion) {
        Pattern pattern = Pattern.compile("([^0-9])");
        Matcher matcher = pattern.matcher(opcion);
        boolean matchFound = matcher.find();
        if (matchFound ||opcion.isEmpty()) {
            System.out.println("Please select a valid choice");
            return Main.menu();
        }
        else{
            if (Integer.parseInt(opcion)<-1 || Integer.parseInt(opcion)>Main.subjects.size()+1){
                System.out.println("Out of range value please select a valid choice");
                return Main.menu();
            }
        }
        return Integer.parseInt(opcion);
    }
    static String validateEnrollment(String name, int m_option) {
        Pattern pattern = Pattern.compile("([^a-zA-Z ])");
        Matcher matcher = pattern.matcher(name);
        boolean matchFound = matcher.find();
        if (matchFound ||name.isEmpty()) {
            System.out.println("Please enter a valid name");
            return Enroll.enrollstudent(m_option);
        }
        return name;
    }
    static void validateDecision(String decision, Enroll e) {
        Pattern pattern = Pattern.compile("(^[ynYN]$)");
        Matcher matcher = pattern.matcher(decision);
        boolean matchFound = matcher.find();
        if (matchFound) {
            Grade.assignGrade(e);
        } else {
            System.out.println("You're going to the menu");
        }
    }
    static void validateGrade(String inputGrade, Enroll e) {
        Pattern pattern = Pattern.compile("(^\\d\\.?\\d?\\d?$)");
        Matcher matcher = pattern.matcher(inputGrade);
        boolean matchFound = matcher.find();
        if (matchFound) {
            if (Double.parseDouble(inputGrade) >= 0 && Double.parseDouble(inputGrade) <= 10) {
                Grade grade= new Grade(e,Double.parseDouble(inputGrade));
                Main.grades.add(grade);

            } else{
                System.out.println("Error, Out of range");
                Grade.assignGrade(e);
            }
        } else {
            System.out.println("Error enter a valid grade");
            Grade.assignGrade(e);
        }
    }
    static int validateOptionSubMenu(String opcion) {
        Pattern pattern = Pattern.compile("([^0-9])");
        Matcher matcher = pattern.matcher(opcion);
        boolean matchFound = matcher.find();
        if (matchFound ||opcion.isEmpty()) {
            System.out.println("Please select a valid choice");
            return Main.submenu();
        }
        else {
            if (Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >4) {
                System.out.println("Out of range value please select a valid choice");
                return Main.submenu();
            }
        }
        return Integer.parseInt(opcion);
    }
}
