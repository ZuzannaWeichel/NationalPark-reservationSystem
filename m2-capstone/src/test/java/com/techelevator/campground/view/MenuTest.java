package com.techelevator.campground.view;

import java.awt.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MenuTest {

	private ByteArrayOutputStream output;

	@Before
	public void setup() {
		output = new ByteArrayOutputStream();
	}
	
	@Test
	public void displays_a_list_of_menu_options_and_prompts_user_to_make_a_choice() {
		Object[] option = new Object[] {  new Integer(3), "Blind", "Mice" };
		LinkedList<Object> options = new LinkedList<Object>();
		options.add(option[0].toString());
		options.add(option[1].toString());
		options.add(option[2].toString());
		Menu menu = getMenuForTesting();
		
		menu.getChoiceFromOptions(options, false, false, false);
		
		String expected = "\n"+
				 		  "1)  "+options.get(0) + "\n" + 
						  "2)  "+options.get(1) + "\n" +
						  "3)  "+options.get(2) + "\n\n" +
						  "Please choose an option >>> ";
		Assert.assertEquals(expected, output.toString());	  
	}
	
	@Test
	public void displays_a_list_of_menu_options_and_prompts_user_to_make_a_choice_with_quit() {
		Object[] option = new Object[] {  new Integer(3), "Blind", "Mice" };
		LinkedList<Object> options = new LinkedList<Object>();
		options.add(option[0].toString());
		options.add(option[1].toString());
		options.add(option[2].toString());
		Menu menu = getMenuForTesting();
		
		menu.getChoiceFromOptions(options, true, false, false);
		
		String expected = "\n"+
				 		  "1)  "+options.get(0) + "\n" + 
						  "2)  "+options.get(1) + "\n" +
						  "3)  "+options.get(2) + "\n" +
						  "Q)  " + "quit" + "\n\n" +
						  "Please choose an option >>> ";
		Assert.assertEquals(expected, output.toString());	  
	}
	
	@Test
	public void returns_object_corresponding_to_user_choice() {
		Integer expected = new Integer(456);
		LinkedList<Object> options = new LinkedList<Object>();
		options.add(new Integer(123));
		options.add(expected);
		options.add(new Integer(789));
		
		Menu menu = getMenuForTestingWithUserInput("2\n");

		Integer result = (Integer)menu.getChoiceFromOptions(options, false, false, false);
		
		Assert.assertEquals(expected, result);	  
	}
	
	@Test
	public void redisplays_menu_if_user_does_not_choose_valid_option() {
		Object[] option = new Object[] {  "Larry", "Curly", "Moe" };
		LinkedList<Object> options = new LinkedList<Object>();
		options.add(option[0].toString());
		options.add(option[1].toString());
		options.add(option[2].toString());
		Menu menu = getMenuForTestingWithUserInput("4\n1\n");
		
		menu.getChoiceFromOptions(options, false, false, false);
		
		String menuDisplay = "\n"+
				  "1)  "+options.get(0) + "\n" + 
				  "2)  "+options.get(1) + "\n" +
				  "3)  "+options.get(2) + "\n\n" +
						     "Please choose an option >>> ";
		
		String expected = menuDisplay + 
					      "\n*** 4 is not a valid option ***\n\n" +
					      menuDisplay;
		
		Assert.assertEquals(expected, output.toString());
	}
	
	@Test
	public void redisplays_menu_if_user_enters_garbage() {
		Object[] option = new Object[] {  "Larry", "Curly", "Moe" };
		LinkedList<Object> options = new LinkedList<Object>();
		options.add(option[0].toString());
		options.add(option[1].toString());
		options.add(option[2].toString());
		Menu menu = getMenuForTestingWithUserInput("Mickey Mouse\n1\n");
		
		menu.getChoiceFromOptions(options, false, false, false);
		
		String menuDisplay = "\n"+
				  "1)  "+options.get(0) + "\n" + 
				  "2)  "+options.get(1) + "\n" +
				  "3)  "+options.get(2) + "\n\n" +
						     "Please choose an option >>> ";
		
		String expected = menuDisplay + 
					      "\n*** Mickey Mouse is not a valid option ***\n\n" +
					      menuDisplay;
		
		Assert.assertEquals(expected, output.toString());
	}

	private Menu getMenuForTestingWithUserInput(String userInput) {
		ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
		return new Menu(input, output);
	}

	private Menu getMenuForTesting() {
		return getMenuForTestingWithUserInput("1\n");
	}

}
