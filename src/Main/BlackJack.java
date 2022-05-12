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
	
	//게임 중 사용할 카드를 집어넣는 큐
	private LinkedList<Card> mCards;
	
	//게임 중 각 참가자의 카드를 저장하는 벡터
	private Vector<Card> mPlayerCard;
	private Vector<Card> mDealerCard;
	
	static Random Rand = new Random();
	
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
		String tempSuit = "?";
		
		for(int i = 1; i <= MAX_CARD_COUNT / MAX_CARD_TYPE; ++i)
		{
			for(int j = 0; j < MAX_CARD_TYPE; ++j)
			{
				switch(j)
				{
					case 0: 
					{
						tempSuit = "◆";
						
						break;
					}
					
					case 1: 
					{
						tempSuit = "♣";
						
						break;
					}
					
					case 2: 
					{
						tempSuit = "♥";
						
						break;
					}
					
					case 3: 
					{
						tempSuit = "♠";
						
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
		
		//딜러와 플레이어에게 중복되지 않은 카드를 각 두장씩 나눠준다.
		//Queue의 Poll함수로 선두에있는 원소를 리턴하며 제거한다.
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
		Card tempCard;
		
		System.out.println("------------- Jack's BlackJack Game -------------");
		
		System.out.print("  # Dealer: ");
		//0은 A로, 11이상은 각 J Q K로 출력한다 그 외는 정수 그대로
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
	
	//사용자의 입력을 받는다, H / S
	public void InputPlayer()
	{
		Scanner Scan = new Scanner(System.in);
		
		//System.out.printf("딜러의 두번째 카드: %-3s, 다음 카드: %-3s\n", mDealerCard.get(1) >= 10 ? 10 : mDealerCard.get(1), mCards.peek() >= 10 ? 10 : mCards.peek());
		
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
		int randPos = Rand.nextInt(mCards.size());
		mPlayerCard.add(mCards.get(randPos));
		mCards.remove(randPos);
		
		//만약 추가했을 때 총 값이 21을 초과하면 플레이어는 BUSTED한다.
		if(CalcPrice(mPlayerCard) > 21)
		{
			mCode = FinishCode.PLAYERBUSTED;
		}
	}
	
	//Stand을 하면 딜러의 카드벡터에 전체카드 큐를 17이상이 될때까지 전체카드큐에서 poll한다.
	private void Stand()
	{
		while(CalcPrice(mDealerCard) < 17)
		{
			int randPos = Rand.nextInt(mCards.size());
			mDealerCard.add(mCards.get(randPos));
			mCards.remove(randPos);
		}
		
		//만약에 21보다 커지면 딜러는 BUSTED한다.
		if(CalcPrice(mDealerCard) > 21)
		{
			mCode = FinishCode.DEALERBUSTED;
		}
		//그것이 아니라면 플레이어와 딜러의 값을 비교할 필요가 있다.
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
		
		//21을 초과한 시점에서
		//A 카드를 가지고 있고, 그 카드가 11로 계산되고 있을경우
		if(sumRes > 21)
		{
			for(int i = 0; i < cards.size(); ++i)
			{
				//랭크가 11이다? -> Calcto11이 True인경우?
				if(cards.get(i).GetRank() == 11)
				{
					//해당 A카드의 11여부를 false로 하고
					cards.get(i).SetACalcTo11(false);
					//11 -> 1로 했기에 10을 빼서 리턴한다.
					sumRes -= 10;
					
					//디버그용 출력
					System.out.println("A 카드가 11에서 1로 값 변경됨");
				}
			}
		}
		
		return sumRes;
	}

	//게임이 끝나면 누가 이겼는지 계산한다.
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
		System.out.println("\t [Player:" + CalcPrice(mPlayerCard) + " : Dealer:" + CalcPrice(mDealerCard) + ']');		
	}
}