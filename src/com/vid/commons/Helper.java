package com.vid.commons;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Helper {

	private static Map<String, Map<String, Class<?>[]>> classMethods = new HashMap<>();

	/**
	 * @param ClassName
	 * @return MethodParameterMap Map<MethodName, Parameters : Class<?>[]>
	 * 
	 *         <p>
	 *         A sort of cache for the CustomComponent classes setter
	 *         metthods.Stores the method name against the parameters for
	 *         invoking the methods later.
	 *         </p>
	 */
	public static Map<String, Class<?>[]> addToMethodMap(String className) {

		if (getClassMethods().get(className) == null) {
			Map<String, Class<?>[]> methodParameterMap = new HashMap<String, Class<?>[]>();
			try {
				Method[] methods = Class.forName(className).newInstance().getClass().getMethods();
				for (Method method : methods) {
					if (method.getName().startsWith("set")) {
						methodParameterMap.put(method.getName(), method.getParameterTypes());
					}
				}
				getClassMethods().put(className, methodParameterMap);
			} catch (Exception e) {
			}
		}
		return getClassMethods().get(className);
	}

	public static String setTotalTime(long millis) {
		String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		return s;
	}

	public static Map<String, Map<String, Class<?>[]>> getClassMethods() {
		return classMethods;
	}

	public static void setClassMethods(Map<String, Map<String, Class<?>[]>> classMethods) {
		Helper.classMethods = classMethods;
	}

	public static WritableImage createBorderImage(ImageView iew, javafx.scene.paint.Color color) {
		int width = (int) iew.getFitWidth();
		int height = (int) iew.getFitHeight();
		System.out.println("createBorderImage " + width + " " + height);
		WritableImage writableImage = new WritableImage(width, height);
		try {
			PixelWriter writer = writableImage.getPixelWriter();

			for (int i = 0; i < height; i++) {
				writer.setColor(0, i, color);
				writer.setColor(width - 1, i, color);
			}

			for (int j = 0; j < width; j++) {
				writer.setColor(j, 0, color);
				writer.setColor(j, height - 1, color);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return writableImage;
	}

	public static WritableImage createFilledImage(ImageView iew, javafx.scene.paint.Color color) {
		int width = (int) iew.getFitWidth();
		int height = (int) iew.getFitHeight();
		System.out.println("createBorderImage " + width + " " + height);
		WritableImage writableImage = new WritableImage(width, height);
		try {
			PixelWriter writer = writableImage.getPixelWriter();
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					writer.setColor(i, j, color);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return writableImage;
	}

	public static Color getColorWithOpacity(Color color, double opacity) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
	}

}
