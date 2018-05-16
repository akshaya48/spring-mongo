package com.example.demo;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GeneralUtil {
	
	public static List<SportsPerson> applyLogic(List<SportsPerson> sp, Predicate<SportsPerson> predicate){
		 return sp.parallelStream()
	    		.filter(predicate)
	    		.collect(Collectors.toList());
	}
	
	public static boolean isGreaterThan130(SportsPerson sp){
		return sp.getId() > 130;
	}

}
