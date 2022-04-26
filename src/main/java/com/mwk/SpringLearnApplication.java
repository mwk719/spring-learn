package com.mwk;

import com.mwk.netty.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@SpringBootApplication
@EnableAsync
public class SpringLearnApplication implements CommandLineRunner {

	@Autowired
	NettyServer nettyServer;

	@Override
	public void run(String... args) throws Exception {
		log.info("项目启动完成后启动netty服务的监听器");
		nettyServer.start();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringLearnApplication.class, args);
	}

	@EnableAsync
	@Configuration
	class TaskPoolConfig {
		@Bean("taskExecutor")
		public Executor taskExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			//核心线程数10：线程池创建时候初始化的线程数
			executor.setCorePoolSize(10);
			//最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
			executor.setMaxPoolSize(20);
			//缓冲队列200：用来缓冲执行任务的队列
			executor.setQueueCapacity(200);
			//允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
			executor.setKeepAliveSeconds(60);
			//线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
			executor.setThreadNamePrefix("taskExecutor-");
			/*
			线程池对拒绝任务的处理策略：
			这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，
			该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；
			如果执行程序已关闭，则会丢弃该任务
			CallerRunsPolicy 会使用用户的线程执行任务，可能会将用户线程占满，造成用户业务长时间等待
			AbortPolicy 默认的拒绝策略，就是抛出异常的
			 */
			executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
			//用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
			executor.setWaitForTasksToCompleteOnShutdown(true);
			return executor;
		}
	}

}
