import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Home {
	public static void main(String[] args) {
		String api = "http://localhost:8080/WebApiSample/Algorithms/solveequation/solve?maxiteration=";
		Scanner quest = new Scanner(System.in);
		System.out.println("target?:");
		int target = quest.nextInt();
		System.out.println("Max Iterations ?");
		int maxIterations = quest.nextInt();
		try {
			URL url = new URL(api+maxIterations+"&target="+target);
			System.out.println(url);
			Controller ctr = new Controller(url);
			ctr.SendRequest();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
