package Main;

import java.util.Vector;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

enum STATE {
	LOADGAME, BET, INGAME, FINISH, PLYAER_SQUANDERD, PLYAER_NOT_SQUANDER,
}

public class BlackJack implements KeyListener {
	private Frame mFrame;
	FinishCode mCode;
	STATE mState;

	static public Integer MAX_CARD_COUNT = 52;
	static public Integer MAX_CARD_TYPE = 4;

	// 게임 중 사용할 카드를 집어넣는 큐
	private LinkedList<Card> mCards;

	// 게임 중 각 참가자의 카드를 저장하는 벡터
	private Vector<Card> mPlayerCard;
	private Vector<Card> mDealerCard;

	private int mPlayerCost;
	private int mDealerCost;

	private int mCurrentBetCost;
	private int mFlowCost;

	private boolean mIsDataExist;

	static Random Rand = new Random();

	// 생성자
	public BlackJack() {
		mFrame = new Frame(this);

		this.mPlayerCost = 100;
		this.mDealerCost = 10000;
		this.mFlowCost = mPlayerCost;

		mState = STATE.LOADGAME;
		

		SelectData();
	}

	private void LoadData() {
		try {

			BufferedReader in= new BufferedReader(new FileReader("data.txt"));


			mPlayerCost = Integer.parseInt(in.readLine());
			mDealerCost = Integer.parseInt(in.readLine());
			
			mFlowCost = mPlayerCost;
			
			    
			in.close();

			
			}

		catch (IOException e) {
			if (e.equals(e)) {
				// 에러 발생 내용을 출력
				e.printStackTrace();

				// 종료
				System.exit(0);
			}
		}
	}

