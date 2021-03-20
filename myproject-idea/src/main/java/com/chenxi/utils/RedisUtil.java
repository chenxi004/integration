package com.chenxi.utils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

public final class RedisUtil {
    //Redis服务器IP
    private static String ADDR = "127.0.0.1";
    //Redis的端口号
    private static Integer PORT = 6378;
    //访问密码
    private static String AUTH = null;

    //可用连接实例的最大数目，默认为8；
    //如果赋值为-1，则表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
    private static Integer MAX_TOTAL = 1024;
    //控制一个pool最多有多少个状态为idle(空闲)的jedis实例，默认值是8
    private static Integer MAX_IDLE = 200;
    //等待可用连接的最大时间，单位是毫秒，默认值为-1，表示永不超时。
    //如果超过等待时间，则直接抛出JedisConnectionException
    private static Integer MAX_WAIT_MILLIS = 10000;
    private static Integer TIMEOUT = 10000;
    //在borrow(用)一个jedis实例时，是否提前进行validate(验证)操作；
    //如果为true，则得到的jedis实例均是可用的
    private static Boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool = null;

    /**
     * 静态块，初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
        /*注意：
            在高版本的jedis jar包，比如本版本2.9.0，JedisPoolConfig没有setMaxActive和setMaxWait属性了
            这是因为高版本中官方废弃了此方法，用以下两个属性替换。
            maxActive  ==>  maxTotal
            maxWait==>  maxWaitMillis
         */
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config,ADDR,PORT,TIMEOUT,AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis(){
        try {
            if(jedisPool != null){
                Jedis jedis = jedisPool.getResource();
                return jedis;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void returnResource(final Jedis jedis){
        //方法参数被声明为final，表示它是只读的。
        if(jedis!=null){
            //jedisPool.returnResource(jedis);
            //jedis.close()取代jedisPool.returnResource(jedis)方法将3.0版本开始
            jedis.close();
        }
    }

//    public static void setJedis(String host,int port,String password) {
//        //连接redis服务器(在这里是连接本地的)
//        jedis = new Jedis(host,port);
//        if(password!=null) {
//            //权限认证
//            jedis.auth(password);
//        }
//        System.out.println("连接服务成功");
//    }

    public static void setString (String key,String value){
        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //key为name放入value值为chx
            getJedis().set(key, value);
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }
    public static String getString (String key){

        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //添加数据
            return jedis.get(key); //key为name放入value值为chx
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }

    public static String msetString (String... keysvalues){

        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //添加数据
            return jedis.mset(keysvalues); //key为name放入value值为chx
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }
    public static void del (String key){

        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //删除数据
            jedis.del(key);
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }
    public static void incr(String key){
        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            jedis.incr(key);//用于将键的整数值递增1。如果键不存在，则在执行操作之前将其设置为0。 如果键包含错误类型的值或包含无法表示为整数的字符串，则会返回错误。此操作限于64位有符号整数。
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }

    public static void setHashMap(String key,HashMap<String, String> hasgmap)
    {

        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            jedis.hmset(key, hasgmap);
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }
    public static Set<String> getHashMapFileds(String key)
    {

        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //返回user对象中的所有field
            return jedis.hkeys(key);
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }
    public static List<String> getHashMapValues(String key)
    {

        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //返回map对象中的所有value
            return jedis.hvals(key);
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }
    public static List<String> getHashMapFieldsValue(String key,String... fields)
    {

        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //取出map中的value，结果是一个泛型的List
            //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key是可变参数
            return jedis.hmget(key,fields);
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }
    public static void delHashMapFields(String key, String... fields)
    {

        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            jedis.hdel(key,fields);//删除map中的某个键值
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }
    public void getHashMapLength(String key)
    {

        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //返回key的键中存放的值的个数
            jedis.hlen(key);
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }
    public static boolean existsHashMapKey(String key)
    {
        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //是否存在key的记录 返回true
            return jedis.exists(key);
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }
    }

    /**
     * Redis操作字符串
     */
    public static void testString() {
        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //添加数据
            jedis.set("name", "chx"); //key为name放入value值为chx
            System.out.println("拼接前:" + jedis.get("name"));//读取key为name的值

            //向key为name的值后面加上数据 ---拼接
            jedis.append("name", " is my name;");
            System.out.println("拼接后:" + jedis.get("name"));

            //删除某个键值对
            jedis.del("name");
            System.out.println("删除后:" + jedis.get("name"));


            //s设置多个键值对
            jedis.mset("name", "chenhaoxiang", "age", "20", "email", "chxpostbox@outlook.com");
            jedis.incr("age");
            System.out.println(jedis.get("name") + " " + jedis.get("age") + " " + jedis.get("email"));
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }

    }

    public static void testMap() {
        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //添加数据
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "chx");
            map.put("age", "100");
            map.put("email", "***@outlook.com");
            jedis.hmset("user", map);
            //取出user中的name，结果是一个泛型的List
            //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key是可变参数
            List<String> list = jedis.hmget("user", "name", "age", "email");
            System.out.println(list);

            //删除map中的某个键值
            jedis.hdel("user", "age");
            System.out.println("age:" + jedis.hmget("user", "age")); //因为删除了，所以返回的是null
            System.out.println("user的键中存放的值的个数:" + jedis.hlen("user")); //返回key为user的键中存放的值的个数2
            System.out.println("是否存在key为user的记录:" + jedis.exists("user"));//是否存在key为user的记录 返回true
            System.out.println("user对象中的所有key:" + jedis.hkeys("user"));//返回user对象中的所有key
            System.out.println("user对象中的所有value:" + jedis.hvals("user"));//返回map对象中的所有value

            //拿到key，再通过迭代器得到值
            Iterator<String> iterator = jedis.hkeys("user").iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                System.out.println(key + ":" + jedis.hmget("user", key));
            }
            jedis.del("user");
            System.out.println("删除后是否存在key为user的记录:" + jedis.exists("user"));//是否存在key为user的记录

        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }

    }

    /**
     * jedis操作List
     */
    public static void testList(){
        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //移除javaFramwork所所有内容
            jedis.del("javaFramwork");
            //存放数据
            jedis.lpush("javaFramework","spring");
            jedis.lpush("javaFramework","springMVC");
            jedis.lpush("javaFramework","mybatis");
            //取出所有数据,jedis.lrange是按范围取出
            //第一个是key，第二个是起始位置，第三个是结束位置
            System.out.println("长度:"+jedis.llen("javaFramework"));
            //jedis.llen获取长度，-1表示取得所有
            System.out.println("javaFramework:"+jedis.lrange("javaFramework",0,-1));

            jedis.del("javaFramework");
            System.out.println("删除后长度:"+jedis.llen("javaFramework"));
            System.out.println(jedis.lrange("javaFramework",0,-1));
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }

    }

    /**
     * jedis操作Set
     */
    public static void testSet(){
        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            //添加
            jedis.sadd("user","chenhaoxiang");
            jedis.sadd("user","hu");
            jedis.sadd("user","chen");
            jedis.sadd("user","xiyu");
            jedis.sadd("user","chx");
            jedis.sadd("user","are");
            //移除user集合中的元素are
            jedis.srem("user","are");
            System.out.println("user中的value:"+jedis.smembers("user"));//获取所有加入user的value
            System.out.println("chx是否是user中的元素:"+jedis.sismember("user","chx"));//判断chx是否是user集合中的元素
            System.out.println("集合中的一个随机元素:"+jedis.srandmember("user"));//返回集合中的一个随机元素
            System.out.println("user中元素的个数:"+jedis.scard("user"));
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }

    }

    /**
     * 排序
     */
    public static void test(){
        Jedis jedis = null;
        try{
            // 从连接池获取一个Jedis实例
            jedis = jedisPool.getResource();
            jedis.del("number");//先删除数据，再进行测试
            jedis.rpush("number","4");//将一个或多个值插入到列表的尾部(最右边)
            jedis.rpush("number","5");
            jedis.rpush("number","3");

            jedis.lpush("number","9");//将一个或多个值插入到列表头部
            jedis.lpush("number","1");
            jedis.lpush("number","2");
            System.out.println(jedis.lrange("number",0,jedis.llen("number")));
            System.out.println("排序:"+jedis.sort("number"));
            System.out.println(jedis.lrange("number",0,-1));//不改变原来的排序
            jedis.del("number");//测试完删除数据
        }finally{
            if(null != jedis)
                jedis.close(); // 释放资源还给连接池
        }

    }

}
