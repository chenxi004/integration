package com.chenxi.utils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

public final class RedisUtil {
    //Redis������IP
    private static String ADDR = "127.0.0.1";
    //Redis�Ķ˿ں�
    private static Integer PORT = 6378;
    //��������
    private static String AUTH = null;

    //��������ʵ���������Ŀ��Ĭ��Ϊ8��
    //�����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)
    private static Integer MAX_TOTAL = 1024;
    //����һ��pool����ж��ٸ�״̬Ϊidle(����)��jedisʵ����Ĭ��ֵ��8
    private static Integer MAX_IDLE = 200;
    //�ȴ��������ӵ����ʱ�䣬��λ�Ǻ��룬Ĭ��ֵΪ-1����ʾ������ʱ��
    //��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException
    private static Integer MAX_WAIT_MILLIS = 10000;
    private static Integer TIMEOUT = 10000;
    //��borrow(��)һ��jedisʵ��ʱ���Ƿ���ǰ����validate(��֤)������
    //���Ϊtrue����õ���jedisʵ�����ǿ��õ�
    private static Boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool = null;

    /**
     * ��̬�飬��ʼ��Redis���ӳ�
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
        /*ע�⣺
            �ڸ߰汾��jedis jar�������籾�汾2.9.0��JedisPoolConfigû��setMaxActive��setMaxWait������
            ������Ϊ�߰汾�йٷ������˴˷��������������������滻��
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
     * ��ȡJedisʵ��
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
        //��������������Ϊfinal����ʾ����ֻ���ġ�
        if(jedis!=null){
            //jedisPool.returnResource(jedis);
            //jedis.close()ȡ��jedisPool.returnResource(jedis)������3.0�汾��ʼ
            jedis.close();
        }
    }

//    public static void setJedis(String host,int port,String password) {
//        //����redis������(�����������ӱ��ص�)
//        jedis = new Jedis(host,port);
//        if(password!=null) {
//            //Ȩ����֤
//            jedis.auth(password);
//        }
//        System.out.println("���ӷ���ɹ�");
//    }

    public static void setString (String key,String value){
        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //keyΪname����valueֵΪchx
            getJedis().set(key, value);
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }
    public static String getString (String key){

        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //�������
            return jedis.get(key); //keyΪname����valueֵΪchx
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }

    public static String msetString (String... keysvalues){

        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //�������
            return jedis.mset(keysvalues); //keyΪname����valueֵΪchx
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }
    public static void del (String key){

        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //ɾ������
            jedis.del(key);
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }
    public static void incr(String key){
        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            jedis.incr(key);//���ڽ���������ֵ����1������������ڣ�����ִ�в���֮ǰ��������Ϊ0�� ����������������͵�ֵ������޷���ʾΪ�������ַ�������᷵�ش��󡣴˲�������64λ�з���������
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }

    public static void setHashMap(String key,HashMap<String, String> hasgmap)
    {

        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            jedis.hmset(key, hasgmap);
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }
    public static Set<String> getHashMapFileds(String key)
    {

        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //����user�����е�����field
            return jedis.hkeys(key);
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }
    public static List<String> getHashMapValues(String key)
    {

        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //����map�����е�����value
            return jedis.hvals(key);
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }
    public static List<String> getHashMapFieldsValue(String key,String... fields)
    {

        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //ȡ��map�е�value�������һ�����͵�List
            //��һ�������Ǵ���redis��map�����key����������Ƿ���map�еĶ����key�������key�ǿɱ����
            return jedis.hmget(key,fields);
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }
    public static void delHashMapFields(String key, String... fields)
    {

        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            jedis.hdel(key,fields);//ɾ��map�е�ĳ����ֵ
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }
    public void getHashMapLength(String key)
    {

        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //����key�ļ��д�ŵ�ֵ�ĸ���
            jedis.hlen(key);
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }
    public static boolean existsHashMapKey(String key)
    {
        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //�Ƿ����key�ļ�¼ ����true
            return jedis.exists(key);
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }
    }

    /**
     * Redis�����ַ���
     */
    public static void testString() {
        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //�������
            jedis.set("name", "chx"); //keyΪname����valueֵΪchx
            System.out.println("ƴ��ǰ:" + jedis.get("name"));//��ȡkeyΪname��ֵ

            //��keyΪname��ֵ����������� ---ƴ��
            jedis.append("name", " is my name;");
            System.out.println("ƴ�Ӻ�:" + jedis.get("name"));

            //ɾ��ĳ����ֵ��
            jedis.del("name");
            System.out.println("ɾ����:" + jedis.get("name"));


            //s���ö����ֵ��
            jedis.mset("name", "chenhaoxiang", "age", "20", "email", "chxpostbox@outlook.com");
            jedis.incr("age");
            System.out.println(jedis.get("name") + " " + jedis.get("age") + " " + jedis.get("email"));
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }

    }

    public static void testMap() {
        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //�������
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "chx");
            map.put("age", "100");
            map.put("email", "***@outlook.com");
            jedis.hmset("user", map);
            //ȡ��user�е�name�������һ�����͵�List
            //��һ�������Ǵ���redis��map�����key����������Ƿ���map�еĶ����key�������key�ǿɱ����
            List<String> list = jedis.hmget("user", "name", "age", "email");
            System.out.println(list);

            //ɾ��map�е�ĳ����ֵ
            jedis.hdel("user", "age");
            System.out.println("age:" + jedis.hmget("user", "age")); //��Ϊɾ���ˣ����Է��ص���null
            System.out.println("user�ļ��д�ŵ�ֵ�ĸ���:" + jedis.hlen("user")); //����keyΪuser�ļ��д�ŵ�ֵ�ĸ���2
            System.out.println("�Ƿ����keyΪuser�ļ�¼:" + jedis.exists("user"));//�Ƿ����keyΪuser�ļ�¼ ����true
            System.out.println("user�����е�����key:" + jedis.hkeys("user"));//����user�����е�����key
            System.out.println("user�����е�����value:" + jedis.hvals("user"));//����map�����е�����value

            //�õ�key����ͨ���������õ�ֵ
            Iterator<String> iterator = jedis.hkeys("user").iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                System.out.println(key + ":" + jedis.hmget("user", key));
            }
            jedis.del("user");
            System.out.println("ɾ�����Ƿ����keyΪuser�ļ�¼:" + jedis.exists("user"));//�Ƿ����keyΪuser�ļ�¼

        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }

    }

    /**
     * jedis����List
     */
    public static void testList(){
        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //�Ƴ�javaFramwork����������
            jedis.del("javaFramwork");
            //�������
            jedis.lpush("javaFramework","spring");
            jedis.lpush("javaFramework","springMVC");
            jedis.lpush("javaFramework","mybatis");
            //ȡ����������,jedis.lrange�ǰ���Χȡ��
            //��һ����key���ڶ�������ʼλ�ã��������ǽ���λ��
            System.out.println("����:"+jedis.llen("javaFramework"));
            //jedis.llen��ȡ���ȣ�-1��ʾȡ������
            System.out.println("javaFramework:"+jedis.lrange("javaFramework",0,-1));

            jedis.del("javaFramework");
            System.out.println("ɾ���󳤶�:"+jedis.llen("javaFramework"));
            System.out.println(jedis.lrange("javaFramework",0,-1));
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }

    }

    /**
     * jedis����Set
     */
    public static void testSet(){
        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            //���
            jedis.sadd("user","chenhaoxiang");
            jedis.sadd("user","hu");
            jedis.sadd("user","chen");
            jedis.sadd("user","xiyu");
            jedis.sadd("user","chx");
            jedis.sadd("user","are");
            //�Ƴ�user�����е�Ԫ��are
            jedis.srem("user","are");
            System.out.println("user�е�value:"+jedis.smembers("user"));//��ȡ���м���user��value
            System.out.println("chx�Ƿ���user�е�Ԫ��:"+jedis.sismember("user","chx"));//�ж�chx�Ƿ���user�����е�Ԫ��
            System.out.println("�����е�һ�����Ԫ��:"+jedis.srandmember("user"));//���ؼ����е�һ�����Ԫ��
            System.out.println("user��Ԫ�صĸ���:"+jedis.scard("user"));
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }

    }

    /**
     * ����
     */
    public static void test(){
        Jedis jedis = null;
        try{
            // �����ӳػ�ȡһ��Jedisʵ��
            jedis = jedisPool.getResource();
            jedis.del("number");//��ɾ�����ݣ��ٽ��в���
            jedis.rpush("number","4");//��һ������ֵ���뵽�б��β��(���ұ�)
            jedis.rpush("number","5");
            jedis.rpush("number","3");

            jedis.lpush("number","9");//��һ������ֵ���뵽�б�ͷ��
            jedis.lpush("number","1");
            jedis.lpush("number","2");
            System.out.println(jedis.lrange("number",0,jedis.llen("number")));
            System.out.println("����:"+jedis.sort("number"));
            System.out.println(jedis.lrange("number",0,-1));//���ı�ԭ��������
            jedis.del("number");//������ɾ������
        }finally{
            if(null != jedis)
                jedis.close(); // �ͷ���Դ�������ӳ�
        }

    }

}
