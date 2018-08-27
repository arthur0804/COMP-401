package a1;
import java.util.Scanner;

public class A1Jedi {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		processRecord(sc);
	}
	
	public static void processRecord(Scanner sc) {
		// create double arrays 
		double[] participation = new double[100];
		double[] assignment = new double[100];
		double[] midterm = new double[100];
		double[] finalexam = new double[100];
		
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
		
		// get the input and store into the array
		
		for(int i=0; i<class_size; i++) {
			String firstname = sc.next();
			String lastname = sc.next();
			double participationscore = (double) sc.nextInt(); // convert to double
			participation[i] = Math.min(100, (participationscore/(participationtoal*0.8))*100);
			
			// calculate the total assignment score for a single student
			double assignmentscore = 0;
			for (int j=0; j<assignmentnum; j++) {
				assignmentscore += sc.nextDouble();
				}
			assignment[i] = (assignmentscore / assignmenttotal) * 100;
			
			midterm[i] = (double) sc.nextInt();
			finalexam[i] = (double) sc.nextInt();
			}
		
			// calculate normalized and curved scores
			calculatescore(midterm, class_size);
			calculatescore(finalexam, class_size);
		
			print_grade(participation, assignment, midterm, finalexam, class_size);
	}
	
	public static void calculatescore(double[] scores, int class_size) {
		// calculate average
		double sumscore = 0;
		double avgscore = 0;
		for(int i=0; i<class_size; i++) {
			sumscore += scores[i];
		}
		avgscore = sumscore / class_size;
		
		// calculate SD
		double sd = 0;
		for(int i=0; i<class_size; i++) {
			sd += Math.pow((scores[i]-avgscore), 2);
		}
		sd = Math.sqrt(sd/class_size);
		
		for(int i=0; i<class_size; i++) {
			double normalizedscore = 0;
			// first calculate the normalized score
			normalizedscore = (scores[i] - avgscore) / sd;
			// then calculate the curved score and store into the midterm/finalexam array (replacing the original data)
			scores[i] = calculate_curved(normalizedscore);
		}
	}
	
	public static double calculate_curved(double normalized) {
		// pass in the normalized score, and then calculate the curved score
		double curvedsocre = 0;
		if(normalized >= 2.0) {
			curvedsocre = 100.0;
		}else if (normalized >=1.0) {
			curvedsocre = (((normalized - 1.0) / (2.0 -1.0)) * (100.0 - 94.0)) + 94.0;
		}else if (normalized >=0.0) {
			curvedsocre = (((normalized - 0.0) / (1.0 -0.0)) * (94.0 - 85.0)) + 85.0;
		}else if (normalized >=-1.0) {
			curvedsocre = (((normalized - (-1.0)) / (0.0 - (-1.0))) * (85.0 - 75.0)) + 75.0;
		}else if (normalized >=-1.5) {
			curvedsocre = (((normalized - (-1.5)) / ((-1.0) - (-1.5))) * (75.0 - 65.0)) + 65.0;
		}else if (normalized >= -2.0) {
			curvedsocre = (((normalized - (-2.0)) / ((-1.5) - (-2.0))) * (65.0 - 55.0)) + 55.0;
		}else if (normalized >= -3.0) {
			curvedsocre = (((normalized - (-3.0)) / ((-2.0) - (-3.0))) * (55.0 - 30.0)) + 30.0;
		}else if (normalized > -4.0){
			curvedsocre = (((normalized - (-4.0)) / ((-3.0) - (-4.0))) * (30.0 - 0.0)) + 0.0;
		}else {
			curvedsocre = 0.0;
		}	
		return curvedsocre;	
	}
	
	public static void print_grade(double[] participation, double[] assignment, double[] midterm, double[] finalexam, int class_size) {
		// create an array to store the number of students in each grade
		int[] grades = new int[11];
		
		for(int i=0; i<class_size; i++) {
			//calculate
			double WA = (0.4 * assignment[i]) +
					(0.15 * participation[i]) +
					(0.2 * midterm[i]) +
					(0.25 * finalexam[i]);
			
			// determine which grade
			if (WA >= 94) {
                grades[0]++;
            } else if (WA >= 90) {
                grades[1]++;
            } else if (WA >= 86) {
                grades[2]++;
            } else if (WA >= 83) {
                grades[3]++;
            } else if (WA >= 80) {
                grades[4]++;
            } else if (WA >= 76) {
                grades[5]++;
            } else if (WA >= 73) {
                grades[6]++;
            } else if (WA >= 70) {
                grades[7]++;
            } else if (WA >= 65) {
                grades[8]++;
            } else if (WA >= 60) {
                grades[9]++;
            } else {
                grades[10]++;
            }
		}
		
			System.out.println("A : " + grades[0]);
		    System.out.println("A-: " + grades[1]);
		    System.out.println("B+: " + grades[2]);
		    System.out.println("B : " + grades[3]);
		    System.out.println("B-: " + grades[4]);
		    System.out.println("C+: " + grades[5]);
		    System.out.println("C : " + grades[6]);
		    System.out.println("C-: " + grades[7]);
		    System.out.println("D+: " + grades[8]);
		    System.out.println("D : " + grades[9]);
		    System.out.println("F : " + grades[10]);
	}
	
}
