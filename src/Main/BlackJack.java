package Main;
import java.util.Scanner;
import java.util.Vector;
import java.util.Random;
import java.util.LinkedList;

public class BlackJack 
{	
	FinishCode mCode;
	
	static public Integer MAX_CARD_COUNT = 52;
	static public Integer MAX_CARD_TYPE = 4;
	
	//���� �� ����� ī�带 ����ִ� ť
	private LinkedList<Card> mCards;
	
	//���� �� �� �������� ī�带 �����ϴ� ����
	private Vector<Card> mPlayerCard;
	private Vector<Card> mDealerCard;
	
	static Random Rand = new Random();
	
	//������
	public BlackJack()
	{
		mCards = new LinkedList<>();
		mPlayerCard = new Vector<>();
		mDealerCard = new Vector<>();
		
		mCode = FinishCode.NONE;
		
		InitGame();
	}

	//����� ����������� �ʱ�ȭ�Ѵ�
	public void InitGame()
	{
		String tempSuit = "?";
		
		for(int i = 1; i <= MAX_CARD_COUNT / MAX_CARD_TYPE; ++i)
		{
			for(int j = 0; j < MAX_CARD_TYPE; ++j)
			{
				switch(j)
				{
					case 0: 
					{
						tempSuit = "��";
						
						break;
					}
					
					case 1: 
					{
						tempSuit = "��";
						
						break;
					}
					
					case 2: 
					{
						tempSuit = "��";
						
						break;
					}
					
					case 3: 
					{
						tempSuit = "��";
						
						break;
					}
					
					default:
					{
						System.out.println("Index Error");
						System.exit(0);
					}
				}
				
				mCards.add(new Card(tempSuit, i));
			}
		}
		
		
		int randPos;
		
		//������ �÷��̾�� �ߺ����� ���� ī�带 �� ���徿 �����ش�.
		//Queue�� Poll�Լ��� ���ο��ִ� ���Ҹ� �����ϸ� �����Ѵ�.
		for(int i = 0; i < 2; ++i)
		{
			randPos = Rand.nextInt(mCards.size());
			mPlayerCard.add(mCards.get(randPos));
			mCards.remove(randPos);
			
			randPos = Rand.nextInt(mCards.size());
			mDealerCard.add(mCards.get(randPos));
			mCards.remove(randPos);
		}
	}
	
	//���� ����
	public void RunGame()
	{
		//�� �ϸ��� ���� ī����� ����ϰ� �ÿ��� �Է��� �޴´�
		do
		{
			DisplayInfo();
			InputPlayer();
		}
		while (mCode == FinishCode.NONE); //mCode�� NONE(�ºΰ� �ȳ� ����)�� �ƴ϶�� �������� ����� ����Ѵ�.
		
		//���� �¸��ߴ��� ���
		DisplayResult();

		//���� ī����� �ѹ� �� ���
		DisplayInfo();
	}

	//���� ī����� ����ϴ� �Լ�
	public void DisplayInfo()
	{
		Card tempCard;
		
		System.out.println("------------- Jack's BlackJack Game -------------");
		
		System.out.print("  # Dealer: ");
		//0�� A��, 11�̻��� �� J Q K�� ����Ѵ� �� �ܴ� ���� �״��
		for(int i = 0; i < mDealerCard.size() - 1; ++i)
		{
			tempCard = mDealerCard.get(i);
			
			tempCard.DisplayCard();
		}
		
		if(mCode == FinishCode.NONE)
		{
			System.out.print("XX");
		}
		
		else
		{
			tempCard = mDealerCard.get(mDealerCard.size() - 1);
			
			tempCard.DisplayCard();
		}
		
		System.out.println();
		
		System.out.print("  # Player: ");
		for(int i = 0; i < mPlayerCard.size(); ++i)
		{
			tempCard = mPlayerCard.get(i);
			
			tempCard.DisplayCard();
		}
		
		System.out.println();
		
		System.out.println("-------------------------------------------------");
	}
	
	//������� �Է��� �޴´�, H / S
	public void InputPlayer()
	{
		Scanner Scan = new Scanner(System.in);
		
		//System.out.printf("������ �ι�° ī��: %-3s, ���� ī��: %-3s\n", mDealerCard.get(1) >= 10 ? 10 : mDealerCard.get(1), mCards.peek() >= 10 ? 10 : mCards.peek());
		
		System.out.print("Hit or Stand? (H/S): ");
		
		String input = Scan.nextLine();
		
		System.out.println();
		
		//string - switch������ ó���Ҷ� ������� �Է��� �ִ��� �ڿ������� �޴´� (hit, Hit, h, H...)
		switch(input)
		{
			case "h":
			{
				
			}
			case "H":
			{
				
			}
			case "Hit":
			{
				
			}
			case "hit":
			{
				Hit();
				break;
			}
			
			case "s":
			{
				
			}
			case "S":
			{
				
			}
			case "Stand":
			{
				
			}
			case "stand":
			{
				Stand();
				break;
			}
			
			default:
			{
				
			}
		}
	}
	
