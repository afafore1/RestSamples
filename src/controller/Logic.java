package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/logic")
public class Logic {
	
	private List<Integer> _myList = new ArrayList<>();

	private String GetPairs(int length, int target) {
		String result = "";
		int[] arr = new int[length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * target);
		}
		HashMap<Integer, Integer> pairs = new HashMap<>();
		for (int i = 0; i < arr.length; i++) {
			if (pairs.containsKey(arr[i])) {
				result += arr[i] + " + " + pairs.get(arr[i]);
				result += "; ";
			} else {
				pairs.put(target - arr[i], arr[i]);
			}
		}
		return result;
	}
	
	@Path("/findPair")
	@GET
	@Produces("application/json")
	public Response findPair(@QueryParam("arraylength") int length, @QueryParam("target") int target,
			@QueryParam("random") int random) throws JSONException {
		JSONObject rstObject = new JSONObject();
		JSONArray rsts = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		String rst = GetPairs(length, target);
		jsonObject.put("pair", rst);
		rsts.put(jsonObject);
		while (random > 0) {
			int rand = (int) (Math.random() * target); // get a random number as
														// target

			rst = GetPairs(length, rand);
			jsonObject = new JSONObject(); // better way than new each time ?
			jsonObject.put("pair", rst);
			rsts.put(jsonObject);
			random--;
		}
		rstObject.put("Pairs", rsts);
		return Response.status(200).entity(rstObject.toString()).build();
	}

	@Path("{list}")
	@GET
	@Produces("application/json")
	public Response SecretaryProblem(@PathParam("list") List<Integer> list) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("something", "hellow");
		int p = 37;
		int nextBest = 0;
		int best = 0;
		for (int i = 0; i < p; i++) {
			if (list.get(i) > nextBest) {
				nextBest = list.get(i);
			}
		}
		for (int i = p; i < list.size(); i++) {
			if (list.get(i) > nextBest) {
				best = list.get(i);
				break;
			}
		}
		jsonObject.put("valuePicked", best);
		return Response.status(200).entity(jsonObject).build();
	}

	@Path("/sayhellomultiple/{n}/{m}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response SayHello(@PathParam("n") String name1, @PathParam("m") String name2) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String greeting = "Hello ";
		jsonObject.put("Greetings", greeting + name1 + " and " + name2);
		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
	}

	@Path("/isPalindrome")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response isPalindrome(@QueryParam("number") int number) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		int checknumber = number;
		int reverse = 0;
		boolean isPalindrome = false;
		while (checknumber != 0) {
			int mod = checknumber % 10;
			reverse = reverse * 10 + mod;
			checknumber /= 10;
		}

		if (reverse == number) {
			isPalindrome = true;
		}
		jsonObject.put(String.valueOf(number), isPalindrome);
		return Response.status(200).entity(jsonObject.toString()).build();
	}
	
	@Path("/incrementArray/{value}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response IncrementArray(@PathParam("value") int value) throws JSONException 
	{
		JSONObject jsonObject = new JSONObject();
		_myList.add(value);
		jsonObject.put("List", _myList);
		return Response.status(200).entity(jsonObject.toString()).build();
	}
	
	@Path("/createList/{value}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response CreateList(@PathParam("value") int value) throws JSONException 
	{
		JSONObject jsonObject = new JSONObject();
		_myList.add(value);
		jsonObject.put("List", _myList);
		return Response.status(200).entity(jsonObject.toString()).build();
	}
}