	private void SaveData() {
		BufferedWriter out = null;

		try {
			// OutputStreamWriter ? UTF-8 캐스팅
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.txt", false), "UTF-8"));

			out.write(mPlayerCost + System.lineSeparator());

			out.write(mDealerCost + System.lineSeparator());

			out.close();
		}

		catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not write to file");
		}
	}

	private void SelectData() {
		
		File checkFile = new File("data.txt");
		if(checkFile.exists())
		{
			mIsDataExist = true;
		}
		else
		{
			mIsDataExist = false;
		}
		
		mFrame.SetText(null);

		mFrame.AddText("\n\n");

		mFrame.AddText("   --------------- Jack's BlackJack Game ---------------\n\n");

		mFrame.AddText("   1. START NEW GAME\n\n");

		if (mIsDataExist) {
			mFrame.AddText("   2. CONTINUE GAME\n\n");
		} else {
			mFrame.AddText("\n\n");
		}

		mFrame.AddText("   -------------------------------------------------\n\n");

		mFrame.AddText("   Select : ");
	}

	// 사용할 멤버변수들을 초기화한다
	private void ContinueGame() {
		mCurrentBetCost = (mPlayerCost / 2);
		mCurrentBetCost += mCurrentBetCost % 10;
		mState = STATE.BET;
		mCode = FinishCode.NONE;

		mCards = new LinkedList<>();
		mPlayerCard = new Vector<>();
		mDealerCard = new Vector<>();

		String tempSuit = "?";

		for (int i = 1; i <= MAX_CARD_COUNT / MAX_CARD_TYPE; ++i) {
			for (int j = 0; j < MAX_CARD_TYPE; ++j) {
				switch (j) {
				case 0: {
					tempSuit = "◆";

					break;
				}

				case 1: {
					tempSuit = "♣";

					break;
				}

				case 2: {
					tempSuit = "♥";

					break;
				}

				case 3: {
					tempSuit = "♠";

					break;
				}

				default: {
					System.out.println("Index Error");
					System.exit(0);
				}
				}

				mCards.add(new Card(tempSuit, i));
			}
		}

		int randPos;

		// 딜러와 플레이어에게 중복되지 않은 카드를 각 두장씩 나눠준다.
		// Queue의 Poll함수로 선두에있는 원소를 리턴하며 제거한다.
		for (int i = 0; i < 2; ++i) {
			randPos = Rand.nextInt(mCards.size());
			mPlayerCard.add(mCards.get(randPos));
			mCards.remove(randPos);

			randPos = Rand.nextInt(mCards.size());
			mDealerCard.add(mCards.get(randPos));
			mCards.remove(randPos);
		}

		DealMoney();
	}

	private void DealMoney() {
		mFrame.SetText(null);

		mFrame.AddText("\n\n");

		mFrame.AddText("   --------------- Jack's BlackJack Game ---------------\n\n");

		mFrame.AddText("    # Dealer: ($" + mDealerCost + ")\n");

		mFrame.AddText("    # Player: ($" + mPlayerCost + ")\n");

		mFrame.AddText("   -------------------------------------------------\n\n");

		mFrame.AddText("   How much money do you want to bet?\n\n");

		mFrame.AddText("   " + mCurrentBetCost + "     (Up/Down/Enter)");
	}

	private void DisplayInfo() {
		CalcBet();
		CheckPlayerCost();
		
		Card tempCard;

		mFrame.SetText(null);

		mFrame.AddText("\n\n   --------------- Jack's BlackJack Game ---------------\n");

		mFrame.AddText("    # Betting : $" + mCurrentBetCost + "\n\n");

		mFrame.AddText("    # Dealer: ($" + mDealerCost + ") \t");

		// 0은 A로, 11이상은 각 J Q K로 출력한다 그 외는 정수 그대로
		for (int i = 0; i < mDealerCard.size() - 1; ++i) {
			tempCard = mDealerCard.get(i);

			mFrame.AddText(tempCard.DisplayCard());
		}

		if (mCode == FinishCode.NONE) {
			mFrame.AddText("XX");
		}

		else {
			tempCard = mDealerCard.get(mDealerCard.size() - 1);

			mFrame.AddText(tempCard.DisplayCard());
		}

		mFrame.AddText("\n");

		mFrame.AddText("    # Player:  ($" + mPlayerCost + ") \t");

		for (int i = 0; i < mPlayerCard.size(); ++i) {
			tempCard = mPlayerCard.get(i);

			mFrame.AddText(tempCard.DisplayCard());
		}

		mFrame.AddText("\n");

		mFrame.AddText("   -------------------------------------------------");

		DisplayCurrentState();
	}

	// Hit을 하면 플레이어의 카드벡터에 전체카드큐에서 poll하여 추가한다.
	private void Hit() {
		int randPos = Rand.nextInt(mCards.size());
		mPlayerCard.add(mCards.get(randPos));
		mCards.remove(randPos);

		// 만약 추가했을 때 총 값이 21을 초과하면 플레이어는 BUSTED한다.
		if (CalcPrice(mPlayerCard) > 21) {
			mCode = FinishCode.PLAYERBUSTED;
		}
	}

	// Stand을 하면 딜러의 카드벡터에 전체카드 큐를 17이상이 될때까지 전체카드큐에서 poll한다.
	private void Stand() {
		while (CalcPrice(mDealerCard) < 17) {
			int randPos = Rand.nextInt(mCards.size());
			mDealerCard.add(mCards.get(randPos));
			mCards.remove(randPos);
		}

		// 만약에 21보다 커지면 딜러는 BUSTED한다.
		if (CalcPrice(mDealerCard) > 21) {
			mCode = FinishCode.DEALERBUSTED;
		}
		// 그것이 아니라면 플레이어와 딜러의 값을 비교할 필요가 있다.
		else {
			mCode = FinishCode.NEEDCALC;
		}
	}

	private int CalcPrice(Vector<Card> cards) {
		int sumRes = 0;
		Card tempCard = null;

		for (int i = 0; i < cards.size(); ++i) {
			tempCard = cards.get(i);

			sumRes += tempCard.GetRank();
		}

		// 21을 초과한 시점에서
		// A 카드를 가지고 있고, 그 카드가 11로 계산되고 있을경우
		if (sumRes > 21) {
			for (int i = 0; i < cards.size(); ++i) {
				// 랭크가 11이다? -> Calcto11이 True인경우?
				if (cards.get(i).GetRank() == 11) {
					// 해당 A카드의 11여부를 false로 하고
					cards.get(i).SetACalcTo11(false);
					// 11 -> 1로 했기에 10을 빼서 리턴한다.
					sumRes -= 10;

					// 디버그용 출력
					System.out.println("A 카드가 11에서 1로 값 변경됨");
				}
			}
		}

		return sumRes;
	}

	// 게임이 끝나면 누가 이겼는지 계산한다.
	private void CalcWinner() {
		int playerVal = CalcPrice(mPlayerCard);
		int dealerVal = CalcPrice(mDealerCard);

		if (playerVal > 21 && dealerVal > 21) {
			mCode = FinishCode.DRAW;
		}

		else if (playerVal == dealerVal) {
			mCode = FinishCode.DRAW;
		}

		else if (playerVal > dealerVal) {
			mCode = FinishCode.PLAYER;
		}

		else {
			mCode = FinishCode.DEALER;
		}
	}

	// enum타입의 FinishCode에 따라 알맞은 결과를 출력한다.
	@SuppressWarnings("incomplete-switch")
	private void DisplayCurrentState() {
		if (mCode == FinishCode.NEEDCALC) {
			CalcWinner();
			DisplayInfo();

			return;
		}

		mFrame.AddText("\n\n   ");

		if (mState == STATE.PLYAER_SQUANDERD) {
			mFrame.AddText("\n\n   You lost EVERYTHING!! Quit Game !");
			return;
		}

		switch (mCode) {

		case NONE: {
			mFrame.AddText("Hit or Stand? (H/S): ");
			break;
		}
		case DRAW: {
			mFrame.AddText("Equal points...");
			break;
		}

		case PLAYER: {
			mFrame.AddText("Player Wins...");
			break;
		}

		case PLAYERBUSTED: {
			mFrame.AddText("Player Busted...");
			break;
		}

		case DEALER: {
			mFrame.AddText("Dealer Wins...");
			break;
		}

		case DEALERBUSTED: {
			mFrame.AddText("Dealer Busted...");
			break;
		}
		}

		if (mState == STATE.PLYAER_NOT_SQUANDER) {
			SaveData();
			mFrame.AddText("\n\n   Play Again? (Y/N)");
		}
	}
	
	private void CalcBet()
	{
		switch (mCode) {

		case DRAW: {
			mPlayerCost += mCurrentBetCost;
			break;
		}

		case PLAYER: {
			mDealerCost -= mCurrentBetCost;
			mPlayerCost += mCurrentBetCost * 2;
			break;
		}

		case PLAYERBUSTED: {
			mDealerCost += mCurrentBetCost;
			break;
		}

		case DEALER: {
			mDealerCost += mCurrentBetCost;
			break;
		}

		case DEALERBUSTED: {
			mDealerCost -= mCurrentBetCost;
			mPlayerCost += mCurrentBetCost * 2;
			break;
		}
		case NEEDCALC:
			break;
		case NONE:
			break;
		default:
			break;
		}
	}

	private void CheckPlayerCost() {
		if (mCode == FinishCode.NONE)
			return;

		if (mPlayerCost <= 0) {
			
			if(mCode == FinishCode.DEALERBUSTED || mCode == FinishCode.PLAYER || mCode == FinishCode.DRAW)
			{
				mState = STATE.PLYAER_NOT_SQUANDER;
				return;
			}
			
			mState = STATE.PLYAER_SQUANDERD;
			File file = new File("data.txt");
			
			if(file.exists())
			{
				file.delete();
			}
				
		} else {
			mState = STATE.PLYAER_NOT_SQUANDER;
		}
	}

	private void DisplayResult() {
		
		int flow = mPlayerCost - mFlowCost;
		
		mFrame.SetText(null);

		mFrame.AddText("\n\n   --------------- Jack's BlackJack Game ---------------\n\n");

		if(flow > 0)
		{
			mFrame.AddText("   You earned $" + flow);
		}
		else if (flow < 0)
		{
			mFrame.AddText("   You lost $" + (-flow));
		}
		else
		{
			mFrame.AddText("   No $ changed");
		}

		mFrame.AddText("\n\n");

		mFrame.AddText("   -------------------------------------------------");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_H: {
			if (mState != STATE.INGAME)
				break;

			Hit();
			DisplayInfo();

			break;
		}

		case KeyEvent.VK_S: {
			if (mState != STATE.INGAME)
				break;

			Stand();
			DisplayInfo();
		}

		case KeyEvent.VK_UP: {
			if (mState != STATE.BET)
				break;
			
			if(mCurrentBetCost + 10 > mPlayerCost) break;
			
			mCurrentBetCost += 10;
		
			DealMoney();

			break;
		}

		case KeyEvent.VK_DOWN: {
			if (mState != STATE.BET)
				break;

			if(mCurrentBetCost - 10 < 10) break;
			
			mCurrentBetCost -= 10;
			DealMoney();

			break;
		}

		case KeyEvent.VK_ENTER: {
			if (mState != STATE.BET)
				break;

			mPlayerCost -= mCurrentBetCost;
			mState = STATE.INGAME;
			DisplayInfo();

			break;
		}

		case KeyEvent.VK_Y: {
			if (mState != STATE.PLYAER_NOT_SQUANDER)
				break;
			
			ContinueGame();
			break;
		}

		case KeyEvent.VK_N: {
			if (mState != STATE.PLYAER_NOT_SQUANDER)
				break;
			
			DisplayResult();
			break;
		}

		case KeyEvent.VK_1: {
			if (mState != STATE.LOADGAME)
				break;
			
			mState = STATE.BET;
			ContinueGame();
			break;
		}

		case KeyEvent.VK_2: {
			if (mState != STATE.LOADGAME || !mIsDataExist)
				break;
			
			LoadData();
			ContinueGame();
				
			break;
		}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}