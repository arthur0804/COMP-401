package a1;
import java.util.Scanner;

public class A1Adept {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		processRecord(sc);
	}
	
	public static void processRecord(Scanner sc) {
		
		// get the assignment numbers
		int assignmentnum = sc.nextInt();
		int assignmenttotal = 0;
		
		// calculate the assignment total score
		for(int i=0; i<assignmentnum; i++) {
			assignmenttotal += sc.nextInt();
		}
		
		// get the total participation
		int participationtoal = sc.nextInt();
		
		// get the class size
		int class_size = sc.nextInt();
		
		for(int i=0; i<class_size; i++) {
			// accept the input
			String firstname = sc.next();
			String lastname = sc.next();
			double participationscore = (double) sc.nextInt(); // convert to double
			
			// calculate the total assignment score for a single student
			double assignmentscore = 0;
			for (int j=0; j<assignmentnum; j++) {
				assignmentscore += sc.nextDouble();
			}
			
			double midterm = sc.nextDouble();
			double finalexam = sc.nextDouble();
			
			// calculate assignment
			double assignment = (assignmentscore / assignmenttotal) * 100;
			
			// calculate participation
			double participation = (participationscore/(participationtoal*0.8))*100;
			if(participation > 100) {
				participation = 100;
			}
			
			// calculate WA
			double WA = (0.4 * assignment) +
						(0.15 * participation) +
						(0.2 * midterm) +
						(0.25 * finalexam);
			
			// judge the grade
			String letter_grade;
            if (WA >= 94) {
                letter_grade = "A";
            } else if (WA >= 90) {
                letter_grade = "A-";
            } else if (WA >= 86) {
                letter_grade = "B+";
            } else if (WA >= 83) {
                letter_grade = "B";
            } else if (WA >= 80) {
                letter_grade = "B-";
            } else if (WA >= 76) {
                letter_grade = "C+";
            } else if (WA >= 73) {
                letter_grade = "C";
            } else if (WA >= 70) {
                letter_grade = "C-";
            } else if (WA >= 65) {
                letter_grade = "D+";
            } else if (WA >= 60) {
                letter_grade = "D";
            } else {
                letter_grade = "F";
            }
            
            // print the result
			System.out.println(firstname.substring(0, 1) + ". " + lastname + " " + letter_grade);	
		}
	}
}
