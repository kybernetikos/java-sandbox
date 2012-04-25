package com.kybernetikos.edit;

import java.util.List;

public class Delete<T> extends Action<T> {
	public Delete(T obj, int at) {
		super(obj, at);
	}

	@Override
	public String getOperationName() {
		return "delete";
	}

	@Override
	public void apply(List<T> list) {
		T deleted = list.remove(at);
		if (deleted.equals(obj) == false) {
			throw new RuntimeException(deleted+" was not "+obj);
		}
	}
}