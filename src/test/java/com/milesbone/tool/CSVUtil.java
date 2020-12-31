package com.milesbone.tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.input.BOMInputStream;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.ICSVWriter;

public class CSVUtil {
	
	public void readCSVByLine(String finalPath) {
		try {
			final CSVParser parser = new CSVParserBuilder()
					.withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
					.withIgnoreQuotations(true)
					.build();
			// 使用BOMInputStream自动去除UTF-8中的BOM
			final CSVReader reader = new CSVReaderBuilder(
					new InputStreamReader(
							new BOMInputStream(new FileInputStream(finalPath)), "UTF-8"))
					.withSkipLines(0)
					.withCSVParser(parser)
					.build();

			/*
			 * 逐行读取
			 */
			String[] strArr = null;
			while ((strArr = reader.readNext()) != null) {
				System.out.println(strArr[0] + "---" + strArr[1] + "----" + strArr[2]);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
