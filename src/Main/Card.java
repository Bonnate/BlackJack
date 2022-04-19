package Main;

public class Card 
{
	private String mSuit;
	private int mRank;
	
	//Aī�带 11�� �������, 1�� ������� �����ϴ� boolean �Լ�
	//Rank(���� ��)�� �����ϴ°��� ������ �� �ִٰ� �Ǵ��Ͽ� boolean���� �⺻ ���� �ǵ帮�� �ʱ�� ����
	private boolean mIsACalc11;
	
	//�⺻������ A�� 11�� ����Ѵ�.
	public Card(String suit, int rank)
	{
		this.mSuit = suit;
		this.mRank = rank;
		
		this.mIsACalc11 = true;
	}
	
	public String GetSuit()
	{
		return mSuit;
	}
	
	//GetRank�� ȣ���ϸ� 10�� �ʰ��ϴ� ���� 10���� �����ϰ�
	//Aī��� mIsACalc11�� �������� ���� 11, 1�� �����Ѵ�.
	//�� �ܴ� Rank�� �°� ����
	public int GetRank()
	{		
		if(mRank > 10)
		{
			return 10;
		}
		
		if(mRank == 1)
		{
			if(mIsACalc11)
			{
				return 11;
			}
			
			else
			{
				return 1;
			}
		}
		
		return mRank;
	}
	
	public void SetACalcTo11(boolean flag)
	{
		mIsACalc11 = flag;
	}
	
	public void DisplayCard()
	{
		String tempRank = null;
		
		switch(mRank)
		{
			case 1:
			{
				tempRank = "A";
				break;
			}
			
			case 11:
			{
				tempRank = "J";
				break;
			}
			
			case 12:
			{
				tempRank = "Q";
				break;
			}
			
			case 13:
			{
				tempRank = "K";
				break;
			}
			
			default:
			{
				tempRank = Integer.toString(mRank);
			}
		}
		
		System.out.print(tempRank + "(" + mSuit + ")  ");
	}
}
