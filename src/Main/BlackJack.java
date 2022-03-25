package Main;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Random;

public class BlackJack 
{	
	FinishCode mCode;
	
	//게임에 사용할 카드 개수 (기본은 13)
	static public Integer MAX_CARD_COUNT = 13;
	
	//게임 중 사용할 카드를 집어넣는 큐
	private Queue<Integer> mCards;
	
	//게임 중 각 참가자의 카드를 저장하는 벡터
	private Vector<Integer> mPlayerCard;
	private Vector<Integer> mDealerCard;
	
	//생성자
	public BlackJack()
	{
		mCards = new LinkedList<>();
		mPlayerCard = new Vector<>();
		mDealerCard = new Vector<>();
		
		mCode = FinishCode.NONE;
		
		InitGame();
	}

	//사용할 멤버변수들을 초기화한다
	public void InitGame()
	{
		//임시로 카드들을 보관할 벡터 변수를 만든다
		Vector<Integer> CardsTemp = new Vector<>();
		
		//벡터 변수에 연속되는 수를 삽입한다.
		for(int i = 1; i <= MAX_CARD_COUNT; ++i)
		{
			CardsTemp.add(i);
		}
		
		//랜덤 함수를 사용하기 위한 클래스를 생성한다.
		Random random = new Random();
		
		//MAX_CARD_COUNT 까지의 랜덤수 두개를 받아 각 랜덤수의 위치에 해당하는 원소를 서로 바꾼다(swap)
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
		
		//무작위로 섞은 MAX_CARD_COUNT개의 카드를 Queue에 삽입한다.
		//함수가 끝나면 CardsTemp는 사라진다.
		for(int i = 0; i < MAX_CARD_COUNT; ++i)
		{
			mCards.add(CardsTemp.get(i));
		}
		
		//딜러와 플레이어에게 중복되지 않은 카드를 각 두장씩 나눠준다.
		//Queue의 Poll함수로 선두에있는 원소를 리턴하며 제거한다.
		for(int i = 0; i < 2; ++i)
		{
			mPlayerCard.add(mCards.poll());
			mDealerCard.add(mCards.poll());
		}
	}
	
	//게임 실행
	public void RunGame()
	{
		//매 턴마다 현재 카드들을 출력하고 시용자 입력을 받는다
		do
		{
			DisplayInfo();
			InputPlayer();
		}
		while (mCode == FinishCode.NONE); //mCode가 NONE(승부가 안난 상태)가 아니라면 빠져나와 결과를 출력한다.
		
		//누가 승리했는지 출력
		DisplayResult();

		//현재 카드들을 한번 더 출력
		DisplayInfo();
	}
	
	//현재 카드들을 출력하는 함수
	public void DisplayInfo()
	{
		System.out.println("------------- Jack's BlackJack Game -------------");
		
		System.out.print("  # Dealer: ");
		//0은 A로, 11이상은 각 J Q K로 출력한다 그 외는 정수 그대로
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
	
	//사용자의 입력을 받는다, H / S
	public void InputPlayer()
	{
		Scanner Scan = new Scanner(System.in);
		
		System.out.print("Hit or Stand? (H/S): ");
		
		String input = Scan.nextLine();
		
		System.out.println();
		
		//string - switch문에서 처리할때 사용자의 입력을 최대한 자연스럽게 받는다 (hit, Hit, h, H...)
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
	
	//Hit을 하면 플레이어의 카드벡터에 전체카드큐에서 poll하여 추가한다.
	private void Hit()
	{
		mPlayerCard.add(mCards.poll());
		
		//만약 추가했을 때 총 값이 21을 초과하면 플레이어는 BUSTED한다.
		if(CalcPrice(true) > 21)
		{
			mCode = FinishCode.PLAYERBUSTED;
		}
	}
	
	//Stand을 하면 딜러의 카드벡터에 전체카드 큐를 17이상이 될때까지 전체카드큐에서 poll한다.
	private void Stand()
	{
		while(CalcPrice(false) < 17)
		{
			mDealerCard.add(mCards.poll());
		}
		
		//만약에 21보다 커지면 딜러는 BUSTED한다.
		if(CalcPrice(false) > 21)
		{
			mCode = FinishCode.DEALERBUSTED;
		}
		//그것이 아니라면 플레이어와 딜러의 값을 비교할 필요가 있다.
		else
		{
			mCode = FinishCode.NEEDCALC;
		}
	}
	
	//플레이어 또는 딜러의 카드값을 계산한다. 매개변수가 true이면 플레이어, false이면 딜러.
	//★ 두개를 flag할때 bool이 나은가 enum이 나은가 int형으로 받아올지 질문하기 ★
	private int CalcPrice(boolean isPlayer)
	{
		int sumRes = 0;
		int tempValue;
		
		if(isPlayer)
		{
			for(int i = 0; i < mPlayerCard.size(); ++i)
			{
				tempValue = mPlayerCard.get(i);
				
				//받아온 값이 10보다 크면 그 값은 10으로한다 (J Q K는 10으로 간주함)
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
	
	//게임이 끝나면 누가 이겼는지 계산한다.
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
	
	//enum타입의 FinishCode에 따라 알맞은 결과를 출력한다.
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
		
		//사용자가 쉽게 결과를 파악하기 위해 플레이어와 딜러의 각 합계를 출력해준다
		System.out.println("\t [Player:" + CalcPrice(true) + " : Dealer:" + CalcPrice(false) + ']');
		
	}
}


