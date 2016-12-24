

public class Player extends Person{

	private String name;
	private int chips;
	private int bet=0; 
	
	public static void main(String[] args) {}
	
	public Player(String name, int chips)
	{
		this.name=name;
		this.chips=chips;
	}
	public String get_name() 
	{
		return name;
	}
	public int make_bet()
	{
		bet=1;
		return bet;
	}
	
	@Override
	public boolean hit_me(Table tb1) 
	{
		int value=getTotalValue();
		boolean x;
		if(value<=16)
		{
			x=true;
		}
		else
		{
			x=false;
		}
		
		return x;
		
		
	}
	
	public int get_current_chips()
	{
		return chips;
	}
	public void increase_chips (int diff)
	{
		chips+=diff;
	}
	
	public void say_hello()
	{
		System.out.println("Hello, I am " + name + "."); 
		System.out.println("I have " + chips + " chips.");
	}

	

}
