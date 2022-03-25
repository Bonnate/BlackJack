package Main;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Random;

public class BlackJack 
{	
	FinishCode mCode;
	
	//���ӿ� ����� ī�� ���� (�⺻�� 13)
	static public Integer MAX_CARD_COUNT = 13;
	
	//���� �� ����� ī�带 ����ִ� ť
	private Queue<Integer> mCards;
	
	//���� �� �� �������� ī�带 �����ϴ� ����
	private Vector<Integer> mPlayerCard;
	private Vector<Integer> mDealerCard;
	
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
		//�ӽ÷� ī����� ������ ���� ������ �����
		Vector<Integer> CardsTemp = new Vector<>();
		
		//���� ������ ���ӵǴ� ���� �����Ѵ�.
		for(int i = 1; i <= MAX_CARD_COUNT; ++i)
		{
			CardsTemp.add(i);
		}
		
		//���� �Լ��� ����ϱ� ���� Ŭ������ �����Ѵ�.
		Random random = new Random();
		
		//MAX_CARD_COUNT ������ ������ �ΰ��� �޾� �� �������� ��ġ�� �ش��ϴ� ���Ҹ� ���� �ٲ۴�(swap)
		for(int i = 0; i < 100; ++i)
		{
			int randPos1 = random.nextInt(MAX_CARD_COUNT);
			int randPos2 = random.nextInt(MAX_CARD_COUNT);
			
			
			if(randPos1 == randPos2)
			{
				continue;
			}
			
			int tempVal = CardsTemp.get(randPos1);
			
			CardsTemp.set(randPos1, CardsTemp.get(randPos2));
			CardsTemp.set(randPos2, tempVal);  
		}
		
		//�������� ���� MAX_CARD_COUNT���� ī�带 Queue�� �����Ѵ�.
		//�Լ��� ������ CardsTemp�� �������.
		for(int i = 0; i < MAX_CARD_COUNT; ++i)
		{
			mCards.add(CardsTemp.get(i));
		}
		
		//������ �÷��̾�� �ߺ����� ���� ī�带 �� ���徿 �����ش�.
		//Queue�� Poll�Լ��� ���ο��ִ� ���Ҹ� �����ϸ� �����Ѵ�.
		for(int i = 0; i < 2; ++i)
		{
			mPlayerCard.add(mCards.poll());
			mDealerCard.add(mCards.poll());
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
		System.out.println("------------- Jack's BlackJack Game -------------");
		
		System.out.print("  # Dealer: ");
		//0�� A��, 11�̻��� �� J Q K�� ����Ѵ� �� �ܴ� ���� �״��
		for(int i = 0; i < mDealerCard.size() - 1; ++i)
		{
			int tempVal = mDealerCard.get(i);
			
			if(tempVal > 1 && tempVal < 11)
			{
				System.out.printf("%-3d", tempVal);
			}
			else if(tempVal == 1)
			{
				System.out.print("A  ");
			}
			else if(tempVal == 11)
			{
				System.out.print("J  ");
			}
			else if(tempVal == 12)
			{
				System.out.print("Q  ");
			}
			else if(tempVal == 13)
			{
				System.out.print("K  ");
			}
		}
		
		if(mCode == FinishCode.NONE)
		{
			System.out.print('X');
		}
		else
		{
			int tempVal = mDealerCard.get(mDealerCard.size() - 1);
			
			if(tempVal > 1 && tempVal < 11)
			{
				System.out.printf("%-3d", tempVal);
			}
			else if(tempVal == 1)
			{
				System.out.print("A  ");
			}
			else if(tempVal == 11)
			{
				System.out.print("J  ");
			}
			else if(tempVal == 12)
			{
				System.out.print("Q  ");
			}
			else if(tempVal == 13)
			{
				System.out.print("K  ");
			}
		}
		
		
		
		System.out.println();
		
		System.out.print("  # Player: ");
		for(int i = 0; i < mPlayerCard.size(); ++i)
		{
			int tempVal = mPlayerCard.get(i);
			
			if(tempVal > 1 && tempVal < 11)
			{
				System.out.printf("%-3d", tempVal);
			}
			else if(tempVal == 1)
			{
				System.out.print("A  ");
			}
			else if(tempVal == 11)
			{
				System.out.print("J  ");
			}
			else if(tempVal == 12)
			{
				System.out.print("Q  ");
			}
			else if(tempVal == 13)
			{
				System.out.print("K  ");
			}
		}
		
		System.out.println();
		
		System.out.println("-------------------------------------------------");
	}
	
	//������� �Է��� �޴´�, H / S
	public void InputPlayer()
	{
		Scanner Scan = new Scanner(System.in);
		
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
		mPlayerCard.add(mCards.poll());
		
		//���� �߰����� �� �� ���� 21�� �ʰ��ϸ� �÷��̾�� BUSTED�Ѵ�.
		if(CalcPrice(true) > 21)
		{
			mCode = FinishCode.PLAYERBUSTED;
		}
	}
	
	//Stand�� �ϸ� ������ ī�庤�Ϳ� ��üī�� ť�� 17�̻��� �ɶ����� ��üī��ť���� poll�Ѵ�.
	private void Stand()
	{
		while(CalcPrice(false) < 17)
		{
			mDealerCard.add(mCards.poll());
		}
		
		//���࿡ 21���� Ŀ���� ������ BUSTED�Ѵ�.
		if(CalcPrice(false) > 21)
		{
			mCode = FinishCode.DEALERBUSTED;
		}
		//�װ��� �ƴ϶�� �÷��̾�� ������ ���� ���� �ʿ䰡 �ִ�.
		else
		{
			mCode = FinishCode.NEEDCALC;
		}
	}
	
	//�÷��̾� �Ǵ� ������ ī�尪�� ����Ѵ�. �Ű������� true�̸� �÷��̾�, false�̸� ����.
	//�� �ΰ��� flag�Ҷ� bool�� ������ enum�� ������ int������ �޾ƿ��� �����ϱ� ��
	private int CalcPrice(boolean isPlayer)
	{
		int sumRes = 0;
		int tempValue;
		
		if(isPlayer)
		{
			for(int i = 0; i < mPlayerCard.size(); ++i)
			{
				tempValue = mPlayerCard.get(i);
				
				//�޾ƿ� ���� 10���� ũ�� �� ���� 10�����Ѵ� (J Q K�� 10���� ������)
				if(tempValue > 10) tempValue = 10;
				
				sumRes += tempValue;
			}
		}
		
		else
		{
			for(int i = 0; i < mDealerCard.size(); ++i)
			{
				tempValue = mDealerCard.get(i);
				
				if(tempValue > 10) tempValue = 10;
				
				sumRes += tempValue;
			}
		}
		
		return sumRes;
	}
	
	//������ ������ ���� �̰���� ����Ѵ�.
	private void CalcWinner()
	{
		int playerVal = CalcPrice(true);
		int dealerVal = CalcPrice(false);
		
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
		System.out.println("\t [Player:" + CalcPrice(true) + " : Dealer:" + CalcPrice(false) + ']');
		
	}
}


