import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static ArrayList<Subject> subjects= new ArrayList<>();
    public static ArrayList<Enroll> enroll= new ArrayList<>();
    public static ArrayList<Grade> grades=new ArrayList<>();
  public static void main(String[] args) {

    Subject sub = new Subject("Databases");
    subjects.add(sub);
    menu();
    Stadistic.print();
  }
  //Main menu of the app
  public static int menu(){
    int contador = 2;
    Scanner menu = new Scanner(System.in);
    System.out.println("Input your choice to navigate in the menu \n0. Generate Reports and exit \n1. Add a subject");
    for (Subject s : subjects) {
      System.out.println(contador +". "+ s.getSubjectName());
      contador=contador+1;
    }
    String option = menu.nextLine();
    int m_option= Validation.validateOptionMenu(option);
    if (m_option>1) {
      int s_option = submenu();
      navigate(s_option,m_option);
    }
    else if (m_option == 1){
        Scanner s = new Scanner(System.in);
        System.out.println("Do you want to load the subject from a file? (y/n)");
        String sub = s.nextLine();
        Pattern pattern = Pattern.compile("(^[yY]$)");
        Matcher matcher = pattern.matcher(sub);
        boolean matchFound = matcher.find();
        if (matchFound) {
            Subject.loadSubject();
        }
        else{
            Subject.createSubject();
        }
        }
    else if(m_option==0){
        System.out.println("Exit");
    }
    return 0;
  }
  private static void navigate(int s_option, int m_option) {
    switch (s_option){
      case 0:
        System.out.println("Salir");
        break;
      case 1:
        Grade.loaddata(m_option);
        break;
      case 2:
        Enroll.enrollstudent(m_option);
        break;
      case 3:
        Grade.subjectsStudents(m_option);
        break;
      case 4:
      Stadistic.print();
      break;
    }
    menu();
  }
  //This Method asign a Grade to a Student in  a  specific Subject

  //Validate Menu options Method

  //Submenu Method
  public static int submenu(){
    Scanner menu = new Scanner(System.in);
    System.out.println("Input your choice to navigate in the menu \n0.Go back to the menu\n1.Load file (subject) \n2.Add student to subject \n3.Assign Grade to Student\n 4.Generate and send Reports");
    String opcion = menu.nextLine();
        return Validation.validateOptionSubMenu(opcion);
  }
  //Validate Option of the SubMenu Method

}
