package uk.co.bestbuy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BarCodeView extends View {
	
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
		int width = this.getWidth();
		int height = this.getHeight();
		
		String[] barWidthArray = getContext().getResources().getStringArray(R.array.bar_widths);
		int[] barWidths = convertToIntArray(barWidthArray);

		drawBarCode(canvas, barWidths, 1, 10, 0, height);
	}
	
	private void drawBarCode(Canvas c, int[] bars, int proportion,
							 int actualLeft, int barcodeTop, int barcodeHeight) {

		Paint paint = new Paint();
		paint.setColor(0xff000000);
		paint.setStyle(Paint.Style.FILL);
		
		int pos=0;
		
		for (int i=0; i<bars.length; i+=2) {
			
			int blackWidth = bars[i];
			c.drawRect(actualLeft+(pos*proportion), barcodeTop, 
					   actualLeft+(pos*proportion)+(blackWidth*proportion), barcodeHeight,
					   paint);
			//c.drawCircle(actualLeft+(pos*proportion), 50, 5, paint);
			Log.d("BarCodeView", "Drew bar position "+i+" of width "+bars[i]+": " 
				  + (actualLeft+(pos*proportion)) + "," + barcodeTop + "," + blackWidth*proportion +"," + barcodeHeight);
			
			if (bars.length > (i+1)) {
				int whiteWidth = bars[i+1];
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

}
