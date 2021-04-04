package com.Carbook.carbook;

/**
 * A simple interface for custom onClick listeners for a RecyclerView
 */
public interface RecyclerViewClickInterface {

    public void onItemClick(int position);

    public void onLongItemClick(int position);
}
