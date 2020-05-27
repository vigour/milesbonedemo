package com.milesbone.common.id.impl;

import com.milesbone.common.id.IIdGeneratorConfig;

public class DefaultIdGeneratorConfig implements IIdGeneratorConfig {

	@Override
	public String getSpiltString() {
		return "";
	}

	@Override
	public int getInitial() {
		return 1;
	}

	@Override
	public String getPrefix() {
		return "TEST";
	}

	@Override
	public int getRollingInterval() {
		return 1;
	}

}
