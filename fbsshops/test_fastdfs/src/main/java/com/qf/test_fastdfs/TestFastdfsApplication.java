package com.qf.test_fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(FdfsClientConfig.class)
public class TestFastdfsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestFastdfsApplication.class, args);
	}

}
