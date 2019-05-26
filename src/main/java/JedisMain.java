import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisMain {

    public static void main(String[] args) {
//        Jedis jedis = new Jedis("127.0.0.1", 6379);



//        Object person = jedis.eval("return redis.call('lrange', KEYS[1], 0, -1)", 1, "person");
//
//        Object eval = jedis.eval("return redis.call('set', KEYS[1], ARGV[1])", 1, "name", "robinyang");
//        System.out.println(eval.toString());






        for (int i = 0; i < 1000; i++) {
            new Thread(()->{
                Jedis jedis = getJedis();
                JedisMain jedisMain = new JedisMain();
                boolean lock = jedisMain.lock(jedis, "mobile-test", "123456");
                if (lock) {
                    System.out.println("获取锁成功： done");
                } else {
                    System.out.println("获取锁失败： done");
                }

                jedisMain.unlock(jedis, "mobile-test", "123456");
            }).start();
        }
    }

    public boolean lock(Jedis jedis, String lock, String val) {
        Object name = jedis.eval("if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('expire', KEYS[1], ARGV[2]) return 'true' else return 'false' end", 1, lock, val, "10");
        return Boolean.valueOf(name.toString());
    }

    public void unlock(Jedis jedis, String lock, String val) {
        jedis.eval("if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('del', KEYS[1]) end return 1", 1, lock, val);
    }

    public static synchronized Jedis getJedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        // 1.1 最大连接数
        config.setMaxTotal(30);

        //1.2 最大空闲连接数
        config.setMaxIdle(10);
        JedisPool jedisPool = new JedisPool(config,"localhost",6379);

        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}
