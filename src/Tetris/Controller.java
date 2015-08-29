package Tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

	Boolean ismove=true;
	TetrisMain game;

	public Controller(TetrisMain game) {
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.left)) {
			left_shift();
		} else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.right)) {
			right_shift();
		} else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.rotate)) {
			r_rotate();
		} else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.down)) {
			down_shift();
		} else if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)) {
			if (ismove == true) {
				game.timer.stop();
				ismove=false;
			}
			else{
				game.timer.start();
				ismove=true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			while (down_shift())
				;
			Music.music("src/TetrisMusic/Pedal Braking.au");
		} else if (e.getKeyCode() == KeyEvent.VK_Z) {
			l_rotate();
		} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			if (game.change == 0) {
				game.hold = game.type;
				game.newBlock();
				game.change = 1;
			} else {
				int temp = game.hold;
				game.hold = game.type;
				game.type = temp;
				game.x = 3;
				game.y = 0;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_C) {
			if (game.change == 0) {
				game.hold = game.type;
				game.newBlock();
				game.change = 1;
			} else {
				int temp = game.hold;
				game.hold = game.type;
				game.type = temp;
				game.x = 3;
				game.y = 0;
			}
		}
	}

	public void r_rotate() {
		int temp = (game.state + 1) % 4;
		if (game.Judge(game.x, game.y, game.type, temp))
			game.state = temp;
	}

	public void l_rotate() {
		int temp = (game.state + 3) % 4;
		if (game.Judge(game.x, game.y, game.type, temp))
			game.state = temp;
	}

	public void right_shift() {
		if (game.Judge(game.x + 1, game.y, game.type, game.state))
			game.x++;
	}

	public void left_shift() {
		if (game.Judge(game.x - 1, game.y, game.type, game.state))
			game.x--;
	}

	public boolean down_shift() {
		if (game.Judge(game.x, game.y + 1, game.type, game.state))
			game.y++;
		else {
			game.setBlock();
			game.newBlock();
			game.delLine();
			return false;
		}
		return true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
