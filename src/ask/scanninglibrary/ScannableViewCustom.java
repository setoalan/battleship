package ask.scanninglibrary;

import java.util.ArrayList;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Provides a basic concrete implementation of a "Scannable".  Most of
 * these functions should be reusable for any potential "Scannable" and
 * so anyone wanting to create their own subclass of "Scannable" should 
 * probably inherit from this, rather than just implementing "Scannable".  
 * Provides default implementations for row grouping, selection, and
 * view highlighting.  Since using "getTouchables" this implementation
 * likely supercedes the need for a separate "ScannableViewGroup".
 */
public class ScannableViewCustom implements Scannable {
	ArrayList<ArrayList<View>> mRows;

	private final View ROOT_VIEW;

	public ScannableViewCustom(View view, View exclude) {
		ROOT_VIEW = view;
		mRows = new ArrayList<ArrayList<View>>();
		if (view == null) return;
		System.out.print("here");
		for(View childView : ROOT_VIEW.getTouchables()) {
			if (!(childView.getParent()).equals(exclude) && (!(childView instanceof ViewGroup) || ((ViewGroup)childView).getTouchables().size() == 0)) {
				boolean added = false;
	
				for (ArrayList<View> row : mRows) {
					if (intersect(row.get(0), childView)) {
						//if ((childView.getParent()).equals(exclude)) break;
						//if ((childView.getParent()).equals(exclude)) 
						row.add(childView);
						added = true;
						break;
					}
				}
	
				if (!added) {
					//if (!((childView.getParent()).equals(exclude)))
					//{
						ArrayList<View> newRow = new ArrayList<View>();
						newRow.add(childView);
						mRows.add(newRow);
					//}
				}
			}
		}
	}

	
	/**
	 * @param row The row the view is in
	 * @param index  The views position in the row
	 * @return The view at [row][index]
	 */
	public View getView(int row, int index) {
		return mRows.get(row).get(index);
	}
	
	/**
	 * Highlights all views in the given row, this method probably shouldn't be overridden.  
	 * @param row The row to highlight
	 */
	public void highlightRow(final ScreenOverlay overlay, final int row) {
		Log("Highlighting Row");
		for(int i = 0; i < numViewsForRow(row); i++) {			
			View view = getView(row, i);
			highlightViewWithColor(overlay, view, Settings.row_color);
		}
	}
	
	/**
	 * Defines how views are highlighted.  Provides a base implementation
	 * which higlights the view at [row][index], as well as highlighting the row
	 * so as to provide a reference for what individual views will be scanned over.
	 * @param row The row the view to be highlighted is in.
	 * @param index The views position within the row
	 */
	public void highlightView(final ScreenOverlay overlay, final int row, final int index) {
		for(int i = 0; i < numViewsForRow(row); i++) {
			View view = getView(row, i);
			if (i != index) highlightViewWithColor(overlay, view, Settings.row_color);
			else highlightViewWithColor(overlay, view, Settings.selection_color);
		}
	}
	
	/**
	 * Defines what row selection means.  Provides a base implementaiton which simply 
	 * highlights the row in the haptic feedback color.  
	 * @param row The row that is selected.
	 */
	public void selectRow(final ScreenOverlay overlay, final int row) {
		for(int i = 0; i < numViewsForRow(row); i++) {
			highlightViewWithColor(overlay, getView(row, i), Settings.haptic_color);
		}				
	}
	
	/**
	 * Defines what view selection means for a given view.  A useful function to override if you would like
	 * to select view groups, or implement delegated event listeners which are more efficient.
	 * @param row The row that the selected view exists in.
	 * @param index The position of the view within the given row.
	 */
	public boolean selectView(final ScreenOverlay overlay, final int row, final int index) {
		
		View selectedView = getView(row, index);
		
		highlightViewWithColor(overlay, selectedView, Settings.haptic_color);
		
		MotionEvent downEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0);
		MotionEvent upEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0);
				
		selectedView.dispatchTouchEvent(downEvent);
		selectedView.dispatchTouchEvent(upEvent);
		
		downEvent.recycle();
		upEvent.recycle();
		
		return true;
	}
	
	public void highlightViewWithColor(ScreenOverlay overlay, View view, int color) {
		View overlayView = new HollowRectangle(view, color, Settings.border_width);
		overlay.highlightView(view, overlayView);
	}
	
	public View getRootView() {
		return ROOT_VIEW;
	}

	private void Log(String msg) {
		Log.wtf(this.toString(), msg);
		
	}

	public int numRows() {
		return mRows.size();
	}

	public int numViewsForRow(int row) {
		if(mRows.size() > 0) {
			return mRows.get(row).size();
		} else {
			return 0;
		}
	}
	
	protected static boolean intersect(View view1, View view2) {
		int[] x1 = new int[2];
		int[] x2 = new int[2];
		view1.getLocationOnScreen(x1);
		view2.getLocationOnScreen(x2);
		float x1_min = x1[1];
		float x1_max = view1.getHeight() + x1_min;
		float x2_min = x2[1];
		float x2_max = view2.getHeight() + x2_min;
		//Log.wtf("kl;","x1_min: "+x1_min+", x1_max: "+x1_max+", x2_min: "+x2_min+", x2_max: "+x2_max);
		if (x2_min >= x1_min && x2_min < x1_max) return true;
		if (x2_max >= x1_min && x2_max <= x1_max) return true;
		if (x1_min >= x2_min && x1_min <= x2_max) return true;
		if (x1_max > x2_min && x1_max <= x2_max) return true;
		return false;
		//if (x1_min == x2_min && x1_max == x2_max) return true;
		//else return false;//this would probably work as well 
		//and be more intuitive but I don't feel like testing 
		//it right now and the current iteration works
	}
}
