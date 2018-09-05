public class HttpResponse
{
	private int val;
	private String message;
	public HttpResponse(int value,String message)
	{
		val = value;
		this.message = message;
	}
	public int getValue()
	{
	return val;
	}
	public String getMessage()
	{
		return message;
	}
}