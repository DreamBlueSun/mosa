package com.mhb.mosa.task;

import com.mhb.mosa.util.StaticUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date: 2021/6/10 16:40
 */
@Slf4j
@Component
@EnableScheduling
public class LinkTask {

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolScheduler = new ThreadPoolTaskScheduler();
        threadPoolScheduler.setThreadNamePrefix("Socket-");
        threadPoolScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        threadPoolScheduler.setRemoveOnCancelPolicy(true);
        return threadPoolScheduler;
    }

    /**
     * 每次执行后间隔5秒执行从redis在线队列中清除下线超过5秒的用户
     *
     * @return void
     */
    @Scheduled(fixedDelay = 5000)
    private void logout() {
        Set<Tuple> tuples = StaticUtils.jedisCluster.zrangeByScoreWithScores(REDIS_KEY_LIST_LOGOUT, 0, System.currentTimeMillis() - 5000);
        if (!CollectionUtils.isEmpty(tuples)) {
            List<String> list = tuples.stream().map(Tuple::getElement).collect(Collectors.toList());
            StaticUtils.jedisCluster.zrem(REDIS_KEY_LIST_LOGOUT, list.toArray(new String[]{}));
            for (String userName : list) {
                StaticUtils.linkService.logout(userName);
            }
        }

    }

    private final static String REDIS_KEY_STRING_LOGOUT_LAST_TIME = "r:k:string:logout:l:t";

    private final static String REDIS_KEY_LIST_LOGOUT = "r:k:list:logout";

    public static void putListLogout(String userName) {
        StaticUtils.jedisCluster.zadd(REDIS_KEY_LIST_LOGOUT, System.currentTimeMillis(), userName);
    }

    public static void removeListLogout(String userName) {
        StaticUtils.jedisCluster.zrem(REDIS_KEY_LIST_LOGOUT, userName);
    }
}
