package uk.co.bestbuy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BarCodeView extends View {

	private static final int BARCODE_LENGTH = 90;
	
	public BarCodeView(Context context) {
		super(context);
	}

	public BarCodeView(Context context, AttributeSet  attrs) {
		super(context, attrs);
	}
	
	public BarCodeView(Context context, AttributeSet  attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		String[] barWidthArray = getContext().getResources().getStringArray(R.array.bar_widths);
		int[] barWidths = convertToIntArray(barWidthArray);

		drawBarCode(canvas, barWidths, this.getWidth(), this.getHeight());
	}
	
	private void drawBarCode(Canvas c, int[] bars,
							 int availWidth, int availHeight) {

		Paint paint = new Paint();
		paint.setColor(0xff000000);
		paint.setStyle(Paint.Style.FILL);
		
		int pos=0;

		int[] normalizedBars = reduce(bars, sum(bars)/BARCODE_LENGTH);
		int proportion = availWidth/BARCODE_LENGTH;
		int left = (availWidth - (BARCODE_LENGTH*proportion))/2;
		
		for (int i=0; i<normalizedBars.length; i+=2) {
			
			int blackWidth = normalizedBars[i];
			c.drawRect(left+(pos*proportion), 0,
					   left+(pos*proportion)+(blackWidth*proportion), availHeight,
					   paint);

			if (normalizedBars.length > (i+1)) {
				int whiteWidth = normalizedBars[i+1];
				pos += (blackWidth + whiteWidth);
			}
		}
	}

	private int[] convertToIntArray(String[] array) {
		int[] result = new int[array.length];
		for (int i=0; i<result.length; i++) {
			try {
				result[i] = Integer.parseInt(array[i]);
			} catch (NumberFormatException e) {
				//Handle exception here.
				result[i] = 0;
			}
		}
		
		return result;
	}
	
	private int[] reduce(int[] array, int divisor) {
		int[] result = new int[array.length];

		for (int i=0; i<result.length; i++) {
			result[i] = array[i]/divisor;
		}

		return result;
	}

	private int sum(int[] array) {
		int sum=0;
		
		for (int i=0; i<array.length; i++) {
			sum += array[i];
		}

		return sum;
	}

}
