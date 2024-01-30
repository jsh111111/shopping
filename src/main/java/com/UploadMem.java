package com;

import java.io.File;

public class UploadMem {
	// Windows, VMWare, AWS cloud 절대 경로 설정
	public static synchronized String getUploadDir() {
		String path = "";
		if (File.separator.equals("\\")) {
			path = "C:/Users/PC-95/Desktop/jsh/deploy/member/storage/";
			System.out.println("Windows 10: " + path);
		} else {
			// System.out.println("Linux");
			path = "/home/ubuntu/deploy/member/storage/";
		}

		return path;
	}

}