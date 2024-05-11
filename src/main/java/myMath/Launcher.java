package myMath;

import myMath.UI.MultiplicationTableBoard;

public class Launcher {
        /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        MultiplicationTableBoard.launch(args); // due to https://github.com/javafxports/openjdk-jfx/issues/236#issuecomment-426583174
	}
}
