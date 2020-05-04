package np.dns.spam.lookup;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Main {

	private static String reverseIp(String ip) {
		// log("reverseIp: (" + ip + ")");
		char revBuff[] = new char[ip.length()];

		int write = 0;

		for (int read = ip.length() - 1; read >= 0; --read) {
			int end = read + 1;
			// log("end = " + end + " read = " + read);
			while (read >= 0 && ip.charAt(read) != '.')
				--read;

			int start = read + 1;
			// log("start = " + start + " read = " + read);

			for (int i = start; i < end; ++i) {
				// log("i = " + i + " write = " + write + " char[i] = " + ip.charAt(i));
				revBuff[write++] = ip.charAt(i);
			}
			if (read >= 0)
				revBuff[write++] = '.';

		}

		return String.copyValueOf(revBuff);

	}

	public static void main(String[] args) {
		
		Map<String, String> m = new HashMap<String, String>();
		//Maybe put in some file/db or sth
		m.put("127.0.0.2", "127.0.0.2 - SBL - Spamhaus SBL Data");
		m.put("127.0.0.3", "127.0.0.3 - SBL - Spamhaus SBL CSS Data");
		m.put("127.0.0.4", "127.0.0.4 - XBL - CBL Data");
		m.put("127.0.0.9", "127.0.0.9 - SBL - Spamhaus DROP/EDROP Data (in addition to 127.0.0.2, since 01-Jun-2016)");
		m.put("127.0.0.10", "127.0.0.10 - PBL - ISP Maintained");
		m.put("127.0.0.11", "127.0.0.11 - PBL - Spamhaus Maintained");
		
		
		for (String ip : args) { // 2.0.0.127
			try {
				
				InetAddress addrs[] = InetAddress.getAllByName(reverseIp(ip) + ".zen.spamhaus.org");

				for (InetAddress a : addrs){
					System.out.printf("The IP address: %s is found in the following Spamhaus public IP zone: %s \n"
										, ip, m.get(a.getHostAddress()));
				}

			} catch (UnknownHostException e) {
				System.out.printf("The IP address: %s is NOT found in the Spamhaus blacklists. \n", ip);
			}
		}

	}

}
