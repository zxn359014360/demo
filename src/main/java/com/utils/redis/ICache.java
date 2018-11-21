package com.utils.redis;


/***
 * 系统运行参数-缓存接口
 * @author xieyi
 *
 */
public interface ICache<T> {

	/**
	 * 根据键值获取系统运行参数对象
	 * @param key 键值
	 * @return
	 */
	public T getParam(String key);

	/***
	 * 根据键值获取系统运行参数对象值
	 * @param key
	 * @return
	 */
	public String getParamValue(String key);

	/***
	 * 添加系统运行参数对象
	 * @param param
	 */
	public void addParam(T param);

}
