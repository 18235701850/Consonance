package com.example.homepage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SlidingLayout extends RelativeLayout implements OnTouchListener {

	public static final int SNAP_VELOCITY = 200;

	private int screenWidth;

	private int leftEdge = 0;

	private int rightEdge = 0;

	private int touchSlop;

	private float xDown;

	private float yDown;

	private float xMove;

	private float yMove;

	private float xUp;

	private boolean isLeftLayoutVisible;

	private boolean isSliding;

	public View leftLayout;

	private View rightLayout;

	private View mBindView;

	private MarginLayoutParams leftLayoutParams;


	private MarginLayoutParams rightLayoutParams;
	private VelocityTracker mVelocityTracker;

	public SlidingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public void setScrollEvent(View bindView) {
		mBindView = bindView;
		mBindView.setOnTouchListener(this);
	}

	public void scrollToLeftLayout() {
		new ScrollTask().execute(-30);
	}

	public void scrollToRightLayout() {
		new ScrollTask().execute(30);
	}

	public boolean isLeftLayoutVisible() {
		return isLeftLayoutVisible;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			leftLayout = getChildAt(0);
			leftLayoutParams = (MarginLayoutParams) leftLayout.getLayoutParams();
			rightEdge = -leftLayoutParams.width;
			rightLayout = getChildAt(1);
			rightLayoutParams = (MarginLayoutParams) rightLayout.getLayoutParams();
			rightLayoutParams.width = screenWidth;
			rightLayout.setLayoutParams(rightLayoutParams);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		if (leftLayout.getVisibility() != View.VISIBLE) {
			leftLayout.setVisibility(View.VISIBLE);
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDown = event.getRawX();
			yDown = event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			xMove = event.getRawX();
			yMove = event.getRawY();
			int moveDistanceX = (int) (xMove - xDown);
			int distanceY = (int) (yMove - yDown);
			if (!isLeftLayoutVisible && moveDistanceX >= touchSlop
					&& (isSliding || Math.abs(distanceY) <= touchSlop)) {
				isSliding = true;
				rightLayoutParams.rightMargin = -moveDistanceX;
				if (rightLayoutParams.rightMargin > leftEdge) {
					rightLayoutParams.rightMargin = leftEdge;
				}
				rightLayout.setLayoutParams(rightLayoutParams);
			}
			if (isLeftLayoutVisible && -moveDistanceX >= touchSlop) {
				isSliding = true;
				rightLayoutParams.rightMargin = rightEdge - moveDistanceX;
				if (rightLayoutParams.rightMargin < rightEdge) {
					rightLayoutParams.rightMargin = rightEdge;
				}
				rightLayout.setLayoutParams(rightLayoutParams);
			}
			break;
		case MotionEvent.ACTION_UP:
			xUp = event.getRawX();
			int upDistanceX = (int) (xUp - xDown);
			if (isSliding) {
				if (wantToShowLeftLayout()) {
					if (shouldScrollToLeftLayout()) {
						scrollToLeftLayout();
					} else {
						scrollToRightLayout();
					}
				} else if (wantToShowRightLayout()) {
					if (shouldScrollToRightLayout()) {
						scrollToRightLayout();
					} else {
						scrollToLeftLayout();
					}
				}
			} else if (upDistanceX < touchSlop && isLeftLayoutVisible) {
				scrollToRightLayout();
			}
			recycleVelocityTracker();
			break;
		}
		if (v.isEnabled()) {
			if (isSliding) {
				unFocusBindView();
				return true;
			}
			if (isLeftLayoutVisible) {
				return true;
			}
			return false;
		}
		return true;
	}

	private boolean wantToShowRightLayout() {
		return xUp - xDown < 0 && isLeftLayoutVisible;
	}

	private boolean wantToShowLeftLayout() {
		return xUp - xDown > 0 && !isLeftLayoutVisible;
	}
	private boolean shouldScrollToLeftLayout() {
		return xUp - xDown > leftLayoutParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	private boolean shouldScrollToRightLayout() {
		return xDown - xUp > leftLayoutParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}


	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	private void unFocusBindView() {
		if (mBindView != null) {
			mBindView.setPressed(false);
			mBindView.setFocusable(false);
			mBindView.setFocusableInTouchMode(false);
		}
	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int rightMargin = rightLayoutParams.rightMargin;
			// 閺嶈宓佹导鐘插弳閻ㄥ嫰锟界喎瀹抽弶銉︾泊閸斻劎鏅棃顫礉瑜版挻绮撮崝銊ュ煂鏉堟儳涔忔潏鍦櫕閹存牕褰告潏鍦櫕閺冭绱濈捄鍐插毉瀵邦亞骞嗛妴锟�
			while (true) {
				rightMargin = rightMargin + speed[0];
				if (rightMargin < rightEdge) {
					rightMargin = rightEdge;
					break;
				}
				if (rightMargin > leftEdge) {
					rightMargin = leftEdge;
					break;
				}
				publishProgress(rightMargin);
				// 娑撹桨绨＄憰浣规箒濠婃艾濮╅弫鍫熺亯娴溠呮晸閿涘本鐦″▎鈥虫儕閻滎垯濞囩痪璺ㄢ柤閻紕婀�20濮ｎ偆顫楅敍宀冪箹閺嶇柉鍊濋惇鍏煎閼宠棄顧勯惇瀣煂濠婃艾濮╅崝銊ф暰閵嗭拷
				sleep(15);
			}
			if (speed[0] > 0) {
				isLeftLayoutVisible = false;
			} else {
				isLeftLayoutVisible = true;
			}
			isSliding = false;
			return rightMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... rightMargin) {
			rightLayoutParams.rightMargin = rightMargin[0];
			rightLayout.setLayoutParams(rightLayoutParams);
			unFocusBindView();
		}

		@Override
		protected void onPostExecute(Integer rightMargin) {
			rightLayoutParams.rightMargin = rightMargin;
			rightLayout.setLayoutParams(rightLayoutParams);
		}
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
