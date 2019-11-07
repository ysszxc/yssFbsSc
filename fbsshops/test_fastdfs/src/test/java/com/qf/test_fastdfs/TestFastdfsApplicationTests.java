package com.qf.test_fastdfs;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFastdfsApplicationTests {

	@Autowired
	private FastFileStorageClient fastFileStorageClient;

	@Test
	public void contextLoads() {
		String pic = "E:\\图片\\3.jpg";

		try {
			StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
					new FileInputStream(pic),
					new File(pic).length(),
					"jpg",
					null
			);

			String fullPath = storePath.getFullPath();
			System.out.println(fullPath);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
