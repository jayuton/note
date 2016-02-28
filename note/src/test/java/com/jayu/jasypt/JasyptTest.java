package com.jayu.jasypt;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
	// TODO Auto-generated method stub
	EnvironmentStringPBEConfig ec = new EnvironmentStringPBEConfig();
	ec.setAlgorithm("PBEWithMD5AndDES");
	//ec.setPasswordEnvName("APP_ENCRYPTION_PASSWORD");
	ec.setPassword("111");
	StandardPBEStringEncryptor sp = new StandardPBEStringEncryptor();
	sp.setConfig(ec);
	sp.setStringOutputType("base64");
	String str="111";//
	str="root";
	String estr=sp.encrypt(str);
	String dstr=sp.decrypt("");
	System.out.println("enc:== "+estr );
	System.out.println("dec:== "+dstr );
	}
}
