package scripts.randoms;

import java.awt.Graphics;

import com.bsbot.hooks.GameInterface;
import com.bsbot.input.Keyboard;
import com.bsbot.wrappers.RSInterface;

import scripts.Script;

public class NumberRandom extends Random {

	@Override
	public int loop() {
		System.out.println("numberrandom");
		RSInterface[] parents = interfaces.getAllParents();
		for (RSInterface parent : parents) {
			if (parent != null) {
				for (GameInterface child : parent.getParentInterface()) {
					if (child != null) {
						if (child.getText() != null && child.getText().contains("word is") && child.getId() !=  2162882|| child.getText() != null && child.getText().contains("the word")) {

							String text = child.getText();
							String[] subString = null;
							if(text.contains("word is")){
							subString = child.getText().split(
									"word is ");
							}else if(text.contains("the word")){
								 subString = child.getText().split(
										"the word ");
							}
							String text2 = subString[1];
							Keyboard k = new Keyboard();
							k.sendKeys(text2);
						}
					}
				}
			}
		}
		return 500;
	}

	@Override
	public boolean startUp() {
		RSInterface[] parents = interfaces.getAllParents();
		for (RSInterface parent : parents) {
			if (parent != null) {
				for (GameInterface child : parent.getParentInterface()) {
					if (child != null) {
						if (child.getText() != null
								&& child.getText().contains("word is") || child.getText() != null && child.getText().contains("the word")) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
