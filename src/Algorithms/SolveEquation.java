package Algorithms;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/solveequation")
public class SolveEquation {
 // solve for a + 2b + 3c + 4d = target
	@Path("/solve")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response Solve(@QueryParam("maxiteration") int max, @QueryParam("target") int target) throws JSONException
	{
		JSONObject rst = new JSONObject();
		GeneticAlgorithmMath.InitialPop(target);
		int i = 0;
		while(i < max)
		{
			rst.put("Solution "+i, GeneticAlgorithmMath.getBreedandCombine(target));
			if(GeneticAlgorithmMath.evaluate(target)) break;
			i++;
		}
		
		return Response.status(200).entity(rst.toString()).build();
	}
}
