package dev.gavin.wb.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 * @author Administrator
 *
 */
public class EncryptTool {
	
	private final static String fixkey = "fixkey";
	
	public static String md5Hex(String target){
		return DigestUtils.md5Hex(target + fixkey);
	}
	
	public static String sha1Hex(String target){
		return DigestUtils.sha1Hex(target + fixkey);
	}
	
	public static void main(String[] args) {
		
		String target = "123456";
		System.out.println("加密前：" + target);
		System.out.println("md5加密后：" + EncryptTool.md5Hex(target));
		System.out.println("sha1加密后：" + EncryptTool.sha1Hex(target));
		
	}

}