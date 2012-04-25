package com.kybernetikos.edit;

import java.util.List;

public class Insert<T> extends Action<T> {
	public Insert(T obj, int at) {
		super(obj, at);
	}

	@Override
	public String getOperationName() {
		return "insert";
	}

	@Override
	public void apply(List<T> list) {
		list.add(at, obj);
	}
}