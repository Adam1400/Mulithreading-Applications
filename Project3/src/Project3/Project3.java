package Project3;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Project3 implements Runnable{
	//initialized all variables
	public static int people;
	public static int minTime;
	public static int maxTime;
	public static int seed;
	public static int delay =0;
	public static int increment=0;
	
	
	public static int currentGender;
	public static int buffer = 0;
	public static int currentTime = 0;
	public static boolean lock = false;
	public static int longestTime;
	public static int counter =1;
	
	private static int id;
	private static int gender;
	private static int time;
	
	//Created a lock for the buffer
	private final static Lock bufflock = new ReentrantLock();
	
	//Created the threads (people)
	public static void Threads() {
		for(int i=0; i<people; i++) {
			if(i==increment)
			{
				try {
					TimeUnit.MILLISECONDS.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Thread Pthread = new Thread(new Project3());	
			Pthread.setName(Integer.toString(i + 1));
						
			Pthread.start();
		}
	}

	//created when a person arrives.
	public void OnePerson(int id, int gender, int time) {
		
		//person arrives
		if (gender == 0) {
				System.out.println("Time = "+currentTime+";"
						+ " Person "+Thread.currentThread().getName()+" (F) arrives");
		}
		else {
			System.out.println("Time = "+currentTime+";"
					+ " Person "+Thread.currentThread().getName()+" (M) arrives");
		}
		
		
		//Called the arrive function
		try {
			Arrive(id, gender, time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		UseFacilities(id, gender, time);
		
		Depart(id, gender, time);
	
	}
	
	//Created the arrive function
	@SuppressWarnings("deprecation")
	public static void Arrive(int id, int gender, int time) throws InterruptedException {
		
		//locked the buffer
		bufflock.lock();
		boolean waiting  = true;
		
		//Looped through to check and see what gender is in the bathroom
		while (waiting == true) {
			
			if(Integer.parseInt(Thread.currentThread().getName()) > counter) {
				TimeUnit.NANOSECONDS.sleep(id);	
			}
		
			//for girls
			if (gender == 0) { //if you are a girl
				
				if (buffer == 0 && lock == false) { //and if no one is in the bathroom
					lock = true; //Acquire the lock for girls
					
					currentGender = gender; //put a girls sign on the door
					System.out.println("Time = "+currentTime+"; Person "+Thread.currentThread().getName()+" (F) "
							+ "enters the facilities for "+time+" minutes");
					buffer++;
					counter++;
					break;
					
				
				}
				
				
				if (buffer < 3 && lock == true && currentGender == 0) { //if there is room and girls have the lock
						System.out.println("Time = "+currentTime+"; Person "+Thread.currentThread().getName()+" (F) "
								+ "enters the facilities for "+time+" minutes");
						buffer++; //arrive in the bathroom
						counter++;
						break;
					
					}
				
				else {
					TimeUnit.MILLISECONDS.sleep(1);
					
					continue;
					
				}
			}
			
			//for boys
			if (gender == 1) { //if you are a boy
				
				
				if(buffer == 0 && lock == false) { //and if no one is in the bathoom 
					lock = true; //acquire the lock for boys
					
					currentGender = gender; //put a boys sign on the door
					System.out.println("Time = "+currentTime+"; Person "+Thread.currentThread().getName()+" (M) "
							+ "enters the facilities for "+time+" minutes");
					buffer++;
					counter++;
					break;
				
					
				}
				
				if (buffer < 3 && lock == true && currentGender == 1) { // if theres room and boys have the lock
					System.out.println("Time = "+currentTime+"; Person "+Thread.currentThread().getName()+" (M) "
							+ "enters the facilities for "+time+" minutes");
					buffer++; //arrive in the bathroom
					counter++;
					break;
				}
				
				else {
					TimeUnit.MILLISECONDS.sleep(1);
					continue;
					
				}
			}
			
		}
		
		//System.out.println(buffer);
	}
	
	//Tracked who was in the facility
	public static void UseFacilities(int id, int gender, int time) {
		
		bufflock.unlock();
		
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (time > longestTime) {
			
			longestTime = time;
			
		}
		
		
	}
	
	//Tracked when people were leaving the bathroom
	public static void Depart(int id, int gender, int time) {
		
		buffer--;
		if (gender == 0) {
			System.out.println("@ Time "+(currentTime+time)+"; Person "+id+" (F) exits");
			
		}
		else {
			System.out.println("@ Time "+(currentTime+time)+"; Person "+id+" (M) exits");
			
		}
		
		
		if (buffer == 0) {
			
			lock = false;
			currentTime = currentTime + longestTime;
			longestTime = 0;
			
		}
		
		
	}
	
	//Called the program
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
		
		
		
		
		
		}
	
	}

}

//Called the project 3
class Simulate {
	
	public static void main(String[] args) {
	
		//Gathered input to set up delay time and what amount of people.
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please enter the amount of people.");
		Project3.people = keyboard.nextInt();
		System.out.println("Please enter your delay time. (If no delay enter 0)");
		Project3.delay=keyboard.nextInt();
		System.out.println("Please enter the increments people will come in. (If no increment, enter 1)");
		Project3.increment=keyboard.nextInt();;
		
		//Set times and seed
		Project3.minTime = 3;
		Project3.maxTime = 7;
		Project3.seed = 17;
		
		
		//Run the program
		Thread main = new Thread(new Project3());
		main.setName("main");
		main.start();
		
		
		
		
	}	
}
