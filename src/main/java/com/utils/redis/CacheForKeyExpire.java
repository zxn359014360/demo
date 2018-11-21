package com.utils.redis;

import java.util.List;
import java.util.Map;

/**
 * 添加带失效时间的存储内容
 * @author admin
 *
 */
public interface CacheForKeyExpire {

	/**
	 * 失效时间key值
	 */
	public static final String ALL_KEYEXPIRE = "ExpireKey-";
	/**
	 * PC HOMEPAGE-key值
	 */
	public static final String PC_HOMEPAGE_KEY = "HomePageKey-";

	/**
	 *	设置key值得有效期
	 * @param key
	 * @param expire
	 */
	public void exprie(String key, int expire);

	/**
	 * 添加带失效时间的存储内容
	 * @param key 键值
	 * @param val 内容
	 * @param expire 失效时间单位秒
	 */
	public void addKeyExpire(String key, String val, int expire);

	/**
	 * @param key 键值
	 * @param val 内容  Object
	 * @param expire 失效时间单位秒
	 */
	public void addKeyExpireObject(String key, Object val, int expire);


    void addKeyExpireList(String key, List<?> list, int expire);

    List getKeyExpireList(String key, Class clazz);

	/**
	 * @param key 键值
	 * @param val 内容  Object
	 */
	public void addKeyObject(String key, Object val);

	/**
	 * 根据主键获取内容
	 * @return
	 */
	public String getKeyExpire(String key);

	/**
	 * 根据主键获取内容
	 * @return
	 */
	public Object getKeyExpireObject(String key, Class clazz);
	/**
	 * 根据主键获取内容
	 * @return
	 */
	public Object getKeyObject(String key, Class clazz);

	/**
	 * 添加不带失效时间的存储内容
	 * @param key 键值
	 * @param val 内容
	 */
	public void addKeyVal(String key, String val);


	/**
	 * 清除不带失效时间的存储内容
	 */
	public void clearKeyVal();

	/**
	 * 清除带失效时间的存储内容
	 */
	public void clearExValByKey(String key);

	/**
	 * 不带失效时间 根据主键获取内容
	 * @return
	 */
	public String getKeyVal(String key);

	/**
	 * 不带失效时间 删除key值
	 * @return
	 */
	void clearValByKey(String key);


	/**
	 *  不带失效时间的key值int缓存变化changeValue
	 * @param key
	 */
	public void incrByKey(String key, int changeValue);

	/**
	 *  获取剩余失效时间
	 * @param key
	 */
	public Long getKeyExpireTime(String key) ;

	/**
	 *  向map里面存key value
	 * @param mapKey    Map的key
	 * @param mapInKey      Map里面的Key
	 * @param mapInValue    Map里面的Value
	 * @return
	 */
    Long putInMap(String mapKey, String mapInKey, String mapInValue);

	/**
	 *获取map里面的value
	 * @param mapKey    Map的key
	 * @param mapInKey      Map里面的Key
	 * @return
	 */
	String getInMap(String mapKey, String mapInKey);

	/**
	 * 存储整个Map
	 * @param mapKey
	 * @param map
	 * @return
	 */
	String setMap(String mapKey, Map map);

	/**
	 *删除Map中的value
	 * @param mapKey  cache map key
	 * @param mapInKey  map里面value对应的key
	 * @return
	 */
	Long delValInMap(String mapKey, String mapInKey);

	/**
	 * 获取caceh中的map
	 * @param mapKey
	 * @return
	 */
    Map getMap(String mapKey);
}
