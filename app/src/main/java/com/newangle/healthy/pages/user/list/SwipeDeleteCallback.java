//package com.newangle.healthy.pages.user.list;
//
//import android.animation.ObjectAnimator;
//import android.graphics.Canvas;
//import android.support.annotation.NonNull;
//import android.view.View;
//
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class SwipeDeleteCallback extends ItemTouchHelper.SimpleCallback {
//
//    private UserListAdapter adapter;
//    private float swipeThreshold = 0.5f;
//    private int activePosition = -1; // 跟踪当前激活的item位置
//
//    public SwipeDeleteCallback(UserListAdapter adapter) {
//        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
//        this.adapter = adapter;
//    }
//
//    @Override
//    public int getMovementFlags(@NonNull RecyclerView recyclerView,
//                                @NonNull RecyclerView.ViewHolder viewHolder) {
//        // 允许左右滑动
//        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//        return makeMovementFlags(0, swipeFlags);
//    }
//
//    @Override
//    public boolean onMove(@NonNull RecyclerView recyclerView,
//                          @NonNull RecyclerView.ViewHolder viewHolder,
//                          @NonNull RecyclerView.ViewHolder target) {
//        return false; // 不需要拖动功能
//    }
//
//    @Override
//    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//        // 不再这里处理删除，只处理复位
//        if (direction == ItemTouchHelper.END) { // 右滑
//            resetItem(viewHolder);
//            activePosition = -1;
//        }
//    }
//
//    @Override
//    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
//                            @NonNull RecyclerView.ViewHolder viewHolder,
//                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
//
//        UserListAdapter.UserListViewHolder holder = (UserListAdapter.UserListViewHolder) viewHolder;
//        int position = viewHolder.getAdapterPosition();
//
//        // 处理多个item的互斥：当滑动新item时，复位前一个item
//        if (position != activePosition && activePosition != -1) {
//            RecyclerView.ViewHolder activeHolder = recyclerView.findViewHolderForAdapterPosition(activePosition);
//            if (activeHolder != null) resetItem(activeHolder);
//            activePosition = position;
//        }
//
//        float deleteButtonWidth = holder.getBinding().btnDelete.getWidth();
//
//        // 左滑逻辑：显示删除按钮
//        if (dX < 0) {
//            // 限制最大左滑距离
//            if (Math.abs(dX) > deleteButtonWidth) {
//                dX = -deleteButtonWidth;
//            }
//            holder.getBinding().layoutForeground.setTranslationX(dX);
//            holder.getBinding().btnDelete.setVisibility(View.VISIBLE);
//            activePosition = position;
//        }
//        // 右滑逻辑：复位item
//        else if (dX > 0) {
//            // 仅当item已经左滑过时才允许右滑复位
//            if (holder.getBinding().layoutForeground.getTranslationX() < 0) {
//                // 计算复位进度 (0~1)
//                float progress = Math.min(1, dX / deleteButtonWidth);
//                // 将右滑转换为复位移动
//                float targetX = Math.max(holder.getBinding().layoutForeground.getTranslationX() + dX, 0);
//                holder.getBinding().layoutForeground.setTranslationX(targetX);
//
//                // 当完全复位时隐藏按钮
//                if (targetX >= 0) {
//                    holder.getBinding().btnDelete.setVisibility(View.INVISIBLE);
//                    activePosition = -1;
//                }
//            }
//        }
//    }
//
//    @Override
//    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
//        return swipeThreshold;
//    }
//
//    @Override
//    public void clearView(@NonNull RecyclerView recyclerView,
//                          @NonNull RecyclerView.ViewHolder viewHolder) {
//        // 滑动结束时，如果没有完全复位，则保持状态
//        UserListAdapter.UserListViewHolder holder = (UserListAdapter.UserListViewHolder) viewHolder;
//        if (holder.getBinding().layoutForeground.getTranslationX() < 0) {
//            getDefaultUIUtil().clearView(holder.getBinding().layoutForeground);
//        } else {
//            resetItem(viewHolder);
//        }
//    }
//
//    private void resetItem(RecyclerView.ViewHolder viewHolder) {
//        UserListAdapter.UserListViewHolder holder = (UserListAdapter.UserListViewHolder) viewHolder;
//        // 使用动画平滑复位
//        ObjectAnimator animator = ObjectAnimator.ofFloat(
//                holder.getBinding().layoutForeground, "translationX", 0);
//        animator.setDuration(200);
//        animator.start();
//
//        holder.getBinding().btnDelete.setVisibility(View.INVISIBLE);
//    }
//}