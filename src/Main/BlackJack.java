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

	// ���� �� ����� ī�带 ����ִ� ť
	private LinkedList<Card> mCards;

	// ���� �� �� �������� ī�带 �����ϴ� ����
	private Vector<Card> mPlayerCard;
	private Vector<Card> mDealerCard;

	private int mPlayerCost;
	private int mDealerCost;

	private int mCurrentBetCost;
	private int mFlowCost;

	private boolean mIsDataExist;

	static Random Rand = new Random();

	// ������
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
				// ���� �߻� ������ ���
				e.printStackTrace();

				// ����
				System.exit(0);
			}
		}
	}

	private void SaveData() {
		BufferedWriter out = null;

		try {
			// OutputStreamWriter ? UTF-8 ĳ����
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

	// ����� ����������� �ʱ�ȭ�Ѵ�
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
					tempSuit = "��";

					break;
				}

				case 1: {
					tempSuit = "��";

					break;
				}

				case 2: {
					tempSuit = "��";

					break;
				}

				case 3: {
					tempSuit = "��";

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

		// ������ �÷��̾�� �ߺ����� ���� ī�带 �� ���徿 �����ش�.
		// Queue�� Poll�Լ��� ���ο��ִ� ���Ҹ� �����ϸ� �����Ѵ�.
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

		// 0�� A��, 11�̻��� �� J Q K�� ����Ѵ� �� �ܴ� ���� �״��
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

	// Hit�� �ϸ� �÷��̾��� ī�庤�Ϳ� ��üī��ť���� poll�Ͽ� �߰��Ѵ�.
	private void Hit() {
		int randPos = Rand.nextInt(mCards.size());
		mPlayerCard.add(mCards.get(randPos));
		mCards.remove(randPos);

		// ���� �߰����� �� �� ���� 21�� �ʰ��ϸ� �÷��̾�� BUSTED�Ѵ�.
		if (CalcPrice(mPlayerCard) > 21) {
			mCode = FinishCode.PLAYERBUSTED;
		}
	}

	// Stand�� �ϸ� ������ ī�庤�Ϳ� ��üī�� ť�� 17�̻��� �ɶ����� ��üī��ť���� poll�Ѵ�.
	private void Stand() {
		while (CalcPrice(mDealerCard) < 17) {
			int randPos = Rand.nextInt(mCards.size());
			mDealerCard.add(mCards.get(randPos));
			mCards.remove(randPos);
		}

		// ���࿡ 21���� Ŀ���� ������ BUSTED�Ѵ�.
		if (CalcPrice(mDealerCard) > 21) {
			mCode = FinishCode.DEALERBUSTED;
		}
		// �װ��� �ƴ϶�� �÷��̾�� ������ ���� ���� �ʿ䰡 �ִ�.
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

		// 21�� �ʰ��� ��������
		// A ī�带 ������ �ְ�, �� ī�尡 11�� ���ǰ� �������
		if (sumRes > 21) {
			for (int i = 0; i < cards.size(); ++i) {
				// ��ũ�� 11�̴�? -> Calcto11�� True�ΰ��?
				if (cards.get(i).GetRank() == 11) {
					// �ش� Aī���� 11���θ� false�� �ϰ�
					cards.get(i).SetACalcTo11(false);
					// 11 -> 1�� �߱⿡ 10�� ���� �����Ѵ�.
					sumRes -= 10;

					// ����׿� ���
					System.out.println("A ī�尡 11���� 1�� �� �����");
				}
			}
		}

		return sumRes;
	}

	// ������ ������ ���� �̰���� ����Ѵ�.
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

	// enumŸ���� FinishCode�� ���� �˸��� ����� ����Ѵ�.
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