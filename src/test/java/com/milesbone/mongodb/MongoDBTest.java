package com.milesbone.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.milesbone.base.AbstractServiceTestCase;
import com.milesbone.entity.Category;
import com.milesbone.entity.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.ICSVWriter;

//@RunWith(SpringRunner.class)
//@ContextConfiguration(locations = "classpath:config/spring/spring-*.xml")
public class MongoDBTest extends AbstractServiceTestCase{

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

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void insert() {
		// 单条插入
		List<String> phones = new ArrayList<String>();
		phones.add("1234567890");
		phones.add("1234567891");
		phones.add("1234567892");
		mongoTemplate.insert(new User("testarray", 14, 5000.21f, new Date(), phones));
		// List<User> programmers = new ArrayList<User>();
		// // 批量插入
		// programmers.add(new User("xiaohong", 21, 52200.21f, new Date()));
		// programmers.add(new User("xiaolan", 34, 500.21f, new Date()));
		// mongoTemplate.insert(programmers, User.class);
	}

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
//						break;
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
//					System.out.println(category);
					this.mongoTemplate.insert(category, "category");
				}
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 条件查询
	@Test
	public void select() {
		Criteria criteria = new Criteria();
		criteria.andOperator(where("state").is("1"),where("cid").is(1), where("site").is("0"), where("name").is("喜剧"));
		Query query = new Query(criteria);
//		List<Category> categories = mongoTemplate.find(query, Category.class, "category");
		Category categories = mongoTemplate.findOne(query, Category.class, "category");
//		User one = mongoTemplate.findOne(query, User.class, "user");
		System.out.println(categories);
	}
	// 条件查询
	@Test
	public void select1() {
		Criteria criteria = new Criteria();
		criteria.andOperator(where("name").is("fa"));
		Query query = new Query(criteria);
//		List<Category> categories = mongoTemplate.find(query, Category.class, "category");
//		Category categories = mongoTemplate.findOne(query, Category.class, "category");
		User one = mongoTemplate.findOne(query, User.class, "user");
		System.out.println(one);
	}

	// 更新数据
	@Test
	public void MUpdate() {
		UpdateResult updateResult = mongoTemplate.updateMulti(query(where("name").is("xiaoming")), update("age", 35),
				User.class);
		System.out.println("更新记录数：" + updateResult.getModifiedCount());
	}

	// 删除指定数据
	@Test
	public void delete() {
		DeleteResult result = mongoTemplate.remove(query(where("name").is("fa")), User.class);
		System.out.println("影响记录数：" + result.getDeletedCount());
		System.out.println("是否成功：" + result.wasAcknowledged());
	}
}
