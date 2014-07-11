package com.bota.server.patch.deployer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;

import com.bota.server.patch.util.PathUtil;
import com.bota.server.patch.util.UnzipUtil;

public class PatchDeployer {
	public static void hashFolder() throws IOException {
		File deployFolder = new File("deploy");
		if (!deployFolder.exists()) deployFolder.mkdir();
		ArrayList<File> files = new ArrayList<File>();
		PathUtil.listf("files", files);
		StringBuilder builder = new StringBuilder();
		
		for (File file : files) {
			FileInputStream fileInputStream = new FileInputStream(file);
			builder.append(file.getPath().replace("\\", "/") + "$" + DigestUtils.md5Hex(fileInputStream) + System.lineSeparator());
		}
		
		try (FileWriter fileWriter = new FileWriter("deploy/hash")) {
			fileWriter.write(builder.toString());
		}
		System.out.println("Hash file done");
	}
	public static void main(String args[]) throws IOException {
		if (args.length == 0) {
			hashFolder();
			return;
		}
		String zipPath = args[0];
		
		File zipFile = new File(zipPath);
		if (zipFile.exists() && zipFile.isFile()) {
			File filesDirectory = new File("files");
			if (!filesDirectory.exists()) {
				filesDirectory.mkdir();
			}
			UnzipUtil unzipUtil = new UnzipUtil();
			unzipUtil.unzip(zipPath, "files");
			System.out.println("Unzipped files");
			
			hashFolder();
			
			Path releaseFile = FileSystems.getDefault().getPath(zipPath);
			Path serverFile = FileSystems.getDefault().getPath("deploy/patch.zip");
			
			Files.copy(releaseFile, serverFile, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("ZIP file copied");
		}
		else {
			System.err.println("Sorry, the path is not valid.");
			return;
		}
	}
}
