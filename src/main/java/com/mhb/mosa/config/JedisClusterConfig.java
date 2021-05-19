package com.mhb.mosa.config;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
public class JedisClusterConfig {

    private String password;
    private int maxTotal;
    private int maxIdle;
    private long maxWaitMillis;
    private boolean testOnBorrow;
    private int connectionTimeout;
    private int soTimeout;
    private int maxAttempts;
    private List<Map<String, String>> jedisClusterNodes;

    @Bean
    public GenericObjectPoolConfig poolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMaxWaitMillis(maxWaitMillis);
        genericObjectPoolConfig.setTestOnBorrow(testOnBorrow);
        return genericObjectPoolConfig;
    }

    @Bean
    public JedisCluster getJedisCluster(GenericObjectPoolConfig poolConfig) {
        Set<HostAndPort> clusterNodes = new HashSet<>();
        for (Map<String, String> node : jedisClusterNodes) {
            HostAndPort hap = new HostAndPort(node.get("ip"), Integer.valueOf(node.get("port")));
            clusterNodes.add(hap);
        }
        return new JedisCluster(clusterNodes, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
    }
}
