package com.mwk.tools.name;

import com.mwk.utils.PathUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author MinWeikai
 * @date 2020/7/17 16:43
 */
public class CreateUserName {

	public static void main(String[] args) {
		System.out.println(getRandomUserName("闵", 10));
		System.out.println(getRandomUserName(10));
	}

	/**
	 * 获取随机姓名
	 *
	 * @param n 姓名个数
	 * @return
	 */
	private static List<String> getRandomUserName(int n) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(createSurname() + createUserName());
		}
		return list;
	}

	/**
	 * 获取随机姓名
	 *
	 * @param surname 姓
	 * @param n       姓名个数
	 * @return
	 */
	private static List<String> getRandomUserName(String surname, int n) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(surname + createUserName());
		}
		return list;
	}


	/**
	 * 获取名字
	 *
	 * @return
	 */
	private static String createUserName() {
		String nameData = getText("name-database.txt");
		return createName(nameData, 2);
	}

	/**
	 * 生成姓
	 *
	 * @return
	 */
	private static String createSurname() {
		String nameData = getText("surname-database.txt");
		return createName(nameData, 1);
	}

	/**
	 * 根据名字库获取名字
	 *
	 * @param nameData
	 * @param offset   长度
	 * @return
	 */
	private static String createName(String nameData, int offset) {
		//名字库文本长度
		int nameDataLength = nameData.length();

		Random random = new Random();
		//名字截取开始
		int start = random.nextInt(nameDataLength);
		//名字截取结尾 = 名字截取开始 + 名字长度
		int end = start + offset;

		//名字截取结尾不可以大于名字库文本长度
		if (end > nameDataLength) {
			//重新生成名字
			return createName(nameData, offset);
		}

		//特殊符号校验
		String name = nameData.substring(start, end);
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？《》 ]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(name);
		if (m.find()) {
			//重新生成名字
			return createName(nameData, offset);
		}
		return name;
	}

	/**
	 * 获取名字文本
	 *
	 * @return
	 */
	private static String getText(String fileName) {
		File file = new File(PathUtil.getResources(fileName, CreateUserName.class));
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
