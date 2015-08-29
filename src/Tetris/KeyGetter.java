package Tetris;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyGetter {
	public static HashMap<String, Integer> keys; // 存按鍵名稱跟按鍵數字
	public static ArrayList<String> keyNames; // 存按鍵名稱

	public static void LoadKeys() {
		keys = new HashMap<String, Integer>();
		keyNames = new ArrayList<String>();
		Field[] fields = KeyEvent.class.getFields();
		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers())) {
				if (f.getName().startsWith("VK")) {
					try {
						int num = f.getInt(null);
						String name = KeyEvent.getKeyText(num);
						keys.put(name, num);
						keyNames.add(name);
						// System.out.println(num+name);
						// System.out.println(f);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
