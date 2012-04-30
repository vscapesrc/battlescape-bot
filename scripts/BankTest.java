package scripts;

import java.awt.Graphics;

import com.bsbot.wrappers.RSBankItem;

public class BankTest extends Script{

	@Override
	public String author() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String scriptName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int loop() {
		RSBankItem swordFish = banking.getItem("Swordfish");
		if(banking.isOpen()){
			if(swordFish != null){
				swordFish.interact("Withdraw 1");
				return 1000;
			}
		}
		return 200;
	}

	@Override
	public void onBegin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
