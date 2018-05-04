package com.businessModel.utils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Oleksandr Ryzhkov on 25.04.2018.
 */
@Component
public class RedisRunner {
    private final Path pathToServer = Paths.get("..\\Redis server\\redis-server.exe");
    private Process redisServerProcess = null;

    @PostConstruct
    public void runRedis() throws IOException {
        if (Files.exists(pathToServer)) {
            ProcessBuilder pb = new ProcessBuilder(pathToServer.toAbsolutePath().toString());
            redisServerProcess = pb.start();
        } else {
            if (redisServerProcess != null) {
                redisServerProcess.destroyForcibly();
            }
            throw new RuntimeException("Redis server cannot be started.");
        }
    }

    @PreDestroy
    public void stopRedisServer() {
        if (redisServerProcess != null) {
            try {
                redisServerProcess.destroy();
            } catch (Exception e) {
                redisServerProcess.destroyForcibly();
            }
        }
    }
}
