package com.milesbone.redis;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Sets;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClusterUtil {
	private static final Logger LOGGER = Logger.getLogger(RedisClusterUtil.class);
	private int timeout = 10000;
	private JedisCluster jedisCluster;
	private Set<HostAndPort> clusterNodes;

	public RedisClusterUtil(String redisUris, JedisPoolConfig jedisPoolConfig) throws Exception {
		init(redisUris);
		// checkHostAndPost();
		jedisCluster = new JedisCluster(clusterNodes, timeout, jedisPoolConfig);
	}

	/** * reids 链接节点地址 */
	public void init(String redisUris) {
		LOGGER.info("redisUris:" + redisUris);
		// ## 注册redis连接节点
		clusterNodes = Sets.newHashSet();
		if (StringUtils.isNotBlank(redisUris)) {
			// 以“；”分割成"ip:post"
			String[] ipAndPostes = redisUris.split(";");
			// 以“：”分割成 “ip”，“post”
			if (ipAndPostes != null) {
				for (String ipAndPost : ipAndPostes) {
					// ipAndPost 值：(ip：端口)
					String[] ipAndPostArray = ipAndPost.split(":");
					clusterNodes.add(new HostAndPort(ipAndPostArray[0], Integer.parseInt(ipAndPostArray[1])));
				}
			}
		}
		LOGGER.info("redis链接节点个数(clusterNodes)：" + clusterNodes.size());
	}
}
