package com.wyh.javabase.spi;

import java.util.ServiceLoader;

/**
 * SPI:
 * 	声明一个接口(Animal)，有多个实现类(Dog、Cat)
 * 
 * 通过ServiceLoader.load(接口名)的方式可以获取/META-INF/services下的所有实现类，将之加载到JVM
 * 
 * 文件名：接口全名
 * 文件内容：所有需要加载到JVM的实现类全名，每个实现类一行
 *
 */
public class SpiTest {
	
	public static void main(String[] args) {
		ServiceLoader<Animal> sl = ServiceLoader.load(Animal.class);
		sl.forEach((animal) -> {
			animal.show();
		});
	}

}