	//Hit�� �ϸ� �÷��̾��� ī�庤�Ϳ� ��üī��ť���� poll�Ͽ� �߰��Ѵ�.
	private void Hit()
	{
		int randPos = Rand.nextInt(mCards.size());
		mPlayerCard.add(mCards.get(randPos));
		mCards.remove(randPos);
		
		//���� �߰����� �� �� ���� 21�� �ʰ��ϸ� �÷��̾�� BUSTED�Ѵ�.
		if(CalcPrice(mPlayerCard) > 21)
		{
			mCode = FinishCode.PLAYERBUSTED;
		}
	}
	
	//Stand�� �ϸ� ������ ī�庤�Ϳ� ��üī�� ť�� 17�̻��� �ɶ����� ��üī��ť���� poll�Ѵ�.
	private void Stand()
	{
		while(CalcPrice(mDealerCard) < 17)
		{
			int randPos = Rand.nextInt(mCards.size());
			mDealerCard.add(mCards.get(randPos));
			mCards.remove(randPos);
		}
		
		//���࿡ 21���� Ŀ���� ������ BUSTED�Ѵ�.
		if(CalcPrice(mDealerCard) > 21)
		{
			mCode = FinishCode.DEALERBUSTED;
		}
		//�װ��� �ƴ϶�� �÷��̾�� ������ ���� ���� �ʿ䰡 �ִ�.
		else
		{
			mCode = FinishCode.NEEDCALC;
		}
	}
	
	private int CalcPrice(Vector<Card> cards)
	{
		int sumRes = 0;
		Card tempCard = null;
		
		for(int i = 0; i < cards.size(); ++i)
		{
			tempCard = cards.get(i);
			
			sumRes += tempCard.GetRank();
		}
		
		//21�� �ʰ��� ��������
		//A ī�带 ������ �ְ�, �� ī�尡 11�� ���ǰ� �������
		if(sumRes > 21)
		{
			for(int i = 0; i < cards.size(); ++i)
			{
				//��ũ�� 11�̴�? -> Calcto11�� True�ΰ��?
				if(cards.get(i).GetRank() == 11)
				{
					//�ش� Aī���� 11���θ� false�� �ϰ�
					cards.get(i).SetACalcTo11(false);
					//11 -> 1�� �߱⿡ 10�� ���� �����Ѵ�.
					sumRes -= 10;
					
					//����׿� ���
					System.out.println("A ī�尡 11���� 1�� �� �����");
				}
			}
		}
		
		return sumRes;
	}

	//������ ������ ���� �̰���� ����Ѵ�.
	private void CalcWinner()
	{
		int playerVal = CalcPrice(mPlayerCard);
		int dealerVal = CalcPrice(mDealerCard);
		
		if(playerVal > 21 && dealerVal > 21)
		{
			mCode = FinishCode.DRAW;
		}
		
		else if(playerVal == dealerVal)
		{
			mCode = FinishCode.DRAW;
		}
		
		else if(playerVal > dealerVal)
		{
			mCode = FinishCode.PLAYER;
		}
		
		else
		{
			mCode = FinishCode.DEALER;
		}
	}
	
	//enumŸ���� FinishCode�� ���� �˸��� ����� ����Ѵ�.
	@SuppressWarnings("incomplete-switch")
	private void DisplayResult()
	{
		switch(mCode)
		{
		case NEEDCALC:
			{
				CalcWinner();
				DisplayResult();
				return;
			}
		case DRAW:
			{
				System.out.print("Equal points...");
				break;
			}
			
		case PLAYER:
			{
				System.out.print("Player Wins...");
				break;
			}
			
		case PLAYERBUSTED:
			{
				System.out.print("Player Busted...");
				break;
			}
			
		case DEALER:
			{
				System.out.print("Dealer Wins...");
				break;
			}
			
		case DEALERBUSTED:
			{
				System.out.print("Dealer Busted...");
				break;
			}
		}
		
		//����ڰ� ���� ����� �ľ��ϱ� ���� �÷��̾�� ������ �� �հ踦 ������ش�
		System.out.println("\t [Player:" + CalcPrice(mPlayerCard) + " : Dealer:" + CalcPrice(mDealerCard) + ']');		
	}
}