package com.example.homeautomation;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;


public class ConnectionHandler extends AsyncTask<String, Void, String>{
	NetworkInfo network = new NetworkInfo();
	String ip = network.getIp();
	Integer port = network.getPort();
	Socket s = null;
	PrintWriter output = null;

	protected String doInBackground(String...arg0) {
		try{
			String message = arg0[0];
			InetAddress serverAddr = InetAddress.getByName(ip);
			s = new Socket(serverAddr, port);
			output = new PrintWriter(s.getOutputStream());
			output.println(message);
			output.flush();
			output.close();
			s.close();
			if(arg0[1]=="return photo"){
				ServerSocket server = new ServerSocket(2002);
				while (true){
					Socket socket = server.accept();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
					arg0[3] = in.readLine();
					Log.i("Received URL response from server", arg0[3]);

					in.close();
					socket.close();
					return arg0[3];
				}
			}
			if(arg0[1]=="return details"){
				ServerSocket server = new ServerSocket(2002);
				while (true){
					Socket socket = server.accept();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
					arg0[3] = in.readLine();
					Log.i("Received URL response from server", arg0[3]);

					in.close();
					socket.close();
					return arg0[3];
				}
				
			}
		}
		catch(IOException e)
		{
			Log.i("exception: ", e.toString());
			
		}
		return null;

	}

	@Override
	protected void onPostExecute(String result) {
		Urls url = new Urls();
		url.setUrl(result);
		
	}
}
