import java.net.*;
import java.io.*;
import java.util.*;

public class Main
{
	// FIX - User environment for PORT
	static final int port = 8080;
	static final String newLine = "\r\n";

	public static void main(String[] args)
	{
		try
		{
			System.out.println("Starting server on port " + Integer.toString(port));

			ServerSocket socket = new ServerSocket(port);

			while (true)
			{
				Socket connection=socket.accept();

				try
				{
					InputStream istream;
					OutputStream ostream;

					istream = connection.getInputStream();
					ostream = connection.getOutputStream();

					BufferedReader in = new BufferedReader(new InputStreamReader(istream));

					OutputStream out = new BufferedOutputStream(ostream);

					PrintStream pout = new PrintStream(out);

					// read first line of request
					String request = in.readLine();

					if (request == null)
						continue;

					// we ignore the rest
					while (true)
					{
						String ignore = in.readLine();

						if (ignore == null || ignore.length() == 0)
							break;
					}

					// in.close();

					System.out.println("Request: " + request);

					if (!request.startsWith("GET "))
					{
						// unsupported method
						pout.print("HTTP/1.0 405 Method Not Allowed" + newLine + newLine);
						pout.close();
						continue;
					}

					if (!(request.endsWith(" HTTP/1.0") || request.endsWith(" HTTP/1.1")))
					{
						// bad request
						pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
						pout.close();
						continue;
					}

					String ip = connection.getRemoteSocketAddress().toString().replace("/","");
					System.out.println("IP Address: " + ip);

					pout.print(
						"HTTP/1.0 200 OK" + newLine +
						"Date: " + new Date() + newLine +
						"Server: PlainHttpServer" + newLine +
						"Cache-Control: max-age=0, no-cache, s-maxage=0" + newLine +
						"Content-Type: text/plain" + newLine +
						"Content-length: " + ip.length() + newLine +
						newLine +
						ip
					);

					pout.close();
				}
				catch (Throwable tr)
				{
					System.err.println("Error handling request: " + tr);
				}
			}
		}
		catch (Throwable tr)
		{
			System.err.println("Could not start server: " + tr);
		}
	}
}
