import java.net.URL;

import org.json.JSONObject;

public class Controller {

	private Request _rt;
	public URL _api;
	public Controller(URL api)
	{
		_api = api;
		_rt = new Request();
	}
	
	public JSONObject SendRequest()
	{
		try {
			String reqResponse = _rt.SendRequest(_api);
			System.out.println(reqResponse);
			JSONObject j = new JSONObject(reqResponse);
			return j;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
