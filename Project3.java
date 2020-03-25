package Project3;

import java.util.Random;

public class Project3 implements Runnable{
	
	public static int people;
	public static int minTime;
	public static int maxTime;
	public static int seed;
	
	public static int currentGender;
	public static int buffer = 0;
	public static int arrivalTime = 0;
	public static boolean lock = false;
	
	private static int id;
	private static int gender;
	private static int time;
	

	public static void Threads() {
			
		for(int i=0; i<people; i++) {
			Thread Pthread = new Thread(new Project3());	
			Pthread.setName(Integer.toString(i + 1));
			
			
			
			Pthread.start();
		}
	}

	
	public void OnePerson(int id, int gender, int time) {
		
		//person arives
		if (gender == 0) {
				System.out.println("Time = "+arrivalTime+";"
						+ " Person "+Thread.currentThread().getName()+" (F) arrives");
		}
		else {
			System.out.println("Time = "+arrivalTime+";"
					+ " Person "+Thread.currentThread().getName()+" (M) arrives");
		}
		
		
		try {
			Arrive(id, gender, time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public static void Arrive(int id, int gender, int time) throws InterruptedException {
		
		//for girls
		if (gender == 0) { //if you are a girl
			
			if (buffer == 0 && lock == false) { //and if no one is in the bathroom
				lock = true; //aquire the lock for girls
				
				currentGender = gender; //put a girls sign on the door
				System.out.println("Time = "+arrivalTime+"; Person "+Thread.currentThread().getName()+" (F) "
						+ "enters the facilities for "+time+" minutes");
				buffer++;
				
			}
			
			
			else if (buffer < 3 && lock == true && currentGender == 0) { //if there is room and girls have the lock
					System.out.println("Time = "+arrivalTime+"; Person "+Thread.currentThread().getName()+" (F) "
							+ "enters the facilities for "+time+" minutes");
					buffer++; //arrive in the bathroom
				}
			else if(buffer >= 3) {
				
				
			}
				else {
					//Thread.currentThread().wait();
				}
		}
		
		
		//for boys
		if (gender == 1) { //if you are a boy
			
			if(buffer == 0 && lock == false) { //and if no one is in the bathoom 
				lock = true; //aquire the lock for boys
				
				currentGender = gender; //put a boys sign on the door
				System.out.println("Time = "+arrivalTime+"; Person "+Thread.currentThread().getName()+" (M) "
						+ "enters the facilities for "+time+" minutes");
				buffer++;
				
			}
			
			else if (buffer < 3 && lock == true && currentGender == 1) { // if theres room and boys have the lock
				System.out.println("Time = "+arrivalTime+"; Person "+Thread.currentThread().getName()+" (M) "
						+ "enters the facilities for "+time+" minutes");
				buffer++; //arrive in the bathroom
			}
			else if(buffer >= 3) {
				
				
			}
			else {
				//Thread.currentThread().wait();
			}
			
			
			
			
		}
		
		
		
	}
	
	public void run() {
		
		
		if(Thread.currentThread().getName()== "main") {
			Threads();
			
		}
		else {
			
		
		
		Random randG = new Random();
		Random randT = new Random();
		//randG.setSeed(seed);
		//randT.setSeed(seed);
		
		
		gender = randG.nextInt(2);
		time = randT.nextInt((maxTime- minTime)+1) + minTime;
		id = Integer.parseInt(Thread.currentThread().getName());
		
		OnePerson(id , gender, time);
		
		
		
		
		
		
		//System.out.println(buffer);
		}
	
	}

	
	
	
	
}

class Simulate {
	
	public static void main(String[] args) {
	
		Project3.people = 5;
		Project3.minTime = 3;
		Project3.maxTime = 7;
		Project3.seed = 17;
		
		Thread main = new Thread(new Project3());
		main.setName("main");
		main.start();
		
		
		
		
	}	
}
