package com.businessModel.utils;

import com.businessModel.service.DriversStandingsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(RedisRunner.class);

    private final Path pathToServer = Paths.get("..\\Redis server\\redis-server.exe");
    private Process redisServerProcess = null;

    @PostConstruct
    public void runRedis() throws Exception {
        if (Files.exists(pathToServer)) {
            ProcessBuilder pb = new ProcessBuilder(pathToServer.toAbsolutePath().toString());
            redisServerProcess = pb.start();
        } else {
            if (redisServerProcess != null) {
                redisServerProcess.destroyForcibly();
            }
            logger.error("Folder with Redis server cannot be found");
            throw new Exception("Redis server cannot be started.");
        }
    }

    @PreDestroy
    public void stopRedisServer() {
        if (redisServerProcess != null) {
            try {
                redisServerProcess.destroy();
            } catch (Exception e) {
                logger.error("Redis server must be killed :)");
                redisServerProcess.destroyForcibly();
            }
        }
    }
}
