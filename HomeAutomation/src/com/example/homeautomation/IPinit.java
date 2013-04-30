package com.example.homeautomation;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;



import org.apache.http.conn.util.InetAddressUtils;

public class IPinit {
	
		public static void SendIP(){
			@SuppressWarnings("unused")
			String ip = GetIPAddress(true);
			/*
			ConnectionHandler connection = new ConnectionHandler();
			//send light on message
			connection.execute("D:"+ip);
			*/
		}
	
	
		//retreaved from http://stackoverflow.com/questions/6305918/code-to-detect-the-android-devices-own-ip-address
	    /**
	     * Get IP address from first non-localhost interface
	     * @param ipv4  true=return ipv4, false=return ipv6
	     * @return  address or empty string
	     */
	    public static String GetIPAddress(boolean useIPv4) {
	        try {
	            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
	            for (NetworkInterface intf : interfaces) {
	                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
	                for (InetAddress addr : addrs) {
	                    if (!addr.isLoopbackAddress()) {
	                        String sAddr = addr.getHostAddress().toUpperCase();
	                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr); 
	                        if (useIPv4) {
	                            if (isIPv4) 
	                                return sAddr;
	                        } else {
	                            if (!isIPv4) {
	                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
	                                return delim<0 ? sAddr : sAddr.substring(0, delim);
	                            }
	                        }
	                    }
	                }
	            }
	        } catch (Exception ex) { } // for now eat exceptions
	        return "";
	    }

	}