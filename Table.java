import java.util.ArrayList;

public class Table {

	public static void main(String[] args) {}
	
	public static final int MAXPLAYER = 4;
	private Deck deck;
	private Player[] players;
	private Dealer dealer;
	private int[] pos_betArray;
	
	private int nDecks;
	
	public Table(int nDecks)
	{
		this.nDecks=nDecks;
		this.deck=new Deck(nDecks);
		this.players = new Player[MAXPLAYER];
	}
	public void set_player(int pos, Player p)
	{
		this.players[pos]=p;
	}
	public Player[] get_player()
	{
		return this.players;
		
	}
	public void set_dealer(Dealer d)
	{
		this.dealer=d;
	}
	public Card get_face_up_card_of_dealer()
	{
		Card dealerFacecard=(Card)this.dealer.getOneRoundCard().get(1);
		return dealerFacecard;
		
	}
	private void ask_each_player_about_bets()
	{
		this.pos_betArray = new int[this.players.length];
		for (int i = 0; i < this.players.length; i++) 
		{
			if (this.players[i] != null)
			{
				this.players[i].say_hello();
				int bet = this.players[i].make_bet();
				
				if (bet > this.players[i].get_current_chips())
				{
		           this.pos_betArray[i] = 0;
		        }
		       else
		       	{
		          this.pos_betArray[i] = this.players[i].make_bet();
		       	}
			}
		}
	}
	private void distribute_cards_to_dealer_and_players()
	{
		for (int i = 0; i < this.players.length; i++) 
		{
			if ((this.players[i] != null) && (this.pos_betArray[i] != 0))
			{
				 ArrayList<Card> playersCard = new ArrayList();
				 playersCard.add(this.deck.getOneCard(true));
				 playersCard.add(this.deck.getOneCard(true));
				 this.players[i].setOneRoundCard(playersCard);
			}
		}
		if (this.dealer != null)
		{
			ArrayList<Card> dealersCard = new ArrayList();
			dealersCard.add(this.deck.getOneCard(true));
			dealersCard.add(this.deck.getOneCard(false));
			this.dealer.setOneRoundCard(dealersCard);
			System.out.print("Dealer's face up card is ");
			Card dealers_face_up_card = get_face_up_card_of_dealer();
			dealers_face_up_card.printCard();
		}
	}
	private void ask_each_player_about_hits()
	{
		
		boolean hit;
		for (int i = 0; i < this.players.length; i++) 
		{
			ArrayList<Card> cards = this.players[i].getOneRoundCard();
			if ((this.players[i] != null) && (this.pos_betArray[i] != 0))
			{
				System.out.print(this.players[i].get_name() + "'s Cards now:");
				this.players[i].printAllCard();
				do
				{
					hit = this.players[i].hit_me(this);
					if (hit)
					{
						cards.add(this.deck.getOneCard(true));
						this.players[i].setOneRoundCard(cards);
						System.out.print("Hit! ");
						System.out.print(this.players[i].get_name() + "'s Cards now:");
						this.players[i].printAllCard();
						if (this.players[i].getTotalValue() > 21) 
						{
							hit = false;
						}
					}
					else
					{
						System.out.println("Pass hit!");
					}
				} while (hit);
				System.out.println(this.players[i].get_name() + "'s hit is over!");
			}
		}
		
		
	}
	private void ask_dealer_about_hits()
	{
		ArrayList<Card> cards = this.dealer.getOneRoundCard();
		boolean hit;
		do
		{
			hit = this.dealer.hit_me(this);
			if (hit)
			{
				cards.add(this.deck.getOneCard(true));
				this.dealer.setOneRoundCard(cards);
			}
			if (this.dealer.getTotalValue() > 21) 
			{
				hit = false;
			}
		} while (hit);
		System.out.println("Dealer's hit is over!");
	}
	private void calculate_chips()
	{
		int dealersCradValue = this.dealer.getTotalValue();
		System.out.print("Dealer's card value is " + dealersCradValue + " ,Cards:");
		this.dealer.printAllCard();
		for (int i = 0; i < this.players.length; i++) 
		{
			if ((this.players[i] != null) && (this.pos_betArray[i] != 0))
			{
				System.out.print(this.players[i].get_name() + "'s Cards: ");
				this.players[i].printAllCard();
				
				System.out.print(this.players[i].get_name() + " card value is " + this.players[i].getTotalValue());
				if (this.players[i].getTotalValue() > 21)
				{
					if (this.dealer.getTotalValue() > 21)
					{
						System.out.println(", chips have no change!, the Chips now is: " + this.players[i].get_current_chips());
					}
					else
					{
						this.players[i].increase_chips(-this.pos_betArray[i]);
						System.out.println(", Loss " + this.pos_betArray[i] + " Chips, the Chips now is: " + this.players[i].get_current_chips());
					}
				}
				else if (this.players[i].getTotalValue() > this.dealer.getTotalValue())
				{
					this.players[i].increase_chips(this.pos_betArray[i]);
					System.out.println(", Get " + this.pos_betArray[i] + " Chips, the Chips now is: " + this.players[i].get_current_chips());
				}
				else
				{
					if (this.dealer.getTotalValue() > 21)
					{
						this.players[i].increase_chips(this.pos_betArray[i]);
						System.out.println(", Get " + this.pos_betArray[i] + " Chips, the Chips now is: " + this.players[i].get_current_chips());
					}
					else
					{
					this.players[i].increase_chips(-this.pos_betArray[i]);
					System.out.println(", Loss " + this.pos_betArray[i] + " Chips, the Chips now is: " + this.players[i].get_current_chips());
					}
				}
				
			}
		}
	}
	public int[] get_palyers_bet()
	{
		return this.pos_betArray;
	}
	public void play(){
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}


}
