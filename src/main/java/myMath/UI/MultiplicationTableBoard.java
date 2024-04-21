package myMath.UI;

import java.util.function.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import myMath.*;

public class MultiplicationTableBoard extends Application {

	private MultiplicationGenerator operations;
	private Multiplication currentOperation;
	private int countDownSeconds = 0;
	private AnimationTimer tmr;
	private Label operation;
	private TextField result;
	private Label countDown;

	private final int MAX_TIME = 20;

    /**
	 * Launch the application.
	 */
	public static void launch(String[] args) {
        Application.launch(args);
	}

	public void start(Stage stage) {
		operations = new MultiplicationGenerator();
		currentOperation = operations.NewMultiplication();
        Pane root = new Pane();
		String imgPath = Main.class.getClassLoader().getResource("board.png").toString();
		root.setBackground(new Background(new BackgroundImage(new Image(imgPath),
        	BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          	new BackgroundSize(1380, 900, true, true, true, true))));

		root.getChildren().add(operation = GenerateOperationLabel(50, 150));
		root.getChildren().add(result = GenerateResultTextbox(300, 450));
		root.getChildren().add(countDown = GenerateCountDownLabel(1200, 750));

		Scene scene = new Scene(root, 1380, 900);
        stage.setTitle("MyMath");
        stage.setScene(scene);
		stage.setResizable(false);

		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ENTER) {
					if (result.getText().equals("OK!"))
					{ 
						SetNewOperation();
					}
					else
					{
						try 
						{
							int resultValue = Integer.parseInt(result.getText());
							if (currentOperation.CheckResult(resultValue))
							{
								operation.setText(currentOperation.GetResult());
								result.setText("OK!");
								countDownSeconds = MAX_TIME;
							}
							else
							{
								operations.AddOperationToRepeatOften(currentOperation);
								result.setText("");
							}
						}
						catch (Exception ex)
						{
							result.setText("");
						}
					}
					ke.consume(); // <-- stops passing the event to next node
				}  			
			}
		});
		
        stage.show();

		tmr = new TimerMethod(this);
		tmr.start();
    }

	private void SetNewOperation()
	{
		currentOperation = operations.NewMultiplication();
		operation.setText(currentOperation.GetQuestion());
		result.setText("");
		countDownSeconds = MAX_TIME;
	}

	public void OneSecondTimer_Tick()
	{
		if (!result.getText().equals("OK!"))
		{
			countDownSeconds--;
			if (countDownSeconds < 1)
			{
				operations.AddOperationToRepeatOften(currentOperation);
				SetNewOperation();
			}
			countDown.setText(Integer.toString(countDownSeconds));
		}
	}

	private Label GenerateOperationLabel(double x, double y)
	{
		Label label = new Label(currentOperation.GetQuestion());
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 200));
    	label.setLayoutX(x);
		label.setLayoutY(y);
		label.setTextFill(Color.WHITE);
		return label;
	}

	private Label GenerateCountDownLabel(double x, double y)
	{
		Label label = new Label("--");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
    	label.setLayoutX(x);
		label.setLayoutY(y);
		label.setTextFill(Color.WHITE);
		return label;
	}

	private TextField GenerateResultTextbox(double x, double y)
	{
		TextField textField = new TextField ();
        textField.setFont(Font.font("Verdana", FontWeight.BOLD, 200));
    	textField.setLayoutX(x);
		textField.setLayoutY(y);
		textField.setSkin(new TextFieldSkin(textField) {
			{
				// use red color for text
				setTextFill(Color.WHITE);
			}
	
		});
		textField.setBackground(Background.EMPTY);
		UnaryOperator<Change> modifyChange = c -> {
			if (c.isContentChange()) {
				int newLength = c.getControlNewText().length();
				if (newLength > 3) {
					// replace the input text with the last len chars
					String tail = c.getControlNewText().substring(0, 3);
					c.setText(tail);
					// replace the range to complete text
					// valid coordinates for range is in terms of old text
					int oldLength = c.getControlText().length();
					c.setRange(0, oldLength);
				}
			}
			return c;
		};
		textField.setTextFormatter(new TextFormatter(modifyChange));
		return textField;
	}

	@Override
	public void stop(){
		System.out.println("Stage is closing");
	}
}


class TimerMethod extends AnimationTimer {
	private MultiplicationTableBoard board;

	public TimerMethod(MultiplicationTableBoard board) {
		this.board = board;
	}

	private long lastNow = 0;
	//define the handle method
	@Override
	public void handle(long now) {
		
		if (now - lastNow > 1000000000)
		{
			board.OneSecondTimer_Tick();
			lastNow = now;
		}
	}
}
