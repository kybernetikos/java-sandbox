package com.kybernetikos.edit;

import java.util.List;

public abstract class Action<T> {
	protected int at;
	protected T obj;
	
	public Action(T obj, int at) {
		this.obj = obj;
		this.at = at;
	}
	
	public abstract String getOperationName();

	public abstract void apply(List<T> list);
	
	@Override
	public String toString() {
		return getOperationName()+" object "+obj+" at position "+at;
	}
}