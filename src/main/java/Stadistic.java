import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class Stadistic {
    public static  void print(){
        int cont =0;
        double acum;
        double[][] ming = new double[Main.subjects.size()][1];
        double[][] maxg =new double[Main.subjects.size()][1];
        StringBuilder tabla= new StringBuilder();
        StringBuilder min= new StringBuilder();
        StringBuilder max= new StringBuilder();
        StringBuilder rmin= new StringBuilder();
        StringBuilder rmax= new StringBuilder();
        StringBuilder avrg= new StringBuilder();
        StringBuilder total= new StringBuilder();
        HashMap<Double, Integer> repeated = new HashMap<>();

        tabla.append("////////////////////////////////////\n")
                .append("All the students signed in a subject")
                .append("\n////////////////////////////////////\n");
        avrg.append("////////////////////////////////////\n")
                .append("Average Grades per Subject")
                .append("\n////////////////////////////////////\n");
        for (Subject sub:Main.subjects) {
            int iterator=0;
            acum=0;
            ming[cont][0]=11;
            maxg[cont][0]=-1;

            tabla.append(sub.getSubjectName())
                    .append("\n---------------------------\n");
            avrg.append(sub.getSubjectName());

            for(Grade grade: Main.grades){
                if (sub.getSubjectName().equals(grade.getEnroll().getSubject().getSubjectName())){
                    double value =grade.getGrade();
                    acum= acum+ value;
                    tabla.append("\n")
                            .append(grade.getEnroll().getStudent().getStudentName())
                            .append("\t")
                            .append(grade.getGrade())
                            .append("\n");
                    //Take of the min grade in this subject
                    if(ming[cont][0]> value){
                        ming[cont][0]= value;
                    }
                    //Take of the max grade in this subject
                    if (maxg[cont][0]< value){
                        maxg[cont][0]= value;
                    }
                    //Asign to the hashmap the grades as key and initializate the times that grades is in the hashmap, this is for the most and less repeated
                    if (repeated.containsKey(value)) {
                        repeated.replace(value, repeated.get(value) + 1);
                    } else {
                        repeated.put(value, 1);
                    }
                    iterator++;
                }
            }
            //End of for
            //Begining of the Most and Less Repeated
            rmax.append("////////////////////////////////////\n")
                    .append("THE MOST REPEATED GRADE")
                    .append("\n////////////////////////////////////\n");
            rmin.append("////////////////////////////////////\n")
                    .append("THE LESS REPEATED GRADE")
                    .append("\n////////////////////////////////////\n");
            int minr = 1000000;
            int maxr = -1;
            double lesskey = 0;
            double morekey=-0;
            for (Double key : repeated.keySet()) {
                if (minr > repeated.get(key)) {
                    minr = repeated.get(key);
                    lesskey = key;
                }
                if (maxr < repeated.get(key)) {
                    maxr = repeated.get(key);
                    morekey = key;
                }
            }

            rmin.append(sub.getSubjectName())
                    .append("\t Grade: ")
                    .append(lesskey)
                    .append("\t #Repetitions: ")
                    .append(minr)
                    .append("\n---------------------------\n");
            rmax.append(sub.getSubjectName())
                    .append("\t Grade: ")
                    .append(morekey)
                    .append("\t #Repetitions: ")
                    .append(maxr)
                    .append("\n---------------------------\n");

            for (Grade rgrade : Main.grades){
                if (sub.getSubjectName().equals(rgrade.getEnroll().getSubject().getSubjectName())){
                    if (lesskey==rgrade.getGrade()){
                        rmin.append("\n")
                                .append(rgrade.getEnroll().getStudent().getStudentName())
                                .append("\n");
                    }
                    else if (morekey == rgrade.getGrade()){
                        rmax.append("\n")
                                .append(rgrade.getEnroll().getStudent().getStudentName())
                                .append("\n");
                    }
                }
            }
            rmin.append("---------------------------\n");
            rmax.append("---------------------------\n");
            repeated.clear();
            //END OF THE MOST AND LESS REPEATED
            avrg.append("\t")
                    .append(acum/iterator)
                    .append("\n---------------------------\n");
            cont = cont + 1;
            tabla.append("---------------------------\n");
        }
        //End of for
        cont =0;
        min.append("\n////////////////////////////////////\n")
                .append("Students with the min grade")
                .append("\n////////////////////////////////////\n");

        max.append("\n////////////////////////////////////\n")
                .append("Students with the max grade")
                .append("\n////////////////////////////////////\n");

        for (Subject sub:Main.subjects) {
            min.append(sub.getSubjectName())
                    .append(" con ")
                    .append(ming[cont][0])
                    .append("\n---------------------------\n");

            max.append(sub.getSubjectName())
                    .append(" con ")
                    .append(maxg[cont][0])
                    .append("\n---------------------------\n");
            for(Grade grade: Main.grades){
                if (sub.getSubjectName().equals(grade.getEnroll().getSubject().getSubjectName())){
                    if(ming[cont][0]==grade.getGrade()){
                        min.append("\n")
                                .append(grade.getEnroll().getStudent().getStudentName())
                                .append("\n");
                    }
                    if (maxg[cont][0]==grade.getGrade()){
                        max.append("\n")
                                .append(grade.getEnroll().getStudent().getStudentName())
                                .append("\n");
                    }
                }
            }
            cont = cont + 1;
            min.append("---------------------------\n");
            max.append("---------------------------\n");
        }
        total.append(rmin)
                .append(rmax)
                .append(avrg)
                .append(min)
                .append(max)
                .append(tabla);
//BEFORE PRINT IN TXT AND PDF
        StringBuilder pack = new StringBuilder();
        Scanner em = new Scanner(System.in);
        System.out.println("Please enter your email");
        String email = em.nextLine();
//BEGINING OF PRINT IN TXT AND PDF
        pack.append(email).append("\n").append(total);
        try {
            File myObj = new File("stadistics.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("stadistics.txt");
            myWriter.write(pack.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        String FILE_NAME = "stadistics.pdf";
        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(FILE_NAME));
            //open
            document.open();
            Paragraph p = new Paragraph();
            p.add(email);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            Paragraph p2 = new Paragraph();
            p2.add(total.toString()); //no alignment
            document.add(p2);
            //close
            document.close();
            System.out.println("Done");

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
        // HERE BEGIN THE EMAIL METHOD
        final String fromEmail = "mauricioricaldone14@gmail.com"; //requires valid gmail id
        final String password = "icucmmrtirlvndma"; // correct password for gmail id
        final String toEmail = "hj15001@ues.edu.sv"; // can be any email id

        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");
        EmailUtil.sendAttachmentEmail(session, toEmail,"SSLEmail Testing Subject with Attachment", "SSLEmail Testing Body with Attachment");

}

}
