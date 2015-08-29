package Tetris;

import java.io.*;

import javax.sound.sampled.*;

public class Music extends Thread {
	public void run() {
		// �L����������!!
		while (true) {
			try {
				// �إ� File ����
				File sf = new File("src/TetrisMusic/TetrisBattleBGM.au");

				// ���o�n����J��y
				AudioInputStream astr = AudioSystem.getAudioInputStream(sf);

				// ���o�n���Φ�
				AudioFormat afmt = astr.getFormat();

				// �إ߰T���u��T����
				DataLine.Info inf = new DataLine.Info(SourceDataLine.class, afmt);

				// ���o�ŦX���w�T���u��T���T���u
				SourceDataLine l = (SourceDataLine) AudioSystem.getLine(inf);

				// �H���w�Φ��}�ҰT���u
				l.open(afmt);

				// �}�l�T���u��Ū�g
				l.start();

				// Ū�g�w�İ�
				byte[] buf = new byte[65536];

				// �q�n����yŪ�J��Ƽg�J�V����
				for (int n = 0; (n = astr.read(buf, 0, buf.length)) > 0;) {
					l.write(buf, 0, n);
				}

				// �M���V�����������
				l.drain();

				// ����
				l.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ���֨禡,�u���@��
	public static void music(String path) {
		try {
			// ���o�n����J��y
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream((new File(path)));

			// ���o�n���Φ�
			AudioFormat audioFormat = audioInputStream.getFormat();

			// �w�Ĥj�p�A�p�G���T�ɮפ��j�A�i�H�����s�J�w�ĪŶ��C�o�Ӽƭ����ӭn���ӥγ~�ӨM�w
			int bufferSize = (int) Math.min(audioInputStream.getFrameLength() * audioFormat.getFrameSize(), Integer.MAX_VALUE);

			// �إ߰T���u��T����
			DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioFormat, bufferSize);

			// ���o�ŦX���w�T���u��T���T���u
			Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);
			// �H���w�Φ��}�ҰT���u
			clip.open(audioInputStream);
			// �}�l�T���u��Ū�g
			clip.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}