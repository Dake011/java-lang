package kz.edu.nu.cs.teaching;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class App {
    static final String h = "~~~~~~~~~~~~\n";
    public static int res = 0;
    
    public static void main(String[] args) {
        
        System.out.println("\nTask 1 " + h);
        wordcount(getTestLinesStream());
        
        System.out.println("\nTask 2 " + h);
        filterByWordLength(getTestLinesStream());

        System.out.println("\nTask 3 " + h);
        groupWordsByFirstCharacter(getTestLinesStream());
    }
    
    /*
     * Task 1, count words in entire file
     */
    public static void wordcount(Stream<String> stream) {

    	stream.forEach(words -> {
    			words = words.replace("(", "").replace(")", "");
    			String[] lineOfWords = words.split("\\W+");
    			res += lineOfWords.length;
    	});
    	System.out.println("Number of words: " + res);
    }
    /*
     * Task 2, filter lines by lengths of longest words
     */
    public static void filterByWordLength(Stream<String> stream) {
    	 Map< String,Integer> hm =  new HashMap< String,Integer>(); 
    	 stream.forEach(words -> {
    		int max_v = 0;
 			words = words.replace("(", "").replace(")", "");
 			String[] lineOfWords = words.split("\\W+");
 			for(String word:lineOfWords) {
 				if(word.length() > max_v) {
 					max_v = word.length();
 				}
 			}
 			hm.put(words, max_v);
    	 });
    	hm.entrySet().stream().filter(p ->  p.getValue() > 7).forEach(line->{
    		if(line.getValue() > 7) {
    			System.out.println(line.getKey());  
    		}
    	 });
    }
    
    /*
     * Task 3, group words by first character
     */
    public static void groupWordsByFirstCharacter(Stream<String> stream) {
    	Map< String,Integer> result =  new HashMap< String,Integer>(); 
		ArrayList<String> arrayOfWords = new ArrayList<String>(); 
		stream.flatMap(line -> Stream.of(line)).forEach(
				words -> {
					words = words.replace("(", "").replace(")", "");
		 			String[] lineOfWords = words.split("\\W+");
		 			for(String word:lineOfWords) {
		 				arrayOfWords.add(word);
		 			}
				});
		Map<Object, List<String>> hm  = arrayOfWords.stream().collect(Collectors.groupingBy(s -> s.charAt(0))); 
		hm.entrySet().stream().forEach(line->{
    			result.put(line.getKey().toString(), line.getValue().size());
    	 });
		System.out.println(result);
		
    }
    
    /*
     * Return Stream of lines from file
     */
    public static Stream<String> getTestLinesStream() {
        File file = new File("lambtest.txt");
        
        try {
            return Files.lines(file.toPath());
        } catch (Exception e) {
            System.out.println("Error reading from file");
            return null;
        }
    }
    
    
}
