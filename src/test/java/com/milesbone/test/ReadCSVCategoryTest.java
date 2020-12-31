package com.milesbone.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.milesbone.entity.Category;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.ICSVWriter;

public class ReadCSVCategoryTest {

	private static final String TAG_CSV_DIR = "/Users/flnet/Documents/work/AI推荐/tag";

	public static final Map<String, String> SITE_MAP = new HashMap<String, String>() {
		/**
		 * @Fields serialVersionUID : TODO
		 */
		private static final long serialVersionUID = 801818317363380227L;
		{
			put("iqiyi", "0");
			put("youku", "1");
		}
	};

	public static final Map<String, String> CID_MAP = new HashMap<String, String>() {
		/**
		 * @Fields serialVersionUID : TODO
		 */
		private static final long serialVersionUID = -8405440963545287159L;

		{
			put("1", "电影");
			put("2", "电视剧");
			put("3", "动漫");
			put("4", "综艺");
			put("5", "纪录片");
			put("8", "少儿");
		}
	};

	@Test
	public void testReadCategory() {
		File dirpath = new File(TAG_CSV_DIR);
		if (dirpath.isDirectory()) {
			File[] files = dirpath.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					String filepath = file.getPath();
					if (file.isFile() && filepath.endsWith(".csv")) {
						String fileName = file.getName();
						String[] nameArr = fileName.substring(0, fileName.lastIndexOf(".")).split("_");
						String site = nameArr[0];
						String cid = nameArr[1];
						System.out.println(file.getPath());
						System.out.println(file.getAbsolutePath());
						System.out.println(file.getParent());
						System.out.println(file.getTotalSpace());
						System.out.println(file.getName());
						System.out.println(fileName.substring(0, fileName.lastIndexOf(".")));
						this.readCSVByLine(filepath, site, cid);
						break;
					}
				}
			}
		}
	}

	public void readCSVByLine(String finalPath, String site, String cid) {
		try {
			final CSVParser parser = new CSVParserBuilder().withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
					.withIgnoreQuotations(true).build();
			// 使用BOMInputStream自动去除UTF-8中的BOM
			final CSVReader reader = new CSVReaderBuilder(
					new InputStreamReader(new BOMInputStream(new FileInputStream(finalPath)), "utf-8")).withSkipLines(0)
							.withCSVParser(parser).build();

			/*
			 * 逐行读取
			 */
			String[] strArr = null;
			Category category = null;
			int catIndex = 0;
			while ((strArr = reader.readNext()) != null) {
				if (StringUtils.isBlank(strArr[2])) {
					category = new Category(SITE_MAP.get(site), site, Integer.parseInt(cid+""), CID_MAP.get(cid),
							strArr[0],  String.valueOf(++catIndex),"1");
					System.out.println(category);
				}
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
