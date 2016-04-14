package util;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import connectors.RedisConnector;

public class SetOperations {

	private String keyname;
	private JedisPool pool;


	public SetOperations(String keyname) {
		this.keyname = keyname;
		RedisConnector conn = new RedisConnector();
		this.pool = conn.getPool();
	}

	public void add(int score, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.zadd(keyname, score, member);
		} finally {
			jedis.close();
		}
	}

	public int getScore(String member) {
		Jedis jedis = null;
		double score = -1;
		try {
			jedis = pool.getResource();
			score = jedis.zscore(keyname, member);
		} finally {
			jedis.close();
		}

		return (int)score;
	}

	public void incrementScore(String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.zincrby(keyname, 1, member);
		} finally {
			jedis.close();
		}
	}

	public void deleteSet() {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.del(keyname);
		} finally {
			jedis.close();
		}
	}

	public Set<String> sortDesc() {
		Jedis jedis = null;
		Set<String> s = null;
		try {
			jedis = pool.getResource();
			s = jedis.zrevrange(keyname, 0, -1);
		} finally {
			jedis.close();
		}

		return s;
	}

	public long howMany() {
		Jedis jedis = null;
		long s;
		try {
			jedis = pool.getResource();
			s = jedis.zcard(keyname);
		} finally {
			jedis.close();
		}
		return s;
	}

}
