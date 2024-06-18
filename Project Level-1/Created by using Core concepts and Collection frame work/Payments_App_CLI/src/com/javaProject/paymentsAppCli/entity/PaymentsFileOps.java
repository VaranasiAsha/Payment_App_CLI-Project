package com.javaProject.paymentsAppCli.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PaymentsFileOps {
	public final String usersFilePath = "G:\\Dp Folder\\PaymentAppfile\\Payments_CLI_USers.csv";
	
	public void writeUserToFile(User u) throws IOException {
		File userFile = new File(usersFilePath);
		FileWriter fw = new FileWriter(userFile,true);
		fw.write(u.userToFileRecord());
		
		fw.flush();
		fw.close();
	}

}