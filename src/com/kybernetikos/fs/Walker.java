package com.kybernetikos.fs;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Walker {
	protected Walker() {}
	
	public static FileFilter JUST_FILES = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.isFile();
		}
	};
	
	public static FileFilter JUST_DIRECTORIES = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	};
	
	public static Set<File> filter(Set<File> files, FileFilter filter) {
		Set<File> result = new TreeSet<File>(files);
		Iterator<File> itr = result.iterator();
		while (itr.hasNext()) {
			File f = itr.next();
			if ( ! filter.accept(f)) {
				itr.remove();
			}
		}
		return result;
	}
	
	public static Set<File> walkTree(File root, FileFilter filter) throws IOException {
		return filter(walkTree(root, new TreeSet<File>()), filter);
	}
	
	public static Set<File> walkTree(File root) throws IOException {
		return walkTree(root, new TreeSet<File>());
	}
	
	public static Set<File> walkTree(File root, Set<File> result) throws IOException {
		if ( ! result.contains(root.getCanonicalFile())) {
			result.add(root.getCanonicalFile());
			if (root.isDirectory()) {
				File[] listFiles = root.listFiles();
				for (File f : listFiles) {
					walkTree(f, result);
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		System.err.println(walkTree(new File("."), JUST_FILES));
	}
}