package demo.demomemorystress;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@Data
@Slf4j
public class DemoMemoryStressApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMemoryStressApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
				log.info("start memory loading...");

				IntStream.rangeClosed(1, 10).mapToObj(i -> new Thread(() -> {
					while (true) {
						//每一个线程都是一个死循环，休眠10秒，打印10M数据
						String payload = IntStream.rangeClosed(1, 10000000)
								.mapToObj(__ -> "a")
								.collect(Collectors.joining("")) + UUID.randomUUID().toString();
						try {
							TimeUnit.SECONDS.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(payload.length());
					}
				})).forEach(Thread::start);


				TimeUnit.HOURS.sleep(1);

		};
	}

}
