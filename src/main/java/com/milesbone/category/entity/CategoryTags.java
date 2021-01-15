package com.milesbone.category.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="device_channel_category_tags")
public class CategoryTags extends BaseMongoEntiry {

	@Field(value="mac")
	private String mac;
	
	@Field(value="site")
	private String site;
	
	@Field(value="channel")
	private String channel;
	
	@Field(value="cid")
	private String cid;
	
	@Field(value="workday_channelTop3_30_daytime")
	private List<String> workdayChannelTop3By30daytime;
	
	@Field(value="workday_channelTop3_30_night")
	private List<String> workdayChannelTop3By30night;
	
	@Field(value="holiday_channelTop3_30")
	private List<String> holidayChannelTop3By30;
	
	@Field(value="workday_channelTop3_3_daytime")
	private List<String> workdayChannelTop3By3daytime;
	
	@Field(value="workday_channelTop3_3_night")
	private List<String> workdayChannelTop3By3night;
	
	@Field(value="holiday_channelTop3_3")
	private List<String> holidayChannelTop3By3;
	
	@Field(value="workday_categoryTop5_30_daytime")
	private List<String> workdayCetegoryTop5By30daytime;
	
	@Field(value="workday_cetegoryTop5_30_night")
	private List<String> workdayCetegoryTop5By30night;
	
	@Field(value="holiday_categoryTop5_30")
	private List<String> holidayCategoryTop5By30;
	
	@Field(value="workday_categoryTop5_3_daytime")
	private List<String> workdayCetegoryTop5By3daytime;
	
	@Field(value="workday_cetegoryTop5_3_night")
	private List<String> workdayCetegoryTop5By3night;
	
	@Field(value="holiday_categoryTop5_3")
	private List<String> holidayCategoryTop5By3;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<String> getWorkdayChannelTop3By30daytime() {
		return workdayChannelTop3By30daytime;
	}

	public void setWorkdayChannelTop3By30daytime(List<String> workdayChannelTop3By30daytime) {
		this.workdayChannelTop3By30daytime = workdayChannelTop3By30daytime;
	}

	public List<String> getWorkdayChannelTop3By30night() {
		return workdayChannelTop3By30night;
	}

	public void setWorkdayChannelTop3By30night(List<String> workdayChannelTop3By30night) {
		this.workdayChannelTop3By30night = workdayChannelTop3By30night;
	}

	public List<String> getHolidayChannelTop3By30() {
		return holidayChannelTop3By30;
	}

	public void setHolidayChannelTop3By30(List<String> holidayChannelTop3By30) {
		this.holidayChannelTop3By30 = holidayChannelTop3By30;
	}

	public List<String> getWorkdayChannelTop3By3daytime() {
		return workdayChannelTop3By3daytime;
	}

	public void setWorkdayChannelTop3By3daytime(List<String> workdayChannelTop3By3daytime) {
		this.workdayChannelTop3By3daytime = workdayChannelTop3By3daytime;
	}

	public List<String> getWorkdayChannelTop3By3night() {
		return workdayChannelTop3By3night;
	}

	public void setWorkdayChannelTop3By3night(List<String> workdayChannelTop3By3night) {
		this.workdayChannelTop3By3night = workdayChannelTop3By3night;
	}

	public List<String> getHolidayChannelTop3By3() {
		return holidayChannelTop3By3;
	}

	public void setHolidayChannelTop3By3(List<String> holidayChannelTop3By3) {
		this.holidayChannelTop3By3 = holidayChannelTop3By3;
	}

	public List<String> getWorkdayCetegoryTop5By30daytime() {
		return workdayCetegoryTop5By30daytime;
	}

	public void setWorkdayCetegoryTop5By30daytime(List<String> workdayCetegoryTop5By30daytime) {
		this.workdayCetegoryTop5By30daytime = workdayCetegoryTop5By30daytime;
	}

	public List<String> getWorkdayCetegoryTop5By30night() {
		return workdayCetegoryTop5By30night;
	}

	public void setWorkdayCetegoryTop5By30night(List<String> workdayCetegoryTop5By30night) {
		this.workdayCetegoryTop5By30night = workdayCetegoryTop5By30night;
	}

	public List<String> getHolidayCategoryTop5By30() {
		return holidayCategoryTop5By30;
	}

	public void setHolidayCategoryTop5By30(List<String> holidayCategoryTop5By30) {
		this.holidayCategoryTop5By30 = holidayCategoryTop5By30;
	}

	public List<String> getWorkdayCetegoryTop5By3daytime() {
		return workdayCetegoryTop5By3daytime;
	}

	public void setWorkdayCetegoryTop5By3daytime(List<String> workdayCetegoryTop5By3daytime) {
		this.workdayCetegoryTop5By3daytime = workdayCetegoryTop5By3daytime;
	}

	public List<String> getWorkdayCetegoryTop5By3night() {
		return workdayCetegoryTop5By3night;
	}

	public void setWorkdayCetegoryTop5By3night(List<String> workdayCetegoryTop5By3night) {
		this.workdayCetegoryTop5By3night = workdayCetegoryTop5By3night;
	}

	public List<String> getHolidayCategoryTop5By3() {
		return holidayCategoryTop5By3;
	}

	public void setHolidayCategoryTop5By3(List<String> holidayCategoryTop5By3) {
		this.holidayCategoryTop5By3 = holidayCategoryTop5By3;
	}

	public String toString() {
		return "CategoryTags [mac=" + mac + ", site=" + site + ", channel=" + channel + ", cid=" + cid
				+ ", workdayChannelTop3By30daytime=" + workdayChannelTop3By30daytime + ", workdayChannelTop3By30night="
				+ workdayChannelTop3By30night + ", holidayChannelTop3By30=" + holidayChannelTop3By30
				+ ", workdayChannelTop3By3daytime=" + workdayChannelTop3By3daytime + ", workdayChannelTop3By3night="
				+ workdayChannelTop3By3night + ", holidayChannelTop3By3=" + holidayChannelTop3By3
				+ ", workdayCetegoryTop5By30daytime=" + workdayCetegoryTop5By30daytime
				+ ", workdayCetegoryTop5By30night=" + workdayCetegoryTop5By30night + ", holidayCategoryTop5By30="
				+ holidayCategoryTop5By30 + ", workdayCetegoryTop5By3daytime=" + workdayCetegoryTop5By3daytime
				+ ", workdayCetegoryTop5By3night=" + workdayCetegoryTop5By3night + ", holidayCategoryTop5By3="
				+ holidayCategoryTop5By3 + "]";
	}
	
	
	
}
